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
        val result = mutableListOf<GenerateFile>()

        result += viewTableFile(entity)

        if (entity.canAdd) {
            result += addFormFiles(entity)
        }

        if (entity.canEdit) {
            result += editFormFiles(entity)
        }

        // TODO when long association
        result += editTableFiles(entity)

        if (entity.canQuery) {
            result += queryFormFile(entity)
        }

        if (entity.hasPage) {
            result += pageFile(entity)
        }

        // TODO when short association
        result += idSelectFiles(entity)

        return result
    }
}