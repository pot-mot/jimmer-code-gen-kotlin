package top.potmot.core.business.view.generate.meta.style

data class StyleClass(
    val selector: String,
    val properties: Map<String, String> = emptyMap()
)