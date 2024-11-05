package top.potmot.core.business.view.generate.builder.vue3

sealed class ImportItem(
    open val path: String
)

data class CommonImport(
    override val path: String,
    val items: List<String>,
) : ImportItem(path)

data class TypeOnlyImport(
    override val path: String,
    val items: List<String>,
) : ImportItem(path)

data class DefaultImport(
    override val path: String,
    val item: String,
) : ImportItem(path)

data class Vue3Prop(
    val name: String,
    val type: String,
    val required: Boolean = true,
    val defaultValue: String? = null,
)

data class Vue3ModelProp(
    val name: String,
    val type: String,
    val required: Boolean = true,
)

data class Vue3EmitArg(
    val name: String,
    val type: String,
)

data class Vue3Emit(
    val event: String,
    val args: List<Vue3EmitArg>,
)

data class Vue3SlotProp(
    val name: String,
    val type: String,
)

data class Vue3Slot(
    val name: String,
    val props: List<Vue3SlotProp>,
)

data class Vue3PropBind(
    val name: String,
    val value: String? = null,
    val isLiteral: Boolean = false,
)

fun String?.toPropBind(
    name: String,
    isLiteral: Boolean = false
) = if (this != null) Vue3PropBind(name, this, isLiteral) else null

fun Int?.toPropBind(
    name: String,
) = if (this != null) Vue3PropBind(name, this.toString()) else null

fun Double?.toPropBind(
    name: String,
) = if (this != null) Vue3PropBind(name, this.toString()) else null

fun Boolean.toPropBind(
    name: String,
    default: Boolean = false,
) =
    if (!default) {
        if (this)
            Vue3PropBind(name, isLiteral = true)
        else
            null
    } else {
        if (!this)
            Vue3PropBind(name, value = "false")
        else
            null
    }


data class Vue3EventBind(
    val event: String,
    val fn: String,
)

sealed interface Vue3Directive

data class VModel(
    val value: String,
    val propName: String? = null,
    val modifier: List<String> = emptyList(),
): Vue3Directive

data class VIf(
    val expression: String
): Vue3Directive

data class VShow(
    val expression: String
): Vue3Directive

data class VFor(
    val item: String,
    val list: String,
    val withIndex: Boolean = false
): Vue3Directive

open class Vue3TemplateElementAttributes(
    open val directives: Iterable<Vue3Directive> = emptyList(),
    open val props: Iterable<Vue3PropBind> = emptyList(),
    open val events: Iterable<Vue3EventBind> = emptyList(),
    open val children: Collection<Vue3TemplateElement> = emptyList(),
)

data class Vue3TemplateElement(
    val tag: String,
    val content: String? = null,
    override val directives: Iterable<Vue3Directive> = emptyList(),
    override val props: Iterable<Vue3PropBind> = emptyList(),
    override val events: Iterable<Vue3EventBind> = emptyList(),
    override val children: Collection<Vue3TemplateElement> = emptyList(),
): Vue3TemplateElementAttributes(
    directives = directives,
    props = props,
    events = events,
    children = children,
) {
    fun merge(vararg attributes: Vue3TemplateElementAttributes) = Vue3TemplateElement(
        tag = tag,
        content = content,
        directives = directives + attributes.flatMap { it.directives },
        props = props + attributes.flatMap { it.props },
        events = events + attributes.flatMap { it.events },
        children = children + attributes.flatMap { it.children },
    )
}

sealed interface CodeItem

data class ConstVariable(
    val name: String,
    val type: String? = null,
    val value: String,
) : CodeItem

data class LetVariable(
    val name: String,
    val type: String? = null,
    val value: String,
) : CodeItem

data class TsFunctionArg(
    val name: String,
    val type: String,
    val required: Boolean = true,
    val defaultValue: String? = null,
)

data class TsFunction(
    val name: String,
    val args: Iterable<TsFunctionArg> = emptyList(),
    val returnType: String? = null,
    val body: String,
) : CodeItem

data class CommonBlock(
    val content: String
) : CodeItem

data class StyleClass(
    val selector: String,
    val properties: Map<String, String> = emptyMap()
)

data class Vue3ComponentPart(
    val importItems: Iterable<ImportItem> = emptyList(),
    val models: Iterable<Vue3ModelProp> = emptyList(),
    val props: Iterable<Vue3Prop> = emptyList(),
    val emits: Iterable<Vue3Emit> = emptyList(),
    val slots: Iterable<Vue3Slot> = emptyList(),
    val script: Iterable<CodeItem> = emptyList(),
    val template: Iterable<Vue3TemplateElement> = emptyList(),
    val style: Iterable<StyleClass> = emptyList(),
)

fun Iterable<Vue3ComponentPart>.merge() = Vue3ComponentPart(
    importItems = flatMap { it.importItems },
    models = flatMap { it.models },
    props = flatMap { it.props },
    emits = flatMap { it.emits },
    slots = flatMap { it.slots },
    script = flatMap { it.script },
    template = flatMap { it.template },
    style = flatMap { it.style },
)