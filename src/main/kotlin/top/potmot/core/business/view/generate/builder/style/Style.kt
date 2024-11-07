package top.potmot.core.business.view.generate.builder.style

data class StyleClass(
    val selector: String,
    val properties: Map<String, String> = emptyMap()
)