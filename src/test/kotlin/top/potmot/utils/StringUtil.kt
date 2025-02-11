package top.potmot.utils

fun String.replaceSinceTimeComment() =
    this.replace(Regex("\n \\*\\s*@since\\s+\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}"), "")
