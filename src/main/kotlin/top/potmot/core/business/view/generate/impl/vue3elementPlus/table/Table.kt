package top.potmot.core.business.view.generate.impl.vue3elementPlus.table

import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.tableColumn
import top.potmot.core.common.vue3.Element
import top.potmot.core.common.vue3.Prop
import top.potmot.core.common.vue3.PropBind
import top.potmot.core.common.vue3.VIf

private const val idColumn = "idColumn"
private const val indexColumn = "indexColumn"
private const val multiSelect = "multiSelect"

fun tableUtilProps(
    showId: Boolean = false,
    showIndex: Boolean = true,
    showSelection: Boolean = true,
) = listOf(
    Prop(idColumn, "boolean", false, showId.toString()),
    Prop(indexColumn, "boolean", false, showIndex.toString()),
    Prop(multiSelect, "boolean", false, showSelection.toString()),
)

fun tableUtilColumns(idPropertyName: String) = listOf(
    tableColumn(
        label = "ID",
        prop = idPropertyName,
    ).merge {
        directives += VIf(idColumn)
        props += PropBind("fixed", "pageSizeStore.isSmall ? undefined : 'left'")
    },
    tableColumn(
        type = "index",
    ).merge {
        directives += VIf(indexColumn)
        props += PropBind("fixed", "pageSizeStore.isSmall ? undefined : 'left'")
    },
    tableColumn(
        type = "selection",
        width = 43,
    ).merge {
        directives += VIf(multiSelect)
        props += PropBind("fixed", "pageSizeStore.isSmall ? undefined : 'left'")
    },
)

fun operationsColumn(content: Collection<Element>) = tableColumn(
    label = "操作",
    content = content,
).merge {
    props += PropBind("fixed", "pageSizeStore.isSmall ? undefined : 'right'")
}
