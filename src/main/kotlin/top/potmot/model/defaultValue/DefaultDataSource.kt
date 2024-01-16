package top.potmot.model.defaultValue

import top.potmot.enumeration.DataSourceType.MySQL
import top.potmot.enumeration.DataSourceType.PostgreSQL
import top.potmot.model.dto.GenDataSourceTemplateView

private val mysqlDefaultDataSource = GenDataSourceTemplateView(
    name = "mysql",
    host = "127.0.0.1",
    port = "3306",
    urlSuffix = "",
    username = "root",
    remark = "MySQL DataSource",
)

private val postgreDefaultDataSource = GenDataSourceTemplateView(
    name = "postgres",
    host = "127.0.0.1",
    port = "5432",
    urlSuffix = "/postgres",
    username = "postgres",
    remark = "PostgreSQL DataSource",
)

fun defaultDataSources(): List<Pair<String, GenDataSourceTemplateView>> =
    listOf(
        MySQL.name to mysqlDefaultDataSource,
        PostgreSQL.name to postgreDefaultDataSource,
    )
