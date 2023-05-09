<script setup lang="ts">
import TableMapping from "./components/TableMapping.vue";
import {ref} from "vue";

const keyword = ref("md")
</script>

<template>
  {{ keyword }}
  <input @change="(event: InputEvent) => {keyword = event.target.value}">
  <TableMapping :keyword="keyword"></TableMapping>
</template>

<style scoped>
</style>
