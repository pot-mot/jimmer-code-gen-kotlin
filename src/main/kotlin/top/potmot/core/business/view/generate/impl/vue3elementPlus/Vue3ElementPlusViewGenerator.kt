package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.meta.LazyGenerateResult
import top.potmot.core.business.meta.LazyGenerated
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.view.generate.ViewGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.edit.AddFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.edit.EditFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.page.PageGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.query.QueryFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.view.ViewFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.view.ViewTableGen
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.entity.dto.GenerateFile

object Vue3ElementPlusViewGenerator :
    ViewGenerator,
    ViewTableGen,
    ViewFormGen,
    QueryFormGen,
    AddFormGen,
    EditFormGen,
    LazyGenerator,
    PageGen {
    override val indent = "    "

    override val wrapThreshold = 40

    override val componentBuilder = Vue3ComponentBuilder(indent, wrapThreshold)

    override val rulesBuilder = Vue3ElementPlusRulesBuilder(indent, wrapThreshold)

    override fun stringify(component: Component) = componentBuilder.build(component)

    private fun generate(
        entity: RootEntityBusiness,
    ): LazyGenerateResult? {
        if (!entity.hasPage || !entity.canQuery) return null

        val files = mutableListOf<GenerateFile>()
        val lazyItems = mutableListOf<LazyGenerated>()

        viewTableFiles(entity).let {
            files += it.files
            lazyItems += it.lazyItems
        }

        if (entity.pageCanAdd) {
            addFormFiles(entity).let {
                files += it.files
                lazyItems += it.lazyItems
            }
        }

        if (entity.pageCanEdit) {
            editFormFiles(entity).let {
                files += it.files
                lazyItems += it.lazyItems
            }
        }

        if (entity.pageCanViewDetail) {
            viewFormFiles(entity).let {
                files += it.files
                lazyItems += it.lazyItems
            }
        }

        if (entity.pageCanQuery) {
            queryFormFile(entity).let {
                files += it.files
                lazyItems += it.lazyItems
            }
        }

        files += pageFile(entity)

        return LazyGenerateResult(
            files,
            lazyItems
        )
    }

    override fun generateEntity(entities: Iterable<RootEntityBusiness>): List<GenerateFile> {
        val files = mutableListOf<GenerateFile>()
        val generatedKeySet = mutableSetOf<String>()

        entities.forEach { entity ->
            generate(entity)?.let {
                files += it.files
                files += generateLazy(it.lazyItems, generatedKeySet)
            }
        }

        return files
    }
}