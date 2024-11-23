package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.property.EntityPropertyCategories
import top.potmot.core.business.utils.apiServiceName
import top.potmot.core.business.utils.components
import top.potmot.core.business.utils.constants
import top.potmot.core.business.utils.dir
import top.potmot.core.business.utils.dto
import top.potmot.core.business.utils.enums
import top.potmot.core.business.utils.idProperty
import top.potmot.core.business.utils.rules
import top.potmot.core.business.utils.selectOptionLabels
import top.potmot.core.business.utils.typeStrToTypeScriptType
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
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.error.ModelException
import top.potmot.utils.string.appendLines

object Vue3ElementPlusViewGenerator :
    ViewGenerator,
    ElementPlus,
    EntityPropertyCategories,
    TableColumn,
    FormItem,
    QueryFormItem,
    AddFormDefault,
    AddFormType {
    private val builder = Vue3ComponentBuilder()

    private val rulesBuilder = Vue3ElementPlusRuleBuilder(builder.indent, builder.wrapThreshold)

    override fun getFileSuffix() = "vue"

    override fun stringifyEnumView(enum: GenEnumGenerateView): String {
        val items = enum.items.mapIndexed { index, it ->
            val ifExpression = "value === '${it.name}'"

            text(it.comment).merge {
                directives += VIf(expression = ifExpression, index != 0)
            }
        }

        val component = Component(
            imports = listOf(
                ImportType(enumPath, enum.name),
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
        val dir = enum.dir
        val view = enum.components.view

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

    override fun stringifyEnumSelect(enum: GenEnumGenerateView) =
        builder.build(createEnumSelectVue(enum, nullable = false))

    override fun stringifyEnumNullableSelect(enum: GenEnumGenerateView) =
        builder.build(createEnumSelectVue(enum, nullable = true))

    private val Iterable<GenEntityBusinessView.TargetOf_properties>.selectOptionPairs
        get() = mapNotNull {
            if (it.typeEntity == null)
                null
            else
                it to SelectOption(
                    it.name + "Options",
                    it.typeEntity.dto.optionView,
                )
        }

    private val Iterable<GenEntityBusinessView.TargetOf_properties>.selectOptions
        get() = selectOptionPairs.map { it.second }

    override fun stringifyQueryForm(entity: GenEntityBusinessView): String {
        val spec = "spec"

        return builder.build(
            queryForm(
                spec = spec,
                specType = entity.dto.spec,
                specTypePath = staticPath,
                selectOptions = entity.specificationSelectProperties.selectOptions,
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
                type = entity.dto.listView,
                typePath = staticPath,
                idPropertyName = entity.idProperty.name,
                content = entity.tableProperties
                    .associateWith { it.createTableColumn() }
            )
        )
    }

    override fun stringifyAddFormType(entity: GenEntityBusinessView): String {
        val context = entity.addFormProperties.associateWith { it.addFormType }
        val enumImports = entity.enums.map {
            ImportType(enumPath, it.name)
        }

        return buildString {
            builder.apply {
                appendLines(enumImports.stringifyImports())
            }

            if (enumImports.isNotEmpty()) appendLine()

            appendLine("export type ${entity.addFormDataType} = {")
            context.forEach { (property, type) ->
                appendLine("${builder.indent}${property.name}: $type")
            }
            appendLine("}")
        }
    }

    @Throws(ModelException.DefaultItemNotFound::class)
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

    @Throws(
        ModelException.IdPropertyNotFound::class,
        ModelException.IndexRefPropertyNotFound::class,
        ModelException.IndexRefPropertyCannotBeList::class
    )
    override fun stringifyAddFormRules(entity: GenEntityBusinessView): String {
        val addFormRulesProperties = entity.addFormRulesProperties
        val rules = addFormRulesProperties.associateWith { it.rules } +
                entity.existValidRules(withId = false, addFormRulesProperties)
        return rulesBuilder.createFormRules("useRules", "formData", entity.dto.insertInput, rules)
    }

    override fun stringifyAddForm(entity: GenEntityBusinessView): String {
        val formData = "formData"

        val nullableDiffProperties = entity.addFormEditNullableProperty

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
                submitType = entity.dto.insertInput,
                submitTypePath = staticPath,
                type = entity.addFormDataType,
                typePath = staticPath,
                default = entity.addFormDefault,
                defaultPath = componentPath + "/" + entity.dir + "/" + entity.addFormDefault,
                useRules = "useRules",
                useRulesPath = rulePath + "/" + entity.rules.addFormRules,
                formData = formData,
                indent = builder.indent,
                selectOptions = entity.insertSelectProperties.selectOptions,
                afterValidCodes = afterValidCodes,
                content = entity.addFormProperties
                    .associateWith { it.createFormItem(formData) }
            ).merge {
                if (hasNullableDiffProperties) {
                    imports += Import("$utilPath/message", "sendMessage")
                }
            }
        )
    }

    @Throws(
        ModelException.IdPropertyNotFound::class,
        ModelException.IndexRefPropertyNotFound::class,
        ModelException.IndexRefPropertyCannotBeList::class
    )
    override fun stringifyEditFormRules(entity: GenEntityBusinessView): String {
        val editFormRulesProperties = entity.editFormRulesProperties
        val rules = editFormRulesProperties.associateWith { it.rules } +
                entity.existValidRules(withId = true, editFormRulesProperties)
        return rulesBuilder.createFormRules("useRules", "formData", entity.dto.updateInput, rules)
    }

    override fun stringifyEditForm(entity: GenEntityBusinessView): String {
        val formData = "formData"

        return builder.build(
            editForm(
                formData = formData,
                type = entity.dto.updateInput,
                typePath = staticPath,
                useRules = "useRules",
                useRulesPath = rulePath + "/" + entity.rules.editFormRules,
                indent = builder.indent,
                selectOptions = entity.updateSelectProperties.selectOptions,
                content = entity.editFormProperties
                    .associateWith { it.createFormItem(formData) }
            )
        )
    }

    @Throws(
        ModelException.IdPropertyNotFound::class,
        ModelException.IndexRefPropertyNotFound::class,
        ModelException.IndexRefPropertyCannotBeList::class
    )
    override fun stringifyEditTableRules(entity: GenEntityBusinessView): String {
        val editTableRulesProperties = entity.editTableRulesProperties
        val rules = editTableRulesProperties.associateWith { it.rules } +
                entity.existValidRules(withId = false, editTableRulesProperties)
        return rulesBuilder.createFormRules("useRules", "formData", entity.dto.updateInput, rules, isArray = true)
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
                defaultPath = componentPath + "/" + entity.dir + "/" + entity.addFormDefault,
                useRulesPath = rulePath + "/" + entity.rules.editTableRules,
                indent = builder.indent,
                idPropertyName = entity.idProperty.name,
                comment = entity.comment,
                selectOptions = entity.editTableSelectProperties.selectOptions,
                content = entity.editTableProperties
                    .associateWith { it.createFormItem(rows) }
            )
        )
    }

    private fun createIdSelect(entity: GenEntityBusinessView, multiple: Boolean): Component {
        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)

        val optionView = entity.dto.optionView

        val labels = entity.selectOptionLabels.ifEmpty { listOf(idName) }

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
                            label = {
                                if (labels.size == 1) {
                                    "$it.$labels"
                                } else {
                                    "`${labels.joinToString("_") { label -> "${'$'}{$it.$label}" }}`"
                                }
                            },
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

    private fun createOptionQuery(
        comment: String,
        selectOption: SelectOption,
        serviceName: String,
    ) = mutableListOf(
        ImportType("vue", "Ref"),
        ImportType(staticPath, selectOption.type),
        Import("vue", "onBeforeMount"),
        Import("@/api", "api"),
    ) to mutableListOf(
        commentLine("${comment}选项"),
        selectOption.toVariable(),
        CodeBlock(
            "onBeforeMount(async () => {\n",
            "${builder.indent}${selectOption.name}.value = await api.$serviceName.listOptions({body: {}})\n",
            "})\n"
        )
    )

    override fun stringifyPage(entity: GenEntityBusinessView): String {
        val dir = entity.dir
        val (table, addForm, editForm, queryForm) = entity.components
        val (listView, _, insertInput, updateInput, spec) = entity.dto

        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)
        val apiServiceName = entity.apiServiceName

        val selectOptionPairs = entity.selectProperties.selectOptionPairs
        val optionQueries = selectOptionPairs.map {
            createOptionQuery(it.first.comment, it.second, apiServiceName)
        }

        val insertSelectNames = entity.insertSelectProperties.selectOptions.map { it.name }
        val updateSelectNames = entity.updateSelectProperties.selectOptions.map { it.name }
        val specificationSelectNames = entity.specificationSelectProperties.selectOptions.map { it.name }

        return builder.build(
            Component(
                imports = listOfNotNull(
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
                            "Page", "PageQuery",
                            listView,
                            if (!entity.canAdd) null else insertInput,
                            if (!entity.canEdit) null else updateInput,
                            if (!entity.canQuery) null else spec,
                        )
                    ),
                    Import(apiPath, "api"),
                    Import("$utilPath/message", "sendMessage"),
                    if (!entity.canDelete) null else Import("$utilPath/confirm", "deleteConfirm"),
                    Import("$utilPath/loading", "useLoading"),
                    Import("$utilPath/legalPage", "useLegalPage"),
                ) + listOfNotNull(
                    table,
                    if (!entity.canAdd) null else addForm,
                    if (!entity.canEdit) null else editForm,
                    if (!entity.canQuery) null else queryForm
                ).map {
                    ImportDefault("$componentPath/$dir/$it.vue", it)
                } + optionQueries.flatMap { it.first },
                script = listOfNotNull(
                    ConstVariable("{isLoading, withLoading}", null, "useLoading()\n"),
                    ConstVariable("pageData", null, "ref<Page<EntityListView>>()\n"),
                    commentLine("分页查询"),
                    ConstVariable(
                        "queryInfo", null,
                        "ref<PageQuery<EntitySpec>>({\n",
                        listOf(
                            "spec: {}",
                            "pageIndex: 1",
                            "pageSize: 5"
                        ).joinToString(",\n") { "${builder.indent}$it" },
                        "\n})\n",
                    ),
                    ConstVariable(
                        "{queryPage}", null,
                        "useLegalPage(\n",
                        listOf(
                            "pageData",
                            "queryInfo",
                            "withLoading(api.entityService.page)"
                        ).joinToString(",\n") { "${builder.indent}$it" },
                        "\n)\n",
                    ),
                    if (!entity.canEdit) null else ConstVariable(
                        "get${entity.name}",
                        null,
                        "withLoading((id: $idType) => api.$apiServiceName.get({id}))\n"
                    ),
                    if (!entity.canAdd) null else ConstVariable(
                        "add${entity.name}",
                        null,
                        "withLoading((body: $insertInput) => api.$apiServiceName.insert({body}))\n"
                    ),
                    if (!entity.canEdit) null else ConstVariable(
                        "edit${entity.name}",
                        null,
                        "withLoading((body: $updateInput) => api.$apiServiceName.update({body}))\n"
                    ),
                    if (!entity.canDelete) null else ConstVariable(
                        "delete${entity.name}",
                        null,
                        "withLoading((ids: Array<$idType>) => api.$apiServiceName.delete({ids}))\n"
                    ),

                    commentLine("多选"),
                    ConstVariable("selection", null, "ref<EntityListView[]>([])"),
                    emptyLineCode,
                    Function(
                        name = "handleSelectionChange",
                        args = listOf(FunctionArg("newSelection", "Array<$listView>")),
                        "selection.value = newSelection"
                    ),
                    emptyLineCode,

                    *optionQueries.flatMap { it.second }.toTypedArray(),

                    *if (!entity.canAdd) emptyArray() else arrayOf(
                        commentLine("新增"),
                        ConstVariable("addDialogVisible", null, "ref<boolean>(false)"),
                        emptyLineCode,
                        Function(
                            name = "startAdd",
                            body = listOf(
                                CodeBlock("addDialogVisible.value = true")
                            )
                        ),
                        emptyLineCode,
                        Function(
                            name = "submitAdd",
                            args = listOf(FunctionArg("insertInput", insertInput)),
                            "try {\n",
                            listOf(
                                "await add${entity.name}(insertInput)",
                                "await queryPage()",
                                "addDialogVisible.value = false",
                                "",
                                "sendMessage('新增${entity.comment}成功', 'success')"
                            ).joinToString("") { "${builder.indent}$it\n" },
                            "} catch (e) {\n",
                            listOf(
                                "sendMessage(\"新增${entity.comment}失败\", \"error\", e)"
                            ).joinToString("") { "${builder.indent}$it\n" },
                            "}"
                        ),
                        emptyLineCode,
                        Function(
                            name = "cancelAdd",
                            body = listOf(
                                CodeBlock("addDialogVisible.value = false")
                            )
                        ),
                    ),

                    *if (!entity.canEdit) emptyArray() else arrayOf(
                        emptyLineCode,
                        commentLine("修改"),
                        ConstVariable("editDialogVisible", null, "ref(false)"),
                        emptyLineCode,
                        ConstVariable("updateInput", null, "ref<${entity.name}UpdateInput | undefined>(undefined)"),
                        emptyLineCode,
                        Function(
                            name = "startEdit",
                            args = listOf(FunctionArg("id", "number")),
                            "updateInput.value = await get${entity.name}(id)\n",
                            "if (updateInput.value === undefined) {\n",
                            listOf(
                                "sendMessage('编辑的${entity.comment}不存在', 'error')",
                                "return"
                            ).joinToString("") { "${builder.indent}$it\n" },
                            "}\n",
                            "editDialogVisible.value = true"
                        ),
                        emptyLineCode,
                        Function(
                            name = "submitEdit",
                            args = listOf(FunctionArg("updateInput", "${entity.name}UpdateInput")),
                            "try {\n",
                            listOf(
                                "await edit${entity.name}(updateInput)",
                                "await queryPage()",
                                "editDialogVisible.value = false",
                                "",
                                "sendMessage('编辑${entity.comment}成功', 'success')"
                            ).joinToString("") { "${builder.indent}$it\n" },
                            "} catch (e) {\n",
                            listOf(
                                "sendMessage('编辑${entity.comment}失败', 'error', e)"
                            ).joinToString("") { "${builder.indent}$it\n" },
                            "}"
                        ),
                        emptyLineCode,
                        Function(
                            name = "cancelEdit",
                            body = listOf(
                                CodeBlock("editDialogVisible.value = false\nupdateInput.value = undefined")
                            )
                        ),
                    ),

                    *if (!entity.canDelete) emptyArray() else arrayOf(
                        emptyLineCode,
                        commentLine("删除"),
                        Function(
                            name = "handleDelete",
                            args = listOf(FunctionArg("ids", "number[]")),
                            "const result = await deleteConfirm('${entity.comment}')\n",
                            "if (!result) return\n",
                            "\n",
                            "try {\n",
                            listOf(
                                "await delete${entity.name}(ids)",
                                "await queryPage()",
                                "",
                                "sendMessage('删除${entity.comment}成功', 'success')"
                            ).joinToString("") { "${builder.indent}$it\n" },
                            "} catch (e) {\n",
                            listOf(
                                "sendMessage('删除${entity.comment}失败', 'error', e)"
                            ).joinToString("") { "${builder.indent}$it\n" },
                            "}",
                        )
                    )
                ),
                template = listOf(
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
                                        VModel("queryForm.spec")
                                    ),
                                    props = specificationSelectNames.map {
                                        PropBind(it, it)
                                    },
                                    events = listOf(
                                        EventBind("query", "queryPage")
                                    )
                                ).merge {
                                    directives += VIf(specificationSelectNames.joinToString(" && "))
                                },
                                emptyLineElement,
                            ),
                            TagElement(
                                "div",
                                children = listOfNotNull(
                                    if (!entity.canAdd) null else button(
                                        content = "新增",
                                        type = ElementPlus.Type.PRIMARY,
                                        icon = "Plus",
                                    ).merge {
                                        events += EventBind("click", "startAdd")
                                    },
                                    if (!entity.canDelete) null else button(
                                        content = "删除",
                                        type = ElementPlus.Type.DANGER,
                                        icon = "Delete",
                                    ).merge {
                                        directives += VIf("selection.length === 0")
                                        events += EventBind("click", "handleDelete(selection.map(it => it.id))")
                                    },
                                )
                            ),
                            emptyLineElement,
                            TagElement(
                                table,
                                props = listOf(
                                    PropBind("rows", "pageData.rows"),
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
                                                plain = true,
                                            ).merge {
                                                events += EventBind("click", "startEdit(row.$idName)")
                                            },
                                            if (!entity.canDelete) null else button(
                                                content = "删除",
                                                type = ElementPlus.Type.DANGER,
                                                icon = "Delete",
                                                plain = true,
                                            ).merge {
                                                events += EventBind("click", "handleDelete([row.$idName])")
                                            },
                                        )
                                    )
                                )
                            ),
                            emptyLineElement,
                            pagination(
                                currentPage = "queryInfo.pageIndex",
                                pageSize = "queryInfo.pageSize",
                                total = "pageData.totalRowCount",
                            )
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
                            directives += VIf(insertSelectNames.joinToString(" && "))
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
                            directives += VIf(updateSelectNames.joinToString(" && "))
                        }
                    )
                )
            )
        )
    }
}