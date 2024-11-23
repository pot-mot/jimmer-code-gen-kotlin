package top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn

import top.potmot.core.business.property.isShortAssociation
import top.potmot.core.business.utils.PropertyFormType
import top.potmot.core.business.utils.components
import top.potmot.core.business.utils.formType
import top.potmot.core.business.utils.lowerName
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.typescript.ImportItem
import top.potmot.core.business.view.generate.meta.vue3.Element
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.share.GenerateProperty
import top.potmot.entity.dto.share.GeneratePropertyData
import top.potmot.enumeration.targetOneAssociationTypes

data class TableColumnData(
    val elements: Collection<Element> = emptyList(),
    val imports: Collection<ImportItem> = emptyList(),
    val props: Collection<PropBind> = emptyList(),
)

private const val formatTableColumnDate = "formatTableColumnDate"
private const val formatTableColumnTime = "formatTableColumnTime"
private const val formatTableColumnDateTime = "formatTableColumnDateTime"

private fun GenEntityBusinessView.TargetOf_properties.TargetOf_typeEntity.TargetOf_shortViewProperties.toProperty(entityId: Long) =
    GenEntityBusinessView.TargetOf_properties(
        toEntity {
            entity {
                id = entityId
            }
            typeTable {
                entity = null
            }
        }
    )

interface TableColumn {
    fun GenEntityBusinessView.TargetOf_properties.tableColumnDataList(): List<Pair<GenerateProperty, TableColumnData>> {
        if (isShortAssociation) {
            if (associationType in targetOneAssociationTypes) {
                return typeEntity!!.shortViewProperties.flatMap { shortViewProperty ->
                    shortViewProperty.toProperty(entityId = typeEntity.id).tableColumnDataList().map {
                        GeneratePropertyData(
                            name = "${name}.${shortViewProperty.name}",
                            comment = "${comment}${shortViewProperty.comment}"
                        ) to it.second
                    }
                }
            }
        }

        if (enum != null) {
            val componentName = enum.components.view

            return listOf(
                this to TableColumnData(
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
                            componentPath + "/" + enum.lowerName + "/" + componentName + ".vue",
                            componentName,
                        )
                    )
                )
            )
        }

        return listOf(
            this to when (formType) {
                PropertyFormType.DATE -> {
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
                    TableColumnData(
                        imports = listOf(
                            Import("$utilPath/timeFormat", formatTableColumnDateTime)
                        ),
                        props = listOf(
                            PropBind("formatter", formatTableColumnDateTime)
                        )
                    )
                }

                else -> TableColumnData()
            }
        )
    }
}