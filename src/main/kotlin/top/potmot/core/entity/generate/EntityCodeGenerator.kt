package top.potmot.core.entity.generate

import top.potmot.core.entityExtension.filePath
import top.potmot.error.GenerateEntityException
import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.entity.dto.GenPropertyEnum

abstract class EntityCodeGenerator {
    abstract fun getFileSuffix(): String

    private fun formatFileName(
        entity: GenEntityPropertiesView,
        withPath: Boolean
    ): String =
        "${if (withPath) entity.filePath else ""}${entity.name}${getFileSuffix()}"

    private fun formatFileName(
        enum: GenPropertyEnum,
        withPath: Boolean
    ): String =
        "${if (withPath) enum.filePath else ""}${enum.name}${getFileSuffix()}"

    protected abstract fun stringify(entity: GenEntityPropertiesView): String

    protected abstract fun stringify(enum: GenPropertyEnum): String

    @Throws(GenerateEntityException::class)
    fun generateEntityWithEnums(
        entity: GenEntityPropertiesView,
        withPath: Boolean = false
    ): Pair<String, String> =
        Pair(formatFileName(entity, withPath), stringify(entity))

    @Throws(GenerateEntityException::class)
    fun generateEntityWithEnums(
        entities: Collection<GenEntityPropertiesView>,
        withPath: Boolean = false
    ): List<Pair<String, String>> =
        entities
            .map { generateEntityWithEnums(it, withPath) }
            .distinct().sortedBy { it.first }

    fun generateEnum(
        enum: GenPropertyEnum,
        withPath: Boolean = false
    ): Pair<String, String> =
        Pair(formatFileName(enum, withPath), stringify(enum))

    fun generateEnum(
        enums: Collection<GenPropertyEnum>,
        withPath: Boolean = false
    ): List<Pair<String, String>> =
        enums
            .map { generateEnum(it, withPath) }
            .distinct().sortedBy { it.first }
}
