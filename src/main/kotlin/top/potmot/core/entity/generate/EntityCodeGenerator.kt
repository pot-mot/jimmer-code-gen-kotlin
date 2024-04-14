package top.potmot.core.entity.generate

import top.potmot.error.GenerateEntityException
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenPropertyEnum

abstract class EntityCodeGenerator {
    abstract fun getFileSuffix(): String

    private fun formatFilePath(packagePath: String): String =
        packagePath.replace(".", "/") + "/"

    private fun formatFileName(
        entity: GenEntityPropertiesView,
        withPath: Boolean
    ): String =
        "${if (withPath) formatFilePath(entity.packagePath) else ""}${entity.name}${getFileSuffix()}"

    private fun formatFileName(
        enum: GenPropertyEnum,
        withPath: Boolean
    ): String =
        "${if (withPath) formatFilePath(enum.packagePath) else ""}${enum.name}${getFileSuffix()}"

    protected abstract fun stringify(entity: GenEntityPropertiesView): String

    protected abstract fun stringify(enum: GenPropertyEnum): String

    @Throws(GenerateEntityException::class)
    fun generateEntity(
        entity: GenEntityPropertiesView,
        withPath: Boolean = false
    ): Pair<String, String> =
        Pair(formatFileName(entity, withPath), stringify(entity))

    @Throws(GenerateEntityException::class)
    fun generateEntities(
        entities: Collection<GenEntityPropertiesView>,
        withPath: Boolean = false
    ): List<Pair<String, String>> =
        entities
            .map { generateEntity(it, withPath) }
            .distinct().sortedBy { it.first }

    fun generateEnum(
        enum: GenPropertyEnum,
        withPath: Boolean = false
    ): Pair<String, String> =
        Pair(formatFileName(enum, withPath), stringify(enum))

    fun generateEnums(
        enums: Collection<GenPropertyEnum>,
        withPath: Boolean = false
    ): List<Pair<String, String>> =
        enums
            .map { generateEnum(it, withPath) }
            .distinct().sortedBy { it.first }
}
