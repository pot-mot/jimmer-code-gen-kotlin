package top.potmot.model.modelParse

import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.model.load.ModelInputEntities
import top.potmot.core.model.load.parseGraphData
import top.potmot.utils.json.commonObjectMapper

class TestParse {
    @Test
    fun testAssociationsGraphDataParse() {
        val graphData = parseGraphData(1, GRAPH_DATA)

        assertEquals(9, graphData.tables.size)
        assertEquals(5, graphData.associations.size)

        assertEquals(
            commonObjectMapper.readValue<ModelInputEntities>(PARSE_RESULT).toString(),
            graphData.toString()
        )
    }
}
