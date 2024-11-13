package top.potmot.utils.string

fun StringBuilder.appendLines(lines: Iterable<String>, produce: (line: String) -> String = { it }) {
    lines.forEach {
        appendLine(produce(it))
    }
}

fun StringBuilder.appendLines(vararg lines: String, produce: (line: String) -> String = { it }) {
    lines.forEach {
        appendLine(produce(it))
    }
}

fun StringBuilder.appendBlock(block: String?, produce: (line: String) -> String = { it }) {
    block?.let {
        appendLines(it.split("\n"), produce)
    }
}