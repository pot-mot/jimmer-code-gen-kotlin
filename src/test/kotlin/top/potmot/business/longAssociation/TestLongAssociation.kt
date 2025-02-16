package top.potmot.business.longAssociation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.dto.generate.DtoGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator

class TestLongAssociation {
    private val index = "${'$'}index"
    @Test
    fun `test longAssociationToOneTarget dto`() {
        assertEquals(
            """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    longAssociationToOneProperty {
        name
    }
}

EntityDetailView {
    #allScalars
    longAssociationToOneProperty {
        #allScalars
    }
}

EntityOptionView {
    id
}

input EntityInsertInput {
    #allScalars
    -id
    longAssociationToOneProperty {
        #allScalars
        -id
    }
}

EntityUpdateFillView {
    #allScalars
    longAssociationToOneProperty {
        #allScalars
    }
}

input EntityUpdateInput {
    #allScalars
    id!
    longAssociationToOneProperty {
        #allScalars
    }
}

specification EntitySpec {
    eq(id)
    associatedIdEq(longAssociationToOneProperty)
})
            """.trimIndent(),
            DtoGenerator.generateDto(longAssociationToOneTargetEntity)
                .let { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test longAssociationToOneTarget view`() {
        assertEquals(
            """
[(components/entity/EntityTable.vue, <script setup lang="ts">
import type {EntityListView} from "@/api/__generated/model/static"
import {usePageSizeStore} from "@/stores/pageSizeStore"

withDefaults(defineProps<{
    rows: Array<EntityListView>,
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined
}>(), {
    idColumn: false,
    indexColumn: true,
    multiSelect: true,
})

const emits = defineEmits<{
    (
        event: "selectionChange",
        selection: Array<EntityListView>
    ): void
}>()

defineSlots<{
    operations(props: {row: EntityListView, index: number}): any
}>()

const pageSizeStore = usePageSizeStore()

const handleSelectionChange = (newSelection: Array<EntityListView>): void => {
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
        <el-table-column
            prop="longAssociationToOneProperty.name"
            label="commentname"
        />
        <el-table-column
            label="操作"
            :fixed="pageSizeStore.isSmall ? undefined : 'right'"
        >
            <template #default="scope">
                <slot
                    name="operations"
                    :row="scope.row as EntityListView"
                    :index="scope.$index"
                />
            </template>
        </el-table-column>
    </el-table>
</template>
), (components/entity/EntityAddFormType.d.ts, export type EntityAddFormType = {
    longAssociationToOneProperty: {
        name: string
    } | undefined
}
), (components/entity/createDefaultEntity.ts, import type {EntityAddFormType} from "./EntityAddFormType"

export const createDefaultEntity = (): EntityAddFormType => {
    return {
        longAssociationToOneProperty: undefined
    }
}
), (components/entity/EntityAddForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityInsertInput} from "@/api/__generated/model/static"
import type {EntityAddFormType} from "@/components/entity/EntityAddFormType"
import {createDefaultEntity} from "@/components/entity/createDefaultEntity"
import {useRules} from "@/rules/entity/EntityAddFormRules"
import EntityLongAssociationToOnePropertySubForm from "@/components/entity/longAssociationEntity/EntityLongAssociationToOnePropertySubForm.vue"
import {sendMessage} from "@/utils/message"

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined
}>(), {
    withOperations: true,
    submitLoading: false,
})

const emits = defineEmits<{
    (
        event: "submit",
        formData: EntityInsertInput
    ): void,
    (event: "cancel"): void
}>()

defineSlots<{
    operations(props: {
        handleSubmit: () => Promise<void>,
        handleCancel: () => void
    }): any
}>()

const formData = ref<EntityAddFormType>(createDefaultEntity())

const formRef = ref<FormInstance>()
const rules = useRules(formData)

// 校验
const handleValidate = async (): Promise<boolean> => {
    const formValid: boolean | undefined = await formRef.value?.validate().catch(() => false)

    if (formValid) {
        if (formData.value.longAssociationToOneProperty === undefined) {
            sendMessage("comment不可为空", "warning")
            return false
        }

        return true
    } else {
        return false
    }
}

// 提交
const handleSubmit = async (): Promise<void> => {
    if (props.submitLoading) return

    const validResult = await handleValidate()
    if (validResult) {
        emits("submit", formData.value as EntityInsertInput)
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
        <el-form-item
            prop="longAssociationToOneProperty"
            label="comment"
        >
            <EntityLongAssociationToOnePropertySubForm
                v-model="formData.longAssociationToOneProperty"
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
), (rules/entity/EntityAddFormRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityAddFormType} from "@/components/entity/EntityAddFormType"
import type {EntityInsertInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<EntityAddFormType>): FormRules<EntityInsertInput> => {
    return {
        longAssociationToOnePropertyId: [
            {required: true, message: "comment不能为空", trigger: "blur"},
        ],
    }
}), (components/entity/EntityEditForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityUpdateInput} from "@/api/__generated/model/static"
import {useRules} from "@/rules/entity/EntityEditFormRules"
import EntityLongAssociationToOnePropertySubForm from "@/components/entity/longAssociationEntity/EntityLongAssociationToOnePropertySubForm.vue"

const formData = defineModel<EntityUpdateInput>({
    required: true
})

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined
}>(), {
    withOperations: true,
    submitLoading: false,
})

const emits = defineEmits<{
    (
        event: "submit",
        formData: EntityUpdateInput
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
        <el-form-item
            prop="longAssociationToOneProperty"
            label="comment"
        >
            <EntityLongAssociationToOnePropertySubForm
                v-model="formData.longAssociationToOneProperty"
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
), (rules/entity/EntityEditFormRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityUpdateInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<EntityUpdateInput>): FormRules<EntityUpdateInput> => {
    return {
        longAssociationToOnePropertyId: [
            {required: true, message: "comment不能为空", trigger: "blur"},
        ],
    }
}), (components/entity/longAssociationEntity/EntityLongAssociationToOnePropertySubFormType.d.ts, export type EntityLongAssociationToOnePropertySubFormType = {
    name: string
}
), (components/entity/longAssociationEntity/createDefaultEntityLongAssociationToOneProperty.ts, import type {EntityLongAssociationToOnePropertySubFormType} from "./EntityLongAssociationToOnePropertySubFormType"

export const createDefaultEntityLongAssociationToOneProperty = (): EntityLongAssociationToOnePropertySubFormType => {
    return {
        name: ""
    }
}
), (components/entity/longAssociationEntity/EntityLongAssociationToOnePropertySubForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {
    EntityInsertInput_TargetOf_longAssociationToOneProperty
} from "@/api/__generated/model/static"
import type {
    EntityLongAssociationToOnePropertySubFormType
} from "@/components/entity/longAssociationEntity/EntityLongAssociationToOnePropertySubFormType"
import {
    createDefaultEntityLongAssociationToOneProperty
} from "@/components/entity/longAssociationEntity/createDefaultEntityLongAssociationToOneProperty"
import {useRules} from "@/rules/longAssociationEntity/LongAssociationEntitySubFormRules"

const formData = defineModel<EntityLongAssociationToOnePropertySubFormType>({
    required: false,
    defaultValue: createDefaultEntityLongAssociationToOneProperty()
})

withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined
}>(), {
    withOperations: true,
    submitLoading: false,
})

defineSlots<{
    operations(props: {
        handleSubmit: () => Promise<void>,
        handleCancel: () => void
    }): any
}>()

const formData = ref<EntityLongAssociationToOnePropertySubFormType>(createDefaultEntityLongAssociationToOneProperty())

const formRef = ref<FormInstance>()
const rules = useRules(formData)

// 校验
const handleValidate = async (): Promise<boolean> => {
    return await formRef.value?.validate().catch(() => false) ?? false
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
        <el-form-item prop="name" label="name">
            <el-input
                v-model="formData.name"
                placeholder="请输入name"
                clearable
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
), (rules/longAssociationEntity/LongAssociationEntitySubFormRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {
    EntityLongAssociationToOnePropertySubFormType
} from "@/components/entity/longAssociationEntity/EntityLongAssociationToOnePropertySubFormType"
import type {
    EntityInsertInput_TargetOf_longAssociationToOneProperty
} from "@/api/__generated/model/static"

export const useRules = (
    _: Ref<EntityLongAssociationToOnePropertySubFormType>
): FormRules<EntityInsertInput_TargetOf_longAssociationToOneProperty> => {
    return {
        name: [
            {required: true, message: "name不能为空", trigger: "blur"},
        ],
    }
}), (components/entity/EntityQueryForm.vue, <script setup lang="ts">
import {Search} from "@element-plus/icons-vue"
import type {EntitySpec} from "@/api/__generated/model/static"
import LongAssociationEntityIdSelect from "@/components/longAssociationEntity/LongAssociationEntityIdSelect.vue"

const spec = defineModel<EntitySpec>({
    required: true
})

const emits = defineEmits<{(event: "query", spec: EntitySpec): void}>()
</script>

<template>
    <el-form :model="spec" @submit.prevent>
        <el-row :gutter="20">
            <el-col :span="8" :xs="24">
                <el-form-item
                    prop="longAssociationToOneProperty"
                    label="comment"
                >
                    <LongAssociationEntityIdSelect
                        v-model="spec.longAssociationToOneProperty"
                        :options="longAssociationToOnePropertyOptions"
                        @change="emits('query', spec)"
                    />
                </el-form-item>
            </el-col>
            <el-button
                type="primary"
                :icon="Search"
                class="search-button"
                @click="emits('query', spec)"
            >
                查询
            </el-button>
        </el-row>
    </el-form>
</template>
), (pages/entity/EntityPage.vue, <script setup lang="ts">
import {ref} from "vue"
import {Plus, EditPen, Delete} from "@element-plus/icons-vue"
import type {
    Page,
    PageQuery,
    EntityListView,
    EntityInsertInput,
    EntityUpdateInput,
    EntitySpec
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
            userStore.permissions.includes('entity:insert')
        "
        destroy-on-close
        :close-on-click-modal="false"
    >
        <EntityAddForm
            :submitLoading="isLoading"
            @submit="submitAdd"
            @cancel="cancelAdd"
        />
    </el-dialog>

    <el-dialog
        v-model="editDialogVisible"
        v-if="
            userStore.permissions.includes('entity:update')
        "
        destroy-on-close
        :close-on-click-modal="false"
    >
        <EntityEditForm
            v-if="updateInput !== undefined"
            v-model="updateInput"
            :submitLoading="isLoading"
            @submit="submitEdit"
            @cancel="cancelEdit"
        />
    </el-dialog>
</template>
)]
            """.trimIndent(),
            Vue3ElementPlusViewGenerator.generateEntity(listOf(longAssociationToOneTargetEntity))
                .map { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test longAssociationTarget toMany dto`() {
        assertEquals(
            """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    longAssociationToOneProperty {
        name
    }
}

EntityDetailView {
    #allScalars
    longAssociationToOneProperty {
        #allScalars
    }
}

EntityOptionView {
    id
}

input EntityInsertInput {
    #allScalars
    -id
    longAssociationToOneProperty {
        #allScalars
        -id
    }
}

EntityUpdateFillView {
    #allScalars
    longAssociationToOneProperty {
        #allScalars
    }
}

input EntityUpdateInput {
    #allScalars
    id!
    longAssociationToOneProperty {
        #allScalars
    }
}

specification EntitySpec {
    eq(id)
    associatedIdIn(longAssociationToOneProperty) as longAssociationToOnePropertyIds
})
            """.trimIndent(),
            DtoGenerator.generateDto(longAssociationToManyTargetEntity)
                .let { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test longAssociationTarget toMany view`() {
        assertEquals(
            """
[(components/entity/EntityTable.vue, <script setup lang="ts">
import type {EntityListView} from "@/api/__generated/model/static"
import {usePageSizeStore} from "@/stores/pageSizeStore"

withDefaults(defineProps<{
    rows: Array<EntityListView>,
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined
}>(), {
    idColumn: false,
    indexColumn: true,
    multiSelect: true,
})

const emits = defineEmits<{
    (
        event: "selectionChange",
        selection: Array<EntityListView>
    ): void
}>()

defineSlots<{
    operations(props: {row: EntityListView, index: number}): any
}>()

const pageSizeStore = usePageSizeStore()

const handleSelectionChange = (newSelection: Array<EntityListView>): void => {
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
        <el-table-column
            prop="longAssociationToOneProperty"
            label="comment"
        />
        <el-table-column
            label="操作"
            :fixed="pageSizeStore.isSmall ? undefined : 'right'"
        >
            <template #default="scope">
                <slot
                    name="operations"
                    :row="scope.row as EntityListView"
                    :index="scope.$index"
                />
            </template>
        </el-table-column>
    </el-table>
</template>
), (components/entity/EntityAddFormType.d.ts, export type EntityAddFormType = {
    longAssociationToOneProperty: Array<{
            name: string
        }
    >
}
), (components/entity/createDefaultEntity.ts, import type {EntityAddFormType} from "./EntityAddFormType"

export const createDefaultEntity = (): EntityAddFormType => {
    return {
        longAssociationToOneProperty: []
    }
}
), (components/entity/EntityAddForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityInsertInput} from "@/api/__generated/model/static"
import type {EntityAddFormType} from "@/components/entity/EntityAddFormType"
import {createDefaultEntity} from "@/components/entity/createDefaultEntity"
import {useRules} from "@/rules/entity/EntityAddFormRules"
import EntityLongAssociationToOnePropertyEditTable from "@/components/entity/longAssociationEntity/EntityLongAssociationToOnePropertyEditTable.vue"

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined
}>(), {
    withOperations: true,
    submitLoading: false,
})

const emits = defineEmits<{
    (
        event: "submit",
        formData: EntityInsertInput
    ): void,
    (event: "cancel"): void
}>()

defineSlots<{
    operations(props: {
        handleSubmit: () => Promise<void>,
        handleCancel: () => void
    }): any
}>()

const formData = ref<EntityAddFormType>(createDefaultEntity())

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
        emits("submit", formData.value as EntityInsertInput)
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
        <el-form-item
            prop="longAssociationToOneProperty"
            label="comment"
        >
            <EntityLongAssociationToOnePropertyEditTable
                v-model="formData.longAssociationToOneProperty"
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
), (rules/entity/EntityAddFormRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityAddFormType} from "@/components/entity/EntityAddFormType"
import type {EntityInsertInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<EntityAddFormType>): FormRules<EntityInsertInput> => {
    return {
        longAssociationToOnePropertyIds: [
            {required: true, message: "comment不能为空", trigger: "blur"},
            {type: "array", message: "comment必须是列表", trigger: "blur"},
        ],
    }
}), (components/entity/EntityEditForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityUpdateInput} from "@/api/__generated/model/static"
import {useRules} from "@/rules/entity/EntityEditFormRules"
import EntityLongAssociationToOnePropertyEditTable from "@/components/entity/longAssociationEntity/EntityLongAssociationToOnePropertyEditTable.vue"

const formData = defineModel<EntityUpdateInput>({
    required: true
})

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined
}>(), {
    withOperations: true,
    submitLoading: false,
})

const emits = defineEmits<{
    (
        event: "submit",
        formData: EntityUpdateInput
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
        <el-form-item
            prop="longAssociationToOneProperty"
            label="comment"
        >
            <EntityLongAssociationToOnePropertyEditTable
                v-model="formData.longAssociationToOneProperty"
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
), (rules/entity/EntityEditFormRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityUpdateInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<EntityUpdateInput>): FormRules<EntityUpdateInput> => {
    return {
        longAssociationToOnePropertyIds: [
            {required: true, message: "comment不能为空", trigger: "blur"},
            {type: "array", message: "comment必须是列表", trigger: "blur"},
        ],
    }
}), (components/entity/longAssociationEntity/EntityLongAssociationToOnePropertyEditTableType.d.ts, export type EntityLongAssociationToOnePropertyEditTableType = {
    name: string
}
), (components/entity/longAssociationEntity/createDefaultEntityLongAssociationToOneProperty.ts, import type {EntityLongAssociationToOnePropertyEditTableType} from "./EntityLongAssociationToOnePropertyEditTableType"

export const createDefaultEntityLongAssociationToOneProperty = (): EntityLongAssociationToOnePropertyEditTableType => {
    return {
        name: ""
    }
}
), (components/entity/longAssociationEntity/EntityLongAssociationToOnePropertyEditTable.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {
    EntityLongAssociationToOnePropertyEditTableType
} from "@/api/__generated/model/static"
import {
    createDefaultEntityLongAssociationToOneProperty
} from "@/components/entity/longAssociationEntity/createDefaultEntityLongAssociationToOneProperty"
import {useRules} from "@/rules/longAssociationEntity/LongAssociationEntityEditTableRules"
import {usePageSizeStore} from "@/stores/pageSizeStore"
import {Plus, Delete} from "@element-plus/icons-vue"
import {deleteConfirm} from "@/utils/confirm"

const rows = defineModel<Array<EntityLongAssociationToOnePropertyEditTableType>>({
    required: true
})

const props = withDefaults(defineProps<{
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined,
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined
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
        rows: Array<EntityLongAssociationToOnePropertyEditTableType>
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
const selection = ref<Array<EntityLongAssociationToOnePropertyEditTableType>>([])

const handleSelectionChange = (
    newSelection: Array<EntityLongAssociationToOnePropertyEditTableType>
): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    rows.value.push(createDefaultEntityLongAssociationToOneProperty())
}

// 删除
const handleBatchDelete = async (): Promise<void> => {
    const result = await deleteConfirm("这些longAssociationEntityComment")
    if (!result) return
    rows.value = rows.value.filter(it => !selection.value.includes(it))
}

const handleSingleDelete = async (index: number): Promise<void> => {
    const result = await deleteConfirm("该longAssociationEntityComment")
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
            <el-table-column prop="name" label="name">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'name']"
                        label="name"
                        :rule="rules.name"
                    >
                        <el-input
                            v-model="scope.row.name"
                            placeholder="请输入name"
                            clearable
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
), (rules/longAssociationEntity/LongAssociationEntityEditTableRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {
    EntityUpdateInput_TargetOf_longAssociationToOneProperty
} from "@/api/__generated/model/static"

export const useRules = (
    _: Ref<Array<EntityUpdateInput_TargetOf_longAssociationToOneProperty>>
): FormRules<EntityUpdateInput_TargetOf_longAssociationToOneProperty> => {
    return {
        name: [
            {required: true, message: "name不能为空", trigger: "blur"},
        ],
    }
}), (components/entity/EntityQueryForm.vue, <script setup lang="ts">
import {Search} from "@element-plus/icons-vue"
import type {EntitySpec} from "@/api/__generated/model/static"

const spec = defineModel<EntitySpec>({
    required: true
})

const emits = defineEmits<{(event: "query", spec: EntitySpec): void}>()
</script>

<template>
    <el-form :model="spec" @submit.prevent>
        <el-row :gutter="20">
            <el-button
                type="primary"
                :icon="Search"
                class="search-button"
                @click="emits('query', spec)"
            >
                查询
            </el-button>
        </el-row>
    </el-form>
</template>
), (pages/entity/EntityPage.vue, <script setup lang="ts">
import {ref} from "vue"
import {Plus, EditPen, Delete} from "@element-plus/icons-vue"
import type {
    Page,
    PageQuery,
    EntityListView,
    EntityInsertInput,
    EntityUpdateInput,
    EntitySpec
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
            userStore.permissions.includes('entity:insert')
        "
        destroy-on-close
        :close-on-click-modal="false"
    >
        <EntityAddForm
            :submitLoading="isLoading"
            @submit="submitAdd"
            @cancel="cancelAdd"
        />
    </el-dialog>

    <el-dialog
        v-model="editDialogVisible"
        v-if="
            userStore.permissions.includes('entity:update')
        "
        destroy-on-close
        :close-on-click-modal="false"
    >
        <EntityEditForm
            v-if="updateInput !== undefined"
            v-model="updateInput"
            :submitLoading="isLoading"
            @submit="submitEdit"
            @cancel="cancelEdit"
        />
    </el-dialog>
</template>
)]
            """.trimIndent(),
            Vue3ElementPlusViewGenerator.generateEntity(listOf(longAssociationToManyTargetEntity))
                .map { it.path to it.content }.toString()
        )
    }
}