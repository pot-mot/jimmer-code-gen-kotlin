package top.potmot.core.entity.convert

import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
import top.potmot.entity.dto.GenPropertyInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.utils.string.clearColumnComment
import top.potmot.utils.string.snakeToLowerCamel

/**
 * 转换基本属性
 * 返回 Map<columnId, property>
 */
@Throws(ConvertEntityException::class, ColumnTypeException::class)
fun convertBaseProperties(
    table: GenTableConvertView,
    typeMapping: TypeMapping,
) =
    table.columns.associate { column ->
        column.id to column
            .toBaseProperty(typeMapping)
            .let { property ->
                if (column.partOfPk) property.toIdProperty(column) else property
            }
    }

/**
 * 转换为基础属性
 */
fun GenTableConvertView.TargetOf_columns.toBaseProperty(
    typeMapping: TypeMapping,
): GenPropertyInput {
    val column = this

    return GenPropertyInput(
        columnId = column.id,
        name = snakeToLowerCamel(column.name),
        comment = column.comment.clearColumnComment(),
        type = typeMapping(column),
        typeTableId = null,
        listType = false,
        typeNotNull = column.typeNotNull,
        idProperty = false,
        idGenerationAnnotation = null,
        keyProperty = column.businessKey,
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
