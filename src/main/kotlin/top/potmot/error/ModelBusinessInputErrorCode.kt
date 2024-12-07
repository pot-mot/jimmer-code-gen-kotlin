package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField
import top.potmot.entity.dto.IdName

@ErrorFamily
enum class ModelBusinessInputErrorCode {
    @ErrorField(name = "entityName", type = String::class)
    @ErrorField(name = "tableName", type = String::class)
    ENTITY_CANNOT_MATCH_TABLE,

    @ErrorField(name = "entityName", type = String::class)
    @ErrorField(name = "table", type = IdName::class)
    ENTITY_MATCHED_TABLE_CONVERTED_ENTITY_NOT_FOUND,

    @ErrorField(name = "entity", type = IdName::class)
    @ErrorField(name = "propertyName", type = String::class)
    PROPERTY_CANNOT_MATCH_COLUMN,

    @ErrorField(name = "entity", type = IdName::class)
    @ErrorField(name = "propertyName", type = String::class)
    @ErrorField(name = "matchedColumn", type = IdName::class)
    PROPERTY_CANNOT_REMATCH_OLD_PROPERTY,

    @ErrorField(name = "entity", type = IdName::class)
    @ErrorField(name = "propertyName", type = String::class)
    @ErrorField(name = "matchedColumn", type = IdName::class)
    @ErrorField(name = "matchedProperties", type = IdName::class, list = true)
    PROPERTY_MATCHED_MORE_THAN_ONE_OLD_PROPERTY,
}