package top.potmot.model

import org.babyfish.jimmer.kt.new
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.dao.GenTableRepository
import top.potmot.model.input.GenTableInput

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestGenTable(
    @Autowired
    val genTableRepository: GenTableRepository
) {

    @Order(1)
    @Test
    fun save() {
        val genTableBeforeInsert = GenTableInput(
            tableName = "user",
            tableComment = "用户表",
            className = "User",
            packageName = "com.potmot",
            moduleName = "user",
            functionName = "用户",
        )
        val genTableInserted = genTableRepository.save(genTableBeforeInsert)
        println(genTableInserted)
        val genTableBeforeUpdate = new(GenTable::class).by(genTableInserted) {
            isAdd = false
            isQuery = false
        }
        val genTableUpdated = genTableRepository.save(genTableBeforeUpdate)
        println(genTableUpdated)
        genTableRepository.findAll().forEach {
            println(it)
        }
        genTableRepository.deleteById(genTableUpdated.id)
        genTableRepository.findAll().forEach {
            println(it)
        }
    }
}
