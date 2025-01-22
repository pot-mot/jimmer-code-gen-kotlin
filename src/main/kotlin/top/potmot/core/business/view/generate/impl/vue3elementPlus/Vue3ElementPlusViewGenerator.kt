package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.EnumBusiness
import top.potmot.core.business.view.generate.ViewGenerator
import top.potmot.core.business.view.generate.builder.rules.Vue3ElementPlusRuleBuilder
import top.potmot.core.business.view.generate.builder.rules.existValidRules
import top.potmot.core.business.view.generate.builder.rules.rules
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.builder.vue3.elementPlus.ElementPlus
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.enumPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.addForm.AddForm
import top.potmot.core.business.view.generate.impl.vue3elementPlus.enumSelect.EnumSelect
import top.potmot.core.business.view.generate.impl.vue3elementPlus.enumView.EnumView
import top.potmot.core.business.view.generate.impl.vue3elementPlus.addForm.AddFormDefault
import top.potmot.core.business.view.generate.impl.vue3elementPlus.addForm.AddFormType
import top.potmot.core.business.view.generate.impl.vue3elementPlus.editForm.EditForm
import top.potmot.core.business.view.generate.impl.vue3elementPlus.editTable.EditTable
import top.potmot.core.business.view.generate.impl.vue3elementPlus.formItem.FormItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.page.Page
import top.potmot.core.business.view.generate.impl.vue3elementPlus.queryForm.queryForm
import top.potmot.core.business.view.generate.impl.vue3elementPlus.queryFormItem.QueryFormItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.select.IdSelect
import top.potmot.core.business.view.generate.impl.vue3elementPlus.select.IdTreeSelect
import top.potmot.core.business.view.generate.impl.vue3elementPlus.selectOptions.selectOptions
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.viewTable
import top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn.TableColumn
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.rulePath
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException
import top.potmot.utils.map.iterableMapOf
import top.potmot.utils.string.buildScopeString

