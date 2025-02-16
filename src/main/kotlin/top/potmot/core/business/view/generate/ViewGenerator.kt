package top.potmot.core.business.view.generate

import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.entity.dto.GenerateFile

interface ViewGenerator {
    fun generateEntity(
        entities: Iterable<RootEntityBusiness>,
    ): List<GenerateFile>

    fun generateEntity(
        vararg entities: RootEntityBusiness,
    ) = generateEntity(entities.toList())
}