package top.potmot.core.generate

import top.potmot.config.GenConfig
import top.potmot.core.template.table.MysqlTableDefineGenerator
import top.potmot.core.template.table.PostgreTableDefineGenerator
import top.potmot.core.template.table.TableDefineGenerator
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


