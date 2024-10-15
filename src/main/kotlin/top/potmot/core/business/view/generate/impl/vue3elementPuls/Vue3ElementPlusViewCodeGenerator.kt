package top.potmot.core.business.view.generate.impl.vue3elementPuls

import top.potmot.core.business.view.generate.ViewCodeGenerator
import top.potmot.entity.dto.GenEntityPropertiesView

object Vue3ElementPlusViewCodeGenerator : ViewCodeGenerator() {
    override fun getFileSuffix() = "vue"

    override fun stringifyTable(entity: GenEntityPropertiesView): String {
        val listView = "${entity.name}ListView"

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
            entity.properties.joinToString("\n        ") {
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

    override fun stringifyForm(entity: GenEntityPropertiesView): String {
        val insertInput = "${entity.name}InsertInput"
        val updateInput = "${entity.name}UpdateInput"

        return """
<script setup lang="ts">
import type {${insertInput}, ${updateInput}} from "@/api/__generated/model/static";

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
        <el-form-item>
        
        </el-form-item>
    </el-form>
</template>
        """.trim()
    }

    override fun stringifyQueryForm(entity: GenEntityPropertiesView): String {
        val spec = "${entity.name}Spec"

        return """
<script setup lang="ts">
import type {${spec}} from "@/api/__generated/model/static";

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
            <el-col :span="6">
                <el-form-item label="内容">
                    <el-input
                        placeholder="请输入内容"
                        v-model="spec.content"
                        clearable
                        @change="emits('query')"/>
                </el-form-item>
            </el-col>
    
            <el-button :icon="Search" type="primary" @click="emits('query')"/>
        </el-row>
    </el-form>
</template>
        """.trim()
    }

    override fun stringifyPage(entity: GenEntityPropertiesView): String {
        val dir = entity.name.replaceFirstChar { it.lowercase() }

        val serviceName = "${entity.name.replaceFirstChar { it.lowercase() }}Service"

        val table = "${entity.name}Table"
        val form = "${entity.name}Form"
        val queryForm = "${entity.name}QueryForm"

        val listView = "${entity.name}ListView"
        val spec = "${entity.name}Spec"

        return """
<script setup lang="ts">
import {Check, Close, Delete, Search} from "@element-plus/icons-vue";
import {ref} from "vue";
import type {Page, PageQuery, ${spec}, ${listView}} from "@/api/__generated/model/static";
import {api} from "@/api";
import {sendMessage} from "@/utils/message";
import {deleteConfirm} from "@/utils/confirm";
import {useLoading} from "@/utils/loading";
import {useLegalPage} from "@/utils/legalPage";
import $table from "@/src/components/$dir/$table"
import $form from "@/src/components/$dir/$form"
import $queryForm from "@/src/components/$dir/$queryForm"

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
}