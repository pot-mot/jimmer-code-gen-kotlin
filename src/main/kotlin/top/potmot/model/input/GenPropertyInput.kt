package top.potmot.model.input

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import top.potmot.constant.SortDirection
import java.time.LocalDateTime
import top.potmot.model.GenProperty

/**
 * 生成字段实体输入类
 *
 * @author potmot
 * @since 2023-08-06 17:22:20
 */
data class GenPropertyInput(
    var id: Long? = null,
    var columnId: Long? = null,
    var entityId: Long? = null,
    var propertyName: String? = null,
    var propertyType: String? = null,
    var propertyComment: String? = null,
    var isList: Boolean? = null,
    var listSort: Long? = null,
    var isAdd: Boolean? = null,
    var addSort: Long? = null,
    var isAddRequired: Boolean? = null,
    var isEdit: Boolean? = null,
    var editSort: Long? = null,
    var isEditRequired: Boolean? = null,
    var readOnly: Boolean? = null,
    var isQuery: Boolean? = null,
    var querySort: Long? = null,
    var queryType: String? = null,
    var dictType: String? = null,
    var isSort: Boolean? = null,
    var sortDirection: SortDirection? = null,
    var isLogicalDelete: Boolean? = null,
    var createdTime: LocalDateTime? = null,
    var modifiedTime: LocalDateTime? = null,
    var remark: String? = null,
) : Input<GenProperty> {
    override fun toEntity(): GenProperty =
        CONVERTER.toGenProperty(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
        fun toGenProperty(input: GenPropertyInput): GenProperty
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}

