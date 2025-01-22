package top.potmot.core.business.view.generate.impl.vue3elementPlus.addForm

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.type.typeStrToTypeScriptType

interface AddFormType {
    val PropertyBusiness.addFormType: String
        get() {
            if (this is EnumProperty) {
                return typeStrToTypeScriptType(enum.name, typeNotNull)
            }

            val baseType = if (this is AssociationProperty && isLongAssociation)
                TODO("longAssociation addForm type")
            else
                typeStrToTypeScriptType(type, typeNotNull)

            return if (listType) {
                "Array<$baseType>"
            } else {
                baseType
            }
        }

}