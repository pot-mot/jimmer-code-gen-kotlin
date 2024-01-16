package top.potmot.core.database.load

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.babyfish.jimmer.jackson.ImmutableModule
import org.babyfish.jimmer.kt.unload
import top.potmot.error.ModelLoadException
import top.potmot.model.GenTable
import top.potmot.model.GenTableIndex
import top.potmot.model.copy
import top.potmot.model.dto.GenAssociationInput
import top.potmot.model.dto.GenAssociationModelInput
import top.potmot.model.dto.GenModelView
import top.potmot.model.dto.GenTableModelInput

private val mapper = jacksonObjectMapper()
    .registerModule(ImmutableModule())
    .registerModule(JavaTimeModule())

fun parseGraphData(modelId: Long, graphData: String): Pair<List<GenTableModelInput>, List<GenAssociationModelInput>> {
    val tables = mutableListOf<GenTableModelInput>()
    val associations = mutableListOf<GenAssociationModelInput>()

    val jsonNode = mapper.readTree(graphData)

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

fun GenModelView.getGraphEntities(): Pair<List<GenTableModelInput>, List<GenAssociationModelInput>> =
    parseGraphData(this.id, this.graphData)

private fun JsonNode.toTable(modelId: Long): GenTableModelInput {
    if (this.isObject) {
        val node = this as ObjectNode
        node.put("modelId", modelId)
    }
    return mapper.readValue<GenTableModelInput>(this.toString())
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

fun GenTableModelInput.toInputPart(enumNameIdMap: Map<String, Long>): Pair<GenTable, List<GenTableIndex>> {
    val columns = this.columns.map {input ->
        input.toEntity().copy {
            enumId = enumNameIdMap[input.enum?.name]
        }
    }

    val indexes = this.indexes.map { it.toEntity() }

    val entity = this.toEntity().copy {
        this.columns = columns
        unload(this, GenTable::indexes)
    }

    return Pair(entity, indexes)
}

fun GenTableIndex.toInput(columnNameIdMap: Map<String, Long>): GenTableIndex =
    this.copy {
        this.columnIds = columns.mapNotNull {
            columnNameIdMap[it.name]
        }
    }

fun GenAssociationModelInput.toInput(
    tables: List<GenTable>
): GenAssociationInput {
    val tableMap = tables.associate { it.name to Pair(it.id, it) }
    val sourceTablePair = tableMap[sourceTable.name]
        ?: throw ModelLoadException.association("association [${name}] recreate fail: \nsourceTable [${sourceTable.name}] not found")
    val sourceTableColumnsMap = sourceTablePair.second.columns.associate { it.name to it.id }

    val targetTablePair = tableMap[targetTable.name]
        ?: throw ModelLoadException.association("association [${name}] recreate fail: \ntargetTable [${targetTable.name}] not found")
    val targetTableColumnsMap = targetTablePair.second.columns.associate { it.name to it.id }

    val columnReferenceInputs = columnReferences.map {
        val sourceColumnId = sourceTableColumnsMap[it.sourceColumn.name]
            ?: throw ModelLoadException.association("association [${name}] recreate fail: \nsourceColumn [${it.sourceColumn.name}] not found")

        val targetColumnId = targetTableColumnsMap[it.targetColumn.name]
            ?: throw ModelLoadException.association("association [${name}] recreate fail: \ntargetColumn [${it.targetColumn.name}] not found")

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
