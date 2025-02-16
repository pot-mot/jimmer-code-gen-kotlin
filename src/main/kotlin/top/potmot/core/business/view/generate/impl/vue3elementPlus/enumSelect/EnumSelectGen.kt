package top.potmot.core.business.view.generate.impl.vue3elementPlus.enumSelect

import top.potmot.core.business.meta.LazyEnumSelect
import top.potmot.core.business.meta.LazyEnumView
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.options
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.select
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

interface EnumSelectGen : Generator {
    private fun enumSelectComponent(
        data: LazyEnumSelect,
    ): Pair<Component, LazyEnumView> {
        val enum = data.enum
        val multiple = data.multiple
        val nullable = data.nullable

        val component = data.component
        val type = data.type

        val modelValue = "modelValue"
        val options = enum.constants
        val option = "option"

        val selectElement = select(
            modelValue = modelValue,
            comment = enum.comment,
            multiple = multiple,
            clearable = multiple || nullable,
            valueOnClear = if (multiple) "[]" else if (nullable) "undefined" else null,
            content = listOf(
                options(
                    options = options,
                    option = option,
                    value = { option },
                    label = { null },
                    content = listOf(
                        TagElement(
                            component.name,
                            props = listOf(PropBind("value", option))
                        )
                    )
                ),
                slotTemplate(
                    "tag", content = listOf(
                        TagElement(
                            component.name,
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
                ImportDefault("@/" + component.fullPath, component.name)
            ),
            models = listOf(
                ModelProp(modelValue, type)
            ),
            template = listOf(
                selectElement
            )
        ) to data.lazyView
    }

    fun enumSelectFile(
        data: LazyEnumSelect,
    ): Pair<GenerateFile, LazyEnumView> {
        val (component, lazyView) = enumSelectComponent(data)

        val file = GenerateFile(
            data.enum,
            data.component.fullPath,
            stringify(component),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumSelect),
        )

        return file to lazyView
    }
}