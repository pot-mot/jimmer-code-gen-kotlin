package top.potmot.core.database.load

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
import top.potmot.core.database.meta.toColumnReferenceMeta
import top.potmot.enumeration.AssociationType
import top.potmot.enumeration.TableType
import top.potmot.error.DataSourceLoadException
import top.potmot.model.GenTable
import top.potmot.model.dto.GenAssociationInput
import top.potmot.model.dto.GenSchemaInput
import top.potmot.model.dto.GenSchemaPreview
import top.potmot.model.dto.GenTableIndexInput
import us.fatehi.utility.datasource.DatabaseConnectionSource
import java.util.regex.Pattern

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
            schemaInfoBuilder.setRetrieveColumnDataTypes(true)
            schemaInfoBuilder.setRetrieveAdditionalColumnAttributes(true)
            schemaInfoBuilder.setRetrieveAdditionalColumnMetadata(true)
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

fun Schema.toView(
    dataSourceId: Long,
) = GenSchemaPreview(
    dataSourceId = dataSourceId,
    name = this.fullName,
    remark = this.remarks,
)

fun Schema.toInput(
    catalog: Catalog,
    dataSourceId: Long,
) = GenSchemaInput(
    dataSourceId = dataSourceId,
    name = fullName,
    remark = remarks,
    tables = catalog.getTables(this).map {
        it.toInput()
    }
)

fun Table.toInput() = GenSchemaInput.TargetOf_tables(
    name = this.name,
    comment = remarks,
    type = TableType.fromValue(type.tableType),
    remark = "",
    columns = columns.mapIndexed { index, it ->
        it.toInput(index.toLong())
    }
)

fun Column.toInput(
    orderKey: Long
) = GenSchemaInput.TargetOf_tables.TargetOf_columns_2(
    name = name,
    typeCode = columnDataType.javaSqlType.vendorTypeNumber,
    overwriteByRaw = false,
    rawType = columnDataType.name,
    typeNotNull = !isNullable,
    dataSize = size.toLong(),
    numericPrecision = decimalDigits.toLong(),
    defaultValue = defaultValue,
    comment = remarks,
    partOfPk = isPartOfPrimaryKey,
    autoIncrement = isAutoIncremented,
    idGeneration = false,
    businessKey = false,
    logicalDelete = false,
    orderKey = orderKey,
    remark = "",
)

/**
 * 根据 Table 中的外键转换 Association
 * 要求 GenTable 已经 save，具有 columns，columns 具有 id 与 name
 */
fun ForeignKey.toInput(
    tableNameMap: Map<String, GenTable>
): GenAssociationInput {
    val columnReferences = mutableListOf<GenAssociationInput.TargetOf_columnReferences>()

    var sourceTableId: Long? = null
    var targetTableId: Long? = null

    this.columnReferences.forEachIndexed { index, columnRef ->
        columnRef.toColumnReferenceMeta(tableNameMap)?.let { meta ->
            columnReferences +=
                GenAssociationInput.TargetOf_columnReferences(
                    orderKey = index.toLong(),
                    remark = "",
                    sourceColumnId = meta.source.column.id,
                    targetColumnId = meta.target.column.id,
                )

            if (sourceTableId != null && sourceTableId != meta.source.table.id) {
                throw DataSourceLoadException.association(
                    "Convert foreign key [${name}] to association fail: \n" +
                            "source table not match: \n" +
                            "association table not equals meta table [${meta.source.table}]"
                )
            }
            sourceTableId = meta.target.table.id

            if (targetTableId != null && targetTableId != meta.target.table.id) {
                throw DataSourceLoadException.association(
                    "Convert foreign key [${name}] to association fail: \n" +
                            "target table not match: \n" +
                            "association table not equals meta table [${meta.target.table}]"
                )
            }
            targetTableId = meta.target.table.id
        }
    }

    return GenAssociationInput(
        name = name,
        type = AssociationType.MANY_TO_ONE,
        sourceTableId = sourceTableId!!,
        targetTableId = targetTableId!!,
        dissociateAction = deleteRule.toDissociateAction(),
        updateAction = updateRule.toString(),
        deleteAction = deleteRule.toString(),
        fake = false,
        remark = "",
        columnReferences = columnReferences,
    )
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

@Throws(DataSourceLoadException::class)
fun Index.toInput(table: GenTable): GenTableIndexInput? {
    val columnIds = columns.map {
        val nameMatchColumns = table.columns.filter { column -> column.name == it.name }
        if (nameMatchColumns.size != 1) {
            throw DataSourceLoadException.index("Load index [$name] fail: \nmatch name ${it.name} column more than one in table [${table.name}]")
        }
        nameMatchColumns[0].id
    }

    // 当索引唯一且所有列均为主键列时，排除
    if (this.isUnique && columnIds.toSortedSet() == table.columns.filter { it.partOfPk }.map { it.id }.toSortedSet()) {
        return null
    }

    return GenTableIndexInput(
        name = name,
        uniqueIndex = isUnique,
        tableId = table.id,
        remark = remarks,
        columnIds = columnIds,
    )
}
