package top.potmot.business.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.SelectOption
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.SubValidateItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.editForm
import top.potmot.core.business.view.generate.rulePath
import top.potmot.core.business.view.generate.staticPath

class EditFormTest {
    private val builder = Vue3ComponentBuilder()

    @Test
    fun `test editForm`() {
        val component = editForm(
            "EntityUpdateInput",
            staticPath,
            "useRules",
            "$rulePath/entity",
            indent = "    ",
            content = mapOf()
        )

        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityUpdateInput} from "@/api/__generated/model/static"
import {useRules} from "@/rules/entity"

const formData = defineModel<EntityUpdateInput>({
    required: true
})

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined
}>(), {
    withOperations: true,
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
    >

        <slot
            v-if="withOperations"
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
    fun `test editForm with validateItems`() {
        val component = editForm(
            "EntityUpdateInput",
            staticPath,
            "useRules",
            "$rulePath/entity",
            indent = "    ",
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
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityUpdateInput} from "@/api/__generated/model/static"
import {useRules} from "@/rules/entity"

const formData = defineModel<EntityUpdateInput>({
    required: true
})

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined
}>(), {
    withOperations: true,
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

defineExpose<FormExpose>({
    validate: handleValidate
})
</script>

<template>
    <el-form
        :model="formData"
        ref="formRef"
        :rules="rules"
    >

        <slot
            v-if="withOperations"
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
    fun `test editForm with selectOptions`() {
        val component = editForm(
            "EntityUpdateInput",
            staticPath,
            "useRules",
            "$rulePath/entity",
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
    EntityUpdateInput,
    CustomerOptionView,
    TypeOptionView
} from "@/api/__generated/model/static"
import {useRules} from "@/rules/entity"

const formData = defineModel<EntityUpdateInput>({
    required: true
})

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined,
    CustomerOptions: Array<CustomerOptionView>,
    TypeOptions: Array<TypeOptionView>
}>(), {
    withOperations: true,
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
    >

        <slot
            v-if="withOperations"
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