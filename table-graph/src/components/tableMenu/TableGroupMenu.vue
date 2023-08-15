<script setup lang="ts">
import {ref, onMounted, reactive} from 'vue'
import {api} from "../../api";
import {useLoading} from "../../hooks/useLoading";
import {GenTableGroupTreeView} from "../../__generated/model/static";

const groups = ref(<readonly GenTableGroupTreeView[]>[])

const {loading, add, sub} = useLoading()

const queryParams = reactive({
	pageNum: 1,
	pageSize: 10,
	tableName: undefined,
	tableComment: undefined
})

const preview = ref({})

const getList = async () => {
	add()
	groups.value = await api.tableGroupController.getTrees({})
	sub()
}

const resetQuery = () => {

}

const handleQuery = () => {

}

const dialog = ref()
const node = ref()

onMounted(() => {
	getList()
})
</script>

<template>
	<table-group-tree :trees="groups"></table-group-tree>
</template>
