<script setup lang="ts">
import TableMapping from "./components/TableMapping.vue";
import {ref} from "vue";
import TableER from "./components/TableER.vue";

const keyword = ref("md")
</script>

<template>
<!--  <input @change="(event: InputEvent) => {keyword = event.target.value}" style="position: fixed;">-->
<!--  <TableMapping :keyword="keyword"></TableMapping>-->
  <TableER></TableER>
</template>

<style>
* {
	margin: 0;
	padding: 0;
}
</style>
