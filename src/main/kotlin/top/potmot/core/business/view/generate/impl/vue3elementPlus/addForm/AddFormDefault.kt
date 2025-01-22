package top.potmot.core.business.view.generate.impl.vue3elementPlus.addForm

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.type.typeStrToTypeScriptDefault
import top.potmot.error.ModelException

interface AddFormDefault {
    val PropertyBusiness.addFormDefault
        @Throws(ModelException.DefaultItemNotFound::class)
        get() =
            if (listType) {
                "[]"
            } else if (this is EnumProperty && typeNotNull) {
                "\"${enum.defaultItem.name}\""
            } else {
                if (this is AssociationProperty && isLongAssociation)
                    TODO("longAssociation addForm default")
                else
                    typeStrToTypeScriptDefault(type, typeNotNull)
            }
}