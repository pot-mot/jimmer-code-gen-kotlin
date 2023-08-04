package top.potmot.matedata

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.dao.GenMetadataDao

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestMetadata(
    @Autowired
    val genMetadataDao: GenMetadataDao,
) {
    @Order(1)
    @Test
    fun getTables() {
        genMetadataDao.getTables()
    }
}
