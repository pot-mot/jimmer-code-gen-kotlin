package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.ForceIdViewProperty
import top.potmot.core.business.utils.mark.apiServiceName
import top.potmot.core.business.utils.mark.components
import top.potmot.core.business.utils.mark.constants
import top.potmot.core.business.utils.mark.dir
import top.potmot.core.business.utils.mark.dto
import top.potmot.core.business.utils.type.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.ViewGenerator
import top.potmot.core.business.view.generate.apiPath
import top.potmot.core.business.view.generate.builder.rules.Vue3ElementPlusRuleBuilder
import top.potmot.core.business.view.generate.builder.rules.existValidRules
import top.potmot.core.business.view.generate.builder.rules.rules
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.builder.vue3.elementPlus.ElementPlus
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
import top.potmot.core.business.view.generate.impl.vue3elementPlus.select.IdSelect
import top.potmot.core.business.view.generate.impl.vue3elementPlus.select.IdTreeSelect
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.viewTable
import top.potmot.core.business.view.generate.impl.vue3elementPlus.tableColumn.TableColumn
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.FunctionArg
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.commentLine
import top.potmot.core.business.view.generate.meta.typescript.emptyLineCode
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.VModel
import top.potmot.core.business.view.generate.meta.vue3.emptyLineElement
import top.potmot.core.business.view.generate.meta.vue3.slotTemplate
import top.potmot.core.business.view.generate.rulePath
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.storePath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException
import top.potmot.utils.list.join
import top.potmot.utils.map.iterableMapOf
import top.potmot.utils.string.buildScopeString

