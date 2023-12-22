package top.potmot.core.database.load

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import org.babyfish.jimmer.jackson.ImmutableModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.babyfish.jimmer.kt.unload
import top.potmot.constant.ModelShape
import top.potmot.model.GenTable
import top.potmot.model.GenTableIndex
import top.potmot.model.copy
import top.potmot.model.dto.GenAssociationInput
import top.potmot.model.dto.GenAssociationModelInput
import top.potmot.model.dto.GenModelView
import top.potmot.model.dto.GenTableColumnsInput

private val mapper = jacksonObjectMapper()
    .registerModule(ImmutableModule())
    .registerModule(JavaTimeModule())

fun valueToData(modelId: Long, value: String): Pair<List<GenTableColumnsInput>, List<GenAssociationModelInput>> {
    val tables = mutableListOf<GenTableColumnsInput>()
    val associations = mutableListOf<GenAssociationModelInput>()

    val jsonNode = mapper.readTree(value)

    val cells = jsonNode.path("json").path("cells").toList()

    cells.forEach { cell ->
        if (cell.path("shape").textValue() == ModelShape.ASSOCIATION_EDGE.name) {
            cell.path("data").path("association").takeIf { it.isMissingNode.not() }?.let {
                associations += it.toAssociation(modelId)
            }
        } else if (cell.path("shape").textValue() == ModelShape.TABLE_NODE.name) {
            cell.path("data").path("table").takeIf { it.isMissingNode.not() }?.let {
                tables += it.toTable(modelId)
            }
        }
    }

    return Pair(tables, associations)
}

fun GenModelView.valueToData(): Pair<List<GenTableColumnsInput>, List<GenAssociationModelInput>> =
    valueToData(this.id, this.value)

private fun JsonNode.toTable(modelId: Long): GenTableColumnsInput {
    if (this.isObject) {
        val node = this as ObjectNode
        node.put("modelId", modelId)
    }
    return mapper.readValue<GenTableColumnsInput>(this.toString())
}

private fun JsonNode.toAssociation(modelId: Long): GenAssociationModelInput {
    if (this.isObject) {
        val node = this as ObjectNode
        node.put("modelId", modelId)
        node.path("sourceTable").let {
            if (it.isObject) {
                val sourceTable = it as ObjectNode
                sourceTable.put("modelId", modelId)
            }
        }
        node.path("targetTable").let {
            if (it.isObject) {
                val targetTable = it as ObjectNode
                targetTable.put("modelId", modelId)
            }
        }
    }
    return mapper.readValue<GenAssociationModelInput>(this.toString())
}

fun GenTableColumnsInput.toInputPair(): Pair<GenTable, List<GenTableIndex>> {
    val indexes = this.indexes.map { it.toEntity() }

    val entity = this.toEntity().copy {
        unload(this, GenTable::indexes)
    }

    return Pair(entity, indexes)
}

fun GenTableIndex.toInput(table: GenTable): GenTableIndex =
    this.copy {
        this.columnIds = columns.map {
            val nameMatchColumns = table.columns.filter { column -> column.name == it.name }
            if (nameMatchColumns.size != 1) {
                throw RuntimeException("Load index [$name] fail: \nmatch name ${it.name} column more than one")
            }
            nameMatchColumns[0].id
        }
    }

fun GenAssociationModelInput.toInput(
    tables: List<GenTable>
): GenAssociationInput {
    val tableMap = tables.associate { it.name to Pair(it.id, it) }
    val sourceTablePair = tableMap[sourceTable.name]
        ?: throw RuntimeException("Model association [${name}] parse fail: \nsourceTable [${sourceTable.name}] not found")
    val sourceTableColumnsMap = sourceTablePair.second.columns.associate { it.name to it.id }

    val targetTablePair = tableMap[targetTable.name]
        ?: throw RuntimeException("Model association [${name}] parse fail: \ntargetTable [${targetTable.name}] not found")
    val targetTableColumnsMap = targetTablePair.second.columns.associate { it.name to it.id }

    val columnReferenceInputs = columnReferences.map {
        val sourceColumnId = sourceTableColumnsMap[it.sourceColumn.name]
            ?: throw RuntimeException("Model association [${name}] fail: \nsourceColumn [${it.sourceColumn.name}] not found")

        val targetColumnId = targetTableColumnsMap[it.targetColumn.name]
            ?: throw RuntimeException("Model association [${name}] fail: \ntargetColumn [${it.targetColumn.name}] not found")

        GenAssociationInput.TargetOf_columnReferences(
            sourceColumnId, targetColumnId
        )
    }

    return GenAssociationInput(
        name = name,
        associationType = associationType,
        dissociateAction = dissociateAction,
        sourceTableId = sourceTablePair.first,
        targetTableId = targetTablePair.first,
        fake = fake,
        columnReferences = columnReferenceInputs,
        remark = "",
        orderKey = 0,
    )
}
