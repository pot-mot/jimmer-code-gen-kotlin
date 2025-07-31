package top.potmot.core.business.view.generate.impl.vue3elementPlus.view

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.LazyGenerated
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.tableMinWidth
import top.potmot.core.common.typescript.TsImport
import top.potmot.core.common.vue3.Element
import top.potmot.core.common.vue3.PropBind
import top.potmot.core.config.getContextOrGlobal

data class ViewTableColumnData(
    val label: String,
    val properties: Collection<PropertyBusiness>,
    val elements: Collection<Element> = emptyList(),
    val imports: Collection<TsImport> = emptyList(),
    val props: Collection<PropBind> = emptyList(),
    val lazyItems: Collection<LazyGenerated> = emptyList(),
    val width: Int? = null,
    val minWidth: Int? = null,
    val expand: Boolean = false,
    val showOverflowTooltip: Boolean = false,
)

interface ViewTableColumn : ViewItem {
    fun PropertyBusiness.viewTableColumns(
        withDateTimeFormat: Boolean = getContextOrGlobal().dateTimeFormatInView,
    ): List<ViewTableColumnData> =
        viewItem(
            { name -> "scope.row.$name" },
            { properties ->
                ViewItemData(
                    label = properties.lastOrNull()?.comment ?: "",
                    properties = properties
                )
            },
            withDateTimeFormat
        ).let { viewItem ->
            if (viewItem.shortViews.isNotEmpty()) {
                viewItem.flatShortViews.map { (shortViewProperty, shortViewItem) ->
                    ViewTableColumnData(
                        properties = shortViewItem.properties,
                        label = shortViewItem.label,
                        elements = shortViewItem.elements,
                        imports = shortViewItem.imports,
                        lazyItems = shortViewItem.lazyItems,
                        minWidth = shortViewProperty.tableMinWidth,
                    )
                }
            } else {
                val expand = this is AssociationProperty && (isLongAssociation || isShortView)

                listOf(
                    ViewTableColumnData(
                        properties = viewItem.properties,
                        label = comment,
                        elements = viewItem.elements,
                        imports = viewItem.imports,
                        lazyItems = viewItem.lazyItems,
                        minWidth = tableMinWidth,
                        expand = expand
                    )
                )
            }
        }
}