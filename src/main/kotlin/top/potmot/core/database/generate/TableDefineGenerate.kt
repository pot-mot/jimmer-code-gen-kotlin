package top.potmot.core.database.generate

import top.potmot.config.GenConfig
import top.potmot.core.database.generate.mysql.MysqlTableDefineGenerator
import top.potmot.core.database.generate.postgres.PostgreTableDefineGenerator
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView

private val MYSQL_GENERATOR = MysqlTableDefineGenerator()

private val POSTGRE_GENERATOR = PostgreTableDefineGenerator()

fun DataSourceType?.getTableDefineGenerator(): TableDefineGenerator =
    when (this ?: GenConfig.dataSourceType) {
        DataSourceType.MySQL -> MYSQL_GENERATOR
        DataSourceType.PostgreSQL -> POSTGRE_GENERATOR
    }

fun Set<DataSourceType>.getTableDefineBuilder(): Map<DataSourceType, TableDefineGenerator> =
    associate { Pair(it, it.getTableDefineGenerator()) }

fun generateTableDefine(
    table: GenTableAssociationsView,
    dataSourceTypes: Collection<DataSourceType>
): List<Pair<String, String>> =
    dataSourceTypes.toSet().getTableDefineBuilder()
        .map { it.value.generate(table) }
        .distinct()

fun generateTableDefines(
    tables: Collection<GenTableAssociationsView>,
    dataSourceTypes: Collection<DataSourceType>
): List<Pair<String, String>> =
    dataSourceTypes.toSet().getTableDefineBuilder()
        .flatMap { it.value.generate(tables) }
        .distinct()


