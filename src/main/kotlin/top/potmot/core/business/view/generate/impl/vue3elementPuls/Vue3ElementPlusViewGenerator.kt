package top.potmot.core.business.view.generate.impl.vue3elementPuls

import top.potmot.core.business.utils.PropertyFormType
import top.potmot.core.business.utils.PropertyQueryType
import top.potmot.core.business.utils.associationProperties
import top.potmot.core.business.utils.componentNames
import top.potmot.core.business.utils.constants
import top.potmot.core.business.utils.dtoNames
import top.potmot.core.business.utils.enums
import top.potmot.core.business.utils.formType
import top.potmot.core.business.utils.queryType
import top.potmot.core.business.utils.serviceName
import top.potmot.core.business.utils.typeStrToTypeScriptDefault
import top.potmot.core.business.utils.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.ViewGenerator
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.error.GenerateException
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.toSingular
import top.potmot.utils.string.trimBlankLine

object Vue3ElementPlusViewGenerator : ViewGenerator() {
    override fun getFileSuffix() = "vue"

    override fun stringifyEnumSelect(enum: GenEnumGenerateView): String {
        val (_, dir, _, view) = enum.componentNames

        return """
<script setup lang="ts">
import {${enum.constants}} from "@/api/__generated/model/enums"
import type {${enum.name}} from "@/api/__generated/model/enums"
import $view from "@/components/$dir/$view.vue"

const data = defineModel<${enum.name}>({
    required: true
})
</script>

<template>
    <el-select
        placeholder="请选择${enum.comment}"
        v-model="data"
        clearable>
        <el-option
            v-for="value in ${enum.constants}" 
            :value="value">
            <$view :value="value"/>
        </el-option>
    </el-select>
</template>
""".trim()
    }

    override fun stringifyEnumView(enum: GenEnumGenerateView): String {
        val itemTexts = buildString {
            enum.items.forEachIndexed { index, it ->
                if (index == 0) {
                    append("""    <el-text v-if="value === '${it.name}'">${it.comment}</el-text>""")
                } else {
                    append("\n" + """    <el-text v-else-if="value === '${it.name}'">${it.comment}</el-text>""")
                }
            }
        }

        return """
<script setup lang="ts">
import type {${enum.name}} from "@/api/__generated/model/enums"

defineProps<{
    value: ${enum.name}
}>()
</script>

<template>
$itemTexts
</template>
        """.trim()
    }

    @Throws(GenerateException::class)
    override fun stringifyTable(entity: GenEntityBusinessView): String {
        val listView = entity.dtoNames.listView

        val enums = entity.enums
        val enumImports = enums.joinToString("\n") {
            val (_, dir, _, view) = it.componentNames
            """import $view from "@/components/$dir/$view.vue""""
        }

        val idProperty =
            if (entity.idProperties.size != 1)
                throw GenerateException.idPropertyNotFound("entityName: ${entity.name}")
            else
                entity.idProperties[0]

        val idName = idProperty.name

        val propertyColumns = buildString {
            entity.properties.filter { !it.idProperty && it.associationType == null }.forEach {
                appendBlock(
                    if (it.enum != null) {
                        """
        <el-table-column label="${it.comment}" prop="${it.name}">
            <template #default="{row}">
                <${it.enum.componentNames.view} :value="row.${it.name}"/>
            </template>
        </el-table-column>
                         """
                    } else {
                        """
        <el-table-column label="${it.comment}" prop="${it.name}"/>
                        """
                    }.trimBlankLine()
                )
            }
        }

        return """
<script setup lang="ts">
import type {${listView}} from "@/api/__generated/model/static"
$enumImports

withDefaults(
    defineProps<{
        rows: Array<$listView>,
        idColumn: boolean,
        indexColumn: boolean,
        multiSelect: boolean,
    }>(),
    {
        idColumn: false,
        indexColumn: true,
        multiSelect: true,
    }
)

const slots = defineSlots<{
	operations(props: {row: ${listView}, index: number}): any,
}>()

const emits = defineEmits<{
    (event: "changeSelection", selection: Array<${listView}>): void,
}>()

const handleChangeSelection = (selection: Array<${listView}>) => {
    emits("changeSelection", selection)
}
</script>

<template>
    <el-table 
        :data="rows" border stripe row-key="$idName"
        @selection-change="handleChangeSelection">
        <el-table-column v-if="idColumn" label="${idProperty.comment}" prop="${idProperty.name}"/>
        <el-table-column v-if="indexColumn" type="index"/>
        <el-table-column v-if="multiSelect" type="selection"/>
$propertyColumns        
        <el-table-column label="操作" width="330px">
            <template #default="scope">
                <slot name="operations" :row="scope.row as $listView" :index="scope.${'$'}index" />
            </template>
        </el-table-column>
    </el-table>
</template>
        """.trim()
    }

