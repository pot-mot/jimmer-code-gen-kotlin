package top.potmot.core.convert

import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.GenerationType
import top.potmot.config.GenConfig
import top.potmot.enum.AssociationType
import top.potmot.enum.GenLanguage
import top.potmot.enum.TableType
import top.potmot.model.GenEntity
import top.potmot.model.GenProperty
import top.potmot.model.GenTypeMapping
import top.potmot.model.by
import top.potmot.model.dto.GenTableAssociationView

/**
 * 表到实体转换
 */
fun tableToEntity(
    table: GenTableAssociationView,
): GenEntity {
    return new(GenEntity::class).by {
        this.table = table.toEntity()
        this.author = GenConfig.author
        this.name = tableNameToClassName(table.name)
        this.comment = table.comment

        if (table.type == TableType.VIEW) {
            isAdd = false
            isEdit = false
        }
    }
}

/**
 * 列到属性转换
 * 具备关联时将生成关联字段
 * TODO 目前仅支持 ManyToOne 和 OneToOne，不支持 ManyToMany
 * 目前转换规则如下:
 * outAssociations 出关联（一般场景下一列应该只有一种指向，则虽然代码中为 forEach，但实际操作为单指）
 *     -> 当前列转换成 ManyToOne 并补充 IdView
 *     -> 当前列转换成 OneToOne 并补充 IdView
 * inAssociations 入关联
 *     -> 添加 OneToMany 和 IdView
 *     -> 添加 OneToOne 和 IdView
 *
 * warning: 为保证保存时关联成立，请保证对应 column 具有 id
 */
fun columnToProperties(
    column: GenTableAssociationView.TargetOf_columns,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val properties = mutableListOf<GenProperty>()

    column.outAssociations.forEach {
        if (it.associationType == AssociationType.MANY_TO_ONE) {
            properties += column.toManyToOneProperty(it, typeMappings)
        } else if (it.associationType == AssociationType.ONE_TO_ONE) {
            properties += column.toOneToOneProperty(it, typeMappings)
        }
    }

    if (properties.isEmpty()) {
        properties += column.toBaseProperty(typeMappings)
    }

    column.inAssociations.forEach {
        if (it.associationType == AssociationType.MANY_TO_ONE) {
            properties += column.getOneToManyProperty(it, typeMappings)
        } else if (it.associationType == AssociationType.ONE_TO_ONE) {
            properties += column.getOneToOneProperty(it, typeMappings)
        }
    }

    return properties
}

fun GenTableAssociationView.TargetOf_columns.toBaseProperty(
    typeMappings: List<GenTypeMapping> = emptyList(),
): GenProperty {
    val column = this

    return new(GenProperty::class).by {
        this.column = column.toEntity()
        this.name = columnNameToPropertyName(column.name)
        this.type = getPropertyTypeName(column, typeMappings)
        this.comment = column.comment
        this.isNotNull = column.isNotNull
        this.isKey = column.isUnique

        if (column.isPk) {
            isId = true
            if (column.isAutoIncrement) {
                idGenerationType = GenerationType.IDENTITY
            }
        }
    }
}

fun GenTableAssociationView.TargetOf_columns.toManyToOneProperty(
    outAssociation: GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val sourceColumn = this

    val targetColumn = outAssociation.targetColumn

    val ManyToOneProperty = new(GenProperty::class).by {
        this.column = sourceColumn.toEntity()
        this.name = tableNameToPropertyName(targetColumn.table.name)
        this.type = tableNameToClassName(targetColumn.table.name)
        this.typeTableId = targetColumn.table.id
        this.comment = targetColumn.table.comment
        this.isNotNull = sourceColumn.isNotNull
        this.associationType = AssociationType.MANY_TO_ONE
        this.associationAnnotation = "@ManyToOne"
        outAssociation.dissociateAction?.let {
            this.dissociateAnnotation = "@OnDissociate(DissociateAction.${outAssociation.dissociateAction})"
        }
    }

    val IdViewProperty = new(GenProperty::class).by {
        this.column = sourceColumn.toEntity()
        this.name = tableNameToPropertyName(targetColumn.table.name) + "Id"
        this.type = getPropertyTypeName(sourceColumn, typeMappings)
        this.comment = targetColumn.table.comment + " ID 视图"
        this.isNotNull = sourceColumn.isNotNull
        this.isIdView = true
        this.idViewAnnotation = "@IdView(\"${ManyToOneProperty.name}\")"
        this.associationType = AssociationType.MANY_TO_ONE
    }

    return listOf(ManyToOneProperty, IdViewProperty)
}

