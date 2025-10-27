package top.potmot.entity.database

data class DbColumnRef(
    val columnName: String,
    val referencedColumnName: String
)