    private fun GenEntityBusinessView.enumSelectImports() = enums.joinToString("\n") {
        val (_, dir, select) = it.componentNames
        """import $select from "@/components/$dir/$select.vue""""
    }

    private fun GenEntityBusinessView.queryFormItems(spec: String = "spec") =
        properties.filter { !it.idProperty && it.associationType == null }.map {
            val vModel = """v-model="${spec}.${it.name}""""

            it to when (it.queryType) {
                PropertyQueryType.ENUM_SELECT ->
                    """
<${it.enum!!.componentNames.select}
    $vModel
    clearable
    @change="emits('query')"
/>
"""

                PropertyQueryType.NUMBER_RANGE ->
                    """
<el-input-number
    v-model="${spec}.min${it.name.replaceFirstChar { c -> c.uppercaseChar() }}"
    placeholder="请输入最小${it.comment}"
    @change="emits('query')"
/>
<el-input-number
    v-model="${spec}.max${it.name.replaceFirstChar { c -> c.uppercaseChar() }}"
    placeholder="请输入最大${it.comment}"
    @change="emits('query')"
/>
"""

                PropertyQueryType.TIME_RANGE ->
                    """
<el-time-picker
    $vModel
    is-range
    start-placeholder="初始${it.comment}"
    end-placeholder="结束${it.comment}"
    clearable
    @change="emits('query')"
/>
"""

                PropertyQueryType.DATE_RANGE ->
                    """
<el-date-picker
    $vModel
    type="daterange"
    start-placeholder="初始${it.comment}"
    end-placeholder="结束${it.comment}"
    unlink-panels
    clearable
    @change="emits('query')"
/>
"""

                PropertyQueryType.DATETIME_RANGE ->
                    """
<el-date-picker
    $vModel
    type="datetimerange"
    start-placeholder="初始${it.comment}"
    end-placeholder="结束${it.comment}"
    unlink-panels
    clearable
    @change="emits('query')"
/>
"""

                else ->
                    when (it.formType) {
                        PropertyFormType.SWITCH ->
                            """
<el-select 
    $vModel
    placeholder="请选择${it.comment}"
    clearable
    @change="emits('query')">
  <el-option :value="true" label="是"/>
  <el-option :value="false" label="否"/>
</el-select>
"""

                        else ->
                            """
<el-input
    $vModel
    placeholder="请输入${it.comment}"
    clearable
    @change="emits('query')"
/>
"""
                    }

            }.trimBlankLine()
        }.joinToString("\n") { (property, component) ->
            buildString {
                appendLine("""        <el-col :span="8">""")
                appendLine("""            <el-form-item label="${property.comment}">""")
                appendBlock(component) { "                $it" }
                appendLine("            </el-form-item>")
                appendLine("        </el-col>")
            }
        }

