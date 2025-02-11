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
    filterProperties: List<PropertyBusiness> = properties,
): Map<PropertyBusiness, List<ExistValidRule>> {
    val filterPropertyIds = filterProperties.map { it.property.id }.toSet()

    return existValidItems
        .flatMap { item ->
            item.properties.mapNotNull { property ->
                if (property.id !in filterPropertyIds)
                    null
                else
                    ExistValidRule(
                        item,
                        property,
                        this,
                        withId
                    )
            }
        }
        .groupBy { it.property }
}