package top.potmot.model

import org.babyfish.jimmer.kt.new
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import top.potmot.constant.TableType
import top.potmot.dao.GenTableRepository

@Transactional
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestGenTable(
    @Autowired
    val genTableRepository: GenTableRepository
) {

    @Order(1)
    @Test
    fun testCRUD() {
        val genTableBeforeInsert = new(GenTable::class).by {
            dataSourceId = 1
            tableName = "user"
            tableComment = "用户表"
            tableType = TableType.TABLE
        }
        val genTableInserted = genTableRepository.save(genTableBeforeInsert)
        println(genTableInserted)
        val genTableBeforeUpdate = new(GenTable::class).by(genTableInserted) {
            tableComment = "用户表修改了"
        }
        val genTableUpdated = genTableRepository.save(genTableBeforeUpdate)
        genTableRepository.deleteById(genTableUpdated.id)
    }
}
