package top.potmot.core.business.view.generate.builder.rules

import top.potmot.core.business.utils.existValidItems
import top.potmot.core.business.view.generate.meta.rules.ExistValidRule
import top.potmot.entity.dto.GenEntityBusinessView

fun GenEntityBusinessView.existValidRules(withId: Boolean): Map<String, List<ExistValidRule>> =
    existValidItems.flatMap { item ->
        item.properties.map { property ->
            ExistValidRule(
                item,
                property,
                this,
                withId
            )
        }
    }.groupBy { it.property.name }