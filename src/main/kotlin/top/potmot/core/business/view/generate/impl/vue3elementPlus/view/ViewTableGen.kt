package top.potmot.core.business.view.generate.impl.vue3elementPlus.view

import top.potmot.core.business.meta.LazyGenerateResult
import top.potmot.core.business.meta.LazyGenerated
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.table
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.tableColumn
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.operationsColumn
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.tableUtilColumns
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.tableUtilProps
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
    content: List<ViewTableColumnData>,
    childrenProp: String? = null,
    showId: Boolean = false,
    showIndex: Boolean = true,
    showSelection: Boolean = true,
    border: Boolean = true,
    stripe: Boolean = true,
) = Component {
    imports += listOf(
        ImportType(typePath, type),
        Import("$storePath/pageSizeStore", "usePageSizeStore"),
    )

    imports += content.flatMap { it.imports }

    props += Prop(data, "Array<$type>")
    props += tableUtilProps(
        showId,
        showIndex,
        showSelection
    )

    slots += Slot(
        "operations",
        props = listOf(
            SlotProp("row", type),
            SlotProp("index", "number")
        )
    )

    emits += Event(
        "selectionChange",
        args = listOf(EventArg("selection", "Array<$type>"))
    )

    script += listOf(
        ConstVariable("pageSizeStore", null, "usePageSizeStore()"),
        emptyLineCode,
        Function(
            name = "handleSelectionChange",
            args = listOf(FunctionArg("newSelection", "Array<$type>")),
            body = listOf(CodeBlock("emits(\"selectionChange\", newSelection)"))
        )
    )

    val propColumns = mutableListOf<Element>()
    val expand = mutableListOf<Element>()

    content.forEach {
        val column = tableColumn(
            prop = it.prop,
            label = it.label,
            width = it.width,
            minWidth = it.minWidth,
            showOverflowTooltip = it.showOverflowTooltip,
            content = it.elements
        ).merge {
            props += it.props
        }

        if (it.expand) expand += column
        else propColumns += column
    }

    val operationsColumn = operationsColumn(
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

    template += table(
        data = data,
        rowKey = idPropertyName,
        childrenProp = childrenProp,
        border = border,
        stripe = stripe,
        columns = tableUtilColumns(idPropertyName) + propColumns + operationsColumn,
        expand = expand
    ).merge {
        props += PropBind("class", "view-table", isLiteral = true)
        events += EventBind("selection-change", "handleSelectionChange")
    }
}

interface ViewTableGen : Generator, ViewTableColumn {
    @Throws(ModelException.TreeEntityCannotFoundChildrenProperty::class)
    private fun viewTableComponent(
        entity: RootEntityBusiness,
        properties: Collection<PropertyBusiness>
    ): Pair<Component, List<LazyGenerated>> {
        val rows = "rows"

        val childrenProp = takeIf { entity.isTree }?.let {
            entity.childrenProperty.name
        }

        val dto = entity.dto
        val isTree = entity.isTree

        val content = properties.flatMap { it.viewTableColumns() }

        val component = viewTable(
            data = rows,
            type = if (isTree) dto.treeView else dto.listView,
            typePath = staticPath,
            stripe = !isTree,
            idPropertyName = entity.idProperty.name,
            content = content,
            childrenProp = childrenProp,
            showIndex = !isTree,
        )

        return component to content.flatMap { it.lazyItems }
    }

    fun viewTableFiles(
        entity: RootEntityBusiness,
        properties: Collection<PropertyBusiness> = entity.viewTableProperties
    ): LazyGenerateResult {
        val (component, lazyItems) = viewTableComponent(entity, properties)

        val files = listOf(
            GenerateFile(
                entity,
                entity.components.table.fullPath,
                stringify(component),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.ViewTable),
            )
        )

        return LazyGenerateResult(
            files,
            lazyItems,
        )
    }

    @Throws(ModelException.TreeEntityCannotFoundChildrenProperty::class)
    private fun viewTableComponent(
        entity: SubEntityBusiness,
        properties: Collection<PropertyBusiness>
    ): Pair<Component, List<LazyGenerated>> {
        val rows = "rows"

        val childrenProp = takeIf { entity.isTree }?.let {
            entity.childrenProperty.name
        }

        val dto = entity.dto
        val isTree = entity.isTree

        val content = properties.flatMap { it.viewTableColumns() }

        val component = viewTable(
            data = rows,
            type = if (isTree) dto.treeView else dto.listView,
            typePath = staticPath,
            stripe = !isTree,
            idPropertyName = entity.idProperty.name,
            content = content,
            childrenProp = childrenProp,
            showIndex = !isTree,
            showSelection = false,
        )

        return component to content.flatMap { it.lazyItems }
    }

    fun viewTableFiles(
        entity: SubEntityBusiness,
        properties: Collection<PropertyBusiness> = entity.viewTableProperties
    ): LazyGenerateResult {
        val (component, lazyItems) = viewTableComponent(entity, properties)

        val files = listOf(
            GenerateFile(
                entity,
                entity.components.viewTable.fullPath,
                stringify(component),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.ViewTable, GenerateTag.SubView),
            )
        )

        return LazyGenerateResult(
            files,
            lazyItems,
        )
    }
}