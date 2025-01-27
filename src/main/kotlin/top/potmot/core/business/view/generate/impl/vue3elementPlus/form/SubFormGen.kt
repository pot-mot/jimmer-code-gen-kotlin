package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.meta.SelectOption
import top.potmot.core.business.view.generate.meta.rules.Rules
import top.potmot.core.business.view.generate.meta.rules.existValidRules
import top.potmot.core.business.view.generate.meta.rules.rules
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.TsImport
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
    submitTypes: List<String>,
    submitTypePath: String,
    dataType: String,
    dataTypePath: String,
    typeNotNull: Boolean,
    createDefault: String,
    defaultPath: String,
    useRules: String,
    useRulesPath: String,
    formData: String = "formData",
    formRef: String = "formRef",
    indent: String,
    subValidateItems: Collection<FormRefValidateItem> = emptyList(),
    selectOptions: Collection<SelectOption> = emptyList(),
    content: Map<PropertyBusiness, FormItemData>,
) = Component {
    val submitType = "SubmitType"
    script += listOf(
        CodeBlock("type $submitType = ${submitTypes.joinToString(" | ")}"),
        emptyLineCode,
    )

    val validateDataForSubmit = "validate${dataType}ForSubmit"
    val assertDataTypeAsSubmitType = "assert${dataType}AsSubmitType"

    imports += listOf(
        Import("vue", "ref"),
        ImportType("element-plus", "FormInstance"),
        formExposeImport,
        ImportType(submitTypePath, submitTypes),
        ImportType(dataTypePath, dataType),
        Import(dataTypePath, validateDataForSubmit, assertDataTypeAsSubmitType),
        Import(useRulesPath, useRules),
    )
    imports += content.values.flatMap { it.imports }

    if (typeNotNull) {
        models += ModelProp(formData, dataType)
    } else {
        imports += Import(defaultPath, createDefault)
        models += ModelProp(formData, dataType, required = false, defaultValue = "$createDefault()")
    }

    props += listOf(
        Prop("withOperations", "boolean", required = false, defaultValue = "false"),
        submitLoadingProp,
    )
    if (selectOptions.isNotEmpty()) {
        imports += selectOptions.map { it.import }
        props += selectOptions.map { it.prop }
    }

    emits += listOf(
        submitEvent(formData, submitType),
        cancelEvent
    )

    slots += operationsSlot

    script += listOf(
        ConstVariable(formRef, null, "ref<FormInstance>()"),
        ConstVariable("rules", null, "$useRules($formData)"),
        emptyLineCode
    )

    val validateItems = mutableListOf<ValidateItem>()
    validateItems += CommonValidateItem(
        "formValid",
        "await $formRef.value?.validate().catch(() => false) ?? false"
    )
    if (subValidateItems.isNotEmpty()) {
        imports += subValidateItems.flatMap { it.imports }
        script += subValidateItems.map { it.ref }
        script += emptyLineCode
        validateItems += subValidateItems
    }

    validateItems += CommonValidateItem(
        "typeValidate",
        "$validateDataForSubmit($formData.value)"
    )

    script += listOf(
        commentLine("校验"),
        handleValidate(validateItems, indent),
        emptyLineCode,
        commentLine("提交"),
        handleSubmit("$assertDataTypeAsSubmitType($formData.value)", indent),
        emptyLineCode,
        commentLine("取消"),
        handleCancel,
        emptyLineCode,
        exposeValid(indent)
    )

    template += form(
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
}

interface SubFormGen : Generator, FormItem, FormType, EditNullableValid, FormDefault, EditTableGen {
    private fun subFormType(entity: SubEntityBusiness): String {
        val rootEntity = entity.path.rootEntity
        val dataType = entity.components.subFormType.name
        val submitTypes = listOfNotNull(
            if (rootEntity.canAdd) entity.dto.insertInput else null,
            if (rootEntity.canEdit) entity.dto.updateInput else null
        )
        val submitType = submitTypes.joinToString(" | ")

        val imports = mutableListOf<TsImport>()

        imports += Import("$utilPath/message", "sendMessage")
        imports += ImportType(staticPath, submitTypes)
        imports += entity.enums.map {
            ImportType(enumPath, it.name)
        }

        return buildScopeString(indent) {
            lines(imports.stringify(indent, wrapThreshold))
            if (imports.isNotEmpty()) line()

            append("export type $dataType = ")
            entity.subEditProperties
                .formType { it.subEditProperties }
                .stringify(this)
            line()
            line()

            entity.subEditNoIdProperties
                .editNullableValid(this, dataType, submitType)
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
                entity.subEditProperties
                    .formDefault { it.subEditProperties }
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
        val type = entity.components.subFormType
        val properties = entity.subEditNoIdProperties
        val rules = iterableMapOf(
            properties.associateWith { it.rules },
            entity.existValidRules(withId = false, properties)
        )

        return Rules(
            functionName = "useRules",
            formData = "formData",
            formDataType = type.name,
            formDataTypePath = "@/" + type.fullPathNoSuffix,
            ruleDataType = entity.dto.insertInput,
            ruleDataTypePath = staticPath,
            propertyRules = rules
        )
    }

    private fun subFormComponent(entity: SubEntityBusiness, typeNotNull: Boolean): Component {
        val formData = "formData"

        val rootEntity = entity.path.rootEntity
        val subFormType = entity.components.subFormType
        val subFormDefault = entity.components.subFormDefault
        val subFormRules = entity.rules.subFormRules

        return subForm(
            submitTypes = listOfNotNull(
                if (rootEntity.canAdd) entity.dto.insertInput else null,
                if (rootEntity.canEdit) entity.dto.updateInput else null
            ),
            submitTypePath = staticPath,
            dataType = subFormType.name,
            dataTypePath = "@/" + subFormType.fullPathNoSuffix,
            typeNotNull = typeNotNull,
            createDefault = subFormDefault.name,
            defaultPath = "@/" + subFormDefault.fullPathNoSuffix,
            useRules = "useRules",
            useRulesPath = "@/" + subFormRules.fullPathNoSuffix,
            formData = formData,
            indent = indent,
            selectOptions = entity.subFormSelects,
            subValidateItems = entity.subEditProperties.toFormRefValidateItems(),
            content = entity.subEditNoIdProperties
                .associateWith { it.createFormItem(formData) }
        )
    }

    private fun subFormFiles(entity: SubEntityBusiness, typeNotNull: Boolean) = listOfNotNull(
        GenerateFile(
            entity.path.rootEntity,
            entity.components.subFormType.fullPath,
            subFormType(entity),
            listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.SubForm, GenerateTag.FormType),
        ),
        if (typeNotNull) null else GenerateFile(
            entity.path.rootEntity,
            entity.components.subFormDefault.fullPath,
            subFormDefault(entity),
            listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.SubForm, GenerateTag.FormDefault),
        ),
        GenerateFile(
            entity.path.rootEntity,
            entity.components.subForm.fullPath,
            stringify(subFormComponent(entity, typeNotNull)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.SubForm),
        ),
        GenerateFile(
            entity.path.rootEntity,
            entity.rules.subFormRules.fullPath,
            stringify(subFormRules(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.FormRules, GenerateTag.SubForm),
        )
    )

    fun deepSubFormFiles(entity: EntityBusiness): List<GenerateFile> {
        val result = mutableListOf<GenerateFile>()

        entity.editSubEntityMap.forEach { (property, entity) ->
            result +=
                if (property.listType) {
                    editTableFiles(entity)
                } else {
                    subFormFiles(entity, property.typeNotNull)
                }

            result += deepSubFormFiles(entity)
        }

        return result
    }
}