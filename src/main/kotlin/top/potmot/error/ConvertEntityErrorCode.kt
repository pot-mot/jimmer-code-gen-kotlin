package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily

@ErrorFamily
enum class ConvertEntityErrorCode {
    ASSOCIATION,
    SUPER_TABLE,
}
