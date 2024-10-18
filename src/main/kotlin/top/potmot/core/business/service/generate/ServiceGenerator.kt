package top.potmot.core.business.service.generate

import top.potmot.core.business.utils.serviceName
import top.potmot.core.business.utils.toFlat
import top.potmot.core.utils.filePath
import top.potmot.entity.dto.GenEntityBusinessView

abstract class ServiceGenerator {
    abstract fun getFileSuffix(): String

    protected abstract fun stringifyService(entity: GenEntityBusinessView): String

    fun generateService(
        entity: GenEntityBusinessView,
        withPath: Boolean = false
    ): Pair<String, String> {
        val flatEntity = entity.toFlat()

        return "${if (withPath) entity.filePath else ""}${entity.serviceName}${getFileSuffix()}" to stringifyService(flatEntity)
    }

    fun generateService(
        entities: Iterable<GenEntityBusinessView>,
        withPath: Boolean = false
    ): List<Pair<String, String>> =
        entities
            .map { generateService(it, withPath) }
            .distinct().sortedBy { it.first }
}