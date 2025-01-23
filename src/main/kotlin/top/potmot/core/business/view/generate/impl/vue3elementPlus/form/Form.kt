package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Type.*
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.button
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Event
import top.potmot.core.business.view.generate.meta.vue3.EventArg
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.Slot
import top.potmot.core.business.view.generate.meta.vue3.SlotProp
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.slotElement
import top.potmot.utils.string.buildScopeString

private const val formExposeType = "FormExpose"

private const val formExposePath = "@/components/form/$formExposeType"

val formExposeImport = ImportType(formExposePath, formExposeType)

private const val handleValidateFnName = "handleValidate"

private const val submitLoading = "submitLoading"

private const val submitEventName = "submit"

private const val handleSubmitFnName = "handleSubmit"

private val submitEventBind = EventBind("click", handleSubmitFnName)

private const val cancelEventName = "cancel"

private const val handleCancelFnName = "handleCancel"

private val cancelEventBind = EventBind("click", handleCancelFnName)

val operationsSlot = Slot(
    "operations",
    props = listOf(
        SlotProp(handleSubmitFnName, "() => Promise<void>"),
        SlotProp(handleCancelFnName, "() => void")
    )
)

val operationsSlotElement = slotElement(
    "operations",
    props = listOf(
        PropBind(handleSubmitFnName, handleSubmitFnName),
        PropBind(handleCancelFnName, handleCancelFnName),
    ),
    content = listOf(
        TagElement(
            "div",
            props = listOf(PropBind("class", "form-operations", isLiteral = true)),
            children = listOf(
                button(content = "取消", type = WARNING).merge {
                    events += cancelEventBind
                },
                button(content = "提交", type = PRIMARY).merge {
                    props += PropBind("loading", submitLoading)
                    events += submitEventBind
                },
            ),
        ),
    )
)

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


fun handleValidate(
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

fun exposeValid(indent: String) = CodeBlock(
    buildScopeString(indent) {
        line("defineExpose<$formExposeType>({")
        scope {
            line("validate: $handleValidateFnName")
        }
        append("})")
    }
)


val submitEvent = { formData: String, formDataType: String ->
    Event(
        event = submitEventName,
        args = listOf(
            EventArg(formData, formDataType),
        )
    )
}

val submitLoadingProp = Prop(submitLoading, "boolean", false, "false")

fun handleSubmit(
    formData: String,
    indent: String,
    emitSubmit: String = "emits(\"submit\", $formData.value)",
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

val cancelEvent = Event(
    event = cancelEventName,
    args = emptyList()
)

val handleCancel = Function(
    name = handleCancelFnName,
    body = listOf(
        CodeBlock("emits(\"cancel\")")
    )
)