package top.potmot.core.business.service.generate

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.GenerateException

abstract class ServiceGenerator {
    abstract fun getFileSuffix(): String

    protected abstract fun stringifyService(entity: EntityBusiness): String

    @Throws(GenerateException::class)
    fun generateService(
        entity: EntityBusiness,
    ) = GenerateFile(
        entity,
        "${entity.serviceFilePath}/${entity.serviceName}${getFileSuffix()}",
        stringifyService(entity),
        listOf(GenerateTag.BackEnd, GenerateTag.Service)
    )

    @Throws(GenerateException::class)
    fun generateService(
        entities: Iterable<EntityBusiness>,
    ): List<GenerateFile> =
        entities
            .map { generateService(it) }
            .distinctBy { it.path }
            .sortedBy { it.path }
}