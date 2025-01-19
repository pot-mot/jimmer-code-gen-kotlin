package top.potmot.core.business.view.generate

import top.potmot.entity.dto.GenEntityBusinessView
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
            .sortedBy { it.path }


    fun generateView(
        entity: GenEntityBusinessView,
        entityIdMap: Map<Long, GenEntityBusinessView>
    ): List<GenerateFile>

    fun generateView(
        entities: Iterable<GenEntityBusinessView>,
    ): List<GenerateFile> {
        val entityIdMap = entities.associateBy { it.id }

        return entities
            .flatMap { generateView(it, entityIdMap) }
            .distinctBy { it.path }
            .sortedBy { it.path }
    }
}