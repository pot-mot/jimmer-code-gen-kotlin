package top.potmot.core.business.view.generate.impl.vue3elementPlus.enumSelect

import top.potmot.core.business.meta.EnumBusiness
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.select
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.options
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

interface EnumSelectGen: Generator {
    fun enumSelectComponent(
        enum: EnumBusiness,
        nullable: Boolean,
    ): Component {
        val view = enum.components.view

        val modelValue = "modelValue"
        val options = enum.constants
        val option = "option"

        val selectElement = select(
            modelValue = modelValue,
            comment = enum.comment,
            clearable = nullable,
            valueOnClear = if (nullable) "undefined" else null,
            content = listOf(
                options(
                    options = options,
                    option = option,
                    value = { option },
                    label = { null },
                    content = listOf(
                        TagElement(
                            view.name,
                            props = listOf(PropBind("value", option))
                        )
                    )
                ),
                slotTemplate(
                    "label", content = listOf(
                        TagElement(
                            view.name,
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
                ImportDefault("@/" + view.fullPath, view.name)
            ),
            models = listOf(
                ModelProp(modelValue, if (nullable) "${enum.name} | undefined" else enum.name)
            ),
            template = listOf(
                selectElement
            )
        )
    }

    fun enumSelectFile(enum: EnumBusiness) = listOf(
        GenerateFile(
            enum,
            enum.components.select.fullPath,
            stringify(enumSelectComponent(enum, nullable = false)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumSelect),
        ),
        GenerateFile(
            enum,
            enum.components.nullableSelect.fullPath,
            stringify(enumSelectComponent(enum, nullable = true)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumNullableSelect),
        )
    )
}