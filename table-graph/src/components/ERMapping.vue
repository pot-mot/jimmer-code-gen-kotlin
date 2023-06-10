<template>
	<div class="er-mapping" ref="mainContainer"></div>
</template>

<script lang="ts" setup>
import {Graph} from '@antv/x6';
import {onMounted, ref} from "vue";

const mainContainer = ref()

let graph

const resetGraph = () => {
	graph = new Graph({
		container: <HTMLDivElement>mainContainer.value,
		width: 800,
		height: 600
	});
}

onMounted(() => {
	resetGraph()
})

</script>

<style lang="scss" scoped>
.er-mapping {
	width: 100vw;
	height: 100vh;
	margin: auto;
	overflow: hidden;
}
</style>