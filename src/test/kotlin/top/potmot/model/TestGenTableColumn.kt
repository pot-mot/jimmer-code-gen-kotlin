package top.potmot.model

import org.babyfish.jimmer.kt.new
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.constant.QueryTypeEnum
import top.potmot.constant.SortDirectionEnum
import top.potmot.dao.GenTableColumnRepository
import top.potmot.model.input.GenTableColumnInput

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestGenTableColumn(
    @Autowired
    val genTableColumnRepository: GenTableColumnRepository
) {

    @Order(1)
    @Test
    fun save() {
        val genTableColumnBeforeInsert = GenTableColumnInput(
            tableId = 1,
            columnName = "test_column",
            columnSort = 1,
            columnType = "varchar",
            columnDefault = "测试列",
            columnComment = "test",
            fieldName = "test",
            fieldComment = "测试字段",
            fieldType = "String",
        )
        val genTableColumnInserted = genTableColumnRepository.save(genTableColumnBeforeInsert)
        val genTableColumnInsertedFull = genTableColumnRepository.findById(genTableColumnInserted.id).get()
        println(genTableColumnInsertedFull)
        val genTableColumnBeforeUpdate = new(GenTableColumn::class).by(genTableColumnInserted) {
            queryType = QueryTypeEnum.BETWEEN
            sortDirection = SortDirectionEnum.DESC
        }
        val genTableColumnUpdated = genTableColumnRepository.save(genTableColumnBeforeUpdate)
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
