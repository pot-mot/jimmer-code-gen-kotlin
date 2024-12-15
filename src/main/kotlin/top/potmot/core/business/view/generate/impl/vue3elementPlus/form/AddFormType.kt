package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.utils.typeStrToTypeScriptType
import top.potmot.entity.dto.GenEntityBusinessView

interface AddFormType {
    val GenEntityBusinessView.TargetOf_properties.addFormType: String
        get() {
            if (enum != null) {
                return enum.name
            }

            val baseType = if (associationType == null)
                typeStrToTypeScriptType(type, typeNotNull)
            else
                typeStrToTypeScriptType(type, typeNotNull)

            return if (listType) {
                "Array<$baseType>"
            } else {
                baseType
            }
        }

}