object Vue3ElementPlusViewGenerator :
    ViewGenerator,
    ElementPlus,
    TableColumn,
    FormItem,
    IdSelect,
    IdTreeSelect,
    QueryFormItem,
    AddFormDefault,
    AddFormType {
    private val componentBuilder = Vue3ComponentBuilder()

    fun stringify(component: Component) = componentBuilder.build(component)

    private val indent = componentBuilder.indent
    private val wrapThreshold = componentBuilder.wrapThreshold

    private val rulesBuilder = Vue3ElementPlusRuleBuilder(indent, wrapThreshold)

    private val EntityBusiness.addFormDataType
        get() = "${entity.name}AddFormType"

    val EntityBusiness.addFormCreateDefault
        get() = "createDefault${entity.name}"


    fun EnumView(enum: GenEnumGenerateView): Component {
        val items = enum.items.mapIndexed { index, it ->
            val ifExpression = "value === '${it.name}'"

            text(it.comment).merge {
                directives += VIf(expression = ifExpression, index != 0)
            }
        }

        return Component(
            imports = listOf(
                ImportType(enumPath, enum.name),
            ),
            props = listOf(
                Prop("value", enum.name)
            ),
            template = items
        )
    }

    fun EnumSelect(
        enum: GenEnumGenerateView,
        nullable: Boolean,
    ): Component {
        val dir = enum.dir
        val view = enum.components.view

        val modelValue = "modelValue"
        val options = enum.constants
        val option = "option"

        val selectElement = select(
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

    private val Iterable<ForceIdViewProperty>.selectOptionPairs
        get() = map {
            it to SelectOption(
                it.name + "Options",
                it.typeEntity.dto.optionView,
                it.typeEntity.apiServiceName
            )
        }

    private val Iterable<ForceIdViewProperty>.selectOptions
        get() = selectOptionPairs.map { it.second }

    fun QueryForm(entity: EntityBusiness): Component {
        val spec = "spec"

        return queryForm(
            spec = spec,
            specType = entity.dto.spec,
            specTypePath = staticPath,
            selectOptions = entity.specificationSelectPropertyBusiness.selectOptions,
            content = entity.queryPropertyBusiness
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
            content = entity.tablePropertyBusiness.flatMap { it.tableColumnDataList() },
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
                entity.addFormPropertyBusiness.forEach {
                    line("${it.property.name}: ${it.property.addFormType}")
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
                    entity.addFormPropertyBusiness.forEach {
                        line("${it.property.name}: ${it.property.addFormDefault},")
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
        val addFormRulesProperties = entity.addFormRulesPropertyBusiness
        val rules = iterableMapOf(
            addFormRulesProperties.associateWith { it.property.rules },
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

        val nullableDiffProperties = entity.addFormEditNullablePropertyBusiness

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
            selectOptions = entity.insertSelectPropertyBusiness.selectOptions,
            afterValidCodes = afterValidCodes,
            content = entity.addFormPropertyBusiness
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
        val editFormRulesProperties = entity.editFormRulesPropertyBusiness
        val rules = iterableMapOf(
            editFormRulesProperties.associateWith { it.property.rules },
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
            selectOptions = entity.updateSelectPropertyBusiness.selectOptions,
            content = entity.editFormPropertyBusiness
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
        val editTableRulesProperties = entity.editTableRulesPropertyBusiness
        val rules = iterableMapOf(
            editTableRulesProperties.associateWith { it.property.rules },
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
            selectOptions = entity.editTableSelectPropertyBusiness.selectOptions,
            content = entity.editTablePropertyBusiness
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

    private fun optionQueryCodes(
        comment: String,
        selectOption: SelectOption,
    ) = mutableListOf(
        ImportType(staticPath, selectOption.type),
        Import("vue", "onBeforeMount"),
        Import("@/api", "api"),
    ) to mutableListOf(
        commentLine("${comment}选项"),
        selectOption.toVariable(),
        emptyLineCode,
        ConstVariable(
            "set${selectOption.upperName}",
            null,
            "withLoading(async () => {\n",
            "${indent}${selectOption.name}.value = await api.${selectOption.apiServiceName}.listOptions({body: {}})\n",
            "})",
        ),
        emptyLineCode,
        CodeBlock(
            "onBeforeMount(async () => {\n",
            "${indent}await set${selectOption.upperName}()\n",
            "})"
        )
    )

    // FIXME 移动至 entity 内
    private const val queryByPage: Boolean = true

    fun Page(entity: EntityBusiness): Component {
        val dir = entity.dir
        val (table, addForm, editForm, queryForm) = entity.components
        val (listView, treeView, _, insertInput, _, updateInput, spec) = entity.dto
        val permission = entity.permissions

        val needMessage = entity.canAdd || entity.canEdit || entity.canDelete
        val needUserStore = entity.canAdd || entity.canEdit || entity.canDelete

        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)
        val apiServiceName = entity.apiServiceName

        val selectOptionPairs = entity.pageSelectPropertyBusiness.selectOptionPairs
        val selfOptionPairs = selectOptionPairs
            .filter { (property, _) ->
                property.typeEntity.id == entity.id
            }
        val optionQueries = selectOptionPairs.map {
            optionQueryCodes(it.first.comment, it.second)
        }

        val insertSelectNames = entity.insertSelectPropertyBusiness.selectOptions.map { it.name }
        val updateSelectNames = entity.updateSelectPropertyBusiness.selectOptions.map { it.name }
        val specificationSelectNames = entity.specificationSelectPropertyBusiness.selectOptions.map { it.name }

        val isTree = entity.isTree
        val dataType = if (isTree) treeView else listView

        val queryFn = if (queryByPage) "queryPage" else "queryRows"

        val component = Component {
            imports += listOfNotNull(
                Import("vue", "ref"),
                Import(
                    "@element-plus/icons-vue", listOfNotNull(
                        if (!entity.canAdd) null else "Plus",
                        if (!entity.canEdit) null else "EditPen",
                        if (!entity.canDelete) null else "Delete"
                    )
                ),
                ImportType(
                    staticPath, listOfNotNull(
                        if (queryByPage) "Page" else null,
                        if (queryByPage) "PageQuery" else null,
                        dataType,
                        if (!entity.canAdd) null else insertInput,
                        if (!entity.canEdit) null else updateInput,
                        if (!entity.canQuery) null else spec,
                    )
                ),
                Import(apiPath, "api"),
                if (!needMessage) null else Import("$utilPath/message", "sendMessage"),
                if (!needUserStore) null else Import("$storePath/userStore", "useUserStore"),
                if (!entity.canDelete) null else Import("$utilPath/confirm", "deleteConfirm"),
                Import("$utilPath/loading", "useLoading"),
                if (queryByPage) Import("$utilPath/legalPage", "useLegalPage") else null,
            )

            imports += listOfNotNull(
                table,
                if (!entity.canAdd) null else addForm,
                if (!entity.canEdit) null else editForm,
                if (!entity.canQuery) null else queryForm
            ).map {
                ImportDefault("$componentPath/$dir/$it.vue", it)
            }

            imports += optionQueries.flatMap { it.first }

            script += listOfNotNull(
                if (!needUserStore) null else ConstVariable("userStore", null, "useUserStore()\n"),
                ConstVariable("{isLoading, withLoading}", null, "useLoading()\n")
            )

            if (queryByPage) {
                script += listOf(
                    ConstVariable("pageData", null, "ref<Page<${dataType}>>()\n"),
                    commentLine("分页查询"),
                    ConstVariable(
                        "queryInfo", null,
                        buildScopeString(indent) {
                            line("ref<PageQuery<${spec}>>({")
                            scope {
                                lines(
                                    "spec: {},",
                                    "pageIndex: 0,",
                                    "pageSize: 5",
                                )
                            }
                            line("})")
                        }
                    ),
                    ConstVariable(
                        "{queryPage, currentPage}", null,
                        buildScopeString(indent) {
                            line("useLegalPage(")
                            scope {
                                lines(
                                    "pageData,",
                                    "queryInfo,"
                                )
                                block("withLoading(api.$apiServiceName.${if (isTree) "treePage" else "page"})")
                            }
                            line(")")
                        },
                    )
                )
            } else {
                script += listOf(
                    ConstVariable("rows", null, "ref<Array<${dataType}>>()\n"),
                    commentLine("数据查询"),
                    ConstVariable(
                        "spec", null,
                        "ref<$spec>({})\n"
                    ),
                    ConstVariable("queryRows", null,
                        buildScopeString(indent) {
                            line("withLoading(async () => {")
                            scope {
                                line("rows.value = await api.$apiServiceName.${if (isTree) "tree" else "list"}({body: spec.value})")
                            }
                            line("})")
                        }
                    ),
                    CodeBlock(buildScopeString {
                        line("onMounted(async () => {")
                        scope {
                            line("await queryRows()")
                        }
                        line("})")
                    })
                )

                imports += Import("vue", "onMounted")
            }

            if (entity.canAdd) {
                script += listOf(
                    ConstVariable(
                        "add${entity.name}",
                        null,
                        if (selfOptionPairs.isNotEmpty()) {
                            buildScopeString {
                                line("withLoading(async (body: $insertInput) => {")
                                scope {
                                    line("const result = await api.$apiServiceName.insert({body})")
                                    selfOptionPairs.forEach {
                                        line("await set${it.second.upperName}()")
                                    }
                                    line("return result")
                                }
                                append("})")
                            }
                        } else {
                            "withLoading((body: $insertInput) => api.$apiServiceName.insert({body}))"
                        }
                    ),
                    emptyLineCode,
                )
            }

            if (entity.canEdit) {
                script += listOf(
                    ConstVariable(
                        "get${entity.name}ForUpdate",
                        null,
                        "withLoading((id: $idType) => api.$apiServiceName.getForUpdate({id}))"
                    ),
                    emptyLineCode,
                    ConstVariable(
                        "edit${entity.name}",
                        null,
                        if (isTree) {
                            buildScopeString {
                                line("withLoading(async (body: $updateInput) => {")
                                scope {
                                    line("const result = await api.$apiServiceName.update({body})")
                                    selfOptionPairs.forEach {
                                        line("await set${it.second.upperName}()")
                                    }
                                    line("return result")
                                }
                                append("})")
                            }
                        } else {
                            "withLoading((body: $updateInput) => api.$apiServiceName.update({body}))"
                        }
                    ),
                    emptyLineCode,
                )
            }

            if (entity.canDelete) {
                script += listOf(
                    ConstVariable(
                        "delete${entity.name}",
                        null,
                        if (isTree) {
                            buildScopeString {
                                line("withLoading(async (ids: Array<$idType>) => {")
                                scope {
                                    line("const result = await api.$apiServiceName.delete({ids})")
                                    selfOptionPairs.forEach {
                                        line("await set${it.second.upperName}()")
                                    }
                                    line("return result")
                                }
                                append("})")
                            }
                        } else {
                            "withLoading((ids: Array<$idType>) => api.$apiServiceName.delete({ids}))"
                        }
                    ),
                    emptyLineCode,
                )
            }

            script += listOf(
                commentLine("多选"),
                ConstVariable("selection", null, "ref<${dataType}[]>([])"),
                emptyLineCode,
                Function(
                    name = "handleSelectionChange",
                    args = listOf(FunctionArg("newSelection", "Array<$dataType>")),
                    content = arrayOf("selection.value = newSelection")
                ),
                emptyLineCode
            )

            script += optionQueries.map { it.second }.join(listOf(emptyLineCode)).flatten()

            if (entity.canAdd) {
                script += listOf(
                    emptyLineCode,
                    commentLine("新增"),
                    ConstVariable("addDialogVisible", null, "ref<boolean>(false)"),
                    emptyLineCode,
                    Function(
                        name = "startAdd",
                        content = arrayOf("addDialogVisible.value = true")
                    ),
                    emptyLineCode,
                    Function(
                        async = true,
                        name = "submitAdd",
                        args = listOf(FunctionArg("insertInput", insertInput)),
                        buildScopeString(indent) {
                            line("try {")
                            scope {
                                line("await add${entity.name}(insertInput)")
                                line("await ${queryFn}()")
                                line("addDialogVisible.value = false")
                                line()
                                line("sendMessage('新增${entity.comment}成功', 'success')")
                            }
                            line("} catch (e) {")
                            scope {
                                line("sendMessage(\"新增${entity.comment}失败\", \"error\", e)")
                            }
                            append("}")
                        }
                    ),
                    emptyLineCode,
                    Function(
                        name = "cancelAdd",
                        content = arrayOf("addDialogVisible.value = false")
                    ),
                )
            }

            if (entity.canEdit) {
                script += listOf(
                    emptyLineCode,
                    commentLine("修改"),
                    ConstVariable("editDialogVisible", null, "ref(false)"),
                    emptyLineCode,
                    ConstVariable("updateInput", null, "ref<${entity.name}UpdateInput | undefined>(undefined)"),
                    emptyLineCode,
                    Function(
                        async = true,
                        name = "startEdit",
                        args = listOf(FunctionArg("id", idType)),
                        buildScopeString {
                            line("updateInput.value = await get${entity.name}ForUpdate(id)")
                            line("if (updateInput.value === undefined) {")
                            scope {
                                line("sendMessage('编辑的${entity.comment}不存在', 'error')")
                                line("return")
                            }
                            line("}")
                            append("editDialogVisible.value = true")
                        },
                    ),
                    emptyLineCode,
                    Function(
                        async = true,
                        name = "submitEdit",
                        args = listOf(FunctionArg("updateInput", "${entity.name}UpdateInput")),
                        buildScopeString {
                            line("try {")
                            scope {
                                line("await edit${entity.name}(updateInput)")
                                line("await ${queryFn}()")
                                line("editDialogVisible.value = false")
                                line()
                                line("sendMessage('编辑${entity.comment}成功', 'success')")
                            }
                            line("} catch (e) {")
                            scope {
                                line("sendMessage('编辑${entity.comment}失败', 'error', e)")
                            }
                            append("}")
                        },
                    ),
                    emptyLineCode,
                    Function(
                        name = "cancelEdit",
                        body = listOf(
                            CodeBlock("editDialogVisible.value = false\nupdateInput.value = undefined")
                        )
                    ),
                )
            }

            if (entity.canDelete) {
                script += listOf(
                    emptyLineCode,
                    commentLine("删除"),
                    Function(
                        async = true,
                        name = "handleDelete",
                        args = listOf(FunctionArg("ids", "Array<${idType}>")),
                        buildScopeString {
                            line("const result = await deleteConfirm('${entity.comment}')")
                            line("if (!result) return")
                            line()
                            line("try {")
                            scope {
                                line("await delete${entity.name}(ids)")
                                line("await ${queryFn}()")
                                line()
                                line("sendMessage('删除${entity.comment}成功', 'success')")
                            }
                            line("} catch (e) {")
                            scope {
                                line("sendMessage('删除${entity.comment}失败', 'error', e)")
                            }
                            append("}")
                        },
                    )
                )
            }

            template += listOf(
                TagElement(
                    "el-card",
                    props = listOf(
                        PropBind("v-loading", "isLoading", isLiteral = true)
                    ),
                    children = listOf(
                        *if (!entity.canQuery) emptyArray() else arrayOf(
                            TagElement(
                                queryForm,
                                directives = listOf(
                                    VModel(if (queryByPage) "queryInfo.spec" else "spec")
                                ),
                                props = specificationSelectNames.map {
                                    PropBind(it, it)
                                },
                                events = listOf(
                                    EventBind("query", queryFn)
                                )
                            ).merge {
                                directives += specificationSelectNames.map { VIf(it) }
                            },
                            emptyLineElement,
                        ),
                        TagElement(
                            "div",
                            props = listOf(
                                PropBind("class", "page-operations", isLiteral = true),
                            ),
                            children = listOfNotNull(
                                if (!entity.canAdd) null else button(
                                    content = "新增",
                                    type = ElementPlus.Type.PRIMARY,
                                    icon = "Plus",
                                ).merge {
                                    directives += VIf("userStore.permissions.includes('${permission.insert}')")
                                    events += EventBind("click", "startAdd")
                                },

                                if (!entity.canDelete) null else button(
                                    content = "删除",
                                    type = ElementPlus.Type.DANGER,
                                    icon = "Delete",
                                ).merge {
                                    directives += VIf("userStore.permissions.includes('${permission.delete}')")
                                    props += PropBind("disabled", "selection.length === 0")
                                    events += EventBind("click", "handleDelete(selection.map(it => it.$idName))")
                                },
                            )
                        ),
                        emptyLineElement,
                        TagElement(
                            "template",
                            directives = listOf(VIf(if (queryByPage) "pageData" else "rows")),
                            children = listOf(
                                TagElement(
                                    table,
                                    props = listOf(
                                        PropBind("rows", if (queryByPage) "pageData.rows" else "rows"),
                                    ),
                                    events = listOf(
                                        EventBind("selectionChange", "handleSelectionChange")
                                    ),
                                    children = listOf(
                                        slotTemplate(
                                            "operations",
                                            props = listOf("row"),
                                            content = listOfNotNull(
                                                if (!entity.canEdit) null else button(
                                                    content = "编辑",
                                                    type = ElementPlus.Type.WARNING,
                                                    icon = "EditPen",
                                                    link = true,
                                                ).merge {
                                                    directives += VIf("userStore.permissions.includes('${permission.update}')")
                                                    events += EventBind("click", "startEdit(row.$idName)")
                                                },
                                                if (!entity.canDelete) null else button(
                                                    content = "删除",
                                                    type = ElementPlus.Type.DANGER,
                                                    icon = "Delete",
                                                    link = true,
                                                ).merge {
                                                    directives += VIf("userStore.permissions.includes('${permission.delete}')")
                                                    events += EventBind("click", "handleDelete([row.$idName])")
                                                },
                                            )
                                        )
                                    )
                                )
                            ) + if (queryByPage) listOf(
                                emptyLineElement,
                                pagination(
                                    currentPage = "currentPage",
                                    pageSize = "queryInfo.pageSize",
                                    total = "pageData.totalRowCount",
                                )
                            ) else emptyList()
                        ),
                    )
                ),
                *if (!entity.canAdd) emptyArray() else arrayOf(
                    emptyLineElement,
                    dialog(
                        modelValue = "addDialogVisible",
                        content = listOf(
                            TagElement(
                                addForm
                            ).merge {
                                props += insertSelectNames.map { PropBind(it, it) }
                                props += PropBind("submitLoading", "isLoading")
                                events += EventBind("submit", "submitAdd")
                                events += EventBind("cancel", "cancelAdd")
                            }
                        )
                    ).merge {
                        directives += VIf("userStore.permissions.includes('${permission.insert}')")
                        directives += insertSelectNames.map { VIf(it) }
                    },
                ),
                *if (!entity.canEdit) emptyArray() else arrayOf(
                    emptyLineElement,
                    dialog(
                        modelValue = "editDialogVisible",
                        content = listOf(
                            TagElement(
                                editForm
                            ).merge {
                                directives += VIf("updateInput !== undefined")
                                directives += VModel("updateInput")
                                props += updateSelectNames.map { PropBind(it, it) }
                                props += PropBind("submitLoading", "isLoading")
                                events += EventBind("submit", "submitEdit")
                                events += EventBind("cancel", "cancelEdit")
                            }
                        )
                    ).merge {
                        directives += VIf("userStore.permissions.includes('${permission.update}')")
                        directives += updateSelectNames.map { VIf(it) }
                    }
                )
            )
        }

        return component
    }

    override fun generateEnum(
        enum: GenEnumGenerateView,
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
        entityBusiness: EntityBusiness,
    ): List<GenerateFile> {
        val entity = entityBusiness.entity

        val dir = entityBusiness.dir
        val (table, addForm, editForm, queryForm, page, idSelect, idMultiSelect, editTable) = entityBusiness.components
        val (addFormRules, editFormRules, editTableRules) = entityBusiness.rules

        val result = mutableListOf<GenerateFile>()

        result += GenerateFile(
            entityBusiness,
            "components/${dir}/${table}.vue",
            stringify(ViewTable(entityBusiness)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Table),
        )

        if (entity.canAdd) {
            val addFormDataType = entityBusiness.addFormDataType
            val defaultAddFormData = entityBusiness.addFormCreateDefault

            result += GenerateFile(
                entityBusiness,
                "components/${dir}/${addFormDataType}.d.ts",
                AddFormTypeDeclare(entityBusiness),
                listOf(GenerateTag.FrontEnd, GenerateTag.AddFormDataType),
            )
            result += GenerateFile(
                entityBusiness,
                "components/${dir}/${defaultAddFormData}.ts",
                AddFormDefaultFunction(entityBusiness),
                listOf(GenerateTag.FrontEnd, GenerateTag.DefaultAddFormData),
            )
            result += GenerateFile(
                entityBusiness,
                "components/${dir}/${addForm}.vue",
                stringify(AddForm(entityBusiness)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.AddForm),
            )
        }

        if (entity.canEdit) {
            result += GenerateFile(
                entityBusiness,
                "components/${dir}/${editForm}.vue",
                stringify(EditForm(entityBusiness)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.EditForm),
            )
        }

        // TODO when long association
        result += GenerateFile(
            entityBusiness,
            "components/${dir}/${editTable}.vue",
            stringify(EditTable(entityBusiness)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.EditTable),
        )

        if (entity.canQuery) {
            result += GenerateFile(
                entityBusiness,
                "components/${dir}/${queryForm}.vue",
                stringify(QueryForm(entityBusiness)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.QueryForm),
            )
        }

        if (entity.hasPage) {
            result += GenerateFile(
                entityBusiness,
                "pages/${dir}/${page}.vue",
                stringify(Page(entityBusiness)),
                listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Page),
            )
        }

        // TODO when short association
        result += GenerateFile(
            entityBusiness,
            "components/${dir}/${idSelect}.vue",
            stringify(IdSelect(entityBusiness, multiple = false)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdSelect),
        )
        result += GenerateFile(
            entityBusiness,
            "components/${dir}/${idMultiSelect}.vue",
            stringify(IdSelect(entityBusiness, multiple = true)),
            listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.IdMultiSelect),
        )

        if (entity.canAdd) {
            result += GenerateFile(
                entityBusiness,
                "rules/${dir}/${addFormRules}.ts",
                AddFormRules(entityBusiness),
                listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.AddFormRules, GenerateTag.AddForm),
            )
        }

        if (entity.canEdit) {
            result += GenerateFile(
                entityBusiness,
                "rules/${dir}/${editFormRules}.ts",
                EditFormRules(entityBusiness),
                listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.EditFormRules, GenerateTag.EditForm),
            )
        }

        result += GenerateFile(
            entityBusiness,
            "rules/${dir}/${editTableRules}.ts",
            EditTableRules(entityBusiness),
            listOf(GenerateTag.FrontEnd, GenerateTag.Rules, GenerateTag.EditTableRules, GenerateTag.EditTable),
        )

        return result
    }
}