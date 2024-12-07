package top.potmot.utils.string

class StringIndentScopeBuilder(
    private val indent: String = "    ",
    val stringBuilder: StringBuilder = StringBuilder(),
) {
    private var currentIndent = ""

    fun scope(
        scopeBlock: () -> Unit
    ) {
        currentIndent += indent
        scopeBlock()
        currentIndent = currentIndent.dropLast(indent.length)
    }

    fun append(content: String) {
        stringBuilder.append(content)
    }

    fun line(
        content: String,
    ) {
        stringBuilder.appendLine("$currentIndent$content")
    }

    fun lines(
        lines: Iterable<String>,
        produce: (line: String) -> String = { it }
    ) {
        lines.forEach {
            line(produce(it))
        }
    }

    fun lines(
        vararg lines: String,
        produce: (line: String) -> String = { it }
    ) {
        lines.forEach {
            line(produce(it))
        }
    }

    fun block(
        block: String?,
        produce: (line: String) -> String = { it }
    ) {
        block?.let {
            lines(it.lines(), produce)
        }
    }
}

fun buildScopeString(
    indent: String = "    ",
    block: StringIndentScopeBuilder.() -> Unit
): String {
    val scopeBuilder = StringIndentScopeBuilder(indent)
    scopeBuilder.block()
    return scopeBuilder.stringBuilder.toString()
}
