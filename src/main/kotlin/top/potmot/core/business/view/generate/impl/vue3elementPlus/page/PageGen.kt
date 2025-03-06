package top.potmot.core.business.view.generate.impl.vue3elementPlus.page

import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.meta.SelectOption
import top.potmot.core.business.type.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.apiPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.button
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.dialog
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.pagination
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Generator
import top.potmot.core.common.typescript.CodeBlock
import top.potmot.core.common.typescript.ConstVariable
import top.potmot.core.common.typescript.Function
import top.potmot.core.common.typescript.FunctionArg
import top.potmot.core.common.typescript.Import
import top.potmot.core.common.typescript.ImportDefault
import top.potmot.core.common.typescript.ImportType
import top.potmot.core.common.typescript.commentLine
import top.potmot.core.common.typescript.emptyLineCode
import top.potmot.core.common.vue3.Component
import top.potmot.core.common.vue3.Element
import top.potmot.core.common.vue3.EventBind
import top.potmot.core.common.vue3.PropBind
import top.potmot.core.common.vue3.TagElement
import top.potmot.core.common.vue3.VIf
import top.potmot.core.common.vue3.VModel
import top.potmot.core.common.vue3.emptyLineElement
import top.potmot.core.common.vue3.slotTemplate
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.storePath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.collection.join
import top.potmot.utils.string.StringIndentScopeBuilder
import top.potmot.utils.string.buildScopeString

