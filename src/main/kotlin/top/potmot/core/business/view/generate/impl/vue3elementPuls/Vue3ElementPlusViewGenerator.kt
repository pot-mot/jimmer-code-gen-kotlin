package top.potmot.core.business.view.generate.impl.vue3elementPuls

import top.potmot.core.business.view.generate.ViewGenerator
import top.potmot.core.business.utils.component
import top.potmot.core.business.utils.constants
import top.potmot.core.business.utils.dto
import top.potmot.core.business.utils.enums
import top.potmot.core.business.utils.serviceName
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEnumGenerateView

object Vue3ElementPlusViewGenerator : ViewGenerator() {
    override fun getFileSuffix() = "vue"

    override fun stringifyTable(entity: GenEntityBusinessView): String {
        val listView = entity.dto.listView

        return """
<script setup lang="ts">
defineProps<{
    rows: Array<$listView>
}>()

const slots = defineSlots<{
	default(props: {row: ${listView}, index: number}): any,
}>()

const emits = defineEmits<{
    (event: "changeSelection"): void,
}>()
</script>

<template>
    <el-table :data="rows" border stripe row-key="id"
              @selection-change="emits('changeSelection')">
        <el-table-column type="index"/>
        <el-table-column type="selection"/>
        ${
            entity.properties.filter { !it.idProperty && it.associationType == null }.joinToString("\n        ") {
                """
                <el-table-column label="${it.comment}" prop="${it.name}"/>
                """.trim()
            }
        }
        
        <el-table-column label="操作" width="330px">
            <template #default="scope">
                <slot name="operations" row="scope.row" :index="scope.${'$'}index" />
            </template>
        </el-table-column>
    </el-table>
</template>
        """.trim()
    }

    override fun stringifyForm(entity: GenEntityBusinessView): String {
        val (_, _, _, insertInput, updateInput) = entity.dto

        return """
<script setup lang="ts">
import type {${insertInput}, ${updateInput}} from "@/api/__generated/model/static"

const formData = defineModel<${insertInput} | ${updateInput}>({
    required: true
})

const emits = defineEmits<{
    (event: "submit"): void,
    (event: "cancel"): void,
}>()
</script>

<template>
    <el-form>
        ${
            entity.properties.filter { !it.idProperty && it.associationType == null }.joinToString("\n") {
                """
        <el-form-item label="${it.comment}">
            <el-input
                placeholder="请输入${it.comment}"
                v-model="formData.${it.name}"/>
        </el-form-item>
                    """.trimEnd()
            }
        }
        
        <div style="text-align: right;">
            <el-button type="warning" @click="emits('cancel')" v-text="取消"/>
            <el-button type="primary" @click="emits('submit')" v-text="提交"/>
        </div>
    </el-form>
</template>
        """.trim()
    }

    override fun stringifyQueryForm(entity: GenEntityBusinessView): String {
        val spec = entity.dto.spec

        val enums = entity.enums
        val enumConstants = enums.map { it.constants }

        return """
<script setup lang="ts">
import type {${spec}, ${enums.joinToString(", ") { it.name }}} from "@/api/__generated/model/static"${
    if (enumConstants.isNotEmpty()) {
        "\nimport {${enumConstants.joinToString(", ")}} from \"@/api/__generated/model/static\""
    } else {
        ""
    }
}
${
    enums.joinToString("\n") {
        val (_, dir, select) = it.component
        """import $select from "@/components/$dir/$select.vue""""
    }
}

const spec = defineModel<${spec}>({
    required: true
})

const emits = defineEmits<{
    (event: "query"): void,
}>()
</script>

<template>
    <el-form>
        <el-row :gutter="20">
            ${
            entity.properties.filter { !it.idProperty && it.associationType == null }.joinToString("\n") {
                if (it.enum != null) {
                    """
            <el-col :span="8">
                <el-form-item label="${it.comment}">
                    <${it.enum.component.select} v-model="spec.${it.name}"/>
                </el-form-item>
            </el-col>
                    """.trimEnd()
                } else {
                    """
            <el-col :span="8">
                <el-form-item label="${it.comment}">
                    <el-input
                        placeholder="请输入${it.comment}"
                        v-model="spec.${it.name}"
                        clearable
                        @change="emits('query')"/>
                </el-form-item>
            </el-col>
                    """.trimEnd()
                }
            }
        }
    
            <el-button :icon="Search" type="primary" @click="emits('query')"/>
        </el-row>
    </el-form>
</template>
        """.trim()
    }

