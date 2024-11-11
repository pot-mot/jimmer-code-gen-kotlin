package top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn

import top.potmot.core.business.utils.componentNames
import top.potmot.core.business.view.generate.meta.vue3.Element
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.entity.dto.GenEntityBusinessView

interface TableColumn {
    fun GenEntityBusinessView.TargetOf_properties.createTableColumn(): List<Element> {
        if (enum != null) {
            return listOf(
                TagElement(
                    enum.componentNames.view,
                    props = listOf(
                        PropBind("value", "scope.row.$name")
                    )
                )
            )
        }

        return emptyList()
    }
}