package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.utils.defaultItems
import top.potmot.core.business.utils.typeStrToTypeScriptDefault
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.error.GenerateException

interface AddFormDefault {
    val GenEntityBusinessView.TargetOf_properties.addFormDefault
        @Throws(GenerateException.DefaultItemNotFound::class)
        get() =
            if (listType) {
                "[]"
            } else if (enum != null) {
                if (enum.defaultItems.size != 1)
                    throw GenerateException.defaultItemNotFound("enumName: ${enum.name}")

                "\"${enum.defaultItems[0].name}\""
            } else {
                typeStrToTypeScriptDefault(type, typeNotNull)
            }
}