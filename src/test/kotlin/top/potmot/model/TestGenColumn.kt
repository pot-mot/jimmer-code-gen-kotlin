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
            columnName = "test_column"
            columnSort = 1
            columnTypeCode = Types.VARCHAR
            columnType = "varchar"
            columnDefault = "测试列"
            columnComment = "test"
            isPk = true
            isAutoIncrement = true
        }
        val genTableColumnInserted = genTableColumnRepository.save(genTableColumnBeforeInsert)

        assertEquals(1, genTableColumnInserted.tableId)
        assert(genTableColumnInserted.isPk)
        assert(genTableColumnInserted.isAutoIncrement)
        assertEquals("测试列", genTableColumnInserted.columnDefault)

        val genColumnBeforeUpdate = new(GenColumn::class).by(genTableColumnInserted) {
            columnDefault = null
            isPk = false
            isAutoIncrement = false
        }

        val genTableColumnUpdated = genTableColumnRepository.save(genColumnBeforeUpdate)

        assertEquals(genTableColumnInserted.id, genTableColumnUpdated.id)
        assertEquals(1, genTableColumnUpdated.tableId)
        assert(!genTableColumnUpdated.isPk)
        assert(!genTableColumnUpdated.isAutoIncrement)
        assertEquals(null, genTableColumnUpdated.columnDefault)

        genTableColumnRepository.deleteById(genTableColumnUpdated.id)
    }
}