fun GenTableAssociationView.TargetOf_columns.toOneToOneProperty(
    outAssociation: GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val sourceColumn = this

    val targetColumn = outAssociation.targetColumn

    val OneToOneProperty = new(GenProperty::class).by {
        this.column = sourceColumn.toEntity()
        this.name = tableNameToPropertyName(targetColumn.table.name)
        this.type = tableNameToClassName(targetColumn.table.name)
        this.typeTableId = targetColumn.table.id
        this.comment = targetColumn.table.comment
        this.isNotNull = sourceColumn.isNotNull
        this.associationType = AssociationType.ONE_TO_ONE
        this.associationAnnotation = "@OneToOne"
        outAssociation.dissociateAction?.let {
            this.dissociateAnnotation = "@OnDissociate(DissociateAction.${outAssociation.dissociateAction})"
        }
    }

    val IdViewProperty = new(GenProperty::class).by {
        this.column = sourceColumn.toEntity()
        this.name = tableNameToPropertyName(targetColumn.table.name) + "Id"
        this.type = getPropertyTypeName(sourceColumn, typeMappings)
        this.comment = targetColumn.table.comment + " ID 视图"
        this.isNotNull = sourceColumn.isNotNull
        this.isIdView = true
        this.idViewAnnotation = "@IdView(\"${OneToOneProperty.name}\")"
        this.associationType = AssociationType.ONE_TO_ONE
    }

    return listOf(OneToOneProperty, IdViewProperty)
}

fun GenTableAssociationView.TargetOf_columns.getOneToManyProperty(
    inAssociation: GenTableAssociationView.TargetOf_columns.TargetOf_inAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val targetColumn = this

    val sourceColumn = inAssociation.sourceColumn

    val OneToManyProperty = new(GenProperty::class).by {
        this.column = targetColumn.toEntity()
        this.name = tableNameToPropertyName(sourceColumn.table.name).toPlural()
        this.type = tableNameToClassName(sourceColumn.table.name)
        this.isList = true
        this.typeTableId = sourceColumn.table.id
        this.comment = sourceColumn.table.comment
        this.isNotNull = targetColumn.isNotNull
        this.isIdView = true
        this.associationType = AssociationType.ONE_TO_MANY
        this.associationAnnotation = "@OneToMany(mappedBy = \"${tableNameToPropertyName(sourceColumn.table.name)}\")"
    }

    val IdViewProperty = new(GenProperty::class).by {
        this.column = targetColumn.toEntity()
        this.name = tableNameToPropertyName(sourceColumn.table.name) + "Ids"
        this.type = getPropertyTypeName(targetColumn, typeMappings)
        this.isList = true
        this.comment = sourceColumn.table.comment + " ID 视图"
        this.isNotNull = targetColumn.isNotNull
        this.isIdView = true
        this.idViewAnnotation = "@IdView(\"${OneToManyProperty.name}\")"
        this.associationType = AssociationType.ONE_TO_MANY
    }

    return listOf(OneToManyProperty, IdViewProperty)
}

fun GenTableAssociationView.TargetOf_columns.getOneToOneProperty(
    inAssociation: GenTableAssociationView.TargetOf_columns.TargetOf_inAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val targetColumn = this

    val sourceColumn = inAssociation.sourceColumn


    val OneToOneProperty = new(GenProperty::class).by {
        this.column = targetColumn.toEntity()
        this.name = tableNameToPropertyName(sourceColumn.table.name)
        this.type = tableNameToClassName(sourceColumn.table.name)
        this.typeTableId = sourceColumn.table.id
        this.comment = sourceColumn.table.comment
        this.isNotNull = targetColumn.isNotNull
        this.associationType = AssociationType.ONE_TO_ONE
        this.associationAnnotation = "@OneToOne(mappedBy = \"${tableNameToPropertyName(sourceColumn.table.name)}\")"
    }

    val IdViewProperty = new(GenProperty::class).by {
        this.column = targetColumn.toEntity()
        this.name = tableNameToPropertyName(sourceColumn.table.name) + "Id"
        this.type = getPropertyTypeName(targetColumn, typeMappings)
        this.comment = sourceColumn.table.comment + " ID 视图"
        this.isNotNull = targetColumn.isNotNull
        this.isIdView = true
        this.idViewAnnotation = "@IdView(\"${OneToOneProperty.name}\")"
        this.associationType = AssociationType.ONE_TO_ONE
    }

    return listOf(OneToOneProperty, IdViewProperty)
}

/**
 * 设置属性类型
 */
fun getPropertyTypeName(
    column: GenTableAssociationView.TargetOf_columns,
    typeMappings: List<GenTypeMapping> = emptyList(),
    language: GenLanguage = GenConfig.language,
    defaultType: String = GenConfig.defaultType
): String {
    return when (language) {
        GenLanguage.JAVA -> jdbcTypeToJavaType(column.typeCode, column.isNotNull).name
        GenLanguage.KOTLIN -> jdbcTypeToKotlinType(column.typeCode).qualifiedName ?: defaultType
    }
}
