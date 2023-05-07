package top.potmot.jimmercodegen.model.input;

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import top.potmot.jimmercodegen.model.GenTableAssociation;

/**
 * 代码生成业务表关联实体类
 *
 * @author potmot
 * @since 2023-05-07 09:36:25
 */
data class GenTableAssociationInput(
    var genTableAssociationId: Long?,
    var tableAssociationName: String,
    var masterTableId: Long,
    var masterTableColumnId: Long,
    var slaveTableId: Long,
    var slaveTableColumnId: Long,
    var associationCategory: String?,
) : Input<GenTableAssociation> {
    override fun toEntity(): GenTableAssociation =
        CONVERTER.toGenTableAssociation(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
        fun toGenTableAssociation(input: GenTableAssociationInput): GenTableAssociation
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}
