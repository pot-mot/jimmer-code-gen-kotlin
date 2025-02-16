package top.potmot.entity.dto

data class IdName(
    val id: Long,
    val name: String,
)

data class IdNullableName(
    val id: Long,
    val name: String?,
)

data class IdNamePackagePath(
    val id: Long,
    val name: String,
    val packagePath: String
)