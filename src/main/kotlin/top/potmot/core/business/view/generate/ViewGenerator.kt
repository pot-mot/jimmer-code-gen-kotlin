package top.potmot.core.business.view.generate

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.EnumBusiness
import top.potmot.entity.dto.GenerateFile

interface ViewGenerator {
    fun generateEnum(
        enum: EnumBusiness,
    ): List<GenerateFile>

    fun generateEnum(
        enums: Iterable<EnumBusiness>,
    ): List<GenerateFile> =
        enums
            .flatMap { generateEnum(it) }
            .distinctBy { it.path }


    fun generateView(
        entity: EntityBusiness,
    ): List<GenerateFile>

    fun generateView(
        entities: Iterable<EntityBusiness>,
    ): List<GenerateFile> =
        entities
            .flatMap { generateView(it) }
            .distinctBy { it.path }
}