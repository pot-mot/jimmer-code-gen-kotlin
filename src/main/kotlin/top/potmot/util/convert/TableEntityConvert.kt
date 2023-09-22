package top.potmot.util.convert

import org.babyfish.jimmer.ImmutableObjects
import org.babyfish.jimmer.kt.new
import top.potmot.config.GenConfig
import top.potmot.enum.Language
import top.potmot.enum.QueryType
import top.potmot.enum.TableType
import top.potmot.model.GenColumn
import top.potmot.model.GenEntity
import top.potmot.model.GenProperty
import top.potmot.model.GenTable
import top.potmot.model.GenTypeMapping
import top.potmot.model.by

/**
 * 初始化表到实体类的信息
 *
 * 需要保证 table 的基本属性均被加载
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
        className = tableNameToClassName(genTable.name)
        classComment = genTable.comment
        moduleName = packageNameToModuleName(GenConfig.packageName)
        functionName = commentToFunctionName(genTable.comment)

        if (genTable.type == TableType.VIEW) {
            isAdd = false
            isEdit = false
        }

        properties = genTable.columns.map {
            columnToProperty(it, typeMappings)
        }
    }
}

/**
 * 初始化列到属性的信息
 * warning: 如果需要保存生成的结果，请一定保证 对应 column 已经 save
 */
fun columnToProperty(
    genColumn: GenColumn,
    typeMappings: List<GenTypeMapping> = emptyList(),
): GenProperty {
    return new(GenProperty::class).by {
        column = genColumn
        if (ImmutableObjects.isLoaded(genColumn, "id")) {
            columnId = genColumn.id
        }
        name = columnNameToPropertyName(genColumn.name)
        propertyType = getPropertyTypeName(genColumn, typeMappings)
        propertyComment = genColumn.comment
        isAddRequired = genColumn.isNotNull
        isEditRequired = genColumn.isNotNull

        listSort = genColumn.orderKey
        addSort = genColumn.orderKey
        editSort = genColumn.orderKey
        querySort = genColumn.orderKey

        queryType = getPropertyQueryType(genColumn)
    }
}

/**
 * 设置属性类型
 */
fun getPropertyTypeName(
    column: GenColumn,
    typeMappings: List<GenTypeMapping> = emptyList(),
    language: Language = GenConfig.language,
    defaultType: String = GenConfig.defaultType
): String {
    return when (language) {
        Language.JAVA -> jdbcTypeToJavaType(column.typeCode, column.isNotNull).name
        Language.KOTLIN -> jdbcTypeToKotlinType(column.typeCode).qualifiedName ?: defaultType
    }
}

/**
 * 设置 ManyToOne 关联属性
 */

// TODO 补充完整类型映射
fun getPropertyQueryType(
    column: GenColumn,
): QueryType {
    return QueryType.EQ
}
