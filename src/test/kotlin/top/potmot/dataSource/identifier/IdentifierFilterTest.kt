package top.potmot.dataSource.identifier

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.core.database.generate.identifier.IdentifierFilter

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class IdentifierFilterTest {
    @Test
    @Order(1)
    fun testSameIdentifier() {
        val filter = IdentifierFilter(8, 4)

        val id1 = "ABCDEFGH"
        val id2 = "ABCD_EFGH"
        val id3 = "ABCD_EFGH"

        val id4 = "ABCD_EFGH1"

        assert(id1 == filter.getIdentifier(id1))

        assertEquals(filter.getIdentifier(id2), filter.getIdentifier(id3))

        assert(filter.getIdentifier(id4).length == 8)
    }
}
