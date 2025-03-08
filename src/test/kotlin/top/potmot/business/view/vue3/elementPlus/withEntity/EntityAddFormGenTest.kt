package top.potmot.business.view.vue3.elementPlus.withEntity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.business.testEntityBusiness

class EntityAddFormGenTest {
    private val generator = Vue3ElementPlusViewGenerator

    @Test
    fun `test addFormType`() {
        assertEquals(
            """
import type {Enum} from "@/api/__generated/model/enums"

export type EntityAddData = {
    enumProperty: Enum
    enumNullableProperty: Enum | undefined
    toOnePropertyId: number | undefined
    toOneNullablePropertyId: number | undefined
}
            """.trimIndent(),
            generator.addFormFiles(testEntityBusiness).files.first { it.name == "EntityAddData.ts" }.content.trim()
        )
    }

    @Test
    fun `test addFormDefault`() {
        assertEquals(
            """
import type {EntityAddData} from "./EntityAddData"

export const createDefaultEntity = (): EntityAddData => {
    return {
        enumProperty: "item1",
        enumNullableProperty: undefined,
        toOnePropertyId: undefined,
        toOneNullablePropertyId: undefined
    }
}
            """.trimIndent(),
            generator.addFormFiles(testEntityBusiness).files.first { it.name == "createDefaultEntity.ts" }.content.trim()
        )
    }

    @Test
    fun `test addFormRules`() {
        assertEquals(
            """
import type {Ref} from "vue"
import type {FormRules} from "element-plus"
import type {EntityAddData} from "@/components/entity/EntityAddData"
import type {EntityInsertInput} from "@/api/__generated/model/static"

export const useRules = (_: Ref<EntityAddData>): FormRules<EntityInsertInput> => {
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
            generator.addFormFiles(testEntityBusiness).files.first { it.name == "EntityAddFormRules.ts" }.content.trim()
        )
    }

    @Test
    fun `test addForm`() {
        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityInsertInput, ToOneEntityOptionView} from "@/api/__generated/model/static"
import type {EntityAddData} from "@/components/entity/EntityAddData"
import {createDefaultEntity} from "@/components/entity/createDefaultEntity"
import {useRules} from "@/rules/entity/EntityAddFormRules"
import EnumSelect from "@/components/enums/enum/EnumSelect.vue"
import EnumNullableSelect from "@/components/enums/enum/EnumNullableSelect.vue"
import ToOneEntityIdSelect from "@/components/toOneEntity/ToOneEntityIdSelect.vue"
import {sendMessage} from "@/utils/message"

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined,
    toOnePropertyIdOptions: LazyOptions<ToOneEntityOptionView>,
    toOneNullablePropertyIdOptions: LazyOptions<ToOneEntityOptionView>
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

const formData = ref<EntityAddData>(createDefaultEntity())

const formRef = ref<FormInstance>()
const rules = useRules(formData)

// 校验
const handleValidate = async (): Promise<boolean> => {
    const formValid: boolean =
        await formRef.value?.validate().catch(() => false) ?? false
    const typeValidate: boolean =
        validateEntityAddDataForSubmit(formData.value)

    return formValid && typeValidate
}

// 提交
const handleSubmit = async (): Promise<void> => {
    if (props.submitLoading) return

    const validResult = await handleValidate()
    if (validResult) {
        emits("submit", assertEntityAddDataAsSubmitType(formData.value))
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
        label-width="auto"
        @submit.prevent
        class="add-form"
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
            generator.addFormFiles(testEntityBusiness).files.first { it.name == "EntityAddForm.vue" }.content.trim()
        )
    }
}