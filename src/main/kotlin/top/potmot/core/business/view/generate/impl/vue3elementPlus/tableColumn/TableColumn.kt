package top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn

import top.potmot.core.business.utils.components
import top.potmot.core.business.utils.lowerName
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.entity.dto.GenEntityBusinessView

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

        return TableColumnData()
    }
}