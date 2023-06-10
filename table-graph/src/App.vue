<script setup lang="ts">
import TableMapping from "./components/TableMapping.vue";
import {ref} from "vue";
import ERMapping from "./components/ERMapping.vue";

const keyword = ref("")
</script>

<template>
  <TableMapping :keyword="keyword"></TableMapping>
<!--<ERMapping></ERMapping>-->
</template>

<style>
* {
	margin: 0;
	padding: 0;
}
</style>
