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
import {useUserStore} from "@/stores/userStore"
import {deleteConfirm} from "@/utils/confirm"
import {useLoading} from "@/utils/loading"
import {useLegalPage} from "@/utils/legalPage"
import EntityTable from "@/components/entity/EntityTable.vue"
import EntityAddForm from "@/components/entity/EntityAddForm.vue"
import EntityEditForm from "@/components/entity/EntityEditForm.vue"
import EntityQueryForm from "@/components/entity/EntityQueryForm.vue"

const userStore = useUserStore()

const {isLoading, withLoading} = useLoading()

const pageData = ref<Page<EntityListView>>()

// 分页查询
const queryInfo = ref<PageQuery<EntitySpec>>({
    spec: {},
    pageIndex: 0,
    pageSize: 5
})

const {queryPage, currentPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.entityService.page)
)

const addEntity = withLoading((body: EntityInsertInput) => api.entityService.insert({body}))

const getEntityForUpdate = withLoading((id: number) => api.entityService.getForUpdate({id}))

const editEntity = withLoading((body: EntityUpdateInput) => api.entityService.update({body}))

const deleteEntity = withLoading((ids: Array<number>) => api.entityService.delete({ids}))

// 多选
const selection = ref<EntityListView[]>([])

const handleSelectionChange = (newSelection: Array<EntityListView>): void => {
    selection.value = newSelection
}

// toOneProperty选项
const toOnePropertyIdOptions = ref<Array<ToOneEntityOptionView>>()

const setToOnePropertyIdOptions = withLoading(async () => {
    toOnePropertyIdOptions.value = await api.toOneEntityService.listOptions({body: {}})
})

onBeforeMount(async () => {
    await setToOnePropertyIdOptions()
})

// toOneNullableProperty选项
const toOneNullablePropertyIdOptions = ref<Array<ToOneEntityOptionView>>()

const setToOneNullablePropertyIdOptions = withLoading(async () => {
    toOneNullablePropertyIdOptions.value = await api.toOneEntityService.listOptions({body: {}})
})

onBeforeMount(async () => {
    await setToOneNullablePropertyIdOptions()
})

// 新增
const addDialogVisible = ref<boolean>(false)

const startAdd = (): void => {
    addDialogVisible.value = true
}

const submitAdd = async (insertInput: EntityInsertInput): Promise<void> => {
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

const startEdit = async (id: number): Promise<void> => {
    updateInput.value = await getEntityForUpdate(id)
    if (updateInput.value === undefined) {
        sendMessage('编辑的comment不存在', 'error')
        return
    }
    editDialogVisible.value = true
}

const submitEdit = async (updateInput: EntityUpdateInput): Promise<void> => {
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
const handleDelete = async (ids: Array<number>): Promise<void> => {
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
            v-model="queryInfo.spec"
            v-if="
                toOnePropertyIdOptions &&
                toOneNullablePropertyIdOptions
            "
            :toOnePropertyIdOptions="toOnePropertyIdOptions"
            :toOneNullablePropertyIdOptions="toOneNullablePropertyIdOptions"
            @query="queryPage"
        />

        <div class="page-operations">
            <el-button
                v-if="
                    userStore.permissions.includes('entity:insert')
                "
                type="primary"
                :icon="Plus"
                @click="startAdd"
            >
                新增
            </el-button>
            <el-button
                v-if="
                    userStore.permissions.includes('entity:delete')
                "
                type="danger"
                :icon="Delete"
                :disabled="selection.length === 0"
                @click="handleDelete(selection.map(it => it.id))"
            >
                删除
            </el-button>
        </div>

        <template v-if="pageData">
            <EntityTable
                :rows="pageData.rows"
                @selectionChange="handleSelectionChange"
            >
                <template #operations="{row}">
                    <el-button
                        v-if="
                            userStore.permissions.includes('entity:update')
                        "
                        type="warning"
                        :icon="EditPen"
                        link
                        @click="startEdit(row.id)"
                    >
                        编辑
                    </el-button>
                    <el-button
                        v-if="
                            userStore.permissions.includes('entity:delete')
                        "
                        type="danger"
                        :icon="Delete"
                        link
                        @click="handleDelete([row.id])"
                    >
                        删除
                    </el-button>
                </template>
            </EntityTable>

            <el-pagination
                v-model:current-page="currentPage"
                v-model:page-size="queryInfo.pageSize"
                :total="pageData.totalRowCount"
                :page-sizes="[5, 10, 20]"
                layout="total, sizes, prev, pager, next, jumper"
            />
        </template>
    </el-card>

    <el-dialog
        v-model="addDialogVisible"
        v-if="
            userStore.permissions.includes('entity:insert') &&
            toOnePropertyIdOptions &&
            toOneNullablePropertyIdOptions
        "
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
        v-if="
            userStore.permissions.includes('entity:update') &&
            toOnePropertyIdOptions &&
            toOneNullablePropertyIdOptions
        "
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
            generator.stringify(generator.Page(testEntity)).trim()
        )
    }
}
