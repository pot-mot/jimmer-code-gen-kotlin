package top.potmot.core.business.view.generate.impl.vue3elementPlus.view

import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.descriptionsItem
import top.potmot.core.business.view.generate.meta.vue3.ExpressionElement
import top.potmot.core.config.getContextOrGlobal

interface ViewFormItem : ViewItem {
    fun PropertyBusiness.viewFormItem(
        withDateTimeFormat: Boolean = getContextOrGlobal().dateTimeFormatInView,
    ): List<ViewItemData> =
        viewItem(
            { name -> "value.$name" },
            { property ->
                ViewItemData(
                    label = property.comment,
                    prop = property.name,
                    ExpressionElement("value.${property.name}")
                )
            },
            withDateTimeFormat,
        ).let { viewItem ->
            if (viewItem.shortViews.isNotEmpty()) {
                viewItem.flatShortViews.map { (_, shortViewItem) ->
                    shortViewItem
                }
            } else {
                listOf(viewItem)
            }
        }

    fun Iterable<ViewItemData>.toElements() = map {
        descriptionsItem(
            label = it.label,
            content = it.elements
        )
    }
}