package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.SubEntityBusiness
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
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
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

fun subForm(
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
    models = listOf(
        ModelProp(formData, dataType, required = false, defaultValue = "$createDefault()"),
    ),
    props = listOf(
        Prop("withOperations", "boolean", required = false, defaultValue = "true"),
        submitLoadingProp,
        *selectOptions.map { it.toProp() }.toTypedArray(),
    ),
    emits = emptyList(),
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

interface SubFormGen : Generator, FormItem, FormType, FormDefault, EditTableGen {
    private fun subFormType(entity: SubEntityBusiness): String {
        val enumImports = entity.enums.map {
            ImportType(enumPath, it.name)
        }

        return buildScopeString(indent) {
            lines(enumImports.stringify(indent, wrapThreshold))

            if (enumImports.isNotEmpty()) line()

            append("export type ${entity.components.subFormType.name} = ")
            entity.subFormProperties
                .formType { it.subFormProperties }
                .stringify(this)
            line()
        }
    }

    @Throws(ModelException.DefaultItemNotFound::class)
    private fun subFormDefault(entity: SubEntityBusiness): String {
        val type = entity.components.subFormType.name
        val createDefault = entity.components.subFormDefault.name

        return buildScopeString(indent) {
            line("import type {$type} from \"./${type}\"")
            line()
            line("export const $createDefault = (): $type => {")
            scope {
                append("return ")
                entity.subFormProperties
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
    private fun subFormRules(entity: SubEntityBusiness): Rules {
        val subFormType = entity.components.subFormType
        val subFormRulesProperties = entity.subFormRulesProperties
        val rules = iterableMapOf(
            subFormRulesProperties.associateWith { it.rules },
            entity.existValidRules(withId = false, subFormRulesProperties)
        )

        return Rules(
            functionName = "useRules",
            formData = "formData",
            formDataType = subFormType.name,
            formDataTypePath = "@/" + subFormType.fullPathNoSuffix,
            ruleDataType = entity.dto.insertInput,
            ruleDataTypePath = staticPath,
            propertyRules = rules
        )
    }

    private fun subFormComponent(entity: SubEntityBusiness): Component {
        val formData = "formData"

        val nullableDiffProperties = entity.subFormEditNullableProperties

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

        val subFormType = entity.components.subFormType
        val subFormDefault = entity.components.subFormDefault
        val subFormRules = entity.rules.subFormRules

        return subForm(
            submitType = entity.dto.insertInput,
            submitTypePath = staticPath,
            dataType = subFormType.name,
            dataTypePath = "@/" + subFormType.fullPathNoSuffix,
            createDefault = subFormDefault.name,
            defaultPath = "@/" + subFormDefault.fullPathNoSuffix,
            useRules = "useRules",
            useRulesPath = "@/" + subFormRules.fullPathNoSuffix,
            formData = formData,
            indent = indent,
            selectOptions = entity.insertSelectProperties.selectOptions,
            afterValidCodes = afterValidCodes,
            content = entity.subFormProperties
                .associateWith { it.createFormItem(formData) }
        ).merge {
            if (hasNullableDiffProperties) {
                imports += Import("$utilPath/message", "sendMessage")
            }
        }
    }

    private fun subFormFiles(entity: SubEntityBusiness) = listOf(
        GenerateFile(
            entity,
            entity.components.subFormType.fullPath,
            subFormType(entity),
            listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.SubForm, GenerateTag.FormType),
        ),
        GenerateFile(
            entity,
            entity.components.subFormDefault.fullPath,
            subFormDefault(entity),
            listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.SubForm, GenerateTag.FormDefault),
        ),
        GenerateFile(
            entity,
            entity.components.subForm.fullPath,
            stringify(subFormComponent(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.SubForm),
        ),
        GenerateFile(
            entity,
            entity.rules.subFormRules.fullPath,
            stringify(subFormRules(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.FormRules, GenerateTag.SubForm),
        )
    )

    fun deepSubFormFiles(entity: EntityBusiness): List<GenerateFile> {
        val result = mutableListOf<GenerateFile>()

        entity.subEntityMap.forEach { (property, entity) ->
            result +=
                if (property.listType) {
                    editTableFiles(entity)
                } else {
                    subFormFiles(entity)
                }

            result += deepSubFormFiles(entity)
        }

        return result
    }
}