package top.potmot.business.enum

const val vue3ElementPlusResult = """
[(components/enumTest/EnumTestView.vue, <script setup lang="ts">
import type {EnumTest} from "@/api/__generated/model/enums"

defineProps<{
    value: EnumTest
}>()
</script>

<template>
    <el-text v-if="value === 'Item1'">选项1</el-text>
    <el-text v-else-if="value === 'Item2'">选项2</el-text>
    <el-text v-else-if="value === 'Item3'">选项3</el-text>
</template>), (components/enumTest/EnumTestSelect.vue, <script setup lang="ts">
import {EnumTest_CONSTANTS} from "@/api/__generated/model/enums"
import type {EnumTest} from "@/api/__generated/model/enums"
import EnumTestView from "@/components/enumTest/EnumTestView.vue"

const data = defineModel<EnumTest>({
    required: true
})
</script>

<template>
    <el-select
        v-model="data"
        placeholder="请选择测试">
        <el-option
            v-for="value in EnumTest_CONSTANTS" 
            :value="value">
            <EnumTestView :value="value"/>
        </el-option>
        <template #label>
            <EnumTestView :value="data"/>
        </template>
    </el-select>
</template>), (components/enumTest/EnumTestNullableSelect.vue, <script setup lang="ts">
import {EnumTest_CONSTANTS} from "@/api/__generated/model/enums"
import type {EnumTest} from "@/api/__generated/model/enums"
import EnumTestView from "@/components/enumTest/EnumTestView.vue"

const data = defineModel<EnumTest | undefined>({
    required: true
})
</script>

<template>
    <el-select
        v-model="data"
        placeholder="请选择测试"
        clearable
        :empty-values="[undefined]"
        :value-on-clear="undefined">
        <el-option
            v-for="value in EnumTest_CONSTANTS" 
            :value="value">
            <EnumTestView :value="value"/>
        </el-option>
        <template #label>
            <EnumTestView v-if="data" :value="data"/>
        </template>
    </el-select>
</template>)]
"""