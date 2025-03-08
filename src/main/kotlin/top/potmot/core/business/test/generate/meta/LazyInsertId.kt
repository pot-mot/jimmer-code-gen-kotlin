package top.potmot.core.business.test.generate.meta

import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.utils.string.StringIndentScopeBuilder

data class LazyInsertId(
    val propertyId: Long,
    val entity: SubEntityBusiness,
    val name: String,
    val insertByService: Boolean = entity.canAdd,
)

typealias BuilderAction = StringIndentScopeBuilder.() -> Unit

fun StringIndentScopeBuilder.append(builderAction: BuilderAction) {
    apply { builderAction() }
}

sealed interface PropertyValueResult

data class RawPropertyValueResult(
    val value: String,
) : PropertyValueResult

data class ActionPropertyValueResult(
    val lazyInsertIds: List<LazyInsertId> = emptyList(),
    val action: BuilderAction,
) : PropertyValueResult

typealias BuilderAction_WithLazyInsertIds = ActionPropertyValueResult

fun StringIndentScopeBuilder.append(propertyValueResult: PropertyValueResult): List<LazyInsertId> =
    when (propertyValueResult) {
        is RawPropertyValueResult -> {
            append(propertyValueResult.value)
            emptyList()
        }
        is ActionPropertyValueResult -> {
            append(propertyValueResult.action)
            propertyValueResult.lazyInsertIds
        }
    }
