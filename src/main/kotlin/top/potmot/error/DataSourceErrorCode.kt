package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily

@ErrorFamily
enum class DataSourceErrorCode {
    H2_INIT_FAIL,
    CONNECT_FAIL,
    SQL_EXECUTE_FAIL
}
