package top.potmot.core.business.view.generate.impl.vue3elementPlus.queryFormItem

import top.potmot.core.business.property.PropertyFormType
import top.potmot.core.business.property.PropertyQueryType
import top.potmot.core.business.utils.mark.components
import top.potmot.core.business.utils.mark.dir
import top.potmot.core.business.property.formType
import top.potmot.core.business.property.queryType
import top.potmot.core.business.utils.mark.upperName
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.datePickerRange
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.dateTimePickerRange
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.input
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.inputNumber
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.option
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.select
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.timePickerRange
import top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem.FormItemData
import top.potmot.core.business.property.numberMax
import top.potmot.core.business.property.numberMin
import top.potmot.core.business.property.numberPrecision
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VModel
import top.potmot.entity.dto.GenEntityBusinessView

interface QueryFormItem {
    fun GenEntityBusinessView.TargetOf_properties.createQueryFormItem(spec: String): FormItemData {
        val modelValue = "$spec.${name}"
        val rangeModelValue = "${name}Range"
        val minModelValue = "$spec.min${upperName}"
        val maxModelValue = "$spec.max${upperName}"
        val numberMin by lazy { numberMin }
        val numberMax by lazy { numberMax }

        val rangeComputed by lazy {
            CodeBlock(
                """
                    const ${name}Range = computed<[string | undefined, string | undefined]>({
                        get() {
                            return [
                                $spec.value.min${upperName},
                                $spec.value.max${upperName},
                           ]
                        },
                        set(range: [string | undefined, string | undefined] | null) {
                            $spec.value.min${upperName} = range?.[0]
                            $spec.value.max${upperName} = range?.[1]
                        }
                    })
                    """.trimIndent()
            )
        }

        return when (queryType) {
            PropertyQueryType.ASSOCIATION_ID_EQ ->
                if (typeEntity == null) {
                    FormItemData()
                } else {
                    val components = typeEntity.components
                    val dir = typeEntity.dir
                    val componentName = components.idSelect
                    FormItemData(
                        elements = listOf(
                            TagElement(
                                componentName,
                                directives = listOf(VModel(modelValue)),
                                props = listOf(
                                    PropBind("options", "${name}Options"),
                                )
                            )
                        ),
                        imports = listOf(
                            ImportDefault(
                                "$componentPath/$dir/$componentName.vue",
                                componentName,
                            )
                        )
                    )
                }

            PropertyQueryType.ASSOCIATION_ID_IN ->
                if (typeEntity == null) {
                    FormItemData()
                } else {
                    val dir = typeEntity.dir
                    val componentName = typeEntity.components.idMultiSelect
                    FormItemData(
                        elements = listOf(
                            TagElement(
                                componentName,
                                directives = listOf(VModel(modelValue)),
                                props = listOf(
                                    PropBind("options", "${name}Options"),
                                )
                            )
                        ),
                        imports = listOf(
                            ImportDefault(
                                "$componentPath/$dir/$componentName.vue",
                                componentName,
                            )
                        )
                    )
                }

            PropertyQueryType.ENUM_SELECT ->
                if (enum == null) {
                    FormItemData()
                } else {
                    val dir = enum.dir
                    val componentName = enum.components.nullableSelect

                    FormItemData(
                        elements = listOf(
                            TagElement(
                                componentName,
                                directives = listOf(VModel(modelValue)),
                            )
                        ),
                        imports = listOf(
                            ImportDefault(
                                "$componentPath/$dir/$componentName.vue",
                                componentName,
                            )
                        )
                    )
                }

            PropertyQueryType.INT_RANGE ->
                FormItemData(
                    inputNumber(
                        minModelValue,
                        placeholder = { "最小" },
                        precision = 0,
                        min = numberMin,
                        max = numberMax,
                    ),
                    inputNumber(
                        maxModelValue,
                        placeholder = { "最大" },
                        precision = 0,
                        min = numberMin,
                        max = numberMax,
                    )
                )

            PropertyQueryType.FLOAT_RANGE ->
                FormItemData(
                    inputNumber(
                        minModelValue,
                        placeholder = { "最小" },
                        precision = numberPrecision ?: 0,
                        min = numberMin,
                        max = numberMax,
                    ),
                    inputNumber(
                        maxModelValue,
                        placeholder = { "最大" },
                        precision = numberPrecision ?: 0,
                        min = numberMin,
                        max = numberMax,
                    )
                )

            PropertyQueryType.TIME_RANGE ->
                FormItemData(
                    imports = listOf(
                        Import("vue", "computed")
                    ),
                    scripts = listOf(
                        rangeComputed
                    ),
                    elements = listOf(
                        timePickerRange(
                            rangeModelValue,
                            comment = comment,
                        )
                    )
                )

            PropertyQueryType.DATE_RANGE ->
                FormItemData(
                    imports = listOf(
                        Import("vue", "computed")
                    ),
                    scripts = listOf(
                        rangeComputed
                    ),
                    elements = listOf(
                        datePickerRange(
                            rangeModelValue,
                            comment = comment,
                        )
                    )
                )

            PropertyQueryType.DATETIME_RANGE ->
                FormItemData(
                    imports = listOf(
                        Import("vue", "computed")
                    ),
                    scripts = listOf(
                        rangeComputed
                    ),
                    elements = listOf(
                        dateTimePickerRange(
                            rangeModelValue,
                            comment = comment,
                        )
                    )
                )

            else ->
                when (formType) {
                    PropertyFormType.SWITCH ->
                        FormItemData(
                            select(
                                modelValue,
                                comment = comment,
                                filterable = false,
                                content = listOf(
                                    option("true", "是", true),
                                    option("false", "否", true)
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