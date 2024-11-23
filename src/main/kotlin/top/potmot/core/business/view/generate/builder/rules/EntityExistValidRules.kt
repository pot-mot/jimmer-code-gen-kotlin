package top.potmot.core.business.view.generate.builder.rules

import top.potmot.core.business.utils.existValidItems
import top.potmot.core.business.view.generate.meta.rules.ExistValidRule
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.error.ModelException

/**
 * 将 existValidItem 转换为 ExistValidRule
 *
 */
@Throws(
    ModelException.IdPropertyNotFound::class,
    ModelException.IndexRefPropertyNotFound::class,
    ModelException.IndexRefPropertyCannotBeList::class
)
fun GenEntityBusinessView.existValidRules(
    withId: Boolean,
    filterProperties: List<GenEntityBusinessView.TargetOf_properties> = properties,
): Map<GenEntityBusinessView.TargetOf_properties, List<ExistValidRule>> {
    val currentPropertyIds = filterProperties.map { it.id }.toSet()

    return existValidItems
        .flatMap { item ->
            item.properties.mapNotNull { property ->
                if (property.id !in currentPropertyIds)
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