package top.potmot.core.business.view.generate.impl.vue3elementPlus.edit

import top.potmot.core.business.meta.LazyGenerateResult
import top.potmot.core.business.meta.LazyGenerated
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.meta.SelectOption
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.toElements
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.FormItemData
import top.potmot.core.business.view.generate.meta.rules.Rules
import top.potmot.core.business.view.generate.meta.rules.existValidRules
import top.potmot.core.business.view.generate.meta.rules.rules
import top.potmot.core.common.typescript.ConstVariable
import top.potmot.core.common.typescript.Import
import top.potmot.core.common.typescript.ImportType
import top.potmot.core.common.typescript.TsImport
import top.potmot.core.common.typescript.commentLine
import top.potmot.core.common.typescript.emptyLineCode
import top.potmot.core.common.typescript.stringify
import top.potmot.core.common.vue3.Component
import top.potmot.core.common.vue3.ModelProp
import top.potmot.core.common.vue3.Prop
import top.potmot.core.common.vue3.PropBind
import top.potmot.core.common.vue3.VIf
import top.potmot.core.common.vue3.emptyLineElement
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException
import top.potmot.utils.collection.iterableMapOf
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
        Prop("withOperations", "boolean", required = false, default = "true"),
        submitLoadingProp,
    )
    if (selectOptions.isNotEmpty()) {
        imports += ImportType("$utilPath/lazyOptions", "LazyOptions")
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
        "const formValid: boolean = await $formRef.value?.validate().catch(e => {\n" +
                "    errors.push(e)\n" +
                "    return false\n" +
                "}) ?? false"
    )
    if (subValidateItems.isNotEmpty()) {
        imports += subValidateItems.flatMap { it.imports }
        script += subValidateItems.map { it.ref }
        script += emptyLineCode
        validateItems += subValidateItems
    }
    validateItems += CommonValidateItem(
        "typeValidate",
        "const typeValidate: boolean = $validateDataForSubmit($formData.value)"
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
        content = content.toElements(withCommentLabel = true) + listOf(
            emptyLineElement,
            operationsSlotElement.merge {
                directives += VIf("withOperations")
            },
        ),
    ).merge {
        props += PropBind("class", "edit-form", isLiteral = true)
    }
}

interface EditFormGen : Generator, EditFormType, EditNullableValid, EditFormItem {
    private fun editFormType(entity: RootEntityBusiness): String {
        val dataType = entity.components.editFormType.name
        val submitType = entity.dto.updateInput

        val imports = mutableListOf<TsImport>()

        imports += Import("$utilPath/message", "sendMessage")
        imports += ImportType(staticPath, submitType)
        imports += entity.editFormProperties
            .editEnums { it.subEditProperties }
            .map { ImportType(enumPath, it.name) }

        return buildScopeString(indent) {
            lines(imports.stringify(indent, wrapThreshold))
            if (imports.isNotEmpty()) line()

            append("export type $dataType = ")
            entity.editFormProperties
                .editFormType { it.subEditProperties }
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
    private fun editFormComponent(entity: RootEntityBusiness): Pair<Component, List<LazyGenerated>> {
        val formData = "formData"

        val editFormType = entity.components.editFormType
        val editFormRules = entity.rules.editFormRules

        val content = entity.editFormNoIdProperties
            .associateWith {
                it.toEditFormItem(
                    formData,
                    excludeSelf = true,
                    entityId = entity.id,
                    idName = entity.idProperty.name
                )
            }

        val component = editForm(
            formData = formData,
            submitType = entity.dto.updateInput,
            submitTypePath = staticPath,
            dataType = editFormType.name,
            dataTypePath = "@/" + editFormType.fullPathNoSuffix,
            useRules = "useRules",
            useRulesPath = "@/" + editFormRules.fullPathNoSuffix,
            indent = indent,
            selectOptions = entity.updateSelects,
            subValidateItems = entity.editFormProperties.toRefValidateItems(),
            content = content
        )

        return component to content.values.flatMap { it.lazyItems }
    }

    fun editFormFiles(entity: RootEntityBusiness): LazyGenerateResult {
        val (component, lazyItems) = editFormComponent(entity)

        val files = listOf(
            GenerateFile(
                entity,
                entity.components.editFormType.fullPath,
                editFormType(entity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.EditForm, GenerateTag.FormType),
            ),
            GenerateFile(
                entity,
                entity.components.editForm.fullPath,
                stringify(component),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.EditForm, GenerateTag.Component),
            ),
            GenerateFile(
                entity,
                entity.rules.editFormRules.fullPath,
                stringify(editFormRules(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.EditForm, GenerateTag.Rules),
            ),
        )

        return LazyGenerateResult(
            files,
            lazyItems,
        )
    }
}