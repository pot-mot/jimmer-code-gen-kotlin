package top.potmot.core.business.view.generate.impl.vue3elementPlus.form

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.CommonProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.PropertyFormType
import top.potmot.core.business.meta.TypeEntityProperty
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.datePicker
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.dateTimePicker
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.input
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.inputNumber
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.option
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.select
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.switch
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.timePicker
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.vue3.Element
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VModel
import top.potmot.core.business.view.generate.meta.vue3.toPropBind

interface FormItem {
    fun PropertyBusiness.toFormItemData(
        formData: String,
        disabled: Boolean = false,
        excludeSelf: Boolean = false,
        entityId: Long? = null,
        idName: String? = null,
    ): FormItemData {
        val modelValue = "$formData.${name}"

        return when (this) {
            is TypeEntityProperty -> {
                if (this is AssociationProperty && isLongAssociation) {
                    val component = longComponent
                    val refName = longComponentRefName
                    val selectOptions = typeEntityBusiness.subFormSelects
                    FormItemData(
                        elements = listOf(
                            TagElement(
                                component.name,
                                directives = listOf(VModel(modelValue)),
                                props = listOfNotNull(
                                    PropBind("ref", refName, isLiteral = true),
                                    disabled.toPropBind("disabled"),
                                ) + selectOptions.map {
                                    PropBind(it.name, it.name)
                                },
                            )
                        ),
                        imports = listOf(
                            ImportDefault(
                                "@/" + component.fullPath,
                                component.name,
                            )
                        ),
                        formItemNotAround = true
                    )
                } else {
                    val components = typeEntityBusiness.components
                    val component = if (listType) components.idMultiSelect else components.idSelect
                    FormItemData(
                        elements = listOf(
                            TagElement(
                                component.name,
                                directives = listOf(VModel(modelValue)),
                                props = listOfNotNull(
                                    PropBind("options", selectOption.name),
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
                                "@/" + component.fullPath,
                                component.name,
                            )
                        )
                    )
                }
            }

            is EnumProperty -> {
                val components = enum.components
                val component = if (typeNotNull) components.select else components.nullableSelect

                FormItemData(
                    elements = listOf(
                        TagElement(
                            component.name,
                            directives = listOf(VModel(modelValue)),
                            props = listOfNotNull(
                                disabled.toPropBind("disabled")
                            ),
                        )
                    ),
                    imports = listOf(
                        ImportDefault(
                            "@/" + component.fullPath,
                            component.name,
                        )
                    )
                )
            }

            is CommonProperty -> {
                when (formType) {
                    PropertyFormType.BOOLEAN ->
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

    fun Map<PropertyBusiness, FormItemData>.toElements(withCommentLabel: Boolean): List<Element> {
        val result = mutableListOf<Element>()

        forEach { (property, data) ->
            if (data.formItemNotAround) {
                result += listOf(
                    formItem(
                        prop = property.name,
                        label = if (withCommentLabel) property.comment else null,
                        content = listOf(formItemPlaceholder)
                    ).merge {
                        props += listOf(
                            PropBind("class", "sub-form-property-item", isLiteral = true)
                        )
                    }
                )
                result += data.elements
            } else {
                result += formItem(
                    prop = property.name,
                    label = if (withCommentLabel) property.comment else null,
                    content = data.elements
                )
            }
        }

        return result
    }
}

private val formItemPlaceholder = TagElement(
    "div",
    props = listOf(
        PropBind("class", "sub-form-property-item-placeholder", isLiteral = true)
    )
)
