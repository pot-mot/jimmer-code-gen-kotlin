package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.meta.SelectOption
import top.potmot.core.business.view.generate.meta.rules.Rules
import top.potmot.core.business.view.generate.meta.rules.existValidRules
import top.potmot.core.business.view.generate.meta.rules.rules
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

fun editForm(
    submitType: String,
    submitTypePath: String,
    dataType: String,
    dataTypePath: String,
    useRules: String,
    useRulesPath: String,
    formData: String = "formData",
    formRef: String = "formRef",
    indent: String,
    subValidateItems: Collection<FormRefValidateItem> = emptyList(),
    selectOptions: Collection<SelectOption> = emptyList(),
    content: Map<PropertyBusiness, FormItemData>,
) = Component {
    val validateDataForSubmit = "validate${dataType}ForSubmit"
    val assertDataTypeAsSubmitType = "assert${dataType}AsSubmitType"

    imports += listOf(
        Import("vue", "ref"),
        ImportType("element-plus", "FormInstance"),
        formExposeImport,
        ImportType(submitTypePath, submitType),
        ImportType(dataTypePath, dataType),
        Import(dataTypePath, validateDataForSubmit, assertDataTypeAsSubmitType),
        Import(useRulesPath, useRules),
    )
    imports += content.values.flatMap { it.imports }

    models += ModelProp(formData, dataType)
    props += listOf(
        Prop("withOperations", "boolean", required = false, defaultValue = "true"),
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
        emptyLineCode,
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
        ),
    ).merge {
        props += PropBind("@submit.prevent", isLiteral = true)
    }
}

interface EditFormGen : Generator, FormType, EditNullableValid, FormItem {
    private fun editFormType(entity: RootEntityBusiness): String {
        val dataType = entity.components.editFormType.name
        val submitType = entity.dto.updateInput

        val imports = mutableListOf<TsImport>()

        imports += Import("$utilPath/message", "sendMessage")
        imports += ImportType(staticPath, submitType)
        imports += entity.enums.map {
            ImportType(enumPath, it.name)
        }

        return buildScopeString(indent) {
            lines(imports.stringify(indent, wrapThreshold))
            if (imports.isNotEmpty()) line()

            append("export type $dataType = ")
            entity.editFormProperties
                .formType { it.subEditProperties }
                .stringify(this)
            line()
            line()

            entity.editFormProperties
                .editNullableValid(this, dataType, submitType)
        }
    }

    @Throws(
        ModelException.IdPropertyNotFound::class,
        ModelException.IndexRefPropertyNotFound::class,
        ModelException.IndexRefPropertyCannotBeList::class
    )
    private fun editFormRules(entity: RootEntityBusiness): Rules {
        val type = entity.components.editFormType
        val properties = entity.editFormNoIdProperties
        val rules = iterableMapOf(
            properties.associateWith { it.rules },
            entity.existValidRules(withId = true, properties)
        )
        return Rules(
            functionName = "useRules",
            formData = "formData",
            formDataType = type.name,
            formDataTypePath = "@/" + type.fullPathNoSuffix,
            ruleDataType = entity.dto.updateInput,
            ruleDataTypePath = staticPath,
            propertyRules = rules
        )
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    private fun editFormComponent(entity: RootEntityBusiness): Component {
        val formData = "formData"

        val editFormType = entity.components.editFormType
        val editFormRules = entity.rules.editFormRules

        return editForm(
            formData = formData,
            submitType = entity.dto.updateInput,
            submitTypePath = staticPath,
            dataType = editFormType.name,
            dataTypePath = "@/" + editFormType.fullPathNoSuffix,
            useRules = "useRules",
            useRulesPath = "@/" + editFormRules.fullPathNoSuffix,
            indent = indent,
            selectOptions = entity.updateSelects,
            subValidateItems = entity.editFormProperties.toFormRefValidateItems(),
            content = entity.editFormNoIdProperties
                .associateWith {
                    it.createFormItem(
                        formData,
                        excludeSelf = true,
                        entityId = entity.id,
                        idName = entity.idProperty.name
                    )
                }
        )
    }

    fun editFormFiles(entity: RootEntityBusiness) = listOf(
        GenerateFile(
            entity,
            entity.components.editFormType.fullPath,
            editFormType(entity),
            listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.AddForm, GenerateTag.FormType),
        ),
        GenerateFile(
            entity,
            entity.components.editForm.fullPath,
            stringify(editFormComponent(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.EditForm),
        ),
        GenerateFile(
            entity,
            entity.rules.editFormRules.fullPath,
            stringify(editFormRules(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.FormRules, GenerateTag.EditForm),
        ),
    )
}