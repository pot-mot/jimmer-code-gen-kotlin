package top.potmot.core.business.utils.entity

import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.IdName
import top.potmot.error.ModelException

val GenEntityBusinessView.idProperty
    @Throws(ModelException.IdPropertyNotFound::class)
    get() =
        if (idProperties.size != 1)
            throw ModelException.idPropertyNotFound("entityName: $name", entity = IdName(id, name))
        else
            idProperties[0]
