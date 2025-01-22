package top.potmot.core.business.view.generate.impl.vue3elementPlus.select

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.type.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.options
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.select
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.staticPath

interface IdSelect : Generator {
    fun modelValueType(
        modelValue: String,
        multiple: Boolean,
        idType: String,
    ) =
        if (multiple)
            "Array<$idType>"
        else
            "$idType | undefined"

    fun keepModelValueLegal(
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

    private fun templateVariable(name: String, typeNotNull: Boolean, nullPlaceholder: String = "''") =
        "${'$'}{$name${if (typeNotNull) "" else " ?? $nullPlaceholder"}}"

    private fun labelExpression(name: String, tsType: String, typeNotNull: Boolean, nullPlaceholder: String = "''") =
        when (tsType) {
            "string" -> name
            "string | undefined" -> "$name ?? $nullPlaceholder"
            else -> "`${templateVariable(name, typeNotNull, nullPlaceholder)}`"
        }

    fun createLabelExpression(
        item: String,
        labelProperties: List<PropertyBusiness>,
        idProperty: PropertyBusiness,
    ): String {
        val idExpression by lazy {
            val tsType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)
            labelExpression("$item.${idProperty.name}", tsType, idProperty.typeNotNull)
        }

        return if (labelProperties.size == 1) {
            val property = labelProperties[0]
            val tsType = typeStrToTypeScriptType(property.type, property.typeNotNull)
            labelExpression("$item.${property.name}", tsType, property.typeNotNull, idExpression)
        } else if (labelProperties.isEmpty()) {
            idExpression
        } else {
            "`${
                labelProperties.joinToString(" ") { property ->
                    templateVariable("$item.${property.name}", property.typeNotNull)
                }
            }`"
        }
    }

    fun createIdSelect(entity: EntityBusiness, multiple: Boolean): Component {
        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)

        val optionView = entity.dto.optionView

        val modelValue = "modelValue"
        val options = "options"
        val option = "option"

        val modelValueType = modelValueType(
            modelValue, multiple, idType
        )
        val keepModelValueLegal = keepModelValueLegal(
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
                    label = { createLabelExpression(it, entity.optionLabelProperties, idProperty) },
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