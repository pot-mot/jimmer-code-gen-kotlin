package top.potmot.core.business.view.generate.impl.vue3elementPlus.queryForm

import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.view.generate.builder.vue3.elementPlus.ElementPlus
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.col
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.row
import top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem.FormItemData
import top.potmot.core.business.view.generate.impl.vue3elementPlus.selectOptions.SelectOption
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.Element
import top.potmot.core.business.view.generate.meta.vue3.Event
import top.potmot.core.business.view.generate.meta.vue3.EventArg
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement

private fun createCol(property: PropertyBusiness, elements: Collection<Element>) =
    col(
        span = 8,
        xs = 24,
        content = listOf(
            formItem(
                prop = property.name,
                label = property.comment,
                content = elements.map {
                    if (it is TagElement) {
                        it.merge {
                            events += EventBind("change", "emits('query', spec)")
                        }
                    } else {
                        it
                    }
                }
            )
        )
    )

fun queryForm(
    spec: String,
    specType: String,
    specTypePath: String,
    selectOptions: Iterable<SelectOption> = emptyList(),
    content: Map<PropertyBusiness, FormItemData>,
) = Component(
    imports = listOf(
        Import("@element-plus/icons-vue", "Search"),
        ImportType(specTypePath, specType),
    )
            + content.values.flatMap { it.imports }
            + selectOptions.map { it.toImport() },
    models = listOf(
        ModelProp(spec, specType)
    ),
    props = selectOptions.map { it.toProp() },
    emits = listOf(
        Event("query", args = listOf(EventArg("spec", specType)))
    ),
    script = content.values.flatMap {
        it.scripts
    },
    template = listOf(
        form(
            model = spec,
            content = listOf(
                row(
                    gutter = 20,
                    content =
                    content.map { (property, formItemData) ->
                        createCol(property, formItemData.elements)
                    } +
                            button(
                                content = "查询",
                                type = ElementPlus.Type.PRIMARY,
                                icon = "Search",
                            ).merge {
                                events += EventBind("click", "emits('query', spec)")
                                props += PropBind("class", "search-button", isLiteral = true)
                            }
                )
            )
        ).merge {
            props += PropBind("@submit.prevent", isLiteral = true)
        }
    )
)