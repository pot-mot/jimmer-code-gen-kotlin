package top.potmot.core.import

import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.DissociateAction
import schemacrawler.schema.Catalog
import schemacrawler.schema.Column
import schemacrawler.schema.ForeignKeyUpdateRule
import schemacrawler.schema.Schema
import schemacrawler.schema.Table
import top.potmot.enum.AssociationType
import top.potmot.enum.TableType
import top.potmot.model.GenAssociation
import top.potmot.model.GenColumn
import top.potmot.model.GenSchema
import top.potmot.model.GenTable
import top.potmot.model.by
import top.potmot.model.copy

fun Catalog.toGenSchemas(dataSourceId: Long): List<Pair<Schema, GenSchema>> {
    val catalog = this
    return this.schemas.map { schema ->
        val genSchema = schema.toGenSchema(dataSourceId).copy {
            this.tables = catalog.getTables(schema).map { table ->
                table.toGenTable()
            }
        }
        Pair(schema, genSchema)
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

            result +=
                new(GenAssociation::class).by {
                    this.sourceColumn = sourceColumn
                    this.targetColumn = targetColumn
                    this.associationType = AssociationType.MANY_TO_ONE
                    this.dissociateAction = it.deleteRule.toDissociateAction()
                    this.remark = columnRef.toString()
                }
        }
    }

    return result
}

fun ForeignKeyUpdateRule.toDissociateAction(): DissociateAction {
    return when(this) {
        ForeignKeyUpdateRule.noAction -> DissociateAction.NONE
        ForeignKeyUpdateRule.unknown -> DissociateAction.NONE
        ForeignKeyUpdateRule.cascade -> DissociateAction.DELETE
        ForeignKeyUpdateRule.setNull -> DissociateAction.SET_NULL
        ForeignKeyUpdateRule.setDefault -> DissociateAction.NONE
        ForeignKeyUpdateRule.restrict -> DissociateAction.NONE
    }
}

