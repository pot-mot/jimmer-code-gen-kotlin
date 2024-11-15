package top.potmot.error

import org.babyfish.jimmer.error.ErrorFamily
import org.babyfish.jimmer.error.ErrorField
import top.potmot.entity.dto.IdName

@ErrorFamily
enum class ColumnTypeErrorCode {
    @ErrorField(name = "column", type = IdName::class, nullable = true)
    @ErrorField(name = "columnName", type = String::class, nullable = true)
    @ErrorField(name = "typeCode", type = Int::class)
    @ErrorField(name = "requiredParam", type = String::class)
    MISS_REQUIRED_PARAM
}
