package top.potmot.core.database.load

import org.babyfish.jimmer.ImmutableObjects
import org.babyfish.jimmer.ImmutableObjects.isLoaded
import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.DissociateAction
import schemacrawler.schema.Catalog
import schemacrawler.schema.Column
import schemacrawler.schema.ForeignKey
import schemacrawler.schema.ForeignKeyUpdateRule
import schemacrawler.schema.Index
import schemacrawler.schema.Schema
import schemacrawler.schema.Table
import schemacrawler.schemacrawler.LimitOptionsBuilder
import schemacrawler.schemacrawler.LoadOptionsBuilder
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder
import schemacrawler.schemacrawler.SchemaInfoLevelBuilder
import schemacrawler.tools.utility.SchemaCrawlerUtility
import top.potmot.core.meta.getMeta
import top.potmot.enumeration.AssociationType
import top.potmot.enumeration.TableType
import top.potmot.model.GenAssociation
import top.potmot.model.GenAssociationProps
import top.potmot.model.GenColumn
import top.potmot.model.GenColumnReference
import top.potmot.model.GenSchema
import top.potmot.model.GenTable
import top.potmot.model.GenTableIndex
import top.potmot.model.by
import top.potmot.model.copy
import us.fatehi.utility.datasource.DatabaseConnectionSource
import java.util.regex.Pattern
import kotlin.RuntimeException

/**
 * 获取数据库目录（Catalog）
 *
 * @param schemaPattern 表模式的模式匹配字符串，默认为null。
 * @param tablePattern 表名的模式匹配字符串，默认为null。
 * @param withTable 是否携带表信息，默认为 true。
 * @param withPrimaryKey 是否携带主键信息，默认为 true。
 * @param withForeignKey 是否携带外键信息，默认为 true。
 * @param withIndex 是否携带索引信息，默认为 true。
 * @param withColumn 是否携带列信息，默认为 true。
 * @param withPrivilege 是否携带权限信息，默认为 true。
 * @param withRoutine 是否携带存储过程信息，默认为 false。
 * @return 返回数据库目录（Catalog）对象。
 */
fun DatabaseConnectionSource.getCatalog(
    schemaPattern: String? = null,
    tablePattern: String? = null,
    withTable: Boolean = true,
    withColumn: Boolean = true,
    withPrimaryKey: Boolean = true,
    withForeignKey: Boolean = true,
    withIndex: Boolean = true,
    withPrivilege: Boolean = true,
    withRoutine: Boolean = false,
): Catalog {
    val limitBuilder = LimitOptionsBuilder.builder()
    schemaPattern?.let {
        limitBuilder.includeSchemas(Pattern.compile(it))
    }
    tablePattern?.let {
        limitBuilder.includeTables(Pattern.compile(it))
    }

    val schemaInfoBuilder = SchemaInfoLevelBuilder.builder()

    if (withTable) {
        schemaInfoBuilder.setRetrieveTables(true)
        if (withColumn) {
            schemaInfoBuilder.setRetrieveTableColumns(true)
        }
        if (withPrimaryKey) {
            schemaInfoBuilder.setRetrievePrimaryKeys(true)
        }
        if (withForeignKey) {
            schemaInfoBuilder.setRetrieveForeignKeys(true)
        }
        if (withIndex) {
            schemaInfoBuilder.setRetrieveIndexes(true)
        }
    }

    if (withPrivilege) {
        schemaInfoBuilder.setRetrieveTablePrivileges(true)
        schemaInfoBuilder.setRetrieveTableColumnPrivileges(true)
    }

    if (withRoutine) {
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

fun Schema.toGenSchema(
    catalog: Catalog,
    dataSourceId: Long,
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

fun Table.toGenTable(
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

fun Column.toGenColumn(
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
        this.partOfPk = column.isPartOfPrimaryKey
        this.autoIncrement = column.isAutoIncremented
        this.typeNotNull = !column.isNullable
    }
}

/**
 * 根据 Table 中的外键转换 Association
 * 要求 GenTable 已经 save，具有 columns，columns 具有 id 与 name
 */
fun Table.getFkAssociations(tableNameMap: Map<String, GenTable>): List<GenAssociation> =
    foreignKeys.map { it.toGenAssociation(tableNameMap) }

private fun ForeignKey.toGenAssociation(
    tableNameMap: Map<String, GenTable>
): GenAssociation {
    var association = new(GenAssociation::class).by {
        this.name = this@toGenAssociation.name
        this.associationType = AssociationType.MANY_TO_ONE
        this.dissociateAction = deleteRule.toDissociateAction()
        this.fake = false
    }

    val columnReferences = mutableListOf<GenColumnReference>()

    this.columnReferences.forEach { columnRef ->
        columnRef.getMeta(tableNameMap)?.let {
            columnReferences +=
                new(GenColumnReference::class).by {
                    this.sourceColumnId = it.source.column.id
                    this.targetColumnId = it.target.column.id
                    this.remark = columnRef.toString()
                }

            association = association.copy {
                if (!isLoaded(this, GenAssociationProps.SOURCE_TABLE)) {
                    this.sourceTable = it.source.table
                } else if (this.sourceTable.id != it.source.table.id) {
                    throw RuntimeException("Convert foreign key [${association.name}] to association fail: \nsource table not match: \n${this.sourceTable} not equals ${it.source.table}")
                }

                if (!isLoaded(this, GenAssociationProps.TARGET_TABLE)) {
                    this.targetTable = it.target.table
                } else if (this.targetTable.id != it.target.table.id) {
                    throw RuntimeException("Convert foreign key [${association.name}] to association fail: \ntarget table not match: \n${this.targetTable} not equals ${it.target.table}")
                }
            }
        }
    }

    return association.copy { this.columnReferences = columnReferences }
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

fun Table.getGenIndexes(table: GenTable): List<GenTableIndex> =
    indexes.mapNotNull { it.toGenTableIndex(table) }

private fun Index.toGenTableIndex(table: GenTable): GenTableIndex? {
    val columnIds = columns.map {
        val nameMatchColumns = table.columns.filter { column -> column.name == it.name }
        if (nameMatchColumns.size != 1) {
            throw RuntimeException("Load index [$name] fail: \nmatch name ${it.name} column more than one")
        }
        nameMatchColumns[0].id
    }

    // 当索引唯一且所有列均为主键列时，排除
    if (this.isUnique && columnIds.toSortedSet() == table.columns.filter { it.partOfPk }.map { it.id }.toSortedSet()) {
        return null
    }

    return new(GenTableIndex::class).by {
        this.name = this@toGenTableIndex.name
        this.uniqueIndex = this@toGenTableIndex.isUnique
        this.tableId = table.id
        this.columnIds = columnIds
    }
}
