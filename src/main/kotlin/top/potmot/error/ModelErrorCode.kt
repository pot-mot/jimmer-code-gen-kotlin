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
}