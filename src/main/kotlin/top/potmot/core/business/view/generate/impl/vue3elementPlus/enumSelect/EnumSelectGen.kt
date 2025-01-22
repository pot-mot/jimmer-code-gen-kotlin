package top.potmot.core.business.view.generate.impl.vue3elementPlus.enumSelect

import top.potmot.core.business.meta.EnumBusiness
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.slotTemplate
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag

fun enumSelectComponent(
    enum: EnumBusiness,
    nullable: Boolean,
): Component {
    val dir = enum.dir
    val view = enum.components.view

    val modelValue = "modelValue"
    val options = enum.constants
    val option = "option"

    val selectElement = ElementPlusComponents.select(
        comment = enum.comment,
        clearable = nullable,
        valueOnClear = if (nullable) "undefined" else null,
        content = listOf(
            ElementPlusComponents.options(
                options = options,
                option = option,
                value = { option },
                label = { null },
                content = listOf(
                    TagElement(
                        view,
                        props = listOf(PropBind("value", option))
                    )
                )
            ),
            slotTemplate(
                "label", content = listOf(
                    TagElement(
                        view,
                        props = listOf(PropBind("value", modelValue)),
                        directives = listOfNotNull(if (nullable) VIf(modelValue) else null)
                    ),
                )
            ),
        )
    )

    return Component(
        imports = listOf(
            Import(enumPath, options),
            ImportType(enumPath, enum.name),
            ImportDefault("$componentPath/$dir/$view.vue", view)
        ),
        models = listOf(
            ModelProp(modelValue, if (nullable) "${enum.name} | undefined" else enum.name)
        ),
        template = listOf(
            selectElement
        )
    )
}

interface EnumSelectGen: Generator {
    fun enumSelectFile(enum: EnumBusiness) = listOf(
        GenerateFile(
            enum,
            "components/${enum.dir}/${enum.components.select}.vue",
            stringify(enumSelectComponent(enum, nullable = false)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumSelect),
        ),
        GenerateFile(
            enum,
            "components/${enum.dir}/${enum.components.nullableSelect}.vue",
            stringify(enumSelectComponent(enum, nullable = true)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumNullableSelect),
        )
    )
}