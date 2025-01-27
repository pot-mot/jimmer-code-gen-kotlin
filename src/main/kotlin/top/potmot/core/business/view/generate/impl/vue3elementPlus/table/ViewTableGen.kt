package top.potmot.core.business.view.generate.impl.vue3elementPlus.table

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.table
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.tableColumn
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.FunctionArg
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.emptyLineCode
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.Event
import top.potmot.core.business.view.generate.meta.vue3.EventArg
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.Slot
import top.potmot.core.business.view.generate.meta.vue3.SlotProp
import top.potmot.core.business.view.generate.meta.vue3.slotElement
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.storePath
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException

fun viewTable(
    data: String,
    type: String,
    typePath: String,
    idPropertyName: String,
    content: List<Pair<TableColumnPropertyKey, TableColumnData>>,
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
                    width = tableColumnData.width,
                    minWidth = tableColumnData.minWidth,
                    showOverflowTooltip = tableColumnData.showOverflowTooltip,
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
            props += PropBind("class", "view-table", isLiteral = true)
            events += EventBind("selection-change", "handleSelectionChange")
        }
    )
)

interface ViewTableGen: Generator, TableColumn {
    @Throws(ModelException.TreeEntityCannotFoundChildrenProperty::class)
    private fun viewTableComponent(entity: EntityBusiness): Component {
        val rows = "rows"

        val childrenProp = takeIf { entity.isTree }?.let {
            entity.childrenProperty.name
        }

        val dto = entity.dto
        val isTree = entity.isTree

        return viewTable(
            data = rows,
            type = if (isTree) dto.treeView else dto.listView,
            typePath = staticPath,
            stripe = !isTree,
            idPropertyName = entity.idProperty.name,
            content = entity.tableProperties.flatMap { it.tableColumnDataPairs() },
            childrenProp = childrenProp,
            showIndex = !isTree,
        )
    }

    fun viewTableFile(entity: RootEntityBusiness) = GenerateFile(
        entity,
        entity.components.table.fullPath,
        stringify(viewTableComponent(entity)),
        listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Table),
    )
}