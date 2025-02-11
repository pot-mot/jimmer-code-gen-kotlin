package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.meta.EnumBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.view.generate.ViewGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.AddFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.EditFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.enumSelect.EnumSelectGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.enumView.EnumViewGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.SubFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.page.PageGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.queryForm.QueryFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.select.IdSelectGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.ViewTableGen
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
    SubFormGen,
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
        entity: RootEntityBusiness
    ): List<GenerateFile> {
        if (!entity.hasPage) return emptyList()

        val result = mutableListOf<GenerateFile>()

        result += viewTableFile(entity)

        if (entity.pageCanAdd) {
            result += addFormFiles(entity)
        }

        if (entity.pageCanEdit) {
            result += editFormFiles(entity)
        }

        if (entity.pageCanAdd || entity.pageCanEdit) {
            result += deepSubFormFiles(entity)
        }

        if (entity.pageCanViewDetail) {
            // TODO viewDetail
        }

        if (entity.pageCanQuery) {
            result += queryFormFile(entity)
        }

        if (entity.pageCanAdd || entity.pageCanEdit || entity.pageCanQuery) {
            result += idSelectFiles(entity)
        }

        result += pageFile(entity)

        return result.distinct()
    }
}