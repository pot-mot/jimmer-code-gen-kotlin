package top.potmot.core.business.view.generate.impl.vue3elementPlus.queryForm

import top.potmot.core.business.view.generate.builder.vue3.componentLib.ElementPlus
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.col
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.row
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.SelectOption
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.Element
import top.potmot.core.business.view.generate.meta.vue3.Event
import top.potmot.core.business.view.generate.meta.vue3.EventArg
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.entity.dto.GenEntityBusinessView

private fun createCol(property: GenEntityBusinessView.TargetOf_properties, elements: Collection<Element>) =
    col(
        span = 8,
        content = listOf(
            formItem(
                prop = property.name,
                label = property.comment,
                content = elements
            )
        )
    )

fun queryForm(
    spec: String,
    specType: String,
    specTypePath: String,
    selectOptions: Iterable<SelectOption> = emptyList(),
    content: Map<GenEntityBusinessView.TargetOf_properties, List<Element>>,
) = Component(
    imports = listOf(
        Import("@element-plus/icons-vue", listOf("Search")),
        ImportType(specTypePath, listOf(specType)),
    ) + selectOptions.map { it.toImport() },
    models = listOf(
        ModelProp(spec, specType)
    ),
    props = selectOptions.map { it.toProp() },
    emits = listOf(
        Event("query", args = listOf(EventArg("spec", specType)))
    ),
    template = listOf(
        form(
            model = spec,
            content = listOf(
                row(
                    gutter = 20,
                    content = content.map { (property, elements) ->
                        createCol(property, elements)
                    } + button(
                        content = "查询",
                        type = ElementPlus.Type.PRIMARY,
                        icon = "Search",
                    ).merge {
                        events += EventBind("click", "emits('query', spec)")
                    }
                )
            )
        )
    )
)