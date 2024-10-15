package top.potmot.core.business.view.generate.impl.vue3elementPuls

import top.potmot.core.business.view.generate.ViewGenerator
import top.potmot.entity.dto.GenEntityPropertiesView

object Vue3ElementPlusViewGenerator : ViewGenerator() {
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
            entity.properties.filter { !it.idProperty }.joinToString("\n        ") {
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
        ${
            entity.properties.filter { !it.idProperty }.joinToString("\n") {
                """
        <el-form-item label="${it.comment}">
            <el-input
                placeholder="请输入${it.comment}"
                v-model="formData.${it.name}"/>
        </el-form-item>
                    """.trim()
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

    override fun stringifyQueryForm(entity: GenEntityPropertiesView): String {
        val spec = "${entity.name}Spec"

        val enums = entity.properties.mapNotNull { it.enum }
        val enumConstants = enums.map { "${it.name}_CONSTANTS" }

        return """
<script setup lang="ts">
import type {${spec}, ${enums.joinToString(", ") { it.name }}} from "@/api/__generated/model/static";${
    if (enumConstants.isNotEmpty()) {
        "\nimport {${enumConstants.joinToString(", ")}} from \"@/api/__generated/model/static\";"
    } else {
        ""
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
                entity.properties.filter { !it.idProperty }.joinToString("\n") {
                    if (it.enum != null) {
                        """
            <el-col :span="8">
                <el-form-item label="${it.comment}">
                    <el-select
                        placeholder="请选择${it.comment}"
                        v-model="spec.${it.name}"
                        clearable
                        @change="emits('query')">
                        <el-option v-for="value in ${it.enum.name}_CONSTANTS" :value="value"/>
                    </el-select>
                </el-form-item>
            </el-col>
                    """.trim()
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
                    """.trim()
                    }
                }
            }
    
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