package top.potmot.core.entity.convert

import org.babyfish.jimmer.sql.DissociateAction
import top.potmot.context.getContextOrGlobal
import top.potmot.core.database.generate.identifier.getIdentifierProcessor
import top.potmot.core.database.meta.getAssociations
import top.potmot.core.database.meta.getTypeMeta
import top.potmot.core.database.meta.produceOneToMany
import top.potmot.core.entity.generate.getAssociationAnnotationBuilder
import top.potmot.core.entity.meta.AssociationAnnotationMeta
import top.potmot.core.entity.meta.toJoinColumns
import top.potmot.core.entity.meta.toJoinTable
import top.potmot.enumeration.AssociationType.MANY_TO_MANY
import top.potmot.enumeration.AssociationType.MANY_TO_ONE
import top.potmot.enumeration.AssociationType.ONE_TO_MANY
import top.potmot.enumeration.AssociationType.ONE_TO_ONE
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
import top.potmot.model.GenPropertyDraft
import top.potmot.model.copy
import top.potmot.model.dto.GenPropertyInput
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.utils.string.toPlural
import top.potmot.utils.string.toSingular

/**
 * 根据基础属性和表关联转换关联属性
 * 返回 Map<columnId, property>
 * 关联属性将在基础属性的基础上多出 typeTable、associationAnnotation、idView 等特征
 *
 * 因为 idView 属性并不单纯与基础属性类型相同，而是从关联属性映射而来，所以需要 typeMapping 进行对映射结果处理
 * 因为关联注释需要与数据源最终生成的 DDL 一致，所以需要 identifierFilter 处理 table 和 column
 *
 * 为规避重复生成关联属性，
 *  关联映射仅会使用 association 中 columnReferences 中的第一组去匹配对应属性，
 *  且只在映射关联注解时才会直接基于 association 产生对应 meta
 */
@Throws(ConvertEntityException::class, ColumnTypeException::class)
fun convertAssociationProperties(
    table: GenTableAssociationsView,
    basePropertyMap: Map<Long, GenPropertyInput>,
    typeMapping: TypeMapping,
): Map<Long, List<GenPropertyInput>> {
    val context = getContextOrGlobal()

    val identifiers = context.dataSourceType.getIdentifierProcessor()

    val (
        outAssociations,
        inAssociations,
    ) = table.getAssociations().produceOneToMany()

    val propertiesMap =
        basePropertyMap.mapValues {
            mutableListOf(it.value)
        }.toMutableMap()

    outAssociations.forEach { outAssociation ->
        val (
            association,
            sourceTable,
            sourceColumns,
            targetTable,
            _,
        ) = outAssociation

        val sourceColumn = sourceColumns[0]

        if (!propertiesMap.containsKey(sourceColumn.id)) {
            throw ConvertEntityException.association(
                "OutAssociation [${association.name}] convert property fail: \n" +
                        "SourceColumn [${sourceColumn.name}] not found in table [${sourceTable.name}]"
            )
        }

        val currentColumnProperties = propertiesMap[sourceColumn.id]

        if (currentColumnProperties.isNullOrEmpty()) {
            throw ConvertEntityException.association(
                "OutAssociation [${association.name}] convert property fail: \n" +
                        "SourceColumn [${sourceColumn.name}] converted BaseProperty not found"
            )
        }

        val sourceProperty = currentColumnProperties[0]

        if (association.type == MANY_TO_ONE || association.type == ONE_TO_ONE) {
            currentColumnProperties.clear()
        }

        // 当关联类型为 ONE_TO_MANY 或 MANY_TO_MANY 时，目标属性需要为复数形式
        val targetPlural = association.type == MANY_TO_MANY

        // 基于基础类型和关联信息制作出关联类型
        val associationProperty = sourceProperty.toEntity().copy {
            name =
                if (sourceProperty.idProperty)
                    snakeToLowerCamel(targetTable.name)
                else
                    sourceProperty.name.removeLastId()

            comment = targetTable.comment.clearTableComment()
            type = snakeToUpperCamel(targetTable.name)
            typeTableId = targetTable.id
            idProperty = false
            idGenerationAnnotation = null

            when (association.type) {
                ONE_TO_ONE, MANY_TO_ONE -> {
                    setAssociation(
                        AssociationAnnotationMeta(
                            association.type,
                            joinColumns = outAssociation.toJoinColumns(identifiers)
                        ),
                        association.dissociateAction
                    )

                    // 当外键为伪时，需要将类型设置为可空
                    if (association.fake) {
                        typeNotNull = false
                    }
                }

                MANY_TO_MANY -> {
                    keyProperty = false
                    setAssociation(
                        AssociationAnnotationMeta(
                            association.type,
                            joinTable = outAssociation.toJoinTable(identifiers)
                        )
                    )
                }

                ONE_TO_MANY -> {
                    throw ConvertEntityException.association(
                        "OutAssociation [${association.name}] convert property fail: \n" +
                                "AssociationType can not be OneToMany"
                    )
                }
            }

            // 当目标为复数时，该属性也为复数
            if (targetPlural) toPlural()
        }.let {
            GenPropertyInput(it)
        }

        currentColumnProperties += associationProperty

        if (context.idViewProperty) {
            currentColumnProperties += createIdViewProperty(
                sourceProperty,
                sourceColumn,
                associationProperty,
                typeMapping
            )
        }

        propertiesMap[sourceColumn.id] = currentColumnProperties
    }

    inAssociations.forEach { inAssociation ->
        val (
            association,
            sourceTable,
            sourceColumns,
            targetTable,
            targetColumns,
        ) = inAssociation

        val sourceColumn = sourceColumns[0]

        val targetColumn = targetColumns[0]

        val currentColumnProperties = propertiesMap[targetColumn.id]

        if (currentColumnProperties.isNullOrEmpty()) {
            throw ConvertEntityException.association(
                "InAssociation [${association.name}] convert property fail: \n" +
                        "TargetColumn [${targetColumn.name}] converted property not found"
            )
        }

        val targetProperty = currentColumnProperties[0]

        if (association.type == ONE_TO_MANY) {
            currentColumnProperties.clear()
        }

        // 当关联类型为 MANY_TO_ONE 或 MANY_TO_MANY 时，来源属性需要为复数形式
        val sourcePlural = association.type == MANY_TO_ONE || association.type == MANY_TO_MANY

        // 当关联类型为 ONE_TO_MANY 或 MANY_TO_MANY 时，目标属性需要为复数形式
        val targetPlural = association.type == ONE_TO_MANY || association.type == MANY_TO_MANY

        // 基于基础类型和关联信息制作出关联类型
        val associationProperty = targetProperty.toEntity().copy {
            name =
                if (targetProperty.idProperty)
                    snakeToLowerCamel(sourceTable.name)
                else
                    targetProperty.name.removeLastId()

            comment = sourceTable.comment.clearTableComment()
            type = snakeToUpperCamel(sourceTable.name)
            typeTableId = sourceTable.id
            idProperty = false
            idGenerationAnnotation = null

            when (association.type) {
                ONE_TO_ONE, MANY_TO_ONE, MANY_TO_MANY -> {
                    keyProperty = false

                    val mappedBy =
                        (if (sourceColumn.partOfPk)
                            snakeToLowerCamel(targetTable.name)
                        else
                            snakeToLowerCamel(sourceColumn.name).removeLastId())
                            .let {
                                if (targetPlural) it.toPlural() else it
                            }

                    setAssociation(
                        AssociationAnnotationMeta(
                            association.type.reversed(),
                            mappedBy,
                        )
                    )

                    // 当关联为一对一时，被动方需要将类型设置为可空
                    if (association.type == ONE_TO_ONE) {
                        typeNotNull = false
                    }
                    // 当外键为伪时，需要将类型设置为可空
                    if (association.fake && association.type == MANY_TO_ONE) {
                        typeNotNull = false
                    }
                }

                ONE_TO_MANY -> {
                    throw ConvertEntityException.association(
                        "OutAssociation [${association.name}] convert property fail: \n" +
                                "AssociationType can not be OneToMany"
                    )
                }
            }

            if (sourcePlural) toPlural()
        }.let {
            GenPropertyInput(it)
        }

        currentColumnProperties += associationProperty

        if (context.idViewProperty) {
            currentColumnProperties += createIdViewProperty(
                targetProperty,
                targetColumn,
                associationProperty,
                typeMapping
            )
        }

        propertiesMap[targetColumn.id] = currentColumnProperties
    }

    return propertiesMap
}

