package top.potmot.core.business.view.generate.impl.vue3elementPlus.table

import top.potmot.core.business.view.generate.builder.vue3.componentLib.ElementPlus
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.table
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.tableColumn
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.FunctionArg
import top.potmot.core.business.view.generate.meta.typescript.ImportType
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
import top.potmot.entity.dto.GenEntityBusinessView

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
        fixed = ElementPlus.TableColumnFixed.LEFT,
    ).merge {
        directives += VIf(idColumn)
    },
    tableColumn(
        type = "index",
        fixed = ElementPlus.TableColumnFixed.LEFT,
    ).merge {
        directives += VIf(indexColumn)
    },
    tableColumn(
        type = "selection",
        fixed = ElementPlus.TableColumnFixed.LEFT,
    ).merge {
        directives += VIf(multiSelect)
    },
)

fun operationsColumn(content: Collection<Element>) = tableColumn(
    label = "操作",
    fixed = ElementPlus.TableColumnFixed.RIGHT,
    content = content,
)

fun viewTable(
    data: String,
    type: String,
    typePath: String,
    idPropertyName: String,
    content: Map<GenEntityBusinessView.TargetOf_properties, List<Element>>,
) = Component(
    imports = listOf(
        ImportType(typePath, listOf(type)),
    ),
    props = listOf(
        Prop(data, "Array<$type>"),
    ) + tableUtilProps(),
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
        Function(
            name = "handleSelectionChange",
            args = listOf(FunctionArg("newSelection", "Array<$type>")),
            body = listOf(CodeBlock("emits(\"selectionChange\", newSelection)"))
        )
    ),
    template = listOf(
        table(
            data = data,
            columns = tableUtilColumns(idPropertyName) + content.map { (property, elements) ->
                tableColumn(
                    property.name,
                    property.comment,
                    content = elements
                )
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

