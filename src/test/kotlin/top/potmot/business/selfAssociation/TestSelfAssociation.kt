package top.potmot.business.selfAssociation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.dto.generate.DtoGenerator
import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.service.generate.impl.java.JavaServiceGenerator
import top.potmot.core.business.service.generate.impl.kotlin.KotlinServiceGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.string.trimBlankLine

class TestSelfAssociation {
    private val index = "${'$'}index"

    private val EntityBusiness.result
        get() = DtoGenerator.generateDto(this).let { it.path to it.content }.toString()

    @Test
    fun `test selfAssociation dto`() {
        assertEquals(
            """
(dto/SelfAssociationEntity.dto, export EntityPackage.SelfAssociationEntity

SelfAssociationEntityListView {
    #allScalars
    parent {
        label
    }
}

SelfAssociationEntityTreeView {
    #allScalars
    id(parent)
    children*
}

SelfAssociationEntityDetailView {
    #allScalars
    parent {
        label
    }
    children {
        label
    }
}

SelfAssociationEntityOptionView {
    id(parent)
    id
    label
}

input SelfAssociationEntityInsertInput {
    #allScalars
    -id
    id(parent)
}

SelfAssociationEntityUpdateFillView {
    #allScalars
    id(parent)
}

input SelfAssociationEntityUpdateInput {
    #allScalars
    id!
    id(parent)
}

specification SelfAssociationEntitySpec {
    eq(id)
    like/i(label)
    associatedIdEq(parent)
    associatedIdIn(children) as childIds
})
            """.trimIndent(),
            selfAssociationEntity.result
        )
    }

