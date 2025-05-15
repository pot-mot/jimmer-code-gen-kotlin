package top.potmot.core.business.view.generate.impl.vue3elementPlus.edit

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.LazyGenerateResult
import top.potmot.core.business.meta.LazyGenerated
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.SelectOption
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.table
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.tableColumn
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Type.*
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.FormItemData
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.operationsColumn
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.tableMinWidth
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.tableUtilColumns
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.tableUtilProps
import top.potmot.core.business.view.generate.meta.rules.Rules
import top.potmot.core.business.view.generate.meta.rules.existValidRules
import top.potmot.core.business.view.generate.meta.rules.rules
import top.potmot.core.common.typescript.CodeBlock
import top.potmot.core.common.typescript.ConstVariable
import top.potmot.core.common.typescript.Function
import top.potmot.core.common.typescript.FunctionArg
import top.potmot.core.common.typescript.Import
import top.potmot.core.common.typescript.ImportType
import top.potmot.core.common.typescript.TsImport
import top.potmot.core.common.typescript.commentLine
import top.potmot.core.common.typescript.emptyLineCode
import top.potmot.core.common.typescript.stringify
import top.potmot.core.common.vue3.Component
import top.potmot.core.common.vue3.Element
import top.potmot.core.common.vue3.EventBind
import top.potmot.core.common.vue3.ModelProp
import top.potmot.core.common.vue3.Prop
import top.potmot.core.common.vue3.PropBind
import top.potmot.core.common.vue3.TagElement
import top.potmot.core.common.vue3.VIf
import top.potmot.core.common.vue3.emptyLineElement
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.storePath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException
import top.potmot.utils.collection.iterableMapOf
import top.potmot.utils.string.buildScopeString

fun editTable(
    submitTypes: List<String>,
    submitTypePath: String,
    dataType: String,
    dataTypePath: String,
    createDefault: String,
    defaultPath: String,
    useRules: String,
    useRulesPath: String,
    comment: String,
    idPropertyName: String,
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
        Import(defaultPath, createDefault),
        Import(useRulesPath, useRules),
        Import("$storePath/pageSizeStore", "usePageSizeStore"),
        Import("@element-plus/icons-vue", "Plus", "Delete"),
        Import("$utilPath/confirm", "deleteConfirm"),
    )
    imports += content.values.flatMap { it.imports }

    models += ModelProp(formData, "Array<$dataType>")

    props += tableUtilProps(showIndex = false)
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
        submitEvent(formData, "Array<$submitType>"),
        cancelEvent
    )

    slots += operationsSlot

    script += listOfNotNull(
        ConstVariable(formRef, null, "ref<FormInstance>()"),
        ConstVariable("rules", null, "$useRules($formData)"),
        emptyLineCode,
        ConstVariable("pageSizeStore", null, "usePageSizeStore()"),
        emptyLineCode
    )

    val validateItems = mutableListOf<ValidateItem>()
    validateItems += CommonValidateItem(
        "formValid",
        "const formValid: boolean = await $formRef.value?.validate().catch(() => false) ?? false"
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
        commentLine("多选"),
        ConstVariable("selection", null, "ref<Array<$dataType>>([])"),
        emptyLineCode,
        Function(
            name = "handleSelectionChange",
            args = listOf(FunctionArg("newSelection", "Array<$dataType>")),
            body = listOf(CodeBlock("selection.value = newSelection"))
        ),
        emptyLineCode,
        commentLine("新增"),
        Function(
            name = "handleAdd",
            args = listOf(
                FunctionArg("index", "number")
            ),
            body = listOf(CodeBlock("$formData.value.splice(index, 0, $createDefault())"))
        ),
        emptyLineCode,
        commentLine("删除"),
        Function(
            async = true,
            name = "handleBatchDelete",
            body = listOf(
                ConstVariable("result", null, "await deleteConfirm(\"这些$comment\")"),
                CodeBlock("if (!result) return"),
                CodeBlock("$formData.value = $formData.value.filter(it => !selection.value.includes(it))"),
            )
        ),
        emptyLineCode,
        Function(
            async = true,
            name = "handleSingleDelete",
            args = listOf(
                FunctionArg("index", "number")
            ),
            body = listOf(
                ConstVariable("result", null, "await deleteConfirm(\"该$comment\")"),
                CodeBlock("if (!result) return"),
                CodeBlock("$formData.value.splice(index, 1)"),
            )
        ),
        emptyLineCode,
        exposeValid(indent)
    )

    val propColumns = mutableListOf<Element>()
    val expand = mutableListOf<Element>()

    content.forEach { (property, formItemData) ->
        val formItem = formItem(
            prop = "[scope.${'$'}index, '${property.name}']",
            propIsLiteral = false,
            label = null,
            rules = "rules.${property.name}",
            content = formItemData.elements
        )

        if (property is AssociationProperty && property.isLongAssociation) {
            expand += formItem
        } else {
            propColumns += tableColumn(
                prop = property.name,
                label = property.comment,
                minWidth = property.tableMinWidth,
                content = listOf(formItem)
            )
        }
    }

    val operationsColumn = operationsColumn(
        listOf(
            button(
                icon = "Plus",
                type = INFO,
                link = true,
            ).merge {
                events += EventBind("click", "handleAdd(scope.${'$'}index + 1)")
            },
            button(
                icon = "Delete",
                type = DANGER,
                link = true,
            ).merge {
                events += EventBind("click", "handleSingleDelete(scope.${'$'}index)")
            }
        )
    )

    template += form(
        model = formData,
        ref = formRef,
        labelWidth = null,
        content = listOf(
            TagElement(
                "div",
                props = listOf(
                    PropBind("class", "edit-table-operations", isLiteral = true)
                ),
                children = listOf(
                    button(
                        content = "新增",
                        type = PRIMARY,
                        icon = "Plus"
                    ).merge {
                        events += EventBind("click", "handleAdd($formData.length)")
                    },
                    button(
                        content = "删除",
                        type = DANGER,
                        icon = "Delete"
                    ).merge {
                        events += EventBind("click", "handleBatchDelete")
                        props += PropBind("disabled", "selection.length === 0")
                    }
                )
            ),
            emptyLineElement,
            table(
                data = formData,
                rowKey = idPropertyName,
                columns = tableUtilColumns(idPropertyName) + propColumns +operationsColumn,
                expand = expand,
            ).merge {
                props += PropBind("class", "edit-table", isLiteral = true)
                events += EventBind("selection-change", "handleSelectionChange")
            },
            emptyLineElement,
            operationsSlotElement.merge {
                directives += VIf("withOperations")
            },
        ),
    ).merge {
        props += PropBind("class", "sub-form", isLiteral = true)
    }
}

