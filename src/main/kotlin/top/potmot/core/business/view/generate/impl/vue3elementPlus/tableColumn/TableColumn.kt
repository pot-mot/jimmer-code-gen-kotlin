package top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn

import top.potmot.config.tableColumnWithDateTimeFormat
import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.ForceIdViewProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.PropertyFormType
import top.potmot.core.business.meta.TypeEntityProperty
import top.potmot.core.business.meta.formType
import top.potmot.core.business.utils.mark.components
import top.potmot.core.business.utils.mark.dir
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.typescript.ImportItem
import top.potmot.core.business.view.generate.meta.vue3.Element
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.enumeration.targetOneAssociationTypes

data class TableColumnData(
    val elements: Collection<Element> = emptyList(),
    val imports: Collection<ImportItem> = emptyList(),
    val props: Collection<PropBind> = emptyList(),
)

data class TableColumnPropertyKey(
    val id: Long,
    val name: String,
    val comment: String
)

private val defaultTableColumnData = TableColumnData()

private const val formatTableColumnDate = "formatTableColumnDate"
private const val formatTableColumnTime = "formatTableColumnTime"
private const val formatTableColumnDateTime = "formatTableColumnDateTime"

interface TableColumn {
    fun GenEntityBusinessView.TargetOf_properties.tableColumnDataList(
        withDateTimeFormat: Boolean = tableColumnWithDateTimeFormat,
    ): List<Pair<TableColumnPropertyKey, TableColumnData>> {
        val key = TableColumnPropertyKey(
            id = id,
            name = name,
            comment = comment
        )

        if (enum != null) {
            val componentName = enum.components.view

            return listOf(
                key to TableColumnData(
                    elements = listOf(
                        TagElement(
                            componentName,
                            props = listOf(
                                PropBind("value", "scope.row.$name")
                            )
                        )
                    ),
                    imports = listOf(
                        ImportDefault(
                            componentPath + "/" + enum.dir + "/" + componentName + ".vue",
                            componentName,
                        )
                    )
                )
            )
        }

        return listOf(
            key to when (formType) {
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
        )
    }

    fun PropertyBusiness.tableColumnDataList(
        withDateTimeFormat: Boolean = tableColumnWithDateTimeFormat,
    ): List<Pair<TableColumnPropertyKey, TableColumnData>> {
        if (this is TypeEntityProperty && isShortView) {
            if (associationType in targetOneAssociationTypes) {
                return typeEntityBusiness.shortViewPropertyBusiness.flatMap { shortViewProperty ->
                    shortViewProperty.tableColumnDataList(withDateTimeFormat).map {
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
            } else {
                // TODO 完成对多短关联的翻译
            }
        }
        return property.tableColumnDataList(withDateTimeFormat)
    }
}