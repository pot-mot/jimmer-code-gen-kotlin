package top.potmot.util.extension

import org.babyfish.jimmer.kt.new
import schemacrawler.schema.Catalog
import schemacrawler.schema.Column
import schemacrawler.schema.Schema
import schemacrawler.schema.Table
import schemacrawler.schemacrawler.LimitOptionsBuilder
import schemacrawler.schemacrawler.LoadOptionsBuilder
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder
import schemacrawler.tools.utility.SchemaCrawlerUtility
import top.potmot.constant.TableType
import top.potmot.error.DataSourceException
import top.potmot.model.GenColumn
import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.GenTable
import top.potmot.model.GenTableGroup
import top.potmot.model.by
import us.fatehi.utility.datasource.DatabaseConnectionSource
import us.fatehi.utility.datasource.DatabaseConnectionSources
import us.fatehi.utility.datasource.MultiUseUserCredentials
import java.util.regex.Pattern


fun GenDataSource.url(): String {
    return "jdbc:${this.type.name.lowercase()}://${this.host}:${this.port}"
}

fun GenDataSource.toSource(): DatabaseConnectionSource {
    return DatabaseConnectionSources.newDatabaseConnectionSource(
        this.url(), MultiUseUserCredentials(this.username, this.password)
    ).test()
}

fun String.removeQuotes(): String {
    return this.trim('\'', '"', '`')
}

fun Schema.toGenSchema(dataSourceId: Long): GenSchema {
    val schema = this
    return new(GenSchema::class).by {
        this.dataSourceId = dataSourceId
        this.name = schema.fullName
    }
}

fun GenSchema.toGenTableGroup(catalog: Catalog, parentId: Long? = null): GenTableGroup {
    val genSchema = this
    return new(GenTableGroup::class).by {
        this.parentId = parentId
        this.groupName = genSchema.name.removeQuotes()
        var index = 0L
        this.tables = catalog.tables.map {
            it.toGenTable(genSchema.id, index ++)
        }
    }
}

fun DatabaseConnectionSource.test(): DatabaseConnectionSource {
    try {
        this.get().close()
        return this
    } catch (e: Exception) {
        throw DataSourceException.connectFail("dataSource connect fail", e)
    }
}

fun DatabaseConnectionSource.getCatalog(
    schemaPattern: String? = null,
    tablePattern: String? = null,
    withoutTable: Boolean = false,
    withoutRoutine: Boolean = true,
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

fun Table.toGenTable(
    schemaId: Long,
    orderKey: Long? = null
): GenTable {
    val table = this
    return new(GenTable::class).by {
        this.schemaId = schemaId
        this.tableName = table.name
        this.tableComment = table.remarks
        this.tableType = TableType.fromValue(table.tableType.tableType)
        var index = 0L
        this.columns = table.columns.map {
            it.toGenColumn(index++)
        }
        orderKey?.let { this.orderKey = it }
    }
}

fun Column.toGenColumn(index: Long): GenColumn {
    val column = this
    return new(GenColumn::class).by {
        this.columnSort = index
        this.columnName = column.name
        this.columnType = column.type.name
        this.columnTypeCode = column.type.javaSqlType.vendorTypeNumber
        this.columnDisplaySize = column.size.toLong()
        this.columnPrecision = column.decimalDigits.toLong()
        this.columnDefault = column.defaultValue
        this.columnComment = column.remarks
        this.isPk = column.isPartOfPrimaryKey
        this.isFk = column.isPartOfForeignKey
        this.isUnique = column.isPartOfUniqueIndex
        this.isAutoIncrement = column.isAutoIncremented
        this.isNotNull = !column.isNullable
    }
}
