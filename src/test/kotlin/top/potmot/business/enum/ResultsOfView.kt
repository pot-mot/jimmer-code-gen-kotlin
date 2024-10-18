package top.potmot.business.enum

const val vue3ElementPlusResult = """
[(/components/enumTest/EnumTestSelect.vue, <script setup lang="ts">
import {EnumTest_CONSTANTS} from "@/api/__generated/model/static"
import type {EnumTest} from "@/api/__generated/model/static"
import EnumTestView from "@/components/enumTest/EnumTestView.vue"

const data = defineModel<EnumTest>({
    required: true
})
</script>

<template>
    <el-select
        placeholder="请选择测试"
        v-model="data"
        clearable>
        <el-option
            v-for="value in EnumTest_CONSTANTS" 
            :value="value">
            <EnumTestView :value="value"/>
        </el-option>
    </el-select>
</template>), (/components/enumTest/EnumTestView.vue, <script setup lang="ts">
import type {EnumTest} from "@/api/__generated/model/static"

defineProps<{
    value: EnumTest
}>()
</script>

<template>
    <el-text v-if="value === 'Item1'">选项1</el-text>
    <el-text v-if="value === 'Item2'">选项2</el-text>
    <el-text v-if="value === 'Item3'">选项3</el-text>
</template>)]
"""