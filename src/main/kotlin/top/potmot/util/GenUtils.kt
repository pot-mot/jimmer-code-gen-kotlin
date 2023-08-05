package top.potmot.util

import org.babyfish.jimmer.ImmutableObjects
import org.babyfish.jimmer.kt.new
import top.potmot.config.GenConfig
import top.potmot.constant.Language
import top.potmot.constant.TableType
import top.potmot.model.GenTable
import top.potmot.model.GenTableColumn
import top.potmot.model.GenTypeMapping
import top.potmot.model.by
import top.potmot.util.NameConvertUtils.columnNameToFieldName
import top.potmot.util.NameConvertUtils.tableCommentToFunctionName
import top.potmot.util.NameConvertUtils.packageNameToModuleName
import top.potmot.util.NameConvertUtils.tableNameToClassName

/**
 * 代码生成器 工具类
 *
 * @author potmot */
object GenUtils {
    /**
     * 初始化表到实体类的信息
     */
    fun initClassInfo(
        genTable: GenTable,
        typeMappings: List<GenTypeMapping> = emptyList()
    ): GenTable {
        return new(GenTable::class).by(genTable) {
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

            columns = genTable.columns.map {
                initFieldInfo(it, genTable, typeMappings)
            }
        }
    }

    /**
     * 初始化列到字段的信息
     */
    fun initFieldInfo(
        column: GenTableColumn,
        table: GenTable,
        typeMappings: List<GenTypeMapping> = emptyList(),
    ): GenTableColumn {
        return new(GenTableColumn::class).by(column) {
            if (ImmutableObjects.isLoaded(table, "id")) {
                tableId = table.id
            }
            fieldName = columnNameToFieldName(column.columnName)
            fieldType = getFieldTypeName(column, typeMappings)
            fieldComment = column.columnComment
            isAddRequired = column.isNotNull
            isEditRequired = column.isNotNull
        }
    }

    /**
     * 设置字段类型
     */
    fun getFieldTypeName(
        column: GenTableColumn,
        typeMappings: List<GenTypeMapping> = emptyList(),
        language: Language = GenConfig.language,
        defaultType: String = GenConfig.defaultType
    ): String {
        return when (language) {
            Language.JAVA -> jdbcTypeToJavaType(column.columnTypeCode, column.isNotNull).name
            Language.KOTLIN -> jdbcTypeToKotlinType(column.columnTypeCode).qualifiedName ?: defaultType
        }
    }
}
