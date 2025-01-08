package top.potmot.business.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.impl.vue3elementPlus.form.SelectOption
import top.potmot.core.business.view.generate.impl.vue3elementPlus.queryForm.queryForm
import top.potmot.core.business.view.generate.staticPath

class QueryFormTest {
    private val builder = Vue3ComponentBuilder()

    @Test
    fun `test queryForm`() {
        val component = queryForm(
            "spec",
            "EntitySpecification",
            staticPath,
            content = mapOf(),
        )

        assertEquals(
            """
<script setup lang="ts">
import {Search} from "@element-plus/icons-vue"
import type {EntitySpecification} from "@/api/__generated/model/static"

const spec = defineModel<EntitySpecification>({
    required: true
})

const emits = defineEmits<{
    (
        event: "query",
        spec: EntitySpecification
    ): void
}>()
</script>

<template>
    <el-form :model="spec" @submit.prevent>
        <el-row :gutter="20">
            <el-button
                type="primary"
                :icon="Search"
                class="search-button"
                @click="emits('query', spec)"
            >
                查询
            </el-button>
        </el-row>
    </el-form>
</template>
            """.trimIndent(),
            builder.build(component).trim()
        )
    }

    @Test
    fun `test queryForm with selectOptions`() {
        val component = queryForm(
            "spec",
            "EntitySpecification",
            staticPath,
            content = mapOf(),
            selectOptions = listOf(
                SelectOption("CustomerOptions", "CustomerOptionView", "customerService"),
                SelectOption("TypeOptions", "TypeOptionView", "typeService"),
            )
        )

        assertEquals(
            """
<script setup lang="ts">
import {Search} from "@element-plus/icons-vue"
import type {
    EntitySpecification,
    CustomerOptionView,
    TypeOptionView
} from "@/api/__generated/model/static"

const spec = defineModel<EntitySpecification>({
    required: true
})

defineProps<{
    CustomerOptions: Array<CustomerOptionView>,
    TypeOptions: Array<TypeOptionView>
}>()

const emits = defineEmits<{
    (
        event: "query",
        spec: EntitySpecification
    ): void
}>()
</script>

<template>
    <el-form :model="spec" @submit.prevent>
        <el-row :gutter="20">
            <el-button
                type="primary"
                :icon="Search"
                class="search-button"
                @click="emits('query', spec)"
            >
                查询
            </el-button>
        </el-row>
    </el-form>
</template>
            """.trimIndent(),
            builder.build(component).trim()
        )
    }
}