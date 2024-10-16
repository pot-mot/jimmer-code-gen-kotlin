package top.potmot.core.business.view.generate

import top.potmot.core.entityExtension.component
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
        val (_, table, form, queryForm, page) = entity.component

        return listOf(
            Pair("/components/${dir}/${table}.${getFileSuffix()}", stringifyTable(entity)),
            Pair("/components/${dir}/${form}.${getFileSuffix()}", stringifyForm(entity)),
            Pair("/components/${dir}/${queryForm}.${getFileSuffix()}", stringifyQueryForm(entity)),
            Pair("/pages/${dir}/${page}.${getFileSuffix()}", stringifyPage(entity))
        )
    }

    fun generateView(
        entities: Collection<GenEntityPropertiesView>,
    ): List<Pair<String, String>> =
        entities
            .flatMap { generateView(it) }
            .distinct().sortedBy { it.first }
}