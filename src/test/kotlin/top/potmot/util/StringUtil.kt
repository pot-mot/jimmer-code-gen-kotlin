package top.potmot.util

fun String.replaceSinceTimeComment() =
    this.replace(Regex("\n \\*\\s*@since\\s+\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}"), "")

fun String.escape() =
    this
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("'", "\\'")
        .replace("\n", "\\n")
        .replace("\t", "\\t")
        .replace("\r", "\\r")
        .replace("\b", "\\b")
