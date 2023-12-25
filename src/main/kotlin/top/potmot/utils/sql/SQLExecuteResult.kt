package top.potmot.utils.sql

data class SQLExecuteResult(
    val sql: String,
    val success: Boolean,
    val exception: Exception? = null,
)
