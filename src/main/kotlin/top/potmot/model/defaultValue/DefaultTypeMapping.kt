package top.potmot.model.defaultValue

import top.potmot.config.GenConfig
import top.potmot.enumeration.GenLanguage
import top.potmot.model.dto.GenTypeMappingInput

fun defaultTypeMapping(): GenTypeMappingInput  = GenTypeMappingInput(
    dataSourceType = GenConfig.dataSourceType,
    language = GenConfig.language,
    typeExpression = "",
    propertyType = if (GenConfig.language == GenLanguage.JAVA) "java.lang.String" else "kotlin.String",
    orderKey = 0,
    remark = "",
)
