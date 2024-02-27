package top.potmot.model

import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.dto.GenConfigProperties

/**
 * 基础测试属性集
 * 可配合 multiple 实现笛卡尔积
 */

val dataSourceTypeProperties =
    DataSourceType.entries.map { GenConfigProperties(dataSourceType = it) }

val languageProperties =
    GenLanguage.entries.map { GenConfigProperties(language = it) }

val lowerCaseNameProperties =
    listOf(true, false).map { GenConfigProperties(lowerCaseName = it) }

val realFkProperties =
    listOf(true, false).map { GenConfigProperties(realFk = it) }
