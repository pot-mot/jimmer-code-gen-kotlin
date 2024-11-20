package top.potmot.business.single

const val index = """${'$'}index"""

const val vue3ElementPlusResult = """
[(components/conditionMatch/ConditionMatchTable.vue, <script setup lang="ts">
import type {ConditionMatchListView} from "@/api/__generated/model/static"
import MatchStatusView from "@/components/matchStatus/MatchStatusView.vue"

withDefaults(defineProps<{
    rows: Array<ConditionMatchListView>,
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined
}>(), {
    idColumn: false,
    indexColumn: true,
    multiSelect: true
})

const emits = defineEmits<{
    (
        event: "selectionChange",
        selection: Array<ConditionMatchListView>
    ): void
}>()

defineSlots<{
    operations(props: {
        row: ConditionMatchListView,
        index: number
    }): any
}>()

const handleSelectionChange = (
    newSelection: Array<ConditionMatchListView>
): void => {
    emits("selectionChange", newSelection)
}
</script>

<template>
    <el-table
        :data="rows"
        row-key="id"
        border
        stripe
        @selection-change="handleSelectionChange"
    >
        <el-table-column
            v-if="idColumn"
            prop="id"
            label="ID"
            fixed
        />
        <el-table-column v-if="indexColumn" type="index" fixed/>
        <el-table-column
            v-if="multiSelect"
            type="selection"
            fixed
        />
        <el-table-column prop="userId" label="用户"/>
        <el-table-column prop="conditionId" label="条件"/>
        <el-table-column prop="status" label="匹配状态">
            <template #default="scope">
                <MatchStatusView :value="scope.row.status"/>
            </template>
        </el-table-column>
        <el-table-column prop="date" label="匹配日期"/>
        <el-table-column prop="money" label="金额"/>
        <el-table-column prop="description" label="结果描述"/>
        <el-table-column label="操作" fixed="right">
            <template #default="scope">
                <slot
                    name="operations"
                    :row="scope.row as ConditionMatchListView"
                    :index="scope.$index"
                />
            </template>
        </el-table-column>
    </el-table>
</template>
), (components/conditionMatch/ConditionMatchAddFormType.d.ts, export type ConditionMatchAddFormType = {
    userId: number | undefined
    conditionId: number | undefined
    status: string
    date: string
    money: number
    description: string
}
), (components/conditionMatch/defaultConditionMatch.ts, import type {ConditionMatchAddFormType} from "./ConditionMatchAddFormType"

export const defaultConditionMatch: ConditionMatchAddFormType = {
    userId: undefined,
    conditionId: undefined,
    status: "EQ",
    date: "",
    money: 0,
    description: "",
}
), (components/conditionMatch/ConditionMatchAddForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {AddFormExpose} from "@/api/__generated/model/static/form/AddFormExpose"
import type {
    ConditionMatchInsertInput,
    ConditionMatchAddFormType,
    SysUserOptionView,
    PolicyConditionOptionView
} from "@/api/__generated/model/static"
import {cloneDeep} from "lodash"
import {defaultConditionMatch} from "@/components/conditionMatch/defaultConditionMatch"
import {useRules} from "@/rules/ConditionMatchAddFormRules"
import SysUserIdSelect from "@/components/sysUser/SysUserIdSelect.vue"
import PolicyConditionIdSelect from "@/components/policyCondition/PolicyConditionIdSelect.vue"
import MatchStatusSelect from "@/components/matchStatus/MatchStatusSelect.vue"
import {sendMessage} from "@/utils/message"

const props = withDefaults(defineProps<{
    submitLoading?: boolean | undefined,
    userIdOptions: Array<SysUserOptionView>,
    conditionIdOptions: Array<PolicyConditionOptionView>
}>(), {
    submitLoading: false
})

const emits = defineEmits<{
    (
        event: "submit",
        formData: ConditionMatchInsertInput
    ): void,
    (event: "cancel"): void
}>()

defineSlots<{
    operations(props: {
        handleSubmit: () => Promise<void>,
        handleCancel: () => void
    }): any
}>()

const formData = ref<ConditionMatchAddFormType>(cloneDeep(defaultConditionMatch))

const formRef = ref<FormInstance>()
const rules = useRules(formData)

// 提交
const handleSubmit = async (): Promise<void> => {
    if (props.submitLoading) return

    const formValid: boolean | undefined = await formRef.value?.validate().catch(() => false)

    if (formValid) {
        if (formData.value.toOnePropertyId === undefined) {
            sendMessage("toOneProperty不可为空", "warning")
            return
        }


        if (formData.value.toOnePropertyId === undefined) {
            sendMessage("toOneProperty不可为空", "warning")
            return
        }

        emits("submit", formData.value)
    }
}

// 取消
const handleCancel = (): void => {
    emits("cancel")
}
</script>

<template>
    <el-form
        :model="formData"
        ref="formRef"
        :rules="rules"
    >
        <el-form-item prop="userId" label="用户">
            <SysUserIdSelect
                v-model="formData.userId"
                :options="userIdOptions"
            />
        </el-form-item>
        <el-form-item prop="conditionId" label="条件">
            <PolicyConditionIdSelect
                v-model="formData.conditionId"
                :options="conditionIdOptions"
            />
        </el-form-item>
        <el-form-item prop="status" label="匹配状态">
            <MatchStatusSelect v-model="formData.status"/>
        </el-form-item>
        <el-form-item prop="date" label="匹配日期">
            <el-date-picker
                v-model="formData.date"
                type="datetime"
                value-format="YYYY-MM-DDTHH:mm:ss"
                placeholder="请选择匹配日期"
                clearable
            />
        </el-form-item>
        <el-form-item prop="money" label="金额">
            <el-input-number
                v-model.number="formData.money"
                placeholder="请输入金额"
                :precision="2"
                :min="0.00"
                :max="99999.99"
                :value-on-clear="0.00"
            />
        </el-form-item>
        <el-form-item prop="description" label="结果描述">
            <el-input
                v-model="formData.description"
                placeholder="请输入结果描述"
                clearable
            />
        </el-form-item>

        <slot
            name="operations"
            :handleSubmit="handleSubmit"
            :handleCancel="handleCancel"
        >
            <div style="text-align: right;">
                <el-button type="warning" @click="handleCancel">
                    取消
                </el-button>
                <el-button
                    type="primary"
                    :loading="submitLoading"
                    @click="handleSubmit"
                >
                    提交
                </el-button>
            </div>
        </slot>
    </el-form>
</template>
), (components/conditionMatch/ConditionMatchEditForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {EditFormExpose} from "@/api/__generated/model/static/form/EditFormExpose"
import type {
    ConditionMatchUpdateInput,
    SysUserOptionView,
    PolicyConditionOptionView
} from "@/api/__generated/model/static"
import {useRules} from "@/rules/ConditionMatchEditFormRules"
import SysUserIdSelect from "@/components/sysUser/SysUserIdSelect.vue"
import PolicyConditionIdSelect from "@/components/policyCondition/PolicyConditionIdSelect.vue"
import MatchStatusSelect from "@/components/matchStatus/MatchStatusSelect.vue"

const formData = defineModel<ConditionMatchUpdateInput>({
    required: true
})

const props = withDefaults(defineProps<{
    submitLoading?: boolean | undefined,
    userIdOptions: Array<SysUserOptionView>,
    conditionIdOptions: Array<PolicyConditionOptionView>
}>(), {
    submitLoading: false
})

const emits = defineEmits<{
    (
        event: "submit",
        formData: ConditionMatchUpdateInput
    ): void,
    (event: "cancel"): void
}>()

defineSlots<{
    operations(props: {
        handleSubmit: () => Promise<void>,
        handleCancel: () => void
    }): any
}>()

const formRef = ref<FormInstance>()
const rules = useRules(formData)

// 提交
const handleSubmit = async (): Promise<void> => {
    if (props.submitLoading) return

    const formValid: boolean | undefined = await formRef.value?.validate().catch(() => false)

    if (formValid) {
        emits("submit", formData.value)
    }
}

// 取消
const handleCancel = (): void => {
    emits("cancel")
}
</script>

<template>
    <el-form
        :model="formData"
        ref="formRef"
        :rules="rules"
    >
        <el-form-item
            prop="userId"
            label="用户"
            @change="emits('query')"
        >
            <SysUserIdSelect
                v-model="formData.userId"
                :options="userIdOptions"
            />
        </el-form-item>
        <el-form-item
            prop="conditionId"
            label="条件"
            @change="emits('query')"
        >
            <PolicyConditionIdSelect
                v-model="formData.conditionId"
                :options="conditionIdOptions"
            />
        </el-form-item>
        <el-form-item
            prop="status"
            label="匹配状态"
            @change="emits('query')"
        >
            <MatchStatusSelect v-model="formData.status"/>
        </el-form-item>
        <el-form-item
            prop="date"
            label="匹配日期"
            @change="emits('query')"
        >
            <el-date-picker
                v-model="formData.date"
                type="datetime"
                value-format="YYYY-MM-DDTHH:mm:ss"
                placeholder="请选择匹配日期"
                clearable
            />
        </el-form-item>
        <el-form-item
            prop="money"
            label="金额"
            @change="emits('query')"
        >
            <el-input-number
                v-model.number="formData.money"
                placeholder="请输入金额"
                :precision="2"
                :min="0.00"
                :max="99999.99"
                :value-on-clear="0.00"
            />
        </el-form-item>
        <el-form-item
            prop="description"
            label="结果描述"
            @change="emits('query')"
        >
            <el-input
                v-model="formData.description"
                placeholder="请输入结果描述"
                clearable
            />
        </el-form-item>

        <slot
            name="operations"
            :handleSubmit="handleSubmit"
            :handleCancel="handleCancel"
        >
            <div style="text-align: right;">
                <el-button type="warning" @click="handleCancel">
                    取消
                </el-button>
                <el-button
                    type="primary"
                    :loading="submitLoading"
                    @click="handleSubmit"
                >
                    提交
                </el-button>
            </div>
        </slot>
    </el-form>
</template>
), (components/conditionMatch/ConditionMatchEditTable.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {AddFormExpose} from "@/api/__generated/model/static/form/AddFormExpose"
import type {
    ConditionMatchAddFormType,
    SysUserOptionView,
    PolicyConditionOptionView
} from "@/api/__generated/model/static"
import {cloneDeep} from "lodash"
import {defaultConditionMatch} from "@/components/conditionMatch/defaultConditionMatch"
import {useRules} from "@/rules/ConditionMatchEditTableRules"
import {Plus, Delete} from "@element-plus/icons-vue"
import SysUserIdSelect from "@/components/sysUser/SysUserIdSelect.vue"
import PolicyConditionIdSelect from "@/components/policyCondition/PolicyConditionIdSelect.vue"
import MatchStatusSelect from "@/components/matchStatus/MatchStatusSelect.vue"

const rows = defineModel<Array<ConditionMatchAddFormType>>({
    required: true
})

const props = withDefaults(defineProps<{
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined,
    submitLoading?: boolean | undefined,
    userIdOptions: Array<SysUserOptionView>,
    conditionIdOptions: Array<PolicyConditionOptionView>
}>(), {
    idColumn: false,
    indexColumn: false,
    multiSelect: true,
    submitLoading: false
})

const emits = defineEmits<{
    (
        event: "submit",
        rows: Array<ConditionMatchAddFormType>
    ): void,
    (event: "cancel"): void
}>()

defineSlots<{
    operations(props: {
        handleSubmit: () => Promise<void>,
        handleCancel: () => void
    }): any
}>()

const formRef = ref<FormInstance>()
const rules = useRules(rows)

// 提交
const handleSubmit = async (): Promise<void> => {
    if (props.submitLoading) return

    const formValid: boolean | undefined = await formRef.value?.validate().catch(() => false)

    if (formValid) {
        emits("submit", rows.value)
    }
}

// 取消
const handleCancel = (): void => {
    emits("cancel")
}

// 多选
const selection = ref<Array<ConditionMatchAddFormType>>([])

const handleSelectionChange = (
    newSelection: Array<ConditionMatchAddFormType>
): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    rows.value.push(cloneDeep(defaultConditionMatch))
}

// 删除
const handleBatchDelete = async (): Promise<void> => {
    const result = await deleteConfirm("这些条件匹配")
    if (!result) return
    rows.value = rows.value.filter(it => !selection.value.includes(it))
}

const handleSingleDelete = async (index: number): Promise<void> => {
    const result = await deleteConfirm("该条件匹配")
    if (!result) return
    rows.value = rows.value.filter((_, i) => i !== index)
}
</script>

<template>
    <el-form
        :model="rows"
        ref="formRef"
        :rules="rules"
    >
        <div>
            <el-button
                type="primary"
                :icon="Plus"
                @click="handleAdd"
            >
                新增
            </el-button>
            <el-button
                type="danger"
                :icon="Delete"
                :disabled="selection.length === 0"
                @click="handleBatchDelete"
            >
                删除
            </el-button>
        </div>

        <el-table
            :data="rows"
            row-key="id"
            border
            stripe
            @selection-change="handleSelectionChange"
        >
            <el-table-column
                v-if="idColumn"
                prop="id"
                label="ID"
                fixed
            />
            <el-table-column v-if="indexColumn" type="index" fixed/>
            <el-table-column
                v-if="multiSelect"
                type="selection"
                fixed
            />
            <el-table-column prop="userId" label="用户">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'userId']"
                        label="用户"
                        :rule="rules.userId"
                    >
                        <SysUserIdSelect
                            v-model="rows.userId"
                            :options="userIdOptions"
                        />
                    </el-form-item>
                </template>
            </el-table-column>
            <el-table-column prop="conditionId" label="条件">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'conditionId']"
                        label="条件"
                        :rule="rules.conditionId"
                    >
                        <PolicyConditionIdSelect
                            v-model="rows.conditionId"
                            :options="conditionIdOptions"
                        />
                    </el-form-item>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="匹配状态">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'status']"
                        label="匹配状态"
                        :rule="rules.status"
                    >
                        <MatchStatusSelect v-model="rows.status"/>
                    </el-form-item>
                </template>
            </el-table-column>
            <el-table-column prop="date" label="匹配日期">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'date']"
                        label="匹配日期"
                        :rule="rules.date"
                    >
                        <el-date-picker
                            v-model="rows.date"
                            type="datetime"
                            value-format="YYYY-MM-DDTHH:mm:ss"
                            placeholder="请选择匹配日期"
                            clearable
                        />
                    </el-form-item>
                </template>
            </el-table-column>
            <el-table-column prop="money" label="金额">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'money']"
                        label="金额"
                        :rule="rules.money"
                    >
                        <el-input-number
                            v-model.number="rows.money"
                            placeholder="请输入金额"
                            :precision="2"
                            :min="0.00"
                            :max="99999.99"
                            :value-on-clear="0.00"
                        />
                    </el-form-item>
                </template>
            </el-table-column>
            <el-table-column prop="description" label="结果描述">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'description']"
                        label="结果描述"
                        :rule="rules.description"
                    >
                        <el-input
                            v-model="rows.description"
                            placeholder="请输入结果描述"
                            clearable
                        />
                    </el-form-item>
                </template>
            </el-table-column>
            <el-table-column label="操作" fixed="right">
                <template #default="scope">
                    <el-button
                        type="danger"
                        :icon="Delete"
                        @click="handleSingleDelete(scope.$index)"
                    />
                </template>
            </el-table-column>
        </el-table>

        <slot
            name="operations"
            :handleSubmit="handleSubmit"
            :handleCancel="handleCancel"
        >
            <div style="text-align: right;">
                <el-button type="warning" @click="handleCancel">
                    取消
                </el-button>
                <el-button
                    type="primary"
                    :loading="submitLoading"
                    @click="handleSubmit"
                >
                    提交
                </el-button>
            </div>
        </slot>
    </el-form>
</template>
), (components/conditionMatch/ConditionMatchQueryForm.vue, <script setup lang="ts">
import {Search} from "@element-plus/icons-vue"
import type {
    ConditionMatchSpec,
    SysUserOptionView,
    PolicyConditionOptionView
} from "@/api/__generated/model/static"
import SysUserIdSelect from "@/components/sysUser/SysUserIdSelect.vue"
import PolicyConditionIdSelect from "@/components/policyCondition/PolicyConditionIdSelect.vue"
import MatchStatusNullableSelect from "@/components/matchStatus/MatchStatusNullableSelect.vue"

const spec = defineModel<ConditionMatchSpec>({
    required: true
})

defineProps<{
    userIdOptions: Array<SysUserOptionView>,
    conditionIdOptions: Array<PolicyConditionOptionView>
}>()

const emits = defineEmits<{
    (event: "query", spec: ConditionMatchSpec): void
}>()

</script>

<template>
    <el-form :model="spec">
        <el-row :gutter="20">
            <el-col :span="8">
                <el-form-item prop="userId" label="用户">
                    <SysUserIdSelect
                        v-model="spec.userId"
                        :options="userIdOptions"
                    />
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item prop="conditionId" label="条件">
                    <PolicyConditionIdSelect
                        v-model="spec.conditionId"
                        :options="conditionIdOptions"
                    />
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item prop="status" label="匹配状态">
                    <MatchStatusNullableSelect v-model="spec.status"/>
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item prop="date" label="匹配日期">
                    <el-date-picker
                        v-model="dateRange"
                        type="datetime"
                        value-format="YYYY-MM-DDTHH:mm:ss"
                        is-range
                        unlink-panels
                        start-placeholder="请选择开始匹配日期"
                        end-placeholder="请选择结束匹配日期"
                        clearable
                    />
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item prop="money" label="金额">
                    <el-input-number
                        v-model.number="spec.minMoney"
                        placeholder="请输入最小金额"
                        :precision="2"
                        :min="0.00"
                        :max="99999.99"
                        :value-on-clear="undefined"
                    />
                    <el-input-number
                        v-model.number="spec.maxMoney"
                        placeholder="请输入最大金额"
                        :precision="2"
                        :min="0.00"
                        :max="99999.99"
                        :value-on-clear="undefined"
                    />
                </el-form-item>
            </el-col>
            <el-col :span="8">
                <el-form-item prop="description" label="结果描述">
                    <el-input
                        v-model="spec.description"
                        placeholder="请输入结果描述"
                        clearable
                    />
                </el-form-item>
            </el-col>
            <el-button
                type="primary"
                :icon="Search"
                @click="emits('query', spec)"
            >
                查询
            </el-button>
        </el-row>
    </el-form>
</template>
), (pages/conditionMatch/ConditionMatchPage.vue, <script setup lang="ts">
import {ref, onBeforeMount} from "vue"
import type {Ref} from "vue"
import {Plus, EditPen, Delete} from "@element-plus/icons-vue"
import type {
    Page,
    PageQuery,
    ConditionMatchListView,
    ConditionMatchInsertInput,
    ConditionMatchUpdateInput,
    ConditionMatchSpec,
    SysUserOptionView,
    PolicyConditionOptionView
} from "@/api/__generated/model/static"
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

const pageData = ref<Page<EntityListView>>()

// 分页查询
const queryInfo = ref<PageQuery<EntitySpec>>({
    spec: {},
    pageIndex: 1,
    pageSize: 5
})

const {queryPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.entityService.page)
)

const getConditionMatch = withLoading((id: number) => api.conditionMatchService.get({id}))

const addConditionMatch = withLoading((body: ConditionMatchInsertInput) => api.conditionMatchService.insert({body}))

const editConditionMatch = withLoading((body: ConditionMatchUpdateInput) => api.conditionMatchService.update({body}))

const deleteConditionMatch = withLoading((ids: Array<number>) => api.conditionMatchService.delete({ids}))

// 多选
const selection = ref<EntityListView[]>([])

const handleSelectionChange = (
    newSelection: Array<ConditionMatchListView>
): void => {
    selection.value = newSelection
}

// 用户选项
const userIdOptions = ref<Array<SysUserOptionView>>()
onBeforeMount(async () => {
    userIdOptions.value = await api.conditionMatchService.listOptions({body: {}})
})

// 条件选项
const conditionIdOptions = ref<Array<PolicyConditionOptionView>>()
onBeforeMount(async () => {
    conditionIdOptions.value = await api.conditionMatchService.listOptions({body: {}})
})

// 新增
const addDialogVisible = ref<boolean>(false)

const startAdd = (): void => {
    addDialogVisible.value = true
}

const submitAdd = (insertInput: ConditionMatchInsertInput): void => {
    try {
        await addConditionMatch(insertInput)
        await queryPage()
        addDialogVisible.value = false

        sendMessage('新增条件匹配成功', 'success')
    } catch (e) {
        sendMessage("新增条件匹配失败", "error", e)
    }
}

const cancelAdd = (): void => {
    addDialogVisible.value = false
}

// 修改
const editDialogVisible = ref(false)

const updateInput = ref<ConditionMatchUpdateInput | undefined>(undefined)

const startEdit = (id: number): void => {
    updateInput.value = await getConditionMatch(id)
    if (updateInput.value === undefined) {
        sendMessage('编辑的条件匹配不存在', 'error')
        return
    }
    editDialogVisible.value = true
}

const submitEdit = (updateInput: ConditionMatchUpdateInput): void => {
    try {
        await editConditionMatch(updateInput)
        await queryPage()
        editDialogVisible.value = false

        sendMessage('编辑条件匹配成功', 'success')
    } catch (e) {
        sendMessage('编辑条件匹配失败', 'error', e)
    }
}

const cancelEdit = (): void => {
    editDialogVisible.value = false
    updateInput.value = undefined
}

// 删除
const handleDelete = (ids: number[]): void => {
    const result = await deleteConfirm('条件匹配')
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
    <el-card v-loading="isLoading">
        <ConditionMatchQueryForm
            v-model="queryForm.spec"
            @query="queryPage"
        />

        <div>
            <el-button
                type="primary"
                :icon="Plus"
                @click="startAdd"
            >
                新增
            </el-button>
            <el-button
                v-if="selection.length === 0"
                type="danger"
                :icon="Delete"
                @click="handleDelete(selection.map(it => it.id))"
            >
                删除
            </el-button>
        </div>

        <ConditionMatchTable
            :rows="pageData.rows"
            @selectionChange="handleSelectionChange"
        >
            <template #operations="{row}">
                <el-button
                    type="warning"
                    :icon="EditPen"
                    plain
                    @click="startEdit(row.id)"
                >
                    编辑
                </el-button>
                <el-button
                    type="danger"
                    :icon="Delete"
                    plain
                    @click="handleDelete([row.id])"
                >
                    删除
                </el-button>
            </template>
        </ConditionMatchTable>

        <el-pagination
            v-model:current-page="queryInfo.pageIndex"
            v-model:page-size="queryInfo.pageSize"
            :total="pageData.totalRowCount"
            :page-sizes="[5, 10, 20]"
            layout="total, sizes, prev, pager, next, jumper"
        />
    </el-card>

    <el-dialog
        v-model="addDialogVisible"
        v-if="userId && conditionId"
        destroy-on-close
        :close-on-click-modal="false"
    >
        <ConditionMatchAddForm
            :userId="userId"
            :conditionId="conditionId"
            :submitLoading="isLoading"
            @submit="submitAdd"
            @cancel="cancelAdd"
        />
    </el-dialog>

    <el-dialog
        v-model="editDialogVisible"
        v-if="userId && conditionId"
        destroy-on-close
        :close-on-click-modal="false"
    >
        <ConditionMatchEditForm
            v-if="updateInput !== undefined"
            v-model="updateInput"
            :userId="userId"
            :conditionId="conditionId"
            :submitLoading="isLoading"
            @submit="submitEdit"
            @cancel="cancelEdit"
        />
    </el-dialog>
</template>
), (components/conditionMatch/ConditionMatchIdSelect.vue, <script setup lang="ts">
import {watch} from "vue"
import type {ConditionMatchOptionView} from "@/api/__generated/model/static"

const modelValue = defineModel<number | undefined>({
    required: true
})

const props = defineProps<{
    options: Array<ConditionMatchOptionView>
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
        placeholder="请选择条件匹配"
        filterable
        clearable
        :value-on-clear="undefined"
    >
        <el-option
            v-for="option in options"
            :key="option.id"
            :value="option.id"
            :label="option.id"
        />
    </el-select>
</template>
), (components/conditionMatch/ConditionMatchIdMultiSelect.vue, <script setup lang="ts">
import {watch} from "vue"
import type {ConditionMatchOptionView} from "@/api/__generated/model/static"

const modelValue = defineModel<Array<number>>({
    required: true
})

const props = defineProps<{
    options: Array<ConditionMatchOptionView>
}>()

watch(() => [modelValue.value, props.options], () => {
    const newModelValue: Array<number> = []

    for (const item of modelValue.value) {
        if (props.options.map(it => it.id).includes(item)) {
            newModelValue.push(item)
        }
    }
    if (modelValue.value.length != newModelValue.length)
        modelValue.value = newModelValue
}, {immediate: true})
</script>

<template>
    <el-select
        v-model="modelValue"
        placeholder="请选择条件匹配"
        filterable
        clearable
        :value-on-clear="undefined"
        multiple
        collapse-tags
        collapse-tags-tooltip
    >
        <el-option
            v-for="option in options"
            :key="option.id"
            :value="option.id"
            :label="option.id"
        />
    </el-select>
</template>
), (rules/conditionMatch/ConditionMatchAddFormRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {ConditionMatchInsertInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<ConditionMatchInsertInput>): FormRules<ConditionMatchInsertInput> => {
    return {
        userId: [
            {required: true, message: "用户不能为空", trigger: "blur"},
        ],
        conditionId: [
            {required: true, message: "条件不能为空", trigger: "blur"},
        ],
        status: [
            {required: true, message: "匹配状态不能为空", trigger: "blur"},
            {type: "enum", enum: ["EQ", "NE"], message: "匹配状态必须是EQ/NE", trigger: "blur"},
        ],
        date: [
            {required: true, message: "匹配日期不能为空", trigger: "blur"},
            {type: "date", message: "匹配日期必须是日期", trigger: "blur"},
        ],
        money: [
            {required: true, message: "金额不能为空", trigger: "blur"},
            {type: "number", message: "金额必须是数字", trigger: "blur"},
            {type: "number", min: 0.0, max: 99999.99, message: "金额必须在0.0-99999.99之间", trigger: "blur"},
        ],
        description: [
            {required: true, message: "结果描述不能为空", trigger: "blur"},
            {type: "string", max: 255, message: "结果描述长度必须小于255", trigger: "blur"},
        ],
    }
}), (rules/conditionMatch/ConditionMatchEditFormRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {ConditionMatchUpdateInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<ConditionMatchUpdateInput>): FormRules<ConditionMatchUpdateInput> => {
    return {
        id: [
            {required: true, message: "ID不能为空", trigger: "blur"},
            {type: "integer", message: "ID必须是整数", trigger: "blur"},
            {type: "integer", min: 0.0, max: 9.999999999E9, message: "ID必须在0.0-9.999999999E9之间", trigger: "blur"},
        ],
        userId: [
            {required: true, message: "用户不能为空", trigger: "blur"},
        ],
        conditionId: [
            {required: true, message: "条件不能为空", trigger: "blur"},
        ],
        status: [
            {required: true, message: "匹配状态不能为空", trigger: "blur"},
            {type: "enum", enum: ["EQ", "NE"], message: "匹配状态必须是EQ/NE", trigger: "blur"},
        ],
        date: [
            {required: true, message: "匹配日期不能为空", trigger: "blur"},
            {type: "date", message: "匹配日期必须是日期", trigger: "blur"},
        ],
        money: [
            {required: true, message: "金额不能为空", trigger: "blur"},
            {type: "number", message: "金额必须是数字", trigger: "blur"},
            {type: "number", min: 0.0, max: 99999.99, message: "金额必须在0.0-99999.99之间", trigger: "blur"},
        ],
        description: [
            {required: true, message: "结果描述不能为空", trigger: "blur"},
            {type: "string", max: 255, message: "结果描述长度必须小于255", trigger: "blur"},
        ],
    }
}), (rules/conditionMatch/ConditionMatchEditTableRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {ConditionMatchUpdateInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<Array<ConditionMatchUpdateInput>>): FormRules<ConditionMatchUpdateInput> => {
    return {
        userId: [
        ],
        conditionId: [
        ],
        status: [
            {required: true, message: "匹配状态不能为空", trigger: "blur"},
            {type: "enum", enum: ["EQ", "NE"], message: "匹配状态必须是EQ/NE", trigger: "blur"},
        ],
        date: [
            {required: true, message: "匹配日期不能为空", trigger: "blur"},
            {type: "date", message: "匹配日期必须是日期", trigger: "blur"},
        ],
        money: [
            {required: true, message: "金额不能为空", trigger: "blur"},
            {type: "number", message: "金额必须是数字", trigger: "blur"},
            {type: "number", min: 0.0, max: 99999.99, message: "金额必须在0.0-99999.99之间", trigger: "blur"},
        ],
        description: [
            {required: true, message: "结果描述不能为空", trigger: "blur"},
            {type: "string", max: 255, message: "结果描述长度必须小于255", trigger: "blur"},
        ],
    }
})]
"""