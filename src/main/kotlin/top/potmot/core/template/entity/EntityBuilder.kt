package top.potmot.core.template.entity

import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2 as PropertyEnum

abstract class EntityBuilder {
    abstract fun stringify(entity: GenEntityPropertiesView): String

    abstract fun stringify(enum: PropertyEnum): String

    private fun GenEntityPropertiesView.enumsStringify(): Map<String, String> =
        properties
            .mapNotNull { it.enum }
            .distinctBy { it.id }
            .associate { Pair(name, stringify(it)) }

    fun generate(entity: GenEntityPropertiesView): Map<String, String> {
        val map = mutableMapOf<String, String>()

        map["${entity.name}.java"] = stringify(entity)

        entity.enumsStringify().forEach {
            map["${it.key}.kt"] = it.value
        }

        return map
    }

    fun generate(enum: PropertyEnum): Pair<String, String> =
        Pair("${enum.name}.kt", stringify(enum))
}