    override fun stringifyQueryForm(entity: GenEntityBusinessView): String {
        val spec = entity.dtoNames.spec

        return """
<script setup lang="ts">
import {Search} from "@element-plus/icons-vue"
import type {${spec}} from "@/api/__generated/model/static"
${entity.enumSelectImports()}

const spec = defineModel<${spec}>({
    required: true
})

const emits = defineEmits<{
    (event: "query"): void,
}>()
</script>

<template>
    <el-form>
        <el-row :gutter="20">
${entity.queryFormItems()}
            <el-button :icon="Search" type="primary" @click="emits('query')"/>
        </el-row>
    </el-form>
</template>
        """.trim()
    }

    @Throws(GenerateException::class)
    override fun stringifyDefaultAddInput(entity: GenEntityBusinessView): String {
        val insertInput = entity.dtoNames.insertInput

        val basePropertyWithDefault =
            entity.properties.filter { it.associationType == null && it.id != entity.id }.joinToString("\n") {
                if (it.listType) {
                    "    ${it.name}: [],"
                } else {
                    "    ${it.name}: ${typeStrToTypeScriptDefault(it.type, it.typeNotNull)},"
                }
            }

        val associationPropertyWithDefault =
            entity.associationProperties.filter { it.id != entity.id }.joinToString("\n") {
                if (it.idView) {
                    if (it.listType) {
                        "    ${it.name}: [],"
                    } else {
                        "    ${it.name}: ${typeStrToTypeScriptDefault(it.type, it.typeNotNull)},"
                    }
                } else {
                    if (it.listType) {
                        "    ${it.name.toSingular()}Ids: [],"
                    } else {
                        val typeIdProperties = it.typeEntity?.idProperties
                            ?: throw GenerateException.entityNotFound("entityName: ${it.typeEntity?.name}")

                        val typeIdProperty =
                            if (typeIdProperties.size != 1)
                                throw GenerateException.idPropertyNotFound("entityName: ${it.typeEntity.name}")
                            else
                                typeIdProperties[0]
                        "    ${it.name}Id: ${typeStrToTypeScriptDefault(typeIdProperty.type, typeIdProperty.typeNotNull)},"
                    }
                }
            }

        return """
import type {${insertInput}} from "@/api/__generated/model/static"

export default <${insertInput}> {
$basePropertyWithDefault
$associationPropertyWithDefault
} 
        """.trimBlankLine()
    }

    private fun GenEntityBusinessView.formItems(formData: String = "formData") =
        properties.filter { !it.idProperty && it.associationType == null && it.entityId == id }.map {
            val vModel = """v-model="${formData}.${it.name}""""

            it to when (it.formType) {
                PropertyFormType.ENUM ->
                    "<${it.enum!!.componentNames.select} $vModel/>"

                PropertyFormType.SWITCH ->
                    "<el-switch $vModel/>"

                PropertyFormType.NUMBER ->
                    """
<el-input-number
    $vModel
    placeholder="请输入${it.comment}"
/>
"""

                PropertyFormType.TIME ->
                    """
<el-time-picker
    $vModel
    placeholder="请选择${it.comment}"
/>
"""

                PropertyFormType.DATE ->
                    """
<el-date-picker
    $vModel
    placeholder="请选择${it.comment}"
/>
"""

                PropertyFormType.DATETIME ->
                    """
<el-date-picker
    $vModel
    placeholder="请选择${it.comment}"
/>
"""

                else ->
                    """
<el-input
    $vModel
    placeholder="请输入${it.comment}"
/>
"""
            }.trimBlankLine()
        }.joinToString("\n") { (property, component) ->
            buildString {
                appendLine("""        <el-form-item label="${property.comment}">""")
                appendBlock(component) { "            $it" }
                appendLine("        </el-form-item>")
            }
        }

