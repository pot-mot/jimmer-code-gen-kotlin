package top.potmot.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.componentPath
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
}