package top.potmot.core.business.view.generate.impl.vue3elementPlus.queryFormItem

import top.potmot.core.business.utils.PropertyFormType
import top.potmot.core.business.utils.PropertyQueryType
import top.potmot.core.business.utils.componentNames
import top.potmot.core.business.utils.formType
import top.potmot.core.business.utils.queryType
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.meta.rules.numberMax
import top.potmot.core.business.view.generate.meta.rules.numberMin
import top.potmot.core.business.view.generate.meta.rules.numberPrecision
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VModel
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.datePickerRange
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.dateTimePickerRange
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.input
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.inputNumber
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.option
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.select
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.timePickerRange
import top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem.FormItemData
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.entity.dto.GenEntityBusinessView

interface QueryFormItem {
    fun GenEntityBusinessView.TargetOf_properties.createQueryFormItem(spec: String): FormItemData {
        val modelValue = "$spec.${name}"
        val rangeModelValue = "${name}Range"
        val minModelValue = "$spec.min${name.replaceFirstChar { c -> c.uppercaseChar() }}"
        val maxModelValue = "$spec.max${name.replaceFirstChar { c -> c.uppercaseChar() }}"
        val numberMin = numberMin
        val numberMax = numberMax

        return when (queryType) {
            PropertyQueryType.ASSOCIATION_ID_EQ ->
                if (typeEntity == null) {
                    FormItemData()
                } else {
                    val componentName = typeEntity.componentNames.idSelect
                    FormItemData(
                        elements = listOf(
                            TagElement(
                                componentName,
                                directives = listOf(VModel(modelValue)),
                            )
                        ),
                        imports = listOf(
                            ImportDefault(
                                componentPath + "/" + typeEntity.name.replaceFirstChar { it.lowercase() } + "/" + componentName + ".vue",
                                componentName,
                            )
                        )
                    )
                }

            PropertyQueryType.ASSOCIATION_ID_IN ->
                if (typeEntity == null) {
                    FormItemData()
                } else {
                    val componentName = typeEntity.componentNames.idMultiSelect
                    FormItemData(
                        elements = listOf(
                            TagElement(
                                componentName,
                                directives = listOf(VModel(modelValue)),
                            )
                        ),
                        imports = listOf(
                            ImportDefault(
                                componentPath + "/" + typeEntity.name.replaceFirstChar { it.lowercase() } + "/" + componentName + ".vue",
                                componentName,
                            )
                        )
                    )
                }

            PropertyQueryType.ENUM_SELECT ->
                if (enum == null) {
                    FormItemData()
                } else {
                    val componentName = enum.componentNames.nullableSelect

                    FormItemData(
                        elements = listOf(
                            TagElement(
                                componentName,
                                directives = listOf(VModel(modelValue)),
                            )
                        ),
                        imports = listOf(
                            ImportDefault(
                                componentPath + "/" + enum.name.replaceFirstChar { it.lowercase() } + "/" + componentName + ".vue",
                                componentName,
                            )
                        )
                    )
                }

            PropertyQueryType.INT_RANGE ->
                FormItemData(
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
                FormItemData(
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
                FormItemData(
                    timePickerRange(
                        rangeModelValue,
                        comment = comment,
                    )
                )

            PropertyQueryType.DATE_RANGE ->
                FormItemData(
                    datePickerRange(
                        rangeModelValue,
                        comment = comment,
                    )
                )

            PropertyQueryType.DATETIME_RANGE ->
                FormItemData(
                    dateTimePickerRange(
                        rangeModelValue,
                        comment = comment,
                    )
                )

            else ->
                when (formType) {
                    PropertyFormType.SWITCH ->
                        FormItemData(
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
                        FormItemData(
                            input(
                                modelValue,
                                comment = comment,
                            )
                        )
                }
        }
    }
}