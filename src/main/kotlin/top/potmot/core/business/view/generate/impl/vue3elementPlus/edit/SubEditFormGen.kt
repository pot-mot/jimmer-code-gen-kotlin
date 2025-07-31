package top.potmot.core.business.view.generate.impl.vue3elementPlus.edit

import top.potmot.core.business.meta.LazyGenerateResult
import top.potmot.core.business.meta.LazyGenerated
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.SelectOption
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.toElements
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.FormItemData
import top.potmot.core.business.view.generate.meta.rules.Rules
import top.potmot.core.business.view.generate.meta.rules.existValidRules
import top.potmot.core.business.view.generate.meta.rules.rules
import top.potmot.core.common.typescript.CodeBlock
import top.potmot.core.common.typescript.ConstVariable
import top.potmot.core.common.typescript.Import
import top.potmot.core.common.typescript.ImportType
import top.potmot.core.common.typescript.TsImport
import top.potmot.core.common.typescript.commentLine
import top.potmot.core.common.typescript.emptyLineCode
import top.potmot.core.common.typescript.stringify
import top.potmot.core.common.vue3.Component
import top.potmot.core.common.vue3.EventBind
import top.potmot.core.common.vue3.ModelProp
import top.potmot.core.common.vue3.Prop
import top.potmot.core.common.vue3.PropBind
import top.potmot.core.common.vue3.TagElement
import top.potmot.core.common.vue3.VElse
import top.potmot.core.common.vue3.VIf
import top.potmot.core.common.vue3.emptyLineElement
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException
import top.potmot.utils.collection.iterableMapOf
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
        CodeBlock("type $submitType = ${submitTypes.joinToString(" | ")}${if (typeNotNull) "" else " | undefined"}"),
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
        models += ModelProp(formData, "$dataType | undefined")
        script += CodeBlock(
            """
// 设置默认值
const setDefault = () => {
    formData.value = $createDefault()
}

// 清除
const clear = () => {
    formData.value = undefined
}
            """.trimIndent()
        )
    }

    props += listOf(
        Prop("withOperations", "boolean", required = false, default = "false"),
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
        handleNullableValidate("$formData.value", validateItems, indent),
        emptyLineCode,
        commentLine("提交"),
        handleSubmit("$assertDataTypeAsSubmitType($formData.value)".let {
            if (typeNotNull) it
            else "$formData.value !== undefined ? $it : undefined"
        }, indent),
        emptyLineCode,
        commentLine("取消"),
        handleCancel,
        emptyLineCode,
        exposeValid(indent)
    )

    val form = form(
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
        props += PropBind("class", "sub-form", isLiteral = true)
    }

    if (typeNotNull) {
        template += form
    } else {
        imports += Import("@element-plus/icons-vue", "Plus", "Delete")

        template += TagElement("template") {
            directives += VIf("formData !== undefined")
            children += button(type = ElementPlusComponents.Type.DANGER, icon = "Delete").merge {
                events += EventBind("click", "clear")
            }
            children += form
        }
        template += TagElement("template") {
            directives += VElse
            children += button(type = ElementPlusComponents.Type.PRIMARY, icon = "Plus").merge {
                events += EventBind("click", "setDefault")
            }
        }
    }
}

interface SubEditFormGen : Generator, EditFormItem, EditFormType, EditNullableValid, EditFormDefault {
    private fun subEditFormType(entity: SubEntityBusiness): String {
        val dataType = entity.components.editFormType.name
        val submitTypes = entity.subFormSubmitTypes
        val submitType = submitTypes.joinToString(" | ")

        val imports = mutableListOf<TsImport>()

        imports += Import("$utilPath/message", "sendMessage")
        imports += ImportType(staticPath, submitTypes)
        imports += entity.subEditProperties
            .editEnums { it.subEditProperties }
            .map { ImportType(enumPath, it.name) }

        return buildScopeString(indent) {
            lines(imports.stringify(indent, wrapThreshold))
            if (imports.isNotEmpty()) line()

            append("export type $dataType = ")
            entity.subEditProperties
                .editFormType { it.subEditProperties }
                .stringify(this)
            line()
            line()

            entity.subEditNoIdProperties
                .editNullableValid(this, dataType, submitType)
        }
    }

    @Throws(ModelException.DefaultItemNotFound::class)
    private fun subEditFormDefault(entity: SubEntityBusiness): String {
        val type = entity.components.editFormType.name
        val createDefault = entity.components.editFormDefault.name

        return buildScopeString(indent) {
            line("import type {$type} from \"./${type}\"")
            line()
            scope("export const $createDefault = (): $type => {", "}") {
                append("return ")
                entity.subEditProperties
                    .formDefault { it.subEditProperties }
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
    private fun subEditFormRules(entity: SubEntityBusiness, typeNotNull: Boolean): Rules {
        val type = entity.components.editFormType
        val properties = entity.subEditNoIdProperties
        val rules = iterableMapOf(
            properties.associateWith { it.rules },
            entity.existValidRules(withId = true, properties)
        )

        return Rules(
            functionName = "useRules",
            formData = "formData",
            formDataType = type.name,
            formDataNotNull = typeNotNull,
            formDataTypePath = "@/" + type.fullPathNoSuffix,
            ruleDataType = entity.dto.insertInput,
            ruleDataTypePath = staticPath,
            propertyRules = rules
        )
    }

    private fun subEditFormComponent(entity: SubEntityBusiness, typeNotNull: Boolean): Pair<Component, List<LazyGenerated>> {
        val formData = "formData"

        val submitTypes = entity.subFormSubmitTypes
        val subFormType = entity.components.editFormType
        val subFormDefault = entity.components.editFormDefault
        val subFormRules = entity.rules.subFormRules

        val content = entity.subEditNoIdProperties
            .associateWith { it.toEditFormItem(formData) }

        val component = subForm(
            submitTypes = submitTypes,
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
            subValidateItems = entity.subEditProperties.toRefValidateItems(),
            content = content
        )

        return component to content.values.flatMap { it.lazyItems }
    }

    fun subEditFormFiles(entity: SubEntityBusiness, nullable: Boolean): LazyGenerateResult {
        val typeNotNull = !nullable

        val (component, lazyItems) = subEditFormComponent(entity, typeNotNull)

        val files = listOfNotNull(
            GenerateFile(
                entity.path.rootEntity,
                entity.components.editFormType.fullPath,
                subEditFormType(entity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.SubEdit, GenerateTag.FormType),
            ),
            if (typeNotNull) null else GenerateFile(
                entity.path.rootEntity,
                entity.components.editFormDefault.fullPath,
                subEditFormDefault(entity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.SubEdit, GenerateTag.FormDefault),
            ),
            GenerateFile(
                entity.path.rootEntity,
                entity.components.editForm.fullPath,
                stringify(component),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.SubEdit, GenerateTag.Component),
            ),
            GenerateFile(
                entity.path.rootEntity,
                entity.rules.subFormRules.fullPath,
                stringify(subEditFormRules(entity, typeNotNull)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.SubEdit, GenerateTag.Rules),
            )
        )

        return LazyGenerateResult(
            files,
            lazyItems,
        )
    }
}