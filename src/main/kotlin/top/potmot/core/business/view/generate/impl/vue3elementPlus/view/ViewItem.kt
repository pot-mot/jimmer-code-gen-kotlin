package top.potmot.core.business.view.generate.impl.vue3elementPlus.view

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.CommonProperty
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.LazyEnumView
import top.potmot.core.business.meta.LazyGenerated
import top.potmot.core.business.meta.LazyShortViewTable
import top.potmot.core.business.meta.LazySubView
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.PropertyFormType
import top.potmot.core.business.meta.TypeEntityProperty
import top.potmot.core.business.view.generate.commonComponentPath
import top.potmot.core.business.view.generate.fileView
import top.potmot.core.business.view.generate.filesView
import top.potmot.core.business.view.generate.imagePreview
import top.potmot.core.business.view.generate.imagesPreview
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.checkbox
import top.potmot.core.common.typescript.Import
import top.potmot.core.common.typescript.ImportDefault
import top.potmot.core.common.typescript.TsImport
import top.potmot.core.common.vue3.Element
import top.potmot.core.common.vue3.ExpressionElement
import top.potmot.core.common.vue3.PropBind
import top.potmot.core.common.vue3.TagElement
import top.potmot.core.common.vue3.VShow
import top.potmot.core.config.getContextOrGlobal
import top.potmot.entity.dto.GenEntityBusinessView

data class ViewItemData(
    val label: String,
    val prop: String,
    val elements: Collection<Element> = emptyList(),
    val imports: Collection<TsImport> = emptyList(),
    val lazyItems: Collection<LazyGenerated> = emptyList(),
    val shortViews: Collection<Pair<PropertyBusiness, ViewItemData>> = emptyList(),
) {
    val flatShortViews: Collection<Pair<PropertyBusiness, ViewItemData>> by lazy {
        if (shortViews.isNotEmpty()) {
            shortViews + shortViews.flatMap { it.second.flatShortViews }
        } else {
            emptyList()
        }
    }
}

fun ViewItemData(
    label: String,
    prop: String,
    vararg elements: Element,
) = ViewItemData(label, prop, elements = elements.toList())

interface ViewItem {
    private fun viewItem(
        value: String,
        property: GenEntityBusinessView.TargetOf_properties,
        formType: PropertyFormType,
        createDefault: (property: GenEntityBusinessView.TargetOf_properties) -> ViewItemData,
        dateTimeFormatInView: Boolean = getContextOrGlobal().dateTimeFormatInView,
    ): ViewItemData = when (formType) {
        PropertyFormType.BOOLEAN -> ViewItemData(
            label = property.comment,
            prop = property.name,
            checkbox(
                value,
                doubleBind = false,
            ).merge {
                if (!property.typeNotNull) {
                    directives += VShow(value)
                }
            }
        )

        PropertyFormType.TIME ->
            if (!dateTimeFormatInView)
                createDefault(property)
            else
                ViewItemData(
                    label = property.comment,
                    prop = property.name,
                    imports = listOf(
                        Import(timeFormatPath, formatTime)
                    ),
                    elements = listOf(
                        ExpressionElement("$formatTime($value)")
                    )
                )

        PropertyFormType.DATE ->
            if (!dateTimeFormatInView)
                createDefault(property)
            else
                ViewItemData(
                    label = property.comment,
                    prop = property.name,
                    imports = listOf(
                        Import(timeFormatPath, formatDate)
                    ),
                    elements = listOf(
                        ExpressionElement("$formatDate($value)")
                    )
                )

        PropertyFormType.DATETIME ->
            if (!dateTimeFormatInView)
                createDefault(property)
            else
                ViewItemData(
                    label = property.comment,
                    prop = property.name,
                    imports = listOf(
                        Import(timeFormatPath, formatDateTime)
                    ),
                    elements = listOf(
                        ExpressionElement("$formatDateTime($value)")
                    )
                )

        PropertyFormType.FILE -> ViewItemData(
            label = property.comment,
            prop = property.name,
            imports = listOf(
                ImportDefault("$commonComponentPath/$fileView", fileView)
            ),
            elements = listOf(
                TagElement(fileView) {
                    props += PropBind("value", value)
                }
            )
        )

        PropertyFormType.FILE_LIST -> ViewItemData(
            label = property.comment,
            prop = property.name,
            imports = listOf(
                ImportDefault("$commonComponentPath/$filesView", filesView)
            ),
            elements = listOf(
                TagElement(filesView) {
                    props += PropBind("value", value)
                }
            )
        )

        PropertyFormType.IMAGE -> ViewItemData(
            label = property.comment,
            prop = property.name,
            imports = listOf(
                ImportDefault("$commonComponentPath/$imagePreview", imagePreview)
            ),
            elements = listOf(
                TagElement(imagePreview) {
                    props += PropBind("value", value)
                }
            )
        )

        PropertyFormType.IMAGE_LIST -> ViewItemData(
            label = property.comment,
            prop = property.name,
            imports = listOf(
                ImportDefault("$commonComponentPath/$imagesPreview", imagesPreview)
            ),
            elements = listOf(
                TagElement(imagesPreview) {
                    props += PropBind("value", value)
                }
            )
        )

        else ->
            createDefault(property)
    }

