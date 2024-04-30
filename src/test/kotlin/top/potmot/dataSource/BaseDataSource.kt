package top.potmot.dataSource

import top.potmot.enumeration.DataSourceType
import top.potmot.entity.dto.GenDataSourceInput

val mysqlDataSource = GenDataSourceInput(
    name = "test",
    url = "jdbc:mysql://localhost:3306",
    type = DataSourceType.MySQL,
    username = "root",
    password = "root",
    remark = ""
)

val postgresDataSource = GenDataSourceInput(
    name = "test",
    url = "jdbc:postgresql://localhost:5432/postgres",
    type = DataSourceType.PostgreSQL,
    username = "postgres",
    password = "root",
    remark = ""
)

val h2DataSource = GenDataSourceInput(
    name = "test",
    url = "jdbc:h2:mem:jimmer_code_gen",
    type = DataSourceType.H2,
    username = "root",
    password = "root",
    remark = ""
)
