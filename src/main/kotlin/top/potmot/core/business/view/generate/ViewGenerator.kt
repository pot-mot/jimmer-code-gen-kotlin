package top.potmot.core.business.view.generate

import top.potmot.core.business.utils.component
import top.potmot.core.business.utils.toFlat
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
            "components/${dir}/${select}.${getFileSuffix()}" to stringifyEnumSelect(enum),
            "components/${dir}/${view}.${getFileSuffix()}" to stringifyEnumView(enum),
        )
    }
    
    fun generateEnum(
        enums: Iterable<GenEnumGenerateView>
    ): List<Pair<String, String>> =
        enums
            .flatMap { generateEnum(it) }
            .distinct().sortedBy { it.first }

    fun generateView(
        entity: GenEntityBusinessView,
    ): List<Pair<String, String>> {
        val flatEntity = entity.toFlat()

        val (_, dir, table, form, queryForm, page) = flatEntity.component

        return listOf(
            "components/${dir}/${table}.${getFileSuffix()}" to stringifyTable(flatEntity),
            "components/${dir}/${form}.${getFileSuffix()}" to stringifyForm(flatEntity),
            "components/${dir}/${queryForm}.${getFileSuffix()}" to stringifyQueryForm(flatEntity),
            "pages/${dir}/${page}.${getFileSuffix()}" to stringifyPage(flatEntity)
        )
    }

    fun generateView(
        entities: Iterable<GenEntityBusinessView>,
    ): List<Pair<String, String>> =
        entities
            .flatMap { generateView(it) }
            .distinct().sortedBy { it.first }
}