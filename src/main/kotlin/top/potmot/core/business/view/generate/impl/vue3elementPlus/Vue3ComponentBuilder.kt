package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.view.generate.builder.component.ComponentBuilder
import top.potmot.core.common.style.StyleClass
import top.potmot.core.common.typescript.TsProperty
import top.potmot.core.common.typescript.TsObject
import top.potmot.core.common.typescript.inlineOrWarpLines
import top.potmot.core.common.typescript.stringify
import top.potmot.core.common.vue3.Component
import top.potmot.core.common.vue3.Element
import top.potmot.core.common.vue3.Event
import top.potmot.core.common.vue3.ExpressionElement
import top.potmot.core.common.vue3.ModelProp
import top.potmot.core.common.vue3.Prop
import top.potmot.core.common.vue3.Slot
import top.potmot.core.common.vue3.TagElement
import top.potmot.core.common.vue3.TextElement
import top.potmot.core.common.vue3.VElse
import top.potmot.core.common.vue3.VFor
import top.potmot.core.common.vue3.VIf
import top.potmot.core.common.vue3.VModel
import top.potmot.core.common.vue3.VShow
import top.potmot.utils.collection.join
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.buildScopeString
import top.potmot.utils.string.clearBlankLine

class Vue3ComponentBuilder(
    val indent: String,
    val wrapThreshold: Int,
) : ComponentBuilder {
    fun Collection<ModelProp>.stringifyModels(): List<String> {
        val withName = size > 1

        return map {
            val withModifier = it.modifier.isNotEmpty()

            val type = if (withModifier) "${it.type}, ${it.modifier.joinToString(" | ")}" else it.type
            val variable = if (withModifier) "[${it.name}, ${it.name}Modifier]" else it.name

            val defineObject = TsObject(
                properties = listOfNotNull(
                    TsProperty("required", it.required.toString()),
                    it.default?.let { value -> TsProperty("default", value) }
                )
            )

            val data = buildScopeString(indent) {
                if (withName) {
                    line()
                    scope {
                        line("\"${it.name}\",")
                        defineObject.stringify(this)
                    }
                    line()
                } else {
                    defineObject.stringify(this)
                }
            }

            "const $variable = defineModel<$type>($data)"
        }
    }

    private fun Component.needPropsDeclare(): Boolean =
        refContextContent.contains("props")

    fun Iterable<Prop>.stringifyProps(): String = buildScopeString(indent) {
        val hasDefaultProps = filter { it.default != null }
        val withDefaults = hasDefaultProps.isNotEmpty()

        if (withDefaults) {
            append("withDefaults(defineProps<{")
        } else {
            append("defineProps<{")
        }

        val props = map { prop ->
            val required = if (prop.required) "" else "?"
            val type = if (prop.required) prop.type else "${prop.type} | undefined"
            "${prop.name}$required: $type"
        }.inlineOrWarpLines(indent, wrapThreshold - "withDefaults(".length)

        append(props)

        if (withDefaults) {
            line("}>(), {")
            scope {
                hasDefaultProps.forEachIndexed { index, it ->
                    if (it.defaultAsFunction) {
                        line("${it.name}() {")
                        scope {
                            block(it.default)
                        }
                        line("}${if (index != hasDefaultProps.size - 1) "," else ""}")
                    } else {
                        line("${it.name}: ${it.default},")
                    }
                }
            }
            append("})")
        } else {
            append("}>()")
        }
    }

    private fun Component.needEmitsDeclare(): Boolean =
        refContextContent.contains("emits")

    fun Iterable<Event>.stringifyEmits(): String {
        val emits = map { emit ->
            val args = (
                    listOf("event" to "\"${emit.event}\"") +
                            emit.args.map { it.name to it.type }
                    )
                .map { "${it.first}: ${it.second}" }
                .inlineOrWarpLines(indent, wrapThreshold)
                .replace("\n", "\n$indent")
            "(${args}): void"
        }.inlineOrWarpLines(indent, wrapThreshold)

        return "defineEmits<{${emits}}>()"
    }

    fun Iterable<Slot>.stringifySlots(): String {
        val slots = map { slot ->
            var props = slot.props.map { prop ->
                "${prop.name}: ${prop.type}"
            }.inlineOrWarpLines(indent, wrapThreshold)

            if (props.length > wrapThreshold) {
                props = props.replace("\n", "\n$indent")
            }

            "${slot.name}(props: {${props}}): any"
        }.inlineOrWarpLines(indent, wrapThreshold)

        return "defineSlots<{${slots}}>()"
    }

    fun Iterable<Element>.stringifyElements(currentIndent: String = ""): String =
        joinToString("\n") { element ->
            when (element) {
                is TextElement ->
                    "$currentIndent${element.text}"

                is ExpressionElement ->
                    "$currentIndent{{ ${element.expression} }}"

                is TagElement -> buildScopeString(indent, currentIndent) {
                    append(currentIndent)
                    append("<")
                    append(element.tag)

                    var firstVIfIndex: Int? = null
                    val vIfDirections = mutableListOf<VIf>()

                    var firstVShowIndex: Int? = null
                    val vShowDirections = mutableListOf<VShow>()

                    val directives = mutableListOf<String>()

                    element.directives.forEach { directive ->
                        when (directive) {
                            is VModel -> {
                                directives += directive.stringify()
                            }

                            is VIf -> {
                                if (firstVIfIndex == null)
                                    firstVIfIndex = directives.size
                                vIfDirections += directive
                            }

                            is VElse -> {
                                directives += "v-else"
                            }

                            is VShow -> {
                                if (firstVShowIndex == null)
                                    firstVShowIndex = directives.size
                                vShowDirections += directive
                            }

                            is VFor -> {
                                directives += directive.stringify()
                            }
                        }
                    }

                    if (vIfDirections.isNotEmpty()) {
                        val vIfExpressions = vIfDirections.map { it.expression }
                        firstVIfIndex?.let { index ->
                            val vIf = "v-${if (vIfDirections.any { it.isElse }) "else-if" else "if"}=\"" +
                                    vIfExpressions.inlineOrWarpLines(indent, wrapThreshold, " &&") +
                                    "\""
                            directives.add(index, vIf)
                        }
                    }
                    if (vShowDirections.isNotEmpty()) {
                        val vShowExpressions = vIfDirections.map { it.expression }
                        firstVShowIndex?.let { index ->
                            val vShow = "v-show=\"" +
                                    vShowExpressions.inlineOrWarpLines(indent, wrapThreshold, " &&") +
                                    "\""
                            directives.add(index, vShow)
                        }
                    }

                    val props = element.props.map { prop ->
                        if (prop.value == null)
                            prop.name
                        else
                            "${if (prop.isLiteral) "" else ":"}${prop.name}=\"${prop.value}\""
                    }

                    val events = element.events.map { event ->
                        "@${event.event}=\"${event.fn}\""
                    }

                    val attributes = listOf(
                        directives,
                        props,
                        events
                    ).flatten()

                    if (attributes.isNotEmpty()) {
                        val joinAttributes = attributes.joinToString(" ")

                        if (joinAttributes.length > wrapThreshold) {
                            scope {
                                append("\n")
                                attributes.forEach {
                                    block(it)
                                }
                            }
                        } else {
                            append(" ")
                            append(joinAttributes)
                        }
                    }

                    val children = element.children.toList()

                    if (children.isEmpty()) {
                        append("/>")
                    } else {
                        val endTag = "</${element.tag}>"

                        if (children.size == 1) {
                            val child = children[0]
                            if (child is TextElement) {
                                val result =
                                    if (child.text.isBlank()) "/>" else ">${child.text}$endTag"
                                if (stringBuilder.length + result.length < wrapThreshold) {
                                    append(result)
                                    return@buildScopeString
                                }
                            } else if (child is ExpressionElement) {
                                val result = ">{{ ${child.expression} }}$endTag"
                                if (stringBuilder.length + result.length < wrapThreshold) {
                                    append(result)
                                    return@buildScopeString
                                }
                            }
                        }

                        val childrenStr = children.stringifyElements()
                        append(">\n")
                        scope {
                            block(childrenStr)
                        }
                        append(endTag)
                    }
                }
            }
        }.clearBlankLine()

    fun Iterable<StyleClass>.stringifyStyleClass(): String =
        joinToString("\n\n") { styleClass ->
            buildScopeString(indent) {
                scopeEndNoLine("${styleClass.selector} {", "}") {
                    styleClass.properties.entries.forEach { (key, value) ->
                        line("$key: $value;")
                    }
                }
            }
        }

    override fun build(component: Component) = buildString {
        appendLine("<script setup lang=\"ts\">")

        val scriptParts = mutableListOf<String>()

        scriptParts += component.imports.stringify(indent, wrapThreshold).joinToString("\n")

        scriptParts += component.models.stringifyModels()

        if (component.props.isNotEmpty()) {
            val stringifyProps = component.props.stringifyProps()
            scriptParts +=
                if (stringifyProps.isNotEmpty() && component.needPropsDeclare()) {
                    "const props = $stringifyProps"
                } else {
                    stringifyProps
                }
        }

        if (component.emits.isNotEmpty()) {
            val stringifyEmits = component.emits.stringifyEmits()
            scriptParts +=
                if (stringifyEmits.isNotEmpty() && component.needEmitsDeclare()) {
                    "const emits = $stringifyEmits"
                } else {
                    stringifyEmits
                }
        }

        if (component.slots.isNotEmpty()) {
            scriptParts += component.slots.stringifySlots()
        }

        if (component.script.isNotEmpty()) {
            scriptParts += component.script.stringify(indent, wrapThreshold)
        }

        for (scriptPart in scriptParts.join("")) {
            appendBlock(scriptPart)
        }

        appendLine("</script>")
        appendLine()
        appendLine("<template>")

        val stringifyElements = component.template.stringifyElements(indent)
        appendBlock(stringifyElements)

        appendLine("</template>")

        if (component.style.isNotEmpty()) {
            appendLine()
            appendLine("<style scoped>")

            val stringifyClasses = component.style.stringifyStyleClass()
            appendLine(stringifyClasses)

            appendLine("</style>")
        }
    }
}