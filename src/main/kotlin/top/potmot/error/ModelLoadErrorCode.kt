package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily

@ErrorFamily
enum class ModelLoadErrorCode {
    TABLE,
    COLUMN,
    INDEX,
    ASSOCIATION,
    ENUM,
}
