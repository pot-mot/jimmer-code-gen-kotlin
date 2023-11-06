package top.potmot.core.convert

import org.babyfish.jimmer.kt.new
import top.potmot.config.GenConfig
import top.potmot.core.immutable.copyProperties
import top.potmot.core.template.table.fullType
import top.potmot.enumeration.AssociationType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenProperty
import top.potmot.model.GenPropertyDraft
import top.potmot.model.GenTypeMapping
import top.potmot.model.by
import top.potmot.model.dto.GenTableAssociationsView


/**
 * 这部分为处理列到属性映射的工具函数
 * 基于关联类型（many or one）和关联方向（in or out）
 * 得到以下一个基本函数和八个关联属性函数：
 *
 * toBaseProperty
 *
 * 出关联（主动向外发散的关联，使用 to 表示当前列【转化】为关联列）
 * toOneToOneProperty
 * toManyToOneProperty
 * toOneToManyProperty
 * toManyToManyProperty
 *
 * 入关联（因为入关联与列本身无关，所以使用 get 表示【追加】而不是从原始列进行转化）
 * getOneToOneProperty
 * getOneToManyProperty
 * getManyToOneProperty
 * getManyToManyProperty
 */


/**
 * 转换为基础属性
 */
fun GenTableAssociationsView.TargetOf_columns.toBaseProperty(
    typeMappings: List<GenTypeMapping> = emptyList(),
): GenProperty {
    val column = this

    return new(GenProperty::class).by {
        this.columnId = column.id
        this.name = columnNameToPropertyName(column.name)
        this.type = column.typeName(typeMappings)
        this.comment = column.comment.clearColumnComment()
        this.typeNotNull = column.typeNotNull
        this.keyProperty = column.partOfUniqueIdx
    }
}

private fun GenPropertyDraft.setAssociation(
    associationType: AssociationType,
    reverse: Boolean = false,
    mappedBy: String? = null,
) {
    val tempAssociationType =
        if (reverse) {
            when (associationType) {
                AssociationType.MANY_TO_ONE -> AssociationType.ONE_TO_MANY
                AssociationType.ONE_TO_MANY -> AssociationType.MANY_TO_ONE
                else -> associationType
            }
        } else {
            associationType
        }

    this.associationType = tempAssociationType

    this.associationAnnotation = "@" + tempAssociationType.toAnnotation().simpleName
    mappedBy?.takeIf { it.isNotBlank() }?.let {
        this.associationAnnotation += "(mappedBy = \"${it}\")"
    }
}

/**
 * 从基础属性和关联属性生成 IdView
 *
 * @param baseProperty 基础属性
 * @param associationProperty 关联属性
 * @param plural 是否复数形式，默认单数形式
 */
fun createIdViewProperty(
    baseProperty: GenProperty,
    associationProperty: GenProperty,
    plural: Boolean = false
): GenProperty {
    return new(GenProperty::class).by {
        copyProperties(baseProperty, this)

        if (plural) {
            this.name = associationProperty.name.toSingular() + "Ids"
            this.listType = true
            this.typeNotNull = true
        } else {
            this.name = associationProperty.name + "Id"
        }

        this.comment = associationProperty.comment + " ID 视图"
        this.associationType = associationProperty.associationType
        this.idView = true
        this.idViewAnnotation = "@IdView(\"${associationProperty.name}\")"
        this.keyProperty = false
    }
}

typealias OutAssociationConvert = (
    sourceColumn: GenTableAssociationsView.TargetOf_columns,
    outAssociation: GenTableAssociationsView.TargetOf_columns.TargetOf_outAssociations,
    typeMappings: List<GenTypeMapping>,
) -> List<GenProperty>

fun toOutAssociationProperty(
    baseProperty: GenProperty,
    sourceColumn: GenTableAssociationsView.TargetOf_columns,
    outAssociation: GenTableAssociationsView.TargetOf_columns.TargetOf_outAssociations,
    plural: Boolean = false
): GenProperty {
    val targetColumn = outAssociation.targetColumn

    return new(GenProperty::class).by {
        copyProperties(baseProperty, this)

        if (plural) {
            this.name = this.name.toPlural()
            this.listType = true
            this.typeNotNull = true
        } else {
            this.name = sourceColumn.name.toOutPropertyName()
        }

        this.type = tableNameToClassName(targetColumn.table.name)
        this.typeTableId = targetColumn.table.id
        this.comment = targetColumn.table.comment.clearTableComment()

        setAssociation(outAssociation.associationType)
        outAssociation.dissociateAction?.let {
            this.dissociateAnnotation = "@OnDissociate(DissociateAction.${it})"
        }
    }
}

val toOneToOneProperty: OutAssociationConvert = { sourceColumn,
                                                  outAssociation,
                                                  typeMappings ->
    val baseProperty = sourceColumn.toBaseProperty(typeMappings)

    val associationProperty = toOutAssociationProperty(baseProperty, sourceColumn, outAssociation)

    val idViewProperty = createIdViewProperty(baseProperty, associationProperty)

    listOf(associationProperty, idViewProperty)
}

val toManyToOneProperty: OutAssociationConvert = { sourceColumn,
                                                   outAssociation,
                                                   typeMappings ->
    val baseProperty = sourceColumn.toBaseProperty(typeMappings)

    val associationProperty = toOutAssociationProperty(baseProperty, sourceColumn, outAssociation)

    val idViewProperty = createIdViewProperty(baseProperty, associationProperty)

    listOf(associationProperty, idViewProperty)
}

val toOneToManyProperty: OutAssociationConvert = { sourceColumn,
                                                   outAssociation,
                                                   typeMappings ->
    TODO()
}

