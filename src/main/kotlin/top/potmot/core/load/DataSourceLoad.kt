package top.potmot.core.load

import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.DissociateAction
import schemacrawler.schema.Catalog
import schemacrawler.schema.Column
import schemacrawler.schema.ForeignKeyUpdateRule
import schemacrawler.schema.Schema
import schemacrawler.schema.Table
import schemacrawler.schemacrawler.LimitOptionsBuilder
import schemacrawler.schemacrawler.LoadOptionsBuilder
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder
import schemacrawler.tools.utility.SchemaCrawlerUtility
import top.potmot.enum.AssociationType
import top.potmot.enum.TableType
import top.potmot.model.GenAssociation
import top.potmot.model.GenColumn
import top.potmot.model.GenSchema
import top.potmot.model.GenTable
import top.potmot.model.by
import us.fatehi.utility.datasource.DatabaseConnectionSource
import java.util.regex.Pattern

/**
 * 获取数据库目录（Catalog）
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

fun Catalog.getSchemas(
    dataSourceId: Long,
    withTable: Boolean = true,
    withColumn: Boolean = true
): List<Pair<Schema, GenSchema>> =
    schemas.map { schema ->
        val genSchema =
            schema.toGenSchema(
                dataSourceId,
                this,
                withTable,
                withColumn
            )
        Pair(schema, genSchema)
    }

private fun Schema.toGenSchema(
    dataSourceId: Long,
    catalog: Catalog,
    withTable: Boolean = true,
    withColumn: Boolean = true
): GenSchema {
    val schema = this
    return new(GenSchema::class).by {
        this.dataSourceId = dataSourceId
        this.name = schema.fullName

        if (withTable) {
            tables = catalog.getTables(schema).map {
                it.toGenTable(withColumn = withColumn)
            }
        }
    }
}

private fun Table.toGenTable(
    schemaId: Long? = null,
    orderKey: Long? = null,
    withColumn: Boolean = true
): GenTable {
    val table = this
    return new(GenTable::class).by {
        schemaId?.let { this.schemaId = it }
        this.name = table.name
        this.comment = table.remarks
        this.type = TableType.fromValue(table.type.tableType)
        orderKey?.let { this.orderKey = it }

        if (withColumn) {
            var index = 0L
            this.columns = table.columns.map {
                it.toGenColumn(index++)
            }
        }
    }
}

private fun Column.toGenColumn(
    index: Long
): GenColumn {
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

fun Table.getFkAssociation(schemaId: Long): List<GenAssociation> {
    val result = mutableListOf<GenAssociation>()

    this.foreignKeys.forEach {
        it.columnReferences.forEach { columnRef ->
            val fkColumnNameList = columnRef.foreignKeyColumn.fullName.split(".").reversed()
            val pkColumnNameList = columnRef.primaryKeyColumn.fullName.split(".").reversed()

            val sourceColumn = new(GenColumn::class).by {
                name = fkColumnNameList[0]
                table = new(GenTable::class).by {
                    name = fkColumnNameList[1]
                    this.schemaId = schemaId
                    type = TableType.TABLE
                }
            }

            val targetColumn = new(GenColumn::class).by {
                name = pkColumnNameList[0]
                table = new(GenTable::class).by {
                    name = pkColumnNameList[1]
                    this.schemaId = schemaId
                    type = TableType.TABLE
                }
            }

            val type =
                if (columnRef.foreignKeyColumn.isPartOfUniqueIndex) AssociationType.ONE_TO_ONE else AssociationType.MANY_TO_ONE

            result +=
                new(GenAssociation::class).by {
                    this.sourceColumn = sourceColumn
                    this.targetColumn = targetColumn
                    this.associationType = type
                    this.dissociateAction = it.deleteRule.toDissociateAction()
                    this.remark = columnRef.toString()
                }
        }
    }

    return result
}

private fun ForeignKeyUpdateRule.toDissociateAction(): DissociateAction =
    when (this) {
        ForeignKeyUpdateRule.noAction -> DissociateAction.NONE
        ForeignKeyUpdateRule.unknown -> DissociateAction.NONE
        ForeignKeyUpdateRule.cascade -> DissociateAction.DELETE
        ForeignKeyUpdateRule.setNull -> DissociateAction.SET_NULL
        ForeignKeyUpdateRule.setDefault -> DissociateAction.NONE
        ForeignKeyUpdateRule.restrict -> DissociateAction.NONE
    }

