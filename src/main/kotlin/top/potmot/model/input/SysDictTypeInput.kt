package top.potmot.model.input;

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import top.potmot.model.SysDictType

/**
 * 字典类型表实体类
 *
 * @author potmot
 * @since 2023-05-06 19:10:25
 */
data class SysDictTypeInput(
    var sysDictTypeId: Long,
    var dictName: String,
    var dictType: String,
) : Input<SysDictType> {
    override fun toEntity(): SysDictType =
        CONVERTER.toSysDictType(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
        fun toSysDictType(input: SysDictTypeInput): SysDictType
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}
