package top.potmot.core.business.view.generate.impl.vue3elementPlus.idSelect

import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.common.vue3.Component
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException

interface IdSelectGen : Generator, IdSelect, IdTreeSelect {
    @Throws(ModelException.TreeEntityCannotFoundParentProperty::class)
    private fun idSelectComponent(entity: SubEntityBusiness, multiple: Boolean): Component =
        if (entity.isTree) {
            createIdTreeSelect(entity, multiple)
        } else {
            createIdSelect(entity, multiple)
        }

    fun idSelectFile(entity: SubEntityBusiness, multiple: Boolean) = GenerateFile(
        entity,
        if (multiple) entity.components.idMultiSelect.fullPath else entity.components.idSelect.fullPath,
        stringify(idSelectComponent(entity, multiple)),
        listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdSelect),
    )
}