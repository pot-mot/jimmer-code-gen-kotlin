package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField
import top.potmot.entity.dto.IdName

@ErrorFamily
enum class LoadFromDataSourceErrorCode {
    @ErrorField(name = "foreignKeyName", type = String::class)
    ASSOCIATION_COLUMN_REFERENCES_CANNOT_BE_EMPTY,

    @ErrorField(name = "foreignKeyName", type = String::class)
    @ErrorField(name = "columnTablePairs", type = ColumnTableNotMatchItem::class, list = true)
    ASSOCIATION_CANNOT_SUPPORT_MULTI_COLUMNS,

    @ErrorField(name = "indexName", type = String::class)
    @ErrorField(name = "indexColumnToTables", type = ColumnTableNotMatchItem::class, list = true)
    INDEX_COLUMN_TABLE_NOT_MATCH,

    @ErrorField(name = "foreignKeyName", type = String::class)
    @ErrorField(name = "tableName", type = String::class)
    ASSOCIATION_COLUMN_REFERENCE_TABLE_NOT_FOUND,

    @ErrorField(name = "foreignKeyName", type = String::class)
    @ErrorField(name = "tableName", type = String::class)
    @ErrorField(name = "columnName", type = String::class)
    ASSOCIATION_COLUMN_REFERENCE_COLUMN_NOT_FOUND,
}

data class ColumnTableNotMatchItem(
    val column: IdName,
    val table: IdName,
)