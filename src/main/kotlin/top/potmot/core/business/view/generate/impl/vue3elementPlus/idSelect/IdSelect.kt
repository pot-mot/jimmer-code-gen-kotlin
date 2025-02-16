package top.potmot.core.business.view.generate.impl.vue3elementPlus.idSelect

import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.type.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.options
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.select
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.emptyLineCode
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.utilPath

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

    fun loadIfNot() = CodeBlock(
        """
const loadIfNot = async () => {
    if (props.options.state === 'unload') {
        await props.options.load()
    } else if (props.options.data === undefined && props.options.state !== 'loading') {
        await props.options.load()
    }
}

onBeforeMount(async () => {
    if (modelValue.value !== undefined) {
        await loadIfNot()
    }
})
""".trim()
    )

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
                if (props.$options.state !== 'loaded') return []
                if (props.$options.data === undefined) return []
            
                if (!(props.$options.data.map(it => it.$idName) as Array<$idType | undefined>).includes($modelValue.value)) {
                    $modelValue.value = undefined
                }
            }, {immediate: true})
            """.trimIndent()
        else
            """
            watch(() => [$modelValue.value, props.${options}], () => {
                if (props.$options.state !== 'loaded') return []
                if (props.$options.data === undefined) return []
            
                const newModelValue: Array<$idType> = []
                
                for (const item of $modelValue.value) {
                    if (props.${options}.data.map(it => it.$idName).includes(item)) {
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

    fun createIdSelect(entity: SubEntityBusiness, multiple: Boolean): Component {
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
        val loadIfNot = loadIfNot()
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
                    options = "$options.data",
                    option = option,
                    key = { "$it.$idName" },
                    value = { "$it.$idName" },
                    label = { createLabelExpression(it, entity.optionLabelProperties, idProperty) },
                )
            )
        ).merge {
            props += PropBind("loading", "options.state === 'loading'")
            events += EventBind("focus.once", "loadIfNot")
        }

        return Component(
            imports = listOf(
                Import("vue", "onBeforeMount", "watch"),
                ImportType("$utilPath/lazyOptions", "LazyOptions"),
                ImportType(staticPath, optionView),
            ),
            models = listOf(
                ModelProp(modelValue, modelValueType)
            ),
            props = listOf(
                Prop(options, "LazyOptions<$optionView>")
            ),
            script = listOf(
                loadIfNot,
                emptyLineCode,
                keepModelValueLegal
            ),
            template = listOf(
                selectElement
            )
        )
    }
}