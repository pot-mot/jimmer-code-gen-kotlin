package top.potmot.core.template.table

import top.potmot.model.dto.GenTableAssociationsView

interface TableDefineBuilder {
    fun stringify(table: GenTableAssociationsView): String

    fun stringify(tables: Collection<GenTableAssociationsView>): String
}
