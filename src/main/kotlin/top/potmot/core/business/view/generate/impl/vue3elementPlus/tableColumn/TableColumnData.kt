package top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn

import top.potmot.core.business.view.generate.meta.typescript.ImportItem
import top.potmot.core.business.view.generate.meta.vue3.Element

data class TableColumnData(
    val elements: Collection<Element>,
    val imports: Collection<ImportItem> = emptyList(),
)

fun TableColumnData(
    vararg element: Element?
) = TableColumnData(element.filterNotNull())