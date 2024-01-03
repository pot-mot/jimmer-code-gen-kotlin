package top.potmot.core.entity.convert

import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.GenerationType
import top.potmot.core.database.meta.ColumnTypeMeta
import top.potmot.core.database.meta.getPropertyType
import top.potmot.core.database.meta.getTypeMeta
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenProperty
import top.potmot.model.by
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTypeMappingView

typealias TypeMapping = (column: ColumnTypeMeta) -> String

fun createProperties(
    table: GenTableAssociationsView,
    language: GenLanguage,
    dataSourceType: DataSourceType,
    typeMappings: List<GenTypeMappingView>
): List<GenProperty> {
    val typeMapping: TypeMapping = { column -> column.getPropertyType(dataSourceType, language, typeMappings) }

    val baseColumnPropertyPairs = table.columns.map {column ->
        column to column.toBaseProperty(typeMapping).let {property ->
            if (column.partOfPk) property.toIdProperty(column) else property
        }
    }

    return producePropertyWithAssociationAndUniqueIndexes(
        table,
        baseColumnPropertyPairs,
        typeMapping
    )
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
        this.idGenerationType  = null
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
