package top.potmot.model.defaultValue

import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.DataSourceType.*
import top.potmot.model.dto.GenDataSourceTemplateView

private val mysqlDefaultDataSource = GenDataSourceTemplateView(
    name = "mysql",
    type = MySQL,
    host = "127.0.0.1",
    port = "3306",
    urlSuffix = "",
    username = "root",
    remark = "MySQL DataSource",
)

private val postgreDefaultDataSource = GenDataSourceTemplateView(
    name = "postgres",
    type = PostgreSQL,
    host = "127.0.0.1",
    port = "5432",
    urlSuffix = "/postgres",
    username = "postgres",
    remark = "PostgreSQL DataSource",
)

fun defaultDataSourceMap(): Map<DataSourceType, GenDataSourceTemplateView> =
    mapOf(
        MySQL to mysqlDefaultDataSource,
        PostgreSQL to postgreDefaultDataSource,
    )

fun defaultDataSource(type: DataSourceType): GenDataSourceTemplateView =
    when (type) {
        MySQL -> mysqlDefaultDataSource
        PostgreSQL -> postgreDefaultDataSource
    }
