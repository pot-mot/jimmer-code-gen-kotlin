package top.potmot.core.business.view.generate.impl.vue3elementPlus.select

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException

interface IdSelectGen : IdSelect, IdTreeSelect {
    @Throws(ModelException.TreeEntityCannotFoundParentProperty::class)
    private fun idSelectComponent(entity: EntityBusiness, multiple: Boolean): Component =
        if (entity.isTree) {
            createIdTreeSelect(entity, multiple)
        } else {
            createIdSelect(entity, multiple)
        }

    fun idSelectFiles(entity: EntityBusiness) = listOf(
        GenerateFile(
            entity,
            "components/${entity.dir}/${entity.components.idSelect}.vue",
            Vue3ElementPlusViewGenerator.stringify(idSelectComponent(entity, multiple = false)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdSelect),
        ),
        GenerateFile(
            entity,
            "components/${entity.dir}/${entity.components.idMultiSelect}.vue",
            Vue3ElementPlusViewGenerator.stringify(idSelectComponent(entity, multiple = true)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdMultiSelect),
        )
    )
}