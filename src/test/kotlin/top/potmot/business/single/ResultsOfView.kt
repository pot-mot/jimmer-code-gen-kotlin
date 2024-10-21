package top.potmot.business.single

const val vue3ElementPlusResult = """
[(components/conditionMatch/ConditionMatchTable.vue, <script setup lang="ts">
import MatchStatusView from "@/components/matchStatus/MatchStatusView.vue"

withDefaults(
    defineProps<{
        rows: Array<ConditionMatchListView>,
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
	default(props: {row: ConditionMatchListView, index: number}): any,
}>()

const emits = defineEmits<{
    (event: "changeSelection"): void,
}>()
</script>

<template>
    <el-table 
    :data="rows" border stripe row-key="id"
    @selection-change="emits('changeSelection')">
        <el-table-column v-if="idColumn" label="ID" prop="id"/>
        <el-table-column v-if="indexColumn" type="index"/>
        <el-table-column v-if="multiSelect" type="selection"/>
        <el-table-column label="匹配状态" prop="status">
            <template #default="{row}">
                <MatchStatusView :value="row.status"/>
            </template>
        </el-table-column>
        <el-table-column label="匹配日期" prop="date"/>
        <el-table-column label="结果描述" prop="description"/>
        
        <el-table-column label="操作" width="330px">
            <template #default="scope">
                <slot name="operations" row="scope.row" :index="scope.${'$'}index" />
            </template>
        </el-table-column>
    </el-table>
</template>), (components/conditionMatch/DefaultConditionMatchAddInput.ts, import type {ConditionMatchInsertInput} from "@/api/__generated/model/static"

export default <ConditionMatchInsertInput> {
    id: 0,
    status: "",
    date: undefined,
    description: "",
    userId: 0,
    conditionId: 0,
} ), (components/conditionMatch/ConditionMatchAddForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type {ConditionMatchInsertInput} from "@/api/__generated/model/static"
import defaultInput from "@/components/conditionMatch/DefaultConditionMatchAddInput.ts"
import MatchStatusSelect from "@/components/matchStatus/MatchStatusSelect.vue"

const formData = ref<ConditionMatchInsertInput>(cloneDeep(defaultInput))

const emits = defineEmits<{
    (event: "submit", insertInput: ConditionMatchInsertInput): void,
    (event: "cancel"): void,
}>()
</script>

<template>
    <el-form :model="formData">
        <el-form-item label="匹配状态">
            <MatchStatusSelect v-model="formData.status"/>
        </el-form-item>

        <el-form-item label="匹配日期">
            <el-date-picker
                v-model="formData.date"
                placeholder="请选择匹配日期"
            />
        </el-form-item>

        <el-form-item label="结果描述">
            <el-input
                v-model="formData.description"
                placeholder="请输入结果描述"
            />
        </el-form-item>

        <div style="text-align: right;">
            <el-button type="info" @click="emits('cancel')" v-text="'取消'"/>
            <el-button type="primary" @click="emits('submit', formData)" v-text="'提交'"/>
        </div>
    </el-form>
</template>), (components/conditionMatch/ConditionMatchEditForm.vue, <script setup lang="ts">
import type {ConditionMatchUpdateInput} from "@/api/__generated/model/static"
import MatchStatusSelect from "@/components/matchStatus/MatchStatusSelect.vue"

defineProps<{
    formData: ConditionMatchUpdateInput
}>()

const emits = defineEmits<{
    (event: "submit", updateInput: ConditionMatchUpdateInput): void,
    (event: "cancel"): void,
}>()
</script>

<template>
    <el-form :model="formData">
        <el-form-item label="匹配状态">
            <MatchStatusSelect v-model="formData.status"/>
        </el-form-item>

        <el-form-item label="匹配日期">
            <el-date-picker
                v-model="formData.date"
                placeholder="请选择匹配日期"
            />
        </el-form-item>

        <el-form-item label="结果描述">
            <el-input
                v-model="formData.description"
                placeholder="请输入结果描述"
            />
        </el-form-item>

        <div style="text-align: right;">
            <el-button type="info" @click="emits('cancel')" v-text="'取消'"/>
            <el-button type="primary" @click="emits('submit', formData)" v-text="'提交'"/>
        </div>
    </el-form>
</template>), (components/conditionMatch/ConditionMatchEditForm.vue, <script setup lang="ts">
import type {ConditionMatchUpdateInput} from "@/api/__generated/model/static"
import MatchStatusSelect from "@/components/matchStatus/MatchStatusSelect.vue"

defineProps<{
    formData: ConditionMatchUpdateInput
}>()

const emits = defineEmits<{
    (event: "submit", updateInput: ConditionMatchUpdateInput): void,
    (event: "cancel"): void,
}>()
</script>

<template>
    <el-form :model="formData">
        <el-form-item label="匹配状态">
            <MatchStatusSelect v-model="formData.status"/>
        </el-form-item>

        <el-form-item label="匹配日期">
            <el-date-picker
                v-model="formData.date"
                placeholder="请选择匹配日期"
            />
        </el-form-item>

        <el-form-item label="结果描述">
            <el-input
                v-model="formData.description"
                placeholder="请输入结果描述"
            />
        </el-form-item>

        <div style="text-align: right;">
            <el-button type="info" @click="emits('cancel')" v-text="'取消'"/>
            <el-button type="primary" @click="emits('submit', formData)" v-text="'提交'"/>
        </div>
    </el-form>
</template>), (components/conditionMatch/ConditionMatchQueryForm.vue, <script setup lang="ts">
import {Search} from "@element-plus/icons-vue"
import type {ConditionMatchSpec} from "@/api/__generated/model/static"
import MatchStatusSelect from "@/components/matchStatus/MatchStatusSelect.vue"

const spec = defineModel<ConditionMatchSpec>({
    required: true
})

const emits = defineEmits<{
    (event: "query"): void,
}>()
</script>

<template>
    <el-form>
        <el-row :gutter="20">
        <el-col :span="8">
            <el-form-item label="匹配状态">
                <MatchStatusSelect
                    v-model="spec.status"
                    clearable
                    @change="emits('query')"
                />
            </el-form-item>
        </el-col>

        <el-col :span="8">
            <el-form-item label="匹配日期">
                <el-date-picker
                    v-model="spec.date"
                    type="datetimerange"
                    start-placeholder="初始匹配日期"
                    end-placeholder="结束匹配日期"
                    unlink-panels
                    clearable
                    @change="emits('query')"
                />
            </el-form-item>
        </el-col>

        <el-col :span="8">
            <el-form-item label="结果描述">
                <el-input
                    v-model="spec.description"
                    placeholder="请输入结果描述"
                    clearable
                    @change="emits('query')"
                />
            </el-form-item>
        </el-col>

            <el-button :icon="Search" type="primary" @click="emits('query')"/>
        </el-row>
    </el-form>
</template>), (pages/conditionMatch/ConditionMatchPage.vue, <script setup lang="ts">
import {ref, onMounted} from "vue"
import {Check, Close, Plus, EditPen, Delete, Search} from "@element-plus/icons-vue"
import type {Page, PageQuery, ConditionMatchSpec, ConditionMatchListView, ConditionMatchInsertInput, ConditionMatchUpdateInput} from "@/api/__generated/model/static"
import {api} from "@/api"
import {sendMessage} from "@/utils/message.ts"
import {deleteConfirm} from "@/utils/confirm.ts"
import {useLoading} from "@/utils/loading.ts"
import {useLegalPage} from "@/utils/legalPage.ts"
import ConditionMatchTable from "@/components/conditionMatch/ConditionMatchTable.vue"
import ConditionMatchAddForm from "@/components/conditionMatch/ConditionMatchAddForm.vue"
import ConditionMatchEditForm from "@/components/conditionMatch/ConditionMatchEditForm.vue"
import ConditionMatchQueryForm from "@/components/conditionMatch/ConditionMatchQueryForm.vue"

const {isLoading, withLoading} = useLoading()

const pageData = ref<Page<ConditionMatchListView>>()

// 查询
const queryInfo = ref<PageQuery<ConditionMatchSpec>>({
    spec: {},
    pageIndex: 1,
    pageSize: 5
})

const {queryPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.conditionMatchService.page)
)

onMounted(async () => {
    await queryPage()
})

const getConditionMatch = withLoading(api.conditionMatchService.get)

const addConditionMatch = withLoading(api.conditionMatchService.insert)

const editConditionMatch = withLoading(api.conditionMatchService.update)

const deleteConditionMatch = withLoading(api.conditionMatchService.delete)

// 多选
const selection = ref<ConditionMatchListView[]>([])

const handleSelectionChange = (item: ConditionMatchListView[]) => {
    selection.value = item
}

// 新增
const addDialogVisible = ref(false)

const startAdd = () => {
    addDialogVisible.value = true
}

const submitAdd = async (insertInput: ConditionMatchInsertInput) => {
    try {
        await addConditionMatch(insertInput)
        await queryPage()
        addDialogVisible.value = false
        
        sendMessage('新增条件匹配成功', 'success')
    } catch (e) {
        sendMessage('新增条件匹配失败', 'error', e)
    }
}

const cancelAdd = () => {
    addDialogVisible.value = false
    addInput.value = undefined
}

// 修改
const editDialogVisible = ref(false)

const updateInput = ref<ConditionMatchUpdateInput | undefined>(undefined)

const startEdit = async (id: number) => {
    updateInput.value = await getConditionMatch(id)
    if (updateInput.value === undefined) {
        sendMessage('编辑的条件匹配不存在', 'error')
        return
    }
    editDialogVisible.value = true
}

const submitEdit = async (updateInput: ConditionMatchUpdateInput) => {
    try {
        await updateConditionMatch(updateInput)
        await queryPage()
        editDialogVisible.value = false
        
        sendMessage('编辑条件匹配成功', 'success')
    } catch (e) {
        sendMessage('编辑条件匹配失败', 'error', e)
    }
}

const cancelEdit = () => {
    editDialogVisible.value = false
    updateInput.value = undefined
}

// 删除
const handleDelete = async (ids: number[]) => {
    const result = await deleteConfirm('该条件匹配')
    if (!result) return

    try {
        await deleteConditionMatch(ids)
        await queryPage()
        
        sendMessage('删除条件匹配成功', 'success')
    } catch (e) {
        sendMessage('删除条件匹配失败', 'error', e)
    }
}
</script>

<template>
    <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>条件匹配管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card v-loading="isLoading">
        <ConditionMatchQueryForm :v-model="queryInfo.spec" @query="queryPage"/>
        
        <div>
            <el-button type="primary" :icon="Plus" @click="startAdd" v-text="'新增'">
            <el-button type="danger" :icon="Delete" @click="handleDelete(selection.map(it => it.id))" v-text="'批量删除'">
        </div>

        <template v-if="pageData">
            <ConditionMatchTable :rows="pageData.rows" @changeSelection="changeSelection">
                <template #operations="{row}">
                    <el-button type="warning" :icon="EditPen" @click="startEdit(row.id)" v-text="'编辑'"/>
                    <el-button type="danger" :icon="Delete" @click="removePost([row.id])" v-text="'删除'"/>                
                </template>
            </ConditionMatchTable>

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
        <ConditionMatchAddForm @submit="submitAdd" @cancel="cancelAdd"/>
    </el-dialog>
    
    <el-dialog v-model="editDialogVisible">
        <ConditionMatchEditForm v-if="updateInput !== undefined" :data="updateInput" @submit="submitEdit" @cancel="cancelEdit"/>
    </el-dialog>
</template>), (components/conditionMatch/ConditionMatchSingleSelect.vue, <script setup lang="ts">
import {ref, onMounted} from "vue"
import {Check} from "@element-plus/icons-vue"
import type {Page, PageQuery, ConditionMatchSpec, ConditionMatchListView} from "@/api/__generated/model/static"
import {api} from "@/api"
import {sendMessage} from "@/utils/message.ts"
import {useLoading} from "@/utils/loading.ts"
import {useLegalPage} from "@/utils/legalPage.ts"
import ConditionMatchTable from "@/components/conditionMatch/ConditionMatchTable.vue"

const {isLoading, withLoading} = useLoading()

const pageData = ref<Page<ConditionMatchListView>>()

const queryInfo = ref<PageQuery<ConditionMatchSpec>>({
    spec: {},
    pageIndex: 1,
    pageSize: 5
})

const {queryPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.conditionMatchService.page)
)

onMounted(async () => {
    await queryPage()
})

const emits = defineEmits<{
    (event: "select", item: ConditionMatchListView): void,
}>()

const handleSelect = (item: ConditionMatchListView) => {
    emits("select", item)
}
</script>

<template>
    <el-form>
        <ConditionMatchQueryForm :v-model="queryInfo.spec" @query="queryPage"/>
    
        <ConditionMatchTable :rows="pageData.rows" :multi-select="false">
            <template #operations="{row}">
                <el-button type="warning" :icon="EditPen" @click="handleSelect(row)" v-text="'选择'"/>
            </template>
        </ConditionMatchTable>
        
        <el-pagination
            v-model:current-page="queryInfo.pageIndex"
            v-model:page-size="queryInfo.pageSize"
            :page-sizes="[5, 10, 20]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="pageData.totalRowCount"
        />
    </el-form>
</template>), (components/conditionMatch/ConditionMatchMultiSelect.vue, <script setup lang="ts">
import {ref, onMounted} from "vue"
import {Check} from "@element-plus/icons-vue"
import type {Page, PageQuery, ConditionMatchSpec, ConditionMatchListView} from "@/api/__generated/model/static"
import {api} from "@/api"
import {sendMessage} from "@/utils/message.ts"
import {useLoading} from "@/utils/loading.ts"
import {useLegalPage} from "@/utils/legalPage.ts"
import ConditionMatchTable from "@/components/conditionMatch/ConditionMatchTable.vue"

const {isLoading, withLoading} = useLoading()

const pageData = ref<Page<ConditionMatchListView>>()

const queryInfo = ref<PageQuery<ConditionMatchSpec>>({
    spec: {},
    pageIndex: 1,
    pageSize: 5
})

const {queryPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.conditionMatchService.page)
)

onMounted(async () => {
    await queryPage()
})

const emits = defineEmits<{
    (event: "submit", selection: Array<number>): void,
    (event: "cancel"): void,
}>()

const selectMap = ref<Map<number, ConditionMatchListView>>(new Map)

const handleSelect = (item: ConditionMatchListView) => {
    selectIds.put(item.id, item)
}

const handleUnSelect = (item: ConditionMatchListView) => {
    selectIds.remove(item.id)
}
</script>

<template>
    <el-form>
        <ConditionMatchQueryForm :v-model="queryInfo.spec" @query="queryPage"/>
    
        <ConditionMatchTable :rows="pageData.rows" :multi-select="false">
            <template #operations="{row}">
                <el-button v-if="!selectIds.has(row.id)" type="warning" :icon="EditPen" @click="handleSelect(row)" v-text="'选择'"/>
                <el-button v-else type="warning" :icon="EditPen" @click="handleSelect(row)" v-text="'取消'"/>
            </template>
        </ConditionMatchTable>
        
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
</template>)]
"""