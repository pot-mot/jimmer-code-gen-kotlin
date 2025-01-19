package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField
import top.potmot.entity.dto.IdName
import top.potmot.entity.dto.IdNullableName

@ErrorFamily
enum class GenerateErrorCode {
    @ErrorField(name = "modelId", type = Long::class)
    MODEL_NOT_FOUND,

    @ErrorField(name = "entityId", type = Long::class, nullable = true)
    ENTITY_NOT_FOUND,

    @ErrorField(name = "index", type = IdName::class)
    @ErrorField(name = "indexColumnIds", type = Long::class, list = true)
    @ErrorField(name = "table", type = IdName::class)
    @ErrorField(name = "tableColumns", type = IdName::class, list = true)
    INDEX_COLUMN_NOT_FOUND_IN_TABLE,

    @ErrorField(name = "path", type = String::class)
    @ErrorField(name = "importItems", type = String::class, list = true)
    DEFAULT_IMPORT_MORE_THAN_ONE,

    @ErrorField(name = "association", type = IdName::class)
    @ErrorField(name = "sourceTable", type = IdName::class)
    @ErrorField(name = "sourceColumn", type = IdNullableName::class)
    @ErrorField(name = "targetTable", type = IdName::class)
    @ErrorField(name = "targetColumn", type = IdName::class)
    OUT_ASSOCIATION_CANNOT_FOUNT_SOURCE_COLUMN,

    @ErrorField(name = "association", type = IdName::class)
    @ErrorField(name = "sourceTable", type = IdName::class)
    @ErrorField(name = "sourceColumn", type = IdName::class)
    @ErrorField(name = "targetTable", type = IdName::class)
    @ErrorField(name = "targetColumn", type = IdNullableName::class)
    IN_ASSOCIATION_CANNOT_FOUNT_TARGET_COLUMN,
}