package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily

@ErrorFamily
enum class GenerateErrorCode {
    MODEL_NOT_FOUND,
    ENTITY_NOT_FOUND,
    DEFAULT_ITEM_NOT_FOUND,
    ID_PROPERTY_NOT_FOUND,
    CAN_ONLY_HAVE_ONE_DEFAULT_IMPORT_FOR_ONE_PATH,
}