package top.potmot.core.entity.convert.base

import top.potmot.core.entity.convert.business.initBusinessConfig
import top.potmot.core.entity.convert.merge.mergeExistAndConvertProperty
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertException
import top.potmot.entity.dto.GenPropertyInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.utils.string.clearColumnComment
import top.potmot.utils.string.columnNameToPropertyName

/**
 * 转换基本属性
 * 返回 Map<columnId, property>
 */
@Throws(ConvertException::class, ColumnTypeException::class)
fun convertBaseProperties(
    table: GenTableConvertView,
    existEntity: GenEntityDetailView?,
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
                    val matchedProperty = existEntity.properties.firstOrNull { it.columnId == column.id && it.associationType == null }
                    if (matchedProperty != null) {
                        return@let GenPropertyInput(mergeExistAndConvertProperty(matchedProperty, property))
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
): GenPropertyInput {
    val column = this

    return GenPropertyInput(
        columnId = column.id,
        name = columnNameToPropertyName(column.name),
        comment = column.comment.clearColumnComment(),
        type = typeMapping(column),
        typeTableId = null,
        listType = false,
        typeNotNull = column.typeNotNull,
        idProperty = false,
        idGenerationAnnotation = null,
        keyProperty = column.businessKey,
        keyGroup = column.keyGroup,
        logicalDelete = column.logicalDelete,
        enumId = column.enumId,
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

private fun GenPropertyInput.toIdProperty(
    column: GenTableConvertView.TargetOf_columns
) =
    copy(
        idProperty = true,
        typeNotNull = true,
        keyProperty = false,
        logicalDelete = false,
        idView = false,
        idGenerationAnnotation = if (column.autoIncrement) "@GeneratedValue(strategy = GenerationType.IDENTITY)" else null
    )
