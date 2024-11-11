package top.potmot.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.addForm
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.editForm
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.editTable

class FormTest {
    private val builder = Vue3ComponentBuilder()

    private val index = "${'$'}index"

    @Test
    fun `test addForm`() {
        val component = addForm(
            "EntityInsertInput",
            "@/api/__generated/model/static",
            "EntityAddFormDataType",
            "@/component/entity/EntityAddFormDataType",
            "defaultEntityAddFormData",
            "@/component/entity/defaultEntityAddFormData",
            "useRules",
            "@/rules/entity",
            indent = "    ",
            content = mapOf()
        )

        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {AddFormExpose} from "@/api/__generated/model/static/form/AddFormExpose"
import type {EntityInsertInput} from "@/api/__generated/model/static"
import type {EntityAddFormDataType} from "@/component/entity/EntityAddFormDataType"
import {cloneDeep} from "lodash"
import {defaultEntityAddFormData} from "@/component/entity/defaultEntityAddFormData"
import {useRules} from "@/rules/entity"

const props = defineProps<{formData: EntityAddFormDataType}>()

const emits = defineEmits<{
    (event: "submit", formData: EntityInsertInput): void,
    (event: "cancel", ): void
}>()

defineSlots<{
    operations(props: {
        handleSubmit: () => Promise<void>,
        handleCancel: () => void
    }): any
}>()

const formData = ref<EntityAddFormDataType>(cloneDeep(defaultEntityAddFormData))

const formRef = ref<FormInstance>()
const rules = useRules(formData)

// 提交
const handleSubmit = async (): Promise<void> => {
    const formValid: boolean | undefined = await formRef.value?.validate().catch(() => false)
    if (formValid)
        emits("submit", formData.value)
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
        
        <slot
            name="operations"
            :handleSubmit="handleSubmit"
            :handleCancel="handleCancel"
        >
            <div style="text-align: right;">
                <el-button type="warning" @click="handleCancel">
                    取消
                </el-button>
                <el-button type="primary" @click="handleSubmit">
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
    fun `test editForm`() {
        val component = editForm(
            "EntityUpdateInput",
            "@/api/__generated/model/static",
            "useRules",
            "@/rules/entity",
            indent = "    ",
            content = mapOf()
        )

        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {EditFormExpose} from "@/api/__generated/model/static/form/EditFormExpose"
import type {EntityUpdateInput} from "@/api/__generated/model/static"
import {useRules} from "@/rules/entity"

const formData = defineModels<EntityUpdateInput>({required: true})

const emits = defineEmits<{
    (event: "submit", formData: EntityUpdateInput): void,
    (event: "cancel", ): void
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
    const formValid: boolean | undefined = await formRef.value?.validate().catch(() => false)
    if (formValid)
        emits("submit", formData.value)
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
        
        <slot
            name="operations"
            :handleSubmit="handleSubmit"
            :handleCancel="handleCancel"
        >
            <div style="text-align: right;">
                <el-button type="warning" @click="handleCancel">
                    取消
                </el-button>
                <el-button type="primary" @click="handleSubmit">
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
    fun `test editTable`() {
        val component = editTable(
            "EntitySubTableType",
            "@/api/__generated/model/static",
            "defaultEntityAddFormData",
            "@/component/entity/defaultEntityAddFormData",
            "useRules",
            "@/rules/entity",
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
import {defaultEntityAddFormData} from "@/component/entity/defaultEntityAddFormData"
import {useRules} from "@/rules/entity"

const formData = defineModels<Array<EntitySubTableType>>({required: true})

const props = withDefaults(defineProps<{
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    selectionColumn?: boolean | undefined
}>(), {
    idColumn: false,
    indexColumn: false,
    selectionColumn: true
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
    const formValid: boolean | undefined = await formRef.value?.validate().catch(() => false)
    if (formValid)
        emits("submit", formData.value)
}

// 取消
const handleCancel = (): void => {
    emits("cancel")
}

// 多选
const selection = ref<Array<EntitySubTableType>>([])

const changeSelection = (item: Array<EntitySubTableType>): void => {
    selection.value = item
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
        <el-table :data="formData" border stripe>
            <el-table-column
                v-if="idColumn"
                prop="id"
                label="ID"
                fixed
            />
            <el-table-column v-if="indexColumn" type="index" fixed/>
            <el-table-column
                v-if="selectionColumn"
                type="selection"
                fixed
            />
            <el-table-column label="操作" fixed="right">
                <template #default="scope">
                    <template #default="{,}">
                        <el-button
                            type="danger"
                            :icon="Delete"
                            @click="handleSingleDelete($index)"
                        />
                    </template>
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
                <el-button type="primary" @click="handleSubmit">
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