package top.potmot.business.superTable

const val index = """${'$'}index"""

const val vue3ElementPlusResult = """
[(components/entity/EntityTable.vue, <script setup lang="ts">
import type {EntityListView} from "@/api/__generated/model/static"
import {usePageSizeStore} from "@/stores/pageSizeStore"
import EnumView from "@/components/enums/enum/EnumView.vue"

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
        <el-table-column prop="enumProperty" label="enumProperty">
            <template #default="scope">
                <EnumView :value="scope.row.enumProperty"/>
            </template>
        </el-table-column>
        <el-table-column
            prop="enumNullableProperty"
            label="enumNullableProperty"
        >
            <template #default="scope">
                <EnumView :value="scope.row.enumNullableProperty"/>
            </template>
        </el-table-column>
        <el-table-column
            prop="toOnePropertyId"
            label="toOneProperty"
        />
        <el-table-column
            prop="toOneNullablePropertyId"
            label="toOneNullableProperty"
        />
        <el-table-column prop="createdById" label="createdBy"/>
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
), (components/entity/EntityAddFormType.d.ts, import type {Enum} from "@/api/__generated/model/enums"

export type EntityAddFormType = {
    enumProperty: Enum
    enumNullableProperty: Enum | undefined
    toOnePropertyId: number | undefined
    toOneNullablePropertyId: number | undefined
    createdById: number | undefined
}
), (components/entity/createDefaultEntity.ts, import type {EntityAddFormType} from "./EntityAddFormType"

export const createDefaultEntity = (): EntityAddFormType => {
    return {
        enumProperty: "item1",
        enumNullableProperty: undefined,
        toOnePropertyId: undefined,
        toOneNullablePropertyId: undefined,
        createdById: undefined
    }
}
), (components/entity/EntityAddForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityInsertInput, ToOneEntityOptionView} from "@/api/__generated/model/static"
import type {EntityAddFormType} from "@/components/entity/EntityAddFormType"
import {createDefaultEntity} from "@/components/entity/createDefaultEntity"
import {useRules} from "@/rules/entity/EntityAddFormRules"
import EnumSelect from "@/components/enums/enum/EnumSelect.vue"
import EnumNullableSelect from "@/components/enums/enum/EnumNullableSelect.vue"
import ToOneEntityIdSelect from "@/components/toOneEntity/ToOneEntityIdSelect.vue"
import {sendMessage} from "@/utils/message"

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined,
    toOnePropertyIdOptions: Array<ToOneEntityOptionView>,
    toOneNullablePropertyIdOptions: Array<ToOneEntityOptionView>,
    createdByIdOptions: Array<ToOneEntityOptionView>
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
        if (formData.value.toOnePropertyId === undefined) {
            sendMessage("toOneProperty不可为空", "warning")
            return false
        }


        if (formData.value.createdById === undefined) {
            sendMessage("createdBy不可为空", "warning")
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
        <el-form-item prop="enumProperty" label="enumProperty">
            <EnumSelect v-model="formData.enumProperty"/>
        </el-form-item>
        <el-form-item
            prop="enumNullableProperty"
            label="enumNullableProperty"
        >
            <EnumNullableSelect v-model="formData.enumNullableProperty"/>
        </el-form-item>
        <el-form-item
            prop="toOnePropertyId"
            label="toOneProperty"
        >
            <ToOneEntityIdSelect
                v-model="formData.toOnePropertyId"
                :options="toOnePropertyIdOptions"
            />
        </el-form-item>
        <el-form-item
            prop="toOneNullablePropertyId"
            label="toOneNullableProperty"
        >
            <ToOneEntityIdSelect
                v-model="formData.toOneNullablePropertyId"
                :options="toOneNullablePropertyIdOptions"
            />
        </el-form-item>
        <el-form-item prop="createdById" label="createdBy">
            <ToOneEntityIdSelect
                v-model="formData.createdById"
                :options="createdByIdOptions"
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
        enumProperty: [
            {required: true, message: "enumProperty不能为空", trigger: "blur"},
            {type: "enum", enum: ["item1"], message: "enumProperty必须是item1", trigger: "blur"},
        ],
        enumNullableProperty: [
            {type: "enum", enum: ["item1"], message: "enumNullableProperty必须是item1", trigger: "blur"},
        ],
        toOnePropertyId: [
            {required: true, message: "toOneProperty不能为空", trigger: "blur"},
        ],
        toOneNullablePropertyId: [
        ],
        createdById: [
            {required: true, message: "createdBy不能为空", trigger: "blur"},
        ],
    }
}), (components/entity/EntityEditForm.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityUpdateInput, ToOneEntityOptionView} from "@/api/__generated/model/static"
import {useRules} from "@/rules/entity/EntityEditFormRules"
import EnumSelect from "@/components/enums/enum/EnumSelect.vue"
import EnumNullableSelect from "@/components/enums/enum/EnumNullableSelect.vue"
import ToOneEntityIdSelect from "@/components/toOneEntity/ToOneEntityIdSelect.vue"

const formData = defineModel<EntityUpdateInput>({
    required: true
})

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined,
    toOnePropertyIdOptions: Array<ToOneEntityOptionView>,
    toOneNullablePropertyIdOptions: Array<ToOneEntityOptionView>,
    createdByIdOptions: Array<ToOneEntityOptionView>
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
        <el-form-item prop="enumProperty" label="enumProperty">
            <EnumSelect v-model="formData.enumProperty"/>
        </el-form-item>
        <el-form-item
            prop="enumNullableProperty"
            label="enumNullableProperty"
        >
            <EnumNullableSelect v-model="formData.enumNullableProperty"/>
        </el-form-item>
        <el-form-item
            prop="toOnePropertyId"
            label="toOneProperty"
        >
            <ToOneEntityIdSelect
                v-model="formData.toOnePropertyId"
                :options="toOnePropertyIdOptions"
            />
        </el-form-item>
        <el-form-item
            prop="toOneNullablePropertyId"
            label="toOneNullableProperty"
        >
            <ToOneEntityIdSelect
                v-model="formData.toOneNullablePropertyId"
                :options="toOneNullablePropertyIdOptions"
            />
        </el-form-item>
        <el-form-item prop="createdById" label="createdBy">
            <ToOneEntityIdSelect
                v-model="formData.createdById"
                :options="createdByIdOptions"
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
        enumProperty: [
            {required: true, message: "enumProperty不能为空", trigger: "blur"},
            {type: "enum", enum: ["item1"], message: "enumProperty必须是item1", trigger: "blur"},
        ],
        enumNullableProperty: [
            {type: "enum", enum: ["item1"], message: "enumNullableProperty必须是item1", trigger: "blur"},
        ],
        toOnePropertyId: [
            {required: true, message: "toOneProperty不能为空", trigger: "blur"},
        ],
        toOneNullablePropertyId: [
        ],
        createdById: [
            {required: true, message: "createdBy不能为空", trigger: "blur"},
        ],
    }
}), (components/entity/EntityEditTable.vue, <script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityAddFormType, ToOneEntityOptionView} from "@/api/__generated/model/static"
import {createDefaultEntity} from "@/components/entity/createDefaultEntity"
import {useRules} from "@/rules/entity/EntityEditTableRules"
import {usePageSizeStore} from "@/stores/pageSizeStore"
import {Plus, Delete} from "@element-plus/icons-vue"
import {deleteConfirm} from "@/utils/confirm"
import EnumSelect from "@/components/enums/enum/EnumSelect.vue"
import EnumNullableSelect from "@/components/enums/enum/EnumNullableSelect.vue"
import ToOneEntityIdSelect from "@/components/toOneEntity/ToOneEntityIdSelect.vue"

const rows = defineModel<Array<EntityAddFormType>>({
    required: true
})

const props = withDefaults(defineProps<{
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined,
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined,
    toOnePropertyIdOptions: Array<ToOneEntityOptionView>,
    toOneNullablePropertyIdOptions: Array<ToOneEntityOptionView>,
    createdByIdOptions: Array<ToOneEntityOptionView>
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
        rows: Array<EntityAddFormType>
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
const selection = ref<Array<EntityAddFormType>>([])

const handleSelectionChange = (newSelection: Array<EntityAddFormType>): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    rows.value.push(createDefaultEntity())
}

// 删除
const handleBatchDelete = async (): Promise<void> => {
    const result = await deleteConfirm("这些comment")
    if (!result) return
    rows.value = rows.value.filter(it => !selection.value.includes(it))
}

const handleSingleDelete = async (index: number): Promise<void> => {
    const result = await deleteConfirm("该comment")
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
            <el-table-column prop="enumProperty" label="enumProperty">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'enumProperty']"
                        label="enumProperty"
                        :rule="rules.enumProperty"
                    >
                        <EnumSelect v-model="scope.row.enumProperty"/>
                    </el-form-item>
                </template>
            </el-table-column>
            <el-table-column
                prop="enumNullableProperty"
                label="enumNullableProperty"
            >
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'enumNullableProperty']"
                        label="enumNullableProperty"
                        :rule="rules.enumNullableProperty"
                    >
                        <EnumNullableSelect v-model="scope.row.enumNullableProperty"/>
                    </el-form-item>
                </template>
            </el-table-column>
            <el-table-column
                prop="toOnePropertyId"
                label="toOneProperty"
            >
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'toOnePropertyId']"
                        label="toOneProperty"
                        :rule="rules.toOnePropertyId"
                    >
                        <ToOneEntityIdSelect
                            v-model="scope.row.toOnePropertyId"
                            :options="toOnePropertyIdOptions"
                        />
                    </el-form-item>
                </template>
            </el-table-column>
            <el-table-column
                prop="toOneNullablePropertyId"
                label="toOneNullableProperty"
            >
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'toOneNullablePropertyId']"
                        label="toOneNullableProperty"
                        :rule="rules.toOneNullablePropertyId"
                    >
                        <ToOneEntityIdSelect
                            v-model="scope.row.toOneNullablePropertyId"
                            :options="toOneNullablePropertyIdOptions"
                        />
                    </el-form-item>
                </template>
            </el-table-column>
            <el-table-column prop="createdById" label="createdBy">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'createdById']"
                        label="createdBy"
                        :rule="rules.createdById"
                    >
                        <ToOneEntityIdSelect
                            v-model="scope.row.createdById"
                            :options="createdByIdOptions"
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
), (rules/entity/EntityEditTableRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityUpdateInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<Array<EntityUpdateInput>>): FormRules<EntityUpdateInput> => {
    return {
        enumProperty: [
            {required: true, message: "enumProperty不能为空", trigger: "blur"},
            {type: "enum", enum: ["item1"], message: "enumProperty必须是item1", trigger: "blur"},
        ],
        enumNullableProperty: [
            {type: "enum", enum: ["item1"], message: "enumNullableProperty必须是item1", trigger: "blur"},
        ],
        toOnePropertyId: [
            {required: true, message: "toOneProperty不能为空", trigger: "blur"},
        ],
        toOneNullablePropertyId: [
        ],
        createdById: [
            {required: true, message: "createdBy不能为空", trigger: "blur"},
        ],
    }
}), (components/entity/EntityQueryForm.vue, <script setup lang="ts">
import {Search} from "@element-plus/icons-vue"
import type {EntitySpec, ToOneEntityOptionView} from "@/api/__generated/model/static"
import EnumNullableSelect from "@/components/enums/enum/EnumNullableSelect.vue"
import ToOneEntityIdSelect from "@/components/toOneEntity/ToOneEntityIdSelect.vue"

const spec = defineModel<EntitySpec>({
    required: true
})

defineProps<{
    toOnePropertyIdOptions: Array<ToOneEntityOptionView>,
    toOneNullablePropertyIdOptions: Array<ToOneEntityOptionView>,
    createdByIdOptions: Array<ToOneEntityOptionView>
}>()

const emits = defineEmits<{(event: "query", spec: EntitySpec): void}>()
</script>

<template>
    <el-form :model="spec" @submit.prevent>
        <el-row :gutter="20">
            <el-col :span="8" :xs="24">
                <el-form-item prop="enumProperty" label="enumProperty">
                    <EnumNullableSelect
                        v-model="spec.enumProperty"
                        @change="emits('query', spec)"
                    />
                </el-form-item>
            </el-col>
            <el-col :span="8" :xs="24">
                <el-form-item
                    prop="enumNullableProperty"
                    label="enumNullableProperty"
                >
                    <EnumNullableSelect
                        v-model="spec.enumNullableProperty"
                        @change="emits('query', spec)"
                    />
                </el-form-item>
            </el-col>
            <el-col :span="8" :xs="24">
                <el-form-item
                    prop="toOnePropertyId"
                    label="toOneProperty"
                >
                    <ToOneEntityIdSelect
                        v-model="spec.toOnePropertyId"
                        :options="toOnePropertyIdOptions"
                        @change="emits('query', spec)"
                    />
                </el-form-item>
            </el-col>
            <el-col :span="8" :xs="24">
                <el-form-item
                    prop="toOneNullablePropertyId"
                    label="toOneNullableProperty"
                >
                    <ToOneEntityIdSelect
                        v-model="spec.toOneNullablePropertyId"
                        :options="toOneNullablePropertyIdOptions"
                        @change="emits('query', spec)"
                    />
                </el-form-item>
            </el-col>
            <el-col :span="8" :xs="24">
                <el-form-item prop="createdById" label="createdBy">
                    <ToOneEntityIdSelect
                        v-model="spec.createdById"
                        :options="createdByIdOptions"
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

// createdBy选项
const createdByIdOptions = ref<Array<ToOneEntityOptionView>>()

const setCreatedByIdOptions = withLoading(async () => {
    createdByIdOptions.value = await api.toOneEntityService.listOptions({body: {}})
})

onBeforeMount(async () => {
    await setCreatedByIdOptions()
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
                toOneNullablePropertyIdOptions &&
                createdByIdOptions
            "
            :toOnePropertyIdOptions="toOnePropertyIdOptions"
            :toOneNullablePropertyIdOptions="toOneNullablePropertyIdOptions"
            :createdByIdOptions="createdByIdOptions"
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
            toOneNullablePropertyIdOptions &&
            createdByIdOptions
        "
        destroy-on-close
        :close-on-click-modal="false"
    >
        <EntityAddForm
            :toOnePropertyIdOptions="toOnePropertyIdOptions"
            :toOneNullablePropertyIdOptions="toOneNullablePropertyIdOptions"
            :createdByIdOptions="createdByIdOptions"
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
            toOneNullablePropertyIdOptions &&
            createdByIdOptions
        "
        destroy-on-close
        :close-on-click-modal="false"
    >
        <EntityEditForm
            v-if="updateInput !== undefined"
            v-model="updateInput"
            :toOnePropertyIdOptions="toOnePropertyIdOptions"
            :toOneNullablePropertyIdOptions="toOneNullablePropertyIdOptions"
            :createdByIdOptions="createdByIdOptions"
            :submitLoading="isLoading"
            @submit="submitEdit"
            @cancel="cancelEdit"
        />
    </el-dialog>
</template>
), (components/entity/EntityIdSelect.vue, <script setup lang="ts">
import {watch} from "vue"
import type {EntityOptionView} from "@/api/__generated/model/static"

const modelValue = defineModel<number | undefined>({
    required: true
})

const props = defineProps<{
    options: Array<EntityOptionView>
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
        placeholder="请选择comment"
        filterable
        clearable
        :value-on-clear="undefined"
    >
        <el-option
            v-for="option in options"
            :key="option.id"
            :value="option.id"
            :label="`${'$'}{option.id}`"
        />
    </el-select>
</template>
), (components/entity/EntityIdMultiSelect.vue, <script setup lang="ts">
import {watch} from "vue"
import type {EntityOptionView} from "@/api/__generated/model/static"

const modelValue = defineModel<Array<number>>({
    required: true
})

const props = defineProps<{
    options: Array<EntityOptionView>
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
        placeholder="请选择comment"
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
            :label="`${'$'}{option.id}`"
        />
    </el-select>
</template>
)]
"""