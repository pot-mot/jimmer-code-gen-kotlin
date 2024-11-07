package top.potmot.core.business.view.generate.builder.vue3

import top.potmot.core.business.view.generate.builder.style.StyleClass
import top.potmot.core.business.view.generate.builder.typescript.CodeItem
import top.potmot.core.business.view.generate.builder.typescript.CodeBlock
import top.potmot.core.business.view.generate.builder.typescript.ConstVariable
import top.potmot.core.business.view.generate.builder.typescript.Function
import top.potmot.core.business.view.generate.builder.typescript.LetVariable
import top.potmot.error.GenerateException
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines
import top.potmot.utils.string.trimBlankLine

class Vue3ComponentBuilder(
    private val indent: String = "    ",
    private val wrapThreshold: Int = 20,
) {
    private fun List<String>.inlineOrWarp(separator: String = ","): String {
        val inline = joinToString("$separator ")

        return if (inline.length > wrapThreshold) {
            "\n" + joinToString("$separator\n") { "$indent$it" } + "\n"
        } else {
            inline
        }
    }

    fun Iterable<ImportItem>.stringifyImports(): List<String> =
        groupBy { it.path }.map { (path, imports) ->
            val commonImports = mutableListOf<String>()
            val typeOnlyImports = mutableListOf<String>()
            val defaultImports = mutableListOf<String>()

            imports.forEach { importItem ->
                when (importItem) {
                    is Import -> commonImports.addAll(importItem.items)
                    is ImportType -> typeOnlyImports.addAll(importItem.items)
                    is ImportDefault -> defaultImports.add(importItem.item)
                }
            }

            if (defaultImports.size > 1)
                throw GenerateException.canOnlyHaveOneDefaultImportForOnePath("path: ${path}, defaultImports: $defaultImports")

            val importStatements = mutableListOf<String>()

            if (commonImports.isNotEmpty())
                importStatements.add("import { ${commonImports.inlineOrWarp()} } from \"$path\"")

            if (typeOnlyImports.isNotEmpty())
                importStatements.add("import type { ${typeOnlyImports.inlineOrWarp()} } from \"$path\"")

            if (defaultImports.isNotEmpty())
                importStatements.add("import ${defaultImports[0]} from \"$path\"")

            importStatements
        }.flatten()

    fun Iterable<ModelProp>.stringifyModels() =
        map {
            """
const ${it.name} = defineModels<${it.type}>({required: ${it.required}})
            """.trimBlankLine()
        }


    private fun Component.needPropsDeclare(): Boolean =
        copy(props = emptyList()).toString().contains("props")

    fun Iterable<Prop>.stringifyProps(): String {
        val props = map { prop ->
            val required = if (prop.required) "" else "?"
            val type = if (prop.required) prop.type else "${prop.type} | undefined"
            "${prop.name}$required: $type"
        }.inlineOrWarp()

        val defineProps = if (any { it.defaultValue != null }) {
            "withDefaults(defineProps<{$props}>(), {\n${
                filter { it.defaultValue != null }.joinToString(",\n") {
                    "$indent${it.name}: ${it.defaultValue}"
                }
            }\n})"
        } else {
            "defineProps<{$props}>()"
        }

        return defineProps
    }

    private fun Component.needEmitsDeclare(): Boolean =
        copy(props = emptyList()).toString().contains("emits")

    fun Iterable<Event>.stringifyEmits(): String {
        val emits = map { emit ->
            val args = emit.args.map { arg ->
                "${arg.name}: ${arg.type}"
            }.inlineOrWarp()
            "(event: \"${emit.event}\", ${args}): void"
        }.inlineOrWarp()

        return "defineEmits<{${emits}}>()"
    }

    fun Iterable<Slot>.stringifySlots(): String {
        val slots = map { slot ->
            val props = slot.props.map { prop ->
                "${prop.name}: ${prop.type}"
            }.inlineOrWarp()
            "${slot.name}(props: {${props}}): any"
        }.inlineOrWarp()

        return "defineSlots<{${slots}}>()"
    }

    fun Iterable<CodeItem>.stringifyCodes(): String =
        joinToString("\n") { item ->
            when (item) {
                is ConstVariable ->
                    "const ${item.name}${if (item.type != null) ": ${item.type}" else ""} = ${item.value}"

                is LetVariable ->
                    "let ${item.name}${if (item.type != null) ": ${item.type}" else ""} = ${item.value}"

                is Function -> {
                    val args = item.args.map { arg ->
                        "${arg.name}${if (arg.required) "" else "?"}: ${arg.type}${if (arg.defaultValue != null) " = ${arg.defaultValue}" else ""}"
                    }.inlineOrWarp()
                    val body = item.body.stringifyCodes().replace("\n", "\n$indent")
                    "const ${item.name} = ${if (item.async) "async " else ""}(${args}): ${
                        if (item.returnType != null) {
                            if (item.async) "Promise<${item.returnType}>" else item.returnType
                        } else "void"
                    } => {\n$indent$body\n}"
                }

                is CodeBlock ->
                    item.content
            }
        }

    fun Iterable<Element>.stringifyElements(currentIndent: String = ""): String =
        joinToString("\n") { element ->
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
                .inlineOrWarp("")
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

            val children = element.children

            val tagStart = "$currentIndent<${element.tag}$attributes"

            if (children.isEmpty()) {
                if (element.content.isNullOrEmpty()) {
                    "$tagStart/>"
                } else {
                    "$tagStart>${element.content}</${element.tag}>"
                }
            } else {
                val childrenStr = children.stringifyElements(currentIndent + indent)
                "$tagStart>\n$childrenStr\n$currentIndent</${element.tag}>"
            }
        }

    fun Iterable<StyleClass>.stringifyStyleClass(): String =
        joinToString("\n\n") { styleClass ->
            val properties = styleClass.properties.entries.map { (key, value) ->
                "$key: $value;"
            }.inlineOrWarp("")
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

        val stringifyProps = vueComponentPart.props.stringifyProps()
        if (vueComponentPart.needPropsDeclare()) append("const props = ")
        appendBlock(stringifyProps)
        if (stringifyProps.isNotEmpty()) appendLine()

        val stringifyEmits = vueComponentPart.emits.stringifyEmits()
        if (vueComponentPart.needEmitsDeclare()) append("const emits = ")
        appendBlock(stringifyEmits)
        if (stringifyEmits.isNotEmpty()) appendLine()

        val stringifySlots = vueComponentPart.slots.stringifySlots()
        appendBlock(stringifySlots)
        if (stringifySlots.isNotEmpty()) appendLine()

        val stringifyCodes = vueComponentPart.script.stringifyCodes()
        appendBlock(stringifyCodes)

        appendLine("</script>")
        appendLine()
        appendLine("<template>")

        val stringifyElements = vueComponentPart.template.stringifyElements(indent)
        appendBlock(stringifyElements)

        appendLine("</template>")
        appendLine()
        appendLine("<style scoped>")

        val stringifyClasses = vueComponentPart.style.stringifyStyleClass()
        appendLine(stringifyClasses)

        appendLine("</style>")
    }
}