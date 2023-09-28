package top.potmot.import

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.core.import.getFkAssociation
import top.potmot.extension.getCatalog
import top.potmot.extension.toSource
import top.potmot.model.GenDataSource

@SpringBootTest
@ActiveProfiles("test-kotlin")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DataSourceImportTest(
    @Autowired val sqlClient: KSqlClient
) {
    @Test
    @Order(1)
    fun testImportSchema() {
        sqlClient.findById(GenDataSource::class, 1L)
            ?.toSource()
            ?.getCatalog("jimmer_code_gen")
            ?.tables
            ?.forEach {
                it.getFkAssociation(1L).forEach {
                    sqlClient.insert(it)
                }
            }
    }
}
