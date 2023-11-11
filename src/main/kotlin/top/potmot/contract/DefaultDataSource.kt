package top.potmot.contract

import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.DataSourceType.*
import top.potmot.model.dto.GenDataSourceTemplateView

val mysqlDataSourceTemplate = GenDataSourceTemplateView(
    name = "mysql",
    type = MySQL,
    host = "127.0.0.1",
    port = "3306",
    urlSuffix = "",
    username = "root",
    remark = "MySQL DataSource",
)

val postgreDataSourceTemplate = GenDataSourceTemplateView(
    name = "postgres",
    type = PostgreSQL,
    host = "127.0.0.1",
    port = "5432",
    urlSuffix = "/postgres",
    username = "postgres",
    remark = "PostgreSQL DataSource",
)


fun DataSourceType.dataSourceTemplate(): GenDataSourceTemplateView =
    when(this) {
        MySQL -> mysqlDataSourceTemplate
        PostgreSQL -> postgreDataSourceTemplate
    }
