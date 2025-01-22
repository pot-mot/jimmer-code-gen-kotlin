package top.potmot.core.business.view.generate.impl.vue3elementPlus.addForm

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.SubValidateItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.cancelEvent
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.exposeValid
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.formExposeImport
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.handleCancel
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.handleSubmit
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.handleValidate
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.operationsSlot
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.operationsSlotElement
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.submitEvent
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.submitLoadingProp
import top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem.FormItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem.FormItemData
import top.potmot.core.business.view.generate.impl.vue3elementPlus.selectOptions.SelectOption
import top.potmot.core.business.view.generate.impl.vue3elementPlus.selectOptions.selectOptions
import top.potmot.core.business.view.generate.meta.rules.Rules
import top.potmot.core.business.view.generate.meta.rules.existValidRules
import top.potmot.core.business.view.generate.meta.rules.rules
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.commentLine
import top.potmot.core.business.view.generate.meta.typescript.emptyLineCode
import top.potmot.core.business.view.generate.meta.typescript.stringify
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.emptyLineElement
import top.potmot.core.business.view.generate.rulePath
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.error.ModelException
import top.potmot.utils.map.iterableMapOf
import top.potmot.utils.string.buildScopeString

fun addForm(
    submitType: String,
    submitTypePath: String,
    dataType: String,
    dataTypePath: String,
    createDefault: String,
    defaultPath: String,
    useRules: String,
    useRulesPath: String,
    formData: String = "formData",
    formRef: String = "formRef",
    indent: String,
    subValidateItems: Collection<SubValidateItem> = emptyList(),
    selectOptions: Iterable<SelectOption> = emptyList(),
    afterValidCodes: String? = null,
    content: Map<PropertyBusiness, FormItemData>,
) = Component(
    imports = listOf(
        Import("vue", "ref"),
        ImportType("element-plus", "FormInstance"),
        formExposeImport,
        ImportType(submitTypePath, submitType),
        ImportType(dataTypePath, dataType),
        Import(defaultPath, createDefault),
        Import(useRulesPath, useRules),
    )
            + content.values.flatMap { it.imports }
            + subValidateItems.map { it.toImport() }
            + selectOptions.map { it.toImport() },
    props = listOf(
        Prop("withOperations", "boolean", required = false, defaultValue = "true"),
        submitLoadingProp,
        *selectOptions.map { it.toProp() }.toTypedArray(),
    ),
    emits = listOf(
        submitEvent(formData, submitType),
        cancelEvent
    ),
    slots = listOf(
        operationsSlot
    ),
    script = listOfNotNull(
        ConstVariable(formData, null, "ref<$dataType>($createDefault())"),
        emptyLineCode,
        ConstVariable(formRef, null, "ref<FormInstance>()"),
        ConstVariable("rules", null, "$useRules($formData)"),
        emptyLineCode,
        *subValidateItems.map { it.toRef() }.toTypedArray(),
        if (subValidateItems.isNotEmpty()) emptyLineCode else null,
        commentLine("校验"),
        handleValidate(formRef, subValidateItems, indent, afterValidCodes),
        emptyLineCode,
        commentLine("提交"),
        handleSubmit(formData, indent, "emits(\"submit\", $formData.value as $submitType)"),
        emptyLineCode,
        commentLine("取消"),
        handleCancel,
        emptyLineCode,
        exposeValid(indent)
    ),
    template = listOf(
        form(
            model = formData,
            ref = formRef,
            rules = "rules",
            content = content.map { (property, formItemData) ->
                formItem(
                    prop = property.name,
                    label = property.comment,
                    content = formItemData.elements
                )
            } + listOf(
                emptyLineElement,
                operationsSlotElement.merge {
                    directives += VIf("withOperations")
                },
            )
        ).merge {
            props += PropBind("@submit.prevent", isLiteral = true)
        }
    )
)

interface AddFormGen : Generator, FormItem, AddFormType, AddFormDefault {
    fun addFormType(entity: EntityBusiness): String {
        val enumImports = entity.enums.map {
            ImportType(enumPath, it.name)
        }

        return buildScopeString(indent) {
            lines(enumImports.stringify(indent, wrapThreshold))

            if (enumImports.isNotEmpty()) line()

            line("export type ${entity.addFormDataType} = {")
            scope {
                entity.addFormProperties.forEach {
                    line("${it.property.name}: ${it.addFormType}")
                }
            }
            line("}")
        }
    }

    @Throws(ModelException.DefaultItemNotFound::class)
    fun addFormDefault(entity: EntityBusiness): String {
        val type = entity.addFormDataType

        return buildScopeString(indent) {
            line("import type {$type} from \"./${entity.addFormDataType}\"")
            line()
            line("export const ${entity.addFormCreateDefault} = (): $type => {")
            scope {
                line("return {")
                scope {
                    entity.addFormProperties.forEach {
                        line("${it.property.name}: ${it.addFormDefault},")
                    }
                }
                line("}")
            }
            line("}")
        }
    }

    @Throws(
        ModelException.IdPropertyNotFound::class,
        ModelException.IndexRefPropertyNotFound::class,
        ModelException.IndexRefPropertyCannotBeList::class
    )
    fun addFormRules(entity: EntityBusiness): Rules {
        val addFormRulesProperties = entity.addFormRulesProperties
        val rules = iterableMapOf(
            addFormRulesProperties.associateWith { it.rules },
            entity.existValidRules(withId = false, addFormRulesProperties)
        )
        return Rules(
            functionName = "useRules",
            formData = "formData",
            formDataType = entity.addFormDataType,
            formDataTypePath = componentPath + "/" + entity.dir + "/" + entity.addFormDataType,
            ruleDataType = entity.dto.insertInput,
            ruleDataTypePath = staticPath,
            propertyRules = rules
        )
    }

    fun addFormComponent(entity: EntityBusiness): Component {
        val formData = "formData"

        val nullableDiffProperties = entity.addFormEditNullableProperties

        val hasNullableDiffProperties = nullableDiffProperties.isNotEmpty()

        val afterValidCodes = nullableDiffProperties.joinToString("\n\n") {
            buildScopeString(indent) {
                line("if (formData.value.${it.name} === undefined) {")
                scope {
                    line("sendMessage(\"${it.comment}不可为空\", \"warning\")")
                    line("return false")
                }
                line("}")
            }
        }

        return addForm(
            submitType = entity.dto.insertInput,
            submitTypePath = staticPath,
            dataType = entity.addFormDataType,
            dataTypePath = componentPath + "/" + entity.dir + "/" + entity.addFormDataType,
            createDefault = entity.addFormCreateDefault,
            defaultPath = componentPath + "/" + entity.dir + "/" + entity.addFormCreateDefault,
            useRules = "useRules",
            useRulesPath = rulePath + "/" + entity.dir + "/" + entity.rules.addFormRules,
            formData = formData,
            indent = indent,
            selectOptions = entity.insertSelectProperties.selectOptions,
            afterValidCodes = afterValidCodes,
            content = entity.addFormProperties
                .associateWith { it.createFormItem(formData) }
        ).merge {
            if (hasNullableDiffProperties) {
                imports += Import("$utilPath/message", "sendMessage")
            }
        }
    }
}