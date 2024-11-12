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
import top.potmot.core.business.view.generate.impl.vue3elementPlus.queryForm.queryForm
import top.potmot.core.business.view.generate.impl.vue3elementPlus.queryFormItem.QueryFormItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.rules.Vue3RulesBuilder
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.viewTable
import top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn.TableColumn
import top.potmot.core.business.view.generate.meta.rules.rules
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
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.share.GenerateEntity

object Vue3ElementPlusViewGenerator :
    ViewGenerator,
    ElementPlus,
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
    ) = mutableListOf(
        ImportType("vue", listOf("Ref")),
        ImportType(staticPath, listOf(selectOption.type)),
        Import("vue", listOf("onBeforeMount")),
        Import("@/api", listOf("api")),
    ) to mutableListOf(
        selectOption.toVariable(),
        CodeBlock(buildString {
            appendLine("onBeforeMount(async () => {")
            appendLine("${builder.indent}${selectOption.name}.value = await api.$serviceName.list({body: {}})")
            appendLine("})")
        })
    )

    private val GenEntityBusinessView.optionQueries
        get() = selectOptions.map { createOptionQuery(it, apiServiceName) }


    private val GenEntityBusinessView.queryProperties
        get() = properties.filter {
            !it.idProperty && (it.associationType == null || it.associationType in targetOneAssociationType)
        }

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


    private val GenEntityBusinessView.tableProperties
        get() = properties.filter {
            !it.idProperty && it.associationType == null
        }

    override fun stringifyTable(entity: GenEntityBusinessView): String {
        val rows = "rows"

        return builder.build(
            viewTable(
                data = rows,
                type = entity.dtoNames.listView,
                typePath = entity.dtoNames.listView,
                idPropertyName = entity.idProperties[0].name,
                content = entity.tableProperties
                    .associateWith { it.createTableColumn() }
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
        val content = entity.addFormProperties.associateWith { it.addFormDefault }
        return buildString {
            appendLine("import type {${entity.addFormDataType}} from \"./${entity.addFormDataType}\"")

            appendLine("export const ${entity.defaultAddFormData} = {")
            content.forEach { (property, default) ->
                appendLine("${builder.indent}${property.name}: $default,")
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
        val rows = "rows"

        return builder.build(
            editTable(
                formData = rows,
                type = entity.addFormDataType,
                typePath = staticPath,
                useRules = "useRules",
                default = entity.defaultAddFormData,
                defaultPath = componentPath + "/" + entity.name.replaceFirstChar { it.lowercase() } + "/" + entity.defaultAddFormData,
                useRulesPath = rulePath + "/" + entity.ruleNames.editTableRules,
                indent = builder.indent,
                idPropertyName = entity.idProperties.first().name,
                comment = entity.comment,
                content = entity.editTableProperties
                    .associateWith { it.createFormItem(rows) }
            )
        )
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

    override fun stringifyIdSelect(entity: GenEntityBusinessView): String {
        return ""
    }

    override fun stringifyIdMultiSelect(entity: GenEntityBusinessView): String {
        return ""
    }

    override fun stringifySingleSelect(entity: GenEntityBusinessView): String {
        return ""
    }

    override fun stringifyMultiSelect(entity: GenEntityBusinessView): String {
        return ""
    }
}