package top.potmot.business.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.edit.addForm
import top.potmot.core.business.view.generate.impl.vue3elementPlus.edit.FormRefValidateItem
import top.potmot.core.business.meta.SelectOption
import top.potmot.core.business.view.generate.staticPath

class AddFormGenTest {
    private val builder = Vue3ElementPlusViewGenerator.componentBuilder

    @Test
    fun `test addForm`() {
        val component = addForm(
            "EntityInsertInput",
            staticPath,
            "EntityAddFormData",
            "@/components/entity/EntityAddFormData",
            "createDefaultEntityAddFormData",
            "@/components/entity/createDefaultEntityAddFormData",
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
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityInsertInput} from "@/api/__generated/model/static"
import type {EntityAddFormData} from "@/components/entity/EntityAddFormData"
import {createDefaultEntityAddFormData} from "@/components/entity/createDefaultEntityAddFormData"
import {useRules} from "@/rules/entity"

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined
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

const formData = ref<EntityAddFormData>(createDefaultEntityAddFormData())

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
    fun `test addForm with validateItems`() {
        val component = addForm(
            "EntityInsertInput",
            staticPath,
            "EntityAddFormData",
            "@/components/entity/EntityAddFormData",
            "createDefaultEntityAddFormData",
            "@/components/entity/createDefaultEntityAddFormData",
            "useRules",
            "@/rules/entity",
            indent = "    ",
            content = mapOf(),
            subValidateItems = listOf(
                FormRefValidateItem("SubTable1", "SubTable1Ref"),
                FormRefValidateItem("SubTable2", "SubTable2Ref"),
            )
        )

        assertEquals(
            """
<script setup lang="ts">
import {ref} from "vue"
import type {FormInstance} from "element-plus"
import type {FormExpose} from "@/components/form/FormExpose"
import type {EntityInsertInput} from "@/api/__generated/model/static"
import type {EntityAddFormData} from "@/components/entity/EntityAddFormData"
import {createDefaultEntityAddFormData} from "@/components/entity/createDefaultEntityAddFormData"
import {useRules} from "@/rules/entity"

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined
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

const formData = ref<EntityAddFormData>(createDefaultEntityAddFormData())

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
    fun `test addForm with selectOptions`() {
        val component = addForm(
            "EntityInsertInput",
            staticPath,
            "EntityAddFormData",
            "@/components/entity/EntityAddFormData",
            "createDefaultEntityAddFormData",
            "@/components/entity/createDefaultEntityAddFormData",
            "useRules",
            "@/rules/entity",
            indent = "    ",
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
    EntityInsertInput,
    CustomerOptionView,
    TypeOptionView
} from "@/api/__generated/model/static"
import type {EntityAddFormData} from "@/components/entity/EntityAddFormData"
import {createDefaultEntityAddFormData} from "@/components/entity/createDefaultEntityAddFormData"
import {useRules} from "@/rules/entity"

const props = withDefaults(defineProps<{
    withOperations?: boolean | undefined,
    submitLoading?: boolean | undefined,
    CustomerOptions: LazyOptions<CustomerOptionView>,
    TypeOptions: LazyOptions<TypeOptionView>
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

const formData = ref<EntityAddFormData>(createDefaultEntityAddFormData())

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