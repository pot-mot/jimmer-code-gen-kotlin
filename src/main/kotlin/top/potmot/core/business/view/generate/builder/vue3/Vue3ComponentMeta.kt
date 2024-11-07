package top.potmot.core.business.view.generate.builder.vue3

import top.potmot.core.business.view.generate.builder.style.StyleClass
import top.potmot.core.business.view.generate.builder.typescript.CodeItem

sealed class ImportItem(
    open val path: String
)

data class Import(
    override val path: String,
    val items: List<String>,
) : ImportItem(path)

data class ImportType(
    override val path: String,
    val items: List<String>,
) : ImportItem(path)

data class ImportDefault(
    override val path: String,
    val item: String,
) : ImportItem(path)

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

open class ElementAttributes(
    open val directives: Iterable<Directive> = emptyList(),
    open val props: Iterable<PropBind> = emptyList(),
    open val events: Iterable<EventBind> = emptyList(),
    open val children: Collection<Element> = emptyList(),
) {
    // 创建一个 Builder 类
    class Builder(
        var directives: MutableList<Directive> = mutableListOf(),
        var props: MutableList<PropBind> = mutableListOf(),
        var events: MutableList<EventBind> = mutableListOf(),
        var children: MutableList<Element> = mutableListOf()
    ) {
        // 构建最终的 Element 对象
        fun build() = ElementAttributes(
            directives = directives,
            props = props,
            events = events,
            children = children
        )
    }
}

data class Element(
    val tag: String,
    val content: String? = null,
    override val directives: Iterable<Directive> = emptyList(),
    override val props: Iterable<PropBind> = emptyList(),
    override val events: Iterable<EventBind> = emptyList(),
    override val children: Collection<Element> = emptyList(),
) : ElementAttributes(
    directives = directives,
    props = props,
    events = events,
    children = children,
) {

    fun merge(block: Builder.() -> Unit): Element {
        val builder = Builder(directives.toMutableList(), props.toMutableList(), events.toMutableList(), children.toMutableList())
        builder.block()
        return Element(
            tag = tag,
            content = content,
            directives = builder.directives,
            props = builder.props,
            events = builder.events,
            children = builder.children
        )
    }
}

fun slotTemplate(
    name: String = "default",
    propScope: String? = null,
    props: Collection<String>? = null,
    content: Collection<Element> = emptyList(),
) = Element(
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

data class Component(
    val imports: Iterable<ImportItem> = emptyList(),
    val models: Iterable<ModelProp> = emptyList(),
    val props: Iterable<Prop> = emptyList(),
    val emits: Iterable<Event> = emptyList(),
    val slots: Iterable<Slot> = emptyList(),
    val script: Iterable<CodeItem> = emptyList(),
    val template: Iterable<Element> = emptyList(),
    val style: Iterable<StyleClass> = emptyList(),
)

fun Iterable<Component>.merge() = Component(
    imports = flatMap { it.imports },
    models = flatMap { it.models },
    props = flatMap { it.props },
    emits = flatMap { it.emits },
    slots = flatMap { it.slots },
    script = flatMap { it.script },
    template = flatMap { it.template },
    style = flatMap { it.style },
)