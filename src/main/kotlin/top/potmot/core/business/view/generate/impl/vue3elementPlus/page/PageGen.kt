package top.potmot.core.business.view.generate.impl.vue3elementPlus.page

import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.type.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.apiPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.dialog
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.pagination
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.selectOptions.SelectOption
import top.potmot.core.business.view.generate.impl.vue3elementPlus.selectOptions.selectOption
import top.potmot.core.business.view.generate.impl.vue3elementPlus.selectOptions.selectOptions
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
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.TagElement
import top.potmot.core.business.view.generate.meta.vue3.VIf
import top.potmot.core.business.view.generate.meta.vue3.VModel
import top.potmot.core.business.view.generate.meta.vue3.emptyLineElement
import top.potmot.core.business.view.generate.meta.vue3.slotTemplate
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.storePath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.list.join
import top.potmot.utils.string.StringIndentScopeBuilder
import top.potmot.utils.string.buildScopeString

// FIXME 移动至 entity 内
private const val queryByPage: Boolean = true

interface PageGen : Generator {
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
            buildScopeString(indent) {
                line("withLoading(async () => {")
                scope { line("${selectOption.name}.value = await api.${selectOption.apiServiceName}.listOptions({body: {}})") }
                append("})")
            }
        ),
        emptyLineCode,
        CodeBlock(
            "onBeforeMount(async () => {\n",
            "${indent}await set${selectOption.upperName}()\n",
            "})"
        )
    )

    private fun functionBody(vararg body: String) = body.map { CodeBlock(it) }

    private fun buildFunctionBody(indent: String, body: StringIndentScopeBuilder.() -> Unit) = listOf(
        CodeBlock(
            buildScopeString(indent) {
                body()
            }
        )
    )

    private fun pageComponent(entity: RootEntityBusiness): Component {
        val table = entity.components.table
        val addForm = entity.components.addForm
        val editForm = entity.components.editForm
        val queryForm = entity.components.queryForm

        val (listView, treeView, _, insertInput, _, updateInput, spec) = entity.dto
        val permission = entity.permissions

        val needMessage = entity.canAdd || entity.canEdit || entity.canDelete
        val needUserStore = entity.canAdd || entity.canEdit || entity.canDelete

        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)
        val apiServiceName = entity.apiServiceName

        val selectOptionPairs = entity.pageSelectProperties.map { it to it.selectOption }
        val selfOptionPairs = selectOptionPairs
            .filter { (property, _) ->
                property.typeEntity.id == entity.id
            }
        val optionQueries = selectOptionPairs.map {
            optionQueryCodes(it.first.comment, it.second)
        }

        val insertSelectNames = entity.insertSelectProperties.selectOptions.map { it.name }
        val updateSelectNames = entity.updateSelectProperties.selectOptions.map { it.name }
        val specificationSelectNames = entity.specificationSelectProperties.selectOptions.map { it.name }

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
                ImportDefault("@/${it.fullPath}", it.name)
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
                    body = functionBody("selection.value = newSelection")
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
                        body = functionBody("addDialogVisible.value = true")
                    ),
                    emptyLineCode,
                    Function(
                        async = true,
                        name = "submitAdd",
                        args = listOf(FunctionArg("insertInput", insertInput)),
                        body = buildFunctionBody(indent) {
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
                        body = functionBody("addDialogVisible.value = false")
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
                        body = buildFunctionBody(indent) {
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
                        body = buildFunctionBody(indent) {
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
                        body = buildFunctionBody(indent) {
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
                                queryForm.name,
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
                                    type = ElementPlusComponents.Type.PRIMARY,
                                    icon = "Plus",
                                ).merge {
                                    directives += VIf("userStore.permissions.includes('${permission.insert}')")
                                    events += EventBind("click", "startAdd")
                                },

                                if (!entity.canDelete) null else button(
                                    content = "删除",
                                    type = ElementPlusComponents.Type.DANGER,
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
                                    table.name,
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
                                                    type = ElementPlusComponents.Type.WARNING,
                                                    icon = "EditPen",
                                                    link = true,
                                                ).merge {
                                                    directives += VIf("userStore.permissions.includes('${permission.update}')")
                                                    events += EventBind("click", "startEdit(row.$idName)")
                                                },
                                                if (!entity.canDelete) null else button(
                                                    content = "删除",
                                                    type = ElementPlusComponents.Type.DANGER,
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
                                addForm.name
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
                                editForm.name
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

    fun pageFile(entity: RootEntityBusiness) = GenerateFile(
        entity,
        entity.components.page.fullPath,
        stringify(pageComponent(entity)),
        listOf(GenerateTag.FrontEnd, GenerateTag.Component, GenerateTag.Page),
    )
}