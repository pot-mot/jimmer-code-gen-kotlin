package top.potmot.utils.database.model

data class CheckConstraintInfo(
    val constraintName: String,
    val columnName: String,
    val checkClause: String
)