    @Test
    fun `test selfAssociation treeSelect`() {
        assertEquals(
            """
[(components/selfAssociationEntity/SelfAssociationEntityIdSelect.vue, <script setup lang="ts">
import {computed, watch} from "vue"
import type {ElTreeSelect} from "element-plus"
import type {SelfAssociationEntityOptionView} from "@/api/__generated/model/static"

const modelValue = defineModel<number | undefined>({
    required: true
})

const props = withDefaults(defineProps<{
    options: Array<SelfAssociationEntityOptionView>,
    excludeIds?: Array<number> | undefined
}>(), {
    excludeIds() {
        return []
    }
})

watch(() => [modelValue.value, props.options], () => {
    if (!(props.options.map(it => it.id) as Array<number | undefined>).includes(modelValue.value)) {
        modelValue.value = undefined
    }
}, {immediate: true})

type TreeNode = {
    id: number,
    label: string,
    disabled: boolean,
    children: Array<TreeNode>
}

const treeOptions = computed<Array<TreeNode>>(() => {
    const items = props.options
    const idNodeMap = new Map<number, TreeNode>
    const roots: Array<TreeNode> = []

    for (const item of items) {
        const node: TreeNode = {
            id: item.id,
            label: item.label,
            disabled: props.excludeIds.includes(item.id),
            children: []
        }
        idNodeMap.set(item.id, node)
    }

    for (const item of items) {
        const node = idNodeMap.get(item.id)
        if (!node) continue

        const parentNode = item.parentId ? idNodeMap.get(item.parentId) : undefined
        if (parentNode) {
            if (parentNode.disabled) {
                node.disabled = true
            }
            parentNode.children.push(node);
        } else {
            roots.push(node);
        }
    }

    return roots
})

const filterNodeMethod = (value: string | undefined, data: TreeNode) => {
    return !value || data.label.includes(value)
}
</script>

<template>
    <el-tree-select
        v-model="modelValue"
        placeholder="请选择树节点"
        :data="treeOptions"
        :props="{label: 'label', children: 'children'}"
        node-key="id"
        filterable
        :filter-node-method="filterNodeMethod"
        clearable
        :value-on-clear="undefined"
        check-strictly
    />
</template>
), (components/selfAssociationEntity/SelfAssociationEntityIdMultiSelect.vue, <script setup lang="ts">
import {computed, watch} from "vue"
import type {ElTreeSelect} from "element-plus"
import type {SelfAssociationEntityOptionView} from "@/api/__generated/model/static"

const modelValue = defineModel<Array<number>>({
    required: true
})

const props = withDefaults(defineProps<{
    options: Array<SelfAssociationEntityOptionView>,
    excludeIds?: Array<number> | undefined
}>(), {
    excludeIds() {
        return []
    }
})

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

type TreeNode = {
    id: number,
    label: string,
    disabled: boolean,
    children: Array<TreeNode>
}

const treeOptions = computed<Array<TreeNode>>(() => {
    const items = props.options
    const idNodeMap = new Map<number, TreeNode>
    const roots: Array<TreeNode> = []

    for (const item of items) {
        const node: TreeNode = {
            id: item.id,
            label: item.label,
            disabled: props.excludeIds.includes(item.id),
            children: []
        }
        idNodeMap.set(item.id, node)
    }

    for (const item of items) {
        const node = idNodeMap.get(item.id)
        if (!node) continue

        const parentNode = item.parentId ? idNodeMap.get(item.parentId) : undefined
        if (parentNode) {
            if (parentNode.disabled) {
                node.disabled = true
            }
            parentNode.children.push(node);
        } else {
            roots.push(node);
        }
    }

    return roots
})

const filterNodeMethod = (value: string | undefined, data: TreeNode) => {
    return !value || data.label.includes(value)
}
</script>

<template>
    <el-tree-select
        v-model="modelValue"
        placeholder="请选择树节点"
        :data="treeOptions"
        :props="{label: 'label', children: 'children'}"
        node-key="id"
        filterable
        :filter-node-method="filterNodeMethod"
        clearable
        :value-on-clear="undefined"
        multiple
        check-strictly
    />
</template>
)]
            """.trimIndent().trimBlankLine(),
            Vue3ElementPlusViewGenerator.generateView(listOf(selfAssociationEntity))
                .filter { GenerateTag.IdSelect in it.tags || GenerateTag.IdMultiSelect in it.tags }
                .map { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test selfAssociation page`() {
        assertEquals(
            """
[(pages/selfAssociationEntity/SelfAssociationEntityPage.vue, <script setup lang="ts">
import {ref, onBeforeMount} from "vue"
import {Plus, EditPen, Delete} from "@element-plus/icons-vue"
import type {
    Page,
    PageQuery,
    SelfAssociationEntityTreeView,
    SelfAssociationEntityInsertInput,
    SelfAssociationEntityUpdateInput,
    SelfAssociationEntitySpec,
    SelfAssociationEntityOptionView
} from "@/api/__generated/model/static"
import {api} from "@/api"
import {sendMessage} from "@/utils/message"
import {useUserStore} from "@/stores/userStore"
import {deleteConfirm} from "@/utils/confirm"
import {useLoading} from "@/utils/loading"
import {useLegalPage} from "@/utils/legalPage"
import SelfAssociationEntityTable from "@/components/selfAssociationEntity/SelfAssociationEntityTable.vue"
import SelfAssociationEntityAddForm from "@/components/selfAssociationEntity/SelfAssociationEntityAddForm.vue"
import SelfAssociationEntityEditForm from "@/components/selfAssociationEntity/SelfAssociationEntityEditForm.vue"
import SelfAssociationEntityQueryForm from "@/components/selfAssociationEntity/SelfAssociationEntityQueryForm.vue"

const userStore = useUserStore()

const {isLoading, withLoading} = useLoading()

const pageData = ref<Page<SelfAssociationEntityTreeView>>()

// 分页查询
const queryInfo = ref<PageQuery<SelfAssociationEntitySpec>>({
    spec: {},
    pageIndex: 0,
    pageSize: 5
})

const {queryPage, currentPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.selfAssociationEntityService.treePage)
)

const addSelfAssociationEntity = withLoading(async (body: SelfAssociationEntityInsertInput) => {
    const result = await api.selfAssociationEntityService.insert({body})
    await setParentIdOptions()
    return result
})

const getSelfAssociationEntityForUpdate = withLoading((id: number) => api.selfAssociationEntityService.getForUpdate({id}))

const editSelfAssociationEntity = withLoading(async (body: SelfAssociationEntityUpdateInput) => {
    const result = await api.selfAssociationEntityService.update({body})
    await setParentIdOptions()
    return result
})

const deleteSelfAssociationEntity = withLoading(async (ids: Array<number>) => {
    const result = await api.selfAssociationEntityService.delete({ids})
    await setParentIdOptions()
    return result
})

// 多选
const selection = ref<SelfAssociationEntityTreeView[]>([])

const handleSelectionChange = (
    newSelection: Array<SelfAssociationEntityTreeView>
): void => {
    selection.value = newSelection
}

// 父节点选项
const parentIdOptions = ref<Array<SelfAssociationEntityOptionView>>()

const setParentIdOptions = withLoading(async () => {
    parentIdOptions.value = await api.selfAssociationEntityService.listOptions({body: {}})
})

onBeforeMount(async () => {
    await setParentIdOptions()
})

// 新增
const addDialogVisible = ref<boolean>(false)

const startAdd = (): void => {
    addDialogVisible.value = true
}

const submitAdd = async (
    insertInput: SelfAssociationEntityInsertInput
): Promise<void> => {
    try {
        await addSelfAssociationEntity(insertInput)
        await queryPage()
        addDialogVisible.value = false

        sendMessage('新增树节点成功', 'success')
    } catch (e) {
        sendMessage("新增树节点失败", "error", e)
    }
}

const cancelAdd = (): void => {
    addDialogVisible.value = false
}

// 修改
const editDialogVisible = ref(false)

const updateInput = ref<SelfAssociationEntityUpdateInput | undefined>(undefined)

const startEdit = async (id: number): Promise<void> => {
    updateInput.value = await getSelfAssociationEntityForUpdate(id)
    if (updateInput.value === undefined) {
        sendMessage('编辑的树节点不存在', 'error')
        return
    }
    editDialogVisible.value = true
}

const submitEdit = async (
    updateInput: SelfAssociationEntityUpdateInput
): Promise<void> => {
    try {
        await editSelfAssociationEntity(updateInput)
        await queryPage()
        editDialogVisible.value = false

        sendMessage('编辑树节点成功', 'success')
    } catch (e) {
        sendMessage('编辑树节点失败', 'error', e)
    }
}

const cancelEdit = (): void => {
    editDialogVisible.value = false
    updateInput.value = undefined
}

// 删除
const handleDelete = async (ids: Array<number>): Promise<void> => {
    const result = await deleteConfirm('树节点')
    if (!result) return

    try {
        await deleteSelfAssociationEntity(ids)
        await queryPage()

        sendMessage('删除树节点成功', 'success')
    } catch (e) {
        sendMessage('删除树节点失败', 'error', e)
    }
}
</script>

<template>
    <el-card v-loading="isLoading">
        <SelfAssociationEntityQueryForm
            v-model="queryInfo.spec"
            v-if="parentIdOptions"
            :parentIdOptions="parentIdOptions"
            @query="queryPage"
        />

        <div class="page-operations">
            <el-button
                v-if="
                    userStore.permissions.includes('selfAssociationEntity:insert')
                "
                type="primary"
                :icon="Plus"
                @click="startAdd"
            >
                新增
            </el-button>
            <el-button
                v-if="
                    userStore.permissions.includes('selfAssociationEntity:delete')
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
            <SelfAssociationEntityTable
                :rows="pageData.rows"
                @selectionChange="handleSelectionChange"
            >
                <template #operations="{row}">
                    <el-button
                        v-if="
                            userStore.permissions.includes('selfAssociationEntity:update')
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
                            userStore.permissions.includes('selfAssociationEntity:delete')
                        "
                        type="danger"
                        :icon="Delete"
                        link
                        @click="handleDelete([row.id])"
                    >
                        删除
                    </el-button>
                </template>
            </SelfAssociationEntityTable>

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
            userStore.permissions.includes('selfAssociationEntity:insert') &&
            parentIdOptions
        "
        destroy-on-close
        :close-on-click-modal="false"
    >
        <SelfAssociationEntityAddForm
            :parentIdOptions="parentIdOptions"
            :submitLoading="isLoading"
            @submit="submitAdd"
            @cancel="cancelAdd"
        />
    </el-dialog>

    <el-dialog
        v-model="editDialogVisible"
        v-if="
            userStore.permissions.includes('selfAssociationEntity:update') &&
            parentIdOptions
        "
        destroy-on-close
        :close-on-click-modal="false"
    >
        <SelfAssociationEntityEditForm
            v-if="updateInput !== undefined"
            v-model="updateInput"
            :parentIdOptions="parentIdOptions"
            :submitLoading="isLoading"
            @submit="submitEdit"
            @cancel="cancelEdit"
        />
    </el-dialog>
</template>
)]
            """.trimIndent().trimBlankLine(),
            Vue3ElementPlusViewGenerator.generateView(listOf(selfAssociationEntity))
                .filter { GenerateTag.Page in it.tags }
                .map { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test selfAssociation table`() {
        assertEquals(
            """
[(components/selfAssociationEntity/SelfAssociationEntityTable.vue, <script setup lang="ts">
import type {SelfAssociationEntityTreeView} from "@/api/__generated/model/static"
import {usePageSizeStore} from "@/stores/pageSizeStore"

withDefaults(defineProps<{
    rows: Array<SelfAssociationEntityTreeView>,
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined
}>(), {
    idColumn: false,
    indexColumn: false,
    multiSelect: true,
})

const emits = defineEmits<{
    (
        event: "selectionChange",
        selection: Array<SelfAssociationEntityTreeView>
    ): void
}>()

defineSlots<{
    operations(props: {
        row: SelfAssociationEntityTreeView,
        index: number
    }): any
}>()

const pageSizeStore = usePageSizeStore()

const handleSelectionChange = (
    newSelection: Array<SelfAssociationEntityTreeView>
): void => {
    emits("selectionChange", newSelection)
}
</script>

<template>
    <el-table
        :data="rows"
        row-key="id"
        border
        :tree-props="{children: 'children'}"
        @selection-change="handleSelectionChange"
    >
        <el-table-column
            v-if="idColumn"
            prop="id"
            label="ID"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            v-if="indexColumn"
            type="index"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            v-if="multiSelect"
            type="selection"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column prop="label" label="标签"/>
        <el-table-column
            label="操作"
            :fixed="pageSizeStore.isSmall ? undefined : 'right'"
        >
            <template #default="scope">
                <slot
                    name="operations"
                    :row="scope.row as SelfAssociationEntityTreeView"
                    :index="scope.$index"
                />
            </template>
        </el-table-column>
    </el-table>
</template>
)]
            """.trimBlankLine(),
            Vue3ElementPlusViewGenerator.generateView(listOf(selfAssociationEntity))
                .filter { GenerateTag.Table in it.tags }
                .map { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test selfAssociation addForm`() {
        assertEquals(
            """
[(components/selfAssociationEntity/SelfAssociationEntityAddForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {
    SelfAssociationEntityInsertInput,
    SelfAssociationEntityOptionView
} from "@/api/__generated/model/static"
import type {SelfAssociationEntityAddFormType} from "@/components/selfAssociationEntity/SelfAssociationEntityAddFormType"
import {createDefaultSelfAssociationEntity} from "@/components/selfAssociationEntity/createDefaultSelfAssociationEntity"
import {useRules} from "@/rules/selfAssociationEntity/SelfAssociationEntityAddFormRules"
import SelfAssociationEntityIdSelect from "@/components/selfAssociationEntity/SelfAssociationEntityIdSelect.vue"

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined,
    parentIdOptions: Array<SelfAssociationEntityOptionView>
}>(), {
    withOperations: true,
    submitLoading: false,
})

const emits = defineEmits<{
    (
        event: "submit",
        formData: SelfAssociationEntityInsertInput
    ): void,
    (event: "cancel"): void
}>()

defineSlots<{
    operations(props: {
        handleSubmit: () => Promise<void>,
        handleCancel: () => void
    }): any
}>()

const formData = ref<SelfAssociationEntityAddFormType>(createDefaultSelfAssociationEntity())

const formRef = ref<FormInstance>()
const rules = useRules(formData)

// 校验
const handleValidate = async (): Promise<boolean> => {
    return await formRef.value?.validate().catch(() => false) ?? false
}

// 提交
const handleSubmit = async (): Promise<void> => {
    if (props.submitLoading) return

    const validResult = await handleValidate()
    if (validResult) {
        emits("submit", formData.value as SelfAssociationEntityInsertInput)
    }
}

// 取消
const handleCancel = (): void => {
    emits("cancel")
}

defineExpose<FormExpose>({
    validate: handleValidate
})
</script>

<template>
    <el-form
        :model="formData"
        ref="formRef"
        :rules="rules"
        @submit.prevent
    >
        <el-form-item prop="label" label="标签">
            <el-input
                v-model="formData.label"
                placeholder="请输入标签"
                clearable
            />
        </el-form-item>
        <el-form-item prop="parentId" label="父节点">
            <SelfAssociationEntityIdSelect
                v-model="formData.parentId"
                :options="parentIdOptions"
            />
        </el-form-item>

        <slot
            v-if="withOperations"
            name="operations"
            :handleSubmit="handleSubmit"
            :handleCancel="handleCancel"
        >
            <div class="form-operations">
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
), (rules/selfAssociationEntity/SelfAssociationEntityAddFormRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {SelfAssociationEntityAddFormType} from "@/components/selfAssociationEntity/SelfAssociationEntityAddFormType"
import type {SelfAssociationEntityInsertInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<SelfAssociationEntityAddFormType>): FormRules<SelfAssociationEntityInsertInput> => {
    return {
        label: [
            {required: true, message: "标签不能为空", trigger: "blur"},
        ],
        parentId: [
        ],
    }
})]
            """.trimIndent().trimBlankLine(),
            Vue3ElementPlusViewGenerator.generateView(listOf(selfAssociationEntity))
                .filter { GenerateTag.AddForm in it.tags }
                .map { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test selfAssociation editForm`() {
        assertEquals(
            """
[(components/selfAssociationEntity/SelfAssociationEntityEditForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {
    SelfAssociationEntityUpdateInput,
    SelfAssociationEntityOptionView
} from "@/api/__generated/model/static"
import {useRules} from "@/rules/selfAssociationEntity/SelfAssociationEntityEditFormRules"
import SelfAssociationEntityIdSelect from "@/components/selfAssociationEntity/SelfAssociationEntityIdSelect.vue"

const formData = defineModel<SelfAssociationEntityUpdateInput>({
    required: true
})

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined,
    parentIdOptions: Array<SelfAssociationEntityOptionView>
}>(), {
    withOperations: true,
    submitLoading: false,
})

const emits = defineEmits<{
    (
        event: "submit",
        formData: SelfAssociationEntityUpdateInput
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

// 校验
const handleValidate = async (): Promise<boolean> => {
    return await formRef.value?.validate().catch(() => false) ?? false
}

// 提交
const handleSubmit = async (): Promise<void> => {
    if (props.submitLoading) return

    const validResult = await handleValidate()
    if (validResult) {
        emits("submit", formData.value)
    }
}

// 取消
const handleCancel = (): void => {
    emits("cancel")
}

defineExpose<FormExpose>({
    validate: handleValidate
})
</script>

<template>
    <el-form
        :model="formData"
        ref="formRef"
        :rules="rules"
        @submit.prevent
    >
        <el-form-item prop="label" label="标签">
            <el-input
                v-model="formData.label"
                placeholder="请输入标签"
                clearable
            />
        </el-form-item>
        <el-form-item prop="parentId" label="父节点">
            <SelfAssociationEntityIdSelect
                v-model="formData.parentId"
                :options="parentIdOptions"
                :exclude-ids="[formData.id]"
            />
        </el-form-item>

        <slot
            v-if="withOperations"
            name="operations"
            :handleSubmit="handleSubmit"
            :handleCancel="handleCancel"
        >
            <div class="form-operations">
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
), (rules/selfAssociationEntity/SelfAssociationEntityEditFormRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {SelfAssociationEntityUpdateInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<SelfAssociationEntityUpdateInput>): FormRules<SelfAssociationEntityUpdateInput> => {
    return {
        label: [
            {required: true, message: "标签不能为空", trigger: "blur"},
        ],
        parentId: [
        ],
    }
})]
            """.trimBlankLine(),
            Vue3ElementPlusViewGenerator.generateView(listOf(selfAssociationEntity))
                .filter { GenerateTag.EditForm in it.tags }
                .map { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test selfAssociation editTable`() {
        assertEquals(
            """
[(components/selfAssociationEntity/SelfAssociationEntityEditTable.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {
    SelfAssociationEntityAddFormType,
    SelfAssociationEntityOptionView
} from "@/api/__generated/model/static"
import {createDefaultSelfAssociationEntity} from "@/components/selfAssociationEntity/createDefaultSelfAssociationEntity"
import {useRules} from "@/rules/selfAssociationEntity/SelfAssociationEntityEditTableRules"
import {usePageSizeStore} from "@/stores/pageSizeStore"
import {Plus, Delete} from "@element-plus/icons-vue"
import {deleteConfirm} from "@/utils/confirm"
import SelfAssociationEntityIdSelect from "@/components/selfAssociationEntity/SelfAssociationEntityIdSelect.vue"

const rows = defineModel<Array<SelfAssociationEntityAddFormType>>({
    required: true
})

const props = withDefaults(defineProps<{
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined,
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined,
    parentIdOptions: Array<SelfAssociationEntityOptionView>
}>(), {
    idColumn: false,
    indexColumn: false,
    multiSelect: true,
    withOperations: false,
    submitLoading: false,
})

const emits = defineEmits<{
    (
        event: "submit",
        rows: Array<SelfAssociationEntityAddFormType>
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

const pageSizeStore = usePageSizeStore()

// 校验
const handleValidate = async (): Promise<boolean> => {
    return await formRef.value?.validate().catch(() => false) ?? false
}

// 提交
const handleSubmit = async (): Promise<void> => {
    if (props.submitLoading) return

    const validResult = await handleValidate()
    if (validResult) {
        emits("submit", rows.value)
    }
}

// 取消
const handleCancel = (): void => {
    emits("cancel")
}

// 多选
const selection = ref<Array<SelfAssociationEntityAddFormType>>([])

const handleSelectionChange = (
    newSelection: Array<SelfAssociationEntityAddFormType>
): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    rows.value.push(createDefaultSelfAssociationEntity())
}

// 删除
const handleBatchDelete = async (): Promise<void> => {
    const result = await deleteConfirm("这些树节点")
    if (!result) return
    rows.value = rows.value.filter(it => !selection.value.includes(it))
}

const handleSingleDelete = async (index: number): Promise<void> => {
    const result = await deleteConfirm("该树节点")
    if (!result) return
    rows.value = rows.value.filter((_, i) => i !== index)
}

defineExpose<FormExpose>({
    validate: handleValidate
})
</script>

<template>
    <el-form
        :model="rows"
        ref="formRef"
        :rules="rules"
        @submit.prevent
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
                :fixed="pageSizeStore.isSmall ? undefined : 'left'"
            />
            <el-table-column
                v-if="indexColumn"
                type="index"
                :fixed="pageSizeStore.isSmall ? undefined : 'left'"
            />
            <el-table-column
                v-if="multiSelect"
                type="selection"
                :fixed="pageSizeStore.isSmall ? undefined : 'left'"
            />
            <el-table-column prop="label" label="标签">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'label']"
                        label="标签"
                        :rule="rules.label"
                    >
                        <el-input
                            v-model="scope.row.label"
                            placeholder="请输入标签"
                            clearable
                        />
                    </el-form-item>
                </template>
            </el-table-column>
            <el-table-column prop="parentId" label="父节点">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'parentId']"
                        label="父节点"
                        :rule="rules.parentId"
                    >
                        <SelfAssociationEntityIdSelect
                            v-model="scope.row.parentId"
                            :options="parentIdOptions"
                            :exclude-ids="[scope.row.id]"
                        />
                    </el-form-item>
                </template>
            </el-table-column>
            <el-table-column
                label="操作"
                :fixed="pageSizeStore.isSmall ? undefined : 'right'"
            >
                <template #default="scope">
                    <el-button
                        type="danger"
                        :icon="Delete"
                        link
                        @click="handleSingleDelete(scope.$index)"
                    />
                </template>
            </el-table-column>
        </el-table>

        <slot
            v-if="withOperations"
            name="operations"
            :handleSubmit="handleSubmit"
            :handleCancel="handleCancel"
        >
            <div class="form-operations">
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
), (rules/selfAssociationEntity/SelfAssociationEntityEditTableRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {SelfAssociationEntityUpdateInput} from "@/api/__generated/model/static"

export const useRules = (
    _: Ref<Array<SelfAssociationEntityUpdateInput>>
): FormRules<SelfAssociationEntityUpdateInput> => {
    return {
        label: [
            {required: true, message: "标签不能为空", trigger: "blur"},
        ],
        parentId: [
        ],
    }
})]
            """.trimBlankLine(),
            Vue3ElementPlusViewGenerator.generateView(listOf(selfAssociationEntity))
                .filter { GenerateTag.EditTable in it.tags }
                .map { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test kotlin service`() {
        assertEquals(
            """
package EntityPackage

import EntityPackage.AuthorizeException
import EntityPackage.SelfAssociationEntity
import EntityPackage.dto.SelfAssociationEntityDetailView
import EntityPackage.dto.SelfAssociationEntityInsertInput
import EntityPackage.dto.SelfAssociationEntityListView
import EntityPackage.dto.SelfAssociationEntityOptionView
import EntityPackage.dto.SelfAssociationEntitySpec
import EntityPackage.dto.SelfAssociationEntityTreeView
import EntityPackage.dto.SelfAssociationEntityUpdateFillView
import EntityPackage.dto.SelfAssociationEntityUpdateInput
import EntityPackage.id
import EntityPackage.parentId
import EntityPackage.query.PageQuery
import cn.dev33.satoken.annotation.SaCheckPermission
import org.babyfish.jimmer.Page
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.ast.tuple.Tuple2
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/selfAssociationEntity")
class SelfAssociationEntityService(
    private val sqlClient: KSqlClient
) {
    /**
     * 根据ID获取树节点。
     *
     * @param id 树节点的ID。
     * @return 树节点的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("selfAssociationEntity:get")
    @Throws(AuthorizeException::class)
    fun get(@PathVariable id: Int): SelfAssociationEntityDetailView? =
        sqlClient.findById(SelfAssociationEntityDetailView::class, id)

    /**
     * 根据提供的查询参数列出树节点。
     *
     * @param spec 查询参数。
     * @return 树节点列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("selfAssociationEntity:list")
    @Throws(AuthorizeException::class)
    fun list(@RequestBody spec: SelfAssociationEntitySpec): List<SelfAssociationEntityListView> =
        sqlClient.executeQuery(SelfAssociationEntity::class) {
            where(spec)
            select(table.fetch(SelfAssociationEntityListView::class))
        }

    /**
     * 根据提供的查询参数分页查询树节点。
     *
     * @param query 分页查询参数。
     * @return 树节点分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("selfAssociationEntity:list")
    @Throws(AuthorizeException::class)
    fun page(@RequestBody query: PageQuery<SelfAssociationEntitySpec>): Page<SelfAssociationEntityListView> =
        sqlClient.createQuery(SelfAssociationEntity::class) {
            where(query.spec)
            select(table.fetch(SelfAssociationEntityListView::class))
        }.fetchPage(query.pageIndex, query.pageSize)

    private fun buildTree(
        list: List<SelfAssociationEntityTreeView>,
    ): List<SelfAssociationEntityTreeView> {
        val idMap = list.associateBy { it.id }
        val parentMap = list.groupBy { it.parentId }

        fun buildSubTree(node: SelfAssociationEntityTreeView): SelfAssociationEntityTreeView {
            val children = parentMap[node.id]?.map { buildSubTree(it) } ?: emptyList()
            return node.copy(children = children)
        }

        val roots = list
            .filter { it.parentId == null || it.parentId !in idMap }
            .map { buildSubTree(it) }

        return roots
    }

    /**
     * 根据提供的查询参数列出树形的树节点。
     *
     * @param spec 查询参数。
     * @return 树节点列表数据。
     */
    @PostMapping("/tree")
    @SaCheckPermission("selfAssociationEntity:list")
    @Throws(AuthorizeException::class)
    fun tree(
        @RequestBody spec: SelfAssociationEntitySpec,
    ): List<SelfAssociationEntityTreeView> =
        sqlClient.executeQuery(SelfAssociationEntity::class) {
            where(spec)
            select(table.fetch(SelfAssociationEntityTreeView.METADATA.fetcher.remove("children")))
        }
            .map { SelfAssociationEntityTreeView(it) }
            .let { buildTree(it) }

    private fun buildIdTree(
        idParentIdList: List<Tuple2<Int, Int?>>,
    ): List<SelfAssociationEntity> {
        val idMap = idParentIdList.associateBy { it._1 }
        val parentMap = idParentIdList.groupBy { it._2 }

        fun buildSubTree(node: Tuple2<Int, Int?>): SelfAssociationEntity {
            return SelfAssociationEntity {
                id = node._1
                children = parentMap[node._1]?.map { buildSubTree(it) } ?: emptyList()
            }
        }

        val roots = idParentIdList
            .filter { it._2 == null || it._2 !in idMap }
            .map { buildSubTree(it) }

        return roots
    }

    private fun flatIds(
        list: List<SelfAssociationEntity>
    ): List<Int> {
        val result = mutableListOf<Int>()
        result += list.map { it.id } + list.flatMap { flatIds(it.children) }
        return result
    }

    /**
     * 根据提供的查询参数分页查询树形的树节点。
     *
     * @param query 分页查询参数。
     * @return 树节点树状分页数据。
     */
    @PostMapping("/tree/page")
    @SaCheckPermission("selfAssociationEntity:list")
    @Throws(AuthorizeException::class)
    fun treePage(
        @RequestBody query: PageQuery<SelfAssociationEntitySpec>
    ): Page<SelfAssociationEntityTreeView> {
        val list = sqlClient.executeQuery(SelfAssociationEntity::class) {
            where(query.spec)
            select(table.id, table.parentId)
        }
            .let { buildIdTree(it) }

        val startIndex = minOf(query.pageIndex * query.pageSize, list.size)
        val endIndex = minOf((query.pageIndex + 1) * query.pageSize, list.size)

        val idList = list
            .subList(startIndex, endIndex)
            .let { flatIds(it) }

        return sqlClient.executeQuery(SelfAssociationEntity::class) {
            where(table.id valueIn idList)
            select(table.fetch(SelfAssociationEntityTreeView.METADATA.fetcher.remove("children")))
        }
            .map { SelfAssociationEntityTreeView(it) }
            .let { buildTree(it) }
            .let { Page(it, list.size.toLong(), (list.size / query.pageSize).toLong()) }
    }

    /**
     * 根据提供的查询参数列出树节点选项。
     *
     * @param spec 查询参数。
     * @return 树节点列表数据。
     */
    @PostMapping("/list/options")
    @SaCheckPermission("selfAssociationEntity:select")
    @Throws(AuthorizeException::class)
    fun listOptions(@RequestBody spec: SelfAssociationEntitySpec): List<SelfAssociationEntityOptionView> =
        sqlClient.executeQuery(SelfAssociationEntity::class) {
            where(spec)
            select(table.fetch(SelfAssociationEntityOptionView::class))
        }

    /**
     * 插入新的树节点。
     *
     * @param input 树节点插入输入对象。
     * @return 插入的树节点的ID。
     */
    @PostMapping
    @SaCheckPermission("selfAssociationEntity:insert")
    @Throws(AuthorizeException::class)
    @Transactional
    fun insert(@RequestBody input: SelfAssociationEntityInsertInput): Int =
        sqlClient.insert(input).modifiedEntity.id

    /**
     * 根据ID获取树节点的更新回填信息。
     *
     * @param id 树节点的ID。
     * @return 树节点的更新回填信息。
     */
    @GetMapping("/{id}/forUpdate")
    @SaCheckPermission("selfAssociationEntity:update")
    @Throws(AuthorizeException::class)
    fun getForUpdate(@PathVariable id: Int): SelfAssociationEntityUpdateFillView? =
        sqlClient.findById(SelfAssociationEntityUpdateFillView::class, id)

    /**
     * 更新树节点。
     *
     * @param input 树节点更新输入对象。
     * @return 更新的树节点的ID。
     */
    @PutMapping
    @SaCheckPermission("selfAssociationEntity:update")
    @Throws(AuthorizeException::class)
    @Transactional
    fun update(@RequestBody input: SelfAssociationEntityUpdateInput): Int =
        sqlClient.update(input, AssociatedSaveMode.REPLACE).modifiedEntity.id

    /**
     * 删除指定ID的树节点。
     *
     * @param ids 要删除的树节点ID列表。
     * @return 删除的树节点的行数。
     */
    @DeleteMapping
    @SaCheckPermission("selfAssociationEntity:delete")
    @Throws(AuthorizeException::class)
    @Transactional
    fun delete(@RequestParam ids: List<Int>): Int =
        sqlClient.deleteByIds(SelfAssociationEntity::class, ids).affectedRowCount(SelfAssociationEntity::class)
}
            """.trimIndent(),
            KotlinServiceGenerator.generateService(selfAssociationEntity)
                .content
        )
    }


    @Test
    fun `test java service`() {
        assertEquals(
            """
package EntityPackage;

import EntityPackage.AuthorizeException;
import EntityPackage.SelfAssociationEntity;
import EntityPackage.SelfAssociationEntityDraft;
import EntityPackage.Tables;
import EntityPackage.dto.SelfAssociationEntityDetailView;
import EntityPackage.dto.SelfAssociationEntityInsertInput;
import EntityPackage.dto.SelfAssociationEntityListView;
import EntityPackage.dto.SelfAssociationEntityOptionView;
import EntityPackage.dto.SelfAssociationEntitySpec;
import EntityPackage.dto.SelfAssociationEntityTreeView;
import EntityPackage.dto.SelfAssociationEntityUpdateFillView;
import EntityPackage.dto.SelfAssociationEntityUpdateInput;
import EntityPackage.query.PageQuery;
import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/selfAssociationEntity")
public class SelfAssociationEntityService implements Tables {
    private final JSqlClient sqlClient;

    public SelfAssociationEntityService(JSqlClient sqlClient) {
        this.sqlClient = sqlClient;
    }

    /**
     * 根据ID获取树节点。
     *
     * @param id 树节点的ID。
     * @return 树节点的详细信息。
     */
    @GetMapping("/{id}")
    @SaCheckPermission("selfAssociationEntity:get")
    @Nullable
    public SelfAssociationEntityDetailView get(@PathVariable int id) throws AuthorizeException {
        return sqlClient.findById(SelfAssociationEntityDetailView.class, id);
    }

    /**
     * 根据提供的查询参数列出树节点。
     *
     * @param spec 查询参数。
     * @return 树节点列表数据。
     */
    @PostMapping("/list")
    @SaCheckPermission("selfAssociationEntity:list")
    @NotNull
    public List<@NotNull SelfAssociationEntityListView> list(@RequestBody @NotNull SelfAssociationEntitySpec spec) throws AuthorizeException {
        return sqlClient.createQuery(SELF_ASSOCIATION_ENTITY_TABLE)
                .where(spec)
                .select(SELF_ASSOCIATION_ENTITY_TABLE.fetch(SelfAssociationEntityListView.class))
                .execute();
    }

    /**
     * 根据提供的查询参数列出树节点。
     *
     * @param query 分页查询参数。
     * @return 树节点分页数据。
     */
    @PostMapping("/page")
    @SaCheckPermission("selfAssociationEntity:list")
    @NotNull
    public Page<@NotNull SelfAssociationEntityListView> page(@RequestBody @NotNull PageQuery<SelfAssociationEntitySpec> query) throws AuthorizeException {
        return sqlClient.createQuery(SELF_ASSOCIATION_ENTITY_TABLE)
                .where(query.getSpec())
                .select(SELF_ASSOCIATION_ENTITY_TABLE.fetch(SelfAssociationEntityListView.class))
                .fetchPage(query.getPageIndex(), query.getPageSize());
    }

    @NotNull
    private List<@NotNull SelfAssociationEntityTreeView> buildTree(
            @NotNull List<@NotNull SelfAssociationEntityTreeView> list
    ) {
        Map<Integer, SelfAssociationEntityTreeView> map = new HashMap<>();
        for (SelfAssociationEntityTreeView node : list) {
            map.put(node.getId(), node);
        }

        List<@NotNull SelfAssociationEntityTreeView> roots = new ArrayList<>();

        for (SelfAssociationEntityTreeView node : list) {
            SelfAssociationEntityTreeView parent = map.get(node.getParentId());
            if (parent == null) {
                roots.add(node);
            } else {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(node);
            }
        }

        return roots;
    }

    /**
     * 根据提供的查询参数列出树形的树节点。
     *
     * @param spec 查询参数。
     * @return 树节点列表数据。
     */
    @PostMapping("/tree")
    @SaCheckPermission("selfAssociationEntity:list")
    @NotNull
    public List<@NotNull SelfAssociationEntityTreeView> tree(
            @RequestBody @NotNull SelfAssociationEntitySpec spec
    ) throws AuthorizeException {
        List<@NotNull SelfAssociationEntityTreeView> list = sqlClient.createQuery(SELF_ASSOCIATION_ENTITY_TABLE)
                .where(spec)
                .select(SELF_ASSOCIATION_ENTITY_TABLE.fetch(SelfAssociationEntityTreeView.METADATA.getFetcher().remove("children")))
                .execute()
                .stream().map(SelfAssociationEntityTreeView::new).toList();

        return buildTree(list);
    }

    private List<SelfAssociationEntity> buildIdTree(
            List<Tuple2<Integer, Integer>> idParentIdList
    ) {
        Map<Integer, Tuple2<Integer, Integer>> idMap = new HashMap<>();
        Map<Integer, List<Tuple2<Integer, Integer>>> parentMap = new HashMap<>();

        for (Tuple2<Integer, Integer> tuple : idParentIdList) {
            idMap.put(tuple.get_1(), tuple);
            parentMap.computeIfAbsent(tuple.get_2(), k -> new ArrayList<>()).add(tuple);
        }

        Function<Tuple2<Integer, Integer>, SelfAssociationEntity> buildSubTree = new Function<>() {
            @Override
            public SelfAssociationEntity apply(Tuple2<Integer, Integer> node) {
                List<SelfAssociationEntity> children = parentMap.getOrDefault(node.get_1(), Collections.emptyList())
                        .stream()
                        .map(this)
                        .collect(Collectors.toList());
                return SelfAssociationEntityDraft.${'$'}.produce(draft -> draft
                        .setId(node.get_1())
                        .setChildren(children)
                );
            }
        };

        List<SelfAssociationEntity> roots = idParentIdList.stream()
                .filter(tuple -> tuple.get_2() == null || !idMap.containsKey(tuple.get_2()))
                .map(buildSubTree)
                .collect(Collectors.toList());

        return roots;
    }

    private List<Integer> flatIds(List<SelfAssociationEntity> list) {
        List<Integer> result = new ArrayList<>();
        for (SelfAssociationEntity entity : list) {
            result.add(entity.id());
            result.addAll(flatIds(entity.children()));
        }
        return result;
    }

    /**
     * 根据提供的查询参数分页查询树形的树节点。
     *
     * @param query 分页查询参数。
     * @return 树节点树状分页数据。
     */
    @PostMapping("/tree/page")
    @SaCheckPermission("selfAssociationEntity:list")
    @NotNull
    public Page<@NotNull SelfAssociationEntityTreeView> treePage(
            @RequestBody @NotNull PageQuery<SelfAssociationEntitySpec> query
    ) throws AuthorizeException {
        List<Tuple2<Integer, Integer>> idParentIdList = sqlClient.createQuery(SELF_ASSOCIATION_ENTITY_TABLE)
                .where(query.getSpec())
                .select(SELF_ASSOCIATION_ENTITY_TABLE.id(), SELF_ASSOCIATION_ENTITY_TABLE.parentId())
                .execute();

        List<SelfAssociationEntity> idTreeList = buildIdTree(idParentIdList);

        int startIndex = Math.min(query.getPageIndex() * query.getPageSize(), idTreeList.size());
        int endIndex = Math.min((query.getPageIndex() + 1) * query.getPageSize(), idTreeList.size());

        List<Integer> idList = flatIds(idTreeList.subList(startIndex, endIndex));

        List<SelfAssociationEntityTreeView> list = sqlClient.createQuery(SELF_ASSOCIATION_ENTITY_TABLE)
                .where(SELF_ASSOCIATION_ENTITY_TABLE.id().in(idList))
                .select(SELF_ASSOCIATION_ENTITY_TABLE.fetch(SelfAssociationEntityTreeView.METADATA.getFetcher().remove("children")))
                .execute()
                .stream()
                .map(SelfAssociationEntityTreeView::new)
                .collect(Collectors.toList());

        List<@NotNull SelfAssociationEntityTreeView> treeList = buildTree(list);

        return new Page<>(treeList, idTreeList.size(), idTreeList.size() / query.getPageSize());
    }

    /**
     * 根据提供的查询参数列出树节点选项。
     *
     * @param spec 查询参数。
     * @return 树节点列表数据。
     */
    @PostMapping("/list/options")
    @SaCheckPermission("selfAssociationEntity:select")
    @NotNull
    public List<@NotNull SelfAssociationEntityOptionView> listOptions(@RequestBody @NotNull SelfAssociationEntitySpec spec) throws AuthorizeException {
        return sqlClient.createQuery(SELF_ASSOCIATION_ENTITY_TABLE)
                .where(spec)
                .select(SELF_ASSOCIATION_ENTITY_TABLE.fetch(SelfAssociationEntityOptionView.class))
                .execute();
    }

    /**
     * 插入新的树节点。
     *
     * @param input 树节点插入输入对象。
     * @return 插入的树节点的ID。
     */
    @PostMapping
    @SaCheckPermission("selfAssociationEntity:insert")
    @Transactional
    public int insert(@RequestBody @NotNull SelfAssociationEntityInsertInput input) throws AuthorizeException {
        return sqlClient.insert(input).getModifiedEntity().id();
    }

    /**
     * 根据ID获取树节点的更新回填信息。
     *
     * @param id 树节点的ID。
     * @return 树节点的更新回填信息。
     */
    @GetMapping("/{id}/forUpdate")
    @SaCheckPermission("selfAssociationEntity:update")
    @Nullable
    public SelfAssociationEntityUpdateFillView getForUpdate(@PathVariable int id) throws AuthorizeException {
        return sqlClient.findById(SelfAssociationEntityUpdateFillView.class, id);
    }

    /**
     * 更新树节点。
     *
     * @param input 树节点更新输入对象。
     * @return 更新的树节点的ID。
     */
    @PutMapping
    @SaCheckPermission("selfAssociationEntity:update")
    @Transactional
    public int update(@RequestBody @NotNull SelfAssociationEntityUpdateInput input) throws AuthorizeException {
        return sqlClient.update(input, AssociatedSaveMode.REPLACE).getModifiedEntity().id();
    }

    /**
     * 删除指定ID的树节点。
     *
     * @param ids 要删除的树节点ID列表。
     * @return 删除的树节点的行数。
     */
    @DeleteMapping
    @SaCheckPermission("selfAssociationEntity:delete")
    @Transactional
    public int delete(@RequestParam @NotNull List<@NotNull Integer> ids) throws AuthorizeException {
        return sqlClient.deleteByIds(SelfAssociationEntity.class, ids).getAffectedRowCount(SelfAssociationEntity.class);
    }
}
            """.trimIndent(),
            JavaServiceGenerator.generateService(selfAssociationEntity)
                .content
        )
    }
}