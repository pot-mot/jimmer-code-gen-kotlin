package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField

@ErrorFamily
enum class DatabaseErrorCode {
    @ErrorField(name = "exceptionMessage", type = String::class)
    H2_INIT_FAIL,

    CONNECT_FAIL,

    DATA_SOURCE_NOT_FOUND,
}
