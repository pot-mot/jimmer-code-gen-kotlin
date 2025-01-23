package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.table
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.tableColumn
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Type.*
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.selectOptions.SelectOption
import top.potmot.core.business.view.generate.impl.vue3elementPlus.selectOptions.selectOptions
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.operationsColumn
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.tableUtilColumns
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.tableUtilProps
import top.potmot.core.business.view.generate.meta.rules.Rules
import top.potmot.core.business.view.generate.meta.rules.existValidRules
import top.potmot.core.business.view.generate.meta.rules.rules
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.FunctionArg
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.commentLine
import top.potmot.core.business.view.generate.meta.typescript.emptyLineCode
import top.potmot.core.business.view.generate.meta.typescript.stringify
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.emptyLineElement
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.storePath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException
import top.potmot.utils.map.iterableMapOf
import top.potmot.utils.string.buildScopeString

fun editTable(
    type: String,
    typePath: String,
    createDefault: String,
    defaultPath: String,
    useRules: String,
    useRulesPath: String,
    comment: String,
    idPropertyName: String,
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
        ImportType(typePath, type),
        Import(defaultPath, createDefault),
        Import(useRulesPath, useRules),
        Import("$storePath/pageSizeStore", "usePageSizeStore"),
        Import("@element-plus/icons-vue", "Plus", "Delete"),
        Import("$utilPath/confirm", "deleteConfirm"),
    )
            + content.values.flatMap { it.imports }
            + subValidateItems.map { it.toImport() }
            + selectOptions.map { it.toImport() },
    models = listOf(
        ModelProp(formData, "Array<$type>"),
    ),
    props = tableUtilProps(showIndex = false) + listOf(
        Prop("withOperations", "boolean", required = false, defaultValue = "false"),
        submitLoadingProp,
        *selectOptions.map { it.toProp() }.toTypedArray(),
    ),
    emits = listOf(
        submitEvent(formData, "Array<$type>"),
        cancelEvent
    ),
    slots = listOf(
        operationsSlot
    ),
    script = listOfNotNull(
        ConstVariable(formRef, null, "ref<FormInstance>()"),
        ConstVariable("rules", null, "$useRules($formData)"),
        emptyLineCode,
        ConstVariable("pageSizeStore", null, "usePageSizeStore()"),
        emptyLineCode,
        *subValidateItems.map { it.toRef() }.toTypedArray(),
        if (subValidateItems.isNotEmpty()) emptyLineCode else null,
        commentLine("校验"),
        handleValidate(formRef, subValidateItems, indent, afterValidCodes),
        emptyLineCode,
        commentLine("提交"),
        handleSubmit(formData, indent),
        emptyLineCode,
        commentLine("取消"),
        handleCancel,
        emptyLineCode,
        commentLine("多选"),
        ConstVariable("selection", null, "ref<Array<$type>>([])"),
        emptyLineCode,
        Function(
            name = "handleSelectionChange",
            args = listOf(FunctionArg("newSelection", "Array<$type>")),
            body = listOf(CodeBlock("selection.value = newSelection"))
        ),
        emptyLineCode,
        commentLine("新增"),
        Function(
            name = "handleAdd",
            body = listOf(CodeBlock("$formData.value.push($createDefault())"))
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
                CodeBlock("$formData.value = $formData.value.filter((_, i) => i !== index)"),
            )
        ),
        emptyLineCode,
        exposeValid(indent)
    ),
    template = listOf(
        form(
            model = formData,
            ref = formRef,
            rules = "rules",
            content = listOf(
                TagElement(
                    "div",
                    children = listOf(
                        button(
                            content = "新增",
                            type = PRIMARY,
                            icon = "Plus"
                        ).merge {
                            events += EventBind("click", "handleAdd")
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
                    columns = tableUtilColumns(idPropertyName) + content.map { (property, formItemData) ->
                        tableColumn(
                            prop = property.name,
                            label = property.comment,
                            content = listOf(
                                formItem(
                                    prop = "[scope.${'$'}index, '${property.name}']",
                                    propIsLiteral = false,
                                    label = property.comment,
                                    rule = "rules.${property.name}",
                                    content = formItemData.elements
                                )
                            )
                        )
                    } + operationsColumn(
                        listOf(
                            button(
                                icon = "Delete",
                                type = DANGER,
                                link = true,
                            ).merge {
                                events += EventBind("click", "handleSingleDelete(scope.${'$'}index)")
                            }
                        )
                    )
                ).merge {
                    events += EventBind("selection-change", "handleSelectionChange")
                },
                emptyLineElement,
                operationsSlotElement.merge {
                    directives += VIf("withOperations")
                },
            ),
        ).merge {
            props += PropBind("@submit.prevent", isLiteral = true)
        }
    )
)

interface EditTableGen : Generator, FormItem, FormType, FormDefault {
    private fun editTableType(entity: SubEntityBusiness): String {
        val enumImports = entity.enums.map {
            ImportType(enumPath, it.name)
        }

        return buildScopeString(indent) {
            lines(enumImports.stringify(indent, wrapThreshold))

            if (enumImports.isNotEmpty()) line()

            append("export type ${entity.components.editTableType.name} = ")
            entity.subFormProperties
                .formType { it.subFormProperties }
                .stringify(this)
            line()
        }
    }

    @Throws(ModelException.DefaultItemNotFound::class)
    private fun editTableDefault(entity: SubEntityBusiness): String {
        val type = entity.components.editTableType.name
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
    private fun editTableRules(entity: SubEntityBusiness): Rules {
        val editTableRulesProperties = entity.subFormRulesProperties
        val rules = iterableMapOf(
            editTableRulesProperties.associateWith { it.rules },
            entity.existValidRules(withId = false, editTableRulesProperties),
        )
        return Rules(
            functionName = "useRules",
            isPlural = true,
            formData = "formData",
            formDataType = entity.dto.updateInput,
            formDataTypePath = staticPath,
            propertyRules = rules,
        )
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    private fun editTableComponent(entity: SubEntityBusiness): Component {
        val rows = "rows"

        return editTable(
            formData = rows,
            type = entity.components.editTableType.name,
            typePath = staticPath,
            useRules = "useRules",
            createDefault = entity.components.subFormDefault.name,
            defaultPath = "@/" + entity.components.subFormDefault.fullPathNoSuffix,
            useRulesPath = "@/" + entity.rules.editTableRules.fullPathNoSuffix,
            indent = indent,
            idPropertyName = entity.idProperty.name,
            comment = entity.comment,
            selectOptions = entity.subFormSelectProperties.selectOptions,
            content = entity.subFormProperties
                .associateWith {
                    it.createFormItem(
                        "scope.row",
                        excludeSelf = true,
                        entityId = entity.id,
                        idName = entity.idProperty.name
                    )
                }
        )
    }

    fun editTableFiles(entity: SubEntityBusiness) = listOf(
        GenerateFile(
            entity,
            entity.components.editTableType.fullPath,
            editTableType(entity),
            listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.AddForm, GenerateTag.FormType),
        ),
        GenerateFile(
            entity,
            entity.components.subFormDefault.fullPath,
            editTableDefault(entity),
            listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.AddForm, GenerateTag.FormDefault),
        ),
        GenerateFile(
            entity,
            entity.components.editTable.fullPath,
            stringify(editTableComponent(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.EditTable),
        ),
        GenerateFile(
            entity,
            entity.rules.editTableRules.fullPath,
            stringify(editTableRules(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.FormRules, GenerateTag.EditTable),
        )
    )
}