<script setup lang="ts">
import {datetimeFormat} from "../utils/dataFormat";
import {useVisible} from "../hooks/useVisible";

const {visible, hide, show} = useVisible()

defineProps({
	columns: {
	}	
})

defineExpose({
	hide, show
})
</script>

<template>
  <el-dialog v-model="visible" :before-close="hide" width="900px" title="表信息" append-to-body>
    <el-table :data="columns">
      <el-table-column label="ID" prop="id" width="50"/>
      <el-table-column label="创建时间" :formatter="(item) => {return datetimeFormat(item.createdTime)}"/>
      <el-table-column label="更新时间" :formatter="(item) => {return datetimeFormat(item.modifiedTime)}"/>
    </el-table>
  </el-dialog>
</template>