interface PageGen : Generator {
    private fun selectQueryCodes(
        selectOption: SelectOption,
    ) = mutableListOf(
        Import("@/api", "api"),
        Import("$utilPath/lazyOptions", "useLazyOptions")
    ) to mutableListOf(
        commentLine(selectOption.comment),
        CodeBlock(
            "const ${selectOption.name} = useLazyOptions(async () => {\n" +
                    "${indent}return await api.${selectOption.apiServiceName}.listOptions({body: {}})\n" +
                    "})",
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
        val viewForm = entity.components.viewForm
        val addForm = entity.components.addForm
        val editForm = entity.components.editForm
        val queryForm = entity.components.queryForm

        val (listView, treeView, _, insertInput, _, updateInput, spec) = entity.dto
        val permission = entity.permissions

        val pageCanAdd = entity.pageCanAdd
        val pageCanEdit = entity.pageCanEdit
        val pageCanViewDetail = entity.pageCanViewDetail
        val pageCanQuery = entity.pageCanQuery
        val pageCanDelete = entity.pageCanDelete

        val needMessage = pageCanAdd || pageCanEdit || pageCanDelete
        val needUserStore = pageCanAdd || pageCanEdit || pageCanDelete

        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)
        val apiServiceName = entity.apiServiceName

        val selectQueries = entity.pageSelects.map { selectQueryCodes(it) }
        val selfSelects = entity.pageSelectPairs.filter { it.first.typeEntity.id == entity.id }.map { it.second }

        val insertSelectNames = entity.insertSelects.map { it.name }
        val updateSelectNames = entity.updateSelects.map { it.name }
        val specificationSelectNames = entity.specificationSelects.map { it.name }

        val isTree = entity.isTree
        val dataType = if (isTree) treeView else listView

        val queryByPage = entity.queryByPage
        val queryFn = if (queryByPage) "queryPage" else "queryRows"

        val component = Component {
            imports += listOfNotNull(
                Import("vue", "ref"),
                Import(
                    "@element-plus/icons-vue", listOfNotNull(
                        if (!pageCanViewDetail) null else "View",
                        if (!pageCanAdd) null else "Plus",
                        if (!pageCanEdit) null else "EditPen",
                        if (!pageCanDelete) null else "Delete"
                    )
                ),
                ImportType(
                    staticPath, listOfNotNull(
                        if (queryByPage) "Page" else null,
                        if (queryByPage) "PageQuery" else null,
                        dataType,
                        if (!pageCanAdd) null else insertInput,
                        if (!pageCanEdit) null else updateInput,
                        if (!pageCanQuery) null else spec,
                    )
                ),
                Import(apiPath, "api"),
                if (!needMessage) null else Import("$utilPath/message", "sendMessage"),
                if (!needUserStore) null else Import("$storePath/userStore", "useUserStore"),
                if (!pageCanDelete) null else Import("$utilPath/confirm", "deleteConfirm"),
                Import("$utilPath/loading", "useLoading"),
                if (queryByPage) Import("$utilPath/legalPage", "useLegalPage") else null,
            )

            imports += listOfNotNull(
                table,
                if (!pageCanViewDetail) null else viewForm,
                if (!pageCanAdd) null else addForm,
                if (!pageCanEdit) null else editForm,
                if (!pageCanQuery) null else queryForm
            ).map {
                ImportDefault("@/${it.fullPath}", it.name)
            }

            imports += selectQueries.flatMap { it.first }

            script += listOfNotNull(
                if (!needUserStore) null else ConstVariable("userStore", null, "useUserStore()\n"),
                ConstVariable("{isLoading, withLoading}", null, "useLoading()\n")
            )

            val pageCardChildren = mutableListOf<Element>()
            val pageCard = TagElement(
                "el-card",
                props = listOf(
                    PropBind("v-loading", "isLoading", isLiteral = true)
                ),
                children = pageCardChildren
            )
            template += pageCard

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

            if (pageCanQuery) {
                pageCardChildren += TagElement(
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
                )
                pageCardChildren += emptyLineElement
            }

            pageCardChildren += TagElement("div") {
                props += PropBind("class", "page-operations", isLiteral = true)

                if (pageCanAdd) {
                    children += button(
                        content = "新增",
                        type = ElementPlusComponents.Type.PRIMARY,
                        icon = "Plus",
                    ).merge {
                        directives += VIf("userStore.permissions.includes('${permission.insert}')")
                        events += EventBind("click", "startAdd")
                    }
                }

                if (pageCanDelete) {
                    children += button(
                        content = "删除",
                        type = ElementPlusComponents.Type.DANGER,
                        icon = "Delete",
                    ).merge {
                        directives += VIf("userStore.permissions.includes('${permission.delete}')")
                        props += PropBind("disabled", "selection.length === 0")
                        events += EventBind("click", "handleDelete(selection.map(it => it.$idName))")
                    }
                }
            }

            pageCardChildren += emptyLineElement
            pageCardChildren += TagElement("template") {
                directives += VIf(if (queryByPage) "pageData" else "rows")

                children += TagElement(table.name) {
                    props += PropBind("rows", if (queryByPage) "pageData.rows" else "rows")
                    events += EventBind("selectionChange", "handleSelectionChange")
                    children += slotTemplate(
                        "operations",
                        props = listOf("row"),
                        content = listOfNotNull(
                            if (!pageCanViewDetail) null else button(
                                content = "详情",
                                type = ElementPlusComponents.Type.INFO,
                                icon = "View",
                                link = true,
                            ).merge {
                                directives += VIf("userStore.permissions.includes('${permission.get}')")
                                events += EventBind("click", "startView(row.$idName)")
                            },
                            if (!pageCanEdit) null else button(
                                content = "编辑",
                                type = ElementPlusComponents.Type.WARNING,
                                icon = "EditPen",
                                link = true,
                            ).merge {
                                directives += VIf("userStore.permissions.includes('${permission.update}')")
                                events += EventBind("click", "startEdit(row.$idName)")
                            },
                            if (!pageCanDelete) null else button(
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
                }

                if (queryByPage) {
                    children += emptyLineElement
                    children += pagination(
                        currentPage = "currentPage",
                        pageSize = "queryInfo.pageSize",
                        total = "pageData.totalRowCount",
                    )
                }
            }

            if (pageCanViewDetail) {
                script += listOf(
                    ConstVariable(
                        "get${entity.name}",
                        null,
                        "withLoading((id: $idType) => api.$apiServiceName.get({id}))"
                    ),
                    emptyLineCode,
                )
            }

            if (pageCanAdd) {
                script += listOf(
                    ConstVariable(
                        "add${entity.name}",
                        null,
                        if (selfSelects.isNotEmpty()) {
                            buildScopeString {
                                line("withLoading(async (body: $insertInput) => {")
                                scope {
                                    line("const result = await api.$apiServiceName.insert({body})")
                                    selfSelects.forEach {
                                        line("await ${it.name}.load()")
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

            if (pageCanEdit) {
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
                        if (selfSelects.isNotEmpty()) {
                            buildScopeString {
                                line("withLoading(async (body: $updateInput) => {")
                                scope {
                                    line("const result = await api.$apiServiceName.update({body})")
                                    selfSelects.forEach {
                                        line("await ${it.name}.load()")
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

            if (pageCanDelete) {
                script += listOf(
                    ConstVariable(
                        "delete${entity.name}",
                        null,
                        if (selfSelects.isNotEmpty()) {
                            buildScopeString {
                                line("withLoading(async (ids: Array<$idType>) => {")
                                scope {
                                    line("const result = await api.$apiServiceName.delete({ids})")
                                    selfSelects.forEach {
                                        line("await ${it.name}.load()")
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

            script += selectQueries.map { it.second }.join(listOf(emptyLineCode)).flatten()

            if (pageCanViewDetail) {
                val detailViewType = entity.dto.detailView

                imports += Import("vue", "watch")
                imports += ImportType(staticPath, detailViewType)
                script += listOf(
                    emptyLineCode,
                    commentLine("详情"),
                    ConstVariable("viewDialogVisible", null, "ref<boolean>(false)"),
                    emptyLineCode,
                    ConstVariable("detailView", null, "ref<$detailViewType | undefined>(undefined)"),
                    emptyLineCode,
                    Function(
                        async = true,
                        name = "startView",
                        args = listOf(FunctionArg("id", idType)),
                        body = buildFunctionBody(indent) {
                            line("detailView.value = await get${entity.name}(id)")
                            scope("if (detailView.value === undefined) {", "}") {
                                line("sendMessage('查看的${entity.comment}不存在', 'error')")
                                line("return")
                            }
                            append("viewDialogVisible.value = true")
                        },
                    ),
                    emptyLineCode,
                    CodeBlock(
                        buildScopeString(indent) {
                            scope("watch(() => viewDialogVisible.value, (newVal) => {", "})") {
                                scope("if (!newVal) {", "}") {
                                    line("detailView.value = undefined")
                                }
                            }
                        }
                    )
                )

                template += emptyLineElement
                template += dialog(
                    modelValue = "viewDialogVisible",
                    content = listOf(
                        TagElement(viewForm.name) {
                            directives += VIf("detailView !== undefined")
                            props += PropBind("value", "detailView")
                        }
                    )
                ).merge {
                    directives += VIf("userStore.permissions.includes('${permission.get}')")
                }
            }

            if (pageCanAdd) {
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
                            scopeEndNoLine("try {", "}") {
                                line("await add${entity.name}(insertInput)")
                                line("await ${queryFn}()")
                                line("addDialogVisible.value = false")
                                line()
                                line("sendMessage('新增${entity.comment}成功', 'success')")
                            }
                            scopeEndNoLine(" catch (e) {", "}") {
                                line("sendMessage(\"新增${entity.comment}失败\", \"error\", e)")
                            }
                        }
                    ),
                    emptyLineCode,
                    Function(
                        name = "cancelAdd",
                        body = functionBody("addDialogVisible.value = false")
                    ),
                )

                template += emptyLineElement
                template += dialog(
                    modelValue = "addDialogVisible",
                    content = listOf(
                        TagElement(addForm.name) {
                            props += insertSelectNames.map { PropBind(it, it) }
                            props += PropBind("submitLoading", "isLoading")
                            events += EventBind("submit", "submitAdd")
                            events += EventBind("cancel", "cancelAdd")
                        }
                    )
                ).merge {
                    directives += VIf("userStore.permissions.includes('${permission.insert}')")
                }
            }

            if (pageCanEdit) {
                val editFormType = entity.components.editFormType

                imports += Import("vue", "watch")
                imports += ImportType("@/" + editFormType.fullPathNoSuffix, editFormType.name)
                script += listOf(
                    emptyLineCode,
                    commentLine("修改"),
                    ConstVariable("editDialogVisible", null, "ref(false)"),
                    emptyLineCode,
                    ConstVariable("updateInput", null, "ref<${editFormType.name} | undefined>(undefined)"),
                    emptyLineCode,
                    Function(
                        async = true,
                        name = "startEdit",
                        args = listOf(FunctionArg("id", idType)),
                        body = buildFunctionBody(indent) {
                            line("updateInput.value = await get${entity.name}ForUpdate(id)")
                            scope("if (updateInput.value === undefined) {", "}") {
                                line("sendMessage('编辑的${entity.comment}不存在', 'error')")
                                line("return")
                            }
                            append("editDialogVisible.value = true")
                        },
                    ),
                    emptyLineCode,
                    Function(
                        async = true,
                        name = "submitEdit",
                        args = listOf(FunctionArg("updateInput", "${entity.name}UpdateInput")),
                        body = buildFunctionBody(indent) {
                            scopeEndNoLine("try {", "}") {
                                line("await edit${entity.name}(updateInput)")
                                line("await ${queryFn}()")
                                line("editDialogVisible.value = false")
                                line()
                                line("sendMessage('编辑${entity.comment}成功', 'success')")
                            }
                            scopeEndNoLine(" catch (e)", "{") {
                                line("sendMessage('编辑${entity.comment}失败', 'error', e)")
                            }
                        },
                    ),
                    emptyLineCode,
                    Function(
                        name = "cancelEdit",
                        body = listOf(
                            CodeBlock("editDialogVisible.value = false")
                        )
                    ),
                    emptyLineCode,
                    CodeBlock(
                        buildScopeString(indent) {
                            scope("watch(() => editDialogVisible.value, (newVal) => {", "})") {
                                scope("if (!newVal) {", "}") {
                                    line("updateInput.value = undefined")
                                }
                            }
                        }
                    )
                )

                template += emptyLineElement
                template += dialog(
                    modelValue = "editDialogVisible",
                    content = listOf(
                        TagElement(editForm.name) {
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
                }
            }

            if (pageCanDelete) {
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
                            scopeEndNoLine("try {", "}") {
                                line("await delete${entity.name}(ids)")
                                line("await ${queryFn}()")
                                line()
                                line("sendMessage('删除${entity.comment}成功', 'success')")
                            }
                            scope(" catch (e) {", "}") {
                                line("sendMessage('删除${entity.comment}失败', 'error', e)")
                            }
                        },
                    )
                )
            }
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