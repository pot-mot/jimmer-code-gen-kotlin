package top.potmot.extension

import schemacrawler.schema.Catalog
import schemacrawler.schemacrawler.LimitOptionsBuilder
import schemacrawler.schemacrawler.LoadOptionsBuilder
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder
import schemacrawler.tools.utility.SchemaCrawlerUtility
import top.potmot.error.DataSourceException
import top.potmot.model.GenDataSource
import us.fatehi.utility.datasource.DatabaseConnectionSource
import us.fatehi.utility.datasource.DatabaseConnectionSources
import us.fatehi.utility.datasource.MultiUseUserCredentials
import java.util.regex.Pattern


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

/**
 * 获取数据库目录（Catalog）。
 *
 * @param schemaPattern 表模式的模式匹配字符串，默认为null。
 * @param tablePattern 表名的模式匹配字符串，默认为null。
 * @param withoutTable 是否排除表信息，默认为false。
 * @param withoutPrimaryKey 是否排除主键信息，默认为false。
 * @param withoutForeignKey 是否排除外键信息，默认为false。
 * @param withoutIndex 是否排除索引信息，默认为false。
 * @param withoutColumn 是否排除列信息，默认为false。
 * @param withoutRoutine 是否排除存储过程信息，默认为true。
 * @param withoutPrivilege 是否排除权限信息，默认为true。
 * @return 返回数据库目录（Catalog）对象。
 */
fun DatabaseConnectionSource.getCatalog(
    schemaPattern: String? = null,
    tablePattern: String? = null,
    withoutTable: Boolean = false,
    withoutPrimaryKey: Boolean = false,
    withoutForeignKey: Boolean = false,
    withoutIndex: Boolean = false,
    withoutColumn: Boolean = false,
    withoutRoutine: Boolean = true,
    withoutPrivilege: Boolean = true,
): Catalog {
    val limitBuilder = LimitOptionsBuilder.builder()
    schemaPattern?.let {
        limitBuilder.includeSchemas(Pattern.compile(it))
    }
    tablePattern?.let {
        limitBuilder.includeTables(Pattern.compile(it))
    }

    val schemaInfoBuilder = SchemaInfoLevelBuilder.builder()

    if (!withoutTable) {
        schemaInfoBuilder.setRetrieveTables(true)
        if (!withoutColumn) {
            schemaInfoBuilder.setRetrieveTableColumns(true)
        }
        if (!withoutPrimaryKey) {
            schemaInfoBuilder.setRetrievePrimaryKeys(true)
        }
        if (!withoutForeignKey) {
            schemaInfoBuilder.setRetrieveForeignKeys(true)
        }
        if (!withoutIndex) {
            schemaInfoBuilder.setRetrieveIndexes(true)
        }

        if (!withoutPrivilege) {
            schemaInfoBuilder.setRetrieveTablePrivileges(true)
            schemaInfoBuilder.setRetrieveTableColumnPrivileges(true)
        }
    }
    if (!withoutRoutine) {
        schemaInfoBuilder.setRetrieveRoutines(true)
    }

    return SchemaCrawlerUtility.getCatalog(
        this,
        SchemaCrawlerOptionsBuilder
            .newSchemaCrawlerOptions()
            .withLimitOptions(limitBuilder.toOptions())
            .withLoadOptions(LoadOptionsBuilder.builder().withSchemaInfoLevelBuilder(schemaInfoBuilder).toOptions())
    )
}
