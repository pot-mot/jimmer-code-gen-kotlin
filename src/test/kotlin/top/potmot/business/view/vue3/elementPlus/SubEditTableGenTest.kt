package top.potmot.business.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.edit.FormRefValidateItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.edit.editTable
import top.potmot.core.business.meta.SelectOption
import top.potmot.core.business.view.generate.staticPath

class SubEditTableGenTest {
    private val builder = Vue3ElementPlusViewGenerator.componentBuilder

    private val index = "${'$'}index"

    @Test
    fun `test editTable`() {
        val component = editTable(
            listOf("EntityInsertInput_TargetOf_entities", "EntityUpdateInput_TargetOf_entities"),
            staticPath,
            "EntityEntitiesEditTableData",
            "@/components/entity/EntityEntitiesEditTableData",
            "createDefaultEntityEditTableData",
            "@/components/entity/createDefaultEntityEntitiesEditTableData",
            "useRules",
            "@/rules/entity/EntityEntitiesEditTableRules",
            indent = "    ",
            idPropertyName = "id",
            comment = "comment",
            content = mapOf()
        )

        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityEditTableData} from "@/api/__generated/model/static"
import {createDefaultEntityEditTableData} from "@/components/entity/createDefaultEntityEditTableData"
import {useRules} from "@/rules/entity"
import {usePageSizeStore} from "@/stores/pageSizeStore"
import {Plus, Delete} from "@element-plus/icons-vue"
import {deleteConfirm} from "@/utils/confirm"

const formData = defineModel<Array<EntityEditTableData>>({
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
        formData: Array<EntityEditTableData>
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

const pageSizeStore = usePageSizeStore()

// 校验
const handleValidate = async (): Promise<boolean> => {
    const formValid: boolean =
        await formRef.value?.validate().catch(() => false) ?? false
    const typeValidate: boolean =
        validateEntityEditDataForSubmit(formData.value)

    return formValid && typeValidate
}

// 提交
const handleSubmit = async (): Promise<void> => {
    if (props.submitLoading) return

    const validResult = await handleValidate()
    if (validResult) {
        emits("submit", assertEntityEditDataAsSubmitType(formData.value))
    }
}

// 取消
const handleCancel = (): void => {
    emits("cancel")
}

// 多选
const selection = ref<Array<EntityEditTableData>>([])

const handleSelectionChange = (newSelection: Array<EntityEditTableData>): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    formData.value.push(createDefaultEntityEditTableData())
}

// 删除
const handleBatchDelete = async (): Promise<void> => {
    const result = await deleteConfirm("这些comment")
    if (!result) return
    formData.value = formData.value.filter(it => !selection.value.includes(it))
}

const handleSingleDelete = async (index: number): Promise<void> => {
    const result = await deleteConfirm("该comment")
    if (!result) return
    formData.value = formData.value.filter((_, i) => i !== index)
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
        label-width="auto"
        @submit.prevent
        class="add-form"
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
            :data="formData"
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
            """.trimIndent(),
            builder.build(component).trim()
        )
    }

    @Test
    fun `test editTable with validateItems`() {
        val component = editTable(
            listOf("EntityInsertInput_TargetOf_entities", "EntityUpdateInput_TargetOf_entities"),
            staticPath,
            "EntityEntitiesEditTableData",
            "@/components/entity/EntityEntitiesEditTableData",
            "createDefaultEntityEditTableData",
            "@/components/entity/createDefaultEntityEntitiesEditTableData",
            "useRules",
            "@/rules/entity/EntityEntitiesEditTableRules",
            indent = "    ",
            idPropertyName = "id",
            comment = "comment",
            content = mapOf(),
            subValidateItems = listOf(
                FormRefValidateItem("SubTable1", "SubTable1Ref", multiple = false),
                FormRefValidateItem("SubTable2", "SubTable2Ref", multiple = true),
            )
        )

        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityEditTableData} from "@/api/__generated/model/static"
import {createDefaultEntityEditTableData} from "@/components/entity/createDefaultEntityEditTableData"
import {useRules} from "@/rules/entity"
import {usePageSizeStore} from "@/stores/pageSizeStore"
import {Plus, Delete} from "@element-plus/icons-vue"
import {deleteConfirm} from "@/utils/confirm"

const formData = defineModel<Array<EntityEditTableData>>({
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
        formData: Array<EntityEditTableData>
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

const pageSizeStore = usePageSizeStore()

const subTable1Ref = ref<FormExpose>()
const subTable2Ref = ref<FormExpose>()

// 校验
const handleValidate = async (): Promise<boolean> => {
    const formValid: boolean | undefined = await formRef.value?.validate().catch(() => false)
    const subTable1Valid: boolean | undefined = await subTable1Ref.value?.validate().catch(() => false)
    const subTable2Valid: boolean | undefined = await subTable2Ref.value?.validate().catch(() => false)

    if (formValid && subTable1Valid && subTable2Valid) {
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
        emits("submit", formData.value)
    }
}

// 取消
const handleCancel = (): void => {
    emits("cancel")
}

// 多选
const selection = ref<Array<EntityEditTableData>>([])

const handleSelectionChange = (newSelection: Array<EntityEditTableData>): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    formData.value.push(createDefaultEntityEditTableData())
}

// 删除
const handleBatchDelete = async (): Promise<void> => {
    const result = await deleteConfirm("这些comment")
    if (!result) return
    formData.value = formData.value.filter(it => !selection.value.includes(it))
}

const handleSingleDelete = async (index: number): Promise<void> => {
    const result = await deleteConfirm("该comment")
    if (!result) return
    formData.value = formData.value.filter((_, i) => i !== index)
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
        label-width="auto"
        @submit.prevent
        class="add-form"
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
            :data="formData"
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
            """.trimIndent(),
            builder.build(component).trim()
        )
    }