interface SubEditTableGen : Generator, EditFormItem, EditFormType, EditNullableValid, EditFormDefault {
    private fun subEditTableType(entity: SubEntityBusiness): String {
        val rootEntity = entity.path.rootEntity
        val dataType = entity.components.editTableType.name
        val submitTypes = listOfNotNull(
            if (rootEntity.canAdd) entity.dto.insertInput else null,
            if (rootEntity.canEdit) entity.dto.updateInput else null
        )
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
                .editNullableValid(this, dataType, submitType, listType = true)
        }
    }

    @Throws(ModelException.DefaultItemNotFound::class)
    private fun subEditTableDefault(entity: SubEntityBusiness): String {
        val type = entity.components.editTableType.name
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
    private fun subEditTableRules(entity: SubEntityBusiness): Rules {
        val type = entity.components.editTableType
        val properties = entity.subEditNoIdProperties
        val rules = iterableMapOf(
            properties.associateWith { it.rules },
            entity.existValidRules(withId = true, properties),
        )
        return Rules(
            functionName = "useRules",
            isPlural = true,
            formData = "formData",
            formDataType = type.name,
            formDataTypePath = "@/" + type.fullPathNoSuffix,
            ruleDataType = entity.dto.updateInput,
            ruleDataTypePath = staticPath,
            propertyRules = rules,
        )
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    private fun subEditTableComponent(entity: SubEntityBusiness): Pair<Component, List<LazyGenerated>> {
        val rows = "rows"

        val rootEntity = entity.path.rootEntity
        val editTableType = entity.components.editTableType
        val editTableDefault = entity.components.editFormDefault
        val editTableRules = entity.rules.editTableRules

        val content = entity.subEditNoIdProperties
            .associateWith {
                it.toEditFormItem(
                    "scope.row",
                    excludeSelf = true,
                    entityId = entity.id,
                    idName = entity.idProperty.name
                )
            }

        val component = editTable(
            formData = rows,
            submitTypes = listOfNotNull(
                if (rootEntity.canAdd) entity.dto.insertInput else null,
                if (rootEntity.canEdit) entity.dto.updateInput else null
            ),
            submitTypePath = staticPath,
            dataType = editTableType.name,
            dataTypePath = "@/" + editTableType.fullPathNoSuffix,
            useRules = "useRules",
            createDefault = editTableDefault.name,
            defaultPath = "@/" + editTableDefault.fullPathNoSuffix,
            useRulesPath = "@/" + editTableRules.fullPathNoSuffix,
            indent = indent,
            idPropertyName = entity.idProperty.name,
            comment = entity.comment,
            selectOptions = entity.subFormSelects,
            subValidateItems = entity.subEditProperties.toRefValidateItems(parentIsMultiple = true),
            content = content
        )

        return component to content.values.flatMap { it.lazyItems }
    }

    fun subEditTableFiles(entity: SubEntityBusiness): LazyGenerateResult {
        val (component, lazyItems) = subEditTableComponent(entity)

        val files = listOf(
            GenerateFile(
                entity.path.rootEntity,
                entity.components.editTableType.fullPath,
                subEditTableType(entity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.SubEdit, GenerateTag.EditTable, GenerateTag.FormType),
            ),
            GenerateFile(
                entity.path.rootEntity,
                entity.components.editFormDefault.fullPath,
                subEditTableDefault(entity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.SubEdit, GenerateTag.EditTable, GenerateTag.FormDefault),
            ),
            GenerateFile(
                entity.path.rootEntity,
                entity.components.editTable.fullPath,
                stringify(component),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.SubEdit, GenerateTag.EditTable, GenerateTag.Component),
            ),
            GenerateFile(
                entity.path.rootEntity,
                entity.rules.editTableRules.fullPath,
                stringify(subEditTableRules(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.SubEdit, GenerateTag.EditTable, GenerateTag.Rules),
            )
        )

        return LazyGenerateResult(
            files,
            lazyItems,
        )
    }
}