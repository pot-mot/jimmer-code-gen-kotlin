package top.potmot.core.service.generate

import top.potmot.entity.dto.GenEntityPropertiesView

abstract class ServiceCodeGenerator {
    abstract fun getFileSuffix(): String

    private fun formatFilePath(packagePath: String): String =
        packagePath.replace(".", "/") + "/"

    private fun formatFileName(
        entity: GenEntityPropertiesView,
        withPath: Boolean
    ): String =
        "${if (withPath) formatFilePath(entity.packagePath) else ""}${entity.name}${getFileSuffix()}"

    protected abstract fun stringify(entity: GenEntityPropertiesView): String

    fun generateService(
        entity: GenEntityPropertiesView,
        withPath: Boolean = false
    ): Pair<String, String> =
        Pair(formatFileName(entity, withPath), stringify(entity))

    fun generateServices(
        entities: Collection<GenEntityPropertiesView>,
        withPath: Boolean = false
    ): List<Pair<String, String>> =
        entities
            .map { generateService(it, withPath) }
            .distinct().sortedBy { it.first }
}