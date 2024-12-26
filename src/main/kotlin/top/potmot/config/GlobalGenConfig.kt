package top.potmot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import top.potmot.context.initContextGlobal
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.DatabaseNamingStrategyType
import top.potmot.enumeration.GenLanguage
import top.potmot.entity.dto.MutableGenConfig
import javax.annotation.PostConstruct

/**
 * 代码生成配置
 */
@Component
@ConfigurationProperties(prefix = "jimmer-code-gen")
class GlobalGenConfig : MutableGenConfig(
    dataSourceType = DataSourceType.MySQL,
    language = GenLanguage.KOTLIN,
    realFk = true,
    databaseNamingStrategy = DatabaseNamingStrategyType.RAW,
    author = "",
    packagePath = "com.example",
    tablePath = "",
    idViewProperty = true,
    logicalDeletedAnnotation = "@LogicalDeleted(\"true\")",
    tableAnnotation = true,
    columnAnnotation = true,
    joinTableAnnotation = true,
    joinColumnAnnotation = true,
    tableNamePrefixes = "",
    tableNameSuffixes = "",
    tableCommentPrefixes = "",
    tableCommentSuffixes = "",
    columnNamePrefixes = "",
    columnNameSuffixes = "",
    columnCommentPrefixes = "",
    columnCommentSuffixes = "",
) {
    @PostConstruct
    fun init() {
        initContextGlobal(this)
    }
}

// TODO 移入GenConfig
const val tableColumnWithDateTimeFormat: Boolean = true
