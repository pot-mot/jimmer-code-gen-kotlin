package top.potmot.jimmercodegen.model.input;

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import top.potmot.jimmercodegen.model.GenTableColumn;

/**
 * 代码生成业务表字段实体类
 *
 * @author potmot
 * @since 2023-05-06 23:20:01
 */
data class GenTableColumnInput(
    var genTableColumnId: Long?,
    var tableId: Long,
    var columnName: String,
    var columnComment: String,
    var columnType: String,
    var fieldType: String,
    var fieldName: String,
    var pk: String,
    var increment: String,
    var required: String,
    var inInsert: String,
    var inEdit: String,
    var inList: String,
    var inQuery: String,
    var idView: String,
    var queryType: String,
    var htmlType: String,
    var dictType: String,
    var sort: Int,
) : Input<GenTableColumn> {
    override fun toEntity(): GenTableColumn =
        CONVERTER.toGenTableColumn(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
        fun toGenTableColumn(input: GenTableColumnInput): GenTableColumn
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}
