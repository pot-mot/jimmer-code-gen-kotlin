<template>
  <div v-show="loading > 0" style="font-size: 10vh;">LOADING</div>
	<div class="mind-mapping" ref="mainContainer"></div>
</template>

<script lang="ts" setup>
import G6, {Graph} from '@antv/g6';
import {onMounted, ref, watch} from "vue";
import {ComboConfig, EdgeConfig, GraphData, NodeConfig} from "@antv/g6-core/lib/types";
import {api} from "../api/ApiInstance";

const parseColumnResponse = (tables: any, associations: any): GraphData => {
	let result = {nodes: <NodeConfig[]>[], edges: <EdgeConfig[]>[], combos: <ComboConfig[]>[]}

	for (const table of tables) {
		result.combos.push({
			id: 'C' + table.id,
			label: table.tableName + "\n" + table.tableComment,
			collapsed: true
		})

		for (const column of table.columns) {
			result.nodes.push({
				id: 'c' + column.id,
				label: column.columnName + "\n" + column.columnComment,
				comboId: 'C' + table.id,
			})
		}
	}

	for (const association of associations) {
		result.edges.push({
			target: 'c' + association.slaveColumn.id,
			source: 'c' + association.masterColumn.id,
			label: association.tableAssociationName + "\n" + association.remark,
			endArrow: true
		})
	}

	return result
}

const mainContainer = ref()

const props = defineProps({
  keyword: {
    required: true,
    type: String,
  }
})

onMounted(() => {
  graph = new G6.Graph({
    autoPaint: false,
    container: <HTMLDivElement>mainContainer.value,
    fitView: true,
    modes: {
      default: [
        'drag-canvas',
        'zoom-canvas',
        'drag-node',
        'drag-combo',
        {
          type: 'collapse-expand-combo',
          trigger: 'click',
          relayout: false,
        },
      ],
    },
    groupByTypes: false,
    layout: {
      type: 'comboForce',
    },
    defaultNode: {
      size: [60, 30],
      type: 'rect',
      style: {
        opacity: 0.1
      },
      labelCfg: {
        style: {
          fill: '#fff'
        },
      },
    },
    defaultEdge: {
      type: 'line',
      labelCfg: {
        style: {
          fill: '#eee'
        },
      },
    },
    defaultCombo: {
      type: 'rect',
      style: {
        opacity: 0.1
      },
      labelCfg: {
        style: {
          fill: '#fff'
        },
      },
    }
  })
})

let graph: undefined | Graph = undefined;

const loading = ref(0)

const render = async () => {
  loading.value ++

  let tables = await api.returnService.getAllTable({keyword: props.keyword})
  let associations = await api.returnService.getAllAssociation({keyword: props.keyword})

  console.log(tables)
  console.log(associations)

  const data = parseColumnResponse(tables, associations)

  console.log(data)

  loading.value --

  graph?.data(data);
  graph?.render();
}

onMounted(() => {
  render()
})

watch(() => props.keyword, () => {
  render()
})
</script>

<style lang="scss">
* {
	margin: 0;
	padding: 0;
}

.mind-mapping {
	width: 100vw;
	height: 100vh;
	margin: auto;
	overflow: hidden;
  background-color: #444;
}
</style>