package top.potmot.core.business.view.generate.meta.typescript

import top.potmot.utils.string.StringIndentScopeBuilder
import top.potmot.utils.string.buildScopeString
import top.potmot.utils.string.clearBlankLine

sealed interface TsCode

data class CodeBlock(
    val content: String,
) : TsCode

fun CodeBlock(vararg content: String?) =
    CodeBlock(content.filterNotNull().joinToString(""))

val emptyLineCode = CodeBlock("")

fun commentLine(comment: String) = CodeBlock("// $comment")

fun commentBlock(vararg comment: String?) = CodeBlock(buildString {
    appendLine("/**")
    comment.filterNotNull().forEach {
        appendLine(" * $it")
    }
    append(" */")
})

data class ConstVariable(
    val name: String,
    val type: String? = null,
    val value: String,
) : TsCode {
    fun stringify() =
        "const $name${if (type != null) ": $type" else ""} = $value"
}

data class LetVariable(
    val name: String,
    val type: String? = null,
    val value: String,
) : TsCode {
    fun stringify() =
        "let $name${if (type != null) ": $type" else ""} = $value"
}


sealed interface ObjectPropertyValue

data class ObjectProperty(
    val name: String,
    val value: ObjectPropertyValue,
)

fun ObjectProperty(
    name: String,
    value: String,
) = ObjectProperty(
    name, RawPropertyValue(value)
)

data class RawPropertyValue(
    val value: String
): ObjectPropertyValue

data class TsObject(
    val properties: Collection<ObjectProperty>
): TsCode, ObjectPropertyValue {
    fun stringify(builder: StringIndentScopeBuilder) {
        builder.line("{")
        builder.scope {
            properties.forEachIndexed { index, it ->
                builder.append(it.name)
                builder.append(": ")

                when (it.value) {
                    is RawPropertyValue -> builder.append(it.value.value)
                    is TsObject -> it.value.stringify(builder)
                }

                builder.line(if (index != properties.size - 1) "," else "")
            }
        }
        builder.append("}")
    }

    fun stringify(indent: String) = buildScopeString(indent) {
        stringify(this)
    }
}


data class FunctionArg(
    val name: String,
    val type: String,
    val required: Boolean = true,
    val defaultValue: String? = null,
) {
    fun stringify() =
        "$name${if (required) "" else "?"}: $type${if (defaultValue != null) " = $defaultValue" else ""}"
}

data class Function(
    var async: Boolean = false,
    val name: String,
    val args: Iterable<FunctionArg> = emptyList(),
    val returnType: String? = null,
    val body: Collection<TsCode>,
) : TsCode {
    fun stringify(indent: String, wrapThreshold: Int): String {
        val argsString = args
            .map { arg -> arg.stringify() }
            .inlineOrWarpLines(indent, wrapThreshold)

        val returnType = (returnType ?: "void")
            .let { if (async) "Promise<$it>" else it }

        val body = body
            .stringify(indent, wrapThreshold)
            .replace("\n", "\n$indent")

        return "const $name = ${if (async) "async " else ""}(${argsString}): $returnType => {\n$indent$body\n}"
    }
}

fun Iterable<TsCode>.stringify(
    indent: String,
    wrapThreshold: Int,
): String = joinToString("\n") { item ->
    when (item) {
        is ConstVariable -> item.stringify()
        is LetVariable -> item.stringify()
        is TsObject -> item.stringify(indent)
        is Function -> item.stringify(indent, wrapThreshold)
        is CodeBlock -> item.content
    }
}.clearBlankLine()