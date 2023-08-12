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

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestGenAssociation(
    @Autowired
    val genTableAssociationRepository: GenAssociationRepository
) {

    @Order(1)
    @Test
    fun testCRUD() {
        val genTableAssociationBeforeInsert = new(GenAssociation::class).by {
            sourceColumnId = 1
            targetColumnId = 1
        }
        val genTableAssociationInserted = genTableAssociationRepository.save(genTableAssociationBeforeInsert)
        val genAssociationBeforeUpdate = new(GenAssociation::class).by(genTableAssociationInserted) {
            associationType = AssociationType.MANY_TO_MANY
        }
        val genTableAssociationUpdated = genTableAssociationRepository.save(genAssociationBeforeUpdate)
        genTableAssociationRepository.deleteById(genTableAssociationUpdated.id)
    }
}
