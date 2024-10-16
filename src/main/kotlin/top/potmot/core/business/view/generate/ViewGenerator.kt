package top.potmot.core.business.view.generate

import top.potmot.core.utils.component
import top.potmot.core.utils.enums
import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.entity.dto.GenPropertyEnum

abstract class ViewGenerator {
    abstract fun getFileSuffix(): String

    protected abstract fun stringifyTable(entity: GenEntityPropertiesView): String

    protected abstract fun stringifyForm(entity: GenEntityPropertiesView): String

    protected abstract fun stringifyQueryForm(entity: GenEntityPropertiesView): String

    protected abstract fun stringifyPage(entity: GenEntityPropertiesView): String

    protected abstract fun stringifyEnumSelect(enum: GenPropertyEnum): String

    protected abstract fun stringifyEnumView(enum: GenPropertyEnum): String

    fun generateEnum(
        enum: GenPropertyEnum,
    ): List<Pair<String, String>> {
        val (_, dir, select, view) = enum.component

        return listOf(
            "/components/${dir}/${select}.${getFileSuffix()}" to stringifyEnumSelect(enum),
            "/components/${dir}/${view}.${getFileSuffix()}" to stringifyEnumView(enum),
        )
    }

    fun generateView(
        entity: GenEntityPropertiesView,
    ): List<Pair<String, String>> {
        val (_, dir, table, form, queryForm, page) = entity.component

        return listOf(
            "/components/${dir}/${table}.${getFileSuffix()}" to stringifyTable(entity),
            "/components/${dir}/${form}.${getFileSuffix()}" to stringifyForm(entity),
            "/components/${dir}/${queryForm}.${getFileSuffix()}" to stringifyQueryForm(entity),
            "/pages/${dir}/${page}.${getFileSuffix()}" to stringifyPage(entity)
        ) + entity.enums.flatMap { generateEnum(it) }
    }

    fun generateView(
        entities: Collection<GenEntityPropertiesView>,
    ): List<Pair<String, String>> =
        entities
            .flatMap { generateView(it) }
            .distinct().sortedBy { it.first }
}