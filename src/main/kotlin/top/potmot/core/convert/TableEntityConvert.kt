package top.potmot.core.convert

import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.GenerationType
import top.potmot.config.GenConfig
import top.potmot.core.immutable.copyProperties
import top.potmot.core.template.table.fullType
import top.potmot.enumeration.AssociationType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenEntity
import top.potmot.model.GenProperty
import top.potmot.model.GenTypeMapping
import top.potmot.model.by
import top.potmot.model.copy
import top.potmot.model.dto.GenTableAssociationView

fun GenTableAssociationView.toGenEntity(
    typeMappings: List<GenTypeMapping> = emptyList(),
): GenEntity =
    convertTableToEntity(this, typeMappings)

private fun convertTableToEntity(
    table: GenTableAssociationView,
    typeMappings: List<GenTypeMapping> = emptyList(),
): GenEntity {
    val baseEntity = tableToEntity(table)

    val properties = table.columns.flatMap { column ->
        columnToProperties(
            column,
            typeMappings
        )
    }

    return baseEntity.copy {
        this.properties = properties
    }
}

/**
 * 表到实体转换
 */
private fun tableToEntity(
    genTable: GenTableAssociationView,
): GenEntity {
    return new(GenEntity::class).by {
        tableId = genTable.toEntity().id
        author = GenConfig.author
        name = tableNameToClassName(genTable.name)
        comment = genTable.comment.clearTableComment()
    }
}

/**
 * 列到属性转换
 * 具备关联时将生成关联字段
 * TODO 目前仅支持 ManyToOne 和 OneToOne，不支持 ManyToMany
 * 目前转换规则如下:
 * outAssociations 出关联（一般场景下一列应该只有一个 out 指向）
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
    val properties = mutableListOf<GenProperty>()

    genColumn.outAssociations.forEach {
        if (it.associationType == AssociationType.MANY_TO_ONE) {
            properties += genColumn.toManyToOneProperty(it, typeMappings)
        } else if (it.associationType == AssociationType.ONE_TO_ONE) {
            properties += genColumn.toOneToOneProperty(it, typeMappings)
        }
    }

    if (properties.isEmpty()) {
        val baseProperty = genColumn.toBaseProperty(typeMappings)
        properties += if (genColumn.partOfPk) {
            new(GenProperty::class).by {
                copyProperties(baseProperty, this)

                idProperty = true
                notNull = true
                keyProperty = false
                listType = false
                logicalDelete = false
                idView = false
                if (genColumn.autoIncrement) {
                    idGenerationType = GenerationType.IDENTITY
                }
            }
        } else {
            baseProperty
        }
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

/**
 * 转换为基础属性
 */
fun GenTableAssociationView.TargetOf_columns.toBaseProperty(
    typeMappings: List<GenTypeMapping> = emptyList(),
): GenProperty {
    val column = this

    return new(GenProperty::class).by {
        this.columnId = column.id
        this.name = columnNameToPropertyName(column.name)
        this.type = column.typeName(typeMappings)
        this.comment = column.comment.clearColumnComment()
        this.notNull = column.notNull
        this.keyProperty = column.partOfUniqueIdx
    }
}

/**
 * 转换为 ManyToOne 属性
 * 基于 outAssociation 进行
 */
fun GenTableAssociationView.TargetOf_columns.toManyToOneProperty(
    outAssociation: GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val sourceColumn = this

    val targetColumn = outAssociation.targetColumn

    val baseProperty = toBaseProperty(typeMappings)

    val manyToOneProperty = new(GenProperty::class).by {
        copyProperties(baseProperty, this)

        this.associationType = AssociationType.MANY_TO_ONE

        this.name = sourceColumn.name.toOutPropertyName()
        this.type = tableNameToClassName(targetColumn.table.name)
        this.typeTableId = targetColumn.table.id
        this.comment = targetColumn.table.comment.clearTableComment()
        this.associationAnnotation = "@ManyToOne"
        outAssociation.dissociateAction?.let {
            this.dissociateAnnotation = "@OnDissociate(DissociateAction.${outAssociation.dissociateAction})"
        }
    }

    val idViewProperty = new(GenProperty::class).by {
        copyProperties(baseProperty, this)

        this.associationType = AssociationType.MANY_TO_ONE

        this.name = manyToOneProperty.name + "Id"
        this.comment = manyToOneProperty.comment + " ID 视图"
        this.idView = true
        this.idViewAnnotation = "@IdView(\"${manyToOneProperty.name}\")"
        this.keyProperty = false
    }

    return listOf(manyToOneProperty, idViewProperty)
}

/**
 * 转换为 OneToOne 属性
 * 基于 outAssociation 进行
 */
