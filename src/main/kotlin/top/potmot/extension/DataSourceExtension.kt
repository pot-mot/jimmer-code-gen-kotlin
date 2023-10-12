package top.potmot.extension

import top.potmot.error.DataSourceException
import top.potmot.model.GenDataSource
import us.fatehi.utility.datasource.DatabaseConnectionSource
import us.fatehi.utility.datasource.DatabaseConnectionSources
import us.fatehi.utility.datasource.MultiUseUserCredentials


fun GenDataSource.url(): String {
    return "jdbc:${this.type.name.lowercase()}://${this.host}:${this.port}"
}

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

/**
 * 从 GenDataSource 转换为 DatabaseConnectionSource
 *
 * @return 返回测试后的数据库连接源（DatabaseConnectionSource）对象。
 * @throws DataSourceException 如果转换失败，则抛出DataSourceException异常。
 */
fun GenDataSource.toSource(): DatabaseConnectionSource {
    return DatabaseConnectionSources.newDatabaseConnectionSource(
        this.url(), MultiUseUserCredentials(this.username, this.password)
    ).test()
}

fun GenDataSource.test() {
    this.toSource().close()
}