object Vue3ElementPlusViewGenerator :
    ViewGenerator,
    ElementPlus,
    EnumView,
    EnumSelect,
    TableColumn,
    FormItem,
    IdSelect,
    IdTreeSelect,
    QueryFormItem,
    AddForm,
    AddFormDefault,
    AddFormType,
    EditForm,
    EditTable,
    Page {
    private val componentBuilder = Vue3ComponentBuilder()

    fun stringify(component: Component) = componentBuilder.build(component)

    override val indent = componentBuilder.indent
    private val wrapThreshold = componentBuilder.wrapThreshold

    private val rulesBuilder = Vue3ElementPlusRuleBuilder(indent, wrapThreshold)

    fun QueryForm(entity: EntityBusiness): Component {
        val spec = "spec"

        return queryForm(
            spec = spec,
            specType = entity.dto.spec,
            specTypePath = staticPath,
            selectOptions = entity.specificationSelectProperties.selectOptions,
            content = entity.queryFormProperties
                .associateWith { it.createQueryFormItem(spec) }
        )
    }

    @Throws(ModelException.TreeEntityCannotFoundChildrenProperty::class)
    fun ViewTable(entity: EntityBusiness): Component {
        val rows = "rows"

        val childrenProp = takeIf { entity.isTree }?.let {
            entity.childrenProperty.name
        }

        val dto = entity.dto
        val isTree = entity.isTree

        return viewTable(
            data = rows,
            type = if (isTree) dto.treeView else dto.listView,
            typePath = staticPath,
            stripe = !isTree,
            idPropertyName = entity.idProperty.name,
            content = entity.tableProperties.flatMap { it.tableColumnDataPairs() },
            childrenProp = childrenProp,
            showIndex = !isTree,
        )
    }

    fun AddFormTypeDeclare(entity: EntityBusiness): String {
        val enumImports = entity.enums.map {
            ImportType(enumPath, it.name)
        }

        return buildScopeString(indent) {
            componentBuilder.apply {
                lines(enumImports.stringifyImports())
            }

            if (enumImports.isNotEmpty()) line()

            line("export type ${entity.addFormDataType} = {")
            scope {
                entity.addFormProperties.forEach {
                    line("${it.property.name}: ${it.addFormType}")
                }
            }
            line("}")
        }
    }

    @Throws(ModelException.DefaultItemNotFound::class)
    fun AddFormDefaultFunction(entity: EntityBusiness): String {
        val type = entity.addFormDataType

        return buildScopeString(indent) {
            line("import type {$type} from \"./${entity.addFormDataType}\"")
            line()
            line("export const ${entity.addFormCreateDefault} = (): $type => {")
            scope {
                line("return {")
                scope {
                    entity.addFormProperties.forEach {
                        line("${it.property.name}: ${it.addFormDefault},")
                    }
                }
                line("}")
            }
            line("}")
        }
    }

    @Throws(
        ModelException.IdPropertyNotFound::class,
        ModelException.IndexRefPropertyNotFound::class,
        ModelException.IndexRefPropertyCannotBeList::class
    )
    fun AddFormRules(entity: EntityBusiness): String {
        val addFormRulesProperties = entity.addFormRulesProperties
        val rules = iterableMapOf(
            addFormRulesProperties.associateWith { it.rules },
            entity.existValidRules(withId = false, addFormRulesProperties)
        )
        return rulesBuilder.createFormRules(
            functionName = "useRules",
            formData = "formData",
            formDataType = entity.addFormDataType,
            formDataTypePath = componentPath + "/" + entity.dir + "/" + entity.addFormDataType,
            ruleDataType = entity.dto.insertInput,
            ruleDataTypePath = staticPath,
            propertyRules = rules
        )
    }

    fun AddForm(entity: EntityBusiness): Component {
        val formData = "formData"

        val nullableDiffProperties = entity.addFormEditNullableProperties

        val hasNullableDiffProperties = nullableDiffProperties.isNotEmpty()

        val afterValidCodes = nullableDiffProperties.joinToString("\n\n") {
            buildScopeString(indent) {
                line("if (formData.value.${it.name} === undefined) {")
                scope {
                    line("sendMessage(\"${it.comment}不可为空\", \"warning\")")
                    line("return false")
                }
                line("}")
            }
        }

        return addForm(
            submitType = entity.dto.insertInput,
            submitTypePath = staticPath,
            dataType = entity.addFormDataType,
            dataTypePath = componentPath + "/" + entity.dir + "/" + entity.addFormDataType,
            createDefault = entity.addFormCreateDefault,
            defaultPath = componentPath + "/" + entity.dir + "/" + entity.addFormCreateDefault,
            useRules = "useRules",
            useRulesPath = rulePath + "/" + entity.dir + "/" + entity.rules.addFormRules,
            formData = formData,
            indent = indent,
            selectOptions = entity.insertSelectProperties.selectOptions,
            afterValidCodes = afterValidCodes,
            content = entity.addFormProperties
                .associateWith { it.createFormItem(formData) }
        ).merge {
            if (hasNullableDiffProperties) {
                imports += Import("$utilPath/message", "sendMessage")
            }
        }
    }

    @Throws(
        ModelException.IdPropertyNotFound::class,
        ModelException.IndexRefPropertyNotFound::class,
        ModelException.IndexRefPropertyCannotBeList::class
    )
    fun EditFormRules(entity: EntityBusiness): String {
        val editFormRulesProperties = entity.editFormRulesProperties
        val rules = iterableMapOf(
            editFormRulesProperties.associateWith { it.rules },
            entity.existValidRules(withId = true, editFormRulesProperties)
        )
        return rulesBuilder.createFormRules(
            functionName = "useRules",
            formData = "formData",
            formDataType = entity.dto.updateInput,
            propertyRules = rules
        )
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    fun EditForm(entity: EntityBusiness): Component {
        val formData = "formData"

        return editForm(
            formData = formData,
            type = entity.dto.updateInput,
            typePath = staticPath,
            useRules = "useRules",
            useRulesPath = rulePath + "/" + entity.dir + "/" + entity.rules.editFormRules,
            indent = indent,
            selectOptions = entity.updateSelectProperties.selectOptions,
            content = entity.editFormProperties
                .associateWith {
                    it.createFormItem(
                        formData,
                        excludeSelf = true,
                        entityId = entity.id,
                        idName = entity.idProperty.name
                    )
                }
        )
    }

    @Throws(
        ModelException.IdPropertyNotFound::class,
        ModelException.IndexRefPropertyNotFound::class,
        ModelException.IndexRefPropertyCannotBeList::class
    )
    fun EditTableRules(entity: EntityBusiness): String {
        val editTableRulesProperties = entity.subFormRulesProperties
        val rules = iterableMapOf(
            editTableRulesProperties.associateWith { it.rules },
            entity.existValidRules(withId = false, editTableRulesProperties),
        )
        return rulesBuilder.createFormRules(
            functionName = "useRules",
            formData = "formData",
            formDataType = entity.dto.updateInput,
            propertyRules = rules,
            isArray = true
        )
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    fun EditTable(entity: EntityBusiness): Component {
        val rows = "rows"

        return editTable(
            formData = rows,
            type = entity.addFormDataType,
            typePath = staticPath,
            useRules = "useRules",
            createDefault = entity.addFormCreateDefault,
            defaultPath = componentPath + "/" + entity.dir + "/" + entity.addFormCreateDefault,
            useRulesPath = rulePath + "/" + entity.dir + "/" + entity.rules.editTableRules,
            indent = indent,
            idPropertyName = entity.idProperty.name,
            comment = entity.comment,
            selectOptions = entity.subFormSelectProperties.selectOptions,
            content = entity.subFormProperties
                .associateWith {
                    it.createFormItem(
                        "scope.row",
                        excludeSelf = true,
                        entityId = entity.id,
                        idName = entity.idProperty.name
                    )
                }
        )
    }

    @Throws(ModelException.TreeEntityCannotFoundParentProperty::class)
    fun IdSelect(entity: EntityBusiness, multiple: Boolean): Component =
        if (entity.isTree) {
            createIdTreeSelect(entity, multiple)
        } else {
            createIdSelect(entity, multiple)
        }

    override fun generateEnum(
        enum: EnumBusiness,
    ): List<GenerateFile> {
        val dir = enum.dir
        val (view, select, nullableSelect) = enum.components

        return listOf(
            GenerateFile(
                enum,
                "components/${dir}/${view}.vue",
                stringify(EnumView(enum)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumView),
            ),
            GenerateFile(
                enum,
                "components/${dir}/${select}.vue",
                stringify(EnumSelect(enum, nullable = false)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumSelect),
            ),
            GenerateFile(
                enum,
                "components/${dir}/${nullableSelect}.vue",
                stringify(EnumSelect(enum, nullable = true)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Enum, GenerateTag.EnumNullableSelect),
            )
        )
    }

    @Throws(ModelException::class)
    override fun generateView(
        entity: EntityBusiness,
    ): List<GenerateFile> {
        val dir = entity.dir
        val (table, addForm, editForm, queryForm, page, idSelect, idMultiSelect, editTable) = entity.components
        val (addFormRules, editFormRules, editTableRules) = entity.rules

        val result = mutableListOf<GenerateFile>()

        result += GenerateFile(
            entity,
            "components/${dir}/${table}.vue",
            stringify(ViewTable(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Table, GenerateTag.ViewTable),
        )

        if (entity.canAdd) {
            val addFormDataType = entity.addFormDataType
            val defaultAddFormData = entity.addFormCreateDefault

            result += GenerateFile(
                entity,
                "components/${dir}/${addFormDataType}.d.ts",
                AddFormTypeDeclare(entity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.AddForm, GenerateTag.AddFormDataType),
            )
            result += GenerateFile(
                entity,
                "components/${dir}/${defaultAddFormData}.ts",
                AddFormDefaultFunction(entity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Form, GenerateTag.AddForm, GenerateTag.DefaultAddFormData),
            )
            result += GenerateFile(
                entity,
                "components/${dir}/${addForm}.vue",
                stringify(AddForm(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.AddForm),
            )
        }

        if (entity.canEdit) {
            result += GenerateFile(
                entity,
                "components/${dir}/${editForm}.vue",
                stringify(EditForm(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.EditForm),
            )
        }

        // TODO when long association
        result += GenerateFile(
            entity,
            "components/${dir}/${editTable}.vue",
            stringify(EditTable(entity)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.Table, GenerateTag.EditTable),
        )

        if (entity.canQuery) {
            result += GenerateFile(
                entity,
                "components/${dir}/${queryForm}.vue",
                stringify(QueryForm(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Form, GenerateTag.QueryForm),
            )
        }

        if (entity.hasPage) {
            result += GenerateFile(
                entity,
                "pages/${dir}/${page}.vue",
                stringify(Page(entity)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Page),
            )
        }

        // TODO when short association
        result += GenerateFile(
            entity,
            "components/${dir}/${idSelect}.vue",
            stringify(IdSelect(entity, multiple = false)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdSelect),
        )
        result += GenerateFile(
            entity,
            "components/${dir}/${idMultiSelect}.vue",
            stringify(IdSelect(entity, multiple = true)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdMultiSelect),
        )

        if (entity.canAdd) {
            result += GenerateFile(
                entity,
                "rules/${dir}/${addFormRules}.ts",
                AddFormRules(entity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.AddFormRules, GenerateTag.AddForm),
            )
        }

        if (entity.canEdit) {
            result += GenerateFile(
                entity,
                "rules/${dir}/${editFormRules}.ts",
                EditFormRules(entity),
                listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.EditFormRules, GenerateTag.EditForm),
            )
        }

        result += GenerateFile(
            entity,
            "rules/${dir}/${editTableRules}.ts",
            EditTableRules(entity),
            listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.EditTableRules, GenerateTag.EditTable),
        )

        return result
    }
}