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
 * @since 2023-08-04 13:06:40
 */
data class GenTableInput(
    var id: Long? = null,
    var tableName: String?,
    var tableComment: String?,
    var className: String?,
    var packageName: String?,
    var moduleName: String?,
    var functionName: String?,
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

