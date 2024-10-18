package top.potmot.entity.dto

import top.potmot.enumeration.GenerateTag

data class GenerateFile(
    val path: String,
    val content: String,
    val tags: List<GenerateTag>
)