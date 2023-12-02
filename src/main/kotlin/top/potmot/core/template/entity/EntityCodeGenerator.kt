package top.potmot.core.template.entity

import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2 as PropertyEnum

abstract class EntityCodeGenerator {
    protected abstract fun formatFileName(name: String): String

    protected abstract fun stringify(entity: GenEntityPropertiesView): String

    protected abstract fun stringify(enum: PropertyEnum): String

    fun generate(enum: PropertyEnum): Pair<String, String> =
        Pair(formatFileName(enum.name), stringify(enum))

    private fun GenEntityPropertiesView.enumsStringify(): List<Pair<String, String>> =
        properties
            .mapNotNull { it.enum }
            .distinctBy { it.id }
            .map { Pair(it.name, stringify(it)) }

    fun generate(
        entity: GenEntityPropertiesView,
        withEnums: Boolean = true
    ): List<Pair<String, String>> =
        listOf(
            Pair(formatFileName(entity.name), stringify(entity))
        ).let {list ->
            if (withEnums) {
                list + entity.enumsStringify().map {Pair(formatFileName(it.first), it.second) }
            } else {
                list
            }
        }
}
