package top.potmot.dataSource.identifier

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import top.potmot.core.database.generate.identifier.IdentifierTruncator

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class IdentifierTruncationTest {
    @Test
    @Order(1)
    fun testSameIdentifier() {
        val filter = IdentifierTruncator(8, 4)

        val id1 = "ABCDEFGH"
        val id2 = "ABCD_EFGH"
        val id3 = "ABCD_EFGH"

        val id4 = "ABCD_EFGH1"

        assertEquals(id1, filter.process(id1))

        assertEquals(filter.process(id2), filter.process(id3))

        assert(filter.process(id4).length == 8)
    }
}
