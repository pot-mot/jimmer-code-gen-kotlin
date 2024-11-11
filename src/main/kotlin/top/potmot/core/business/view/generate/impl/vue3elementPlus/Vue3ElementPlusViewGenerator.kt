package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.utils.componentNames
import top.potmot.core.business.utils.constants
import top.potmot.core.business.utils.dtoNames
import top.potmot.core.business.utils.ruleNames
import top.potmot.core.business.utils.serviceName
import top.potmot.core.business.utils.targetOneAssociationType
import top.potmot.core.business.utils.targetOneProperties
import top.potmot.core.business.view.generate.ViewGenerator
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
import top.potmot.core.business.view.generate.impl.vue3elementPlus.queryFormItem.QueryFormItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.rules.Vue3RulesBuilder
import top.potmot.core.business.view.generate.meta.rules.rules
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.Event
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.slotTemplate
import top.potmot.core.business.view.generate.rulePath
import top.potmot.core.business.view.generate.staticPath
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.share.GenerateEntity

object Vue3ElementPlusViewGenerator :
    ViewGenerator,
    ElementPlus,
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
        get() = targetOneProperties.mapNotNull {
            if (it.typeEntity == null)
                null
            else
                SelectOption(
                    it.name + "Options",
                    it.typeEntity.dtoNames.listView,
                )
        }

    private fun createOptionQuery(
        selectOption: SelectOption,
        serviceName: String,
    ) = Component.Builder(
        imports = mutableListOf(
            ImportType("vue", listOf("Ref")),
            ImportType(staticPath, listOf(selectOption.type)),
            Import("vue", listOf("onBeforeMount")),
            Import("@/api", listOf("api")),
        ),
        script = mutableListOf(
            selectOption.toVariable(),
            CodeBlock(buildString {
                appendLine("onBeforeMount(async () => {")
                appendLine("${builder.indent}${selectOption.name}.value = await api.$serviceName.list({body: {}})")
                appendLine("})")
            })
        )
    )

    private val GenEntityBusinessView.optionQueries
        get() = selectOptions.map { createOptionQuery(it, apiServiceName) }


    private fun createOptionProp(
        selectOption: SelectOption,
    ) = Component.Builder(
        imports = mutableListOf(
            ImportType(staticPath, listOf(selectOption.type))
        ),
        props = mutableListOf(
            selectOption.toProp()
        )
    )

    private val GenEntityBusinessView.optionProps
        get() = selectOptions.map { createOptionProp(it) }

    override fun stringifyQueryForm(entity: GenEntityBusinessView): String {
        val spec = "spec"

        val cols = entity.properties
            .map {
                col(span = 8, content = listOf(it.createQueryFormItem(spec)).flatten())
            }

        return builder.build(
            Component(
                models = listOf(
                    ModelProp(spec, entity.dtoNames.spec)
                ),
                emits = listOf(
                    Event("query")
                ),
                template = listOf(
                    form(
                        model = spec,
                        content = listOf(
                            row(gutter = 20, content = cols)
                        )
                    )
                )
            )
        )
    }

    override fun stringifyTable(entity: GenEntityBusinessView): String {
        return builder.build(
            Component(
                template = listOf(
                    table(
                        data = "rows",
                        columns = entity.properties.map {
                            tableColumn(it.name, it.comment)
                        },
                    )
                )
            )
        )
    }

    private val GenEntityBusinessView.addFormProperties
        get() = properties.filter {
            !it.idProperty && (it.associationType == null || it.associationType in targetOneAssociationType)
        }

    override fun stringifyAddFormDataType(entity: GenEntityBusinessView): String {
        val properties = entity.addFormProperties.map { it.addFormType }
        return buildString {
            appendLine("export type ${entity.addFormDataType} = {")
            properties.forEach {
                appendLine("${builder.indent}$it,")
            }
            appendLine("}")
        }
    }

    override fun stringifyDefaultAddFormData(entity: GenEntityBusinessView): String {
        val properties = entity.addFormProperties.map { it.addFormDefault }
        return buildString {
            appendLine("import type {${entity.addFormDataType}} from \"./${entity.addFormDataType}\"")

            appendLine("export const ${entity.defaultAddFormData} = {")
            properties.forEach {
                appendLine("${builder.indent}$it,")
            }
            appendLine("}")
        }
    }

    override fun stringifyAddFormRules(entity: GenEntityBusinessView): String {
        val rules = entity.addFormProperties.associate { it.name to it.rules }
        return rulesBuilder.createFormRules("useRules", "formData", entity.addFormDataType, rules)
    }

    override fun stringifyAddForm(entity: GenEntityBusinessView): String {
        val formData = "formData"

        return builder.build(
            addForm(
                submitType = entity.dtoNames.insertInput,
                submitTypePath = staticPath,
                type = entity.addFormDataType,
                typePath = staticPath,
                default = entity.defaultAddFormData,
                defaultPath = componentPath + "/" + entity.name.replaceFirstChar { it.lowercase() } + "/" + entity.defaultAddFormData,
                useRules = "useRules",
                useRulesPath = rulePath + "/" + entity.ruleNames.addFormRules,
                formData = formData,
                indent = builder.indent,
                selectOptions = entity.selectOptions,
                content = entity.addFormProperties
                    .associateWith { it.createFormItem(formData) }
            ).merge(
                *entity.optionProps.toTypedArray()
            )
        )
    }

    private val GenEntityBusinessView.editFormProperties
        get() = properties.filter {
            it.associationType == null || it.associationType in targetOneAssociationType
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
                content = entity.editFormProperties
                    .filter { !it.idProperty }
                    .associateWith { it.createFormItem(formData) }
            ).merge(
                *entity.optionProps.toTypedArray(),
            )
        )
    }

    private
    val GenEntityBusinessView.editTableProperties
        get() = properties.filter {
            !it.idProperty && (it.associationType == null || it.associationType in targetOneAssociationType)
        }

    override fun stringifyEditTableRules(entity: GenEntityBusinessView): String {
        val rules = entity.editTableProperties.associate { it.name to it.rules }
        return rulesBuilder.createFormRules("useRules", "formData", entity.dtoNames.updateInput, rules, isArray = true)
    }

    override fun stringifyEditTable(entity: GenEntityBusinessView): String {
        val formData = "formData"

        return builder.build(
            editTable(
                formData = formData,
                type = entity.addFormDataType,
                typePath = staticPath,
                useRules = "useRules",
                useRulesPath = rulePath + "/" + entity.ruleNames.editTableRules,
                indent = builder.indent,
                idPropertyName = entity.idProperties.first().name,
                content = entity.editTableProperties
                    .associateWith { it.createFormItem(formData) }
            ).merge(
                *entity.optionProps.toTypedArray(),
            )
        )
    }

    override fun stringifyPage(entity: GenEntityBusinessView): String {
        val (_, dir, table, addForm, editForm, queryForm) = entity.componentNames
        val (_, listView, _, insertInput, updateInput, spec) = entity.dtoNames

        return builder.build(
            Component(
                imports = listOf(
                    Import("vue", listOf("ref")),
                    ImportType(staticPath, listOf("Page", "PageQuery", listView, insertInput, updateInput, spec))

                ),
            )
        )
    }

    override fun stringifySingleSelect(entity: GenEntityBusinessView): String {
        TODO("Not yet implemented")
    }

    override fun stringifyMultiSelect(entity: GenEntityBusinessView): String {
        TODO("Not yet implemented")
    }

    override fun stringifyIdSelect(entity: GenEntityBusinessView): String {
        TODO("Not yet implemented")
    }

    override fun stringifyIdMultiSelect(entity: GenEntityBusinessView): String {
        TODO("Not yet implemented")
    }
}