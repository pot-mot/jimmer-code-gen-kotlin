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
 * 实体输入类
 *
 * @author potmot
 * @since 2023-08-04 13:31:17
 */
data class GenTypeMappingInput(
    var id: Long? = null,
    var columnType: String? = null,
    var isRegex: Boolean? = null,
    var fieldType: String? = null,
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

