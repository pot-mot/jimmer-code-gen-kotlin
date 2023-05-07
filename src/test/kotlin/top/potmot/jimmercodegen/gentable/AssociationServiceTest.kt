package top.potmot.jimmercodegen.gentable

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.jimmercodegen.service.AssociationService

@SpringBootTest
class AssociationServiceTest (
    @Autowired val associationService: AssociationService
) {
    @Test
    fun getAssociations() {
        associationService.getAssociations()
    }
}