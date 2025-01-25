package top.potmot.business.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.FormRefValidateItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.subForm
import top.potmot.core.business.view.generate.impl.vue3elementPlus.selectOptions.SelectOption
import top.potmot.core.business.view.generate.staticPath

class SubFormGenTest {
    private val builder = Vue3ElementPlusViewGenerator.componentBuilder

    private val index = "${'$'}index"

    @Test
    fun `test subForm`() {
        val component = subForm(
            listOf("EntityInsertInput_TargetOf_entities", "EntityUpdateInput_TargetOf_entities"),
            staticPath,
            "EntityEntitySubFormData",
            "@/components/entity/EntityEntitySubFormData",
            "createDefaultEntitySubFormData",
            "@/components/entity/createDefaultEntityEntitySubFormData",
            "useRules",
            "@/rules/entity/EntityEntitySubFormRules",
            indent = "    ",
            content = mapOf()
        )

        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntitySubFormData} from "@/api/__generated/model/static"
import {createDefaultEntitySubFormData} from "@/components/entity/createDefaultEntitySubFormData"
import {useRules} from "@/rules/entity"
import {usePageSizeStore} from "@/stores/pageSizeStore"
import {Plus, Delete} from "@element-plus/icons-vue"
import {deleteConfirm} from "@/utils/confirm"

const formData = defineModel<Array<EntitySubFormData>>({
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
        formData: Array<EntitySubFormData>
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

// 多选
const selection = ref<Array<EntitySubFormData>>([])

const handleSelectionChange = (newSelection: Array<EntitySubFormData>): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    formData.value.push(createDefaultEntitySubFormData())
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
    fun `test subForm with validateItems`() {
        val component = subForm(
            listOf("EntityInsertInput_TargetOf_entities", "EntityUpdateInput_TargetOf_entities"),
            staticPath,
            "EntityEntitySubFormData",
            "@/components/entity/EntityEntitySubFormData",
            "createDefaultEntitySubFormData",
            "@/components/entity/createDefaultEntityEntitySubFormData",
            "useRules",
            "@/rules/entity/EntityEntitySubFormRules",
            indent = "    ",
            content = mapOf(),
            subValidateItems = listOf(
                FormRefValidateItem("SubTable1"),
                FormRefValidateItem("SubTable2"),
            )
        )

        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntitySubFormData} from "@/api/__generated/model/static"
import {createDefaultEntitySubFormData} from "@/components/entity/createDefaultEntitySubFormData"
import {useRules} from "@/rules/entity"
import {usePageSizeStore} from "@/stores/pageSizeStore"
import {Plus, Delete} from "@element-plus/icons-vue"
import {deleteConfirm} from "@/utils/confirm"

const formData = defineModel<Array<EntitySubFormData>>({
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
        formData: Array<EntitySubFormData>
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
const selection = ref<Array<EntitySubFormData>>([])

const handleSelectionChange = (newSelection: Array<EntitySubFormData>): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    formData.value.push(createDefaultEntitySubFormData())
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
    fun `test subForm with selectOptions`() {
        val component = subForm(
            listOf("EntityInsertInput_TargetOf_entities", "EntityUpdateInput_TargetOf_entities"),
            staticPath,
            "EntityEntitySubFormData",
            "@/components/entity/EntityEntitySubFormData",
            "createDefaultEntitySubFormData",
            "@/components/entity/createDefaultEntityEntitySubFormData",
            "useRules",
            "@/rules/entity/EntityEntitySubFormRules",
            indent = "    ",
            content = mapOf(),
            selectOptions = listOf(
                SelectOption("CustomerOptions", "CustomerOptionView", "customerService"),
                SelectOption("TypeOptions", "TypeOptionView", "typeService"),
            )
        )

        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {
    EntitySubFormData,
    CustomerOptionView,
    TypeOptionView
} from "@/api/__generated/model/static"
import {createDefaultEntitySubFormData} from "@/components/entity/createDefaultEntitySubFormData"
import {useRules} from "@/rules/entity"
import {usePageSizeStore} from "@/stores/pageSizeStore"
import {Plus, Delete} from "@element-plus/icons-vue"
import {deleteConfirm} from "@/utils/confirm"

const formData = defineModel<Array<EntitySubFormData>>({
    required: true
})

const props = withDefaults(defineProps<{
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined,
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined,
    CustomerOptions: Array<CustomerOptionView>,
    TypeOptions: Array<TypeOptionView>
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
        formData: Array<EntitySubFormData>
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

// 多选
const selection = ref<Array<EntitySubFormData>>([])

const handleSelectionChange = (newSelection: Array<EntitySubFormData>): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    formData.value.push(createDefaultEntitySubFormData())
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