<script setup lang="ts">
import {ref, onMounted, reactive, Ref} from 'vue'
import {Delete, Download, Edit, Refresh, Search, View} from "@element-plus/icons-vue";
import {api} from "../api";
import {GenTableDto} from "../__generated/model/dto";
import {datetimeFormat} from "../utils/dataFormat";
import {useLoading} from "../hooks/useLoading";

const tables: Ref<ReadonlyArray<GenTableDto["NodeSet/TABLE"]>> = ref([]);
const selection = ref([]);
const multiple = ref(true);
const total = ref(0);
const dateRange = ref([]);

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
	tables.value = await api.genTableController.listTables()
	sub()
}

const resetQuery = () => {

}

const handleQuery = () => {

}

const dialog = ref()
const node = ref()

const handleEdit = (genTable: GenTableDto["NodeSet/TABLE"]) => {
	node.value = genTable.id
	dialog.value.show()
}

const handleDelete = () => {

}

const handleSync = () => {

}

const handlePreview = () => {

}

const handleGen = () => {

}

const handleImport = () => {

}

const handleSelectionChange = () => {

}

onMounted(() => {
	getList()
})
</script>

<template>
	<el-form :model="queryParams" ref="queryRef" :inline="true">
		<el-form-item label="表名称" prop="tableName">
			<el-input
				v-model="queryParams.tableName"
				placeholder="请输入表名称"
				clearable
				style="width: 200px"
				@keyup.enter="handleQuery"
			/>
		</el-form-item>
		<el-form-item label="表描述" prop="tableComment">
			<el-input
				v-model="queryParams.tableComment"
				placeholder="请输入表描述"
				clearable
				style="width: 200px"
				@keyup.enter="handleQuery"
			/>
		</el-form-item>
		<el-form-item label="创建时间" style="width: 250px">
			<el-date-picker
				v-model="dateRange"
				value-format="YYYY-MM-DD"
				type="daterange"
				range-separator="-"
				start-placeholder="开始日期"
				end-placeholder="结束日期"
			></el-date-picker>
		</el-form-item>
		<el-form-item>
			<el-button type="primary" @click="handleQuery">
				<el-icon>
					<search/>
				</el-icon>
				搜索
			</el-button>
			<el-button @click="resetQuery">
				<el-icon>
					<refresh/>
				</el-icon>
				重置
			</el-button>
		</el-form-item>
	</el-form>

	<el-row :gutter="10" class="mb8">
		<el-col :span="1.5">
			<el-button type="primary" @click="handleGen">
				<el-icon>
					<download/>
				</el-icon>
				生成
			</el-button>
		</el-col>
		<el-col :span="1.5">
			<el-button type="danger" :disabled="multiple" @click="handleDelete">
				<el-icon>
					<delete/>
				</el-icon>
				删除
			</el-button>
		</el-col>
	</el-row>

	<el-table class="genTable" v-loading="loading" :data="tables" @selection-change="handleSelectionChange">
		<el-table-column type="selection" align="center" width="55"></el-table-column>
		<el-table-column label="ID" prop="id" width="50"/>
		<el-table-column label="表名称" prop="tableName" :show-overflow-tooltip="true"/>
		<el-table-column label="表描述" prop="tableComment" :show-overflow-tooltip="true"/>
		<el-table-column label="表类型" prop="tableType" :show-overflow-tooltip="true"/>
		<el-table-column label="创建时间" :formatter="(item) => {return datetimeFormat(item.createdTime)}"/>
		<el-table-column label="更新时间" :formatter="(item) => {return datetimeFormat(item.modifiedTime)}"/>
		<el-table-column label="操作" align="center" width="330" class-name="small-padding fixed-width">
			<template #default="scope">
				<el-tooltip content="预览" placement="top">
					<el-button link type="primary" @click="handlePreview">
						<el-icon>
							<view/>
						</el-icon>
					</el-button>
				</el-tooltip>
				<el-tooltip content="编辑" placement="top">
					<el-button link type="primary" @click="handleEdit(scope.row)">
						<el-icon>
							<edit/>
						</el-icon>
					</el-button>
				</el-tooltip>
				<el-tooltip content="删除" placement="top">
					<el-button link type="primary" @click="handleDelete">
						<el-icon>
							<delete/>
						</el-icon>
					</el-button>
				</el-tooltip>
				<el-tooltip content="同步" placement="top">
					<el-button link type="primary" @click="handleSync">
						<el-icon>
							<refresh/>
						</el-icon>
					</el-button>
				</el-tooltip>
				<el-tooltip content="生成代码" placement="top">
					<el-button link type="primary" @click="handleGen">
						<el-icon>
							<download/>
						</el-icon>
					</el-button>
				</el-tooltip>
			</template>
		</el-table-column>
	</el-table>

	<el-pagination
		:total="total"
		layout="total, size, prev, pager, next"
		:page-size="10"
	/>
</template>

<style scoped>
.genTable {
	font-size: 10px;
}
</style>
