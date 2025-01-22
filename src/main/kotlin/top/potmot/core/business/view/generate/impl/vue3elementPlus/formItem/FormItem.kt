package top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem

import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.PropertyFormType
import top.potmot.core.business.meta.TypeEntityProperty
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.datePicker
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.dateTimePicker
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.input
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.inputNumber
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.option
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.select
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.switch
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.timePicker
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VModel
import top.potmot.core.business.view.generate.meta.vue3.toPropBind

interface FormItem {
    fun PropertyBusiness.createFormItem(
        formData: String,
        disabled: Boolean = false,
        excludeSelf: Boolean = false,
        entityId: Long? = null,
        idName: String? = null,
    ): FormItemData {
        val modelValue = "$formData.${name}"

        return when (this) {
            is TypeEntityProperty -> {
                val components = typeEntityBusiness.components
                val dir = typeEntityBusiness.dir
                val componentName = if (listType) components.idMultiSelect else components.idSelect
                FormItemData(
                    elements = listOf(
                        TagElement(
                            componentName,
                            directives = listOf(VModel(modelValue)),
                            props = listOfNotNull(
                                PropBind("options", "${name}Options"),
                                if (excludeSelf && typeEntity.id == entityId && idName != null)
                                    PropBind("exclude-ids", "[${formData}.${idName}]")
                                else
                                    null,
                                disabled.toPropBind("disabled"),
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

            is EnumProperty -> {
                val components = enum.components
                val dir = enum.dir
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

            else -> {
                when (formType) {
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
                                    valueOnClear = numberMin,
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
                                    precision = numericPrecision,
                                    min = numberMin,
                                    max = numberMax,
                                    valueOnClear = numberMin,
                                    disabled = disabled,
                                )
                            else
                                inputNumber(
                                    modelValue,
                                    comment = comment,
                                    precision = numericPrecision,
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
                                datePicker(
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

                    PropertyFormType.INPUT ->
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
    }
}