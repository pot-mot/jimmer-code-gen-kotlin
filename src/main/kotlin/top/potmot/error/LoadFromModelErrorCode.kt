package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField
import top.potmot.entity.dto.IdName

@ErrorFamily
enum class LoadFromModelErrorCode {
    @ErrorField(name = "indexName", type = String::class)
    @ErrorField(name = "indexColumnNames", type = String::class, list = true)
    @ErrorField(name = "table", type = IdName::class)
    @ErrorField(name = "notFoundColumnName", type = String::class)
    INDEX_COLUMN_NOT_FOUND,

    @ErrorField(name = "associationName", type = String::class)
    @ErrorField(name = "sourceTableName", type = String::class)
    @ErrorField(name = "sourceColumnNames", type = String::class, list = true)
    @ErrorField(name = "targetTableName", type = String::class)
    @ErrorField(name = "targetColumnNames", type = String::class, list = true)
    ASSOCIATION_SOURCE_TABLE_NOT_FOUND,

    @ErrorField(name = "associationName", type = String::class)
    @ErrorField(name = "sourceTableName", type = String::class)
    @ErrorField(name = "sourceColumnNames", type = String::class, list = true)
    @ErrorField(name = "targetTableName", type = String::class)
    @ErrorField(name = "targetColumnNames", type = String::class, list = true)
    ASSOCIATION_TARGET_TABLE_NOT_FOUND,

    @ErrorField(name = "associationName", type = String::class)
    @ErrorField(name = "sourceTableName", type = String::class)
    @ErrorField(name = "sourceColumnNames", type = String::class, list = true)
    @ErrorField(name = "targetTableName", type = String::class)
    @ErrorField(name = "targetColumnNames", type = String::class, list = true)
    @ErrorField(name = "notFoundSourceColumnName", type = String::class)
    ASSOCIATION_SOURCE_COLUMN_NOT_FOUND,

    @ErrorField(name = "associationName", type = String::class)
    @ErrorField(name = "sourceTableName", type = String::class)
    @ErrorField(name = "sourceColumnNames", type = String::class, list = true)
    @ErrorField(name = "targetTableName", type = String::class)
    @ErrorField(name = "targetColumnNames", type = String::class, list = true)
    @ErrorField(name = "notFoundTargetColumnName", type = String::class)
    ASSOCIATION_TARGET_COLUMN_NOT_FOUND,

    @ErrorField(name = "tableName", type = String::class)
    TABLE_NOT_FOUND,

    @ErrorField(name = "table", type = IdName::class)
    @ErrorField(name = "superTableNames", type = String::class, list = true)
    @ErrorField(name = "notFoundSuperTableName", type = String::class)
    TABLE_SUPER_TABLE_NOT_FOUND,

    @ErrorField(name = "indexNames", type = String::class, list = true)
    @ErrorField(name = "notFoundTableName", type = String::class)
    INDEXES_TABLE_NOT_FOUND,
}
