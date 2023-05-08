package top.potmot.jimmercodegen.gentable

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.jimmercodegen.service.AssociationService
import top.potmot.jimmercodegen.service.ReturnService
import top.potmot.jimmercodegen.service.TableService
import java.io.File

@SpringBootTest
class AssociationServiceTest (
    @Autowired val returnService: ReturnService,
    @Autowired val tableService: TableService,
    @Autowired val associationService: AssociationService
) {
    @Test
    fun clearAssociations() {
        associationService.clearAssociations()
    }

    @Test
    fun importAssociations() {
        associationService.clearAssociations()
        associationService.importAssociations()
    }

    @Test
    fun importTables() {
        tableService.clearGenTable()
        tableService.importGenTable()
    }

    @Test
    fun import() {
        tableService.importGenTable()
        associationService.importAssociations()
    }

    @Test
    fun clear() {
        tableService.clearGenTable()
        associationService.clearAssociations()
    }

    @Test
    fun result() {
        File("D:\\java\\project\\jimmer-code-gen\\result\\result.json").writeText(returnService.getTable(205L).toString())
    }

}