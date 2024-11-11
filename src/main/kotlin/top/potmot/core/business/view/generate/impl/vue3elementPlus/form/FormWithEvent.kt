package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.view.generate.builder.vue3.componentLib.ElementPlus
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.table
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.tableColumn
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.commentBlock
import top.potmot.core.business.view.generate.meta.typescript.emptyLineCode
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.Event
import top.potmot.core.business.view.generate.meta.vue3.EventArg
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.Slot
import top.potmot.core.business.view.generate.meta.vue3.SlotProp
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.emptyLineElement
import top.potmot.core.business.view.generate.meta.vue3.slotElement
import top.potmot.core.business.view.generate.meta.vue3.slotTemplate
import top.potmot.core.business.view.generate.meta.vue3.styleProp
import top.potmot.core.business.view.generate.staticPath
import top.potmot.entity.dto.GenEntityBusinessView

private const val submitEventName = "submit"
private const val handleSubmitFnName = "handleSubmit"

data class SubValidateItem(
    val componentName: String,
    val ref: String = componentName.replaceFirstChar { it.lowercase() } + "Ref",
    val type: String = "SubFormExpose",
    val validateVariable: String = componentName.replaceFirstChar { it.lowercase() } + "Valid",
) {
    fun toRef() =
        ConstVariable(ref, null, "ref<$type>()")
}

data class SelectOption(
    val name: String,
    val type: String,
) {
    fun toProp() =
        Prop(name, type)

    fun toVariable() =
        ConstVariable(name, null, "ref<$type>()")
}

private val submitEvent = { formData: String, formDataType: String ->
    Event(
        event = submitEventName,
        args = listOf(
            EventArg(formData, formDataType),
        )
    )
}

