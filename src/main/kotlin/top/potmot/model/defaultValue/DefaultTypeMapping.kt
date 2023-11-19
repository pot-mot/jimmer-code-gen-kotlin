package top.potmot.model.defaultValue

import top.potmot.config.GenConfig
import top.potmot.model.dto.GenTypeMappingInput

val defaultTypeMapping = GenTypeMappingInput(
    dataSourceType = GenConfig.dataSourceType,
    language = GenConfig.language,
    typeExpression = "",
    propertyType = "java.lang.String",
    remark = "",
)
