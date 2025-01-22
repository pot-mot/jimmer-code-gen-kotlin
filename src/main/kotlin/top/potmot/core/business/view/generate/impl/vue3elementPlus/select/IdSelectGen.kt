package top.potmot.core.business.view.generate.impl.vue3elementPlus.select

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.error.ModelException

interface IdSelectGen : IdSelect, IdTreeSelect {
    @Throws(ModelException.TreeEntityCannotFoundParentProperty::class)
    fun idSelectComponent(entity: EntityBusiness, multiple: Boolean): Component =
        if (entity.isTree) {
            createIdTreeSelect(entity, multiple)
        } else {
            createIdSelect(entity, multiple)
        }
}