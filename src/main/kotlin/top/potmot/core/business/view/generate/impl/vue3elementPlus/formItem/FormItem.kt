package top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem

import top.potmot.core.business.utils.PropertyFormType
import top.potmot.core.business.utils.componentNames
import top.potmot.core.business.utils.formType
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
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VModel
import top.potmot.core.business.view.generate.meta.vue3.toPropBind
import top.potmot.entity.dto.GenEntityBusinessView

interface FormItem {
    fun GenEntityBusinessView.TargetOf_properties.createFormItem(
        formData: String,
        disabled: Boolean = false,
    ): List<TagElement> {
        val modelValue = "$formData.${name}"
        val numberMin = numberMin
        val numberMax = numberMax

        return when (formType) {
            PropertyFormType.ASSOCIATION_ID ->
                listOfNotNull(
                    typeEntity?.let {
                        TagElement(
                            it.componentNames.idSelect,
                            directives = listOf(VModel(modelValue)),
                            props = listOfNotNull(
                                disabled.toPropBind("disabled")
                            ),
                        )
                    }
                )

            PropertyFormType.ASSOCIATION_LIST ->
                listOfNotNull(
                    typeEntity?.let {
                        TagElement(
                            it.componentNames.idMultiSelect,
                            directives = listOf(VModel(modelValue)),
                            props = listOfNotNull(
                                disabled.toPropBind("disabled")
                            ),
                        )
                    }
                )

            PropertyFormType.ENUM ->
                listOfNotNull(
                    enum?.let {
                        TagElement(
                            if (typeNotNull) it.componentNames.select else it.componentNames.nullableSelect,
                            directives = listOf(VModel(modelValue)),
                            props = listOfNotNull(
                                disabled.toPropBind("disabled")
                            ),
                        )
                    }
                )

            PropertyFormType.SWITCH ->
                listOf(
                    if (typeNotNull)
                        switch(
                            modelValue,
                            disabled = disabled,
                        )
                    else
                        select(
                            modelValue,
                            comment = comment,
                            disabled = disabled,
                            content = listOf(
                                option("true", "是", true),
                                option("false", "否", true)
                            )
                        )
                )

            PropertyFormType.INT ->
                listOf(
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
                listOf(
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
                listOf(
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
                listOf(
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
                listOf(
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
                listOf(
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