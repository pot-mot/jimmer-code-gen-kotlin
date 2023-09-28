package top.potmot.extension

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
import top.potmot.enum.TableType
import top.potmot.error.DataSourceException
import top.potmot.model.*
import top.potmot.model.dto.GenDataSourceInput
import us.fatehi.utility.datasource.DatabaseConnectionSource
import us.fatehi.utility.datasource.DatabaseConnectionSources
import us.fatehi.utility.datasource.MultiUseUserCredentials
import java.util.regex.Pattern


fun GenDataSource.url(): String {
    return "jdbc:${this.type.name.lowercase()}://${this.host}:${this.port}"
}

fun GenDataSourceInput.test() {
    this.toEntity().toSource().close()
}

fun GenDataSource.toSource(): DatabaseConnectionSource {
    return DatabaseConnectionSources.newDatabaseConnectionSource(
        this.url(), MultiUseUserCredentials(this.username, this.password)
    ).test()
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

fun Catalog.toGenSchemas(dataSourceId: Long): List<GenSchema> {
    val catalog = this
    return this.schemas.map { schema ->
        schema.toGenSchema(dataSourceId).copy {
            this.tables = catalog.getTables(schema).map { table ->
                table.toGenTable()
            }
        }
    }
}

fun Schema.toGenSchema(dataSourceId: Long): GenSchema {
    val schema = this
    return new(GenSchema::class).by {
        this.dataSourceId = dataSourceId
        this.name = schema.fullName
    }
}

fun Table.toGenTable(
    schemaId: Long? = null,
    orderKey: Long? = null
): GenTable {
    val table = this
    return new(GenTable::class).by {
        schemaId?.let { this.schemaId = it }
        this.name = table.name
        this.comment = table.remarks
        this.type = TableType.fromValue(table.type.tableType)
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
        this.orderKey = index
        this.name = column.name
        this.type = column.type.name
        this.typeCode = column.type.javaSqlType.vendorTypeNumber
        this.displaySize = column.size.toLong()
        this.numericPrecision = column.decimalDigits.toLong()
        this.defaultValue = column.defaultValue
        this.comment = column.remarks
        this.isPk = column.isPartOfPrimaryKey
        this.isFk = column.isPartOfForeignKey
        this.isUnique = column.isPartOfUniqueIndex
        this.isAutoIncrement = column.isAutoIncremented
        this.isNotNull = !column.isNullable
    }
}
