package top.potmot.model

import top.potmot.entity.dto.GenModelInput
import top.potmot.entity.sub.AnnotationWithImports
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.DatabaseNamingStrategyType
import top.potmot.enumeration.GenLanguage
import top.potmot.enumeration.ViewType

fun createBaseModel(
    graphData: String,
    enums: List<GenModelInput.TargetOf_enums> = emptyList(),
) = baseModel.copy(
    graphData = graphData,
    enums = enums
)

private val baseModel = GenModelInput(
    name = "test",
    graphData = "",
    language = GenLanguage.KOTLIN,
    dataSourceType = DataSourceType.PostgreSQL,
    viewType = ViewType.VUE3_ELEMENT_PLUS,
    author = "",
    packagePath = "top.potmot",
    tablePath = "",
    databaseNamingStrategy = DatabaseNamingStrategyType.UPPER_CASE,
    realFk = true,
    idViewProperty = true,
    defaultIdType = 4,
    generatedIdAnnotation = AnnotationWithImports(
        imports = listOf(
            "org.babyfish.jimmer.sql.GeneratedValue",
            "org.babyfish.jimmer.sql.GenerationType",
        ),
        annotations = listOf(
            "@GeneratedValue(strategy = GenerationType.IDENTITY)"
        )
    ),
    logicalDeletedAnnotation = AnnotationWithImports(
        imports = listOf(
            "org.babyfish.jimmer.sql.LogicalDeleted",
        ),
        annotations = listOf(
            "@LogicalDeleted"
        )
    ),
    dateTimeFormatInView = false,  // 需要替换为实际的布尔值
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
    remark = "",
    subGroups = emptyList(),
    enums = emptyList(),
)
