package top.potmot.core.business.view.generate

import top.potmot.core.business.utils.component
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEnumGenerateView

abstract class ViewGenerator {
    abstract fun getFileSuffix(): String

    protected abstract fun stringifyTable(entity: GenEntityBusinessView): String

    protected abstract fun stringifyForm(entity: GenEntityBusinessView): String

    protected abstract fun stringifyQueryForm(entity: GenEntityBusinessView): String

    protected abstract fun stringifyPage(entity: GenEntityBusinessView): String

    protected abstract fun stringifyEnumSelect(enum: GenEnumGenerateView): String

    protected abstract fun stringifyEnumView(enum: GenEnumGenerateView): String

    fun generateEnum(
        enum: GenEnumGenerateView,
    ): List<Pair<String, String>> {
        val (_, dir, select, view) = enum.component

        return listOf(
            "/components/${dir}/${select}.${getFileSuffix()}" to stringifyEnumSelect(enum),
            "/components/${dir}/${view}.${getFileSuffix()}" to stringifyEnumView(enum),
        )
    }

    fun generateView(
        entity: GenEntityBusinessView,
    ): List<Pair<String, String>> {
        val (_, dir, table, form, queryForm, page) = entity.component

        return listOf(
            "/components/${dir}/${table}.${getFileSuffix()}" to stringifyTable(entity),
            "/components/${dir}/${form}.${getFileSuffix()}" to stringifyForm(entity),
            "/components/${dir}/${queryForm}.${getFileSuffix()}" to stringifyQueryForm(entity),
            "/pages/${dir}/${page}.${getFileSuffix()}" to stringifyPage(entity)
        )
    }

    fun generateView(
        entities: Collection<GenEntityBusinessView>,
    ): List<Pair<String, String>> =
        entities
            .flatMap { generateView(it) }
            .distinct().sortedBy { it.first }
}