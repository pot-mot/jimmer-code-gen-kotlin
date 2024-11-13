package top.potmot.view.rules

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.meta.rules.numberMax
import top.potmot.core.business.view.generate.meta.rules.numberMin
import top.potmot.core.business.view.generate.meta.rules.numberPrecision
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties.TargetOf_column
import java.time.LocalDateTime

class NumberRangeTest {
    private val baseProperty = TargetOf_properties(
        id = 0,
        createdTime = LocalDateTime.now(),
        modifiedTime = LocalDateTime.now(),
        name = "",
        comment = "",
        type = "",
        remark = "",
        orderKey = 0,
        entityId = 0,
    )

    @Test
    fun `test numberPrecision when column is not null`() {
        val property = baseProperty.copy(
            column = TargetOf_column(numericPrecision = 10, dataSize = 20)
        )
        assertEquals(10, property.numberPrecision)
    }

    @Test
    fun `test numberPrecision when column is null`() {
        val property = baseProperty.copy(
            column = null
        )
        assertEquals(null, property.numberPrecision)
    }

    @Test
    fun `test numberMin when column is not null`() {
        val property = baseProperty.copy(
            column = TargetOf_column(numericPrecision = 10, dataSize = 20)
        )
        assertEquals(0.0, property.numberMin)
    }

    @Test
    fun `test numberMin when column is null`() {
        val property = baseProperty.copy(
            column = null
        )
        assertEquals(null, property.numberMin)
    }

    @Test
    fun `test numberMax when column is not null and within range`() {
        val property = baseProperty.copy(
            column = TargetOf_column(dataSize = 20, numericPrecision = 2)
        )
        assertEquals(999999999999999.99, property.numberMax)
    }

    @Test
    fun `test numberMax when column is not null and exceeds range`() {
        val property = baseProperty.copy(
            column = TargetOf_column(dataSize = 30, numericPrecision = 10)
        )
        assertEquals(999999999999999.9999999999, property.numberMax)
    }

    @Test
    fun `test numberMax when column is null`() {
        val property = baseProperty.copy(
            column = null
        )
        assertEquals(null, property.numberMax)
    }
}
