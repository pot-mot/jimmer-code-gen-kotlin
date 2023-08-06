package top.potmot.model.input

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import java.time.LocalDateTime
import top.potmot.model.GenDictData

/**
 * 字典数据实体输入类
 *
 * @author potmot
 * @since 2023-08-06 17:21:29
 */
data class GenDictDataInput(
    var id: Long? = null,
    var dictTypeId: Long? = null,
    var dictSort: Long? = null,
    var dictLabel: String? = null,
    var dictValue: String? = null,
    var enumName: String? = null,
    var enumLabel: String? = null,
    var enumValue: String? = null,
    var createdTime: LocalDateTime? = null,
    var modifiedTime: LocalDateTime? = null,
    var remark: String? = null,
) : Input<GenDictData> {
    override fun toEntity(): GenDictData =
        CONVERTER.toGenDictData(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
        fun toGenDictData(input: GenDictDataInput): GenDictData
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}

