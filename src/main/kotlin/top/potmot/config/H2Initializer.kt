package top.potmot.config

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.cfg.KInitializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import top.potmot.error.DataSourceException
import top.potmot.utils.sql.execute
import java.io.InputStreamReader
import javax.sql.DataSource

/*
 * Initialize H2 in-memory database
 */
@Component
class H2Initializer(
    private val dataSource: DataSource,
    @Value("\${spring.datasource.url:null}") private val url: String?,
    @Value("\${spring.datasource.sql-path:null}") private val sqlPath: String?,
) : KInitializer {

    var logger = LoggerFactory.getLogger(H2Initializer::class.java)

    override fun initialize(dsl: KSqlClient) {
        if (url != null && url.startsWith("jdbc:h2")) {
            initH2()
        }
    }

    @Throws(DataSourceException.H2InitFail::class)
    private fun initH2() {
        dataSource.connection.use { connection ->
            val inputStream = sqlPath?.let {
                H2Initializer::class.java
                    .classLoader
                    .getResourceAsStream(it)
            } ?: throw DataSourceException.h2InitFail("no h2 sql find in path: $sqlPath")

            val sqls = InputStreamReader(inputStream).use { reader ->
                reader.readText()
            }

            logger.info("start init h2 database")

            val results = connection.execute(sqls)

            val failResults = results.filterNot { it.success }

            if (failResults.isNotEmpty()) {
                failResults.forEach {
                    logger.error("execute fail: ${it.sql}")
                    it.exception?.printStackTrace()
                }
                throw Exception("h2 init fail")
            }

            logger.info("h2 init finish")
        }
    }
}


