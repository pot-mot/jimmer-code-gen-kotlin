package top.potmot.core.entity.convert

import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.GenerationType
import top.potmot.core.meta.ColumnTypeMeta
import top.potmot.core.meta.getPropertyType
import top.potmot.core.meta.getTypeMeta
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenProperty
import top.potmot.model.by
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTypeMappingView
import top.potmot.utils.immutable.copyProperties

typealias TypeMapping = (column: ColumnTypeMeta) -> String

fun createProperties(
    table: GenTableAssociationsView,
    language: GenLanguage,
    dataSourceType: DataSourceType,
    typeMappings: List<GenTypeMappingView>
): List<GenProperty> {
    val typeMapping: TypeMapping = { column -> column.getPropertyType(dataSourceType, language, typeMappings) }

    val baseColumnPropertyPairs = table.columns.map {
        it to columnToProperty(it, typeMapping)
    }

    return producePropertyWithAssociationAndUniqueIndexes(
        table,
        baseColumnPropertyPairs,
        typeMapping
    )
}

/**
 * 列到属性转换
 * warning: 为保证保存时关联成立，请保证对应 column 具有 id
 */
fun columnToProperty(
    column: GenTableAssociationsView.TargetOf_columns,
    typeMapping: TypeMapping = {it.getPropertyType()},
): GenProperty {
    val baseProperty = column.toBaseProperty(typeMapping)

    return new(GenProperty::class).by {
            copyProperties(baseProperty, this)

            if (column.partOfPk) toIdProperty(column)
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
