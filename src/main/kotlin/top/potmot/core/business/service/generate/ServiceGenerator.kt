package top.potmot.core.business.service.generate

import kotlin.jvm.Throws
import top.potmot.core.business.utils.serviceFilePath
import top.potmot.core.business.utils.serviceName
import top.potmot.core.business.utils.toFlat
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.error.GenerateException

abstract class ServiceGenerator {
    abstract fun getFileSuffix(): String

    protected abstract fun stringifyService(entity: GenEntityBusinessView): String

    @Throws(GenerateException::class)
    fun generateService(
        entity: GenEntityBusinessView,
    ): Pair<String, String> {
        val flatEntity = entity.toFlat()

        return "${entity.serviceFilePath}${entity.serviceName}${getFileSuffix()}" to stringifyService(flatEntity)
    }

    @Throws(GenerateException::class)
    fun generateService(
        entities: Iterable<GenEntityBusinessView>,
    ): List<Pair<String, String>> =
        entities
            .map { generateService(it) }
            .distinct().sortedBy { it.first }
}