private fun GenPropertyDraft.toPlural() {
    name = name.toPlural()
    listType = true
    typeNotNull = true
    keyProperty = false
    logicalDelete = false
}

private fun GenPropertyDraft.setAssociation(
    meta: AssociationAnnotationMeta,
    dissociateAction: DissociateAction? = null,
) {
    val context = getContextOrGlobal()
    val associationAnnotationBuilder = context.language.getAssociationAnnotationBuilder()

    associationType = meta.type
    associationAnnotation = associationAnnotationBuilder.build(meta)
    dissociateAnnotation = dissociateAction?.let {
        "@OnDissociate(DissociateAction.${it.name})"
    }
}

/**
 * 从基础属性和关联属性生成 IdView
 *
 * @param baseProperty 基础属性
 * @param baseColumn 基础列
 * @param associationProperty 关联属性
 */
private fun createIdViewProperty(
    baseProperty: GenPropertyInput,
    baseColumn: GenTableAssociationsView.TargetOf_columns,
    associationProperty: GenPropertyInput,
    typeMapping: TypeMapping,
): GenPropertyInput =
    baseProperty.toEntity()
        .copy {
            idProperty = false
            idGenerationAnnotation = null

            if (associationProperty.typeNotNull != baseProperty.typeNotNull) {
                this.typeNotNull = associationProperty.typeNotNull
            }

            if (associationProperty.listType) {
                this.name = associationProperty.name.toSingular() + "Id"
                this.toPlural()
            } else {
                this.name = associationProperty.name + "Id"
            }

            this.type = typeMapping(
                baseColumn.getTypeMeta().copy(
                    // 当为列表属性时，java 为允许集合泛型使用，此时映射时必须调整为可空模式
                    // 除此以外同步关联属性的可空性
                    typeNotNull = if (this.listType) false else associationProperty.typeNotNull
                )
            )

            associationProperty.comment.takeIf { it.isNotBlank() }?.let {
                this.comment = "$it ID 视图"
            }
            this.associationType = associationProperty.associationType
            this.idView = true
            this.idViewAnnotation = "@IdView(\"${associationProperty.name}\")"
            this.keyProperty = false
        }.let {
            GenPropertyInput(it)
        }

private fun String.removeLastId(): String =
    if (lowercase().endsWith("id"))
        slice(0 until length - 2)
    else
        this
