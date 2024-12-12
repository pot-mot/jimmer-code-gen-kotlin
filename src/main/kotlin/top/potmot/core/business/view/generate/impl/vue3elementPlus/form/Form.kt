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
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.emptyLineElement
import top.potmot.core.business.view.generate.meta.vue3.slotElement
import top.potmot.core.business.view.generate.meta.vue3.styleProp
import top.potmot.core.business.view.generate.staticPath
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.utils.string.buildScopeString

private const val formExposeType = "FormExpose"

private const val formExposePath = "$componentPath/form/$formExposeType"

data class SubValidateItem(
    val componentName: String,
    val ref: String = componentName.replaceFirstChar { it.lowercase() } + "Ref",
    val type: String = formExposeType,
    val typePath: String = formExposePath,
    val validateVariable: String = componentName.replaceFirstChar { it.lowercase() } + "Valid",
) {
    fun toRef() =
        ConstVariable(ref, null, "ref<$type>()")

    fun toImport() =
        ImportType(typePath, type)
}

private const val handleValidateFnName = "handleValidate"

private fun handleValidate(
    formRef: String,
    subValidateItems: Collection<SubValidateItem>,
    indent: String,
    afterValidCodes: String? = null,
) = Function(
    async = true,
    name = handleValidateFnName,
    returnType = "boolean",
    body =
    if (subValidateItems.isEmpty() && afterValidCodes.isNullOrBlank())
        listOf(
            CodeBlock(
                "return await $formRef.value?.validate().catch(() => false) ?? false"
            )
        )
    else listOfNotNull(
        ConstVariable("formValid", "boolean | undefined", "await $formRef.value?.validate().catch(() => false)"),
        *subValidateItems.map {
            ConstVariable(
                it.validateVariable,
                "boolean | undefined",
                "await ${it.ref}.value?.validate().catch(() => false)"
            )
        }.toTypedArray(),
        CodeBlock(
            buildScopeString(indent) {
                line()
                line("if (${(listOf("formValid") + subValidateItems.map { it.validateVariable }).joinToString(" && ")}) {")
                scope {
                    afterValidCodes?.let {
                        block(it)
                    }
                    line("return true")
                }
                line("} else {")
                scope {
                    line("return false")
                }
                append("}")
            }
        )
    )
)

private fun exposeValid(indent: String) = CodeBlock(
    buildScopeString(indent) {
        line("defineExpose<$formExposeType>({")
        scope {
            line("validate: $handleValidateFnName")
        }
        append("})")
    }
)

private const val submitEventName = "submit"
private const val handleSubmitFnName = "handleSubmit"

data class SelectOption(
    val name: String,
    val type: String,
    val apiServiceName: String,
    val typePath: String = staticPath,
) {
    val upperName: String = name.replaceFirstChar { it.uppercaseChar() }

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
    indent: String,
    emitSubmit: String = "emits(\"submit\", $formData.value)"
) = Function(
    async = true,
    name = handleSubmitFnName,
    body = listOfNotNull(
        CodeBlock(
            buildScopeString(indent) {
                line("if (props.$submitLoading) return")
                line()
                line("const validResult = await $handleValidateFnName()")
                line("if (validResult) {")
                scope {
                    line(emitSubmit)
                }
                append("}")
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
    dataType: String,
    dataTypePath: String,
    createDefault: String,
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
        ImportType(formExposePath, formExposeType),
        ImportType(submitTypePath, submitType),
        ImportType(dataTypePath, dataType),
        Import(defaultPath, createDefault),
        Import(useRulesPath, useRules),
    )
            + content.values.flatMap { it.imports }
            + subValidateItems.map { it.toImport() }
            + selectOptions.map { it.toImport() },
    props = listOf(
        Prop("withOperations", "boolean", required = false, defaultValue = "true"),
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
        ConstVariable(formData, null, "ref<$dataType>($createDefault())"),
        emptyLineCode,
        ConstVariable(formRef, null, "ref<FormInstance>()"),
        ConstVariable("rules", null, "$useRules($formData)"),
        emptyLineCode,
        *subValidateItems.map { it.toRef() }.toTypedArray(),
        if (subValidateItems.isNotEmpty()) emptyLineCode else null,
        commentLine("校验"),
        handleValidate(formRef, subValidateItems, indent, afterValidCodes),
        emptyLineCode,
        commentLine("提交"),
        handleSubmit(formData, indent, "emits(\"submit\", $formData.value as $submitType)"),
        emptyLineCode,
        commentLine("取消"),
        handleCancel(),
        emptyLineCode,
        exposeValid(indent)
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
                operationsSlotElement.merge {
                    directives += VIf("withOperations")
                },
            )
        ).merge {
            props += PropBind("@submit.prevent", isLiteral = true)
        }
    )
)

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
        ImportType(formExposePath, formExposeType),
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
        Prop("withOperations", "boolean", required = false, defaultValue = "true"),
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
        commentLine("校验"),
        handleValidate(formRef, subValidateItems, indent, afterValidCodes),
        emptyLineCode,
        commentLine("提交"),
        handleSubmit(formData, indent),
        emptyLineCode,
        commentLine("取消"),
        handleCancel(),
        emptyLineCode,
        exposeValid(indent)
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
                operationsSlotElement.merge {
                    directives += VIf("withOperations")
                },
            ),
        ).merge {
            props += PropBind("@submit.prevent", isLiteral = true)
        }
    )
)

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
    content: Map<GenEntityBusinessView.TargetOf_properties, FormItemData>,
) = Component(
    imports = listOf(
        Import("vue", "ref"),
        ImportType("element-plus", "FormInstance"),
        ImportType(formExposePath, formExposeType),
        ImportType(typePath, type),
        Import(defaultPath, createDefault),
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
        *subValidateItems.map { it.toRef() }.toTypedArray(),
        if (subValidateItems.isNotEmpty()) emptyLineCode else null,
        commentLine("校验"),
        handleValidate(formRef, subValidateItems, indent, afterValidCodes),
        emptyLineCode,
        commentLine("提交"),
        handleSubmit(formData, indent),
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