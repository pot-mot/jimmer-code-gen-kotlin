package top.potmot.core.entity.generate

import top.potmot.enumeration.DataSourceType
import top.potmot.error.GenerateEntityException
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

    protected abstract fun stringify(entity: GenEntityPropertiesView, dataSourceType: DataSourceType): String

    protected abstract fun stringify(enum: PropertyEnum, dataSourceType: DataSourceType): String

    @Throws(GenerateEntityException::class)
    fun generate(
        entity: GenEntityPropertiesView,
        dataSourceType: DataSourceType,
        withPath: Boolean = false
    ): Pair<String, String> =
        Pair(formatFileName(entity, withPath), stringify(entity, dataSourceType))

    fun generate(
        enum: PropertyEnum,
        dataSourceType: DataSourceType,
        withPath: Boolean = false
    ): Pair<String, String> =
        Pair(formatFileName(enum, withPath), stringify(enum, dataSourceType))

    @Throws(GenerateEntityException::class)
    fun generateWithEnums(
        entity: GenEntityPropertiesView,
        dataSourceType: DataSourceType,
        withPath: Boolean = false
    ): List<Pair<String, String>> =
        listOf(generate(entity, dataSourceType, withPath)) +
                entity.properties.mapNotNull { it.enum }.map { generate(it, dataSourceType, withPath) }
}
