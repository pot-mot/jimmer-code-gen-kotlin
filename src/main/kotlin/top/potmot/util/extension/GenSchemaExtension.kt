package top.potmot.util.extension

import org.babyfish.jimmer.kt.new
import schemacrawler.schema.Catalog
import schemacrawler.schema.Column
import schemacrawler.schema.Schema
import schemacrawler.schema.Table
import schemacrawler.schemacrawler.LimitOptionsBuilder
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder
import schemacrawler.tools.utility.SchemaCrawlerUtility
import top.potmot.constant.TableType
import top.potmot.model.GenColumn
import top.potmot.model.GenDataSource
import top.potmot.model.GenSchema
import top.potmot.model.GenTable
import top.potmot.model.GenTableGroup
import top.potmot.model.by
import top.potmot.model.dto.GenSchemaInsertInput
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
    )
}

fun GenSchemaInsertInput.schemaPattern(): String {
    return if (this.name.startsWith("`") && this.name.endsWith("`")) {
        this.name
    } else if (this.name.contains("-")) {
        "`${this.name}`"
    } else {
        this.name
    }
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
        this.groupName = genSchema.name
        this.tables = catalog.tables.map {
            it.toGenTable(genSchema.id)
        }
    }
}

fun DatabaseConnectionSource.getCatalog(
    schemaPattern: String? = null,
    tablePattern: String? = null
): Catalog {
    val options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions()
        .withLimitOptions(
            LimitOptionsBuilder.builder()
                .includeSchemas(schemaPattern?.let { Pattern.compile(it) })
                .includeTables(tablePattern?.let { Pattern.compile(it) })
                .toOptions()
        )
    return SchemaCrawlerUtility.getCatalog(this, options)
}

fun Table.toGenTable(schemaId: Long): GenTable {
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
