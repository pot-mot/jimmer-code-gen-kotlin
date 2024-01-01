package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily

@ErrorFamily
enum class DataSourceLoadErrorCode {
    TABLE,
    COLUMN,
    ASSOCIATION,
    INDEX,
}
