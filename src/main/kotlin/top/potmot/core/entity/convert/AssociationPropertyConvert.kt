package top.potmot.core.entity.convert

import org.babyfish.jimmer.sql.DissociateAction
import top.potmot.context.getContextOrGlobal
import top.potmot.core.database.generate.identifier.getIdentifierProcessor
import top.potmot.core.database.meta.getAssociations
import top.potmot.core.entity.meta.AssociationAnnotationMeta
import top.potmot.core.entity.meta.AssociationPropertyPair
import top.potmot.core.entity.meta.ConvertPropertyMeta
import top.potmot.core.entity.meta.toJoinColumns
import top.potmot.core.entity.meta.toJoinTable
import top.potmot.enumeration.AssociationType.MANY_TO_MANY
import top.potmot.enumeration.AssociationType.MANY_TO_ONE
import top.potmot.enumeration.AssociationType.ONE_TO_MANY
import top.potmot.enumeration.AssociationType.ONE_TO_ONE
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
import top.potmot.entity.GenPropertyDraft
import top.potmot.entity.copy
import top.potmot.entity.dto.GenPropertyInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.utils.string.clearTableComment
import top.potmot.utils.string.tableNameToEntityName
import top.potmot.utils.string.tableNameToPropertyName
import top.potmot.utils.string.toPlural

/**
 * 根据基础属性和表关联转换关联属性
 * 关联属性将在基础属性的基础上多出 typeTable、associationAnnotation、idView 等特征
 *
 * 因为 idView 属性并不单纯与基础属性类型相同，而是从关联属性映射而来，所以需要 typeMapping 进行对映射结果处理
 * 因为关联注释需要与数据源最终生成的 DDL 一致，所以需要 identifierFilter 处理 table 和 column
 */
