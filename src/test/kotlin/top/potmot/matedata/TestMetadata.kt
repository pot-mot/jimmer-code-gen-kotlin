package top.potmot.matedata

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.constant.Language
import top.potmot.dao.MetadataDao
import top.potmot.util.GenUtils
import top.potmot.util.LogUtils

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestMetadata(
    @Autowired
    val metadataDao: MetadataDao,
) {
    @Order(1)
    @Test
    fun getTables() {
        metadataDao.getTables("gen_table").forEach {
            LogUtils.logTable(it)
        }
    }

    @Order(2)
    @Test
    fun getColumnFieldTypes() {
        metadataDao.getTables("tm_tool_type").forEach {
            it.columns.forEach {
                println(GenUtils.getFieldTypeName(it, language = Language.KOTLIN))
                println(GenUtils.getFieldTypeName(it, language = Language.JAVA))
            }
        }
    }
}
