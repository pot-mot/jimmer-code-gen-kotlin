package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.view.generate.builder.vue3.elementPlus.ElementPlus
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.table
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.tableColumn
import top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem.FormItemData
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.operationsColumn
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.tableUtilColumns
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.tableUtilProps
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.FunctionArg
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.commentLine
import top.potmot.core.business.view.generate.meta.typescript.emptyLineCode
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.Event
import top.potmot.core.business.view.generate.meta.vue3.EventArg
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.Slot
import top.potmot.core.business.view.generate.meta.vue3.SlotProp
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.emptyLineElement
import top.potmot.core.business.view.generate.meta.vue3.slotElement
import top.potmot.core.business.view.generate.meta.vue3.styleProp
import top.potmot.core.business.view.generate.staticPath
import top.potmot.entity.dto.GenEntityBusinessView

private const val submitEventName = "submit"
private const val handleSubmitFnName = "handleSubmit"

data class SubValidateItem(
    val componentName: String,
    val ref: String = componentName.replaceFirstChar { it.lowercase() } + "Ref",
    val type: String = "SubFormExpose",
    val typePath: String = "$componentPath/form/SubFormExpose",
    val validateVariable: String = componentName.replaceFirstChar { it.lowercase() } + "Valid",
) {
    fun toRef() =
        ConstVariable(ref, null, "ref<$type>()")

    fun toImport() =
        ImportType(typePath, type)
}

data class SelectOption(
    val name: String,
    val type: String,
    val typePath: String = staticPath,
) {
    fun toImport() =
        ImportType(typePath, type)

    fun toProp() =
        Prop(name, "Array<$type>")

    fun toVariable() =
        ConstVariable(name, null, "ref<Array<$type>>()")
}

private val submitEvent = { formData: String, formDataType: String ->
    Event(
        event = submitEventName,
        args = listOf(
            EventArg(formData, formDataType),
        )
    )
}

private const val submitLoading = "submitLoading"

private val submitLoadingProp = Prop(submitLoading, "boolean", false, "false")

private fun handleSubmit(
    formData: String,
    formRef: String,
    subValidateItems: Collection<SubValidateItem>,
    indent: String,
    afterValidCodes: String? = null,
) = Function(
    async = true,
    name = handleSubmitFnName,
    body = listOfNotNull(
        CodeBlock("if (props.$submitLoading) return\n"),

        ConstVariable("formValid", "boolean | undefined", "await $formRef.value?.validate().catch(() => false)"),
        *subValidateItems.map {
            ConstVariable(
                it.validateVariable,
                "boolean | undefined",
                "await ${it.ref}.value?.formRef?.validate().catch(() => false)"
            )
        }.toTypedArray(),

        CodeBlock(
            "\n",
            "if (", (listOf("formValid") + subValidateItems.map { it.validateVariable }).joinToString(" && "), ") {\n",
            afterValidCodes?.lines()?.joinToString("") {
                "$indent$it\n"
            },
            "${indent}emits(\"submit\", $formData.value)\n",
            "}",
        )
    )
)

private val submitEventBind = EventBind("click", handleSubmitFnName)

private const val cancelEventName = "cancel"
private const val handleCancelFnName = "handleCancel"

private val cancelEvent = Event(
    event = cancelEventName,
    args = emptyList()
)

private fun handleCancel() = Function(
    name = handleCancelFnName,
    body = listOf(
        CodeBlock("emits(\"cancel\")")
    )
)

private val cancelEventBind = EventBind("click", handleCancelFnName)

private val operationsSlot = Slot(
    "operations",
    props = listOf(
        SlotProp(handleSubmitFnName, "() => Promise<void>"),
        SlotProp(handleCancelFnName, "() => void")
    )
)

private val operationsSlotElement = slotElement(
    "operations",
    props = listOf(
        PropBind(handleSubmitFnName, handleSubmitFnName),
        PropBind(handleCancelFnName, handleCancelFnName),
    ),
    content = listOf(
        TagElement(
            "div",
            props = listOf(styleProp("text-align" to "right")),
            children = listOf(
                button(content = "取消", type = ElementPlus.Type.WARNING).merge {
                    events += cancelEventBind
                },
                button(content = "提交", type = ElementPlus.Type.PRIMARY).merge {
                    props += PropBind("loading", submitLoading)
                    events += submitEventBind
                },
            ),
        ),
    )
)

