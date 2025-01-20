package top.potmot.core.business.utils.property

import top.potmot.entity.dto.GenEntityBusinessView

val GenEntityBusinessView.TargetOf_properties.nameOrWithId
    get() = if (associationType != null && !idView)
        "${name}${if (listType) "Ids" else "Id"}"
    else
        name