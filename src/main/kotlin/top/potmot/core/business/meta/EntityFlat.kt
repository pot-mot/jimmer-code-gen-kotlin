package top.potmot.core.business.meta

import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.extension.allSuperEntities

fun GenEntityBusinessView.toFlat(): GenEntityBusinessView {
    val allSuperEntities = allSuperEntities()

    // 对属性进行业务排序
    val allProperties = mutableListOf<GenEntityBusinessView.TargetOf_properties>()
    val allSuperProperties = allSuperEntities.flatMap { it.properties }.distinctBy { it.id }

    val idProperties = (properties + allSuperProperties).filter { it.idProperty }
    val (listProperties, nonListProperties) = properties.filter { !it.idProperty }.partition { it.listType }
    val (superListProperties, superNonListProperties) = allSuperProperties.filter { !it.idProperty }
        .partition { it.listType }

    allProperties += idProperties
    allProperties += superNonListProperties
    allProperties += nonListProperties
    allProperties += superListProperties
    allProperties += listProperties

    return copy(
        superEntities = emptyList(),
        properties = allProperties,
    )
}