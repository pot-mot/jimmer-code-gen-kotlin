package top.potmot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.DatabaseNamingStrategyType
import top.potmot.enumeration.GenLanguage
import top.potmot.entity.dto.MutableGenConfig

/**
 * 代码生成配置
 */
@Component
@ConfigurationProperties(prefix = "jimmer-code-gen")
object GlobalGenConfig : MutableGenConfig(
    dataSourceType = DataSourceType.MySQL,
    language = GenLanguage.KOTLIN,
    realFk = true,
    databaseNamingStrategy = DatabaseNamingStrategyType.LOWER_CASE,
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
)
