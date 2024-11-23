package top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn

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

data class TableColumnData(
    val elements: Collection<Element> = emptyList(),
    val imports: Collection<ImportItem> = emptyList(),
    val props: Collection<PropBind> = emptyList(),
)

private const val formatTableColumnDate = "formatTableColumnDate"
private const val formatTableColumnTime = "formatTableColumnTime"
private const val formatTableColumnDateTime = "formatTableColumnDateTime"

interface TableColumn {
    fun GenEntityBusinessView.TargetOf_properties.createTableColumn(): TableColumnData {
        if (enum != null) {
            val componentName = enum.components.view

            return TableColumnData(
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
        }

        return when (formType) {
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
    }
}