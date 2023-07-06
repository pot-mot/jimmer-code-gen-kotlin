package top.potmot.model.input

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import top.potmot.model.GenTable

/**
 * 代码生成业务表实体类
 *
 * @author potmot
 * @since 2023-05-06 18:45:51 */
data class GenTableInput(
    var id: Long?,
    var tableName: String,
    var tableComment: String,
    var className: String,
    var packageName: String,
    var moduleName: String,
    var functionName: String,
    var author: String,
    var genType: String,
    var genPath: String,
) : Input<GenTable> {
    override fun toEntity(): GenTable =
        CONVERTER.toGenTable(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
        fun toGenTable(input: GenTableInput): GenTable
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}
