package top.potmot.business.shortAssociation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.dto.generate.DtoGenerator
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.enumeration.GenerateTag

class TestShortAssociation {
    private val index = "${'$'}index"

    private val RootEntityBusiness.result
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
    fun `test shortAssociationToOneTarget dto`() {
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
    eq(id)
    associatedIdEq(shortAssociationProperty)
})
            """.trimIndent(),
            DtoGenerator.generateDto(shortAssociationToOneTargetEntity).let { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test shortAssociationToOneTarget view`() {
        val viewItems = Vue3ElementPlusViewGenerator.generateEntity(listOf(shortAssociationToOneTargetEntity))

        assertEquals(
            """
[(components/entity/EntityTable.vue, <script setup lang="ts">
import type {EntityListView} from "@/api/__generated/model/static"
import {usePageSizeStore} from "@/stores/pageSizeStore"

withDefaults(defineProps<{
    rows: Array<EntityListView>,
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined
}>(), {
    idColumn: false,
    indexColumn: true,
    multiSelect: true,
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

const pageSizeStore = usePageSizeStore()

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
        class="view-table"
        @selection-change="handleSelectionChange"
    >
        <el-table-column
            v-if="idColumn"
            prop="id"
            label="ID"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            v-if="indexColumn"
            type="index"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            v-if="multiSelect"
            type="selection"
            :width="43"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            prop="shortAssociationProperty.label1"
            label="commentlabel1 comment"
        />
        <el-table-column
            prop="shortAssociationProperty.label2"
            label="commentlabel2 comment"
        />
        <el-table-column
            label="操作"
            :fixed="pageSizeStore.isSmall ? undefined : 'right'"
        >
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
), (components/shortAssociationEntity/ShortAssociationEntityIdSelect.vue, <script setup lang="ts">
import {onBeforeMount, watch} from "vue"
import type {LazyOptions} from "@/utils/lazyOptions"
import type {ShortAssociationEntityOptionView} from "@/api/__generated/model/static"

const modelValue = defineModel<number | undefined>({
    required: true
})

const props = defineProps<{
    options: LazyOptions<ShortAssociationEntityOptionView>
}>()

const loadIfNot = async () => {
    if (props.options.state === 'unload') {
        await props.options.load()
    } else if (props.options.data === undefined && props.options.state !== 'loading') {
        await props.options.load()
    }
}

onBeforeMount(async () => {
    if (modelValue.value !== undefined) {
        await loadIfNot()
    }
})

watch(() => [modelValue.value, props.options], () => {
    if (props.options.state !== 'loaded') return []
    if (props.options.data === undefined) return []

    if (!(props.options.data.map(it => it.id) as Array<number | undefined>).includes(modelValue.value)) {
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
        :loading="options.state === 'loading'"
        @focus.once="loadIfNot"
    >
        <el-option
            v-for="option in options.data"
            :key="option.id"
            :value="option.id"
            :label="`${'$'}{option.label1} ${'$'}{option.label2}`"
        />
    </el-select>
</template>
)]
            """.trimIndent(),
            viewItems.filter { GenerateTag.ViewTable in it.tags || GenerateTag.IdSelect in it.tags }.map { it.path to it.content }.toString()
        )

        viewItems.filter { (GenerateTag.AddForm in it.tags || GenerateTag.EditForm in it.tags || GenerateTag.EditTable in it.tags) && GenerateTag.Component in it.tags }.forEach {
            assert(it.content.contains("shortAssociationPropertyIdOptions"))
            assert(it.content.contains("ShortAssociationEntityIdSelect"))
        }

        viewItems.filter { (GenerateTag.AddForm in it.tags || GenerateTag.EditForm in it.tags || GenerateTag.EditTable in it.tags) && GenerateTag.Rules in it.tags }.forEach {
            assert(it.content.contains("shortAssociationPropertyId: ["))
        }
    }

    @Test
    fun `test shortAssociationToOneTarget idView dto`() {
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
    eq(id)
    associatedIdEq(shortAssociationProperty)
})
            """.trimIndent(),
            DtoGenerator.generateDto(shortAssociationToOneTargetIdViewEntity).let { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test shortAssociationToOneTarget idView view`() {
        val viewItems = Vue3ElementPlusViewGenerator.generateEntity(listOf(shortAssociationToOneTargetIdViewEntity))

        assertEquals(
            """
[(components/entity/EntityTable.vue, <script setup lang="ts">
import type {EntityListView} from "@/api/__generated/model/static"
import {usePageSizeStore} from "@/stores/pageSizeStore"

withDefaults(defineProps<{
    rows: Array<EntityListView>,
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined
}>(), {
    idColumn: false,
    indexColumn: true,
    multiSelect: true,
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

const pageSizeStore = usePageSizeStore()

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
        class="view-table"
        @selection-change="handleSelectionChange"
    >
        <el-table-column
            v-if="idColumn"
            prop="id"
            label="ID"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            v-if="indexColumn"
            type="index"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            v-if="multiSelect"
            type="selection"
            :width="43"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            prop="shortAssociationProperty.label1"
            label="commentlabel1 comment"
        />
        <el-table-column
            prop="shortAssociationProperty.label2"
            label="commentlabel2 comment"
        />
        <el-table-column
            label="操作"
            :fixed="pageSizeStore.isSmall ? undefined : 'right'"
        >
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
), (components/shortAssociationEntity/ShortAssociationEntityIdSelect.vue, <script setup lang="ts">
import {onBeforeMount, watch} from "vue"
import type {LazyOptions} from "@/utils/lazyOptions"
import type {ShortAssociationEntityOptionView} from "@/api/__generated/model/static"

const modelValue = defineModel<number | undefined>({
    required: true
})

const props = defineProps<{
    options: LazyOptions<ShortAssociationEntityOptionView>
}>()

const loadIfNot = async () => {
    if (props.options.state === 'unload') {
        await props.options.load()
    } else if (props.options.data === undefined && props.options.state !== 'loading') {
        await props.options.load()
    }
}

onBeforeMount(async () => {
    if (modelValue.value !== undefined) {
        await loadIfNot()
    }
})

watch(() => [modelValue.value, props.options], () => {
    if (props.options.state !== 'loaded') return []
    if (props.options.data === undefined) return []

    if (!(props.options.data.map(it => it.id) as Array<number | undefined>).includes(modelValue.value)) {
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
        :loading="options.state === 'loading'"
        @focus.once="loadIfNot"
    >
        <el-option
            v-for="option in options.data"
            :key="option.id"
            :value="option.id"
            :label="`${'$'}{option.label1} ${'$'}{option.label2}`"
        />
    </el-select>
</template>
)]
            """.trimIndent(),
            viewItems.filter { GenerateTag.ViewTable in it.tags || GenerateTag.IdSelect in it.tags }.map { it.path to it.content }.toString()
        )

        viewItems.filter { (GenerateTag.AddForm in it.tags || GenerateTag.EditForm in it.tags || GenerateTag.EditTable in it.tags) && GenerateTag.Component in it.tags }.forEach {
            assert(it.content.contains("shortAssociationPropertyIdOptions"))
            assert(it.content.contains("ShortAssociationEntityIdSelect"))
        }

        viewItems.filter { (GenerateTag.AddForm in it.tags || GenerateTag.EditForm in it.tags || GenerateTag.EditTable in it.tags) && GenerateTag.Rules in it.tags }.forEach {
            assert(it.content.contains("shortAssociationPropertyId: ["))
        }
    }

    @Test
    fun `test shortAssociationToManyTarget dto`() {
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
    id(shortAssociationProperty) as shortAssociationPropertyIds
}

EntityUpdateFillView {
    #allScalars
    id(shortAssociationProperty) as shortAssociationPropertyIds
}

input EntityUpdateInput {
    #allScalars
    id!
    id(shortAssociationProperty) as shortAssociationPropertyIds
}

specification EntitySpec {
    eq(id)
    associatedIdIn(shortAssociationProperty) as shortAssociationPropertyIds
})
            """.trimIndent(),
            DtoGenerator.generateDto(shortAssociationToManyTargetEntity).let { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test shortAssociationToManyTarget view`() {
        val viewItems = Vue3ElementPlusViewGenerator.generateEntity(listOf(shortAssociationToManyTargetEntity))

        assertEquals(
            """
[(components/entity/EntityTable.vue, <script setup lang="ts">
import type {EntityListView} from "@/api/__generated/model/static"
import {usePageSizeStore} from "@/stores/pageSizeStore"

withDefaults(defineProps<{
    rows: Array<EntityListView>,
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined
}>(), {
    idColumn: false,
    indexColumn: true,
    multiSelect: true,
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

const pageSizeStore = usePageSizeStore()

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
        class="view-table"
        @selection-change="handleSelectionChange"
    >
        <el-table-column
            v-if="idColumn"
            prop="id"
            label="ID"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            v-if="indexColumn"
            type="index"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            v-if="multiSelect"
            type="selection"
            :width="43"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            prop="shortAssociationPropertyIds"
            label="comment"
        />
        <el-table-column
            label="操作"
            :fixed="pageSizeStore.isSmall ? undefined : 'right'"
        >
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
            :label="`${'$'}{option.label1} ${'$'}{option.label2}`"
        />
    </el-select>
</template>
)]
            """.trimIndent(),
            viewItems.filter { GenerateTag.ViewTable in it.tags || GenerateTag.IdSelect in it.tags }.map { it.path to it.content }.toString()
        )

        viewItems.filter { (GenerateTag.AddForm in it.tags || GenerateTag.EditForm in it.tags || GenerateTag.EditTable in it.tags) && GenerateTag.Component in it.tags }.forEach {
            assert(it.content.contains("shortAssociationPropertyIdsOptions"))
            assert(it.content.contains("ShortAssociationEntityIdMultiSelect"))
        }

        viewItems.filter { (GenerateTag.AddForm in it.tags || GenerateTag.EditForm in it.tags || GenerateTag.EditTable in it.tags) && GenerateTag.Rules in it.tags }.forEach {
            assert(it.content.contains("shortAssociationPropertyIds: ["))
        }
    }

    @Test
    fun `test shortAssociationToManyTarget idView dto`() {
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
    shortAssociationPropertyIds
}

EntityUpdateFillView {
    #allScalars
    shortAssociationPropertyIds
}

input EntityUpdateInput {
    #allScalars
    id!
    shortAssociationPropertyIds
}

specification EntitySpec {
    eq(id)
    associatedIdIn(shortAssociationProperty) as shortAssociationPropertyIds
})
            """.trimIndent(),
            DtoGenerator.generateDto(shortAssociationToManyTargetIdViewEntity).let { it.path to it.content }.toString()
        )
    }

    @Test
    fun `test shortAssociationToManyTarget idView view`() {
        val viewItems = Vue3ElementPlusViewGenerator.generateEntity(listOf(shortAssociationToManyTargetIdViewEntity))

        assertEquals(
            """
[(components/entity/EntityTable.vue, <script setup lang="ts">
import type {EntityListView} from "@/api/__generated/model/static"
import {usePageSizeStore} from "@/stores/pageSizeStore"

withDefaults(defineProps<{
    rows: Array<EntityListView>,
    idColumn?: boolean | undefined,
    indexColumn?: boolean | undefined,
    multiSelect?: boolean | undefined
}>(), {
    idColumn: false,
    indexColumn: true,
    multiSelect: true,
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

const pageSizeStore = usePageSizeStore()

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
        class="view-table"
        @selection-change="handleSelectionChange"
    >
        <el-table-column
            v-if="idColumn"
            prop="id"
            label="ID"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            v-if="indexColumn"
            type="index"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            v-if="multiSelect"
            type="selection"
            :width="43"
            :fixed="pageSizeStore.isSmall ? undefined : 'left'"
        />
        <el-table-column
            prop="shortAssociationPropertyIds"
            label="comment"
        />
        <el-table-column
            label="操作"
            :fixed="pageSizeStore.isSmall ? undefined : 'right'"
        >
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
            :label="`${'$'}{option.label1} ${'$'}{option.label2}`"
        />
    </el-select>
</template>
)]
            """.trimIndent(),
            viewItems.filter { GenerateTag.ViewTable in it.tags || GenerateTag.IdSelect in it.tags }.map { it.path to it.content }.toString()
        )

        viewItems.filter { (GenerateTag.AddForm in it.tags || GenerateTag.EditForm in it.tags || GenerateTag.EditTable in it.tags) && GenerateTag.Component in it.tags }.forEach {
            assert(it.content.contains("shortAssociationPropertyIdsOptions"))
            assert(it.content.contains("ShortAssociationEntityIdMultiSelect"))
        }

        viewItems.filter { (GenerateTag.AddForm in it.tags || GenerateTag.EditForm in it.tags || GenerateTag.EditTable in it.tags) && GenerateTag.Rules in it.tags }.forEach {
            assert(it.content.contains("shortAssociationPropertyIds: ["))
        }
    }
}