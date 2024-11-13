package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.utils.componentNames
import top.potmot.core.business.utils.constants
import top.potmot.core.business.utils.dtoNames
import top.potmot.core.business.utils.idProperty
import top.potmot.core.business.utils.ruleNames
import top.potmot.core.business.utils.selectOptionLabel
import top.potmot.core.business.utils.serviceName
import top.potmot.core.business.utils.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.ViewGenerator
import top.potmot.core.business.view.generate.builder.property.ViewProperties
import top.potmot.core.business.view.generate.builder.property.rules
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.builder.vue3.componentLib.ElementPlus
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.AddFormDefault
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.AddFormType
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.SelectOption
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.addForm
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.editForm
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.editTable
import top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem.FormItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.queryForm.queryForm
import top.potmot.core.business.view.generate.impl.vue3elementPlus.queryFormItem.QueryFormItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.rules.Vue3RulesBuilder
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.viewTable
import top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn.TableColumn
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.slotTemplate
import top.potmot.core.business.view.generate.rulePath
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.share.GenerateEntity

object Vue3ElementPlusViewGenerator :
    ViewGenerator,
    ElementPlus,
    ViewProperties,
    TableColumn,
    FormItem,
    QueryFormItem,
    AddFormDefault,
    AddFormType {
    private val builder = Vue3ComponentBuilder()

    private val rulesBuilder = Vue3RulesBuilder(builder.indent, builder.wrapThreshold)

    override fun getFileSuffix() = "vue"

    private val GenerateEntity.apiServiceName
        get() = serviceName.replaceFirstChar { it.lowercase() }

    override fun stringifyEnumView(enum: GenEnumGenerateView): String {
        val items = enum.items.mapIndexed { index, it ->
            val ifExpression = "value === '${it.name}'"

            text(it.comment).merge {
                directives += VIf(expression = ifExpression, index != 0)
            }
        }

        val component = Component(
            imports = listOf(
                ImportType(enumPath, listOf(enum.name)),
            ),
            props = listOf(
                Prop("value", enum.name)
            ),
            template = items
        )

        return builder.build(component)
    }

    private fun createEnumSelectVue(
        enum: GenEnumGenerateView,
        nullable: Boolean,
    ): Component {
        val (_, dir, _, _, view) = enum.componentNames

        val modelValue = "modelValue"
        val options = enum.constants
        val option = "option"

        val selectElement = select(
            clearable = nullable,
            valueOnClear = if (nullable) "undefined" else null,
            content = listOf(
                options(
                    options, option, label = { null }, content = listOf(
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
                Import(enumPath, listOf(options)),
                ImportType(enumPath, listOf(enum.name)),
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

    override fun stringifyEnumSelect(enum: GenEnumGenerateView) =
        builder.build(createEnumSelectVue(enum, nullable = false))

    override fun stringifyEnumNullableSelect(enum: GenEnumGenerateView) =
        builder.build(createEnumSelectVue(enum, nullable = true))

    private val GenEntityBusinessView.selectOptions
        get() = selectProperties.mapNotNull {
            if (it.typeEntity == null)
                null
            else
                SelectOption(
                    it.name + "Options",
                    it.typeEntity.dtoNames.optionView,
                )
        }

    private fun createOptionQuery(
        selectOption: SelectOption,
        serviceName: String,
    ) = mutableListOf(
        ImportType("vue", listOf("Ref")),
        ImportType(staticPath, listOf(selectOption.type)),
        Import("vue", listOf("onBeforeMount")),
        Import("@/api", listOf("api")),
    ) to mutableListOf(
        selectOption.toVariable(),
        CodeBlock(buildString {
            appendLine("onBeforeMount(async () => {")
            appendLine("${builder.indent}${selectOption.name}.value = await api.$serviceName.listOptions({body: {}})")
            appendLine("})")
        })
    )

    private val GenEntityBusinessView.optionQueries
        get() = selectOptions.map { createOptionQuery(it, apiServiceName) }

    override fun stringifyQueryForm(entity: GenEntityBusinessView): String {
        val spec = "spec"

        return builder.build(
            queryForm(
                spec = spec,
                specType = entity.dtoNames.spec,
                specTypePath = staticPath,
                selectOptions = entity.selectOptions,
                content = entity.queryProperties
                    .associateWith { it.createQueryFormItem(spec) }
            )
        )
    }

    override fun stringifyTable(entity: GenEntityBusinessView): String {
        val rows = "rows"

        return builder.build(
            viewTable(
                data = rows,
                type = entity.dtoNames.listView,
                typePath = staticPath,
                idPropertyName = entity.idProperty.name,
                content = entity.tableProperties
                    .associateWith { it.createTableColumn() }
            )
        )
    }

    override fun stringifyAddFormType(entity: GenEntityBusinessView): String {
        val context = entity.addFormProperties.associateWith { it.addFormType }
        return buildString {
            appendLine("export type ${entity.addFormDataType} = {")
            context.forEach { (property, type) ->
                appendLine("${builder.indent}${property.name}: $type")
            }
            appendLine("}")
        }
    }

    override fun stringifyAddFormDefault(entity: GenEntityBusinessView): String {
        val content = entity.addFormProperties.associateWith { it.addFormDefault }
        val type = entity.addFormDataType

        return buildString {
            appendLine("import type {$type} from \"./${entity.addFormDataType}\"")
            appendLine()
            appendLine("export const ${entity.addFormDefault}: $type = {")
            content.forEach { (property, default) ->
                appendLine("${builder.indent}${property.name}: $default,")
            }
            appendLine("}")
        }
    }

    override fun stringifyAddFormRules(entity: GenEntityBusinessView): String {
        val rules = entity.insertInputProperties.associate { it.name to it.rules }
        return rulesBuilder.createFormRules("useRules", "formData", entity.dtoNames.insertInput, rules)
    }

    override fun stringifyAddForm(entity: GenEntityBusinessView): String {
        val formData = "formData"

        val nullableDiffProperties = entity.addFormInsertInputNullableDiffProperty

        val hasNullableDiffProperties = nullableDiffProperties.isNotEmpty()

        val afterValidCodes = nullableDiffProperties.joinToString("\n\n") {
            buildString {
                appendLine("if (formData.value.toOnePropertyId === undefined) {")
                appendLine("${builder.indent}sendMessage(\"toOneProperty不可为空\", \"warning\")")
                appendLine("${builder.indent}return")
                appendLine("}")
            }
        }

        return builder.build(
            addForm(
                submitType = entity.dtoNames.insertInput,
                submitTypePath = staticPath,
                type = entity.addFormDataType,
                typePath = staticPath,
                default = entity.addFormDefault,
                defaultPath = componentPath + "/" + entity.name.replaceFirstChar { it.lowercase() } + "/" + entity.addFormDefault,
                useRules = "useRules",
                useRulesPath = rulePath + "/" + entity.ruleNames.addFormRules,
                formData = formData,
                indent = builder.indent,
                selectOptions = entity.selectOptions,
                afterValidCodes = afterValidCodes,
                content = entity.addFormProperties
                    .associateWith { it.createFormItem(formData) }
            ).merge {
                if (hasNullableDiffProperties) {
                    imports += Import("$utilPath/message", listOf("sendMessage"))
                }
            }
        )
    }

    override fun stringifyEditFormRules(entity: GenEntityBusinessView): String {
        val rules = entity.editFormProperties.associate { it.name to it.rules }
        return rulesBuilder.createFormRules("useRules", "formData", entity.dtoNames.updateInput, rules)
    }

    override fun stringifyEditForm(entity: GenEntityBusinessView): String {
        val formData = "formData"

        return builder.build(
            editForm(
                formData = formData,
                type = entity.dtoNames.updateInput,
                typePath = staticPath,
                useRules = "useRules",
                useRulesPath = rulePath + "/" + entity.ruleNames.editFormRules,
                indent = builder.indent,
                selectOptions = entity.selectOptions,
                content = entity.editFormProperties
                    .filter { !it.idProperty }
                    .associateWith { it.createFormItem(formData) }
            )
        )
    }

    override fun stringifyEditTableRules(entity: GenEntityBusinessView): String {
        val rules = entity.editTableProperties.associate { it.name to it.rules }
        return rulesBuilder.createFormRules("useRules", "formData", entity.dtoNames.updateInput, rules, isArray = true)
    }

    override fun stringifyEditTable(entity: GenEntityBusinessView): String {
        val rows = "rows"

        return builder.build(
            editTable(
                formData = rows,
                type = entity.addFormDataType,
                typePath = staticPath,
                useRules = "useRules",
                default = entity.addFormDefault,
                defaultPath = componentPath + "/" + entity.name.replaceFirstChar { it.lowercase() } + "/" + entity.addFormDefault,
                useRulesPath = rulePath + "/" + entity.ruleNames.editTableRules,
                indent = builder.indent,
                idPropertyName = entity.idProperty.name,
                comment = entity.comment,
                selectOptions = entity.selectOptions,
                content = entity.editTableProperties
                    .associateWith { it.createFormItem(rows) }
            )
        )
    }

    private fun createIdSelect(entity: GenEntityBusinessView, multiple: Boolean): Component {
        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)

        val optionView = entity.dtoNames.optionView

        val label = entity.selectOptionLabel ?: idName

        val modelValue = "modelValue"
        val options = "options"
        val option = "option"

        val modelValueType = if (multiple) "Array<$idType>" else "$idType | undefined"

        val keepModelValueLegal =
            if (!multiple)
                CodeBlock(
                    """
                    watch(() => [$modelValue.value, props.${options}], () => {
                        if (!(props.${options}.map(it => it.$idName) as Array<$idType | undefined>).includes($modelValue.value)) {
                            $modelValue.value = undefined
                        }
                    }, {immediate: true})
                    """.trimIndent()
                )
            else
                CodeBlock(
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

        return Component(
            imports = listOf(
                Import("vue", listOf("watch")),
                ImportType(staticPath, listOf(optionView))
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
                select(
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
                            label = { "$it.$label" },
                        )
                    )
                )
            )
        )
    }

    override fun stringifyIdSelect(entity: GenEntityBusinessView) = builder.build(
        createIdSelect(entity, false)
    )

    override fun stringifyIdMultiSelect(entity: GenEntityBusinessView) = builder.build(
        createIdSelect(entity, true)
    )

    override fun stringifySingleSelect(entity: GenEntityBusinessView): String {
        return ""
    }

    override fun stringifyMultiSelect(entity: GenEntityBusinessView): String {
        return ""
    }

    override fun stringifyPage(entity: GenEntityBusinessView): String {
        val (_, dir, table, addForm, editForm, queryForm) = entity.componentNames
        val (_, listView, _, insertInput, updateInput, spec) = entity.dtoNames

        val optionQueries = entity.optionQueries

        return builder.build(
            Component(
                imports = listOf(
                    Import("vue", listOf("ref")),
                    ImportType(staticPath, listOf("Page", "PageQuery", listView, insertInput, updateInput, spec))
                ) + optionQueries.flatMap { it.first },
                script = optionQueries.flatMap { it.second },
                template = listOf(

                )
            )
        )
    }
}