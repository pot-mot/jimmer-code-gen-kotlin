package top.potmot.core.business.view.generate.meta.typescript

sealed class ImportItem(
    open val path: String
)

data class Import(
    override val path: String,
    val items: List<String>,
) : ImportItem(path)

data class ImportType(
    override val path: String,
    val items: List<String>,
) : ImportItem(path)

data class ImportDefault(
    override val path: String,
    val item: String,
) : ImportItem(path)