package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily

@ErrorFamily
enum class GenerateErrorCode {
    MODEL_NOT_FOUND,
    ENTITY_NOT_FOUND,
    ID_PROPERTY_NOT_FOUND,
}