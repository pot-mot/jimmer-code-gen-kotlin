package top.potmot.core.entity.convert.base

import top.potmot.core.config.getContextOrGlobal
import top.potmot.core.entity.convert.EntityView
import top.potmot.core.entity.convert.PropertyInput
import top.potmot.core.entity.convert.business.initBusinessConfig
import top.potmot.core.entity.convert.merge.mergeExistAndConvertProperty
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.sub.AnnotationWithImports
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertException
import top.potmot.utils.string.clearForPropertyComment
import top.potmot.utils.string.columnNameToPropertyName

typealias TypeMapping = (column: GenTableConvertView.TargetOf_columns) -> String

/**
 * 转换基本属性
 * 返回 Map<columnId, property>
 */
@Throws(ConvertException::class, ColumnTypeException::class)
fun convertBaseProperties(
    table: GenTableConvertView,
    existEntity: EntityView?,
    typeMapping: TypeMapping,
) =
    table.columns.associate { column ->
        column.id to column
            .toBaseProperty(typeMapping)
            .let { property ->
                if (column.partOfPk) property.toIdProperty(column) else property
            }
            .initBusinessConfig()
            .let { property ->
                if (existEntity != null) {
                    val matchedProperty =
                        existEntity.properties.firstOrNull { it.columnId == column.id && it.associationType == null }
                    if (matchedProperty != null) {
                        return@let PropertyInput(mergeExistAndConvertProperty(matchedProperty, property))
                    }
                }

                property
            }
    }

/**
 * 转换为基础属性
 */
private fun GenTableConvertView.TargetOf_columns.toBaseProperty(
    typeMapping: TypeMapping,
): PropertyInput {
    val column = this

    return PropertyInput(
        columnId = column.id,
        name = columnNameToPropertyName(column.name),
        comment = column.comment.clearForPropertyComment(),
        type = typeMapping(column),
        typeTableId = null,
        listType = false,
        typeNotNull = column.typeNotNull,
        idProperty = false,
        generatedId = false,
        keyProperty = column.businessKey,
        keyGroup = column.keyGroup,
        logicalDelete = column.logicalDelete,
        logicalDeletedAnnotation = if (logicalDelete) getContextOrGlobal().logicalDeletedAnnotation else null,
        enumId = column.enum?.id,
        idView = false,
        idViewTarget = null,
        associationType = null,
        inputNotNull = null,
        joinColumnMetas = null,
        joinTableMeta = null,
        dissociateAnnotation = null,
        otherAnnotation = null,
        remark = column.remark,
        orderKey = column.orderKey,
        inListView = true,
        inDetailView = true,
        inInsertInput = true,
        inUpdateInput = true,
        inSpecification = true,
        inOptionView = false,
        inShortAssociationView = false,
        inLongAssociationView = true,
        inLongAssociationInput = true,
        longAssociation = false,
    )
}

private fun PropertyInput.toIdProperty(
    column: GenTableConvertView.TargetOf_columns,
) = copy(
    idProperty = true,
    typeNotNull = true,
    keyProperty = false,
    logicalDelete = false,
    idView = false,
    generatedId = column.autoIncrement,
    generatedIdAnnotation = 
    if (column.autoIncrement) {
        AnnotationWithImports(
            imports = listOf(
                "org.babyfish.jimmer.sql.GeneratedValue",
                "org.babyfish.jimmer.sql.GenerationType",
            ),
            annotations = listOf(
                "@GeneratedValue(strategy = GenerationType.IDENTITY)"
            )
        )
    } else if (generatedId) {
        getContextOrGlobal().generatedIdAnnotation
    } else {
        null
    }
)
