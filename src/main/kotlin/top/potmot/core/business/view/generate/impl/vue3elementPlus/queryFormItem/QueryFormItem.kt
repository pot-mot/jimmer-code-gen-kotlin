package top.potmot.core.business.view.generate.impl.vue3elementPlus.queryFormItem

import top.potmot.core.business.utils.PropertyFormType
import top.potmot.core.business.utils.PropertyQueryType
import top.potmot.core.business.utils.componentNames
import top.potmot.core.business.utils.formType
import top.potmot.core.business.utils.queryType
import top.potmot.core.business.view.generate.builder.rules.numberMax
import top.potmot.core.business.view.generate.builder.rules.numberMin
import top.potmot.core.business.view.generate.builder.rules.numberPrecision
import top.potmot.core.business.view.generate.builder.vue3.Element
import top.potmot.core.business.view.generate.builder.vue3.EventBind
import top.potmot.core.business.view.generate.builder.vue3.VModel
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.datePickerRange
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.dateTimePickerRange
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.input
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.inputNumber
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.option
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.select
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.timePickerRange
import top.potmot.entity.dto.GenEntityBusinessView

private val queryOnChange = EventBind("change", "emits('query')")

interface QueryFormItem {
    fun GenEntityBusinessView.TargetOf_properties.createQueryFormItem(spec: String): Element {
        val modelValue = "$spec.${name}"
        val rangeModelValue = "${name}Range"
        val minModelValue = "$spec.min${name.replaceFirstChar { c -> c.uppercaseChar() }}"
        val maxModelValue = "$spec.max${name.replaceFirstChar { c -> c.uppercaseChar() }}"
        val numberMin = numberMin
        val numberMax = numberMax

        val elements = when (queryType) {
            PropertyQueryType.ASSOCIATION_ID_EQ ->
                listOf(
                    Element(
                        typeEntity!!.componentNames.idSelect,
                        directives = listOf(VModel(modelValue)),
                    )
                )

            PropertyQueryType.ASSOCIATION_ID_IN ->
                listOf(
                    Element(
                        typeEntity!!.componentNames.idMultiSelect,
                        directives = listOf(VModel(modelValue)),
                    )
                )

            PropertyQueryType.ENUM_SELECT ->
                listOf(
                    Element(
                        enum!!.componentNames.nullableSelect,
                        directives = listOf(VModel(modelValue)),
                    )
                )

            PropertyQueryType.INT_RANGE ->
                listOf(
                    inputNumber(
                        minModelValue,
                        comment = "最小$comment",
                        precision = 0,
                        min = numberMin,
                        max = numberMax,
                    ),
                    inputNumber(
                        maxModelValue,
                        comment = "最大$comment",
                        precision = 0,
                        min = numberMin,
                        max = numberMax,
                    )
                )

            PropertyQueryType.FLOAT_RANGE ->
                listOf(
                    inputNumber(
                        minModelValue,
                        comment = "最小$comment",
                        precision = numberPrecision ?: 0,
                        min = numberMin,
                        max = numberMax,
                    ),
                    inputNumber(
                        maxModelValue,
                        comment = "最大$comment",
                        precision = numberPrecision ?: 0,
                        min = numberMin,
                        max = numberMax,
                    )
                )

            PropertyQueryType.TIME_RANGE ->
                listOf(
                    timePickerRange(
                        rangeModelValue,
                        comment = comment,
                    )
                )

            PropertyQueryType.DATE_RANGE ->
                listOf(
                    datePickerRange(
                        rangeModelValue,
                        comment = comment,
                    )
                )

            PropertyQueryType.DATETIME_RANGE ->
                listOf(
                    dateTimePickerRange(
                        rangeModelValue,
                        comment = comment,
                    )
                )

            else ->
                when (formType) {
                    PropertyFormType.SWITCH ->
                        listOf(
                            select(
                                modelValue,
                                comment = comment,
                                content = listOf(
                                    option("false", "否"),
                                    option("true", "是"),
                                )
                            )
                        )

                    else ->
                        listOf(
                            input(
                                modelValue,
                                comment = comment,
                            )
                        )
                }
        }

        return formItem(
            prop = name,
            label = comment,
            content = elements.map {
                it.merge {
                    events += listOf(queryOnChange)
                }
            }
        )
    }
}