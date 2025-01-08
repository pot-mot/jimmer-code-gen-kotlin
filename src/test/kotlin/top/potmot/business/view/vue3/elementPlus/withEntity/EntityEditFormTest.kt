package top.potmot.business.view.vue3.elementPlus.withEntity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.business.testEntity

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
    toOneNullablePropertyIdOptions: Array<ToOneEntityOptionView>
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
            generator.stringifyEditForm(testEntity).trim()
        )
    }
}