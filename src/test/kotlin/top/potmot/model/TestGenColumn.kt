package top.potmot.model

import org.babyfish.jimmer.kt.new
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.*
import org.springframework.transaction.annotation.Transactional
import top.potmot.dao.GenColumnRepository
import java.sql.Types

@Transactional
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestGenColumn(
    @Autowired
    val genTableColumnRepository: GenColumnRepository
) {

    @Order(1)
    @Test
    fun testCRUD() {
        val genTableColumnBeforeInsert = new(GenColumn::class).by {
            tableId = 1
            name = "test_column"
            orderKey = 1
            typeCode = Types.VARCHAR
            type = "varchar"
            defaultValue = "测试列"
            comment = "test"
            isPk = true
            isAutoIncrement = true
        }
        val genTableColumnInserted = genTableColumnRepository.save(genTableColumnBeforeInsert)

        assertEquals(1, genTableColumnInserted.tableId)
        assert(genTableColumnInserted.isPk)
        assert(genTableColumnInserted.isAutoIncrement)
        assertEquals("测试列", genTableColumnInserted.defaultValue)

        val genColumnBeforeUpdate = new(GenColumn::class).by(genTableColumnInserted) {
            defaultValue = null
            isPk = false
            isAutoIncrement = false
        }

        val genTableColumnUpdated = genTableColumnRepository.save(genColumnBeforeUpdate)

        assertEquals(genTableColumnInserted.id, genTableColumnUpdated.id)
        assertEquals(1, genTableColumnUpdated.tableId)
        assert(!genTableColumnUpdated.isPk)
        assert(!genTableColumnUpdated.isAutoIncrement)
        assertEquals(null, genTableColumnUpdated.defaultValue)

        genTableColumnRepository.deleteById(genTableColumnUpdated.id)
    }
}
