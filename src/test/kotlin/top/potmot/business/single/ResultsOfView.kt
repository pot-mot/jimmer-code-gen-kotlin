package top.potmot.business.single

const val vue3ElementPlusResult = """
[(/components/conditionMatch/ConditionMatchTable.vue, <script setup lang="ts">
defineProps<{
    rows: Array<ConditionMatchListView>
}>()

const slots = defineSlots<{
	default(props: {row: ConditionMatchListView, index: number}): any,
}>()

const emits = defineEmits<{
    (event: "changeSelection"): void,
}>()
</script>

<template>
    <el-table :data="rows" border stripe row-key="id"
              @selection-change="emits('changeSelection')">
        <el-table-column type="index"/>
        <el-table-column type="selection"/>
        <el-table-column label="匹配状态" prop="status"/>
        <el-table-column label="匹配日期" prop="date"/>
        <el-table-column label="结果描述" prop="description"/>
        
        <el-table-column label="操作" width="330px">
            <template #default="scope">
                <slot name="operations" row="scope.row" :index="scope.${'$'}index" />
            </template>
        </el-table-column>
    </el-table>
</template>), (/components/conditionMatch/ConditionMatchForm.vue, <script setup lang="ts">
import type {ConditionMatchInsertInput, ConditionMatchUpdateInput} from "@/api/__generated/model/static"

const formData = defineModel<ConditionMatchInsertInput | ConditionMatchUpdateInput>({
    required: true
})

const emits = defineEmits<{
    (event: "submit"): void,
    (event: "cancel"): void,
}>()
</script>

<template>
    <el-form>
        
        <el-form-item label="匹配状态">
            <el-input
                placeholder="请输入匹配状态"
                v-model="formData.status"/>
        </el-form-item>

        <el-form-item label="匹配日期">
            <el-input
                placeholder="请输入匹配日期"
                v-model="formData.date"/>
        </el-form-item>

        <el-form-item label="结果描述">
            <el-input
                placeholder="请输入结果描述"
                v-model="formData.description"/>
        </el-form-item>
        
        <div style="text-align: right;">
            <el-button type="warning" @click="emits('cancel')" v-text="取消"/>
            <el-button type="primary" @click="emits('submit')" v-text="提交"/>
        </div>
    </el-form>
</template>), (/components/conditionMatch/ConditionMatchQueryForm.vue, <script setup lang="ts">
import type {ConditionMatchSpec, MatchStatus} from "@/api/__generated/model/static"
import {MatchStatus_CONSTANTS} from "@/api/__generated/model/static"
import MatchStatusSelect from "@/components/matchStatus/MatchStatusSelect"

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
                    <MatchStatusSelect v-model="spec.status"/>
                </el-form-item>
            </el-col>

            <el-col :span="8">
                <el-form-item label="匹配日期">
                    <el-input
                        placeholder="请输入匹配日期"
                        v-model="spec.date"
                        clearable
                        @change="emits('query')"/>
                </el-form-item>
            </el-col>

            <el-col :span="8">
                <el-form-item label="结果描述">
                    <el-input
                        placeholder="请输入结果描述"
                        v-model="spec.description"
                        clearable
                        @change="emits('query')"/>
                </el-form-item>
            </el-col>
    
            <el-button :icon="Search" type="primary" @click="emits('query')"/>
        </el-row>
    </el-form>
</template>), (/pages/conditionMatch/ConditionMatchPage.vue, <script setup lang="ts">
import {Check, Close, Delete, Search} from "@element-plus/icons-vue";
import {ref} from "vue";
import type {Page, PageQuery, ConditionMatchSpec, ConditionMatchListView} from "@/api/__generated/model/static";
import {api} from "@/api";
import {sendMessage} from "@/utils/message";
import {deleteConfirm} from "@/utils/confirm";
import {useLoading} from "@/utils/loading";
import {useLegalPage} from "@/utils/legalPage";
import ConditionMatchTable from "@/src/components/conditionMatch/ConditionMatchTable"
import ConditionMatchForm from "@/src/components/conditionMatch/ConditionMatchForm"
import ConditionMatchQueryForm from "@/src/components/conditionMatch/ConditionMatchQueryForm"

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

// 多选
const multipleSelection = ref<ConditionMatchListView[]>([])

const handleSelectionChange = (item: ConditionMatchListView[]) => {
    multipleSelection.value = item
}

// 删除
const handleDelete = async (ids: number[]) => {
    const result = await deleteConfirm('该条件匹配')
    if (!result) return

    try {
        await withLoading(api.conditionMatchService.delete)({ids: ids})
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
            <el-button type="danger" :icon="Delete" @click="handleDelete(multipleSelection.map(it => it.id))" v-text="批量删除">
        </div>

        <template v-if="pageData">
            <ConditionMatchTable :rows="pageData.rows">
                <template #operations="{row}">
                    <el-button type="danger" :icon="Delete" @click="removePost([row.id])" v-text="删除"/>                
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
    
    <ConditionMatchForm />
</template>)]
"""