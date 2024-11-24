package top.potmot.core.business.view.generate

import top.potmot.core.business.utils.components
import top.potmot.core.business.utils.dir
import top.potmot.core.business.utils.rules
import top.potmot.core.business.utils.toFlat
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException

interface ViewGenerator {
    fun getFileSuffix(): String

    fun stringifyEnumView(enum: GenEnumGenerateView): String

    fun stringifyEnumSelect(enum: GenEnumGenerateView): String

    fun stringifyEnumNullableSelect(enum: GenEnumGenerateView): String

    fun stringifyQueryForm(entity: GenEntityBusinessView): String

    fun stringifyTable(entity: GenEntityBusinessView): String

    val GenEntityBusinessView.addFormDataType
        get() = "${name}AddFormType"

    fun stringifyAddFormType(entity: GenEntityBusinessView): String

    val GenEntityBusinessView.addFormDefault
        get() = "default${name}"

    @Throws(ModelException.DefaultItemNotFound::class)
    fun stringifyAddFormDefault(entity: GenEntityBusinessView): String

    fun stringifyAddFormRules(entity: GenEntityBusinessView): String

    fun stringifyAddForm(entity: GenEntityBusinessView): String

    fun stringifyEditFormRules(entity: GenEntityBusinessView): String

    fun stringifyEditForm(entity: GenEntityBusinessView): String

    fun stringifyEditTableRules(entity: GenEntityBusinessView): String

    fun stringifyEditTable(entity: GenEntityBusinessView): String

    fun stringifyPage(entity: GenEntityBusinessView): String

    fun stringifyIdSelect(entity: GenEntityBusinessView): String

    fun stringifyIdMultiSelect(entity: GenEntityBusinessView): String

    fun generateEnum(
        enum: GenEnumGenerateView,
    ): List<GenerateFile> {
        val suffix = getFileSuffix()
        val dir = enum.dir
        val (view, select, nullableSelect) = enum.components

        return listOf(
            GenerateFile(
                enum,
                "components/${dir}/${view}.$suffix",
                stringifyEnumView(enum),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumView),
            ),
            GenerateFile(
                enum,
                "components/${dir}/${select}.$suffix",
                stringifyEnumSelect(enum),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumSelect),
            ),
            GenerateFile(
                enum,
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

    @Throws(ModelException.DefaultItemNotFound::class)
    fun generateView(
        entity: GenEntityBusinessView,
    ): List<GenerateFile> {
        val flatEntity = entity.toFlat()

        val suffix = getFileSuffix()
        val dir = flatEntity.dir
        val (table, addForm, editForm, queryForm, page, idSelect, idMultiSelect, editTable) = flatEntity.components
        val addFormDataType = flatEntity.addFormDataType
        val defaultAddFormData = flatEntity.addFormDefault
        val (addFormRules, editFormRules, editTableRules) = entity.rules

        return listOfNotNull(
            GenerateFile(
                entity,
                "components/${dir}/${table}.$suffix",
                stringifyTable(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Table),
            ),
            if (!entity.canAdd) null else GenerateFile(
                entity,
                "components/${dir}/${addFormDataType}.d.ts",
                stringifyAddFormType(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.AddFormDataType),
            ),
            if (!entity.canAdd) null else GenerateFile(
                entity,
                "components/${dir}/${defaultAddFormData}.ts",
                stringifyAddFormDefault(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.DefaultAddFormData),
            ),
            if (!entity.canAdd) null else GenerateFile(
                entity,
                "components/${dir}/${addForm}.$suffix",
                stringifyAddForm(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.AddForm),
            ),
            if (!entity.canEdit) null else GenerateFile(
                entity,
                "components/${dir}/${editForm}.$suffix",
                stringifyEditForm(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.EditForm),
            ),
            GenerateFile(
                entity,
                "components/${dir}/${editTable}.$suffix",
                stringifyEditTable(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.EditTable),
            ),
            if (!entity.canQuery) null else GenerateFile(
                entity,
                "components/${dir}/${queryForm}.$suffix",
                stringifyQueryForm(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.QueryForm),
            ),
            if (!entity.hasPage) null else GenerateFile(
                entity,
                "pages/${dir}/${page}.$suffix",
                stringifyPage(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Page),
            ),
            GenerateFile(
                entity,
                "components/${dir}/${idSelect}.$suffix",
                stringifyIdSelect(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdSelect),
            ),
            GenerateFile(
                entity,
                "components/${dir}/${idMultiSelect}.$suffix",
                stringifyIdMultiSelect(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdMultiSelect),
            ),
            if (!entity.canAdd) null else GenerateFile(
                entity,
                "rules/${dir}/${addFormRules}.ts",
                stringifyAddFormRules(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.AddFormRules, GenerateTag.AddForm),
            ),
            if (!entity.canEdit) null else GenerateFile(
                entity,
                "rules/${dir}/${editFormRules}.ts",
                stringifyEditFormRules(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.EditFormRules, GenerateTag.EditForm),
            ),
            GenerateFile(
                entity,
                "rules/${dir}/${editTableRules}.ts",
                stringifyEditTableRules(flatEntity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.EditTableRules, GenerateTag.EditTable),
            ),
        )
    }

    @Throws(ModelException.DefaultItemNotFound::class)
    fun generateView(
        entities: Iterable<GenEntityBusinessView>,
    ): List<GenerateFile> =
        entities
            .flatMap { generateView(it) }
            .distinct().sortedBy { it.path }
}