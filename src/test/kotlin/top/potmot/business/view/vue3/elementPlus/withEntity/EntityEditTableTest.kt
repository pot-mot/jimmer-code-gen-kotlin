package top.potmot.business.view.vue3.elementPlus.withEntity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.business.testEntity

class EntityEditTableTest {
    private val index = "${'$'}index"

    private val generator = Vue3ElementPlusViewGenerator

    @Test
    fun `test editTableRules`() {
        assertEquals(
            """
import type {Ref} from "vue"
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
        ],
        toOneNullablePropertyId: [
        ],
    }
}
            """.trimIndent(),
            generator.stringifyEditTableRules(testEntity).trim()
        )
    }

    @Test
    fun `test editTable`() {
        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {AddFormExpose} from "@/api/__generated/model/static/form/AddFormExpose"
import type {EntityAddFormType, ToOneEntityOptionView} from "@/api/__generated/model/static"
import {cloneDeep} from "lodash"
import {defaultEntity} from "@/components/entity/defaultEntity"
import {useRules} from "@/rules/EntityEditTableRules"
import {Plus, Delete} from "@element-plus/icons-vue"
import EnumSelect from "@/components/enum/EnumSelect.vue"
import EnumNullableSelect from "@/components/enum/EnumNullableSelect.vue"
import ToOneEntityIdSelect from "@/components/toOneEntity/ToOneEntityIdSelect.vue"

const rows = defineModel<Array<EntityAddFormType>>({
    required: true
})

const props = withDefaults(defineProps<{
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined,
    submitLoading?: boolean | undefined,
    toOnePropertyIdOptions: Array<ToOneEntityOptionView>,
    toOneNullablePropertyIdOptions: Array<ToOneEntityOptionView>
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
const selection = ref<Array<EntityAddFormType>>([])

const handleSelectionChange = (newSelection: Array<EntityAddFormType>): void => {
    selection.value = newSelection
}

// 新增
const handleAdd = (): void => {
    rows.value.push(cloneDeep(defaultEntity))
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
            <el-table-column prop="enumProperty" label="enumProperty">
                <template #default="scope">
                    <el-form-item
                        :prop="[scope.$index, 'enumProperty']"
                        label="enumProperty"
                        :rule="rules.enumProperty"
                    >
                        <EnumSelect v-model="rows.enumProperty"/>
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
                        <EnumNullableSelect v-model="rows.enumNullableProperty"/>
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
                            v-model="rows.toOnePropertyId"
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
                            v-model="rows.toOneNullablePropertyId"
                            :options="toOneNullablePropertyIdOptions"
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
            """.trimIndent(),
            generator.stringifyEditTable(testEntity).trim()
        )
    }
}