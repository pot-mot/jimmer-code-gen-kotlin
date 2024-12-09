package top.potmot.business.selfAssociation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.dto.generate.DtoGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.string.trimBlankLine

class TestSelfAssociation {
    private val index = "${'$'}index"

    private val GenEntityBusinessView.result
        get() = DtoGenerator.generateDto(this).let { it.path to it.content }.toString()

    @Test
    fun `test selfAssociation dto`() {
        assertEquals(
            """
(dto/SelfAssociationEntity.dto, export EntityPackage.SelfAssociationEntity

SelfAssociationEntityListView {
    #allScalars
    id(parent)
}

SelfAssociationEntityDetailView {
    #allScalars
    id(parent)
    id(children) as childIds
}

SelfAssociationEntityOptionView {
    id
    label
    id(parent)
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
    excludeIds: Array<number>
}>(), {
    excludeIds: []
})

watch(() => [modelValue.value, props.options], () => {
    if (!(props.options.map(it => it.id) as Array<number | undefined>).includes(modelValue.value)) {
        modelValue.value = undefined
    }
}, {immediate: true})

type TreeOption = {
    value: number,
    label: string,
    disabled: boolean,
    children: Array<TreeOption>
}

const treeOptions = computed<Array<TreeOption>>(() => {
    const idNodeMap = new Map<number, TreeNode>
    const roots: Array<TreeNode> = []

    for (const item of items) {
        const node: TreeNode = {
            value: item.id,
            label: item.label,
            disabled: props.excludeIds.includes(item.id),
            children: []
        }
        idNodeMap.set(item.id, node)
    }

    for (const item of items) {
        const node = idNodeMap.get(item.id)
        if (!node) continue

        const parentNode = idNodeMap.get(item.parentId)                    
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

const filterNodeMethod = (value: string | undefined, data: TreeOption) => {
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
    excludeIds: Array<number>
}>(), {
    excludeIds: []
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

type TreeOption = {
    value: number,
    label: string,
    disabled: boolean,
    children: Array<TreeOption>
}

const treeOptions = computed<Array<TreeOption>>(() => {
    const idNodeMap = new Map<number, TreeNode>
    const roots: Array<TreeNode> = []

    for (const item of items) {
        const node: TreeNode = {
            value: item.id,
            label: item.label,
            disabled: props.excludeIds.includes(item.id),
            children: []
        }
        idNodeMap.set(item.id, node)
    }

    for (const item of items) {
        const node = idNodeMap.get(item.id)
        if (!node) continue

        const parentNode = idNodeMap.get(item.parentId)                    
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

const filterNodeMethod = (value: string | undefined, data: TreeOption) => {
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
            """.trimBlankLine(),
            Vue3ElementPlusViewGenerator.generateView(selfAssociationEntity)
                .filter { GenerateTag.IdSelect in it.tags || GenerateTag.IdMultiSelect in it.tags }
                .map { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test selfAssociation table`() {
        assertEquals(
            """
[(components/selfAssociationEntity/SelfAssociationEntityTable.vue, <script setup lang="ts">
import type {SelfAssociationEntityListView} from "@/api/__generated/model/static"

withDefaults(defineProps<{
    rows: Array<SelfAssociationEntityListView>,
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
        selection: Array<SelfAssociationEntityListView>
    ): void
}>()

defineSlots<{
    operations(props: {
        row: SelfAssociationEntityListView,
        index: number
    }): any
}>()

const handleSelectionChange = (
    newSelection: Array<SelfAssociationEntityListView>
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
        :tree-props="{children: 'children'}"
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
        <el-table-column prop="label" label="标签"/>
        <el-table-column prop="parentId" label="父节点"/>
        <el-table-column label="操作" fixed="right">
            <template #default="scope">
                <slot
                    name="operations"
                    :row="scope.row as SelfAssociationEntityListView"
                    :index="scope.$index"
                />
            </template>
        </el-table-column>
    </el-table>
</template>
)]
            """.trimBlankLine(),
            Vue3ElementPlusViewGenerator.generateView(selfAssociationEntity)
                .filter { GenerateTag.Table in it.tags }
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
import type {EditFormExpose} from "@/api/__generated/model/static/form/EditFormExpose"
import type {
    SelfAssociationEntityUpdateInput,
    SelfAssociationEntityOptionView
} from "@/api/__generated/model/static"
import {useRules} from "@/rules/SelfAssociationEntityEditFormRules"
import SelfAssociationEntityIdSelect from "@/components/selfAssociationEntity/SelfAssociationEntityIdSelect.vue"

const formData = defineModel<SelfAssociationEntityUpdateInput>({
    required: true
})

const props = withDefaults(defineProps<{
    submitLoading?: boolean | undefined,
    parentIdOptions: Array<SelfAssociationEntityOptionView>
}>(), {
    submitLoading: false
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
            prop="label"
            label="标签"
            @change="emits('query')"
        >
            <el-input
                v-model="formData.label"
                placeholder="请输入标签"
                clearable
            />
        </el-form-item>
        <el-form-item
            prop="parentId"
            label="父节点"
            @change="emits('query')"
        >
            <SelfAssociationEntityIdSelect
                v-model="formData.parentId"
                :options="parentIdOptions"
                :exclude-ids="[formData.id]"
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
), (rules/selfAssociationEntity/SelfAssociationEntityEditFormRules.ts, import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {SelfAssociationEntityUpdateInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<SelfAssociationEntityUpdateInput>): FormRules<SelfAssociationEntityUpdateInput> => {
    return {
        id: [
            {required: true, message: "id不能为空", trigger: "blur"},
            {type: "integer", message: "id必须是整数", trigger: "blur"},
        ],
        label: [
            {required: true, message: "标签不能为空", trigger: "blur"},
        ],
        parentId: [
        ],
    }
})]
            """.trimBlankLine(),
            Vue3ElementPlusViewGenerator.generateView(selfAssociationEntity)
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
import type {AddFormExpose} from "@/api/__generated/model/static/form/AddFormExpose"
import type {
    SelfAssociationEntityAddFormType,
    SelfAssociationEntityOptionView
} from "@/api/__generated/model/static"
import {createDefaultSelfAssociationEntity} from "@/components/selfAssociationEntity/createDefaultSelfAssociationEntity"
import {useRules} from "@/rules/SelfAssociationEntityEditTableRules"
import {Plus, Delete} from "@element-plus/icons-vue"
import SelfAssociationEntityIdSelect from "@/components/selfAssociationEntity/SelfAssociationEntityIdSelect.vue"

const rows = defineModel<Array<SelfAssociationEntityAddFormType>>({
    required: true
})

const props = withDefaults(defineProps<{
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined,
    submitLoading?: boolean | undefined,
    parentIdOptions: Array<SelfAssociationEntityOptionView>
}>(), {
    idColumn: false,
    indexColumn: false,
    multiSelect: true,
    submitLoading: false
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
            <el-table-column prop="label" label="标签">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'label']"
                        label="标签"
                        :rule="rules.label"
                    >
                        <el-input
                            v-model="rows.label"
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
                            v-model="rows.parentId"
                            :options="parentIdOptions"
                            :exclude-ids="[rows.id]"
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
            Vue3ElementPlusViewGenerator.generateView(selfAssociationEntity)
                .filter { GenerateTag.EditTable in it.tags }
                .map { it.path to it.content }.toString()
        )
    }
}