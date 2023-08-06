package top.potmot.model

import org.babyfish.jimmer.kt.new
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.constant.QueryType
import top.potmot.constant.SortDirection
import top.potmot.dao.GenColumnRepository
import top.potmot.model.input.GenColumnInput

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestGenColumn(
    @Autowired
    val genTableColumnRepository: GenColumnRepository
) {

    @Order(1)
    @Test
    fun save() {
        val genTableColumnBeforeInsert = GenColumnInput(
            tableId = 1,
            columnName = "test_column",
            columnSort = 1,
            columnType = "varchar",
            columnDefault = "测试列",
            columnComment = "test",
        )
        val genTableColumnInserted = genTableColumnRepository.save(genTableColumnBeforeInsert)
        val genTableColumnInsertedFull = genTableColumnRepository.findById(genTableColumnInserted.id).get()
        println(genTableColumnInsertedFull)
        val genColumnBeforeUpdate = new(GenColumn::class).by(genTableColumnInserted) {
            columnDefault = "测试列修改了"
        }
        val genTableColumnUpdated = genTableColumnRepository.save(genColumnBeforeUpdate)
        println(genTableColumnUpdated)
        genTableColumnRepository.findAll().forEach {
            println(it)
        }
        genTableColumnRepository.deleteById(genTableColumnUpdated.id)
        genTableColumnRepository.findAll().forEach {
            println(it)
        }
    }
}
