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
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines
import top.potmot.utils.string.trimBlankLine

class Vue3ComponentBuilder(
    override val indent: String = "    ",
    override val wrapThreshold: Int = 40,
) : TypeScriptBuilder {
    fun Iterable<ModelProp>.stringifyModels() =
        map {
            """
const ${it.name} = defineModels<${it.type}>({required: ${it.required}})
            """.trimBlankLine()
        }

    private fun Component.needPropsDeclare(): Boolean =
        copy(props = emptyList()).toString().replace("props=[]", "").contains("props")

    fun Iterable<Prop>.stringifyProps(currentIndent: String = indent): String {
        val props = map { prop ->
            val required = if (prop.required) "" else "?"
            val type = if (prop.required) prop.type else "${prop.type} | undefined"
            "${prop.name}$required: $type"
        }.inlineOrWarpLines()

        val defineProps = if (any { it.defaultValue != null }) {
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
        copy(emits = emptyList()).toString().replace("emits=[]", "").contains("emits")

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
                is TextElement -> {
                    if (element.text.isBlank()) "" else "$currentIndent${element.text}"
                }

                is ExpressionElement -> {
                    "$currentIndent{{ ${element.expression} }}"
                }

                is TagElement -> {
                    val directives = element.directives.map { directive ->
                        when (directive) {
                            is VModel -> {
                                "v-model${if (directive.propName == null) "" else ":${directive.propName}"}${
                                    if (directive.modifier.isEmpty()) "" else "." + directive.modifier.joinToString(
                                        "."
                                    )
                                }=\"${directive.value}\""
                            }

                            is VIf -> "v-${if (directive.isElse) "else-if" else "if"}=\"${directive.expression}\""
                            is VElse -> "v-else"
                            is VShow -> "v-show=\"${directive.expression}\""
                            is VFor -> {
                                val item = if (directive.withIndex) "(${directive.item}, index)" else directive.item
                                "v-for=\"$item in ${directive.list}\""
                            }
                        }
                    }

                    val props = element.props.map { prop ->
                        if (prop.value == null) prop.name else "${if (prop.isLiteral) "" else ":"}${prop.name}=\"${prop.value}\""
                    }

                    val events = element.events.map { event ->
                        "@${event.event}=\"${event.fn}\""
                    }

                    val attributes = listOf(
                        directives,
                        props,
                        events
                    ).flatten()
                        .inlineOrWarpLines("")
                        .let {
                            if (it.isNotBlank()) {
                                val lines = it.split("\n")
                                if (lines.size > 1) {
                                    lines.joinToString("\n$currentIndent") { line -> line }
                                } else {
                                    " $it"
                                }
                            } else ""
                        }

                    val children = element.children.toList()

                    val tagStart = "<${element.tag}$attributes"
                    val tagEnd = "</${element.tag}>"

                    if (children.isEmpty()) {
                        "$currentIndent$tagStart/>"
                    } else {
                        if (children.size == 1) {
                            val child = children[0]
                            if (child is TextElement) {
                                val result =
                                    if (child.text.isBlank()) "$tagStart/>" else "$tagStart>${child.text}$tagEnd"
                                if (result.length < wrapThreshold) {
                                    return@joinToString "$currentIndent$result"
                                }
                            } else if (child is ExpressionElement) {
                                val result = "$tagStart>{{ ${child.expression} }}$tagEnd"
                                if (result.length < wrapThreshold) {
                                    return@joinToString "$currentIndent$result"
                                }
                            }
                        }

                        val childrenStr = children.stringifyElements(currentIndent + indent)
                        buildString {
                            appendLine("$currentIndent$tagStart>")
                            appendLine(childrenStr)
                            append("$currentIndent$tagEnd")
                        }
                    }
                }
            }
        }

    fun Iterable<StyleClass>.stringifyStyleClass(): String =
        joinToString("\n\n") { styleClass ->
            val properties = styleClass.properties.entries.map { (key, value) ->
                "$key: $value;"
            }.wrapLines("")
            "${styleClass.selector} {$properties}"
        }

    fun build(vueComponentPart: Component) = buildString {
        appendLine("<script setup lang=\"ts\">")

        val stringifyImports = vueComponentPart.imports.stringifyImports()
        appendLines(stringifyImports)
        if (stringifyImports.isNotEmpty()) appendLine()

        val stringifyModels = vueComponentPart.models.stringifyModels()
        appendLines(stringifyModels)
        if (stringifyModels.isNotEmpty()) appendLine()

        if (vueComponentPart.props.isNotEmpty()) {
            val stringifyProps = vueComponentPart.props.stringifyProps()
            if (vueComponentPart.needPropsDeclare()) append("const props = ")
            appendBlock(stringifyProps)
            if (stringifyProps.isNotEmpty()) appendLine()
        }

        if (vueComponentPart.emits.isNotEmpty()) {
            val stringifyEmits = vueComponentPart.emits.stringifyEmits()
            if (vueComponentPart.needEmitsDeclare()) append("const emits = ")
            appendBlock(stringifyEmits)
            if (stringifyEmits.isNotEmpty()) appendLine()
        }

        if (vueComponentPart.slots.isNotEmpty()) {
            val stringifySlots = vueComponentPart.slots.stringifySlots()
            appendBlock(stringifySlots)
            if (stringifySlots.isNotEmpty()) appendLine()
        }

        if (vueComponentPart.script.isNotEmpty()) {
            val stringifyCodes = vueComponentPart.script.stringifyCodes()
            appendBlock(stringifyCodes)
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