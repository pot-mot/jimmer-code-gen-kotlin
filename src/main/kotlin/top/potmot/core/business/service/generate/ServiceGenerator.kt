package top.potmot.core.business.service.generate

import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.GenerateException

interface ServiceGenerator {
    val suffix: String

    fun stringifyService(entity: RootEntityBusiness): String

    @Throws(GenerateException::class)
    fun generateService(
        entity: RootEntityBusiness,
    ) = GenerateFile(
        entity,
        "${entity.serviceFilePath}/${entity.serviceName}.$suffix",
        stringifyService(entity),
        listOf(GenerateTag.BackEnd, GenerateTag.Service)
    )

    @Throws(GenerateException::class)
    fun generateService(
        entities: Iterable<RootEntityBusiness>,
    ): List<GenerateFile> =
        entities
            .map { generateService(it) }
            .distinctBy { it.path }
            .sortedBy { it.path }
}