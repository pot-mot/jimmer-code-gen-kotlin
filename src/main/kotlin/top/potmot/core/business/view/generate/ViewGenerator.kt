package top.potmot.core.business.view.generate

import top.potmot.core.business.meta.EnumBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.entity.dto.GenerateFile

interface ViewGenerator {
    fun generateEnum(
        enum: EnumBusiness,
    ): List<GenerateFile>

    fun generateEnum(
        enums: Iterable<EnumBusiness>,
    ): List<GenerateFile> =
        enums.flatMap { generateEnum(it) }


    fun generateView(
        entity: RootEntityBusiness,
    ): List<GenerateFile>

    fun generateView(
        entities: Iterable<RootEntityBusiness>,
    ): List<GenerateFile> =
        entities.flatMap { generateView(it) }
}