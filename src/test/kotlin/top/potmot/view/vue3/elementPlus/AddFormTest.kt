package top.potmot.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.componentPath
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.SelectOption
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.SubValidateItem
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.addForm
import top.potmot.core.business.view.generate.rulePath
import top.potmot.core.business.view.generate.staticPath

class AddFormTest {
    private val builder = Vue3ComponentBuilder()

    @Test
    fun `test addForm`() {
        val component = addForm(
            "EntityInsertInput",
            staticPath,
            "EntityAddFormDataType",
            "$componentPath/entity/EntityAddFormDataType",
            "defaultEntityAddFormData",
            "$componentPath/entity/defaultEntityAddFormData",
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
import type {AddFormExpose} from "@/api/__generated/model/static/form/AddFormExpose"
import type {EntityInsertInput} from "@/api/__generated/model/static"
import type {EntityAddFormDataType} from "@/components/entity/EntityAddFormDataType"
import {cloneDeep} from "lodash"
import {defaultEntityAddFormData} from "@/components/entity/defaultEntityAddFormData"
import {useRules} from "@/rules/entity"

const props = withDefaults(defineProps<{
    submitLoading?: boolean | undefined
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

const formData = ref<EntityAddFormDataType>(cloneDeep(defaultEntityAddFormData))

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
    fun `test addForm with validateItems`() {
        val component = addForm(
            "EntityInsertInput",
            staticPath,
            "EntityAddFormDataType",
            "$componentPath/entity/EntityAddFormDataType",
            "defaultEntityAddFormData",
            "$componentPath/entity/defaultEntityAddFormData",
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
import type {AddFormExpose} from "@/api/__generated/model/static/form/AddFormExpose"
import type {EntityInsertInput} from "@/api/__generated/model/static"
import type {EntityAddFormDataType} from "@/components/entity/EntityAddFormDataType"
import {cloneDeep} from "lodash"
import {defaultEntityAddFormData} from "@/components/entity/defaultEntityAddFormData"
import {useRules} from "@/rules/entity"
import type {SubFormExpose} from "@/components/form/SubFormExpose"

const props = withDefaults(defineProps<{
    submitLoading?: boolean | undefined
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

const formData = ref<EntityAddFormDataType>(cloneDeep(defaultEntityAddFormData))

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
            "EntityAddFormDataType",
            "$componentPath/entity/EntityAddFormDataType",
            "defaultEntityAddFormData",
            "$componentPath/entity/defaultEntityAddFormData",
            "useRules",
            "$rulePath/entity",
            indent = "    ",
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
    EntityInsertInput,
    CustomerOptionView,
    TypeOptionView
} from "@/api/__generated/model/static"
import type {EntityAddFormDataType} from "@/components/entity/EntityAddFormDataType"
import {cloneDeep} from "lodash"
import {defaultEntityAddFormData} from "@/components/entity/defaultEntityAddFormData"
import {useRules} from "@/rules/entity"

const props = withDefaults(defineProps<{
    submitLoading?: boolean | undefined,
    CustomerOptions: Array<CustomerOptionView>,
    TypeOptions: Array<TypeOptionView>
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

const formData = ref<EntityAddFormDataType>(cloneDeep(defaultEntityAddFormData))

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