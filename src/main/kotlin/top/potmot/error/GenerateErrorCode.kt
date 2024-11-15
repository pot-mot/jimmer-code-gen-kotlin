package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField
import top.potmot.entity.dto.IdName

@ErrorFamily
enum class GenerateErrorCode {
    @ErrorField(name = "modelId", type = Long::class)
    MODEL_NOT_FOUND,

    @ErrorField(name = "entityId", type = Long::class)
    ENTITY_NOT_FOUND,

    @ErrorField(name = "index", type = IdName::class)
    @ErrorField(name = "indexColumnIds", type = Long::class, list = true)
    @ErrorField(name = "table", type = IdName::class)
    @ErrorField(name = "tableColumns", type = IdName::class, list = true)
    INDEX_COLUMN_NOT_FOUND_IN_TABLE,

    CAN_ONLY_HAVE_ONE_DEFAULT_IMPORT_FOR_ONE_PATH,
}