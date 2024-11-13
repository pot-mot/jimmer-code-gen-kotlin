package top.potmot.view.vue3.elementPlus.withEntity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.view.testEntity

class EntityEditFormTest {
    private val generator = Vue3ElementPlusViewGenerator

    @Test
    fun `test editFormRules`() {
        assertEquals(
            """
import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityUpdateInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<EntityUpdateInput>): FormRules<EntityUpdateInput> => {
    return {
        id: [
            {required: true, message: "id不能为空", trigger: "blur"},
            {type: "integer", message: "id必须是整数", trigger: "blur"},
        ],
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
            generator.stringifyEditFormRules(testEntity).trim()
        )
    }

    @Test
    fun `test editForm`() {
        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {EditFormExpose} from "@/api/__generated/model/static/form/EditFormExpose"
import type {EntityUpdateInput, ToOneEntityOptionView} from "@/api/__generated/model/static"
import {useRules} from "@/rules/EntityEditFormRules"
import EnumSelect from "@/components/enum/EnumSelect.vue"
import EnumNullableSelect from "@/components/enum/EnumNullableSelect.vue"
import ToOneEntityIdSelect from "@/components/toOneEntity/ToOneEntityIdSelect.vue"

const formData = defineModel<EntityUpdateInput>({
    required: true
})

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
            prop="enumProperty"
            label="enumProperty"
            @change="emits('query')"
        >
            <EnumSelect v-model="formData.enumProperty"/>
        </el-form-item>
        <el-form-item
            prop="enumNullableProperty"
            label="enumNullableProperty"
            @change="emits('query')"
        >
            <EnumNullableSelect v-model="formData.enumNullableProperty"/>
        </el-form-item>
        <el-form-item
            prop="toOnePropertyId"
            label="toOneProperty"
            @change="emits('query')"
        >
            <ToOneEntityIdSelect
                v-model="formData.toOnePropertyId"
                :options="toOnePropertyIdOptions"
            />
        </el-form-item>
        <el-form-item
            prop="toOneNullablePropertyId"
            label="toOneNullableProperty"
            @change="emits('query')"
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
            generator.stringifyEditForm(testEntity).trim()
        )
    }
}