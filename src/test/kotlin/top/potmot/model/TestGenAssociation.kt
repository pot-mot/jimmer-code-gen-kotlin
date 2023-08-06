package top.potmot.model

import org.babyfish.jimmer.kt.new
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.constant.AssociationType
import top.potmot.dao.GenAssociationRepository
import top.potmot.model.input.GenAssociationInput

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestGenAssociation(
    @Autowired
    val genTableAssociationRepository: GenAssociationRepository
) {

    @Order(1)
    @Test
    fun save() {
        val genTableAssociationBeforeInsert = GenAssociationInput(
            sourcePropertyId = 1,
            sourceEntityId = 1,
            targetPropertyId = 1,
            targetEntityId = 1,
        )
        val genTableAssociationInserted = genTableAssociationRepository.save(genTableAssociationBeforeInsert)
        println(genTableAssociationInserted)
        val genAssociationBeforeUpdate = new(GenAssociation::class).by(genTableAssociationInserted) {
            associationType = AssociationType.MANY_TO_MANY
        }
        val genTableAssociationUpdated = genTableAssociationRepository.save(genAssociationBeforeUpdate)
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
