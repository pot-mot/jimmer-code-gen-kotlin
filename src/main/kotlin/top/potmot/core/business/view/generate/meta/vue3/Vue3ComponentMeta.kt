package top.potmot.core.business.view.generate.meta.vue3

import top.potmot.core.business.view.generate.meta.style.StyleClass
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.CodeItem
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.ImportItem
import top.potmot.core.business.view.generate.meta.typescript.LetVariable

data class Prop(
    val name: String,
    val type: String,
    val required: Boolean = true,
    val defaultValue: String? = null,
)

data class ModelProp(
    val name: String,
    val type: String,
    val required: Boolean = true,
    val defaultValue: String? = null,
    val modifier: Collection<String> = emptyList(),
)

data class EventArg(
    val name: String,
    val type: String,
)

data class Event(
    val event: String,
    val args: Iterable<EventArg> = emptyList(),
)

data class SlotProp(
    val name: String,
    val type: String,
)

data class Slot(
    val name: String,
    val props: Iterable<SlotProp>,
)

data class PropBind(
    val name: String,
    val value: String? = null,
    val isLiteral: Boolean = false,
)

fun String?.toPropBind(
    name: String,
    isLiteral: Boolean = false
) = if (this != null) PropBind(name, this, isLiteral) else null

fun Int?.toPropBind(
    name: String,
    format: Int.() -> String = { toString() }
) = if (this != null) PropBind(name, this.format()) else null

fun Long?.toPropBind(
    name: String,
    format: Long.() -> String = { toString() }
) = if (this != null) PropBind(name, this.format()) else null

fun Double?.toPropBind(
    name: String,
    format: Double.() -> String = { toString() }
) = if (this != null) PropBind(name, this.format()) else null

fun classProp(classNames: Iterable<String>) = PropBind(
    "class", classNames.joinToString(" "), isLiteral = true
)

fun classProp(vararg className: String) =
    classProp(className.toList())

fun styleProp(style: Map<String, String>) = PropBind(
    "style", style.map { (k, v) -> "$k: $v;" }.joinToString(""), isLiteral = true
)

fun styleProp(vararg style: Pair<String, String>) =
    styleProp(mapOf(*style))

fun Boolean.toPropBind(
    name: String,
    default: Boolean = false,
) =
    if (!default) {
        if (this)
            PropBind(name, isLiteral = true)
        else
            null
    } else {
        if (!this)
            PropBind(name, value = "false")
        else
            null
    }

data class EventBind(
    val event: String,
    val fn: String,
)

sealed interface Directive

data class VModel(
    val value: String,
    val propName: String? = null,
    val modifier: Collection<String> = emptyList(),
) : Directive

data class VIf(
    val expression: String,
    val isElse: Boolean = false
) : Directive

data object VElse : Directive

data class VShow(
    val expression: String
) : Directive

data class VFor(
    val item: String,
    val list: String,
    val withIndex: Boolean = false
) : Directive

open class TagElementAttributes(
    open val directives: Iterable<Directive> = emptyList(),
    open val props: Iterable<PropBind> = emptyList(),
    open val events: Iterable<EventBind> = emptyList(),
    open val children: Collection<Element> = emptyList(),
) {
    class Builder(
        var directives: MutableList<Directive> = mutableListOf(),
        var props: MutableList<PropBind> = mutableListOf(),
        var events: MutableList<EventBind> = mutableListOf(),
        var children: MutableList<Element> = mutableListOf()
    ) {
        fun build() = TagElementAttributes(
            directives = directives,
            props = props,
            events = events,
            children = children
        )
    }
}

sealed interface Element

data class TextElement(
    val text: String,
) : Element

val emptyLineElement = TextElement("")

data class ExpressionElement(
    val expression: String,
) : Element

data class TagElement(
    val tag: String,
    override val directives: Iterable<Directive> = emptyList(),
    override val props: Iterable<PropBind> = emptyList(),
    override val events: Iterable<EventBind> = emptyList(),
    override val children: Collection<Element> = emptyList(),
) : TagElementAttributes(
    directives = directives,
    props = props,
    events = events,
    children = children,
), Element {
    fun merge(block: Builder.() -> Unit): TagElement {
        val builder =
            Builder(directives.toMutableList(), props.toMutableList(), events.toMutableList(), children.toMutableList())
        builder.block()
        return TagElement(
            tag = tag,
            directives = builder.directives,
            props = builder.props,
            events = builder.events,
            children = builder.children
        )
    }
}

fun slotElement(
    name: String,
    props: Iterable<PropBind> = emptyList(),
    content: Collection<Element> = emptyList(),
) = TagElement(
    "slot",
    props = listOf(PropBind("name", name, isLiteral = true)) + props,
    children = content
)

fun slotTemplate(
    name: String = "default",
    propScope: String? = null,
    props: Collection<String>? = null,
    content: Collection<Element> = emptyList(),
) = TagElement(
    "template",
    props = listOf(
        PropBind(
            "#$name",
            value =
            if (props.isNullOrEmpty()) {
                if (propScope.isNullOrBlank()) null else propScope
            } else {
                "{${props.joinToString { "," }}}"
            },
            isLiteral = true
        ),
    ),
    children = content
)

private fun CodeItem.getRefContextContent(): String =
    when (this) {
        is CodeBlock -> content
        is Function -> body.joinToString("\n") {
            it.getRefContextContent()
        }

        is ConstVariable -> value
        is LetVariable -> value
    }

private fun Directive.getRefContextContent(): String =
    when (this) {
        is VIf -> expression
        is VShow -> expression
        else -> ""
    }

private fun Element.getRefContextContent(): String =
    when (this) {
        is ExpressionElement -> expression
        is TagElement ->
            props.mapNotNull { it.value }.joinToString() +
                    events.joinToString { it.fn } +
                    directives.joinToString { it.getRefContextContent() } +
                    children.joinToString { it.getRefContextContent() }
        else -> ""
    }

data class Component(
    val imports: Collection<ImportItem> = emptyList(),
    val models: Collection<ModelProp> = emptyList(),
    val props: Collection<Prop> = emptyList(),
    val emits: Collection<Event> = emptyList(),
    val slots: Collection<Slot> = emptyList(),
    val script: Collection<CodeItem> = emptyList(),
    val template: Iterable<Element> = emptyList(),
    val style: Collection<StyleClass> = emptyList(),

    val refContextContent: String =
        script.joinToString { it.getRefContextContent() } +
                template.joinToString { it.getRefContextContent() }
) {
    class Builder(
        var imports: MutableList<ImportItem> = mutableListOf(),
        var models: MutableList<ModelProp> = mutableListOf(),
        var props: MutableList<Prop> = mutableListOf(),
        var emits: MutableList<Event> = mutableListOf(),
        var slots: MutableList<Slot> = mutableListOf(),
        var script: MutableList<CodeItem> = mutableListOf(),
        var template: MutableList<Element> = mutableListOf(),
        var style: MutableList<StyleClass> = mutableListOf(),
    ) {
        fun build() = Component(
            imports = imports,
            models = models,
            props = props,
            emits = emits,
            slots = slots,
            script = script,
            template = template,
            style = style
        )
    }

    fun merge(block: Builder.() -> Unit): Component {
        val builder = Builder(
            imports.toMutableList(),
            models.toMutableList(),
            props.toMutableList(),
            emits.toMutableList(),
            slots.toMutableList(),
            script.toMutableList(),
            template.toMutableList(),
            style.toMutableList()
        )

        builder.block()

        return builder.build()
    }
}