package top.potmot.jimmercodegen.report

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.service.report.ReportService

@SpringBootTest
class AssociationReportTest (
    @Autowired val reportService: ReportService
) {
    @Test
    fun showAssociation() {
        reportService.getAssociation().forEach {
            println(it.targetTable.tableName + " " + it.targetTable.tableComment)
        }
    }
}