    fun PropertyBusiness.viewItem(
        nameToValue: (name: String) -> String,
        createDefault: (property: GenEntityBusinessView.TargetOf_properties) -> ViewItemData,
        withDateTimeFormat: Boolean,
    ): ViewItemData = when (this) {
        is TypeEntityProperty ->
            if (isShortView) {
                if (isTargetOne) {
                    ViewItemData(
                        prop = name,
                        label = comment,
                        shortViews = typeEntityBusiness.shortViewProperties.map { shortProperty ->
                            shortProperty to shortProperty
                                .viewItem(nameToValue, createDefault, withDateTimeFormat)
                                .let { shortViewItem ->
                                    shortViewItem.copy(
                                        label = comment + shortViewItem.label,
                                        prop = name + "." + shortViewItem.prop
                                    )
                                }
                        }
                    )
                } else {
                    val lazyShortViewTable =
                        LazyShortViewTable(typeEntityBusiness, typeEntityBusiness.shortViewProperties)
                    val component = lazyShortViewTable.component

                    ViewItemData(
                        prop = name,
                        label = comment,
                        elements = listOf(
                            TagElement(
                                component.name,
                                props = listOfNotNull(
                                    PropBind(lazyShortViewTable.propName, nameToValue(name))
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
                            lazyShortViewTable
                        ),
                    )
                }
            } else if (this is AssociationProperty && isLongAssociation) {
                val lazySubView = LazySubView(typeEntityBusiness, listType, !typeNotNull)
                val component = lazySubView.component

                ViewItemData(
                    prop = name,
                    label = comment,
                    elements = listOf(
                        TagElement(
                            component.name,
                            props = listOfNotNull(
                                PropBind(lazySubView.propName, name)
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
                        lazySubView
                    ),
                )
            } else {
                ViewItemData(
                    label = property.comment,
                    prop = property.name,
                )
            }

        is EnumProperty -> {
            val lazyEnumView = LazyEnumView(enum, listType, !typeNotNull)
            val component = lazyEnumView.component

            ViewItemData(
                prop = name,
                label = comment,
                elements = listOf(
                    TagElement(
                        component.name,
                        props = listOf(
                            PropBind("value", nameToValue(name))
                        )
                    )
                ),
                imports = listOf(
                    ImportDefault(
                        "@/" + component.fullPath,
                        component.name,
                    )
                ),
                lazyItems = listOf(
                    lazyEnumView
                )
            )
        }

        is CommonProperty ->
            viewItem(nameToValue(name), property, formType, createDefault, withDateTimeFormat)
    }
}