<script setup lang="ts">
import {computed, PropType} from "vue";
import {GenTableGroupTreeView} from "../../__generated/model/static";
import type Node from 'element-plus/es/components/tree/src/model/node'
import type {DragEvents} from 'element-plus/es/components/tree/src/model/useDragNode'
import {NodeDropType} from "element-plus/es/components/tree/src/tree.type";
import {TableGroupTreeItem} from "../../declare/TableGroupTreeItem";

const props = defineProps({
	trees: {
		type: Array as PropType<GenTableGroupTreeView[]>,
		required: true
	}
})

const emits = defineEmits(["clickNode"])

const mapToItem = (tree: GenTableGroupTreeView): TableGroupTreeItem => {
	const children = <TableGroupTreeItem[]>[]
	if (tree.tables) {
		tree.tables.forEach(table => {
			children.push({
				type: "table",
				data: table,
				children: [],
			})
		})
	}
	if (tree.children) {
		tree.children.forEach(child => {
			children.push(mapToItem(child))
		})
	}
	return {
		type: "group",
		data: tree,
		children
	}
}

const trees = computed(() => {
	return props.trees?.map(tree => mapToItem(tree))
})

const handleNodeClick = (data: TableGroupTreeItem) => {
	emits("clickNode", data)
}

const allowDrop = (
	draggingNode: Node,
	targetNode: Node,
	type: NodeDropType
): boolean => {
	if (type == "none") {
		return false
	}
	if (targetNode.data.type == "table" && type == "inner") {
		return false
	}
	if (draggingNode.data.type != targetNode.data.type) {
		return false
	}

	return true
}

const handleDragEnd = (
	draggingNode: Node,
	targetNode: Node,
	type: NodeDropType,
	ev: DragEvents
) => {
	if (allowDrop(draggingNode, targetNode, type)) {
		if (type == "inner") {

		}
	}
}

const defaultProps = {
	children: 'children'
}
</script>

<template>
	<el-tree
		:data="trees"
		:props="defaultProps"
		node-key="id"
		@node-click="handleNodeClick"
		draggable
		:allow-drop="allowDrop"
		@nodeDragEnd="handleDragEnd"
		default-expand-all
		:expand-on-click-node="false"
	>
		<template #default="{ node, data }">
			<span class="index">{{data.data.orderKey}}</span>

			<span v-if="data.type == 'table'">
				{{ data.data.tableName }}

				<a> - </a>
			</span>
			<span v-else-if="data.type == 'group'">
				{{ data.data.groupName }}

				<a> + </a>
				<a> - </a>
			</span>
		</template>
	</el-tree>
</template>