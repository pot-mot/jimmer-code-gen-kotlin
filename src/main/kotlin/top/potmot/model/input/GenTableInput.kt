package top.potmot.model.input

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import java.time.LocalDateTime
import top.potmot.model.GenTable

/**
 * 代码生成业务表实体输入类
 *
 * @author potmot
 * @since 2023-08-05 09:52:02
 */
data class GenTableInput(
    var id: Long? = null,
    var tableName: String? = null,
    var tableComment: String? = null,
    var tableType: String? = null,
    var className: String? = null,
    var classComment: String? = null,
    var packageName: String? = null,
    var moduleName: String? = null,
    var functionName: String? = null,
    var author: String? = null,
    var genPath: String? = null,
    var isAdd: Boolean? = null,
    var isEdit: Boolean? = null,
    var isList: Boolean? = null,
    var isQuery: Boolean? = null,
    var orderKey: Long? = null,
    var createdTime: LocalDateTime? = null,
    var modifiedTime: LocalDateTime? = null,
    var remark: String? = null,
) : Input<GenTable> {
    override fun toEntity(): GenTable =
        CONVERTER.toGenTable(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
        fun toGenTable(input: GenTableInput): GenTable
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}

