package top.potmot.core.business.service.generate

import top.potmot.entity.dto.GenEntityBusinessView

abstract class ServiceGenerator {
    protected fun formatFilePath(packagePath: String): String =
        packagePath.replace(".", "/") + "/"

    abstract fun generateService(
        entity: GenEntityBusinessView,
        withPath: Boolean = false
    ): Pair<String, String>

    fun generateService(
        entities: Collection<GenEntityBusinessView>,
        withPath: Boolean = false
    ): List<Pair<String, String>> =
        entities
            .map { generateService(it, withPath) }
            .distinct().sortedBy { it.first }
}