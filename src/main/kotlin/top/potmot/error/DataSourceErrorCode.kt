package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField

@ErrorFamily
enum class DataSourceErrorCode {
    @ErrorField(name = "exceptionMessage", type = String::class)
    H2_INIT_FAIL,

    @ErrorField(name = "exceptionMessage", type = String::class)
    CONNECT_FAIL,

    @ErrorField(name = "sql", type = String::class)
    @ErrorField(name = "exceptionMessage", type = String::class)
    SQL_EXECUTE_FAIL
}
