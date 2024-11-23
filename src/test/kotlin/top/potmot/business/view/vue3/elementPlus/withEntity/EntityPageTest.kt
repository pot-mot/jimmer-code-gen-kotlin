package top.potmot.business.view.vue3.elementPlus.withEntity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.business.testEntity

class EntityPageTest {
    private val generator = Vue3ElementPlusViewGenerator

    @Test
    fun testPage() {
        assertEquals(
            """
<script setup lang="ts">
import {ref, onBeforeMount} from "vue"
import type {Ref} from "vue"
import {Plus, EditPen, Delete} from "@element-plus/icons-vue"
import type {
    Page,
    PageQuery,
    EntityListView,
    EntityInsertInput,
    EntityUpdateInput,
    EntitySpec,
    ToOneEntityOptionView
} from "@/api/__generated/model/static"
import {api} from "@/api"
import {sendMessage} from "@/utils/message"
import {deleteConfirm} from "@/utils/confirm"
import {useLoading} from "@/utils/loading"
import {useLegalPage} from "@/utils/legalPage"
import EntityTable from "@/components/entity/EntityTable.vue"
import EntityAddForm from "@/components/entity/EntityAddForm.vue"
import EntityEditForm from "@/components/entity/EntityEditForm.vue"
import EntityQueryForm from "@/components/entity/EntityQueryForm.vue"

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

const getEntity = withLoading((id: number) => api.entityService.get({id}))

const addEntity = withLoading((body: EntityInsertInput) => api.entityService.insert({body}))

const editEntity = withLoading((body: EntityUpdateInput) => api.entityService.update({body}))

const deleteEntity = withLoading((ids: Array<number>) => api.entityService.delete({ids}))

// 多选
const selection = ref<EntityListView[]>([])

const handleSelectionChange = (newSelection: Array<EntityListView>): void => {
    selection.value = newSelection
}

// toOneProperty选项
const toOnePropertyIdOptions = ref<Array<ToOneEntityOptionView>>()
onBeforeMount(async () => {
    toOnePropertyIdOptions.value = await api.entityService.listOptions({body: {}})
})

// toOneNullableProperty选项
const toOneNullablePropertyIdOptions = ref<Array<ToOneEntityOptionView>>()
onBeforeMount(async () => {
    toOneNullablePropertyIdOptions.value = await api.entityService.listOptions({body: {}})
})

// 新增
const addDialogVisible = ref<boolean>(false)

const startAdd = (): void => {
    addDialogVisible.value = true
}

const submitAdd = (insertInput: EntityInsertInput): void => {
    try {
        await addEntity(insertInput)
        await queryPage()
        addDialogVisible.value = false

        sendMessage('新增comment成功', 'success')
    } catch (e) {
        sendMessage("新增comment失败", "error", e)
    }
}

const cancelAdd = (): void => {
    addDialogVisible.value = false
}

// 修改
const editDialogVisible = ref(false)

const updateInput = ref<EntityUpdateInput | undefined>(undefined)

const startEdit = (id: number): void => {
    updateInput.value = await getEntity(id)
    if (updateInput.value === undefined) {
        sendMessage('编辑的comment不存在', 'error')
        return
    }
    editDialogVisible.value = true
}

const submitEdit = (updateInput: EntityUpdateInput): void => {
    try {
        await editEntity(updateInput)
        await queryPage()
        editDialogVisible.value = false

        sendMessage('编辑comment成功', 'success')
    } catch (e) {
        sendMessage('编辑comment失败', 'error', e)
    }
}

const cancelEdit = (): void => {
    editDialogVisible.value = false
    updateInput.value = undefined
}

// 删除
const handleDelete = (ids: number[]): void => {
    const result = await deleteConfirm('comment')
    if (!result) return

    try {
        await deleteEntity(ids)
        await queryPage()

        sendMessage('删除comment成功', 'success')
    } catch (e) {
        sendMessage('删除comment失败', 'error', e)
    }
}
</script>

<template>
    <el-card v-loading="isLoading">
        <EntityQueryForm
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

        <EntityTable
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
        </EntityTable>

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
        v-if="toOnePropertyIdOptions && toOneNullablePropertyIdOptions"
        destroy-on-close
        :close-on-click-modal="false"
    >
        <EntityAddForm
            :toOnePropertyIdOptions="toOnePropertyIdOptions"
            :toOneNullablePropertyIdOptions="toOneNullablePropertyIdOptions"
            :submitLoading="isLoading"
            @submit="submitAdd"
            @cancel="cancelAdd"
        />
    </el-dialog>

    <el-dialog
        v-model="editDialogVisible"
        v-if="toOnePropertyIdOptions && toOneNullablePropertyIdOptions"
        destroy-on-close
        :close-on-click-modal="false"
    >
        <EntityEditForm
            v-if="updateInput !== undefined"
            v-model="updateInput"
            :toOnePropertyIdOptions="toOnePropertyIdOptions"
            :toOneNullablePropertyIdOptions="toOneNullablePropertyIdOptions"
            :submitLoading="isLoading"
            @submit="submitEdit"
            @cancel="cancelEdit"
        />
    </el-dialog>
</template>
            """.trimIndent(),
            generator.stringifyPage(testEntity).trim()
        )
    }
}