    override fun stringifyAddForm(entity: GenEntityBusinessView): String {
        val dir = entity.componentNames.dir
        val defaultAddInput = entity.defaultAddInput()
        val (_, _, _, insertInput) = entity.dtoNames

        return """
<script setup lang="ts">
import {ref} from "vue"
import type {${insertInput}} from "@/api/__generated/model/static"
import defaultInput from "@/components/${dir}/${defaultAddInput}"
import {cloneDeep} from "lodash"
${entity.enumSelectImports()}

const formData = ref<${insertInput}>(cloneDeep(defaultInput))

const emits = defineEmits<{
    (event: "submit", insertInput: ${insertInput}): void,
    (event: "cancel"): void,
}>()
</script>

<template>
    <el-form :model="formData">
${entity.formItems()}
        <div style="text-align: right;">
            <el-button type="info" @click="emits('cancel')" v-text="'取消'"/>
            <el-button type="primary" @click="emits('submit', formData)" v-text="'提交'"/>
        </div>
    </el-form>
</template>
        """.trim()
    }

    override fun stringifyEditForm(entity: GenEntityBusinessView): String {
        val (_, _, _, _, updateInput) = entity.dtoNames

        return """
<script setup lang="ts">
import {ref, watch} from "vue"
import type {${updateInput}} from "@/api/__generated/model/static"
import {cloneDeep} from "lodash"
${entity.enumSelectImports()}

const props = defineProps<{
    data: $updateInput
}>()

const formData = ref<${updateInput}>(cloneDeep(props.data))

watch(() => props.data, (data) => {
    formData.value = data
})

const emits = defineEmits<{
    (event: "submit", updateInput: ${updateInput}): void,
    (event: "cancel"): void,
}>()
</script>

<template>
    <el-form :model="formData">
${entity.formItems()}
        <div style="text-align: right;">
            <el-button type="info" @click="emits('cancel')" v-text="'取消'"/>
            <el-button type="primary" @click="emits('submit', formData)" v-text="'提交'"/>
        </div>
    </el-form>
</template>
        """.trim()
    }

    @Throws(GenerateException::class)
    override fun stringifyPage(entity: GenEntityBusinessView): String {
        val (_, dir, table, addForm, editForm, queryForm) = entity.componentNames
        val (_, listView, _, insertInput, updateInput, spec) = entity.dtoNames

        val serviceName = entity.serviceName.replaceFirstChar { it.lowercase() }

        val idProperty =
            if (entity.idProperties.size != 1)
                throw GenerateException.idPropertyNotFound("entityName: ${entity.name}")
            else
                entity.idProperties[0]
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)

