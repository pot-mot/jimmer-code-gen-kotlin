package top.potmot.core.business.view.generate

import top.potmot.core.business.utils.componentNames
import top.potmot.core.business.utils.ruleNames
import top.potmot.core.business.utils.toFlat
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag

abstract class ViewGenerator {
    abstract fun getFileSuffix(): String

    protected abstract fun stringifyEnumView(enum: GenEnumGenerateView): String

    protected abstract fun stringifyEnumSelect(enum: GenEnumGenerateView): String

    protected abstract fun stringifyEnumNullableSelect(enum: GenEnumGenerateView): String

    protected abstract fun stringifyQueryForm(entity: GenEntityBusinessView): String

    protected abstract fun stringifyTable(entity: GenEntityBusinessView): String

    protected fun GenEntityBusinessView.defaultAddInput() = "Default${name}AddInput"

    protected abstract fun stringifyDefaultAddInput(entity: GenEntityBusinessView): String

    protected abstract fun stringifyAddFormRules(entity: GenEntityBusinessView): String

    protected abstract fun stringifyAddForm(entity: GenEntityBusinessView): String

    protected abstract fun stringifyEditFormRules(entity: GenEntityBusinessView): String

    protected abstract fun stringifyEditForm(entity: GenEntityBusinessView): String

    protected abstract fun stringifyEditTableRules(entity: GenEntityBusinessView): String

    protected abstract fun stringifyEditTable(entity: GenEntityBusinessView): String

    protected abstract fun stringifyPage(entity: GenEntityBusinessView): String

    protected abstract fun stringifySingleSelect(entity: GenEntityBusinessView): String

    protected abstract fun stringifyMultiSelect(entity: GenEntityBusinessView): String

    protected abstract fun stringifyIdSelect(entity: GenEntityBusinessView): String

    protected abstract fun stringifyIdMultiSelect(entity: GenEntityBusinessView): String

    fun generateEnum(
        enum: GenEnumGenerateView,
    ): List<GenerateFile> {
        val suffix = getFileSuffix()
        val (_, dir, select, nullableSelect, view) = enum.componentNames

        return listOf(
            GenerateFile(
                "components/${dir}/${view}.$suffix",
                stringifyEnumView(enum),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumView),
            ),
            GenerateFile(
                "components/${dir}/${select}.$suffix",
                stringifyEnumSelect(enum),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumSelect),
            ),
            GenerateFile(
                "components/${dir}/${nullableSelect}.$suffix",
                stringifyEnumNullableSelect(enum),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumNullableSelect),
            )
        )
    }
    
    fun generateEnum(
        enums: Iterable<GenEnumGenerateView>,
    ): List<GenerateFile> =
        enums
            .flatMap { generateEnum(it) }
            .distinct().sortedBy { it.path }

    fun generateView(
        entity: GenEntityBusinessView,
    ): List<GenerateFile> {
        val flatEntity = entity.toFlat()

        val suffix = getFileSuffix()
        val (_, dir, table, addForm, editForm, queryForm, page, singleSelect, multiSelect, idSelect, idMultiSelect, editTable) = flatEntity.componentNames
        val defaultAddInput = flatEntity.defaultAddInput()
        val (_, ruleDir, addFormRules, editFormRules, editTableRules) = entity.ruleNames

        return listOf(
            GenerateFile(
                "components/${dir}/${table}.$suffix",
                stringifyTable(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Table),
            ),
            GenerateFile(
                "components/${dir}/${defaultAddInput}.ts",
                stringifyDefaultAddInput(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.DefaultAddInput),
            ),
            GenerateFile(
                "components/${dir}/${addForm}.$suffix",
                stringifyAddForm(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.AddForm),
            ),
            GenerateFile(
                "components/${dir}/${editForm}.$suffix",
                stringifyEditForm(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.EditForm),
            ),
            GenerateFile(
                "components/${dir}/${editTable}.$suffix",
                stringifyEditTable(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.EditTable),
            ),
            GenerateFile(
                "components/${dir}/${queryForm}.$suffix",
                stringifyQueryForm(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.QueryForm),
            ),
            GenerateFile(
                "pages/${dir}/${page}.$suffix",
                stringifyPage(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Page),
            ),
            GenerateFile(
                "components/${dir}/${singleSelect}.$suffix",
                stringifySingleSelect(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.EntitySelect),
            ),
            GenerateFile(
                "components/${dir}/${multiSelect}.$suffix",
                stringifyMultiSelect(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.EntityMultiSelect),
            ),
            GenerateFile(
                "components/${dir}/${idSelect}.$suffix",
                stringifyIdSelect(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdSelect),
            ),
            GenerateFile(
                "components/${dir}/${idMultiSelect}.$suffix",
                stringifyIdMultiSelect(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdMultiSelect),
            ),
            GenerateFile(
                "rules/${ruleDir}/${addFormRules}.ts",
                stringifyAddFormRules(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.AddFormRules, GenerateTag.AddForm),
            ),
            GenerateFile(
                "rules/${ruleDir}/${editFormRules}.ts",
                stringifyEditFormRules(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.EditFormRules, GenerateTag.EditForm),
            ),
            GenerateFile(
                "rules/${ruleDir}/${editTableRules}.ts",
                stringifyEditTableRules(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.EditTableRules, GenerateTag.EditTable),
            ),
        )
    }

    fun generateView(
        entities: Iterable<GenEntityBusinessView>,
    ): List<GenerateFile> =
        entities
            .flatMap { generateView(it) }
            .distinct().sortedBy { it.path }
}