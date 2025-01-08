package top.potmot.core.business.view.generate.impl.vue3elementPlus.table

import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.table
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.tableColumn
import top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn.TableColumnData
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.FunctionArg
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.emptyLineCode
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.Element
import top.potmot.core.business.view.generate.meta.vue3.Event
import top.potmot.core.business.view.generate.meta.vue3.EventArg
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.Slot
import top.potmot.core.business.view.generate.meta.vue3.SlotProp
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.slotElement
import top.potmot.core.business.view.generate.storePath
import top.potmot.entity.dto.share.GenerateProperty

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

fun viewTable(
    data: String,
    type: String,
    typePath: String,
    idPropertyName: String,
    content: List<Pair<GenerateProperty, TableColumnData>>,
    childrenProp: String? = null,
    showId: Boolean = false,
    showIndex: Boolean = true,
    showSelection: Boolean = true,
    border: Boolean = true,
    stripe: Boolean = true,
) = Component(
    imports = listOf(
        ImportType(typePath, type),
        Import("$storePath/pageSizeStore", "usePageSizeStore"),
    ) + content.flatMap { it.second.imports },
    props = listOf(
        Prop(data, "Array<$type>"),
    ) + tableUtilProps(
        showId,
        showIndex,
        showSelection
    ),
    slots = listOf(
        Slot(
            "operations",
            props = listOf(
                SlotProp("row", type),
                SlotProp("index", "number")
            )
        )
    ),
    emits = listOf(
        Event(
            "selectionChange",
            args = listOf(EventArg("selection", "Array<$type>"))
        )
    ),
    script = listOf(
        ConstVariable("pageSizeStore", null, "usePageSizeStore()"),
        emptyLineCode,
        Function(
            name = "handleSelectionChange",
            args = listOf(FunctionArg("newSelection", "Array<$type>")),
            body = listOf(CodeBlock("emits(\"selectionChange\", newSelection)"))
        )
    ),
    template = listOf(
        table(
            data = data,
            rowKey = idPropertyName,
            childrenProp = childrenProp,
            border = border,
            stripe = stripe,
            columns = tableUtilColumns(idPropertyName) + content.map { (property, tableColumnData) ->
                tableColumn(
                    property.name,
                    property.comment,
                    content = tableColumnData.elements
                ).merge {
                    props += tableColumnData.props
                }
            } + operationsColumn(
                listOf(
                    slotElement(
                        "operations",
                        props = listOf(
                            PropBind("row", "scope.row as $type"),
                            PropBind("index", "scope.${'$'}index")
                        )
                    )
                )
            )
        ).merge {
            events += EventBind("selection-change", "handleSelectionChange")
        }
    )
)

