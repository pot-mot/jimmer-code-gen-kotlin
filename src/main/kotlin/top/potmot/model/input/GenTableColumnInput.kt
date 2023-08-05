package top.potmot.model.input

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import top.potmot.constant.QueryType
import top.potmot.constant.SortDirection
import java.time.LocalDateTime
import top.potmot.model.GenTableColumn

/**
 * 代码生成业务表字段实体输入类
 *
 * @author potmot
 * @since 2023-08-05 10:59:55
 */
data class GenTableColumnInput(
    var id: Long? = null,
    var tableId: Long? = null,
    var columnSort: Long? = null,
    var columnName: String? = null,
    var columnTypeCode: Int? = null,
    var columnType: String? = null,
    var columnDisplaySize: Long? = null,
    var columnPrecision: Long? = null,
    var columnDefault: String? = null,
    var columnComment: String? = null,
    var isPk: Boolean? = null,
    var isAutoIncrement: Boolean? = null,
    var isUnique: Boolean? = null,
    var isNotNull: Boolean? = null,
    var isVirtualColumn: Boolean? = null,
    var fieldName: String? = null,
    var fieldType: String? = null,
    var fieldComment: String? = null,
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
    var queryType: QueryType? = null,
    var dictType: String? = null,
    var isSort: Boolean? = null,
    var sortDirection: SortDirection? = null,
    var isLogicalDelete: Boolean? = null,
    var createdTime: LocalDateTime? = null,
    var modifiedTime: LocalDateTime? = null,
    var remark: String? = null,
) : Input<GenTableColumn> {
    override fun toEntity(): GenTableColumn =
        CONVERTER.toGenTableColumn(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
        fun toGenTableColumn(input: GenTableColumnInput): GenTableColumn
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}

