package top.potmot.model.modelParse

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.core.database.load.parseGraphData
import top.potmot.utils.json.prettyObjectMapper


@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
class TestParse {
    @Test
    fun testAssociationsGraphDataParse() {
        val entities = parseGraphData(1, GRAPH_DATA)

        assertEquals(9, entities.tables.size)
        assertEquals(5, entities.associations.size)

        assertEquals(
            PARSE_RESULT.trim(),
            prettyObjectMapper.writeValueAsString(entities).trim()
        )
    }
}
