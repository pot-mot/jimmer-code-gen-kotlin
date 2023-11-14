package top.potmot.extension

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import org.babyfish.jimmer.jackson.ImmutableModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import top.potmot.core.liquibase.createSql
import top.potmot.model.GenDataSource
import top.potmot.model.dto.GenAssociationModelInput
import top.potmot.model.dto.GenModelView
import top.potmot.model.dto.GenTableColumnsInput

private val mapper = jacksonObjectMapper()
    .registerModule(ImmutableModule())
    .registerModule(JavaTimeModule())

fun GenModelView.createSql(dataSource: GenDataSource): String {
    val data = valueToData()
    return dataSource.createSql(data.first, data.second)
}

fun valueToData(modelId: Long, value: String): Pair<List<GenTableColumnsInput>, List<GenAssociationModelInput>> {
    val tables = mutableListOf<GenTableColumnsInput>()
    val associations = mutableListOf<GenAssociationModelInput>()

    val jsonNode = mapper.readTree(value)

    val cells = jsonNode.path("json").path("cells").toList()

    cells.forEach { cell ->
        if (cell.path("shape").textValue() == "edge") {
            cell.path("data").path("association").takeIf { it.isMissingNode.not() }?.let {
                associations += it.toAssociation(modelId)
            }
        } else if (cell.path("shape").textValue() == "model") {
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
        node.path("sourceColumn").path("table").let {
            if (it.isObject) {
                val sourceTable = it as ObjectNode
                sourceTable.put("modelId", modelId)
            }
        }
        node.path("targetColumn").path("table").let {
            if (it.isObject) {
                val targetTable = it as ObjectNode
                targetTable.put("modelId", modelId)
            }
        }
    }
    return mapper.readValue<GenAssociationModelInput>(this.toString())
}