fun addForm(
    submitType: String,
    submitTypePath: String,
    type: String,
    typePath: String,
    default: String,
    defaultPath: String,
    useRules: String,
    useRulesPath: String,
    formData: String = "formData",
    formRef: String = "formRef",
    indent: String,
    subValidateItems: Collection<SubValidateItem> = emptyList(),
    selectOptions: Iterable<SelectOption> = emptyList(),
    afterValidCodes: String? = null,
    content: Map<GenEntityBusinessView.TargetOf_properties, FormItemData>,
) = Component(
    imports = listOf(
        Import("vue", "ref"),
        ImportType("element-plus", "FormInstance"),
        ImportType("$staticPath/form/AddFormExpose", "AddFormExpose"),
        ImportType(submitTypePath, submitType),
        ImportType(typePath, type),
        Import("lodash", "cloneDeep"),
        Import(defaultPath, default),
        Import(useRulesPath, useRules),
    )
            + content.values.flatMap { it.imports }
            + subValidateItems.map { it.toImport() }
            + selectOptions.map { it.toImport() },
    props = listOf(
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
        ConstVariable(formData, null, "ref<$type>(cloneDeep($default))"),
        emptyLineCode,
        ConstVariable(formRef, null, "ref<FormInstance>()"),
        ConstVariable("rules", null, "$useRules($formData)"),
        emptyLineCode,
        *subValidateItems.map { it.toRef() }.toTypedArray(),
        if (subValidateItems.isNotEmpty()) emptyLineCode else null,
        commentLine("提交"),
        handleSubmit(formData, formRef, subValidateItems, indent, afterValidCodes),
        emptyLineCode,
        commentLine("取消"),
        handleCancel(),
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
                operationsSlotElement,
            )
        )
    )
)

private val queryOnChange = EventBind("change", "emits('query')")

fun editForm(
    type: String,
    typePath: String,
    useRules: String,
    useRulesPath: String,
    formData: String = "formData",
    formRef: String = "formRef",
    indent: String,
    subValidateItems: Collection<SubValidateItem> = emptyList(),
    selectOptions: Iterable<SelectOption> = emptyList(),
    afterValidCodes: String? = null,
    content: Map<GenEntityBusinessView.TargetOf_properties, FormItemData>,
) = Component(
    imports = listOf(
        Import("vue", "ref"),
        ImportType("element-plus", "FormInstance"),
        ImportType("$staticPath/form/EditFormExpose", "EditFormExpose"),
        ImportType(typePath, type),
        Import(useRulesPath, useRules)
    )
            + content.values.flatMap { it.imports }
            + subValidateItems.map { it.toImport() }
            + selectOptions.map { it.toImport() },
    models = listOf(
        ModelProp(formData, type),
    ),
    props = listOf(
        submitLoadingProp,
        *selectOptions.map { it.toProp() }.toTypedArray(),
    ),
    emits = listOf(
        submitEvent(formData, type),
        cancelEvent
    ),
    slots = listOf(
        operationsSlot
    ),
    script = listOfNotNull(
        ConstVariable(formRef, null, "ref<FormInstance>()"),
        ConstVariable("rules", null, "$useRules($formData)"),
        emptyLineCode,
        *subValidateItems.map { it.toRef() }.toTypedArray(),
        if (subValidateItems.isNotEmpty()) emptyLineCode else null,
        commentLine("提交"),
        handleSubmit(formData, formRef, subValidateItems, indent, afterValidCodes),
        emptyLineCode,
        commentLine("取消"),
        handleCancel(),
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
                ).merge {
                    events += listOf(queryOnChange)
                }
            } + listOf(
                emptyLineElement,
                operationsSlotElement,
            ),
        )
    )
)

fun editTable(
    type: String,
    typePath: String,
    default: String,
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
    content: Map<GenEntityBusinessView.TargetOf_properties, FormItemData>,
) = Component(
    imports = listOf(
        Import("vue", "ref"),
        ImportType("element-plus", "FormInstance"),
        ImportType("$staticPath/form/AddFormExpose", "AddFormExpose"),
        ImportType(typePath, type),
        Import("lodash", "cloneDeep"),
        Import(defaultPath, default),
        Import(useRulesPath, useRules),
        Import("@element-plus/icons-vue", "Plus", "Delete"),
    )
            + content.values.flatMap { it.imports }
            + subValidateItems.map { it.toImport() }
            + selectOptions.map { it.toImport() },
    models = listOf(
        ModelProp(formData, "Array<$type>"),
    ),
    props = tableUtilProps(showIndex = false) + listOf(
        submitLoadingProp,
        *selectOptions.map { it.toProp() }.toTypedArray(),
    ),
    slots = listOf(
        operationsSlot
    ),
    script = listOfNotNull(
        ConstVariable(formRef, null, "ref<FormInstance>()"),
        ConstVariable("rules", null, "$useRules($formData)"),
        emptyLineCode,
        *subValidateItems.map { it.toRef() }.toTypedArray(),
        if (subValidateItems.isNotEmpty()) emptyLineCode else null,
        commentLine("提交"),
        handleSubmit(formData, formRef, subValidateItems, indent, afterValidCodes),
        emptyLineCode,
        commentLine("取消"),
        handleCancel(),
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
            body = listOf(CodeBlock("$formData.value.push(cloneDeep($default))"))
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
                            type = ElementPlus.Type.PRIMARY,
                            icon = "Plus"
                        ).merge {
                            events += EventBind("click", "handleAdd")
                        },
                        button(
                            content = "删除",
                            type = ElementPlus.Type.DANGER,
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
                                type = ElementPlus.Type.DANGER,
                            ).merge {
                                events += EventBind("click", "handleSingleDelete(scope.${'$'}index)")
                            }
                        )
                    )
                ).merge {
                    events += EventBind("selection-change", "handleSelectionChange")
                },
                emptyLineElement,
                operationsSlotElement,
            ),
        )
    )
)