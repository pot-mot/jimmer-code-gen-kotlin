package top.potmot.core.database.load

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.readValue
import top.potmot.error.ModelLoadException
import top.potmot.model.GenTable
import top.potmot.model.dto.GenAssociationInput
import top.potmot.model.dto.GenAssociationModelInput
import top.potmot.model.dto.GenModelView
import top.potmot.model.dto.GenTableIndexInput
import top.potmot.model.dto.GenTableInput
import top.potmot.model.dto.GenTableModelInput
import top.potmot.utils.json.commonObjectMapper

data class ModelInputEntities (
    val tables: List<GenTableModelInput>,
    val associations: List<GenAssociationModelInput>,
)

fun parseGraphData(modelId: Long, graphData: String): ModelInputEntities {
    val tables = mutableListOf<GenTableModelInput>()
    val associations = mutableListOf<GenAssociationModelInput>()

    val jsonNode = commonObjectMapper.readTree(graphData)

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

    return ModelInputEntities(tables, associations)
}

fun GenModelView.getGraphEntities(): ModelInputEntities =
    parseGraphData(this.id, this.graphData)

private fun JsonNode.toTable(modelId: Long): GenTableModelInput {
    if (this.isObject) {
        val node = this as ObjectNode
        node.put("modelId", modelId)
    }
    return commonObjectMapper.readValue<GenTableModelInput>(this.toString())
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
    return commonObjectMapper.readValue<GenAssociationModelInput>(this.toString())
}

fun GenTableModelInput.toInput(
    enumNameIdMap: Map<String, Long>
): Pair<GenTableInput, List<GenTableModelInput.TargetOf_indexes>> {
    val entity = GenTableInput(
        name = name,
        comment = comment,
        type = type,
        remark = remark,
        modelId = modelId,
        columns = columns.map {
            GenTableInput.TargetOf_columns(
                name = it.name,
                typeCode = it.typeCode,
                overwriteByRaw = it.overwriteByRaw,
                rawType = it.rawType,
                typeNotNull = it.typeNotNull,
                dataSize = it.dataSize,
                numericPrecision = it.numericPrecision,
                defaultValue = it.defaultValue,
                comment = it.comment,
                partOfPk = it.partOfPk,
                autoIncrement = it.autoIncrement,
                businessKey = it.businessKey,
                logicalDelete = it.logicalDelete,
                orderKey = it.orderKey,
                remark = it.remark,
                enumId = enumNameIdMap[it.enum?.name]
            )
        }
    )

    return Pair(entity, indexes)
}

@Throws(ModelLoadException::class)
fun GenTableModelInput.TargetOf_indexes.toInput(
    tableId: Long,
    columnNameIdMap: Map<String, Long>
) =
    GenTableIndexInput(
        name = name,
        uniqueIndex = uniqueIndex,
        remark = remark,
        tableId = tableId,
        columnIds = columns.map {
            columnNameIdMap[it.name]
                ?: throw ModelLoadException.column("index [${name}] to input fail: \ncolumn [${it.name}] not find")
        }
    )

@Throws(ModelLoadException::class)
fun GenAssociationModelInput.toInput(
    tables: List<GenTable>
): GenAssociationInput {
    val tableMap = tables.associate { it.name to Pair(it.id, it) }
    val sourceTablePair = tableMap[sourceTable.name]
        ?: throw ModelLoadException.association("association [${name}] to input fail: \nsourceTable [${sourceTable.name}] not found")
    val sourceTableColumnsMap = sourceTablePair.second.columns.associate { it.name to it.id }

    val targetTablePair = tableMap[targetTable.name]
        ?: throw ModelLoadException.association("association [${name}] to input fail: \ntargetTable [${targetTable.name}] not found")
    val targetTableColumnsMap = targetTablePair.second.columns.associate { it.name to it.id }

    val columnReferenceInputs = columnReferences.mapIndexed { index, it ->
        val sourceColumnId = sourceTableColumnsMap[it.sourceColumn.name]
            ?: throw ModelLoadException.association("association [${name}] to input fail: \nsourceColumn [${it.sourceColumn.name}] not found")

        val targetColumnId = targetTableColumnsMap[it.targetColumn.name]
            ?: throw ModelLoadException.association("association [${name}] to input fail: \ntargetColumn [${it.targetColumn.name}] not found")

        GenAssociationInput.TargetOf_columnReferences(
            orderKey = index.toLong(),
            remark = "",
            sourceColumnId = sourceColumnId,
            targetColumnId = targetColumnId
        )
    }

    return GenAssociationInput(
        name = name,
        type = type,
        dissociateAction = dissociateAction,
        sourceTableId = sourceTablePair.first,
        targetTableId = targetTablePair.first,
        fake = fake,
        columnReferences = columnReferenceInputs,
        remark = "",
        updateAction = this.updateAction,
        deleteAction = this.deleteAction
    )
}
