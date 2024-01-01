package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily

@ErrorFamily
enum class GenerateEntityErrorCode {
    TABLE,
    COLUMN,
    ASSOCIATION,
    ENUM,
}
