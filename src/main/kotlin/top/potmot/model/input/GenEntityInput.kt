package top.potmot.model.input

import org.babyfish.jimmer.Input
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers
import java.time.LocalDateTime
import top.potmot.model.GenEntity

/**
 * 生成实体实体输入类
 *
 * @author potmot
 * @since 2023-08-06 17:21:51
 */
data class GenEntityInput(
    var id: Long? = null,
    var tableId: Long? = null,
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
) : Input<GenEntity> {
    override fun toEntity(): GenEntity =
        CONVERTER.toGenEntity(this)

    @Mapper
    internal interface Converter {
        @BeanMapping(
            unmappedTargetPolicy = ReportingPolicy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
        )
        fun toGenEntity(input: GenEntityInput): GenEntity
    }

    companion object {
        @JvmStatic
        private val CONVERTER = Mappers.getMapper(Converter::class.java)
    }
}

