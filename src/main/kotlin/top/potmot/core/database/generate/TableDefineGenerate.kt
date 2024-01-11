package top.potmot.core.database.generate

import top.potmot.config.GlobalGenConfig
import top.potmot.core.database.generate.mysql.MysqlTableDefineGenerator
import top.potmot.core.database.generate.postgres.PostgreTableDefineGenerator
import top.potmot.enumeration.DataSourceType
import top.potmot.utils.identifier.IdentifierFilter

private val MYSQL_GENERATOR = MysqlTableDefineGenerator()


private val POSTGRE_GENERATOR = PostgreTableDefineGenerator()

fun DataSourceType?.getTableDefineGenerator(): TableDefineGenerator =
    when (this ?: GlobalGenConfig.dataSourceType) {
        DataSourceType.MySQL -> MYSQL_GENERATOR
        DataSourceType.PostgreSQL -> POSTGRE_GENERATOR
    }

private val MYSQL_IDENTIFIER_FILTER = IdentifierFilter()

private val POSTGRE_IDENTIFIER_FILTER = IdentifierFilter()

fun DataSourceType?.getIdentifierFilter(): IdentifierFilter =
    when (this ?: GlobalGenConfig.dataSourceType) {
        DataSourceType.MySQL -> MYSQL_IDENTIFIER_FILTER
        DataSourceType.PostgreSQL -> POSTGRE_IDENTIFIER_FILTER
    }


