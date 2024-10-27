package top.potmot.business.single

const val index = """${'$'}index"""

const val vue3ElementPlusResult = """
[(components/conditionMatch/ConditionMatchTable.vue, <script setup lang="ts">
import type {ConditionMatchListView} from "@/api/__generated/model/static"
import MatchStatusView from "@/components/matchStatus/MatchStatusView.vue"

withDefaults(
    defineProps<{
        rows: Array<ConditionMatchListView>,
        idColumn?: boolean | undefined,
        indexColumn?: boolean | undefined,
        multiSelect?: boolean | undefined,
    }>(),
    {
        idColumn: false,
        indexColumn: true,
        multiSelect: true,
    }
)

const slots = defineSlots<{
	operations(props: {row: ConditionMatchListView, index: number}): any,
}>()

const emits = defineEmits<{
    (event: "changeSelection", selection: Array<ConditionMatchListView>): void,
}>()

const handleChangeSelection = (selection: Array<ConditionMatchListView>) => {
    emits("changeSelection", selection)
}
</script>

<template>
    <el-table 
        :data="rows" border stripe row-key="id"
        @selection-change="handleChangeSelection">
        <el-table-column v-if="idColumn" label="ID" prop="id"/>
        <el-table-column v-if="indexColumn" type="index"/>
        <el-table-column v-if="multiSelect" type="selection"/>
        <el-table-column label="匹配状态" prop="status">
            <template #default="{row}">
                <MatchStatusView :value="row.status"/>
            </template>
        </el-table-column>
        <el-table-column label="匹配日期" prop="date"/>
        <el-table-column label="金额" prop="money"/>
        <el-table-column label="结果描述" prop="description"/>
        
        <el-table-column label="操作" width="330px">
            <template #default="scope">
                <slot name="operations" :row="scope.row as ConditionMatchListView" :index="scope.$index" />
            </template>
        </el-table-column>
    </el-table>
</template>), (components/conditionMatch/DefaultConditionMatchAddInput.ts, import type {ConditionMatchInsertInput} from "@/api/__generated/model/static"

export default <ConditionMatchInsertInput> {
    status: "EQ",
    date: "",
    money: 0,
    description: "",
    userId: 0,
    conditionId: 0,
}), (components/conditionMatch/ConditionMatchAddForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type { FormInstance } from "element-plus"
import {cloneDeep} from "lodash"
import type {ConditionMatchInsertInput} from "@/api/__generated/model/static"
import defaultInput from "@/components/conditionMatch/DefaultConditionMatchAddInput"
import rules from "@/rules/conditionMatch/ConditionMatchAddFormRules"
import MatchStatusSelect from "@/components/matchStatus/MatchStatusSelect.vue"

const formRef = ref<FormInstance>()

const formData = ref<ConditionMatchInsertInput>(cloneDeep(defaultInput))

const emits = defineEmits<{
    (event: "submit", insertInput: ConditionMatchInsertInput): void,
    (event: "cancel"): void,
}>()

const handleSubmit = () => {
    formRef.value?.validate(valid => {
        if (valid)
            emits('submit', formData.value)
    })
}
</script>

<template>
    <el-form ref="formRef" :rules="rules" :model="formData" inline-message>
        <el-form-item prop="status" label="匹配状态" required>
            <MatchStatusSelect v-model="formData.status"/>
        </el-form-item>

        <el-form-item prop="date" label="匹配日期" required>
            <el-date-picker
                v-model="formData.date"
                placeholder="请选择匹配日期"
                type="datetime"
            />
        </el-form-item>

        <el-form-item prop="money" label="金额" required>
            <el-input-number
                v-model.number="formData.money"
                placeholder="请输入金额"
                :precision="2" 
                :min="0"
                :max="99999"
            />
        </el-form-item>

        <el-form-item prop="description" label="结果描述" required>
            <el-input
                v-model="formData.description" maxlength="255"
                placeholder="请输入结果描述"
                clearable
            />
        </el-form-item>

        <div style="text-align: right;">
            <el-button type="info" @click="emits('cancel')" v-text="'取消'"/>
            <el-button type="primary" @click="handleSubmit" v-text="'提交'"/>
        </div>
    </el-form>
</template>), (components/conditionMatch/ConditionMatchEditForm.vue, <script setup lang="ts">
import {ref, watch} from "vue"
import type { FormInstance } from "element-plus"
import {cloneDeep} from "lodash"
import type {ConditionMatchUpdateInput} from "@/api/__generated/model/static"
import rules from "@/rules/conditionMatch/ConditionMatchEditFormRules"
import MatchStatusSelect from "@/components/matchStatus/MatchStatusSelect.vue"

const props = defineProps<{
    data: ConditionMatchUpdateInput
}>()

const formRef = ref<FormInstance>()

const formData = ref<ConditionMatchUpdateInput>(cloneDeep(props.data))

watch(() => props.data, (data) => {
    formData.value = data
})

const emits = defineEmits<{
    (event: "submit", updateInput: ConditionMatchUpdateInput): void,
    (event: "cancel"): void,
}>()

const handleSubmit = () => {
    formRef.value?.validate(valid => {
        if (valid)
            emits('submit', formData.value)    
    })
}
</script>

<template>
    <el-form ref="formRef" :rules="rules" :model="formData" inline-message>
        <el-form-item prop="status" label="匹配状态" required>
            <MatchStatusSelect v-model="formData.status"/>
        </el-form-item>

        <el-form-item prop="date" label="匹配日期" required>
            <el-date-picker
                v-model="formData.date"
                placeholder="请选择匹配日期"
                type="datetime"
            />
        </el-form-item>

        <el-form-item prop="money" label="金额" required>
            <el-input-number
                v-model.number="formData.money"
                placeholder="请输入金额"
                :precision="2" 
                :min="0"
                :max="99999"
            />
        </el-form-item>

        <el-form-item prop="description" label="结果描述" required>
            <el-input
                v-model="formData.description" maxlength="255"
                placeholder="请输入结果描述"
                clearable
            />
        </el-form-item>

        <div style="text-align: right;">
            <el-button type="info" @click="emits('cancel')" v-text="'取消'"/>
            <el-button type="primary" @click="handleSubmit" v-text="'提交'"/>
        </div>
    </el-form>
</template>), (components/conditionMatch/ConditionMatchEditTable.vue, <script setup lang="ts">
import {ref} from "vue"
import {deleteConfirm} from "@/utils/confirm"
import {cloneDeep} from "lodash"
import DefaultConditionMatchAddInput from "@/components/conditionMatch/DefaultConditionMatchAddInput"
import type {ConditionMatchInsertInput} from "@/api/__generated/model/static"
import type {FormInstance} from "element-plus"
import {Delete, Plus} from "@element-plus/icons-vue"
import rules from "@/rules/conditionMatch/ConditionMatchEditTableRules"
import type {EditTableExpose} from "@/components/EditTable/EditTableExpose"

const formRef = ref<FormInstance | undefined>()

defineExpose<EditTableExpose>({formRef})

const rows = defineModel<ConditionMatchInsertInput[]>({
    required: true
})

withDefaults(
    defineProps<{
        idColumn?: boolean | undefined,
        indexColumn?: boolean | undefined,
        multiSelect?: boolean | undefined,
    }>(),
    {
        idColumn: false,
        indexColumn: false,
        multiSelect: true,
    }
)

// 多选
const selection = ref<ConditionMatchInsertInput[]>([])

const changeSelection = (item: ConditionMatchInsertInput[]) => {
    selection.value = item
}

// 新增
const handleAdd = () => {
    rows.value.push(cloneDeep(DefaultConditionMatchAddInput))
}

// 删除
const handleBatchDelete = async () => {
    const result = await deleteConfirm('这些条件匹配')
    if (!result) return
    rows.value = rows.value.filter(it => !selection.value.includes(it))
}

const handleSingleDelete = async (index: number) => {
    const result = await deleteConfirm('该条件匹配')
    if (!result) return
    rows.value = rows.value.filter((_, i) => i !== index)
}
</script>

<template>
    <el-form ref="formRef" :rules="rules" :model="rows" inline-message>
        <el-divider content-position="center">条件匹配信息</el-divider> 
        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" :icon="Plus" @click="handleAdd" v-text="'添加'"/>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" :icon="Delete" @click="handleBatchDelete" v-text="'删除'" :disabled="selection.length === 0"/>
            </el-col>
        </el-row>
        <el-table :data="rows" border stripe row-key="id" @selection-change="changeSelection">
            <el-table-column v-if="idColumn" label="ID" prop="id" fixed/>
            <el-table-column v-if="indexColumn" type="index" fixed/>
            <el-table-column v-if="multiSelect" type="selection" fixed/>
            <el-table-column label="匹配状态" prop="status">
                <template #default="{$index, row}">
                    <el-form-item :prop="[$index, 'status']" :rules="rules.status">
                    <MatchStatusSelect v-model="row.status"/>
                    </el-form-item>
                </template>
            </el-table-column>

            <el-table-column label="匹配日期" prop="date">
                <template #default="{$index, row}">
                    <el-form-item :prop="[$index, 'date']" :rules="rules.date">
                    <el-date-picker
                        v-model="row.date"
                        placeholder="请选择匹配日期"
                        type="datetime"
                    />
                    </el-form-item>
                </template>
            </el-table-column>

            <el-table-column label="金额" prop="money">
                <template #default="{$index, row}">
                    <el-form-item :prop="[$index, 'money']" :rules="rules.money">
                    <el-input-number
                        v-model.number="row.money"
                        placeholder="请输入金额"
                        :precision="2" 
                        :min="0"
                        :max="99999"
                    />
                    </el-form-item>
                </template>
            </el-table-column>

            <el-table-column label="结果描述" prop="description">
                <template #default="{$index, row}">
                    <el-form-item :prop="[$index, 'description']" :rules="rules.description">
                    <el-input
                        v-model="row.description" maxlength="255"
                        placeholder="请输入结果描述"
                        clearable
                    />
                    </el-form-item>
                </template>
            </el-table-column>

            <el-table-column label="操作" fixed="right">
                <template #default="{$index}">
                    <el-button type="danger" :icon="Delete" @click="handleSingleDelete($index)"/>
                </template>
            </el-table-column>
        </el-table>
    </el-form>
</template>), (components/conditionMatch/ConditionMatchQueryForm.vue, <script setup lang="ts">
import {computed} from "vue"
import {Search} from "@element-plus/icons-vue"
import type {ConditionMatchSpec} from "@/api/__generated/model/static"
import MatchStatusNullableSelect from "@/components/matchStatus/MatchStatusNullableSelect.vue"

const spec = defineModel<ConditionMatchSpec>({
    required: true
})

const emits = defineEmits<{
    (event: "query"): void,
}>()

const dateRange = computed<[string | undefined, string | undefined]>({
    get() {
        return [
            spec.value.minDate,
            spec.value.maxDate,
       ]
    },
    set(range: [string | undefined, string | undefined] | null) {
        spec.value.minDate = range?.[0]
        spec.value.maxDate = range?.[1]
    }
})
</script>

<template>
    <el-form>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-form-item prop="status" label="匹配状态">
                    <MatchStatusNullableSelect
                        v-model="spec.status"
                        @change="emits('query')"
                    />
                </el-form-item>
            </el-col>

            <el-col :span="8">
                <el-form-item prop="date" label="匹配日期">
                    <el-date-picker
                        v-model="dateRange"
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
                <el-form-item prop="money" label="金额">
                    <el-input-number
                        v-model.number="spec.minMoney"
                        placeholder="请输入最小金额"
                        :precision="2" 
                        :min="0"
                        :max="99999"
                        :value-on-clear="undefined"
                        @change="emits('query')"
                    />
                    <el-input-number
                        v-model.number="spec.maxMoney"
                        placeholder="请输入最大金额"
                        :precision="2" 
                        :min="0"
                        :max="99999"
                        :value-on-clear="undefined"
                        @change="emits('query')"
                    />
                </el-form-item>
            </el-col>

            <el-col :span="8">
                <el-form-item prop="description" label="结果描述">
                    <el-input
                        v-model="spec.description" maxlength="255"
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
import {ref} from "vue"
import {Plus, EditPen, Delete} from "@element-plus/icons-vue"
import type {Page, PageQuery, ConditionMatchSpec, ConditionMatchListView, ConditionMatchInsertInput, ConditionMatchUpdateInput} from "@/api/__generated/model/static"
import {api} from "@/api"
import {sendMessage} from "@/utils/message"
import {deleteConfirm} from "@/utils/confirm"
import {useLoading} from "@/utils/loading"
import {useLegalPage} from "@/utils/legalPage"
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

const getConditionMatch = withLoading((id: number) => api.conditionMatchService.get({id}))

const addConditionMatch = withLoading((body: ConditionMatchInsertInput) => api.conditionMatchService.insert({body}))

const editConditionMatch = withLoading((body: ConditionMatchUpdateInput) => api.conditionMatchService.update({body}))

const deleteConditionMatch = withLoading((ids: Array<number>) => api.conditionMatchService.delete({ids}))

// 多选
const selection = ref<ConditionMatchListView[]>([])

const changeSelection = (item: ConditionMatchListView[]) => {
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
        await editConditionMatch(updateInput)
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
        <ConditionMatchQueryForm v-model="queryInfo.spec" @query="queryPage"/>
        
        <div>
            <el-button type="primary" :icon="Plus" @click="startAdd" v-text="'新增'"/>
            <el-button type="danger" :icon="Delete" @click="handleDelete(selection.map(it => it.id))" 
                       v-text="'删除'" :disabled="selection.length === 0"/>
        </div>

        <template v-if="pageData">
            <ConditionMatchTable :rows="pageData.rows" @changeSelection="changeSelection">
                <template #operations="{row}">
                    <el-button type="warning" :icon="EditPen" @click="startEdit(row.id)" v-text="'编辑'"/>
                    <el-button type="danger" :icon="Delete" @click="handleDelete([row.id])" v-text="'删除'"/>                
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
    
    <el-dialog v-model="addDialogVisible" destroy-on-close>
        <ConditionMatchAddForm @submit="submitAdd" @cancel="cancelAdd"/>
    </el-dialog>
    
    <el-dialog v-model="editDialogVisible" destroy-on-close>
        <ConditionMatchEditForm v-if="updateInput !== undefined" :data="updateInput" @submit="submitEdit" @cancel="cancelEdit"/>
    </el-dialog>
</template>), (components/conditionMatch/ConditionMatchSingleSelect.vue, <script setup lang="ts">
import {ref} from "vue"
import type {Page, PageQuery, ConditionMatchSpec, ConditionMatchListView} from "@/api/__generated/model/static"
import {api} from "@/api"
import {useLoading} from "@/utils/loading"
import {useLegalPage} from "@/utils/legalPage"
import ConditionMatchTable from "@/components/conditionMatch/ConditionMatchTable.vue"
import ConditionMatchQueryForm from "@/components/conditionMatch/ConditionMatchQueryForm.vue"

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

const emits = defineEmits<{
    (event: "select", item: ConditionMatchListView): void,
}>()

const handleSelect = (item: ConditionMatchListView) => {
    emits("select", item)
}
</script>

<template>
    <el-form v-loading="isLoading">
        <ConditionMatchQueryForm v-model="queryInfo.spec" @query="queryPage"/>
    
        <template v-if="pageData">
            <ConditionMatchTable :rows="pageData.rows" :multi-select="false">
                <template #operations="{row}">
                    <el-button type="warning" @click="handleSelect(row)" v-text="'选择'"/>
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
    </el-form>
</template>), (components/conditionMatch/ConditionMatchMultiSelect.vue, <script setup lang="ts">
import {ref} from "vue"
import type {Page, PageQuery, ConditionMatchSpec, ConditionMatchListView} from "@/api/__generated/model/static"
import {api} from "@/api"
import {useLoading} from "@/utils/loading"
import {useLegalPage} from "@/utils/legalPage"
import ConditionMatchTable from "@/components/conditionMatch/ConditionMatchTable.vue"
import ConditionMatchQueryForm from "@/components/conditionMatch/ConditionMatchQueryForm.vue"

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

const emits = defineEmits<{
    (event: "submit", selection: Array<ConditionMatchListView>): void,
    (event: "cancel"): void,
}>()

const selectMap = ref<Map<number, ConditionMatchListView>>(new Map)

const handleSelect = (item: ConditionMatchListView) => {
    selectMap.value.set(item.id, item)
}

const handleUnSelect = (item: ConditionMatchListView) => {
    selectMap.value.delete(item.id)
}
</script>

<template>
    <el-form v-loading="isLoading">
        <ConditionMatchQueryForm v-model="queryInfo.spec" @query="queryPage"/>
    
        <template v-if="pageData">
            <ConditionMatchTable :rows="pageData.rows" :multi-select="false">
                <template #operations="{row}">
                    <el-button v-if="!selectMap.has(row.id)" type="warning" @click="handleSelect(row)" v-text="'选择'"/>
                    <el-button v-else type="warning" @click="handleUnSelect(row)" v-text="'取消'"/>
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
        
        <div style="text-align: right;">
            <el-button type="info" @click="emits('cancel')" v-text="'取消'"/>
            <el-button type="primary" @click="emits('submit', [...selectMap.values()])" v-text="'提交'"/>
        </div>
    </el-form>
</template>), (components/conditionMatch/ConditionMatchIdSelect.vue, <script setup lang="ts">
import {watch} from "vue"
import type {ConditionMatchListView} from "@/api/__generated/model/static"

const modelValue = defineModel<number | undefined>({
    required: true
})

const props = defineProps<{
    options: Array<ConditionMatchListView>
}>()

watch(() => [modelValue.value, props.options], () => {
    if (!(props.options.map(it => it.id) as Array<number | undefined>).includes(modelValue.value)) {
        modelValue.value = undefined
    }
}, {immediate: true})
</script>

<template>
    <el-select
        v-model="modelValue"
        filterable
        clearable
        placeholder="请选择条件匹配">
        <el-option
            v-for="option in options"
            :key="option.id"
            :label="option.id"
            :value="option.id"/>
    </el-select>
</template>), (components/conditionMatch/ConditionMatchIdMultiSelect.vue, <script setup lang="ts">
import type {ConditionMatchListView} from "@/api/__generated/model/static"

const modelValue = defineModel<number[]>({
    required: true
})

defineProps<{
    options: Array<ConditionMatchListView>
}>()
</script>

<template>
    <el-select
        v-model="modelValue"
        multiple
        filterable
        clearable
        collapse-tags
        collapse-tags-tooltip
        :max-collapse-tags="4"
        placeholder="请选择条件匹配">
        <el-option
            v-for="option in options"
            :key="option.id"
            :label="option.id"
            :value="option.id"/>
    </el-select>
</template>), (rules/conditionMatch/ConditionMatchAddFormRules.ts, import type {ConditionMatchInsertInput} from "@/api/__generated/model/static"
import type {FormRules} from 'element-plus'

export default <FormRules<ConditionMatchInsertInput>> {
    status: [
        {required: true, message: '匹配状态不能为空', trigger: 'blur'},
    ],
    date: [
        {required: true, message: '匹配日期不能为空', trigger: 'blur'},
        {type: 'date', message: '匹配日期必须是日期', trigger: 'blur'},
    ],
    money: [
        {required: true, message: '金额不能为空', trigger: 'blur'},
        {type: 'number', message: '金额必须是数字', trigger: 'blur'},
        {type: 'number', min: 1, max: 99999, message: '金额需要在1-99999之间', trigger: 'blur'},
    ],
    description: [
        {required: true, message: '结果描述不能为空', trigger: 'blur'},
        {type: 'string', min: 1, max: 255, message: '结果描述长度需要在1-255之间', trigger: 'blur'},
    ],

}), (rules/conditionMatch/ConditionMatchEditFormRules.ts, import type {ConditionMatchUpdateInput} from "@/api/__generated/model/static"
import type {FormRules} from 'element-plus'

export default <FormRules<ConditionMatchUpdateInput>> {
    id: [
        {required: true, message: 'ID不能为空', trigger: 'blur'},
        {type: 'integer', message: 'ID必须是整数', trigger: 'blur'},
        {type: 'integer', min: 1, max: 9999999999, message: 'ID需要在1-9999999999之间', trigger: 'blur'},
    ],
    status: [
        {required: true, message: '匹配状态不能为空', trigger: 'blur'},
    ],
    date: [
        {required: true, message: '匹配日期不能为空', trigger: 'blur'},
        {type: 'date', message: '匹配日期必须是日期', trigger: 'blur'},
    ],
    money: [
        {required: true, message: '金额不能为空', trigger: 'blur'},
        {type: 'number', message: '金额必须是数字', trigger: 'blur'},
        {type: 'number', min: 1, max: 99999, message: '金额需要在1-99999之间', trigger: 'blur'},
    ],
    description: [
        {required: true, message: '结果描述不能为空', trigger: 'blur'},
        {type: 'string', min: 1, max: 255, message: '结果描述长度需要在1-255之间', trigger: 'blur'},
    ],

}), (rules/conditionMatch/ConditionMatchEditTableRules.ts, import type {ConditionMatchInsertInput} from "@/api/__generated/model/static"
import type {FormRules} from 'element-plus'

export default <FormRules<ConditionMatchInsertInput>> {
    status: [
        {required: true, message: '匹配状态不能为空', trigger: 'blur'},
    ],
    date: [
        {required: true, message: '匹配日期不能为空', trigger: 'blur'},
        {type: 'date', message: '匹配日期必须是日期', trigger: 'blur'},
    ],
    money: [
        {required: true, message: '金额不能为空', trigger: 'blur'},
        {type: 'number', message: '金额必须是数字', trigger: 'blur'},
        {type: 'number', min: 1, max: 99999, message: '金额需要在1-99999之间', trigger: 'blur'},
    ],
    description: [
        {required: true, message: '结果描述不能为空', trigger: 'blur'},
        {type: 'string', min: 1, max: 255, message: '结果描述长度需要在1-255之间', trigger: 'blur'},
    ],

})]
"""