val toManyToManyProperty: OutAssociationConvert = { sourceColumn,
                                                    outAssociation,
                                                    typeMappings ->
    TODO()
}

typealias InAssociationConvert = (
    targetColumn: GenTableAssociationsView.TargetOf_columns,
    inAssociation: GenTableAssociationsView.TargetOf_columns.TargetOf_inAssociations,
    typeMappings: List<GenTypeMapping>,
) -> List<GenProperty>

fun toInAssociationProperty(
    baseProperty: GenProperty,
    inAssociation: GenTableAssociationsView.TargetOf_columns.TargetOf_inAssociations,
    plural: Boolean = false,
    mappedByPlural: Boolean = false
): GenProperty {
    val sourceColumn = inAssociation.sourceColumn

    return new(GenProperty::class).by {
        copyProperties(baseProperty, this)

        this.name = tableNameToPropertyName(sourceColumn.table.name)
        this.type = tableNameToClassName(sourceColumn.table.name)
        this.typeTableId = sourceColumn.table.id
        this.comment = sourceColumn.table.comment.clearTableComment()
        this.keyProperty = false

        if (plural) {
            this.name = this.name.toPlural()
            this.listType = true
            this.typeNotNull = true
        }

        setAssociation(
            inAssociation.associationType,
            true,
            sourceColumn.name.toOutPropertyName().let {
                if (mappedByPlural) {
                    it.toPlural()
                } else {
                    it
                }
            }
        )
        inAssociation.dissociateAction?.let {
            this.dissociateAnnotation = "@OnDissociate(DissociateAction.${it})"
        }
    }
}

val getOneToOneProperty: InAssociationConvert = { targetColumn,
                                                  inAssociation,
                                                  typeMappings ->
    val baseProperty = targetColumn.toBaseProperty(typeMappings)

    val associationProperty = toInAssociationProperty(baseProperty, inAssociation)

    val idViewProperty = createIdViewProperty(baseProperty, associationProperty)

    listOf(associationProperty, idViewProperty)
}

val getOneToManyProperty: InAssociationConvert = { targetColumn,
                                                   inAssociation,
                                                   typeMappings ->
    val baseProperty = targetColumn.toBaseProperty(typeMappings)

    val associationProperty = toInAssociationProperty(baseProperty, inAssociation, plural = true)

    val idViewProperty = createIdViewProperty(baseProperty, associationProperty, plural = true)

    listOf(associationProperty, idViewProperty)
}

val getManyToOneProperty: InAssociationConvert = { targetColumn,
                                                   inAssociation,
                                                   typeMappings ->
    TODO()
}

val getManyToManyProperty: InAssociationConvert = { targetColumn,
                                                    inAssociation,
                                                    typeMappings ->
    TODO()
}

/**
 * 转换成出关联属性名
 */
private fun String.toOutPropertyName(): String =
    columnNameToPropertyName(this).removeSuffix("Id")

/**
 * 设置属性类型
 */
private fun GenTableAssociationsView.TargetOf_columns.typeName(
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
        GenLanguage.JAVA -> jdbcTypeToJavaType(typeCode, typeNotNull)?.name ?: type
        GenLanguage.KOTLIN -> jdbcTypeToKotlinType(typeCode)?.qualifiedName ?: type
    }
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
 * 将名词转换为单数形式。
 * @return 转换后的单数形式
 */
private fun String.toSingular(): String {
    val singular: String = when {
        endsWith("ies") -> dropLast(3) + "y"
        endsWith("es") -> dropLast(2)
        endsWith("s") -> dropLast(1)
        else -> this
    }
    return singular
}

/**
 * 扩展方法，只是为了方便调用
 */

fun GenTableAssociationsView.TargetOf_columns.toOneToOneProperty(
    outAssociation: GenTableAssociationsView.TargetOf_columns.TargetOf_outAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> =
    toOneToOneProperty(this, outAssociation, typeMappings)

fun GenTableAssociationsView.TargetOf_columns.toManyToOneProperty(
    outAssociation: GenTableAssociationsView.TargetOf_columns.TargetOf_outAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> =
    toManyToOneProperty(this, outAssociation, typeMappings)

fun GenTableAssociationsView.TargetOf_columns.toOneToManyProperty(
    outAssociation: GenTableAssociationsView.TargetOf_columns.TargetOf_outAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> =
    toOneToManyProperty(this, outAssociation, typeMappings)

fun GenTableAssociationsView.TargetOf_columns.toManyToManyProperty(
    outAssociation: GenTableAssociationsView.TargetOf_columns.TargetOf_outAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> =
    toManyToManyProperty(this, outAssociation, typeMappings)

fun GenTableAssociationsView.TargetOf_columns.getOneToOneProperty(
    inAssociation: GenTableAssociationsView.TargetOf_columns.TargetOf_inAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> =
    getOneToOneProperty(this, inAssociation, typeMappings)

fun GenTableAssociationsView.TargetOf_columns.getOneToManyProperty(
    inAssociation: GenTableAssociationsView.TargetOf_columns.TargetOf_inAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> =
    getOneToManyProperty(this, inAssociation, typeMappings)

fun GenTableAssociationsView.TargetOf_columns.getManyToOneProperty(
    inAssociation: GenTableAssociationsView.TargetOf_columns.TargetOf_inAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> =
    getManyToOneProperty(this, inAssociation, typeMappings)

fun GenTableAssociationsView.TargetOf_columns.getManyToManyProperty(
    inAssociation: GenTableAssociationsView.TargetOf_columns.TargetOf_inAssociations,
    typeMappings: List<GenTypeMapping> = emptyList(),
): List<GenProperty> =
    getManyToManyProperty(this, inAssociation, typeMappings)
