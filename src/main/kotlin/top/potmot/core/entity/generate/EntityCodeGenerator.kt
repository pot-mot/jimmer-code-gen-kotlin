package top.potmot.core.entity.generate

import top.potmot.core.utils.filePath
import top.potmot.error.GenerateEntityException
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenEnumGenerateView

abstract class EntityCodeGenerator {
    abstract fun getFileSuffix(): String

    protected abstract fun stringify(entity: GenEntityGenerateView): String

    protected abstract fun stringify(enum: GenEnumGenerateView): String

    @Throws(GenerateEntityException::class)
    fun generateEntity(
        entity: GenEntityGenerateView,
    ): Pair<String, String> =
        "${entity.filePath}${entity.name}${getFileSuffix()}" to stringify(entity)

    @Throws(GenerateEntityException::class)
    fun generateEntity(
        entities: Iterable<GenEntityGenerateView>,
    ): List<Pair<String, String>> =
        entities
            .map { generateEntity(it) }
            .distinct().sortedBy { it.first }

    fun generateEnum(
        enum: GenEnumGenerateView,
    ): Pair<String, String> =
        "${enum.filePath}${enum.name}${getFileSuffix()}" to stringify(enum)

    fun generateEnum(
        enums: Iterable<GenEnumGenerateView>,
    ): List<Pair<String, String>> =
        enums
            .map { generateEnum(it) }
            .distinct().sortedBy { it.first }
}
