package top.potmot.core.business.view.generate.impl.vue3elementPlus.select

import top.potmot.core.business.property.EntityPropertyCategories
import top.potmot.core.business.utils.dto
import top.potmot.core.business.utils.idProperty
import top.potmot.core.business.utils.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.builder.vue3.elementPlus.ElementPlus
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.staticPath
import top.potmot.entity.dto.GenEntityBusinessView

interface IdSelect : ElementPlus, EntityPropertyCategories {
    fun createModelValueType(
        modelValue: String,
        multiple: Boolean,
        idType: String,
    ) =
        if (multiple)
            "Array<$idType>"
        else
            "$idType | undefined"

    fun createKeepModelValueLegal(
        modelValue: String,
        multiple: Boolean,
        options: String,
        idName: String,
        idType: String,
    ) = CodeBlock(
        if (!multiple)
            """
            watch(() => [$modelValue.value, props.${options}], () => {
                if (!(props.${options}.map(it => it.$idName) as Array<$idType | undefined>).includes($modelValue.value)) {
                    $modelValue.value = undefined
                }
            }, {immediate: true})
            """.trimIndent()
        else
            """
            watch(() => [$modelValue.value, props.${options}], () => {
                const newModelValue: Array<$idType> = []
                
                for (const item of $modelValue.value) {
                    if (props.${options}.map(it => it.$idName).includes(item)) {
                        newModelValue.push(item)
                    }
                }
                if ($modelValue.value.length != newModelValue.length)
                    $modelValue.value = newModelValue
            }, {immediate: true})
            """.trimIndent()
    )

    fun createLabelExpression(
        item: String,
        labelPropertyNames: List<String>,
        idName: String,
    ) =
        if (labelPropertyNames.size == 1) {
            "$item.${labelPropertyNames[0]}"
        } else if (labelPropertyNames.isEmpty()) {
            "$item.${idName}"
        } else {
            "`${labelPropertyNames.joinToString("_") { label -> "${'$'}{$item.$label}" }}`"
        }

    fun createIdSelect(entity: GenEntityBusinessView, multiple: Boolean): Component {
        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)

        val optionView = entity.dto.optionView

        val labels = entity.optionLabelProperties.map { it.name }

        val modelValue = "modelValue"
        val options = "options"
        val option = "option"

        val modelValueType = createModelValueType(
            modelValue, multiple, idType
        )
        val keepModelValueLegal = createKeepModelValueLegal(
            modelValue, multiple, options, idName, idType
        )

        val selectElement = select(
            modelValue = modelValue,
            comment = entity.comment,
            filterable = true,
            clearable = true,
            multiple = multiple,
            content = listOf(
                options(
                    options = options,
                    option = option,
                    key = { "$it.$idName" },
                    value = { "$it.$idName" },
                    label = { createLabelExpression(it, labels, idName) },
                )
            )
        )

        return Component(
            imports = listOf(
                Import("vue", "watch"),
                ImportType(staticPath, optionView)
            ),
            models = listOf(
                ModelProp(modelValue, modelValueType)
            ),
            props = listOf(
                Prop(options, "Array<$optionView>")
            ),
            script = listOf(
                keepModelValueLegal
            ),
            template = listOf(
                selectElement
            )
        )
    }
}