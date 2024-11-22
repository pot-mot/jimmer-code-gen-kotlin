package top.potmot.core.business.utils

import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.IdName
import top.potmot.error.ModelException

val GenEntityBusinessView.TargetOf_properties.TargetOf_enum.defaultItem: GenEntityBusinessView.TargetOf_properties.TargetOf_enum.TargetOf_items
    @Throws(ModelException.DefaultItemNotFound::class)
    get() {
        val defaultItem = items.firstOrNull { it.defaultItem }

        if (defaultItem == null)
            throw ModelException.defaultItemNotFound("enumName: $name", enum = IdName(id, name))

        return defaultItem
    }