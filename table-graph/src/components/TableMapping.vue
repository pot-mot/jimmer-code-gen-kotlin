<!--<template>-->
<!--  <span v-show="loading > 0" style="font-size: 10vh;color: #fff;position: fixed;">LOADING</span>-->
<!--  <div class="mind-mapping" ref="mainContainer"></div>-->
<!--</template>-->

<!--<script lang="ts" setup>-->
<!--import {Graph} from '@antv/g6';-->
<!--import {onMounted, ref, watch} from "vue";-->
<!--import {ComboConfig, EdgeConfig, GraphData, NodeConfig} from "@antv/g6-core/lib/types";-->
<!--import {api} from "../api";-->

<!--const parseColumnResponse = (tables: any, associations: any): GraphData => {-->
<!--  let result = {nodes: <NodeConfig[]>[], edges: <EdgeConfig[]>[], combos: <ComboConfig[]>[]}-->

<!--  for (const table of tables) {-->
<!--    result.combos.push({-->
<!--      id: 'C' + table.id,-->
<!--      label: table.tableName + "\n" + table.tableComment,-->
<!--      collapsed: true-->
<!--    })-->

<!--    for (const column of table.columns) {-->
<!--      result.nodes.push({-->
<!--        id: 'c' + column.id,-->
<!--        label: column.columnName + "\n" + column.columnComment,-->
<!--        comboId: 'C' + table.id,-->
<!--      })-->
<!--    }-->
<!--  }-->

<!--  for (const association of associations) {-->
<!--    result.edges.push({-->
<!--      target: 'c' + association.targetColumn.id,-->
<!--      source: 'c' + association.sourceColumn.id,-->
<!--      label: association.tableAssociationName + "\n" + association.remark,-->
<!--      endArrow: true-->
<!--    })-->
<!--  }-->

<!--  return result-->
<!--}-->

<!--const mainContainer = ref()-->

<!--const props = defineProps({-->
<!--  keyword: {-->
<!--    required: true,-->
<!--    type: String,-->
<!--  }-->
<!--})-->

<!--const resetGraph = () => {-->
<!--  graph = new Graph({-->
<!--    autoPaint: false,-->
<!--    fitCenter: true,-->
<!--    fitView: true,-->
<!--    container: <HTMLDivElement>mainContainer.value,-->
<!--    modes: {-->
<!--      default: [-->
<!--        'drag-canvas',-->
<!--        'zoom-canvas',-->
<!--        'drag-node',-->
<!--        'drag-combo',-->
<!--        {-->
<!--          type: 'collapse-expand-combo',-->
<!--          trigger: 'click',-->
<!--          relayout: false,-->
<!--        },-->
<!--      ],-->
<!--    },-->
<!--    groupByTypes: false,-->
<!--    layout: {-->
<!--      type: 'comboForce',-->
<!--      preventNodeOverlap: true,-->
<!--      edgeStrength: 1,-->
<!--    },-->
<!--    defaultNode: {-->
<!--      size: [100, 60],-->
<!--      type: 'rect',-->
<!--      style: {-->
<!--        opacity: 0.1-->
<!--      },-->
<!--      labelCfg: {-->
<!--        style: {-->
<!--          fill: '#fff',-->
<!--          fontsize: '8px'-->
<!--        },-->
<!--      },-->
<!--      anchorPoints: [-->
<!--        [0, 0.5],-->
<!--        [1, 0.5],-->
<!--      ],-->
<!--    },-->
<!--    defaultEdge: {-->
<!--      type: 'line',-->
<!--      sourceAnchor: 0,-->
<!--      targetAnchor: 1,-->
<!--      style: {-->
<!--        endArrow: true,-->
<!--        stroke: '#aaa',-->
<!--      },-->
<!--      labelCfg: {-->
<!--        style: {-->
<!--          fill: '#eee',-->
<!--          fontsize: '8px'-->
<!--        },-->
<!--      },-->
<!--    },-->
<!--    defaultCombo: {-->
<!--      type: 'rect',-->
<!--      style: {-->
<!--        opacity: 0.1-->
<!--      },-->
<!--      labelCfg: {-->
<!--        style: {-->
<!--          fill: '#fff',-->
<!--          fontsize: '8px'-->
<!--        },-->
<!--      },-->
<!--    }-->
<!--  })-->
<!--}-->

<!--let graph: undefined | Graph = undefined;-->

<!--const loading = ref(0)-->

<!--const render = async () => {-->
<!--  loading.value++-->

<!--  let tables = await api.returnService.getAllTable({keyword: props.keyword})-->
<!--  let associations = await api.returnService.getAllAssociation({keyword: props.keyword})-->

<!--  const data = parseColumnResponse(tables, associations)-->

<!--  graph?.data(data);-->
<!--  graph?.render();-->

<!--  loading.value&#45;&#45;-->
<!--}-->

<!--onMounted(() => {-->
<!--  resetGraph()-->
<!--  render()-->
<!--})-->

<!--watch(() => props.keyword, () => {-->
<!--  graph?.destroy()-->
<!--  resetGraph()-->
<!--  render()-->
<!--})-->

<!--</script>-->

<!--<style lang="scss" scoped>-->
<!--.mind-mapping {-->
<!--  width: 100vw;-->
<!--  height: 100vh;-->
<!--  margin: auto;-->
<!--  overflow: hidden;-->
<!--  background-color: #444;-->
<!--}-->
<!--</style>-->