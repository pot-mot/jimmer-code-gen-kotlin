package top.potmot.entity.model

import org.babyfish.jimmer.sql.Serialized

@Serialized
data class ModelViewport(
    val x: Double,
    val y: Double,
    val zoom: Double
)