package top.potmot.model

import org.babyfish.jimmer.kt.new
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.constant.AssociationTypeEnum
import top.potmot.dao.GenTableAssociationRepository
import top.potmot.model.input.GenTableAssociationInput

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestGenTableAssociation(
    @Autowired
    val genTableAssociationRepository: GenTableAssociationRepository
) {

    @Order(1)
    @Test
    fun save() {
        val genTableAssociationBeforeInsert = GenTableAssociationInput(
            sourceColumnId = 1,
            sourceTableId = 1,
            targetColumnId = 1,
            targetTableId = 1,
        )
        val genTableAssociationInserted = genTableAssociationRepository.save(genTableAssociationBeforeInsert)
        println(genTableAssociationInserted)
        val genTableAssociationBeforeUpdate = new(GenTableAssociation::class).by(genTableAssociationInserted) {
            associationType = AssociationTypeEnum.MANY_TO_MANY
        }
        val genTableAssociationUpdated = genTableAssociationRepository.save(genTableAssociationBeforeUpdate)
        println(genTableAssociationUpdated)
        genTableAssociationRepository.findAll().forEach {
            println(it)
        }
        genTableAssociationRepository.deleteById(genTableAssociationUpdated.id)
        genTableAssociationRepository.findAll().forEach {
            println(it)
        }
    }
}