private fun handleSubmit(
    formData: String,
    formRef: String,
    subValidateItems: Iterable<SubValidateItem>,
    indent: String,
) = Function(
    true,
    handleSubmitFnName,
    body = listOf(
        ConstVariable("formValid", "boolean | undefined", "await $formRef.value?.validate().catch(() => false)"),
        *subValidateItems.map {
            ConstVariable(
                it.validateVariable,
                "boolean | undefined",
                "await ${it.ref}.value?.formRef?.validate().catch(() => false)"
            )
        }.toTypedArray(),
        CodeBlock(
            buildString {
                append("if (")
                append((listOf("formValid") + subValidateItems.map { it.validateVariable }).joinToString(" && "))
                appendLine(")")
                append("${indent}emits(\"submit\", $formData.value)")
            }
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
        CodeBlock(
            "emits(\"cancel\")"
        )
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
        handleSubmitFnName,
        handleCancelFnName,
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
    subValidateItems: Iterable<SubValidateItem> = emptyList(),
    selectOptions: Iterable<SelectOption> = emptyList(),
    content: Map<GenEntityBusinessView.TargetOf_properties, List<TagElement>>,
) = Component(
    imports = listOf(
        Import("vue", listOf("ref")),
        ImportType("element-plus", listOf("FormInstance")),
        ImportType("$staticPath/form/AddFormExpose", listOf("AddFormExpose")),
        ImportType(submitTypePath, listOf(submitType)),
        ImportType(typePath, listOf(type)),
        Import("lodash", listOf("cloneDeep")),
        Import(defaultPath, listOf(default)),
        Import(useRulesPath, listOf(useRules)),
    ),
    props = listOf(
        Prop(
            formData, type,
        ),
        *selectOptions.map { it.toProp() }.toTypedArray(),
    ),
    emits = listOf(
        submitEvent(formData, submitType),
        cancelEvent
    ),
    slots = listOf(
        operationsSlot
    ),
    script = listOf(
        ConstVariable(formData, null, "ref<$type>(cloneDeep($default))"),
        emptyLineCode,
        ConstVariable(formRef, null, "ref<FormInstance>()"),
        ConstVariable("rules", null, "$useRules($formData)"),
        emptyLineCode,
        *subValidateItems.map { it.toRef() }.toTypedArray(),
        commentBlock("提交"),
        handleSubmit(formData, formRef, subValidateItems, indent),
        emptyLineCode,
        commentBlock("取消"),
        handleCancel(),
    ),
    template = listOf(
        form(
            model = formData,
            ref = formRef,
            rules = "rules",
            content = listOf(
                *content.map { (property, elements) ->
                    formItem(
                        property.name,
                        property.comment,
                        content = elements
                    )
                }.toTypedArray(),
                emptyLineElement,
                operationsSlotElement,
            ),
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
    subValidateItems: Iterable<SubValidateItem> = emptyList(),
    selectOptions: Iterable<SelectOption> = emptyList(),
    content: Map<GenEntityBusinessView.TargetOf_properties, List<TagElement>>,
) = Component(
    imports = listOf(
        Import("vue", listOf("ref")),
        ImportType("element-plus", listOf("FormInstance")),
        ImportType("$staticPath/form/EditFormExpose", listOf("EditFormExpose")),
        ImportType(typePath, listOf(type)),
        Import(useRulesPath, listOf(useRules))
    ),
    models = listOf(
        ModelProp(formData, type),
    ),
    props = listOf(
        *selectOptions.map { it.toProp() }.toTypedArray(),
    ),
    emits = listOf(
        submitEvent(formData, type),
        cancelEvent
    ),
    slots = listOf(
        operationsSlot
    ),
    script = listOf(
        ConstVariable(formRef, null, "ref<FormInstance>()"),
        ConstVariable("rules", null, "$useRules($formData)"),
        emptyLineCode,
        *subValidateItems.map { it.toRef() }.toTypedArray(),
        commentBlock("提交"),
        handleSubmit(formData, formRef, subValidateItems, indent),
        emptyLineCode,
        commentBlock("取消"),
        handleCancel(),
    ),
    template = listOf(
        form(
            model = formData,
            ref = formRef,
            rules = "rules",
            content = listOf(
                *content.map { (property, elements) ->
                    formItem(
                        property.name,
                        property.comment,
                        content = elements
                    ).merge {
                        events += listOf(queryOnChange)
                    }
                }.toTypedArray(),
                emptyLineElement,
                operationsSlotElement,
            ),
        )
    )
)

private const val idColumn = "idColumn"
private const val indexColumn = "indexColumn"
private const val selectionColumn = "selectionColumn"

fun editTable(
    type: String,
    typePath: String,
    useRules: String,
    useRulesPath: String,
    idPropertyName: String,
    formData: String = "formData",
    formRef: String = "formRef",
    indent: String,
    subValidateItems: Iterable<SubValidateItem> = emptyList(),
    selectOptions: Iterable<SelectOption> = emptyList(),
    content: Map<GenEntityBusinessView.TargetOf_properties, List<TagElement>>,
) = Component(
    imports = listOf(
        Import("vue", listOf("ref")),
        ImportType("element-plus", listOf("FormInstance")),
        ImportType("$staticPath/form/AddFormExpose", listOf("AddFormExpose")),
        ImportType(typePath, listOf(type)),
        Import(useRulesPath, listOf(useRules))
    ),
    models = listOf(
        ModelProp(formData, "Array<$type>"),
    ),
    props = listOf(
        Prop(idColumn, "boolean", false, "false"),
        Prop(indexColumn, "boolean", false, "false"),
        Prop(selectionColumn, "boolean", false, "true"),
        *selectOptions.map { it.toProp() }.toTypedArray(),
    ),
    slots = listOf(
        operationsSlot
    ),
    script = listOf(
        ConstVariable(formRef, null, "ref<FormInstance>()"),
        ConstVariable("rules", null, "$useRules($formData)"),
        emptyLineCode,
        *subValidateItems.map { it.toRef() }.toTypedArray(),
        commentBlock("提交"),
        handleSubmit(formData, formRef, subValidateItems, indent),
        emptyLineCode,
        commentBlock("取消"),
        handleCancel(),
        emptyLineCode,
        CodeBlock(
            """
// 多选
const selection = ref<Array<InWarehouseInsertInput_TargetOf_details>>([])

const changeSelection = (item: Array<InWarehouseInsertInput_TargetOf_details>) => {
    selection.value = item
}

// 新增
const handleAdd = () => {
    rows.value.push(cloneDeep(DefaultInWarehouseDetailAddInput))
}

// 删除
const handleBatchDelete = async () => {
    const result = await deleteConfirm('这些入库明细')
    if (!result) return
    rows.value = rows.value.filter(it => !selection.value.includes(it))
}

const handleSingleDelete = async (index: number) => {
    const result = await deleteConfirm('该入库明细')
    if (!result) return
    rows.value = rows.value.filter((_, i) => i !== index)
}
        """.trimIndent()
        )
    ),
    template = listOf(
        form(
            model = formData,
            ref = formRef,
            rules = "rules",
            content = listOf(
                table(
                    data = formData,
                    columns =
                    listOf(
                        tableColumn(
                            label = "ID",
                            prop = idPropertyName,
                            fixed = ElementPlus.TableColumnFixed.LEFT,
                        ).merge {
                            directives += VIf(idColumn)
                        },
                        tableColumn(
                            type = "index",
                            fixed = ElementPlus.TableColumnFixed.LEFT,
                        ).merge {
                            directives += VIf(indexColumn)
                        },
                        tableColumn(
                            type = "selection",
                            fixed = ElementPlus.TableColumnFixed.LEFT,
                        ).merge {
                            directives += VIf(selectionColumn)
                        },
                    ) + content.map { (property, elements) ->
                        tableColumn(
                            property.name,
                            property.comment,
                            content = listOf(
                                formItem(
                                    property.name,
                                    property.comment,
                                    "rules.${property.name}",
                                    content = elements
                                )
                            )
                        )
                    } + listOf(
                        tableColumn(
                            label = "操作",
                            fixed = ElementPlus.TableColumnFixed.RIGHT,
                            content = listOf(
                                slotTemplate(
                                    name = "default",
                                    props = listOf("${'$'}index"),
                                    content = listOf(
                                        button(
                                            icon = "Delete",
                                            type = ElementPlus.Type.DANGER,
                                        ).merge {
                                            events += EventBind("click", "handleSingleDelete(${'$'}index)")
                                        }
                                    )
                                )
                            )
                        )
                    )
                ),
                emptyLineElement,
                operationsSlotElement,
            ),
        )
    )
)