package top.potmot.business.shortAssociation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.dto.generate.DtoGenerator
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.enumeration.GenerateTag

class TestShortAssociation {
    private val index = "${'$'}index"

    private val GenEntityBusinessView.result
        get() = DtoGenerator.generateDto(this).let { it.path to it.content }.toString()

    @Test
    fun `test shortAssociation dto`() {
        assertEquals(
            """
(dto/ShortAssociationEntity.dto, export EntityPackage.ShortAssociationEntity

ShortAssociationEntityListView {
    #allScalars
}

ShortAssociationEntityDetailView {
    #allScalars
}

ShortAssociationEntityOptionView {
    id
    label1
    label2
}

input ShortAssociationEntityInsertInput {
    #allScalars
    -id
}

ShortAssociationEntityUpdateFillView {
    #allScalars
}

input ShortAssociationEntityUpdateInput {
    #allScalars
    id!
}

specification ShortAssociationEntitySpec {
    eq(id)
    like/i(label1)
    like/i(label2)
})
            """.trimIndent(),
            shortAssociationEntity.result
        )
    }

    @Test
    fun `test shortAssociation view`() {
        assertEquals(
            """
[(components/shortAssociationEntity/ShortAssociationEntityIdSelect.vue, <script setup lang="ts">
import {watch} from "vue"
import type {ShortAssociationEntityOptionView} from "@/api/__generated/model/static"

const modelValue = defineModel<number | undefined>({
    required: true
})

const props = defineProps<{
    options: Array<ShortAssociationEntityOptionView>
}>()

watch(() => [modelValue.value, props.options], () => {
    if (!(props.options.map(it => it.id) as Array<number | undefined>).includes(modelValue.value)) {
        modelValue.value = undefined
    }
}, {immediate: true})
</script>

<template>
    <el-select
        v-model="modelValue"
        placeholder="请选择shortAssociationEntityComment"
        filterable
        clearable
        :value-on-clear="undefined"
    >
        <el-option
            v-for="option in options"
            :key="option.id"
            :value="option.id"
            :label="`${'$'}{option.label1}_${'$'}{option.label2}`"
        />
    </el-select>
</template>
), (components/shortAssociationEntity/ShortAssociationEntityIdMultiSelect.vue, <script setup lang="ts">
import {watch} from "vue"
import type {ShortAssociationEntityOptionView} from "@/api/__generated/model/static"

const modelValue = defineModel<Array<number>>({
    required: true
})

const props = defineProps<{
    options: Array<ShortAssociationEntityOptionView>
}>()

watch(() => [modelValue.value, props.options], () => {
    const newModelValue: Array<number> = []

    for (const item of modelValue.value) {
        if (props.options.map(it => it.id).includes(item)) {
            newModelValue.push(item)
        }
    }
    if (modelValue.value.length != newModelValue.length)
        modelValue.value = newModelValue
}, {immediate: true})
</script>

<template>
    <el-select
        v-model="modelValue"
        placeholder="请选择shortAssociationEntityComment"
        filterable
        clearable
        :value-on-clear="undefined"
        multiple
        collapse-tags
        collapse-tags-tooltip
    >
        <el-option
            v-for="option in options"
            :key="option.id"
            :value="option.id"
            :label="`${'$'}{option.label1}_${'$'}{option.label2}`"
        />
    </el-select>
</template>
)]
            """.trimIndent(),
            Vue3ElementPlusViewGenerator.generateView(shortAssociationEntity)
                .filter { GenerateTag.IdSelect in it.tags || GenerateTag.IdMultiSelect in it.tags }
                .map { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test shortAssociationTarget dto`() {
        assertEquals(
            """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    shortAssociationProperty {
        label1
        label2
    }
}

EntityDetailView {
    #allScalars
    shortAssociationProperty {
        label1
        label2
    }
}

EntityOptionView {
    id
}

input EntityInsertInput {
    #allScalars
    -id
    id(shortAssociationProperty)
}

EntityUpdateFillView {
    #allScalars
    id(shortAssociationProperty)
}

input EntityUpdateInput {
    #allScalars
    id!
    id(shortAssociationProperty)
}

specification EntitySpec {
    associatedIdEq(shortAssociationProperty)
})
            """.trimIndent(),
            DtoGenerator.generateDto(shortAssociationTargetEntity).let { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test shortAssociationTarget view`() {
        val viewItems = Vue3ElementPlusViewGenerator.generateView(shortAssociationTargetEntity)

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
        <el-table-column
            prop="shortAssociationProperty.label1"
            label="commentlabel1"
        />
        <el-table-column
            prop="shortAssociationProperty.label2"
            label="commentlabel2"
        />
        <el-table-column label="操作" fixed="right">
            <template #default="scope">
                <slot
                    name="operations"
                    :row="scope.row as EntityListView"
                    :index="scope.$index"
                />
            </template>
        </el-table-column>
    </el-table>
</template>
            """.trimIndent(),
            viewItems.filter { GenerateTag.Table in it.tags }[0].content.trim()
        )

        viewItems.filter { (GenerateTag.AddForm in it.tags || GenerateTag.EditForm in it.tags || GenerateTag.EditTable in it.tags) && GenerateTag.Rules !in it.tags }.forEach {
            assert(it.content.contains("shortAssociationPropertyIdOptions"))
            assert(it.content.contains("ShortAssociationEntityIdSelect"))
        }

        viewItems.filter { (GenerateTag.AddForm in it.tags || GenerateTag.EditForm in it.tags || GenerateTag.EditTable in it.tags) && GenerateTag.Rules in it.tags }.forEach {
            assert(it.content.contains("shortAssociationPropertyId: ["))
        }
    }

    @Test
    fun `test shortAssociationTargetIdView dto`() {
        assertEquals(
            """
(dto/Entity.dto, export EntityPackage.Entity

EntityListView {
    #allScalars
    shortAssociationProperty {
        label1
        label2
    }
}

EntityDetailView {
    #allScalars
    shortAssociationPropertyId
}

EntityOptionView {
    id
}

input EntityInsertInput {
    #allScalars
    -id
    shortAssociationPropertyId
}

EntityUpdateFillView {
    #allScalars
    shortAssociationPropertyId
}

input EntityUpdateInput {
    #allScalars
    id!
    shortAssociationPropertyId
}

specification EntitySpec {
    associatedIdEq(shortAssociationProperty)
})
            """.trimIndent(),
            DtoGenerator.generateDto(shortAssociationTargetIdViewEntity).let { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test shortAssociationTargetIdView view`() {
        val viewItems = Vue3ElementPlusViewGenerator.generateView(shortAssociationTargetIdViewEntity)

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
        <el-table-column
            prop="shortAssociationProperty.label1"
            label="commentlabel1"
        />
        <el-table-column
            prop="shortAssociationProperty.label2"
            label="commentlabel2"
        />
        <el-table-column label="操作" fixed="right">
            <template #default="scope">
                <slot
                    name="operations"
                    :row="scope.row as EntityListView"
                    :index="scope.$index"
                />
            </template>
        </el-table-column>
    </el-table>
</template>
            """.trimIndent(),
            viewItems.filter { GenerateTag.Table in it.tags }[0].content.trim()
        )

        viewItems.filter { (GenerateTag.AddForm in it.tags || GenerateTag.EditForm in it.tags || GenerateTag.EditTable in it.tags) && GenerateTag.Rules !in it.tags }.forEach {
            assert(it.content.contains("shortAssociationPropertyIdOptions"))
            assert(it.content.contains("ShortAssociationEntityIdSelect"))
        }

        viewItems.filter { (GenerateTag.AddForm in it.tags || GenerateTag.EditForm in it.tags || GenerateTag.EditTable in it.tags) && GenerateTag.Rules in it.tags }.forEach {
            assert(it.content.contains("shortAssociationPropertyId: ["))
        }
    }
}