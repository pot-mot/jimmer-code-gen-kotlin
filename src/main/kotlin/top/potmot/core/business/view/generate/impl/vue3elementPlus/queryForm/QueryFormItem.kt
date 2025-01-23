package top.potmot.core.business.view.generate.impl.vue3elementPlus.queryForm

import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.PropertyFormType
import top.potmot.core.business.meta.PropertyQueryType
import top.potmot.core.business.meta.TypeEntityProperty
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.datePickerRange
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.dateTimePickerRange
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.input
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.inputNumber
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.option
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.select
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.timePickerRange
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.FormItemData
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VModel

interface QueryFormItem {
    fun PropertyBusiness.createQueryFormItem(spec: String): FormItemData {
        val modelValue = "$spec.${name}"
        val rangeModelValue = "${name}Range"
        val minModelValue = "$spec.min${upperName}"
        val maxModelValue = "$spec.max${upperName}"

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

        return when (this) {
            is TypeEntityProperty -> {
                val components = typeEntityBusiness.components
                val component = if (listType) components.idMultiSelect else components.idSelect
                FormItemData(
                    elements = listOf(
                        TagElement(
                            component.name,
                            directives = listOf(VModel(modelValue)),
                            props = listOf(
                                PropBind("options", "${name}Options"),
                            )
                        )
                    ),
                    imports = listOf(
                        ImportDefault(
                            "@/${component.fullPath}",
                            component.name,
                        )
                    )
                )
            }

            is EnumProperty -> {
                val component = enum.components.nullableSelect

                FormItemData(
                    elements = listOf(
                        TagElement(
                            component.name,
                            directives = listOf(VModel(modelValue)),
                        )
                    ),
                    imports = listOf(
                        ImportDefault(
                            "@/${component.fullPath}",
                            component.name,
                        )
                    )
                )
            }

            else -> when (queryType) {
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
                            precision = numericPrecision ?: 0,
                            min = numberMin,
                            max = numberMax,
                        ),
                        inputNumber(
                            maxModelValue,
                            placeholder = { "最大" },
                            precision = numericPrecision ?: 0,
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

                else -> when (formType) {
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
}