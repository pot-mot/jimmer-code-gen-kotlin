package top.potmot.core.business.view.generate.builder.vue3

import top.potmot.core.business.view.generate.builder.typescript.TypeScriptBuilder
import top.potmot.core.business.view.generate.meta.style.StyleClass
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.Element
import top.potmot.core.business.view.generate.meta.vue3.Event
import top.potmot.core.business.view.generate.meta.vue3.ExpressionElement
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.Slot
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.TextElement
import top.potmot.core.business.view.generate.meta.vue3.VElse
import top.potmot.core.business.view.generate.meta.vue3.VFor
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.VModel
import top.potmot.core.business.view.generate.meta.vue3.VShow
import top.potmot.utils.list.join
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.buildScopeString
import top.potmot.utils.string.clearBlankLine

class Vue3ComponentBuilder(
    override val indent: String = "    ",
    override val wrapThreshold: Int = 40,
) : TypeScriptBuilder {
    fun Collection<ModelProp>.stringifyModels(currentIndent: String = indent): List<String> {
        val withName = size > 1
        val withNameIndent = if (withName) currentIndent else ""

        return map {
            val withModifier = it.modifier.isNotEmpty()

            val type = if (withModifier) "${it.type}, ${it.modifier.joinToString(" | ")}" else it.type
            val variable = if (withModifier) "[${it.name}, ${it.name}Modifier]" else it.name

            val data = buildString {
                if (withName) {
                    appendLine("\n${currentIndent}\"${it.name}\",")
                }
                append("$withNameIndent{")
                appendLine("\n$withNameIndent${currentIndent}required: ${it.required}")
                if (it.defaultValue != null) {
                    appendLine(",\n$withNameIndent${currentIndent}default: ${it.defaultValue}")
                }
                append("$withNameIndent}")
                if (withName) {
                    append("\n")
                }
            }

            "const $variable = defineModel<$type>($data)"
        }
    }

    private fun Component.needPropsDeclare(): Boolean =
        refContextContent.contains("props")

    fun Iterable<Prop>.stringifyProps(currentIndent: String = indent): String {
        val withDefaults = any { it.defaultValue != null }

        val props = map { prop ->
            val required = if (prop.required) "" else "?"
            val type = if (prop.required) prop.type else "${prop.type} | undefined"
            "${prop.name}$required: $type"
        }.inlineOrWarpLines(currentWrapThreshold = wrapThreshold - "withDefaults(".length)

        val defineProps = if (withDefaults) {
            "withDefaults(defineProps<{$props}>(), {\n${
                filter { it.defaultValue != null }.joinToString(",\n") {
                    "$currentIndent${it.name}: ${it.defaultValue}"
                }
            }\n})"
        } else {
            "defineProps<{$props}>()"
        }

        return defineProps
    }

    private fun Component.needEmitsDeclare(): Boolean =
        refContextContent.contains("emits")

    fun Iterable<Event>.stringifyEmits(currentIndent: String = indent): String {
        val emits = map { emit ->
            val args = (
                    listOf("event" to "\"${emit.event}\"") +
                            emit.args.map { it.name to it.type }
                    ).map {
                    "${it.first}: ${it.second}"
                }.inlineOrWarpLines().replace("\n", "\n$currentIndent")
            "(${args}): void"
        }.inlineOrWarpLines()

        return "defineEmits<{${emits}}>()"
    }

    fun Iterable<Slot>.stringifySlots(): String {
        val slots = map { slot ->
            var props = slot.props.map { prop ->
                "${prop.name}: ${prop.type}"
            }.inlineOrWarpLines()

            if (props.length > wrapThreshold) {
                props = props.replace("\n", "\n$indent")
            }

            "${slot.name}(props: {${props}}): any"
        }.inlineOrWarpLines()

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
                                directives +=
                                    "v-model${if (directive.propName == null) "" else ":${directive.propName}"}${
                                        if (directive.modifier.isEmpty()) "" else "." +
                                                directive.modifier.joinToString(".")
                                    }=\"${directive.value}\""
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
                                val item = if (directive.withIndex) "(${directive.item}, index)" else directive.item
                                directives += "v-for=\"$item in ${directive.list}\""
                            }
                        }
                    }

                    if (vIfDirections.isNotEmpty()) {
                        val vIfExpressions = vIfDirections.map { it.expression }
                        firstVIfIndex?.let { index ->
                            val vIf = "v-${if (vIfDirections.any { it.isElse }) "else-if" else "if"}=\"" +
                                    vIfExpressions.inlineOrWarpLines(" &&") +
                                    "\""
                            directives.add(index, vIf)
                        }
                    }
                    if (vShowDirections.isNotEmpty()) {
                        val vShowExpressions = vIfDirections.map { it.expression }
                        firstVShowIndex?.let { index ->
                            val vShow = "v-show=\"" +
                                    vShowExpressions.inlineOrWarpLines(" &&") +
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
            buildScopeString {
                append("${styleClass.selector} {")
                scope {
                    styleClass.properties.entries.forEach { (key, value) ->
                        line("$key: $value;")
                    }
                }
                append("}")
            }
        }

    fun build(vueComponentPart: Component) = buildString {
        appendLine("<script setup lang=\"ts\">")

        val scriptParts = mutableListOf<String>()

        scriptParts += vueComponentPart.imports.stringifyImports().joinToString("\n")

        scriptParts += vueComponentPart.models.stringifyModels()

        if (vueComponentPart.props.isNotEmpty()) {
            val stringifyProps = vueComponentPart.props.stringifyProps()
            scriptParts +=
                if (stringifyProps.isNotEmpty() && vueComponentPart.needPropsDeclare()) {
                    "const props = $stringifyProps"
                } else {
                    stringifyProps
                }
        }

        if (vueComponentPart.emits.isNotEmpty()) {
            val stringifyEmits = vueComponentPart.emits.stringifyEmits()
            scriptParts +=
                if (stringifyEmits.isNotEmpty() && vueComponentPart.needEmitsDeclare()) {
                    "const emits = $stringifyEmits"
                } else {
                    stringifyEmits
                }
        }

        if (vueComponentPart.slots.isNotEmpty()) {
            scriptParts += vueComponentPart.slots.stringifySlots()
        }

        if (vueComponentPart.script.isNotEmpty()) {
            scriptParts += vueComponentPart.script.stringifyCodes()
        }

        for (scriptPart in scriptParts.join("")) {
            appendBlock(scriptPart)
        }

        appendLine("</script>")
        appendLine()
        appendLine("<template>")

        val stringifyElements = vueComponentPart.template.stringifyElements(indent)
        appendBlock(stringifyElements)

        appendLine("</template>")

        if (vueComponentPart.style.isNotEmpty()) {
            appendLine()
            appendLine("<style scoped>")

            val stringifyClasses = vueComponentPart.style.stringifyStyleClass()
            appendLine(stringifyClasses)

            appendLine("</style>")
        }
    }
}