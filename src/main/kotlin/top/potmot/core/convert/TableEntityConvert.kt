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
import top.potmot.model.copy
import top.potmot.model.dto.GenTableAssociationView

/**
 * 表到实体转换
 */
fun tableToEntity(
    genTable: GenTableAssociationView,
): GenEntity {
    return new(GenEntity::class).by {
        table = genTable.toEntity()
        author = GenConfig.author
        name = tableNameToClassName(genTable.name)
        comment = genTable.comment

        if (genTable.type == TableType.VIEW) {
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
    genColumn: GenTableAssociationView.TargetOf_columns,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    if (genColumn.isPk) {
        val idProperty = genColumn.toBaseProperty(typeMappings).copy {
            isId = true
            isNotNull = true
            isKey = false
            isList = false
            isLogicalDelete = false
            isIdView = false
            if (genColumn.isAutoIncrement) {
                idGenerationType = GenerationType.IDENTITY
            }
        }

        return listOf(idProperty)
    }

    val properties = mutableListOf<GenProperty>()

    genColumn.outAssociations.forEach {
        if (it.associationType == AssociationType.MANY_TO_ONE) {
            properties += genColumn.toManyToOneProperty(it, typeMappings)
        } else if (it.associationType == AssociationType.ONE_TO_ONE) {
            properties += genColumn.toOneToOneProperty(it, typeMappings)
        }
    }

    if (properties.isEmpty()) {
        properties += genColumn.toBaseProperty(typeMappings)
    }

    genColumn.inAssociations.forEach {
        if (it.associationType == AssociationType.MANY_TO_ONE) {
            properties += genColumn.getOneToManyProperty(it, typeMappings)
        } else if (it.associationType == AssociationType.ONE_TO_ONE) {
            properties += genColumn.getOneToOneProperty(it, typeMappings)
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
        this.type = column.typeName(typeMappings)
        this.comment = column.comment
        this.isNotNull = column.isNotNull
        this.isKey = column.isUnique
    }
}

fun GenTableAssociationView.TargetOf_columns.toManyToOneProperty(
    outAssociation: GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val targetColumn = outAssociation.targetColumn

    val baseProperty = toBaseProperty(typeMappings)

    val manyToOneProperty = baseProperty.copy {
        this.associationType = AssociationType.MANY_TO_ONE

        this.name = tableNameToPropertyName(targetColumn.table.name)
        this.type = tableNameToClassName(targetColumn.table.name)
        this.typeTableId = targetColumn.table.id
        this.comment = targetColumn.table.comment
        this.associationAnnotation = "@ManyToOne"
        outAssociation.dissociateAction?.let {
            this.dissociateAnnotation = "@OnDissociate(DissociateAction.${outAssociation.dissociateAction})"
        }
    }

    val idViewProperty = baseProperty.copy {
        this.associationType = AssociationType.MANY_TO_ONE

        this.name = manyToOneProperty.name + "Id"
        this.comment = manyToOneProperty.comment + " ID 视图"
        this.isIdView = true
        this.idViewAnnotation = "@IdView(\"${manyToOneProperty.name}\")"
    }

    return listOf(manyToOneProperty, idViewProperty)
}

fun GenTableAssociationView.TargetOf_columns.toOneToOneProperty(
    outAssociation: GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val targetColumn = outAssociation.targetColumn

    val baseProperty = toBaseProperty(typeMappings)

    val oneToOneProperty = baseProperty.copy {
        this.associationType = AssociationType.ONE_TO_ONE

        this.name = tableNameToPropertyName(targetColumn.table.name)
        this.type = tableNameToClassName(targetColumn.table.name)
        this.typeTableId = targetColumn.table.id
        this.comment = targetColumn.table.comment
        this.associationAnnotation = "@OneToOne"
        outAssociation.dissociateAction?.let {
            this.dissociateAnnotation = "@OnDissociate(DissociateAction.${outAssociation.dissociateAction})"
        }
    }

    val idViewProperty = baseProperty.copy {
        this.associationType = AssociationType.ONE_TO_ONE

        this.name = oneToOneProperty.name + "Id"
        this.comment = oneToOneProperty.comment + " ID 视图"
        this.isIdView = true
        this.idViewAnnotation = "@IdView(\"${oneToOneProperty.name}\")"
    }

    return listOf(oneToOneProperty, idViewProperty)
}

fun GenTableAssociationView.TargetOf_columns.getOneToManyProperty(
    inAssociation: GenTableAssociationView.TargetOf_columns.TargetOf_inAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val sourceColumn = inAssociation.sourceColumn

    val baseProperty = toBaseProperty(typeMappings)

    val oneToManyProperty = baseProperty.copy {
        this.associationType = AssociationType.ONE_TO_MANY

        this.name = tableNameToPropertyName(sourceColumn.table.name).toPlural()
        this.type = tableNameToClassName(sourceColumn.table.name)
        this.isList = true
        this.typeTableId = sourceColumn.table.id
        this.comment = sourceColumn.table.comment
        this.associationAnnotation = "@OneToMany(mappedBy = \"${tableNameToPropertyName(sourceColumn.table.name)}\")"
    }

    val idViewProperty = baseProperty.copy {
        this.associationType = AssociationType.ONE_TO_MANY

        this.name = tableNameToPropertyName(sourceColumn.table.name) + "Ids"
        this.isList = true
        this.comment = oneToManyProperty.comment + " ID 视图"
        this.isIdView = true
        this.idViewAnnotation = "@IdView(\"${oneToManyProperty.name}\")"
    }

    return listOf(oneToManyProperty, idViewProperty)
}

fun GenTableAssociationView.TargetOf_columns.getOneToOneProperty(
    inAssociation: GenTableAssociationView.TargetOf_columns.TargetOf_inAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val sourceColumn = inAssociation.sourceColumn

    val baseProperty = toBaseProperty(typeMappings)

    val oneToOneProperty = baseProperty.copy {
        this.associationType = AssociationType.ONE_TO_ONE

        this.name = tableNameToPropertyName(sourceColumn.table.name)
        this.type = tableNameToClassName(sourceColumn.table.name)
        this.typeTableId = sourceColumn.table.id
        this.comment = sourceColumn.table.comment
        this.associationAnnotation = "@OneToOne(mappedBy = \"${tableNameToPropertyName(sourceColumn.table.name)}\")"
    }

    val idViewProperty = baseProperty.copy {
        this.associationType = AssociationType.ONE_TO_ONE

        this.name = tableNameToPropertyName(sourceColumn.table.name) + "Id"
        this.comment = oneToOneProperty.comment + " ID 视图"
        this.isIdView = true
        this.idViewAnnotation = "@IdView(\"${oneToOneProperty.name}\")"
    }

    return listOf(oneToOneProperty, idViewProperty)
}

/**
 * 设置属性类型
 */
private fun GenTableAssociationView.TargetOf_columns.typeName(
    typeMappings: List<GenTypeMapping> = emptyList(),
    language: GenLanguage = GenConfig.language,
): String {
    return when (language) {
        GenLanguage.JAVA -> jdbcTypeToJavaType(typeCode, isNotNull)?.name ?: type
        GenLanguage.KOTLIN -> jdbcTypeToKotlinType(typeCode)?.qualifiedName ?: type
    }
}
