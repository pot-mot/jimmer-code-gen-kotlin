package top.potmot.model.input

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import java.time.LocalDateTime
import top.potmot.model.GenAssociation

/**
 * 生成关联实体输入类
 *
 * @author potmot
 * @since 2023-08-06 17:19:58
 */
data class GenAssociationInput(
    var id: Long? = null,
    var associationComment: String? = null,
    var sourceEntityId: Long? = null,
    var sourcePropertyId: Long? = null,
    var targetEntityId: Long? = null,
    var targetPropertyId: Long? = null,
    var associationType: String? = null,
    var associationExpress: String? = null,
    var orderKey: Long? = null,
    var createdTime: LocalDateTime? = null,
    var modifiedTime: LocalDateTime? = null,
    var remark: String? = null,
) : Input<GenAssociation> {
    override fun toEntity(): GenAssociation =
        CONVERTER.toGenAssociation(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
        fun toGenAssociation(input: GenAssociationInput): GenAssociation
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}

