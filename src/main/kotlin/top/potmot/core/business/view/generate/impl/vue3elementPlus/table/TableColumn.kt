package top.potmot.core.business.view.generate.impl.vue3elementPlus.table

import top.potmot.core.config.getContextOrGlobal
import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.CommonProperty
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
    val width: Int? = null,
    val minWidth: Int? = null,
    val showOverflowTooltip: Boolean = false,
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

private const val BOOLEAN_MIN_WIDTH = 43
private const val DATETIME_MIN_WIDTH = 193
private const val NUMBER_MIN_WIDTH = 161
private const val ENUM_MIN_WIDTH = 161
private const val ASSOCIATION_ID_MIN_WIDTH = 161
private const val COMMON_MIN_WIDTH = 129

val PropertyFormType.tableMinWidth: Int
    get() = when (this) {
        PropertyFormType.BOOLEAN -> BOOLEAN_MIN_WIDTH
        PropertyFormType.DATETIME -> DATETIME_MIN_WIDTH
        PropertyFormType.INT -> NUMBER_MIN_WIDTH
        PropertyFormType.FLOAT -> NUMBER_MIN_WIDTH
        else -> COMMON_MIN_WIDTH
    }

val PropertyBusiness.tableMinWidth: Int?
    get() = when (this) {
        is CommonProperty -> formType.tableMinWidth
        is EnumProperty -> ENUM_MIN_WIDTH
        is ForceIdViewProperty -> ASSOCIATION_ID_MIN_WIDTH
        is AssociationProperty -> if (isLongAssociation) null else COMMON_MIN_WIDTH
    }

interface TableColumn {
    fun GenEntityBusinessView.TargetOf_properties.tableColumnDataPair(
        withDateTimeFormat: Boolean = getContextOrGlobal().dateTimeFormatInView,
    ): Pair<TableColumnPropertyKey, TableColumnData> =
        TableColumnPropertyKey(this) to when (formType) {
            PropertyFormType.FILE -> {
                TODO()
            }

            PropertyFormType.FILE_LIST -> {
                TODO()
            }

            PropertyFormType.IMAGE -> {
                TODO()
            }

            PropertyFormType.IMAGE_LIST -> {
                TODO()
            }

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
            .copy(minWidth = formType.tableMinWidth)

    fun PropertyBusiness.tableColumnDataPairs(
        withDateTimeFormat: Boolean = getContextOrGlobal().dateTimeFormatInView,
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
                    minWidth = COMMON_MIN_WIDTH,
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