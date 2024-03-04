package top.potmot.model.defaultValue

import top.potmot.enumeration.DataSourceType.H2
import top.potmot.enumeration.DataSourceType.MySQL
import top.potmot.enumeration.DataSourceType.PostgreSQL
import top.potmot.model.dto.GenDataSourceTemplateView

private val mysqlDefaultDataSource = GenDataSourceTemplateView(
    name = "mysql",
    url = "jdbc:mysql://localhost:3306",
    username = "root",
    remark = "MySQL DataSource",
)

private val postgreDefaultDataSource = GenDataSourceTemplateView(
    name = "postgres",
    url = "jdbc:postgresql://localhost:5432/postgres",
    username = "postgres",
    remark = "PostgreSQL DataSource",
)

private val h2DefaultDataSource = GenDataSourceTemplateView(
    name = "h2",
    url = "jdbc:h2:tcp://localhost:9092/default",
    username = "root",
    remark = "H2 DataSource",
)

fun defaultDataSources(): List<Pair<String, GenDataSourceTemplateView>> =
    listOf(
        MySQL.name to mysqlDefaultDataSource,
        PostgreSQL.name to postgreDefaultDataSource,
        H2.name to h2DefaultDataSource
    )
