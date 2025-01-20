//package top.potmot.core.business.test.generate
//
//import top.potmot.entity.dto.GenEntityBusinessView
//
//interface EntityDependenceAnalysis {
//    fun analysisDependenceEntities(
//        entities: Iterable<GenEntityBusinessView>,
//    ): Map<Long, List<GenEntityBusinessView>> {
//        val entityIdMap = entities.associateBy { it.id }
//        val result = mutableMapOf<Long, List<GenEntityBusinessView>>()
//
//        entities.forEach {
//            val list = mutableListOf<GenEntityBusinessView>()
//
//            it.properties.filter { it.typeEntity != null }.forEach {
//                val entity = entityIdMap[it.typeEntity?.id]
//                if (entity != null) {
//                    list.add(entity)
//                }
//            }
//
//            result[it.id] = list
//        }
//
//        return result
//    }
//}