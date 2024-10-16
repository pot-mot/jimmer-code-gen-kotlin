package top.potmot.core.business.service.generate

import top.potmot.entity.dto.GenEntityPropertiesView

abstract class ServiceGenerator {
    protected fun formatFilePath(packagePath: String): String =
        packagePath.replace(".", "/") + "/"

    abstract fun generateService(
        entity: GenEntityPropertiesView,
        withPath: Boolean = false
    ): Pair<String, String>

    fun generateService(
        entities: Collection<GenEntityPropertiesView>,
        withPath: Boolean = false
    ): List<Pair<String, String>> =
        entities
            .map { generateService(it, withPath) }
            .distinct().sortedBy { it.first }
}