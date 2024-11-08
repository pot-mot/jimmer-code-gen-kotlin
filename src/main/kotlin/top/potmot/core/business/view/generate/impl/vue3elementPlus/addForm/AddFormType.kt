package top.potmot.core.business.view.generate.impl.vue3elementPlus.addForm

import top.potmot.core.business.utils.typeStrToTypeScriptType
import top.potmot.entity.dto.GenEntityBusinessView

interface AddFormType {
    val GenEntityBusinessView.TargetOf_properties.addFormType
        get() =
            if (associationType == null)
                typeStrToTypeScriptType(type, typeNotNull)
            else
                if (!listType && typeNotNull)
                    typeStrToTypeScriptType(type, false)
                else
                    typeStrToTypeScriptType(type, typeNotNull)
}