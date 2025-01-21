package top.potmot.entity.dto

import top.potmot.entity.extension.allSuperEntities

fun GenEntityBusinessView.toFlat(): GenEntityBusinessView {
    val allSuperEntities = allSuperEntities()

    return copy(
        superEntities = emptyList(),
        properties = properties + allSuperEntities.flatMap { it.properties },
    )
}