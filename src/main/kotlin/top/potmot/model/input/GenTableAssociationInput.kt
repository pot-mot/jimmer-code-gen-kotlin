package top.potmot.model.input

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import top.potmot.constant.AssociationType
import java.time.LocalDateTime
import top.potmot.model.GenTableAssociation

/**
 * 代码生成业务表关联实体输入类
 *
 * @author potmot
 * @since 2023-08-04 13:08:00
 */
data class GenTableAssociationInput(
    var id: Long? = null,
    var tableAssociationName: String? = null,
    var sourceTableId: Long?,
    var sourceColumnId: Long?,
    var targetTableId: Long?,
    var targetColumnId: Long?,
    var associationType: AssociationType? = null,
    var associationExpress: String? = null,
    var orderKey: Long? = null,
    var createdTime: LocalDateTime? = null,
    var modifiedTime: LocalDateTime? = null,
    var remark: String? = null,
) : Input<GenTableAssociation> {
    override fun toEntity(): GenTableAssociation =
        CONVERTER.toGenTableAssociation(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
        fun toGenTableAssociation(input: GenTableAssociationInput): GenTableAssociation
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}