    @Test
    fun `test editTable with selectOptions`() {
        val component = editTable(
            listOf("EntityInsertInput_TargetOf_entities", "EntityUpdateInput_TargetOf_entities"),
            staticPath,
            "EntityEntitiesEditTableData",
            "@/components/entity/EntityEntitiesEditTableData",
            "createDefaultEntityEditTableData",
            "@/components/entity/createDefaultEntityEntitiesEditTableData",
            "useRules",
            "@/rules/entity/EntityEntitiesEditTableRules",
            indent = "    ",
            idPropertyName = "id",
            comment = "comment",
            content = mapOf(),
            selectOptions = listOf(
                SelectOption("CustomerOptions", "顾客选项", "CustomerOptionView", staticPath, "customerService"),
                SelectOption("TypeOptions", "种类选项", "TypeOptionView", staticPath, "typeService"),
            )
        )

        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {
    EntityEditTableData,
    CustomerOptionView,
    TypeOptionView
} from "@/api/__generated/model/static"
import {createDefaultEntityEditTableData} from "@/components/entity/createDefaultEntityEditTableData"
import {useRules} from "@/rules/entity"
import {usePageSizeStore} from "@/stores/pageSizeStore"
import {Plus, Delete} from "@element-plus/icons-vue"
import {deleteConfirm} from "@/utils/confirm"

const formData = defineModel<Array<EntityEditTableData>>({
    required: true
})

const props = withDefaults(defineProps<{
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined,
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined,
    CustomerOptions: LazyOptions<CustomerOptionView>,
    TypeOptions: LazyOptions<TypeOptionView>
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
        formData: Array<EntityEditTableData>
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

const pageSizeStore = usePageSizeStore()

// 校验
const handleValidate = async (): Promise<boolean> => {
    const formValid: boolean =
        await formRef.value?.validate().catch(() => false) ?? false
    const typeValidate: boolean =
        validateEntityEditDataForSubmit(formData.value)

    return formValid && typeValidate
}

// 提交
const handleSubmit = async (): Promise<void> => {
    if (props.submitLoading) return

    const validResult = await handleValidate()
    if (validResult) {
        emits("submit", assertEntityEditDataAsSubmitType(formData.value))
    }
}

// 取消
const handleCancel = (): void => {
    emits("cancel")
}

// 多选
const selection = ref<Array<EntityEditTableData>>([])

const handleSelectionChange = (newSelection: Array<EntityEditTableData>): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    formData.value.push(createDefaultEntityEditTableData())
}

// 删除
const handleBatchDelete = async (): Promise<void> => {
    const result = await deleteConfirm("这些comment")
    if (!result) return
    formData.value = formData.value.filter(it => !selection.value.includes(it))
}

const handleSingleDelete = async (index: number): Promise<void> => {
    const result = await deleteConfirm("该comment")
    if (!result) return
    formData.value = formData.value.filter((_, i) => i !== index)
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
        label-width="auto"
        @submit.prevent
        class="add-form"
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
            :data="formData"
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
            """.trimIndent(),
            builder.build(component).trim()
        )
    }
}