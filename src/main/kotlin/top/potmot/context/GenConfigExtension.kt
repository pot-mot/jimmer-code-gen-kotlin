package top.potmot.context

import top.potmot.model.dto.GenConfig
import top.potmot.model.dto.GenConfigProperties
import top.potmot.utils.bean.copyProperties

fun GenConfig.merge(properties: GenConfigProperties) =
    copyProperties(properties, this)

fun GenConfigProperties.merge(properties: GenConfigProperties) =
    copyProperties(properties, this)

fun GenConfig.toProperties(): GenConfigProperties =
    GenConfigProperties(this.toEntity())
