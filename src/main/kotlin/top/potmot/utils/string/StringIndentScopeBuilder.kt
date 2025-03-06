package top.potmot.utils.string

class StringIndentScopeBuilder(
    private val indent: String,
    private var currentIndent: String,
    val stringBuilder: StringBuilder = StringBuilder(),
) {
    fun scope(
        scopeBlock: () -> Unit
    ) {
        currentIndent += indent
        scopeBlock()
        currentIndent = currentIndent.dropLast(indent.length)
    }

    fun scope(
        before: String,
        after: String,
        scopeBlock: () -> Unit
    ) {
        line(before)
        scope(scopeBlock)
        line(after)
    }

    fun scopeEndNoLine(
        before: String,
        after: String,
        scopeBlock: () -> Unit
    ) {
        line(before)
        scope(scopeBlock)
        append(after)
    }

    fun append(content: String) {
        if (stringBuilder.endsWith('\n')) {
            stringBuilder.append(currentIndent)
        }
        stringBuilder.append(content)
    }

    fun line() {
        stringBuilder.appendLine()
    }

    fun line(content: String) {
        if (stringBuilder.endsWith('\n')) {
            stringBuilder.append(currentIndent)
        }
        stringBuilder.appendLine(content)
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
    currentIndent: String = "",
    block: StringIndentScopeBuilder.() -> Unit
): String {
    val scopeBuilder = StringIndentScopeBuilder(indent, currentIndent)
    scopeBuilder.block()
    return scopeBuilder.stringBuilder.toString()
}
