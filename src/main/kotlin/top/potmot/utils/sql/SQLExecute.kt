package top.potmot.utils.sql

import org.slf4j.LoggerFactory
import java.sql.Connection

val logger = LoggerFactory.getLogger(Connection::class.java)

fun Connection.execute(sql: String, log: Boolean = false, ignoreExecuteFail: Boolean = false): List<SQLExecuteResult> {
    val results = mutableListOf<SQLExecuteResult>()

    val oldAutoCommit = autoCommit

    autoCommit = false

    val parts = sql.split(";").filter { it.isNotBlank() }.map { it.trim() }

    for (part in parts) {
        if (log) logger.info("execute: $part")

        val result = try {
            createStatement().execute(part)
            SQLExecuteResult(part, true)
        } catch (e: Exception) {
            if (log) logger.error("execute fail: \n${e.message}")
            SQLExecuteResult(part, false, e)
        }

        results += result

        if (!ignoreExecuteFail && !result.success) break
    }

    if (results.filterNot { it.success }.isEmpty()) {
        commit()
    } else {
        rollback()
    }

    autoCommit = oldAutoCommit

    return results
}
