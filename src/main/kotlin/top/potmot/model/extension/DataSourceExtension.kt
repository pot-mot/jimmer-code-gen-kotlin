package top.potmot.model.extension

import top.potmot.enumeration.DataSourceType
import top.potmot.error.DataSourceException
import top.potmot.model.GenDataSource
import top.potmot.utils.sql.SQLExecuteResult
import top.potmot.utils.sql.execute
import us.fatehi.utility.datasource.DatabaseConnectionSource
import us.fatehi.utility.datasource.DatabaseConnectionSources
import us.fatehi.utility.datasource.MultiUseUserCredentials

/**
 * 测试数据库连接。
 *
 * @return 返回测试后的数据库连接源（DatabaseConnectionSource）对象。
 * @throws DataSourceException 如果连接失败，则抛出DataSourceException异常。
 */
@Throws(DataSourceException.ConnectFail::class)
fun DatabaseConnectionSource.test(): DatabaseConnectionSource {
    try {
        get().close()
        return this
    } catch (e: Exception) {
        throw DataSourceException.connectFail("dataSource connect fail", e)
    }
}

@Throws(DataSourceException.SqlExecuteFail::class)
fun GenDataSource.execute(
    schemaName: String,
    sql: String,
    log: Boolean = false,
    ignoreExecuteFail: Boolean = false
): List<SQLExecuteResult> {
    val connection = toSource().get()

    try {
        val useSchema = when(type) {
            DataSourceType.MySQL, DataSourceType.H2 -> "USE `$schemaName`;"
            DataSourceType.PostgreSQL -> "SET SEARCH_PATH TO \"$schemaName\";"
        }

        return connection.execute("$useSchema $sql", log, ignoreExecuteFail)
    } catch (e: Exception) {
        connection.rollback()

        throw DataSourceException.sqlExecuteFail("dataSource execute fail", e)
    } finally {
        connection.close()
    }
}

/**
 * 从 GenDataSource 转换为 schema crawler 的 DatabaseConnectionSource
 */
@Throws(DataSourceException.ConnectFail::class)
fun GenDataSource.toSource(urlSuffix: String): DatabaseConnectionSource {
    return DatabaseConnectionSources.newDatabaseConnectionSource(
        url + urlSuffix,
        MultiUseUserCredentials(this.username, this.password)
    ).test()
}

@Throws(DataSourceException.ConnectFail::class)
fun GenDataSource.toSource(): DatabaseConnectionSource {
    return this.toSource("")
}

@Throws(DataSourceException.ConnectFail::class)
fun GenDataSource.test() {
    this.toSource().close()
}
