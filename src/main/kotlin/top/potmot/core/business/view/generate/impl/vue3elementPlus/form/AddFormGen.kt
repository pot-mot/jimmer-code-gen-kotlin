package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
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
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
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

interface AddFormGen : Generator, FormItem, FormType, FormDefault {
    private fun addFormType(entity: RootEntityBusiness): String {
        val enumImports = entity.enums.map {
            ImportType(enumPath, it.name)
        }

        return buildScopeString(indent) {
            lines(enumImports.stringify(indent, wrapThreshold))

            if (enumImports.isNotEmpty()) line()

            append("export type ${entity.components.addFormType.name} = ")
            entity.addFormProperties
                .formType { it.subFormProperties }
                .stringify(this)
            line()
        }
    }

    @Throws(ModelException.DefaultItemNotFound::class)
    private fun addFormDefault(entity: RootEntityBusiness): String {
        val type = entity.components.addFormType.name
        val createDefault = entity.components.addFormDefault.name

        return buildScopeString(indent) {
            line("import type {$type} from \"./${type}\"")
            line()
            line("export const $createDefault = (): $type => {")
            scope {
                append("return ")
                entity.addFormProperties
                    .formDefault { it.subFormProperties }
                    .stringify(this)
                line()
            }
            line("}")
        }
    }

    @Throws(
        ModelException.IdPropertyNotFound::class,
        ModelException.IndexRefPropertyNotFound::class,
        ModelException.IndexRefPropertyCannotBeList::class
    )
    private fun addFormRules(entity: RootEntityBusiness): Rules {
        val addFormType = entity.components.addFormType
        val addFormRulesProperties = entity.addFormRulesProperties
        val rules = iterableMapOf(
            addFormRulesProperties.associateWith { it.rules },
            entity.existValidRules(withId = false, addFormRulesProperties)
        )

        return Rules(
            functionName = "useRules",
            formData = "formData",
            formDataType = addFormType.name,
            formDataTypePath = "@/" + addFormType.fullPathNoSuffix,
            ruleDataType = entity.dto.insertInput,
            ruleDataTypePath = staticPath,
            propertyRules = rules
        )
    }

    private fun addFormComponent(entity: RootEntityBusiness): Component {
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

        val addFormType = entity.components.addFormType
        val addFormDefault = entity.components.addFormDefault
        val addFormRules = entity.rules.addFormRules

        return addForm(
            submitType = entity.dto.insertInput,
            submitTypePath = staticPath,
            dataType = addFormType.name,
            dataTypePath = "@/" + addFormType.fullPathNoSuffix,
            createDefault = addFormDefault.name,
            defaultPath = "@/" + addFormDefault.fullPathNoSuffix,
            useRules = "useRules",
            useRulesPath = "@/" + addFormRules.fullPathNoSuffix,
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

    fun addFormFiles(entity: RootEntityBusiness) = listOf(
        GenerateFile(
            entity,
            entity.components.addFormType.fullPath,
            addFormType(entity),
            listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.AddForm, GenerateTag.FormType),
        ),
        GenerateFile(
            entity,
            entity.components.addFormDefault.fullPath,
            addFormDefault(entity),
            listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.AddForm, GenerateTag.FormDefault),
        ),
        GenerateFile(
            entity,
            entity.components.addForm.fullPath,
            stringify(addFormComponent(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.AddForm),
        ),
        GenerateFile(
            entity,
            entity.rules.addFormRules.fullPath,
            stringify(addFormRules(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.FormRules, GenerateTag.AddForm),
        )
    )
}