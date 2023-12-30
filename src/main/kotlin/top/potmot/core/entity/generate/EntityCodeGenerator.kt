package top.potmot.core.entity.generate

import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2 as PropertyEnum

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
        enum: PropertyEnum,
        withPath: Boolean
    ): String =
        "${if (withPath) formatFilePath(enum.packagePath) else ""}${enum.name}${getFileSuffix()}"

    protected abstract fun stringify(entity: GenEntityPropertiesView): String

    protected abstract fun stringify(enum: PropertyEnum): String

    fun generate(
        entity: GenEntityPropertiesView,
        withPath: Boolean = false
    ): Pair<String, String> =
        Pair(formatFileName(entity, withPath), stringify(entity))

    fun generate(
        enum: PropertyEnum,
        withPath: Boolean = false
    ): Pair<String, String> =
        Pair(formatFileName(enum, withPath), stringify(enum))

    fun generateWithEnums(
        entity: GenEntityPropertiesView,
        withPath: Boolean = false
    ): List<Pair<String, String>> =
        listOf(generate(entity, withPath)) +
                entity.properties.mapNotNull { it.enum }.map { generate(it, withPath) }
}
