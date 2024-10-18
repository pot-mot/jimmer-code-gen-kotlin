package top.potmot.core.business.utils

import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.extension.allSuperEntities

fun GenEntityBusinessView.toFlat(): GenEntityBusinessView {
    val allSuperEntities = allSuperEntities()

    return copy(
        superEntities = emptyList(),
        properties = properties + allSuperEntities.flatMap { it.properties },
    )
}