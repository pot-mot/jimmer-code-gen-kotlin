package top.potmot.core.business.view.generate.meta.typescript

sealed class ImportItem(
    open val path: String
)

data class Import(
    override val path: String,
    val items: List<String>,
) : ImportItem(path)

fun Import(
    path: String,
    vararg item: String
) = Import(path, item.toList())

data class ImportType(
    override val path: String,
    val items: List<String>,
) : ImportItem(path)

fun ImportType(
    path: String,
    vararg item: String
) = ImportType(path, item.toList())

data class ImportDefault(
    override val path: String,
    val item: String,
) : ImportItem(path)