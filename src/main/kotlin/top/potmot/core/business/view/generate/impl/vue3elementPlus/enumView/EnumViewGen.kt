package top.potmot.core.business.view.generate.impl.vue3elementPlus.enumView

import top.potmot.core.business.meta.EnumBusiness
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag

interface EnumViewGen : Generator {
    fun enumViewComponent(enum: EnumBusiness): Component {
        val items = enum.items.mapIndexed { index, it ->
            val ifExpression = "value === '${it.name}'"

            ElementPlusComponents.text(it.comment).merge {
                directives += VIf(expression = ifExpression, index != 0)
            }
        }

        return Component(
            imports = listOf(
                ImportType(enumPath, enum.name),
            ),
            props = listOf(
                Prop("value", enum.name)
            ),
            template = items
        )
    }

    fun enumViewFile(enum: EnumBusiness) =
        GenerateFile(
            enum,
            "components/${enum.dir}/${enum.components.view}.vue",
            stringify(enumViewComponent(enum)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumView),
        )
}