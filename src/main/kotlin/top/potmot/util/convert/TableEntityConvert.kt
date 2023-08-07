package top.potmot.util.convert

import org.babyfish.jimmer.ImmutableObjects
import org.babyfish.jimmer.kt.new
import top.potmot.config.GenConfig
import top.potmot.constant.Language
import top.potmot.constant.TableType
import top.potmot.model.GenTable
import top.potmot.model.GenColumn
import top.potmot.model.GenEntity
import top.potmot.model.GenProperty
import top.potmot.model.GenTypeMapping
import top.potmot.model.by

/**
 * 初始化表到实体类的信息
 * warning: 如果需要保存生成的结果，请一定保证 对应 table 已经 save
 */
fun tableToEntity(
    genTable: GenTable,
    typeMappings: List<GenTypeMapping> = emptyList()
): GenEntity {
    return new(GenEntity::class).by {
        table = genTable
        if (ImmutableObjects.isLoaded(genTable, "id")) {
            tableId = genTable.id
        }
        author = GenConfig.author
        packageName = GenConfig.packageName
        className = tableNameToClassName(genTable.tableName)
        classComment = genTable.tableComment
        moduleName = packageNameToModuleName(GenConfig.packageName)
        functionName = tableCommentToFunctionName(genTable.tableComment)

        if (genTable.tableType == TableType.VIEW.value) {
            isAdd = false
            isEdit = false
        }

        properties = genTable.columns.map {
            columnToField(it, typeMappings)
        }
    }
}

/**
 * 初始化列到字段的信息
 * warning: 如果需要保存生成的结果，请一定保证 对应 column 已经 save
 */
fun columnToField(
    genColumn: GenColumn,
    typeMappings: List<GenTypeMapping> = emptyList(),
): GenProperty {
    return new(GenProperty::class).by {
        column = genColumn
        if (ImmutableObjects.isLoaded(genColumn, "id")) {
            columnId = genColumn.id
        }
        propertyName = columnNameToPropertyName(genColumn.columnName)
        propertyType = getPropertyTypeName(genColumn, typeMappings)
        propertyComment = genColumn.columnComment
        isAddRequired = genColumn.isNotNull
        isEditRequired = genColumn.isNotNull
    }
}

/**
 * 设置字段类型
 */
fun getPropertyTypeName(
    column: GenColumn,
    typeMappings: List<GenTypeMapping> = emptyList(),
    language: Language = GenConfig.language,
    defaultType: String = GenConfig.defaultType
): String {
    return when (language) {
        Language.JAVA -> jdbcTypeToJavaType(column.columnTypeCode, column.isNotNull).name
        Language.KOTLIN -> jdbcTypeToKotlinType(column.columnTypeCode).qualifiedName ?: defaultType
    }
}
