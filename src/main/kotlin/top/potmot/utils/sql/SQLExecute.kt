package top.potmot.utils.sql

import org.slf4j.LoggerFactory
import java.sql.Connection

val logger = LoggerFactory.getLogger(Connection::class.java)

fun Connection.execute(sql: String, log: Boolean = false): List<SQLExecuteResult> {
    val results = mutableListOf<SQLExecuteResult>()

    val oldAutoCommit = autoCommit

    autoCommit = false


    sql.split(";").filter { it.isNotBlank() }.map { it.trim() }.forEach {
        if (log) logger.info("execute: $it")
        results += try {
            createStatement().execute(it)
            SQLExecuteResult(it, true)
        } catch (e: Exception) {
            if (log) logger.error("execute fail: \n${e.message}")
            SQLExecuteResult(it, false, e)
        }
    }

    if (results.filterNot { it.success }.isEmpty()) {
        commit()
    } else {
        rollback()
    }

    autoCommit = oldAutoCommit

    return results
}
