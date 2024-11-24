package top.potmot.core.business.service.generate

import top.potmot.core.business.utils.serviceFilePath
import top.potmot.core.business.utils.serviceName
import top.potmot.core.business.utils.toFlat
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.GenerateException

abstract class ServiceGenerator {
    abstract fun getFileSuffix(): String

    protected abstract fun stringifyService(entity: GenEntityBusinessView): String

    @Throws(GenerateException::class)
    fun generateService(
        entity: GenEntityBusinessView,
    ): GenerateFile {
        val flatEntity = entity.toFlat()

        return GenerateFile(
            entity,
            "${flatEntity.serviceFilePath}${entity.serviceName}${getFileSuffix()}",
            stringifyService(flatEntity),
            listOf(GenerateTag.BackEnd, GenerateTag.Service)
        )
    }

    @Throws(GenerateException::class)
    fun generateService(
        entities: Iterable<GenEntityBusinessView>,
    ): List<GenerateFile> =
        entities
            .map { generateService(it) }
            .sortedBy { it.path }
}