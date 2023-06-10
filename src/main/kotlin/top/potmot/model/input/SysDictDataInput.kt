package top.potmot.model.input;

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import top.potmot.model.SysDictData

/**
 * 字典数据表实体类
 *
 * @author potmot
 * @since 2023-05-06 19:13:45
 */
data class SysDictDataInput(
    var sysDictDataCode: Long,
    var dictSort: Int,
    var dictLabel: String,
    var dictValue: String,
    var dictTypeId: Long,
) : Input<SysDictData> {
    override fun toEntity(): SysDictData =
        CONVERTER.toSysDictData(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
        fun toSysDictData(input: SysDictDataInput): SysDictData
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}
