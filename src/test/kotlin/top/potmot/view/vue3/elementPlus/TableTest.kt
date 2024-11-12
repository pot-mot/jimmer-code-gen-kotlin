package top.potmot.view.vue3.elementPlus

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.impl.vue3elementPlus.table.viewTable
import top.potmot.core.business.view.generate.staticPath

class TableTest {
    private val builder = Vue3ComponentBuilder()

    @Test
    fun `test base table`() {
        assertEquals(
            """
<script setup lang="ts">
import type {EntityListView} from "@/api/__generated/model/static"

withDefaults(defineProps<{
    rows: Array<EntityListView>,
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined
}>(), {
    idColumn: false,
    indexColumn: true,
    multiSelect: true
})

const emits = defineEmits<{
    (
        event: "selectionChange",
        selection: Array<EntityListView>
    ): void
}>()

defineSlots<{
    operations(props: {row: EntityListView, index: number}): any
}>()

const handleSelectionChange = (newSelection: Array<EntityListView>): void => {
    emits("selectionChange", newSelection)
}
</script>

<template>
    <el-table
        :data="rows"
        row-key="id"
        border
        stripe
        @selection-change="handleSelectionChange"
    >
        <el-table-column
            v-if="idColumn"
            prop="id"
            label="ID"
            fixed
        />
        <el-table-column v-if="indexColumn" type="index" fixed/>
        <el-table-column
            v-if="multiSelect"
            type="selection"
            fixed
        />
        <el-table-column label="操作" fixed="right">
            <template #default="scope">
                <slot
                    name="operations"
                    :row="scope.row as EntityListView"
                    :index="scope.${'$'}index"
                />
            </template>
        </el-table-column>
    </el-table>
</template>
            """.trimIndent(),
            builder.build(
                viewTable(
                    "rows",
                    "EntityListView",
                    staticPath,
                    "id",
                    content = emptyMap()
                )
            ).trim(),
        )
    }
}