package top.potmot.core.business.test.generate.meta

import top.potmot.core.business.meta.SubEntityBusiness

data class LazyInsertId(
    val entity: SubEntityBusiness,
    val name: String,
    val insertByService: Boolean = entity.canAdd,
)