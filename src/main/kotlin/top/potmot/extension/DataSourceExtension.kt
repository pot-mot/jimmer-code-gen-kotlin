package top.potmot.extension

import top.potmot.enumeration.DataSourceType
import top.potmot.error.DataSourceException
import top.potmot.model.GenDataSource
import us.fatehi.utility.datasource.DatabaseConnectionSource
import us.fatehi.utility.datasource.DatabaseConnectionSources
import us.fatehi.utility.datasource.MultiUseUserCredentials

/**
 * 测试数据库连接。
 *
 * @return 返回测试后的数据库连接源（DatabaseConnectionSource）对象。
 * @throws DataSourceException 如果连接失败，则抛出DataSourceException异常。
 */
fun DatabaseConnectionSource.test(): DatabaseConnectionSource {
    try {
        this.get().close()
        return this
    } catch (e: Exception) {
        throw DataSourceException.connectFail("dataSource connect fail", e)
    }
}

fun GenDataSource.execute(schemaName: String, multiSql: String): IntArray {
    val connection =
        when (this.type) {
            DataSourceType.MySQL -> {
                this.toSource("/${schemaName}").get()
            }
            DataSourceType.PostgreSQL -> {
                this.toSource("?currentSchema=${schemaName}").get()
            }
        }

    try {
        connection.autoCommit = false

        val statement = connection.createStatement()

        multiSql.split(";").filter { it.isNotBlank() }.forEach {
            statement.addBatch(it.trim())
        }

        val result = statement.executeBatch()

        connection.commit()

        return result
    } catch (e: Exception) {
        connection.rollback()

        throw DataSourceException.sqlExecuteFail("dataSource execute fail", e)
    } finally {
        connection.close()
    }
}

/**
 * 从 GenDataSource 转换为 DatabaseConnectionSource
 *
 * @return 返回测试后的数据库连接源（DatabaseConnectionSource）对象。
 * @throws DataSourceException 如果转换失败，则抛出DataSourceException异常。
 */
fun GenDataSource.toSource(urlSuffix: String): DatabaseConnectionSource {
    return DatabaseConnectionSources.newDatabaseConnectionSource(
        this.url + urlSuffix,
        MultiUseUserCredentials(this.username, this.password)
    ).test()
}

fun GenDataSource.toSource(): DatabaseConnectionSource {
    return this.toSource("")
}

fun GenDataSource.test() {
    this.toSource().close()
}
