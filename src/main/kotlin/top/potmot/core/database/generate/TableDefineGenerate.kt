package top.potmot.core.database.generate

import top.potmot.config.GenConfig
import top.potmot.core.database.generate.mysql.MysqlTableDefineGenerator
import top.potmot.core.database.generate.postgres.PostgreTableDefineGenerator
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.utils.identifier.IdentifierFilter

private val MYSQL_GENERATOR = MysqlTableDefineGenerator()

private val MYSQL_IDENTIFIER_FILTER = IdentifierFilter()

private val POSTGRE_GENERATOR = PostgreTableDefineGenerator()

private val POSTGRE_IDENTIFIER_FILTER = IdentifierFilter()

fun DataSourceType?.getTableDefineGenerator(): TableDefineGenerator =
    when (this ?: GenConfig.dataSourceType) {
        DataSourceType.MySQL -> MYSQL_GENERATOR
        DataSourceType.PostgreSQL -> POSTGRE_GENERATOR
    }

fun DataSourceType?.getIdentifierFilter(): IdentifierFilter =
    when (this ?: GenConfig.dataSourceType) {
        DataSourceType.MySQL -> MYSQL_IDENTIFIER_FILTER
        DataSourceType.PostgreSQL -> POSTGRE_IDENTIFIER_FILTER
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


