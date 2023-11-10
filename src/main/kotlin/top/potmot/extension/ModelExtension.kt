package top.potmot.extension

import com.fasterxml.jackson.databind.JsonNode
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

fun GenModelView.valueToData(): Pair<List<GenTableColumnsInput>, List<GenAssociationModelInput>> {
    val tables = mutableListOf<GenTableColumnsInput>()
    val associations = mutableListOf<GenAssociationModelInput>()

    val jsonNode = mapper.readTree(this.value)

    val cells = jsonNode.path("json").path("cells").toList()

    cells.forEach { cell ->
        if (cell.path("shape").textValue() == "edge") {
            associations += cell.path("data").path("association").toAssociation()
        } else if (cell.path("shape").textValue() == "model") {
            tables += cell.path("data").path("table").toTable()
        }
    }

    return Pair(tables, associations)
}

private fun JsonNode.toTable(): GenTableColumnsInput {
    return mapper.readValue<GenTableColumnsInput>(this.toString())
}

private fun JsonNode.toAssociation(): GenAssociationModelInput {
    return mapper.readValue<GenAssociationModelInput>(this.toString())
}
