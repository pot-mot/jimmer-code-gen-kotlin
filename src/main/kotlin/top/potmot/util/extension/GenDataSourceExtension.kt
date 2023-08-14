package top.potmot.util.extension

import org.babyfish.jimmer.kt.new
import schemacrawler.schema.Column
import schemacrawler.schema.Schema
import schemacrawler.schema.Table
import schemacrawler.schemacrawler.LimitOptionsBuilder
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder
import schemacrawler.tools.utility.SchemaCrawlerUtility
import top.potmot.constant.TableType
import top.potmot.model.GenColumn
import top.potmot.model.GenDataSource
import top.potmot.model.GenTable
import top.potmot.model.GenTableGroup
import top.potmot.model.by
import us.fatehi.utility.datasource.DatabaseConnectionSource
import us.fatehi.utility.datasource.DatabaseConnectionSources
import us.fatehi.utility.datasource.MultiUseUserCredentials
import java.util.regex.Pattern

fun GenDataSource.toSource(): DatabaseConnectionSource {
    return DatabaseConnectionSources.newDatabaseConnectionSource(
        this.url, MultiUseUserCredentials(this.username, this.password)
    )
}

fun GenDataSource.toTableGroup(groupId: Long? = null): GenTableGroup {
    val genDataSource = this
    val source = genDataSource.toSource()
    return new(GenTableGroup::class).by {
        parentId = groupId
        groupName = genDataSource.name
        children = source.getSchemas().map {
            it.toGenTableGroup(genDataSource.id, source)
        }
    }
}

fun DatabaseConnectionSource.getSchemas(
    schemaPattern: String? = null
): Collection<Schema> {
    val options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions()
        .withLimitOptions(
            LimitOptionsBuilder.builder()
                .includeSchemas(schemaPattern?.let { Pattern.compile(it) })
                .toOptions()
        )

    return SchemaCrawlerUtility.getCatalog(this, options).schemas
}

fun DatabaseConnectionSource.getTables(
    schema: Schema,
    tablePattern: String? = null,
): Collection<Table> {
    val options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions()
        .withLimitOptions(
            LimitOptionsBuilder.builder()
                .includeTables(tablePattern?.let { Pattern.compile(it) })
                .toOptions()
        )
    return SchemaCrawlerUtility.getCatalog(this, options).getTables(schema)
}

fun Schema.toGenTableGroup(dataSourceId: Long, source: DatabaseConnectionSource): GenTableGroup {
    val schema = this
    return new(GenTableGroup::class).by {
        groupName = schema.fullName
        tables = source.getTables(schema).map {
            it.toGenTable(dataSourceId)
        }
    }
}

fun Table.toGenTable(dataSourceId: Long): GenTable {
    val table = this
    return new(GenTable::class).by {
        this.dataSourceId = dataSourceId
        this.tableName = table.name
        this.tableComment = table.remarks
        this.tableType = TableType.fromValue(table.tableType.tableType)
        var index = 0L
        this.columns = table.columns.map {
            it.toGenColumn(index ++)
        }
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
