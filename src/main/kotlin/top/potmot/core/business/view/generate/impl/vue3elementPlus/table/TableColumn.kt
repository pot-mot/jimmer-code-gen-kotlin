package top.potmot.core.business.view.generate.impl.vue3elementPlus.table

import top.potmot.config.tableColumnWithDateTimeFormat
import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.ForceIdViewProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.PropertyFormType
import top.potmot.core.business.meta.TypeEntityProperty
import top.potmot.core.business.meta.formType
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.checkbox
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.typescript.TsImport
import top.potmot.core.business.view.generate.meta.vue3.Element
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenEntityBusinessView

data class TableColumnData(
    val elements: Collection<Element> = emptyList(),
    val imports: Collection<TsImport> = emptyList(),
    val props: Collection<PropBind> = emptyList(),
)

data class TableColumnPropertyKey(
    val id: Long,
    val name: String,
    val comment: String,
)

fun TableColumnPropertyKey(property: GenEntityBusinessView.TargetOf_properties) = TableColumnPropertyKey(
    id = property.id,
    name = property.name,
    comment = property.comment
)

fun TableColumnPropertyKey(property: PropertyBusiness) = TableColumnPropertyKey(
    id = property.id,
    name = property.name,
    comment = property.comment
)

private val defaultTableColumnData = TableColumnData()

private const val formatTableColumnDate = "formatTableColumnDate"
private const val formatTableColumnTime = "formatTableColumnTime"
private const val formatTableColumnDateTime = "formatTableColumnDateTime"

interface TableColumn {
    fun GenEntityBusinessView.TargetOf_properties.tableColumnDataPair(
        withDateTimeFormat: Boolean = tableColumnWithDateTimeFormat,
    ): Pair<TableColumnPropertyKey, TableColumnData> =
        TableColumnPropertyKey(this) to when (formType) {
            PropertyFormType.BOOLEAN -> {
                TableColumnData(
                    elements = listOf(
                        checkbox(
                            "scope.row.$name",
                            doubleBind = false,
                        )
                    )
                )
            }

            PropertyFormType.TIME -> {
                if (!withDateTimeFormat)
                    defaultTableColumnData
                else
                    TableColumnData(
                        imports = listOf(
                            Import("$utilPath/timeFormat", formatTableColumnTime)
                        ),
                        props = listOf(
                            PropBind("formatter", formatTableColumnTime)
                        )
                    )
            }

            PropertyFormType.DATE -> {
                if (!withDateTimeFormat)
                    defaultTableColumnData
                else
                    TableColumnData(
                        imports = listOf(
                            Import("$utilPath/timeFormat", formatTableColumnDate)
                        ),
                        props = listOf(
                            PropBind("formatter", formatTableColumnDate)
                        )
                    )
            }

            PropertyFormType.DATETIME -> {
                if (!withDateTimeFormat)
                    defaultTableColumnData
                else
                    TableColumnData(
                        imports = listOf(
                            Import("$utilPath/timeFormat", formatTableColumnDateTime)
                        ),
                        props = listOf(
                            PropBind("formatter", formatTableColumnDateTime)
                        )
                    )
            }

            else -> defaultTableColumnData
        }


    fun PropertyBusiness.tableColumnDataPairs(
        withDateTimeFormat: Boolean = tableColumnWithDateTimeFormat,
    ): List<Pair<TableColumnPropertyKey, TableColumnData>> =
        if (this is TypeEntityProperty && isShortView && isTargetOne) {
            typeEntityBusiness.shortViewProperties.flatMap { shortViewProperty ->
                shortViewProperty.tableColumnDataPairs(withDateTimeFormat).map {
                    TableColumnPropertyKey(
                        id = id,
                        name = "${
                            when (this) {
                                is AssociationProperty -> name
                                is ForceIdViewProperty -> associationProperty.name
                            }
                        }.${shortViewProperty.name}",
                        comment = "${comment}${shortViewProperty.comment}"
                    ) to it.second
                }
            }
        } else if (this is EnumProperty) {
            val component = enum.components.view

            listOf(
                TableColumnPropertyKey(this) to TableColumnData(
                    elements = listOf(
                        TagElement(
                            component.name,
                            props = listOf(
                                PropBind("value", "scope.row.$name")
                            )
                        )
                    ),
                    imports = listOf(
                        ImportDefault(
                            "@/" + component.fullPath,
                            component.name,
                        )
                    )
                )
            )
        } else {
            listOf(property.tableColumnDataPair(withDateTimeFormat))
        }
}