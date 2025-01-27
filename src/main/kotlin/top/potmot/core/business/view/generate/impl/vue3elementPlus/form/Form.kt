package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Type.PRIMARY
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Type.WARNING
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
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

const val formExposeType = "FormExpose"

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

fun handleValidate(
    validateItems: Collection<ValidateItem>,
    indent: String,
) = Function(
    async = true,
    name = handleValidateFnName,
    returnType = "boolean",
    body = listOf(
        CodeBlock(
            buildScopeString(indent) {
                validateItems.forEach {
                    line("const ${it.name}: boolean =")
                    scope { line(it.expression) }
                }
                line()
                line("return ${(listOf("formValid") + validateItems.map { it.name }).joinToString(" && ")}")
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
    submitData: String,
    indent: String,
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
                    line("emits(\"submit\", $submitData)")
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