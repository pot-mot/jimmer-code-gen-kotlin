package top.potmot.core.generate

import top.potmot.config.GenConfig
import top.potmot.core.template.table.mysqlTableStringify
import top.potmot.core.template.table.mysqlTablesStringify
import top.potmot.core.template.table.postgreTableStringify
import top.potmot.core.template.table.postgreTablesStringify
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView

fun generateTableDefine(
    table: GenTableAssociationsView,
    dataSourceTypes: List<DataSourceType>
): Map<String, String> {
    val map = mutableMapOf<String, String>()

    val types = if (dataSourceTypes.isNotEmpty()) {
        dataSourceTypes
    } else {
        listOf(GenConfig.dataSourceType)
    }

    types.forEach {
        map["[${it.name.lowercase()}] ${table.name}.sql"] = when (it) {
            DataSourceType.MySQL -> table.mysqlTableStringify()
            DataSourceType.PostgreSQL -> table.postgreTableStringify()
        }
    }

    return map
}

fun generateTableDefines(
    tables: Collection<GenTableAssociationsView>,
    dataSourceTypes: List<DataSourceType>
): Map<String, String> {
    val map = mutableMapOf<String, String>()

    val types = if (dataSourceTypes.isNotEmpty()) {
        dataSourceTypes
    } else {
        listOf(GenConfig.dataSourceType)
    }

    types.forEach {
        map["[${it.name.lowercase()}] all_in_one.sql"] = when (it) {
            DataSourceType.MySQL -> tables.mysqlTablesStringify()
            DataSourceType.PostgreSQL -> tables.postgreTablesStringify()
        }
    }

    return map
}


