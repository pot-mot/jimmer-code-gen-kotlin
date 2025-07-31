package top.potmot.core.business.view.generate.impl.vue3elementPlus.view

import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.descriptionsItem
import top.potmot.core.common.vue3.ExpressionElement
import top.potmot.core.config.getContextOrGlobal
import kotlin.collections.listOf

private val createPropExpressionElement: (Collection<PropertyBusiness>) -> ExpressionElement = { properties ->
    var beforeNullable = false
    val propExpression = buildString {
        append("value")
        for (property in properties) {
            if (beforeNullable) {
                append("?")
            }
            append(".")
            append(property.name)
            beforeNullable = !property.typeNotNull
        }
    }
    ExpressionElement(propExpression)
}


interface ViewFormItem : ViewItem {
    fun PropertyBusiness.viewFormItem(
        withDateTimeFormat: Boolean = getContextOrGlobal().dateTimeFormatInView,
    ): List<ViewItemData> =
        viewItem(
            nameToValue = { name -> "value.$name" },
            createDefault = { properties ->
                ViewItemData(
                    label = properties.lastOrNull()?.comment ?: "",
                    properties,
                    createPropExpressionElement(properties)
                )
            },
            withDateTimeFormat,
        ).let { viewItem ->
            if (viewItem.shortViews.isNotEmpty()) {
                viewItem.flatShortViews.map { (_, shortViewItem) ->
                    shortViewItem.copy(
                        elements = listOf(createPropExpressionElement(shortViewItem.properties))
                    )
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