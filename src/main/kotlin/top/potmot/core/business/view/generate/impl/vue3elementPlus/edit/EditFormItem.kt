package top.potmot.core.business.view.generate.impl.vue3elementPlus.edit

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.CommonProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.LazyEnumSelect
import top.potmot.core.business.meta.LazyIdSelect
import top.potmot.core.business.meta.LazySubEdit
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.PropertyFormType
import top.potmot.core.business.meta.TypeEntityProperty
import top.potmot.core.business.view.generate.commonComponentPath
import top.potmot.core.business.view.generate.fileUpload
import top.potmot.core.business.view.generate.filesUpload
import top.potmot.core.business.view.generate.imageUpload
import top.potmot.core.business.view.generate.imagesUpload
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.datePicker
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.dateTimePicker
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.input
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.inputNumber
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.option
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.select
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.switch
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.timePicker
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.FormItemData
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.vue3.Element
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VModel
import top.potmot.core.business.view.generate.meta.vue3.toPropBind

interface EditFormItem {
    fun PropertyBusiness.toEditFormItem(
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
                    val lazySubEdit = LazySubEdit(typeEntityBusiness, listType, !typeNotNull)

                    val component = lazySubEdit.component
                    val refName = lazySubEdit.componentRef
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
                        lazyItems = listOf(
                            lazySubEdit
                        ),
                        formItemNotAround = true,
                    )
                } else {
                    val lazyIdSelect = LazyIdSelect(typeEntityBusiness, listType)
                    val component = lazyIdSelect.component

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
                        ),
                        lazyItems = listOf(
                            lazyIdSelect
                        ),
                    )
                }
            }

            is EnumProperty -> {
                val lazyEnumSelect = LazyEnumSelect(enum, listType, !typeNotNull)
                val component = lazyEnumSelect.component

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
                    ),
                    lazyItems = listOf(
                        lazyEnumSelect
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
                            inputNumber(
                                modelValue,
                                comment = comment,
                                precision = 0,
                                min = numberMin,
                                max = numberMax,
                                valueOnClear =
                                if (typeNotNull)
                                    if (numberMin == null) null else "'min'"
                                else
                                    "undefined",
                                disabled = disabled,
                            )
                        )

                    PropertyFormType.FLOAT ->
                        FormItemData(
                            inputNumber(
                                modelValue,
                                comment = comment,
                                precision = numericPrecision,
                                min = numberMin,
                                max = numberMax,
                                valueOnClear =
                                if (typeNotNull)
                                    if (numberMin == null) null else "'min'"
                                else
                                    "undefined",
                                disabled = disabled,
                            )
                        )


                    PropertyFormType.TIME ->
                        FormItemData(
                            timePicker(
                                modelValue,
                                comment = comment,
                                clearable = !typeNotNull,
                                disabled = disabled,
                            )
                        )

                    PropertyFormType.DATE ->
                        FormItemData(
                            datePicker(
                                modelValue,
                                comment = comment,
                                clearable = !typeNotNull,
                                disabled = disabled,
                            )
                        )

                    PropertyFormType.DATETIME ->
                        FormItemData(
                            dateTimePicker(
                                modelValue,
                                comment = comment,
                                clearable = !typeNotNull,
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

                    PropertyFormType.FILE ->
                        FormItemData(
                            imports = listOf(
                                ImportDefault("$commonComponentPath/$fileUpload", fileUpload)
                            ),
                            elements = listOf(
                                TagElement(fileUpload) {
                                    directives += VModel(modelValue)
                                }
                            )
                        )

                    PropertyFormType.FILE_LIST ->
                        FormItemData(
                            imports = listOf(
                                ImportDefault("$commonComponentPath/$filesUpload", filesUpload)
                            ),
                            elements = listOf(
                                TagElement(filesUpload) {
                                    directives += VModel(modelValue)
                                }
                            )
                        )

                    PropertyFormType.IMAGE ->
                        FormItemData(
                            imports = listOf(
                                ImportDefault("$commonComponentPath/$imageUpload", imageUpload)
                            ),
                            elements = listOf(
                                TagElement(imageUpload) {
                                    directives += VModel(modelValue)
                                }
                            )
                        )

                    PropertyFormType.IMAGE_LIST ->
                        FormItemData(
                            imports = listOf(
                                ImportDefault("$commonComponentPath/$imagesUpload", imagesUpload)
                            ),
                            elements = listOf(
                                TagElement(imagesUpload) {
                                    directives += VModel(modelValue)
                                }
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
