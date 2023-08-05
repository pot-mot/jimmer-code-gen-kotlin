<script setup lang="ts">
import {onMounted, Ref, ref} from 'vue';
import {datetimeFormat} from "../utils/dataFormat";
import {api} from "../api";
import {useLoading} from "../hooks/useLoading";
import {GenTableDto} from "../__generated/model/dto";

const {loading, add, sub} = useLoading()

const emits = defineEmits(["beforeClose"])

const table: Ref<GenTableDto['GenTableFetchers/COLUMN_FETCHER'] | undefined> = ref()

const props = defineProps({
	id: {
		type: Number,
		required: true
	},
	visible: {
		type: Boolean,
		required: true,
	}
})

const getList = async () => {
	add()
	table.value = await api.genTableController.get({id: props.id})
	sub()
}

const beforeClose = () => {
	emits("beforeClose")
}

onMounted(() => {
	getList()
})
</script>

<template>
  <el-dialog :model-value="visible" :before-close="beforeClose" width="900px" title="表信息">
    <el-table v-if="table" v-loading="loading" :data="table.columns">
      <el-table-column label="ID" prop="id" width="50"/>
      <el-table-column label="创建时间" :formatter="(item) => {return datetimeFormat(item.createdTime)}"/>
      <el-table-column label="更新时间" :formatter="(item) => {return datetimeFormat(item.modifiedTime)}"/>
    </el-table>
  </el-dialog>
</template>