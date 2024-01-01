package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily

@ErrorFamily
enum class GenerateTableDefineErrorCode {
    TABLE,
    COLUMN,
    INDEX,
}
