package top.potmot.core.business.view.generate.impl.vue3elementPlus.editTable

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.table
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.tableColumn
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Type.*
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
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.emptyLineElement
import top.potmot.core.business.view.generate.rulePath
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.storePath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.error.ModelException
import top.potmot.utils.map.iterableMapOf

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

interface EditTableGen : Generator, FormItem {
    @Throws(
        ModelException.IdPropertyNotFound::class,
        ModelException.IndexRefPropertyNotFound::class,
        ModelException.IndexRefPropertyCannotBeList::class
    )
    fun editTableRules(entity: EntityBusiness): Rules {
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
    fun editTableComponent(entity: EntityBusiness): Component {
        val rows = "rows"

        return editTable(
            formData = rows,
            type = entity.addFormDataType,
            typePath = staticPath,
            useRules = "useRules",
            createDefault = entity.addFormCreateDefault,
            defaultPath = componentPath + "/" + entity.dir + "/" + entity.addFormCreateDefault,
            useRulesPath = rulePath + "/" + entity.dir + "/" + entity.rules.editTableRules,
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
}