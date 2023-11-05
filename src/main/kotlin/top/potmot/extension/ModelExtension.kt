package top.potmot.extension

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.babyfish.jimmer.jackson.ImmutableModule
import top.potmot.core.liquibase.createSql
import top.potmot.model.GenAssociation
import top.potmot.model.GenDataSource
import top.potmot.model.GenTable
import top.potmot.model.dto.GenAssociationModelInput
import top.potmot.model.dto.GenModelView
import top.potmot.model.dto.GenTableColumnsInput

private val mapper = ObjectMapper().registerModule(ImmutableModule())

fun GenModelView.createSql(dataSource: GenDataSource): String {
    val data = valueToData()
    return dataSource.createSql(data.first, data.second)
}

fun GenModelView.valueToData(): Pair<List<GenTableColumnsInput>, List<GenAssociationModelInput>>{
    val tables = mutableListOf<GenTableColumnsInput>()
    val associations = mutableListOf<GenAssociationModelInput>()

    val jsonNode = ObjectMapper().readTree(this.value)

    val cells = jsonNode.path("json").path("cells").toList()

    cells.forEach { cell ->
        if (cell.path("shape").textValue() == "edge") {
            TODO()
        } else if (cell.path("shape").textValue() == "model") {
            tables += cell.path("data").path("table").toTable()
        }
    }

    return Pair(tables, associations)
}

private fun JsonNode.toTable(): GenTableColumnsInput {
    return GenTableColumnsInput(mapper.readValue(this.toString(), GenTable::class.java))
}

private fun JsonNode.toAssociation(): GenAssociationModelInput {
    return GenAssociationModelInput(mapper.readValue(this.toString(), GenAssociation::class.java))
}
