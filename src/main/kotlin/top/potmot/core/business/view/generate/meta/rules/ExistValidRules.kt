package top.potmot.core.business.view.generate.meta.rules

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.error.ModelException

/**
 * 将 ExistValidItem 转换为 ExistValidRule
 *
 */
@Throws(
    ModelException.IdPropertyNotFound::class,
    ModelException.IndexRefPropertyNotFound::class,
    ModelException.IndexRefPropertyCannotBeList::class
)
fun EntityBusiness.existValidRules(
    withId: Boolean,
    filterProperties: List<PropertyBusiness>,
): Map<PropertyBusiness, List<ExistValidRule>> {
    val filterPropertyIdMap = filterProperties.associateBy { it.property.id }
    val existValidRules = mutableListOf<ExistValidRule>()

    for (item in existValidItems) {
        if (item.properties.any { it.id !in filterPropertyIdMap }) continue

        for (property in item.properties) {
            existValidRules += ExistValidRule(
                item,
                filterPropertyIdMap[property.id]!!,
                this,
                withId
            )
        }
    }

    return existValidRules.groupBy { it.property }
}