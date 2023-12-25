package top.potmot.core.database.generate

import top.potmot.config.GenConfig
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView

fun DataSourceType?.getTableDefineGenerator(): TableDefineGenerator =
    when (this ?: GenConfig.dataSourceType) {
        DataSourceType.MySQL -> MysqlTableDefineGenerator()
        DataSourceType.PostgreSQL -> PostgreTableDefineGenerator()
    }

fun Set<DataSourceType>.getTableDefineBuilder(): Map<DataSourceType, TableDefineGenerator> =
    if (this.isEmpty()) {
        mapOf(GenConfig.dataSourceType to GenConfig.dataSourceType.getTableDefineGenerator())
    } else {
        associate { Pair(it, it.getTableDefineGenerator()) }
    }

fun generateTableDefine(
    table: GenTableAssociationsView,
    dataSourceTypes: List<DataSourceType>
): List<Pair<String, String>> =
    dataSourceTypes.toSet().getTableDefineBuilder().map {
        it.value.generate(table)
    }

fun generateTableDefines(
    tables: Collection<GenTableAssociationsView>,
    dataSourceTypes: List<DataSourceType>
): List<Pair<String, String>> =
    dataSourceTypes.toSet().getTableDefineBuilder().flatMap {
        it.value.generate(tables)
    }


