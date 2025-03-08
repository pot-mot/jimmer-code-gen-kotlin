package top.potmot.utils.string

class StringIndentScopeBuilder(
    private val indent: String,
    private var currentIndent: String,
    val stringBuilder: StringBuilder = StringBuilder(),
) {
    fun scope(
        scopeBlock: () -> Unit,
    ) {
        currentIndent += indent
        scopeBlock()
        currentIndent = currentIndent.dropLast(indent.length)
    }

    fun scope(
        before: String,
        after: String,
        scopeBlock: () -> Unit,
    ) {
        line(before)
        scope(scopeBlock)
        line(after)
    }

    fun scopeEndNoLine(
        before: String,
        after: String,
        scopeBlock: () -> Unit,
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
    ) {
        val iterator = lines.iterator()
        if (iterator.hasNext()) {
            if (stringBuilder.endsWith('\n')) {
                stringBuilder.append(currentIndent)
            }
            stringBuilder.appendLine(iterator.next())
            while (iterator.hasNext()) {
                stringBuilder.append(currentIndent)
                stringBuilder.appendLine(iterator.next())
            }
        }
    }

    fun lines(
        lines: Iterable<String>,
        produce: ((String) -> String),
    ) {
        val iterator = lines.iterator()
        if (iterator.hasNext()) {
            if (stringBuilder.endsWith('\n')) {
                stringBuilder.append(currentIndent)
            }
            stringBuilder.appendLine(produce(iterator.next()))
            while (iterator.hasNext()) {
                stringBuilder.append(currentIndent)
                stringBuilder.appendLine(produce(iterator.next()))
            }
        }
    }

    fun lines(
        vararg lines: String,
    ) = lines(lines.asIterable())

    fun lines(
        vararg lines: String,
        produce: ((String) -> String),
    ) = lines(lines.asIterable(), produce)

    fun block(
        block: String?,
    ) {
        if (block == null) return
        lines(block.lines())
    }

    fun block(
        block: String?,
        produce: ((String) -> String),
    ) {
        if (block == null) return
        lines(block.lines(), produce)
    }

    fun blockNotEndLine(
        block: String?,
    ) {
        if (block == null) return

        val blockLines = block.lines()

        if (blockLines.size == 1) {
            append(blockLines.first())
        } else if (blockLines.isNotEmpty()) {
            lines(blockLines.subList(0, blockLines.size - 1))
            append(blockLines.last())
        }
    }

    fun blockNotEndLine(
        block: String?,
        produce: ((String) -> String),
    ) {
        if (block == null) return

        val blockLines = block.lines()

        if (blockLines.size == 1) {
            append(blockLines.first())
        } else if (blockLines.isNotEmpty()) {
            lines(blockLines.subList(0, blockLines.size - 1), produce)
            append(produce(blockLines.last()))
        }
    }
}

fun buildScopeString(
    indent: String = "    ",
    currentIndent: String = "",
    block: StringIndentScopeBuilder.() -> Unit,
): String {
    val scopeBuilder = StringIndentScopeBuilder(indent, currentIndent)
    scopeBuilder.block()
    return scopeBuilder.stringBuilder.toString()
}
