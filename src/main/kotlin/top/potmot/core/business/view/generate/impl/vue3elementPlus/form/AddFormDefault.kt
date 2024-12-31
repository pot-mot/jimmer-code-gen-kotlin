package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.utils.enums.defaultItem
import top.potmot.core.business.utils.type.typeStrToTypeScriptDefault
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.error.ModelException

interface AddFormDefault {
    val GenEntityBusinessView.TargetOf_properties.addFormDefault
        @Throws(ModelException.DefaultItemNotFound::class)
        get() =
            if (listType) {
                "[]"
            } else if (enum != null && typeNotNull) {
                "\"${enum.defaultItem.name}\""
            } else {
                typeStrToTypeScriptDefault(type, typeNotNull)
            }
}