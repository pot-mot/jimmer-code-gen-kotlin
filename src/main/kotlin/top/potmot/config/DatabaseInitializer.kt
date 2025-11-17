package top.potmot.config

import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.cfg.KInitializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import top.potmot.error.DatabaseException
import top.potmot.init.initCrossTypes
import top.potmot.init.initGenerateScripts
import top.potmot.init.initJvmTypes
import top.potmot.init.initSqlTypes
import top.potmot.init.initTsTypes
import top.potmot.utils.sql.execute
import java.io.File
import java.io.InputStreamReader
import java.sql.Connection
import javax.sql.DataSource

/*
 * Initialize H2 database
 */
@Component
open class DatabaseInitializer(
    private val dataSource: DataSource,
    @Value("\${spring.datasource.url:null}") private val url: String?,
    @Value("\${spring.datasource.sql-path:null}") private val sqlPath: String?,
) : KInitializer {

    var logger = LoggerFactory.getLogger(DatabaseInitializer::class.java)!!

    private fun extractFilePathFromUrl(jdbcUrl: String): String {
        val regex = Regex("jdbc:h2:file:(.*?)(?:;|$)")
        val matchResult = regex.find(jdbcUrl)

        if (matchResult != null) {
            return matchResult.groupValues[1]
        } else {
            throw DatabaseException.h2InitFail(exceptionMessage = "Cannot extract database name from URL: $jdbcUrl")
        }
    }

    override fun initialize(dsl: KSqlClient) {
        if (url == null) {
            throw DatabaseException.h2InitFail(exceptionMessage = "no jdbc url find")
        }

        if (url.startsWith("jdbc:h2:file")) {
            val filePath = extractFilePathFromUrl(url)
            val baseFile = File(filePath)
            val dbFile = File(baseFile.canonicalPath + ".mv.db")

            try {
                dataSource.connection.use { connection ->
                    if (checkIsInit(connection)) {
                        logger.info("h2 database by file already init")
                    } else {
                        initH2(connection)
                        initTypeMapping(dsl, connection)
                        initGenerateScript(dsl, connection)
                    }
                }
            } catch (e: Throwable) {
                if (dbFile.exists()) {
                    try {
                        dbFile.delete()
                        logger.info("delete corrupted H2 database file: ${dbFile.absolutePath}")
                    } catch (e: Throwable) {
                        logger.warn("Failed to delete corrupted H2 database file: ${dbFile.absolutePath}")
                        e.printStackTrace()
                    }
                }
                throw e
            }
        } else if (url.startsWith("jdbc:h2:mem")) {
            dataSource.connection.use { connection ->
                if (checkIsInit(connection)) {
                    logger.info("h2 database by memory already init")
                } else {
                    initH2(connection)
                    initTypeMapping(dsl, connection)
                    initGenerateScript(dsl, connection)
                }
            }
        } else {
            throw DatabaseException.h2InitFail(exceptionMessage = "unsupported jdbc url: $url")
        }

        logger.info(
            """
===========================
Jimmer Code Gen
---------------------------
h2 database init finish
===========================
"""
        )
    }

    /**
     * 校验实体数据库架构是否存在
     */
    private fun checkIsInit(connection: Connection): Boolean {
        val schemaCountSql =
            "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'jimmer_code_gen';"

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(schemaCountSql)
        resultSet.next()
        val count = resultSet.getInt(1)
        return count > 0
    }

    private fun initH2(connection: Connection) {
        /**
         * 获取 sql
         */
        val inputStream = sqlPath?.let {
            DatabaseInitializer::class.java
                .classLoader
                .getResourceAsStream(it)
        } ?: throw DatabaseException.h2InitFail(exceptionMessage = "no h2 sql find in path: $sqlPath")

        val sqlList = InputStreamReader(inputStream).use { reader ->
            reader.readText()
        }

        /**
         * 执行 sql
         */
        logger.info("start init h2 database")

        val results = connection.execute(sqlList)

        val failResults = results.filterNot { it.success }

        if (failResults.isNotEmpty()) {
            val failMessages = mutableListOf<String>()
            failResults.forEach {
                val failMessage =
                    "execute fail: \n---\n${it.sql}\n---\nbecause of exception: \n---\n${it.exception}\n---\n"
                failMessages.add(failMessage)
                logger.error(failMessage)
            }
            throw DatabaseException.h2InitFail(exceptionMessage = failMessages.joinToString("\n"))
        }

        logger.info("h2 init finish")
    }

    private fun initTypeMapping(dsl: KSqlClient, connection: Connection) {
        dsl.saveEntitiesCommand(initJvmTypes) {
            setMode(SaveMode.INSERT_ONLY)
            setAssociatedModeAll(AssociatedSaveMode.APPEND)
        }.execute(connection)

        dsl.saveEntitiesCommand(initSqlTypes) {
            setMode(SaveMode.INSERT_ONLY)
            setAssociatedModeAll(AssociatedSaveMode.APPEND)
        }.execute(connection)

        dsl.saveEntitiesCommand(initTsTypes) {
            setMode(SaveMode.INSERT_ONLY)
            setAssociatedModeAll(AssociatedSaveMode.APPEND)
        }.execute(connection)

        dsl.saveEntitiesCommand(initCrossTypes) {
            setMode(SaveMode.INSERT_ONLY)
            setAssociatedModeAll(AssociatedSaveMode.APPEND)
        }.execute(connection)
    }

    private fun initGenerateScript(dsl: KSqlClient, connection: Connection) {
        dsl.saveInputsCommand(initGenerateScripts) {
            setMode(SaveMode.INSERT_ONLY)
            setAssociatedModeAll(AssociatedSaveMode.APPEND)
        }.execute(connection)
    }
}


