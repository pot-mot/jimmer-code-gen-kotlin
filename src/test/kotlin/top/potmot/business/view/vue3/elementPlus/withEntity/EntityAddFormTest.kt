package top.potmot.business.view.vue3.elementPlus.withEntity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.business.testEntity

class EntityAddFormTest {
    private val generator = Vue3ElementPlusViewGenerator

    @Test
    fun `test addFormType`() {
        assertEquals(
            """
import type {Enum} from "@/api/__generated/model/enums"

export type EntityAddFormType = {
    enumProperty: Enum
    enumNullableProperty: Enum | undefined
    toOnePropertyId: number | undefined
    toOneNullablePropertyId: number | undefined
}
            """.trimIndent(),
            generator.stringifyAddFormType(testEntity).trim()
        )
    }

    @Test
    fun `test addFormDefault`() {
        assertEquals(
            """
import type {EntityAddFormType} from "./EntityAddFormType"

export const createDefaultEntity = (): EntityAddFormType => {
    return {
        enumProperty: "item1",
        enumNullableProperty: undefined,
        toOnePropertyId: undefined,
        toOneNullablePropertyId: undefined,
    }
}
            """.trimIndent(),
            generator.stringifyAddFormCreateDefault(testEntity).trim()
        )
    }

    @Test
    fun `test addFormRules`() {
        assertEquals(
            """
import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityInsertInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<EntityInsertInput>): FormRules<EntityInsertInput> => {
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
    }
}
            """.trimIndent(),
            generator.stringifyAddFormRules(testEntity).trim()
        )
    }

    @Test
    fun `test addForm`() {
        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {AddFormExpose} from "@/api/__generated/model/static/form/AddFormExpose"
import type {
    EntityInsertInput,
    EntityAddFormType,
    ToOneEntityOptionView
} from "@/api/__generated/model/static"
import {createDefaultEntity} from "@/components/entity/createDefaultEntity"
import {useRules} from "@/rules/EntityAddFormRules"
import EnumSelect from "@/components/enum/EnumSelect.vue"
import EnumNullableSelect from "@/components/enum/EnumNullableSelect.vue"
import ToOneEntityIdSelect from "@/components/toOneEntity/ToOneEntityIdSelect.vue"
import {sendMessage} from "@/utils/message"

const props = withDefaults(defineProps<{
    submitLoading?: boolean | undefined,
    toOnePropertyIdOptions: Array<ToOneEntityOptionView>,
    toOneNullablePropertyIdOptions: Array<ToOneEntityOptionView>
}>(), {
    submitLoading: false
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

// 提交
const handleSubmit = async (): Promise<void> => {
    if (props.submitLoading) return

    const formValid: boolean | undefined = await formRef.value?.validate().catch(() => false)

    if (formValid) {
        if (formData.value.toOnePropertyId === undefined) {
            sendMessage("toOneProperty不可为空", "warning")
            return
        }

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
            generator.stringifyAddForm(testEntity).trim()
        )
    }
}