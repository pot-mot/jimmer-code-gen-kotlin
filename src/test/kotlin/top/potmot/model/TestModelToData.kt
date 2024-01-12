package top.potmot.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.core.database.load.parseGraphData

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestModelToData {
    @Test
    @Order(1)
    fun testEmptyData() {
        val data = parseGraphData(1, emptyValue)
        assert(data.first.isEmpty())
        assert(data.second.isEmpty())
    }

    @Test
    @Order(2)
    fun testValueData() {
        val valueData = parseGraphData(1, graphDataValue)
        assertEquals(11, valueData.first.size)
        assertEquals(5, valueData.second.size)
    }

    private val emptyValue = "{}"

    private val graphDataValue = getBaseModel().graphData!!
}
