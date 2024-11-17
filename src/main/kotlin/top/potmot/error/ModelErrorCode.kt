package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField
import top.potmot.entity.dto.IdName

@ErrorFamily
enum class ModelErrorCode {
    @ErrorField(name = "enum", type = IdName::class)
    DEFAULT_ITEM_NOT_FOUND,

    @ErrorField(name = "entity", type = IdName::class)
    ID_PROPERTY_NOT_FOUND,

    @ErrorField(name = "entity", type = IdName::class)
    @ErrorField(name = "entityProperties", type = IdName::class, list = true)
    @ErrorField(name = "index", type = IdName::class)
    @ErrorField(name = "indexPropertyIds", type = Long::class, list = true)
    @ErrorField(name = "notFoundPropertyId", type = Long::class)
    INDEX_REF_PROPERTY_NOT_FOUND,

        @ErrorField(name = "entity", type = IdName::class)
    @ErrorField(name = "entityProperties", type = IdName::class, list = true)
    @ErrorField(name = "index", type = IdName::class)
    @ErrorField(name = "indexPropertyIds", type = Long::class, list = true)
    @ErrorField(name = "listProperty", type = IdName::class)
    INDEX_REF_PROPERTY_CANNOT_BE_LIST
}