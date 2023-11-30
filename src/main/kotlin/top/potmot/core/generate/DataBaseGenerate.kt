package top.potmot.core.generate

import top.potmot.config.GenConfig
import top.potmot.core.template.table.MysqlTableDefineBuilder
import top.potmot.core.template.table.PostgreTableDefineBuilder
import top.potmot.core.template.table.TableDefineBuilder
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView

fun DataSourceType?.getTableDefineBuilder(): TableDefineBuilder =
    when (this ?: GenConfig.dataSourceType) {
        DataSourceType.MySQL -> MysqlTableDefineBuilder()
        DataSourceType.PostgreSQL -> PostgreTableDefineBuilder()
    }

fun Set<DataSourceType>.getTableDefineBuilder(): Map<DataSourceType, TableDefineBuilder> =
    if (this.isEmpty()) {
        mapOf(GenConfig.dataSourceType to GenConfig.dataSourceType.getTableDefineBuilder())
    } else {
        associate { Pair(it, it.getTableDefineBuilder()) }
    }

fun generateTableDefine(
    table: GenTableAssociationsView,
    dataSourceTypes: List<DataSourceType>
): Map<String, String> {
    val map = mutableMapOf<String, String>()

    dataSourceTypes.toSet().getTableDefineBuilder().forEach {(type, builder) ->
        map["[${type.name.lowercase()}] ${table.name}.sql"] = builder.stringify(table)
    }

    return map
}

fun generateTableDefines(
    tables: Collection<GenTableAssociationsView>,
    dataSourceTypes: List<DataSourceType>
): Map<String, String> {
    val map = mutableMapOf<String, String>()

    dataSourceTypes.toSet().getTableDefineBuilder().forEach {(type, builder) ->
        map["[${type.name.lowercase()}] tables.sql"] = builder.stringify(tables)
    }

    return map
}


