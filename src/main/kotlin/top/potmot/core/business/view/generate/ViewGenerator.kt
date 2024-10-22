package top.potmot.core.business.view.generate

import top.potmot.core.business.utils.componentNames
import top.potmot.core.business.utils.toFlat
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEnumGenerateView

abstract class ViewGenerator {
    abstract fun getFileSuffix(): String

    protected abstract fun stringifyEnumView(enum: GenEnumGenerateView): String

    protected abstract fun stringifyEnumSelect(enum: GenEnumGenerateView): String

    protected abstract fun stringifyEnumNullableSelect(enum: GenEnumGenerateView): String

    protected abstract fun stringifyQueryForm(entity: GenEntityBusinessView): String

    protected abstract fun stringifyTable(entity: GenEntityBusinessView): String

    protected fun GenEntityBusinessView.defaultAddInput() = "Default${name}AddInput"

    protected abstract fun stringifyDefaultAddInput(entity: GenEntityBusinessView): String

    protected abstract fun stringifyAddForm(entity: GenEntityBusinessView): String

    protected abstract fun stringifyEditForm(entity: GenEntityBusinessView): String

    protected abstract fun stringifyPage(entity: GenEntityBusinessView): String

    protected abstract fun stringifySingleSelect(entity: GenEntityBusinessView): String

    protected abstract fun stringifyMultiSelect(entity: GenEntityBusinessView): String

    fun generateEnum(
        enum: GenEnumGenerateView,
    ): List<Pair<String, String>> {
        val suffix = getFileSuffix()
        val (_, dir, select, nullableSelect, view) = enum.componentNames

        return listOf(
            "components/${dir}/${select}.$suffix" to stringifyEnumSelect(enum),
            "components/${dir}/${nullableSelect}.$suffix" to stringifyEnumNullableSelect(enum),
            "components/${dir}/${view}.$suffix" to stringifyEnumView(enum),
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

        val suffix = getFileSuffix()
        val (_, dir, table, addForm, editForm, queryForm, page, singleSelect, multiSelect) = flatEntity.componentNames
        val defaultAddInput = flatEntity.defaultAddInput()

        return listOf(
            "components/${dir}/${table}.$suffix" to stringifyTable(flatEntity),
            "components/${dir}/${defaultAddInput}.ts" to stringifyDefaultAddInput(flatEntity),
            "components/${dir}/${addForm}.$suffix" to stringifyAddForm(flatEntity),
            "components/${dir}/${editForm}.$suffix" to stringifyEditForm(flatEntity),
            "components/${dir}/${editForm}.$suffix" to stringifyEditForm(flatEntity),
            "components/${dir}/${queryForm}.$suffix" to stringifyQueryForm(flatEntity),
            "pages/${dir}/${page}.$suffix" to stringifyPage(flatEntity),
            "components/${dir}/${singleSelect}.$suffix" to stringifySingleSelect(flatEntity),
            "components/${dir}/${multiSelect}.$suffix" to stringifyMultiSelect(flatEntity),
        )
    }

    fun generateView(
        entities: Iterable<GenEntityBusinessView>,
    ): List<Pair<String, String>> =
        entities
            .flatMap { generateView(it) }
            .distinct().sortedBy { it.first }
}