        return """
<script setup lang="ts">
import {ref, onMounted} from "vue"
import {Plus, EditPen, Delete} from "@element-plus/icons-vue"
import type {Page, PageQuery, ${spec}, ${listView}, ${insertInput}, ${updateInput}} from "@/api/__generated/model/static"
import {api} from "@/api"
import {sendMessage} from "@/utils/message"
import {deleteConfirm} from "@/utils/confirm"
import {useLoading} from "@/utils/loading"
import {useLegalPage} from "@/utils/legalPage"
import $table from "@/components/$dir/$table.vue"
import $addForm from "@/components/$dir/$addForm.vue"
import $editForm from "@/components/$dir/$editForm.vue"
import $queryForm from "@/components/$dir/$queryForm.vue"

const {isLoading, withLoading} = useLoading()

const pageData = ref<Page<${listView}>>()

// 查询
const queryInfo = ref<PageQuery<${spec}>>({
    spec: {},
    pageIndex: 1,
    pageSize: 5
})

const {queryPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.${serviceName}.page)
)

onMounted(async () => {
    await queryPage()
})

const get${entity.name} = withLoading((id: $idType) => api.${serviceName}.get({id}))

const add${entity.name} = withLoading((body: $insertInput) => api.${serviceName}.insert({body}))

const edit${entity.name} = withLoading((body: $updateInput) => api.${serviceName}.update({body}))

const delete${entity.name} = withLoading((ids: Array<$idType>) => api.${serviceName}.delete({ids}))

// 多选
const selection = ref<${listView}[]>([])

const changeSelection = (item: ${listView}[]) => {
    selection.value = item
}

// 新增
const addDialogVisible = ref(false)

const startAdd = () => {
    addDialogVisible.value = true
}

const submitAdd = async (insertInput: ${insertInput}) => {
    try {
        await add${entity.name}(insertInput)
        await queryPage()
        addDialogVisible.value = false
        
        sendMessage('新增${entity.comment}成功', 'success')
    } catch (e) {
        sendMessage('新增${entity.comment}失败', 'error', e)
    }
}

const cancelAdd = () => {
    addDialogVisible.value = false
}

// 修改
const editDialogVisible = ref(false)

const updateInput = ref<${updateInput} | undefined>(undefined)

const startEdit = async (id: ${idType}) => {
    updateInput.value = await get${entity.name}(id)
    if (updateInput.value === undefined) {
        sendMessage('编辑的${entity.comment}不存在', 'error')
        return
    }
    editDialogVisible.value = true
}

const submitEdit = async (updateInput: ${updateInput}) => {
    try {
        await edit${entity.name}(updateInput)
        await queryPage()
        editDialogVisible.value = false
        
        sendMessage('编辑${entity.comment}成功', 'success')
    } catch (e) {
        sendMessage('编辑${entity.comment}失败', 'error', e)
    }
}

const cancelEdit = () => {
    editDialogVisible.value = false
    updateInput.value = undefined
}

// 删除
const handleDelete = async (ids: ${idType}[]) => {
    const result = await deleteConfirm('该${entity.comment}')
    if (!result) return

    try {
        await delete${entity.name}(ids)
        await queryPage()
        
        sendMessage('删除${entity.comment}成功', 'success')
    } catch (e) {
        sendMessage('删除${entity.comment}失败', 'error', e)
    }
}
</script>

<template>
    <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>${entity.comment}管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card v-loading="isLoading">
        <$queryForm v-model="queryInfo.spec" @query="queryPage"/>
        
        <div>
            <el-button type="primary" :icon="Plus" @click="startAdd" v-text="'新增'"/>
            <el-button type="danger" :icon="Delete" @click="handleDelete(selection.map(it => it.${idName}))" v-text="'批量删除'"/>
        </div>

        <template v-if="pageData">
            <$table :rows="pageData.rows" @changeSelection="changeSelection">
                <template #operations="{row}">
                    <el-button type="warning" :icon="EditPen" @click="startEdit(row.${idName})" v-text="'编辑'"/>
                    <el-button type="danger" :icon="Delete" @click="handleDelete([row.${idName}])" v-text="'删除'"/>                
                </template>
            </$table>

            <el-pagination
                v-model:current-page="queryInfo.pageIndex"
                v-model:page-size="queryInfo.pageSize"
                :page-sizes="[5, 10, 20]"
                layout="total, sizes, prev, pager, next, jumper"
                :total="pageData.totalRowCount"
            />
        </template>
    </el-card>
    
    <el-dialog v-model="addDialogVisible">
        <$addForm @submit="submitAdd" @cancel="cancelAdd"/>
    </el-dialog>
    
    <el-dialog v-model="editDialogVisible">
        <$editForm v-if="updateInput !== undefined" :data="updateInput" @submit="submitEdit" @cancel="cancelEdit"/>
    </el-dialog>
</template>
    """.trim()
    }

    override fun stringifySingleSelect(entity: GenEntityBusinessView): String {
        val (_, dir, table, _, _, queryForm) = entity.componentNames
        val (_, listView, _, _, _, spec) = entity.dtoNames

        val serviceName = entity.serviceName.replaceFirstChar { it.lowercase() }

        return """
<script setup lang="ts">
import {ref, onMounted} from "vue"
import {Check} from "@element-plus/icons-vue"
import type {Page, PageQuery, ${spec}, ${listView}} from "@/api/__generated/model/static"
import {api} from "@/api"
import {sendMessage} from "@/utils/message"
import {useLoading} from "@/utils/loading"
import {useLegalPage} from "@/utils/legalPage"
import $table from "@/components/$dir/$table.vue"

const {isLoading, withLoading} = useLoading()

const pageData = ref<Page<${listView}>>()

const queryInfo = ref<PageQuery<${spec}>>({
    spec: {},
    pageIndex: 1,
    pageSize: 5
})

const {queryPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.${serviceName}.page)
)

onMounted(async () => {
    await queryPage()
})

const emits = defineEmits<{
    (event: "select", item: $listView): void,
}>()

const handleSelect = (item: $listView) => {
    emits("select", item)
}
</script>

<template>
    <el-form>
        <$queryForm :v-model="queryInfo.spec" @query="queryPage"/>
    
        <$table :rows="pageData.rows" :multi-select="false">
            <template #operations="{row}">
                <el-button type="warning" :icon="EditPen" @click="handleSelect(row)" v-text="'选择'"/>
            </template>
        </$table>
        
        <el-pagination
            v-model:current-page="queryInfo.pageIndex"
            v-model:page-size="queryInfo.pageSize"
            :page-sizes="[5, 10, 20]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="pageData.totalRowCount"
        />
    </el-form>
</template>
""".trim()
    }

    @Throws(GenerateException::class)
    override fun stringifyMultiSelect(entity: GenEntityBusinessView): String {
        val (_, dir, table, _, _, queryForm) = entity.componentNames
        val (_, listView, _, _, _, spec) = entity.dtoNames

        val serviceName = entity.serviceName.replaceFirstChar { it.lowercase() }

        val idProperty =
            if (entity.idProperties.size != 1)
                throw GenerateException.idPropertyNotFound("entityName: ${entity.name}")
            else
                entity.idProperties[0]
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)

        return """
<script setup lang="ts">
import {ref, onMounted} from "vue"
import {Check} from "@element-plus/icons-vue"
import type {Page, PageQuery, ${spec}, ${listView}} from "@/api/__generated/model/static"
import {api} from "@/api"
import {sendMessage} from "@/utils/message"
import {useLoading} from "@/utils/loading"
import {useLegalPage} from "@/utils/legalPage"
import $table from "@/components/$dir/$table.vue"

const {isLoading, withLoading} = useLoading()

const pageData = ref<Page<${listView}>>()

const queryInfo = ref<PageQuery<${spec}>>({
    spec: {},
    pageIndex: 1,
    pageSize: 5
})

const {queryPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.${serviceName}.page)
)

onMounted(async () => {
    await queryPage()
})

const emits = defineEmits<{
    (event: "submit", selection: Array<${idType}>): void,
    (event: "cancel"): void,
}>()

const selectMap = ref<Map<${idType}, ${listView}>>(new Map)

const handleSelect = (item: $listView) => {
    selectIds.put(item.$idName, item)
}

const handleUnSelect = (item: $listView) => {
    selectIds.remove(item.$idName)
}
</script>

<template>
    <el-form>
        <$queryForm :v-model="queryInfo.spec" @query="queryPage"/>
    
        <$table :rows="pageData.rows" :multi-select="false">
            <template #operations="{row}">
                <el-button v-if="!selectIds.has(row.${idName})" type="warning" :icon="EditPen" @click="handleSelect(row)" v-text="'选择'"/>
                <el-button v-else type="warning" :icon="EditPen" @click="handleSelect(row)" v-text="'取消'"/>
            </template>
        </$table>
        
        <el-pagination
            v-model:current-page="queryInfo.pageIndex"
            v-model:page-size="queryInfo.pageSize"
            :page-sizes="[5, 10, 20]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="pageData.totalRowCount"
        />
        
        <div style="text-align: right;">
            <el-button type="info" @click="emits('cancel')" v-text="'取消'"/>
            <el-button type="primary" @click="emits('submit', Arrays.from(selectMap.values()))" v-text="'提交'"/>
        </div>
    </el-form>
</template>
""".trim()
    }
}