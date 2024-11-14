package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField
import top.potmot.entity.dto.IdName

@ErrorFamily
enum class LoadFromDataSourceErrorCode {
    @ErrorField(name = "foreignKeyName", type = String::class)
    ASSOCIATION_COLUMN_REFERENCES_CANNOT_BE_EMPTY,

    @ErrorField(name = "foreignKeyName", type = String::class)
    @ErrorField(name = "propertyToSourceTables", type = PropertyTableNotMatchItem::class, list = true)
    ASSOCIATION_SOURCE_TABLE_NOT_MATCH,

    @ErrorField(name = "foreignKeyName", type = String::class)
    @ErrorField(name = "propertyToTargetTables", type = PropertyTableNotMatchItem::class, list = true)
    ASSOCIATION_TARGET_TABLE_NOT_MATCH,

    @ErrorField(name = "foreignKeyName", type = String::class)
    @ErrorField(name = "indexColumnToTables", type = IndexColumnTableNotMatchItem::class, list = true)
    INDEX_COLUMN_TABLE_NOT_MATCH,

    @ErrorField(name = "tableName", type = String::class)
    @ErrorField(name = "tableSchemaName", type = String::class)
    @ErrorField(name = "columnName", type = String::class)
    @ErrorField(name = "columnSchemaName", type = String::class)
    ASSOCIATION_COLUMN_REFERENCE_SCHEMA_NOT_MATCH,

    @ErrorField(name = "tableName", type = String::class)
    ASSOCIATION_COLUMN_REFERENCE_TABLE_NOT_FOUND,

    @ErrorField(name = "tableName", type = String::class)
    @ErrorField(name = "columnName", type = String::class)
    ASSOCIATION_COLUMN_REFERENCE_COLUMN_NOT_FOUND,
}

data class PropertyTableNotMatchItem(
    val property: IdName,
    val table: IdName,
)

data class IndexColumnTableNotMatchItem(
    val indexName: String,
    val column: IdName,
    val table: IdName,
)