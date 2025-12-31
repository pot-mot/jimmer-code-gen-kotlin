package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily

@ErrorFamily
enum class UpdateErrorCode {
    NOT_EXISTED,
}

@ErrorFamily
enum class DeleteErrorCode {
    NOT_EXISTED,
}
