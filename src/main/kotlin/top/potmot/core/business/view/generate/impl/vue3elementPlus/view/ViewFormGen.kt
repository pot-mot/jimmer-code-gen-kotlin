package top.potmot.core.business.view.generate.impl.vue3elementPlus.view

import top.potmot.core.business.meta.LazyGenerateResult
import top.potmot.core.business.meta.LazyGenerated
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.descriptions
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator.toElements
import top.potmot.core.common.typescript.ImportType
import top.potmot.core.common.vue3.Component
import top.potmot.core.common.vue3.Prop
import top.potmot.core.common.vue3.PropBind
import top.potmot.core.common.vue3.VIf
import top.potmot.core.business.view.generate.staticPath
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag

fun viewForm(
    formData: String,
    dataType: String,
    dataTypePath: String,
    nullable: Boolean,
    content: List<ViewItemData>,
) = Component {
    imports += listOf(
        ImportType(dataTypePath, dataType),
    )
    imports += content.flatMap { it.imports }

    props += Prop(formData, dataType.let { if (nullable) "$it | undefined" else it })

    template += descriptions(
        content = content.toElements(),
    ).merge {
        if (nullable) {
            directives += VIf(formData)
        }
        props += PropBind("class", "view-form", isLiteral = true)
    }
}

interface ViewFormGen: Generator, ViewFormItem {
    private fun viewFormComponent(entity: RootEntityBusiness): Pair<Component, List<LazyGenerated>> {
        val formData = "value"

        val content = entity.viewFormProperties.flatMap { it.viewFormItem() }

        val component = viewForm(
            formData = formData,
            dataType = entity.dto.detailView,
            dataTypePath = staticPath,
            content = content,
            nullable = false,
        )

        return component to content.flatMap { it.lazyItems }
    }

    fun viewFormFiles(entity: RootEntityBusiness): LazyGenerateResult {
        val (component, lazyItems) = viewFormComponent(entity)

        val files = listOf(
            GenerateFile(
                entity,
                entity.components.viewForm.fullPath,
                stringify(component),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.ViewForm),
            )
        )

        return LazyGenerateResult(
            files,
            lazyItems,
        )
    }

    private fun viewFormComponent(entity: SubEntityBusiness, nullable: Boolean): Pair<Component, List<LazyGenerated>> {
        val formData = "value"

        val content = entity.viewFormProperties.flatMap { it.viewFormItem() }

        val component = viewForm(
            formData = formData,
            dataType = entity.dto.detailView,
            dataTypePath = staticPath,
            content = content,
            nullable = nullable,
        )

        return component to content.flatMap { it.lazyItems }
    }

    fun viewFormFiles(entity: SubEntityBusiness, nullable: Boolean): LazyGenerateResult {
        val (component, lazyItems) = viewFormComponent(entity, nullable)

        val files = listOf(
            GenerateFile(
                entity,
                entity.components.viewForm.fullPath,
                stringify(component),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.ViewForm, GenerateTag.SubView),
            )
        )

        return LazyGenerateResult(
            files,
            lazyItems,
        )
    }
}