@Throws(ConvertEntityException::class, ColumnTypeException::class)
fun convertAssociationProperties(
    table: GenTableConvertView,
    basePropertyMap: Map<Long, GenPropertyInput>,
    typeMapping: TypeMapping,
): Map<Long, ConvertPropertyMeta> {
    val context = getContextOrGlobal()

    val identifiers = context.dataSourceType.getIdentifierProcessor()

    val (
        outAssociations,
        inAssociations,
    ) = table
        .getAssociations()
        .reverseOneToMany()
        .reverseReversedOneToOne()
        .aggregateOtherSideLeafTableAssociations()

    val propertiesMap =
        basePropertyMap.mapValues {
            ConvertPropertyMeta(
                it.value,
                mutableListOf(),
            )
        }.toMutableMap()

    outAssociations.forEach { outAssociation ->
        val (
            association,
            sourceTable,
            sourceColumns,
            targetTable,
            _,
        ) = outAssociation

        if (association.type == ONE_TO_MANY) {
            throw ConvertEntityException.association(
                "OutAssociation [${association.name}] convert property fail: \n" +
                        "AssociationType can not be OneToMany"
            )
        }

        val sourceColumn = sourceColumns[0]

        if (!propertiesMap.containsKey(sourceColumn.id)) {
            throw ConvertEntityException.association(
                "OutAssociation [${association.name}] convert property fail: \n" +
                        "SourceColumn [${sourceColumn.name}] not found in table [${sourceTable.name}]"
            )
        }

        val current = propertiesMap[sourceColumn.id]
            ?: throw ConvertEntityException.association(
                "OutAssociation [${association.name}] convert property fail: \n" +
                        "SourceColumn [${sourceColumn.name}] converted baseProperty not found"
            )

        val sourceProperty = current.baseProperty

        val singularName =
            if (sourceProperty.idProperty)
                tableNameToPropertyName(targetTable.name)
            else
                sourceProperty.name.removeLastId()

        // 基于基础类型和关联信息制作出关联类型
        val associationProperty = sourceProperty.toEntity().copy {
            name = singularName
            if (comment.isBlank()) {
                comment = targetTable.comment.clearTableComment()
            }
            type = tableNameToEntityName(targetTable.name)
            typeTableId = targetTable.id
            idProperty = false
            idGenerationAnnotation = null

            if (association.type == ONE_TO_ONE || association.type == MANY_TO_ONE) {
                // 当外键为伪、表为逻辑删除时，需要将类型设置为可空
                if (association.fake || targetTable.logicalDelete) {
                    typeNotNull = false
                }

                setAssociation(
                    AssociationAnnotationMeta(
                        association.type,
                        joinColumns = outAssociation.toJoinColumns(identifiers)
                    ),
                    association.dissociateAction
                )

                current.enableBase = false
            }

            if (association.type == MANY_TO_MANY) {
                keyProperty = false
                setAssociation(
                    AssociationAnnotationMeta(
                        association.type,
                        joinTable = outAssociation.toJoinTable(identifiers)
                    )
                )
                toPlural()
            }
        }.let {
            GenPropertyInput(it)
        }

        val associationPropertyPair = AssociationPropertyPair(associationProperty)

        if (context.idViewProperty) {
            associationPropertyPair.idView = createIdViewProperty(
                singularName,
                sourceProperty,
                sourceColumn,
                associationProperty,
                targetTable,
                typeMapping
            )
        }

        current.associationPropertyPairs += associationPropertyPair

        propertiesMap[sourceColumn.id] = current
    }

    inAssociations.forEach { inAssociation ->
        val (
            association,
            sourceTable,
            sourceColumns,
            targetTable,
            targetColumns,
        ) = inAssociation

        if (association.type == ONE_TO_MANY) {
            throw ConvertEntityException.association(
                "InAssociation [${association.name}] convert property fail: \n" +
                        "AssociationType can not be OneToMany"
            )
        }

        val sourceColumn = sourceColumns[0]

        val targetColumn = targetColumns[0]

        val current = propertiesMap[targetColumn.id]
            ?: throw ConvertEntityException.association(
                "InAssociation [${association.name}] convert property fail: \n" +
                        "TargetColumn [${targetColumn.name}] converted property not found"
            )

        val targetProperty = current.baseProperty

        val singularName =
            if (targetProperty.idProperty)
                tableNameToPropertyName(sourceTable.name)
            else
                targetProperty.name.removeLastId()

        // 基于基础类型和关联信息制作出关联类型
        val associationProperty = targetProperty.toEntity().copy {
            name = singularName
            if (comment.isBlank()) {
                comment = sourceTable.comment.clearTableComment()
            }
            type = tableNameToEntityName(sourceTable.name)
            typeTableId = sourceTable.id
            idProperty = false
            idGenerationAnnotation = null
            keyProperty = false

            val mappedBy =
                (if (sourceColumn.partOfPk)
                    tableNameToPropertyName(targetTable.name)
                else
                    tableNameToPropertyName(sourceColumn.name).removeLastId())
                    .let {
                        if (association.type == MANY_TO_MANY) it.toPlural() else it
                    }

            // 当关联为被动方或外键为伪需要将类型设置为可空
            if (
                (association.type == ONE_TO_ONE) ||
                (association.fake && association.type == MANY_TO_ONE)
            ) {
                typeNotNull = false

            }

            setAssociation(
                AssociationAnnotationMeta(
                    association.type.reversed(),
                    mappedBy,
                )
            )

            if (association.type == MANY_TO_ONE || association.type == MANY_TO_MANY) {
                toPlural()
            }
        }.let {
            GenPropertyInput(it)
        }

        val associationPropertyPair = AssociationPropertyPair(associationProperty)

        if (context.idViewProperty) {
            associationPropertyPair.idView = createIdViewProperty(
                singularName,
                targetProperty,
                targetColumn,
                associationProperty,
                sourceTable,
                typeMapping
            )
        }

        current.associationPropertyPairs += associationPropertyPair

        propertiesMap[targetColumn.id] = current
    }

    return propertiesMap
}

private fun GenPropertyDraft.setAssociation(
    meta: AssociationAnnotationMeta,
    dissociateAction: DissociateAction? = null,
) {
    associationType = meta.type
    mappedBy = meta.mappedBy

    joinTableMeta = meta.joinTable
    joinColumnMetas = meta.joinColumns

    dissociateAnnotation = dissociateAction?.let {
        "@OnDissociate(DissociateAction.${it.name})"
    }
}

private fun String.removeLastId(): String =
    if (lowercase().endsWith("id"))
        slice(0 until length - 2)
    else
        this