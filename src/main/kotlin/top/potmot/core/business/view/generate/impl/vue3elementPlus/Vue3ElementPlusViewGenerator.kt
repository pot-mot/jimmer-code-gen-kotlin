package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.EnumBusiness
import top.potmot.core.business.view.generate.ViewGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.addForm.AddFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.editForm.EditFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.editTable.EditTableGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.enumSelect.EnumSelectGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.enumView.EnumViewGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.page.PageGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.queryForm.QueryFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.select.IdSelectGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.viewTable.ViewTableGen
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException

object Vue3ElementPlusViewGenerator :
    ViewGenerator,
    EnumViewGen,
    EnumSelectGen,
    ViewTableGen,
    IdSelectGen,
    QueryFormGen,
    AddFormGen,
    EditFormGen,
    EditTableGen,
    PageGen {
    override val indent = "    "

    override val wrapThreshold = 40

    override val componentBuilder = Vue3ComponentBuilder(indent, wrapThreshold)

    override val rulesBuilder = Vue3ElementPlusRulesBuilder(indent, wrapThreshold)

    override fun stringify(component: Component) = componentBuilder.build(component)

    override fun generateEnum(
        enum: EnumBusiness,
    ): List<GenerateFile> {
        val result = mutableListOf<GenerateFile>()

        result += enumViewFile(enum)
        result += enumSelectFile(enum)

        return result
    }

    @Throws(ModelException::class)
    override fun generateView(
        entity: EntityBusiness,
    ): List<GenerateFile> {
        val dir = entity.dir
        val (table, addForm, editForm, queryForm, page, idSelect, idMultiSelect, editTable) = entity.components
        val (addFormRules, editFormRules, editTableRules) = entity.rules

        val result = mutableListOf<GenerateFile>()

        result += GenerateFile(
            entity,
            "components/${dir}/${table}.vue",
            stringify(viewTableComponent(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Table, GenerateTag.ViewTable),
        )

        if (entity.canAdd) {
            val addFormDataType = entity.addFormDataType
            val defaultAddFormData = entity.addFormCreateDefault

            result += GenerateFile(
                entity,
                "components/${dir}/${addFormDataType}.d.ts",
                addFormType(entity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.AddFormDataType),
            )
            result += GenerateFile(
                entity,
                "components/${dir}/${defaultAddFormData}.ts",
                addFormDefault(entity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.DefaultAddFormData),
            )
            result += GenerateFile(
                entity,
                "components/${dir}/${addForm}.vue",
                stringify(addFormComponent(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.AddForm),
            )
        }

        if (entity.canEdit) {
            result += GenerateFile(
                entity,
                "components/${dir}/${editForm}.vue",
                stringify(editFormComponent(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.EditForm),
            )
        }

        // TODO when long association
        result += GenerateFile(
            entity,
            "components/${dir}/${editTable}.vue",
            stringify(editTableComponent(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.Table, GenerateTag.EditTable),
        )

        if (entity.canQuery) {
            result += GenerateFile(
                entity,
                "components/${dir}/${queryForm}.vue",
                stringify(queryFormComponent(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.QueryForm),
            )
        }

        if (entity.hasPage) {
            result += GenerateFile(
                entity,
                "pages/${dir}/${page}.vue",
                stringify(pageComponent(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Page),
            )
        }

        // TODO when short association
        result += GenerateFile(
            entity,
            "components/${dir}/${idSelect}.vue",
            stringify(idSelectComponent(entity, multiple = false)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdSelect),
        )
        result += GenerateFile(
            entity,
            "components/${dir}/${idMultiSelect}.vue",
            stringify(idSelectComponent(entity, multiple = true)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdMultiSelect),
        )

        if (entity.canAdd) {
            result += GenerateFile(
                entity,
                "rules/${dir}/${addFormRules}.ts",
                stringify(addFormRules(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.AddFormRules, GenerateTag.AddForm),
            )
        }

        if (entity.canEdit) {
            result += GenerateFile(
                entity,
                "rules/${dir}/${editFormRules}.ts",
                stringify(editFormRules(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.EditFormRules, GenerateTag.EditForm),
            )
        }

        result += GenerateFile(
            entity,
            "rules/${dir}/${editTableRules}.ts",
            stringify(editTableRules(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.EditTableRules, GenerateTag.EditTable),
        )

        return result
    }
}