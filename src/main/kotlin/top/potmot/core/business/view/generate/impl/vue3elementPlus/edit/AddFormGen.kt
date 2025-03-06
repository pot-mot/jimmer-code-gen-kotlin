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
        Import(defaultPath, createDefault),
        Import(useRulesPath, useRules),
    )
    imports += content.values.flatMap { it.imports }

    script += listOf(
        ConstVariable(formData, null, "ref<$dataType>($createDefault())"),
        emptyLineCode,
    )
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
        content = content.toElements(withCommentLabel = true) + listOf(
            emptyLineElement,
            operationsSlotElement.merge {
                directives += VIf("withOperations")
            },
        )
    ).merge {
        props += PropBind("class", "add-form", isLiteral = true)
    }
}

interface AddFormGen : Generator, EditFormItem, EditFormType, EditNullableValid, EditFormDefault {
    private fun addFormType(entity: RootEntityBusiness): String {
        val dataType = entity.components.addFormType.name
        val submitType = entity.dto.insertInput

        val imports = mutableListOf<TsImport>()

        imports += Import("$utilPath/message", "sendMessage")
        imports += ImportType(staticPath, submitType)
        imports += entity.addFormProperties
            .editEnums { it.subEditNoIdProperties }
            .map { ImportType(enumPath, it.name) }

        return buildScopeString(indent) {
            lines(imports.stringify(indent, wrapThreshold))
            if (imports.isNotEmpty()) line()

            append("export type $dataType = ")
            entity.addFormProperties
                .editFormType { it.subEditNoIdProperties }
                .stringify(this)
            line()
            line()

            entity.addFormProperties
                .editNullableValid(this, dataType, submitType)
        }
    }

    @Throws(ModelException.DefaultItemNotFound::class)
    private fun addFormDefault(entity: RootEntityBusiness): String {
        val type = entity.components.addFormType.name
        val createDefault = entity.components.addFormDefault.name

        return buildScopeString(indent) {
            line("import type {$type} from \"./${type}\"")
            line()
            scope("export const $createDefault = (): $type => {", "}") {
                append("return ")
                entity.addFormProperties
                    .formDefault { it.subEditNoIdProperties }
                    .stringify(this)
                line()
            }
        }
    }

    @Throws(
        ModelException.IdPropertyNotFound::class,
        ModelException.IndexRefPropertyNotFound::class,
        ModelException.IndexRefPropertyCannotBeList::class
    )
    private fun addFormRules(entity: RootEntityBusiness): Rules {
        val type = entity.components.addFormType
        val properties = entity.addFormProperties
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

    private fun addFormComponent(entity: RootEntityBusiness): Pair<Component, List<LazyGenerated>> {
        val formData = "formData"

        val addFormType = entity.components.addFormType
        val addFormDefault = entity.components.addFormDefault
        val addFormRules = entity.rules.addFormRules

        val content = entity.addFormProperties
            .associateWith { it.toEditFormItem(formData) }

        val component = addForm(
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
            selectOptions = entity.insertSelects,
            subValidateItems = entity.addFormProperties.toRefValidateItems(),
            content = content
        )

        return component to content.values.flatMap { it.lazyItems }
    }

    fun addFormFiles(entity: RootEntityBusiness): LazyGenerateResult {
        val (component, lazyItems) = addFormComponent(entity)

        val files = listOf(
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
                stringify(component),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.AddForm, GenerateTag.Component),
            ),
            GenerateFile(
                entity,
                entity.rules.addFormRules.fullPath,
                stringify(addFormRules(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.AddForm, GenerateTag.Rules),
            )
        )

        return LazyGenerateResult(
            files,
            lazyItems,
        )
    }
}