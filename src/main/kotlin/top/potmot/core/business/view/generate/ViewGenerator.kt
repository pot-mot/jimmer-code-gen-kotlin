package top.potmot.core.business.view.generate

import top.potmot.core.business.property.EntityBusiness
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.GenerateFile

interface ViewGenerator {
    fun generateEnum(
        enum: GenEnumGenerateView,
    ): List<GenerateFile>

    fun generateEnum(
        enums: Iterable<GenEnumGenerateView>,
    ): List<GenerateFile> =
        enums
            .flatMap { generateEnum(it) }
            .distinctBy { it.path }


    fun generateView(
        entityBusiness: EntityBusiness,
    ): List<GenerateFile>

    fun generateView(
        entityBusinessList: Iterable<EntityBusiness>,
    ): List<GenerateFile> =
        entityBusinessList
            .flatMap { generateView(it) }
            .distinctBy { it.path }
}