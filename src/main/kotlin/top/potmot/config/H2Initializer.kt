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
            /**
             * 校验 schema jimmer_code_gen 是否存在
             */
            val schemaCountSql =
                "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'jimmer_code_gen';"

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(schemaCountSql)
            resultSet.next()
            val count = resultSet.getInt(1)

            if (count > 0) {
                logger.info("h2 database already have schema `jimmer_code_gen`")
                return
            }

            /**
             * 获取 jimmer_code_gen.sql
             */
            val inputStream = sqlPath?.let {
                H2Initializer::class.java
                    .classLoader
                    .getResourceAsStream(it)
            } ?: throw DataSourceException.h2InitFail("no h2 sql find in path: $sqlPath")

            val sqls = InputStreamReader(inputStream).use { reader ->
                reader.readText()
            }

            /**
             * 执行 jimmer_code_gen.sql
             */
            logger.info("start init h2 database")

            val results = connection.execute(sqls)

            val failResults = results.filterNot { it.success }

            if (failResults.isNotEmpty()) {
                failResults.forEach {
                    logger.error("execute fail: ${it.sql}")
                }
                throw Exception("h2 init fail")
            }

            logger.info("h2 init finish")
        }
    }
}