    override fun stringifyPage(entity: GenEntityBusinessView): String {
        val serviceName = entity.serviceName.replaceFirstChar { it.lowercase() }

        val (_, dir, table, form, queryForm) = entity.component
        val (_, listView, _, _, _, spec) = entity.dto

        return """
<script setup lang="ts">
import {Check, Close, Delete, Search} from "@element-plus/icons-vue";
import {ref} from "vue";
import type {Page, PageQuery, ${spec}, ${listView}} from "@/api/__generated/model/static";
import {api} from "@/api";
import {sendMessage} from "@/utils/message.ts";
import {deleteConfirm} from "@/utils/confirm.ts";
import {useLoading} from "@/utils/loading.ts";
import {useLegalPage} from "@/utils/legalPage.ts";
import $table from "@/src/components/$dir/$table.vue"
import $form from "@/src/components/$dir/$form.vue"
import $queryForm from "@/src/components/$dir/$queryForm.vue"

const {isLoading, withLoading} = useLoading()

const pageData = ref<Page<${listView}>>()

// 查询
const queryInfo = ref<PageQuery<${spec}>>({
    spec: {},
    pageIndex: 1,
    pageSize: 5
})

const {queryPage} = useLegalPage(
    pageData,
    queryInfo,
    withLoading(api.${serviceName}.page)
)

// 多选
const multipleSelection = ref<${listView}[]>([])

const handleSelectionChange = (item: ${listView}[]) => {
    multipleSelection.value = item
}

// 删除
const handleDelete = async (ids: number[]) => {
    const result = await deleteConfirm('该${entity.comment}')
    if (!result) return

    try {
        await withLoading(api.${serviceName}.delete)({ids: ids})
        await queryPage()
        sendMessage('删除${entity.comment}成功', 'success')
    } catch (e) {
        sendMessage('删除${entity.comment}失败', 'error', e)
    }
}
</script>

<template>
    <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>${entity.comment}管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card v-loading="isLoading">
        <$queryForm :v-model="queryInfo.spec" @query="queryPage"/>
        
        <div>
            <el-button type="danger" :icon="Delete" @click="handleDelete(multipleSelection.map(it => it.id))" v-text="批量删除">
        </div>

        <template v-if="pageData">
            <$table :rows="pageData.rows">
                <template #operations="{row}">
                    <el-button type="danger" :icon="Delete" @click="removePost([row.id])" v-text="删除"/>                
                </template>
            </$table>

            <el-pagination
                v-model:current-page="queryInfo.pageIndex"
                v-model:page-size="queryInfo.pageSize"
                :page-sizes="[5, 10, 20]"
                layout="total, sizes, prev, pager, next, jumper"
                :total="pageData.totalRowCount"
            />
        </template>
    </el-card>
    
    <$form />
</template>
    """.trim()
    }

    override fun stringifyEnumSelect(enum: GenEnumGenerateView): String {
        val (_, dir, _, view) = enum.component

        return """
<script setup lang="ts">
import {${enum.constants}} from "@/api/__generated/model/static"
import type {${enum.name}} from "@/api/__generated/model/static"
import $view from "@/components/$dir/$view.vue"

const data = defineModel<${enum.name}>({
    required: true
})
</script>

<template>
    <el-select
        placeholder="请选择${enum.comment}"
        v-model="data"
        clearable>
        <el-option
            v-for="value in ${enum.constants}" 
            :value="value">
            <$view :value="value"/>
        </el-option>
    </el-select>
</template>
        """.trim()
    }

    override fun stringifyEnumView(enum: GenEnumGenerateView): String {
        return """
<script setup lang="ts">
import type {${enum.name}} from "@/api/__generated/model/static"

defineProps<{
    value: ${enum.name}
}>()
</script>

<template>
${
    enum.items.joinToString("\n") {
    """    <el-text v-if="value === '${it.name}'">${it.comment}</el-text>"""
    }
}
</template>
        """.trim()
    }
}