fun GenTableAssociationView.TargetOf_columns.toOneToOneProperty(
    outAssociation: GenTableAssociationView.TargetOf_columns.TargetOf_outAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val sourceColumn = this

    val targetColumn = outAssociation.targetColumn

    val baseProperty = toBaseProperty(typeMappings)

    val oneToOneProperty = new(GenProperty::class).by {
        copyProperties(baseProperty, this)

        this.associationType = AssociationType.ONE_TO_ONE

        this.name = sourceColumn.name.toOutPropertyName()
        this.type = tableNameToClassName(targetColumn.table.name)
        this.typeTableId = targetColumn.table.id
        this.comment = targetColumn.table.comment.clearTableComment()
        this.associationAnnotation = "@OneToOne"
        outAssociation.dissociateAction?.let {
            this.dissociateAnnotation = "@OnDissociate(DissociateAction.${outAssociation.dissociateAction})"
        }
    }

    val idViewProperty = new(GenProperty::class).by {
        copyProperties(baseProperty, this)
        this.associationType = AssociationType.ONE_TO_ONE

        this.name = oneToOneProperty.name + "Id"
        this.comment = oneToOneProperty.comment + " ID 视图"
        this.idView = true
        this.idViewAnnotation = "@IdView(\"${oneToOneProperty.name}\")"
        this.keyProperty = false
    }

    return listOf(oneToOneProperty, idViewProperty)
}

private fun String.toOutPropertyName(): String =
    columnNameToPropertyName(this).removeSuffix("Id")

/**
 * 获取 OneToMany 属性
 * 基于 inAssociation 进行
 */
fun GenTableAssociationView.TargetOf_columns.getOneToManyProperty(
    inAssociation: GenTableAssociationView.TargetOf_columns.TargetOf_inAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val sourceColumn = inAssociation.sourceColumn

    val baseProperty = toBaseProperty(typeMappings)

    val oneToManyProperty = new(GenProperty::class).by {
        copyProperties(baseProperty, this)
        this.associationType = AssociationType.ONE_TO_MANY

        this.name = tableNameToPropertyName(sourceColumn.table.name).toPlural()
        this.type = tableNameToClassName(sourceColumn.table.name)
        this.typeTableId = sourceColumn.table.id
        this.listType = true
        this.notNull = true
        this.comment = sourceColumn.table.comment.clearTableComment()
        this.associationAnnotation = "@OneToMany(mappedBy = \"${sourceColumn.name.toOutPropertyName()}\")"
        this.keyProperty = false
    }

    val idViewProperty = new(GenProperty::class).by {
        copyProperties(baseProperty, this)
        this.associationType = AssociationType.ONE_TO_MANY

        this.name = tableNameToPropertyName(sourceColumn.table.name) + "Ids"
        this.listType = true
        this.notNull = true
        this.comment = oneToManyProperty.comment + " ID 视图"
        this.idView = true
        this.idViewAnnotation = "@IdView(\"${oneToManyProperty.name}\")"
        this.keyProperty = false
    }

    return listOf(oneToManyProperty, idViewProperty)
}

/**
 * 将名词转换为复数形式。
 * @return 转换后的复数形式
 */
private fun String.toPlural(): String {
    val plural: String = when {
        endsWith("s") || endsWith("x") || endsWith("z") || endsWith("ch") || endsWith("sh") ->
            this + "es"

        endsWith("y") -> dropLast(1) + "ies"
        else -> this + "s"
    }
    return plural
}

/**
 * 获取 OneToOne 属性
 * 基于 inAssociation 进行
 */
fun GenTableAssociationView.TargetOf_columns.getOneToOneProperty(
    inAssociation: GenTableAssociationView.TargetOf_columns.TargetOf_inAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> {
    val sourceColumn = inAssociation.sourceColumn

    val baseProperty = toBaseProperty(typeMappings)

    val oneToOneProperty = new(GenProperty::class).by {
        copyProperties(baseProperty, this)
        this.associationType = AssociationType.ONE_TO_ONE

        this.name = tableNameToPropertyName(sourceColumn.table.name)
        this.type = tableNameToClassName(sourceColumn.table.name)
        this.typeTableId = sourceColumn.table.id
        this.comment = sourceColumn.table.comment.clearTableComment()
        this.associationAnnotation = "@OneToOne(mappedBy = \"${sourceColumn.name.toOutPropertyName()}\")"
        this.keyProperty = false
    }

    val idViewProperty = new(GenProperty::class).by {
        copyProperties(baseProperty, this)
        this.associationType = AssociationType.ONE_TO_ONE

        this.name = tableNameToPropertyName(sourceColumn.table.name) + "Id"
        this.comment = oneToOneProperty.comment + " ID 视图"
        this.idView = true
        this.idViewAnnotation = "@IdView(\"${oneToOneProperty.name}\")"
        this.keyProperty = false
    }

    return listOf(oneToOneProperty, idViewProperty)
}

/**
 * 设置属性类型
 */
private fun GenTableAssociationView.TargetOf_columns.typeName(
    typeMappings: List<GenTypeMapping>,
    language: GenLanguage = GenConfig.language,
): String {
    for (typeMapping in typeMappings) {
        val matchResult =
            if (typeMapping.regex) {
                Regex(typeMapping.typeExpression).matches(fullType())
            } else {
                typeMapping.typeExpression == fullType()
            }

        if (matchResult) {
            return typeMapping.propertyType
        }
    }

    return when (language) {
        GenLanguage.JAVA -> jdbcTypeToJavaType(typeCode, notNull)?.name ?: type
        GenLanguage.KOTLIN -> jdbcTypeToKotlinType(typeCode)?.qualifiedName ?: type
    }
}
