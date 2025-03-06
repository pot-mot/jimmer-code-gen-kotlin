package top.potmot.core.common.style

data class StyleClass(
    val selector: String,
    val properties: Map<String, String> = emptyMap()
)