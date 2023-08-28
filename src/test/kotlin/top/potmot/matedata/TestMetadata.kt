package top.potmot.matedata

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import schemacrawler.crawl.SchemaCrawler
import schemacrawler.tools.catalogloader.CatalogLoaderRegistry
import top.potmot.constant.Language
import top.potmot.dao.MetadataDao
import top.potmot.util.LogUtils
import top.potmot.util.convert.getPropertyTypeName

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
            it.columns.forEach { column ->
                println(getPropertyTypeName(column, language = Language.KOTLIN))
                println(getPropertyTypeName(column, language = Language.JAVA))
            }
        }
    }
}
