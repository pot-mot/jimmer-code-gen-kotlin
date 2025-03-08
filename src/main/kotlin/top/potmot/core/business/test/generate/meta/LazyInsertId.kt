package top.potmot.core.business.test.generate.meta

import top.potmot.core.business.meta.SubEntityBusiness

data class LazyInsertId(
    val propertyId: Long,
    val entity: SubEntityBusiness,
    val name: String,
    val insertByService: Boolean = entity.canAdd,
)

data class ValueWithLazyInsertIds(
    val value: String,
    val lazyInsertIds: List<LazyInsertId> = emptyList(),
)

typealias PropertyValueResult = ValueWithLazyInsertIds
