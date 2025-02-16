package top.potmot.core.business.view.generate.impl.vue3elementPlus.enumView

import top.potmot.core.business.meta.LazyEnumView
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VFor
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag

interface EnumViewGen : Generator {
    private fun enumViewComponent(
        data: LazyEnumView,
    ): Component {
        val enum = data.enum
        val multiple = data.multiple

        val type = data.type

        val itemName = if (multiple) "item" else "value"

        val items = enum.items.mapIndexed { index, it ->
            val ifExpression = "$itemName === '${it.name}'"

            ElementPlusComponents.text(it.comment).merge {
                directives += VIf(expression = ifExpression, index != 0)
            }
        }

        val template =
            if (multiple) listOf(
                TagElement("template") {
                    directives += VFor("item", "value")
                    children += items
                }
            )
            else items

        return Component(
            imports = listOf(
                ImportType(enumPath, enum.name),
            ),
            props = listOf(
                Prop("value", type)
            ),
            template = template
        )
    }

    fun enumViewFile(
        data: LazyEnumView,
    ) = GenerateFile(
        data.enum,
        data.component.fullPath,
        stringify(enumViewComponent(data)),
        listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumView),
    )
}