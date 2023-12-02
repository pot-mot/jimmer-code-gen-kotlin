package top.potmot.model.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.format(formatPattern: String = "yyyy-MM-dd HH:mm:ss"): String =
    format(DateTimeFormatter.ofPattern(formatPattern))

fun now(formatPattern: String = "yyyy-MM-dd HH:mm:ss"): String =
    LocalDateTime.now().format(formatPattern)
