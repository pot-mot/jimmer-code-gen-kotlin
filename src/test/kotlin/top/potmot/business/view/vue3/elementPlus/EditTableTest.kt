package top.potmot.business.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.SelectOption
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.SubValidateItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.editTable
import top.potmot.core.business.view.generate.rulePath
import top.potmot.core.business.view.generate.staticPath

class EditTableTest {
    private val builder = Vue3ComponentBuilder()

    private val index = "${'$'}index"

    @Test
    fun `test editTable`() {
        val component = editTable(
            "EntitySubTableType",
            staticPath,
            "defaultEntityAddFormData",
            "$componentPath/entity/defaultEntityAddFormData",
            "useRules",
            "$rulePath/entity",
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
import type {AddFormExpose} from "@/api/__generated/model/static/form/AddFormExpose"
import type {EntitySubTableType} from "@/api/__generated/model/static"
import {cloneDeep} from "lodash"
import {defaultEntityAddFormData} from "@/components/entity/defaultEntityAddFormData"
import {useRules} from "@/rules/entity"
import {Plus, Delete} from "@element-plus/icons-vue"

const formData = defineModel<Array<EntitySubTableType>>({
    required: true
})

const props = withDefaults(defineProps<{
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined,
    submitLoading?: boolean | undefined
}>(), {
    idColumn: false,
    indexColumn: false,
    multiSelect: true,
    submitLoading: false
})

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

// 多选
const selection = ref<Array<EntitySubTableType>>([])

const handleSelectionChange = (newSelection: Array<EntitySubTableType>): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    formData.value.push(cloneDeep(defaultEntityAddFormData))
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
</script>

<template>
    <el-form
        :model="formData"
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
                fixed
            />
            <el-table-column v-if="indexColumn" type="index" fixed/>
            <el-table-column
                v-if="multiSelect"
                type="selection"
                fixed
            />
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
            """.trimIndent(),
            builder.build(component).trim()
        )
    }

    @Test
    fun `test editTable with validateItems`() {
        val component = editTable(
            "EntitySubTableType",
            staticPath,
            "defaultEntityAddFormData",
            "$componentPath/entity/defaultEntityAddFormData",
            "useRules",
            "$rulePath/entity",
            indent = "    ",
            idPropertyName = "id",
            comment = "comment",
            content = mapOf(),
            subValidateItems = listOf(
                SubValidateItem("SubTable1"),
                SubValidateItem("SubTable2"),
            )
        )

        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {AddFormExpose} from "@/api/__generated/model/static/form/AddFormExpose"
import type {EntitySubTableType} from "@/api/__generated/model/static"
import {cloneDeep} from "lodash"
import {defaultEntityAddFormData} from "@/components/entity/defaultEntityAddFormData"
import {useRules} from "@/rules/entity"
import {Plus, Delete} from "@element-plus/icons-vue"
import type {SubFormExpose} from "@/components/form/SubFormExpose"

const formData = defineModel<Array<EntitySubTableType>>({
    required: true
})

const props = withDefaults(defineProps<{
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined,
    submitLoading?: boolean | undefined
}>(), {
    idColumn: false,
    indexColumn: false,
    multiSelect: true,
    submitLoading: false
})

defineSlots<{
    operations(props: {
        handleSubmit: () => Promise<void>,
        handleCancel: () => void
    }): any
}>()

const formRef = ref<FormInstance>()
const rules = useRules(formData)

const subTable1Ref = ref<SubFormExpose>()
const subTable2Ref = ref<SubFormExpose>()

// 提交
const handleSubmit = async (): Promise<void> => {
    if (props.submitLoading) return

    const formValid: boolean | undefined = await formRef.value?.validate().catch(() => false)
    const subTable1Valid: boolean | undefined = await subTable1Ref.value?.formRef?.validate().catch(() => false)
    const subTable2Valid: boolean | undefined = await subTable2Ref.value?.formRef?.validate().catch(() => false)

    if (formValid && subTable1Valid && subTable2Valid) {
        emits("submit", formData.value)
    }
}

// 取消
const handleCancel = (): void => {
    emits("cancel")
}

// 多选
const selection = ref<Array<EntitySubTableType>>([])

const handleSelectionChange = (newSelection: Array<EntitySubTableType>): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    formData.value.push(cloneDeep(defaultEntityAddFormData))
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
</script>

<template>
    <el-form
        :model="formData"
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
                fixed
            />
            <el-table-column v-if="indexColumn" type="index" fixed/>
            <el-table-column
                v-if="multiSelect"
                type="selection"
                fixed
            />
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
            """.trimIndent(),
            builder.build(component).trim()
        )
    }

    @Test
    fun `test editTable with selectOptions`() {
        val component = editTable(
            "EntitySubTableType",
            staticPath,
            "defaultEntityAddFormData",
            "$componentPath/entity/defaultEntityAddFormData",
            "useRules",
            "$rulePath/entity",
            indent = "    ",
            idPropertyName = "id",
            comment = "comment",
            content = mapOf(),
            selectOptions = listOf(
                SelectOption("CustomerOptions", "CustomerOptionView"),
                SelectOption("TypeOptions", "TypeOptionView"),
            )
        )

        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {AddFormExpose} from "@/api/__generated/model/static/form/AddFormExpose"
import type {
    EntitySubTableType,
    CustomerOptionView,
    TypeOptionView
} from "@/api/__generated/model/static"
import {cloneDeep} from "lodash"
import {defaultEntityAddFormData} from "@/components/entity/defaultEntityAddFormData"
import {useRules} from "@/rules/entity"
import {Plus, Delete} from "@element-plus/icons-vue"

const formData = defineModel<Array<EntitySubTableType>>({
    required: true
})

const props = withDefaults(defineProps<{
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined,
    submitLoading?: boolean | undefined,
    CustomerOptions: Array<CustomerOptionView>,
    TypeOptions: Array<TypeOptionView>
}>(), {
    idColumn: false,
    indexColumn: false,
    multiSelect: true,
    submitLoading: false
})

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

// 多选
const selection = ref<Array<EntitySubTableType>>([])

const handleSelectionChange = (newSelection: Array<EntitySubTableType>): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    formData.value.push(cloneDeep(defaultEntityAddFormData))
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
</script>

<template>
    <el-form
        :model="formData"
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
                fixed
            />
            <el-table-column v-if="indexColumn" type="index" fixed/>
            <el-table-column
                v-if="multiSelect"
                type="selection"
                fixed
            />
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
            """.trimIndent(),
            builder.build(component).trim()
        )
    }
}