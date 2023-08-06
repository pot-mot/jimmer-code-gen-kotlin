package top.potmot.model.input

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import java.time.LocalDateTime
import top.potmot.model.GenTypeMapping

/**
 * 列到字段类型映射实体输入类
 *
 * @author potmot
 * @since 2023-08-06 17:23:30
 */
data class GenTypeMappingInput(
    var id: Long? = null,
    var columnType: String? = null,
    var isRegex: Boolean? = null,
    var propertyType: String? = null,
    var orderKey: Long? = null,
    var createdTime: LocalDateTime? = null,
    var modifiedTime: LocalDateTime? = null,
    var remark: String? = null,
) : Input<GenTypeMapping> {
    override fun toEntity(): GenTypeMapping =
        CONVERTER.toGenTypeMapping(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
        fun toGenTypeMapping(input: GenTypeMappingInput): GenTypeMapping
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}

