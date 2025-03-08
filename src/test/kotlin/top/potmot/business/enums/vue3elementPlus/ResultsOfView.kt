package top.potmot.business.enums.vue3elementPlus

const val vue3ElementPlusResult = """
[(components/enums/enumTest/EnumTestView.vue, <script setup lang="ts">
import type {EnumTest} from "@/api/__generated/model/enums"

defineProps<{value: EnumTest}>()
</script>

<template>
    <el-text v-if="value === 'Item1'">
        选项1
    </el-text>
    <el-text v-else-if="value === 'Item2'">
        选项2
    </el-text>
    <el-text v-else-if="value === 'Item3'">
        选项3
    </el-text>
</template>
), (components/enums/enumTest/EnumTestSelect.vue, <script setup lang="ts">
import {EnumTest_CONSTANTS} from "@/api/__generated/model/enums"
import type {EnumTest} from "@/api/__generated/model/enums"
import EnumTestView from "@/components/enums/enumTest/EnumTestView.vue"

const modelValue = defineModel<EnumTest>({
    required: true
})
</script>

<template>
    <el-select
        v-model="modelValue"
        placeholder="请选择测试"
        filterable
    >
        <el-option
            v-for="option in EnumTest_CONSTANTS"
            :value="option"
        >
            <EnumTestView :value="option"/>
        </el-option>
        <template #label>
            <EnumTestView :value="modelValue"/>
        </template>
    </el-select>
</template>
), (components/enums/enumTest/EnumTestNullableSelect.vue, <script setup lang="ts">
import {EnumTest_CONSTANTS} from "@/api/__generated/model/enums"
import type {EnumTest} from "@/api/__generated/model/enums"
import EnumTestView from "@/components/enums/enumTest/EnumTestView.vue"

const modelValue = defineModel<EnumTest | undefined>({
    required: true
})
</script>

<template>
    <el-select
        v-model="modelValue"
        placeholder="请选择测试"
        filterable
        clearable
        :value-on-clear="undefined"
    >
        <el-option
            v-for="option in EnumTest_CONSTANTS"
            :value="option"
        >
            <EnumTestView :value="option"/>
        </el-option>
        <template #label>
            <EnumTestView v-if="modelValue" :value="modelValue"/>
        </template>
    </el-select>
</template>
)]
"""