package top.potmot.core.business.view.generate

import top.potmot.entity.dto.GenEntityPropertiesView

abstract class ViewGenerator {
    abstract fun getFileSuffix(): String

    protected abstract fun stringifyTable(entity: GenEntityPropertiesView): String

    protected abstract fun stringifyForm(entity: GenEntityPropertiesView): String

    protected abstract fun stringifyQueryForm(entity: GenEntityPropertiesView): String

    protected abstract fun stringifyPage(entity: GenEntityPropertiesView): String

    fun generateView(
        entity: GenEntityPropertiesView,
    ): List<Pair<String, String>> {
        val dir = entity.name.replaceFirstChar { it.lowercase() }

        return listOf(
            Pair("/components/${dir}/${entity.name}Table.${getFileSuffix()}", stringifyTable(entity)),
            Pair("/components/${dir}/${entity.name}Form.${getFileSuffix()}", stringifyForm(entity)),
            Pair("/components/${dir}/${entity.name}QueryForm.${getFileSuffix()}", stringifyQueryForm(entity)),
            Pair("/pages/${dir}/${entity.name}Page.${getFileSuffix()}", stringifyPage(entity))
        )
    }

    fun generateView(
        entities: Collection<GenEntityPropertiesView>,
    ): List<Pair<String, String>> =
        entities
            .flatMap { generateView(it) }
            .distinct().sortedBy { it.first }
}