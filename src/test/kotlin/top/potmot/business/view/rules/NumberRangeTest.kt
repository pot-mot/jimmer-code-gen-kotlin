package top.potmot.business.view.rules

import java.sql.Types
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.common.numberMax
import top.potmot.core.common.numberMin

class NumberRangeTest {
    @Test
    fun `test numberMin`() {
        assertEquals("0.0000000000", numberMin(numericPrecision = 10, dataSize = 20, typeCode = Types.DECIMAL))
    }

    @Test
    fun `test numberMax`() {
        assertEquals("999999999999999999.99", numberMax(dataSize = 20, numericPrecision = 2, typeCode = Types.DECIMAL))
    }
}
