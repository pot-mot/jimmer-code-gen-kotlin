package top.potmot.core.entity.convert

import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.GenerationType
import top.potmot.core.database.meta.getTypeMeta
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
import top.potmot.model.GenProperty
import top.potmot.model.by
import top.potmot.model.dto.ColumnTypeMeta
import top.potmot.model.dto.GenTableAssociationsView

typealias TypeMapping = (column: ColumnTypeMeta) -> String

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
): GenProperty {
    val column = this

    return new(GenProperty::class).by {
        this.columnId = column.id
        this.name = columnNameToPropertyName(column.name)
        this.comment = column.comment.clearColumnComment()
        this.type = typeMapping(column.getTypeMeta())
        this.typeTableId = null
        this.listType = false
        this.typeNotNull = column.typeNotNull
        this.idProperty = false
        this.idGenerationType = null
        this.keyProperty = column.businessKey
        this.logicalDelete = column.logicalDelete
        this.enumId = column.enumId
        this.idView = false
        this.idViewAnnotation = null
        this.associationType = null
        this.associationAnnotation = null
        this.dissociateAnnotation = null
        this.otherAnnotation = null
        this.remark = column.remark
        this.orderKey = column.orderKey
    }
}

private fun GenProperty.toIdProperty(
    column: GenTableAssociationsView.TargetOf_columns
) =
    new(GenProperty::class).by(this) {
        idProperty = true
        typeNotNull = true
        keyProperty = false
        logicalDelete = false
        idView = false
        if (column.autoIncrement) {
            idGenerationType = GenerationType.IDENTITY
        }
    }
