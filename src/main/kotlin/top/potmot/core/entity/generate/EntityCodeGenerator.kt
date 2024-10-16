package top.potmot.core.entity.generate

import top.potmot.core.utils.filePath
import top.potmot.error.GenerateEntityException
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenEnumGenerateView

abstract class EntityCodeGenerator {
    abstract fun getFileSuffix(): String

    private fun formatFileName(
        entity: GenEntityGenerateView,
        withPath: Boolean
    ): String =
        "${if (withPath) entity.filePath else ""}${entity.name}${getFileSuffix()}"

    private fun formatFileName(
        enum: GenEnumGenerateView,
        withPath: Boolean
    ): String =
        "${if (withPath) enum.filePath else ""}${enum.name}${getFileSuffix()}"

    protected abstract fun stringify(entity: GenEntityGenerateView): String

    protected abstract fun stringify(enum: GenEnumGenerateView): String

    @Throws(GenerateEntityException::class)
    fun generateEntity(
        entity: GenEntityGenerateView,
        withPath: Boolean = false
    ): Pair<String, String> =
        Pair(formatFileName(entity, withPath), stringify(entity))

    @Throws(GenerateEntityException::class)
    fun generateEntity(
        entities: Collection<GenEntityGenerateView>,
        withPath: Boolean = false
    ): List<Pair<String, String>> =
        entities
            .map { generateEntity(it, withPath) }
            .distinct().sortedBy { it.first }

    fun generateEnum(
        enum: GenEnumGenerateView,
        withPath: Boolean = false
    ): Pair<String, String> =
        Pair(formatFileName(enum, withPath), stringify(enum))

    fun generateEnum(
        enums: Collection<GenEnumGenerateView>,
        withPath: Boolean = false
    ): List<Pair<String, String>> =
        enums
            .map { generateEnum(it, withPath) }
            .distinct().sortedBy { it.first }
}
