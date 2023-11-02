package top.potmot.core.generate

import top.potmot.config.GenConfig
import top.potmot.core.template.table.mysqlTableStringify
import top.potmot.core.template.table.postgreTableStringify
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView

fun generateTableDefine(
    table: GenTableAssociationsView,
    dataSourceTypes: List<DataSourceType> = listOf(table.schema?.dataSource?.type ?: GenConfig.dataSourceType)
): Map<String, String> {
    val map = mutableMapOf<String, String>()

    dataSourceTypes.forEach {
        map["${it.name.lowercase()}${GenConfig.separator}${table.name}.sql"] = when (it) {
            DataSourceType.MySQL -> table.mysqlTableStringify()
            DataSourceType.PostgreSQL -> table.postgreTableStringify()
        }
    }

    return map
}


