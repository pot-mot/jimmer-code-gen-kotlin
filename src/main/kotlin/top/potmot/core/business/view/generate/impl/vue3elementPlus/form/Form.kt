package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Type.PRIMARY
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Type.WARNING
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.TsImport
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

interface ValidateItem {
    val imports: List<TsImport>

    val name: String

    val type: String

    val expression: String

    fun toConst() = ConstVariable(
        name = name,
        type = type,
        value = expression
    )
}

data class CommonValidateItem(
    override val name: String,
    override val type: String,
    override val expression: String,
    override val imports: List<TsImport> = emptyList()
): ValidateItem

data class FormRefValidateItem(
    val componentName: String,
): ValidateItem {
    private val componentLowerName = componentName.replaceFirstChar { it.lowercase() }

    private val refName = "${componentLowerName}Ref"

    val ref = ConstVariable(refName, null, "ref<$formExposeType>()")

    override val name: String = "${componentLowerName}Valid"

    override val type: String = "boolean | undefined"

    override val expression: String = "await ${refName}.value?.validate().catch(() => false)"

    override val imports = listOf(ImportType(formExposePath, formExposeType))
}


fun handleValidate(
    formRef: String,
    subValidateItems: Collection<ValidateItem>,
    indent: String,
) = Function(
    async = true,
    name = handleValidateFnName,
    returnType = "boolean",
    body =
    if (subValidateItems.isEmpty())
        listOf(
            CodeBlock(
                "return await $formRef.value?.validate().catch(() => false) ?? false"
            )
        )
    else
        listOfNotNull(
            ConstVariable("formValid", "boolean | undefined", "await $formRef.value?.validate().catch(() => false)"),
            *subValidateItems.map { it.toConst() }.toTypedArray(),
            CodeBlock(
                buildScopeString(indent) {
                    line()
                    line("if (${(listOf("formValid") + subValidateItems.map { it.name }).joinToString(" && ")}) {")
                    scope {
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