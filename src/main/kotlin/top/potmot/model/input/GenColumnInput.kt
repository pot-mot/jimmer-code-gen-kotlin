package top.potmot.model.input

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import java.time.LocalDateTime
import top.potmot.model.GenColumn

/**
 * 生成列实体输入类
 *
 * @author potmot
 * @since 2023-08-06 17:20:59
 */
data class GenColumnInput(
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
    var createdTime: LocalDateTime? = null,
    var modifiedTime: LocalDateTime? = null,
    var remark: String? = null,
) : Input<GenColumn> {
    override fun toEntity(): GenColumn =
        CONVERTER.toGenColumn(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
        fun toGenColumn(input: GenColumnInput): GenColumn
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}

