package top.potmot.core.entity.convert

import top.potmot.core.database.meta.getTypeMeta
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
import top.potmot.model.dto.GenPropertyInput
import top.potmot.model.dto.GenTableAssociationsView

/**
 * 转换基本属性
 * 返回 Map<columnId, property>
 */
@Throws(ConvertEntityException::class, ColumnTypeException::class)
fun convertBaseProperties(
    table: GenTableAssociationsView,
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
fun GenTableAssociationsView.TargetOf_columns.toBaseProperty(
    typeMapping: TypeMapping,
): GenPropertyInput {
    val column = this

    return GenPropertyInput(
        columnId = column.id,
        name = snakeToLowerCamel(column.name),
        comment = column.comment.clearColumnComment(),
        type = typeMapping(column.getTypeMeta()),
        typeTableId = null,
        listType = false,
        typeNotNull = column.typeNotNull,
        idProperty = false,
        idGenerationAnnotation = null,
        keyProperty = column.businessKey,
        logicalDelete = column.logicalDelete,
        enumId = column.enumId,
        idView = false,
        idViewAnnotation = null,
        associationType = null,
        associationAnnotation = null,
        dissociateAnnotation = null,
        otherAnnotation = null,
        remark = column.remark,
        orderKey = column.orderKey,
    )
}

private fun GenPropertyInput.toIdProperty(
    column: GenTableAssociationsView.TargetOf_columns
) =
    copy(
        idProperty = true,
        typeNotNull = true,
        keyProperty = false,
        logicalDelete = false,
        idView = false,
        idGenerationAnnotation = if (column.autoIncrement) "@GeneratedValue(strategy = GenerationType.IDENTITY)" else null
    )
