package top.potmot.core.business.view.generate.impl.vue3elementPlus.query

import top.potmot.core.business.meta.LazyGenerateResult
import top.potmot.core.business.meta.LazyGenerated
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.meta.SelectOption
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.col
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.form
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.formItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.row
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.FormItemData
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
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag

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
    selectOptions: Collection<SelectOption> = emptyList(),
    content: Map<PropertyBusiness, FormItemData>,
) = Component {
    imports += listOf(
        Import("@element-plus/icons-vue", "Search"),
        ImportType(specTypePath, specType),
    )
    imports += content.values.flatMap { it.imports }

    models += ModelProp(spec, specType)

    emits += Event("query", args = listOf(EventArg("spec", specType)))

    if (selectOptions.isNotEmpty()) {
        imports += ImportType("$utilPath/lazyOptions", "LazyOptions")
        imports += selectOptions.map { it.import }
        props += selectOptions.map { it.prop }
    }

    script += content.values.flatMap {
        it.scripts
    }

    template += form(
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
                            type = ElementPlusComponents.Type.PRIMARY,
                            icon = "Search",
                        ).merge {
                            events += EventBind("click", "emits('query', spec)")
                            props += PropBind("class", "search-button", isLiteral = true)
                        }
            )
        )
    ).merge {
        props += PropBind("class", "query-form", isLiteral = true)
    }
}

interface QueryFormGen : Generator, QueryFormItem {
    private fun queryFormComponent(entity: RootEntityBusiness): Pair<Component, List<LazyGenerated>> {
        val spec = "spec"

        val content = entity.queryFormProperties
            .associateWith { it.toQueryFormItem(spec) }

        val component = queryForm(
            spec = spec,
            specType = entity.dto.spec,
            specTypePath = staticPath,
            selectOptions = entity.specificationSelects,
            content = content
        )

        return component to content.values.flatMap { it.lazyItems }
    }

    fun queryFormFile(entity: RootEntityBusiness): LazyGenerateResult {
        val (component, lazyItems) = queryFormComponent(entity)

        val files = listOf(
            GenerateFile(
                entity,
                entity.components.queryForm.fullPath,
                stringify(component),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.QueryForm),
            )
        )

        return LazyGenerateResult(
            files,
            lazyItems,
        )
    }
}