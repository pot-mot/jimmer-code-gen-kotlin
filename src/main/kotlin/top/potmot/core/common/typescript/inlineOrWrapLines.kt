package top.potmot.core.common.typescript

fun Iterable<String>.inlineOrWarpLines(
    indent: String,
    wrapThreshold: Int,
    separator: String = ",",
): String {
    val inline = joinToString("$separator ")

    return if (inline.length > wrapThreshold) {
        "\n" + joinToString("$separator\n") { "$indent$it" } + "\n"
    } else {
        inline
    }
}
