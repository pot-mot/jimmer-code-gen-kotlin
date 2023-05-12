<template>
	<div class="mind-mapping" ref="mainContainer"></div>
</template>

<script lang="ts" setup>
import {Graph} from '@antv/x6';
import {onMounted, ref} from "vue";

const mainContainer = ref()

Graph.registerNode('gen-table', {
	inherit: 'rect',
	width: 100,
	height: 50,
	attrs: {
		body: {
			stroke: '#31d0c6',
			strokeWidth: 2,
			fill: '#e0f3f3'
		},
		label: {
			fontSize: 14,
			fill: '#444'
		},
	},
	markup: [
		{
			tagName: 'rect',
			selector: 'body'
		},
		{
			tagName: 'text',
			selector: 'label'
		},
	],
});

let graph

const resetGraph = () => {
	graph = new Graph({
		container: <HTMLDivElement>mainContainer.value,
		width: 800,
		height: 600
	});

	graph.addNode({
		shape: 'gen-table',
		x: 100,
		y: 100,
	});
}

onMounted(() => {
	resetGraph()
})

</script>

<style lang="scss" scoped>
.mind-mapping {
	width: 100vw;
	height: 100vh;
	margin: auto;
	overflow: hidden;
}
</style>