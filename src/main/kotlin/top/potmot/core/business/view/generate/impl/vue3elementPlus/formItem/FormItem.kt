package top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem

import top.potmot.core.business.utils.PropertyFormType
import top.potmot.core.business.utils.componentNames
import top.potmot.core.business.utils.formType
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.datePicker
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.dateTimePicker
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.input
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.inputNumber
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.option
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.select
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.switch
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.timePicker
import top.potmot.core.business.view.generate.meta.rules.numberMax
import top.potmot.core.business.view.generate.meta.rules.numberMin
import top.potmot.core.business.view.generate.meta.rules.numberPrecision
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VModel
import top.potmot.core.business.view.generate.meta.vue3.toPropBind
import top.potmot.entity.dto.GenEntityBusinessView

interface FormItem {
    fun GenEntityBusinessView.TargetOf_properties.createFormItem(
        formData: String,
        disabled: Boolean = false,
    ): FormItemData {
        val modelValue = "$formData.${name}"
        val numberMin = numberMin
        val numberMax = numberMax

        return when (formType) {
            PropertyFormType.ASSOCIATION_ID ->
                if (typeEntity == null) {
                    FormItemData()
                } else {
                    val components = typeEntity.componentNames
                    val dir = components.dir
                    val componentName = components.idSelect
                    FormItemData(
                        elements = listOf(
                            TagElement(
                                componentName,
                                directives = listOf(VModel(modelValue)),
                                props = listOfNotNull(
                                    PropBind("options", "${name}Options"),
                                    disabled.toPropBind("disabled")
                                ),
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

            PropertyFormType.ASSOCIATION_ID_LIST ->
                if (typeEntity == null) {
                    FormItemData()
                } else {
                    val components = typeEntity.componentNames
                    val dir = components.dir
                    val componentName = components.idMultiSelect
                    FormItemData(
                        elements = listOf(
                            TagElement(
                                componentName,
                                directives = listOf(VModel(modelValue)),
                                props = listOfNotNull(
                                    PropBind("options", "${name}Options"),
                                    disabled.toPropBind("disabled")
                                ),
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

            PropertyFormType.ENUM ->
                if (enum == null) {
                    FormItemData()
                } else {
                    val components = enum.componentNames
                    val dir = components.dir
                    val componentName = if (typeNotNull) components.select else components.nullableSelect

                    FormItemData(
                        elements = listOf(
                            TagElement(
                                componentName,
                                directives = listOf(VModel(modelValue)),
                                props = listOfNotNull(
                                    disabled.toPropBind("disabled")
                                ),
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


            PropertyFormType.SWITCH ->
                FormItemData(
                    if (typeNotNull)
                        switch(
                            modelValue,
                            disabled = disabled,
                        )
                    else
                        select(
                            modelValue,
                            comment = comment,
                            filterable = false,
                            disabled = disabled,
                            content = listOf(
                                option("true", "是", true),
                                option("false", "否", true)
                            )
                        )
                )

            PropertyFormType.INT ->
                FormItemData(
                    if (typeNotNull)
                        inputNumber(
                            modelValue,
                            comment = comment,
                            precision = 0,
                            min = numberMin,
                            max = numberMax,
                            valueOnClear = numberMin?.toString(),
                            disabled = disabled,
                        )
                    else
                        inputNumber(
                            modelValue,
                            comment = comment,
                            precision = 0,
                            min = numberMin,
                            max = numberMax,
                            disabled = disabled,
                        )
                )

            PropertyFormType.FLOAT ->
                FormItemData(
                    if (typeNotNull)
                        inputNumber(
                            modelValue,
                            comment = comment,
                            precision = numberPrecision,
                            min = numberMin,
                            max = numberMax,
                            valueOnClear = numberMin?.toString(),
                            disabled = disabled,
                        )
                    else
                        inputNumber(
                            modelValue,
                            comment = comment,
                            precision = numberPrecision,
                            min = numberMin,
                            max = numberMax,
                            disabled = disabled,
                        )
                )


            PropertyFormType.TIME ->
                FormItemData(
                    if (typeNotNull)
                        timePicker(
                            modelValue,
                            comment = comment,
                            disabled = disabled,
                        )
                    else
                        timePicker(
                            modelValue,
                            comment = comment,
                            clearable = true,
                            disabled = disabled,
                        )
                )

            PropertyFormType.DATE ->
                FormItemData(
                    if (typeNotNull)
                        datePicker(
                            modelValue,
                            comment = comment,
                            disabled = disabled,
                        )
                    else
                        timePicker(
                            modelValue,
                            comment = comment,
                            clearable = true,
                            disabled = disabled,
                        )
                )

            PropertyFormType.DATETIME ->
                FormItemData(
                    if (typeNotNull)
                        dateTimePicker(
                            modelValue,
                            comment = comment,
                            disabled = disabled,
                        )
                    else
                        dateTimePicker(
                            modelValue,
                            comment = comment,
                            clearable = true,
                            disabled = disabled,
                        )
                )

            else ->
                FormItemData(
                    input(
                        modelValue,
                        comment = comment,
                        clearable = true,
                        disabled = disabled,
                    )
                )
        }
    }
}