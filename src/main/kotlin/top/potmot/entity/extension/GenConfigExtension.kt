package top.potmot.entity.extension

import top.potmot.entity.dto.GenConfigProperties

fun GenConfigProperties.merge(vararg properties: GenConfigProperties?) =
    GenConfigProperties(
        org.babyfish.jimmer.kt.merge(this.toEntity(), *properties.mapNotNull { it?.toEntity() }.toTypedArray())
    )