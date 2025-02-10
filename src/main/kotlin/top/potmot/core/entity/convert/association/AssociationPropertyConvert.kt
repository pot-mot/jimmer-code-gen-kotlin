package top.potmot.core.entity.convert.association

import org.babyfish.jimmer.kt.unload
import org.babyfish.jimmer.sql.DissociateAction
import top.potmot.context.getContextOrGlobal
import top.potmot.core.database.generate.identifier.getIdentifierProcessor
import top.potmot.core.entity.convert.base.TypeMapping
import top.potmot.core.entity.convert.base.tableToEntityPackagePath
import top.potmot.core.entity.convert.base.toPlural
import top.potmot.core.entity.convert.business.initAssociationBusinessConfig
import top.potmot.core.entity.convert.idview.createIdViewProperty
import top.potmot.core.entity.convert.merge.AssociationPropertyPairWaitMergeExist
import top.potmot.core.entity.convert.meta.AssociationAnnotationMeta
import top.potmot.core.entity.convert.meta.ConvertPropertyMeta
import top.potmot.core.entity.convert.meta.TableAssociationMeta
import top.potmot.entity.GenProperty
import top.potmot.entity.GenPropertyDraft
import top.potmot.entity.copy
import top.potmot.core.entity.convert.PropertyInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.IdName
import top.potmot.entity.dto.IdNullableName
import top.potmot.enumeration.AssociationType.MANY_TO_MANY
import top.potmot.enumeration.AssociationType.MANY_TO_ONE
import top.potmot.enumeration.AssociationType.ONE_TO_MANY
import top.potmot.enumeration.AssociationType.ONE_TO_ONE
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertException
import top.potmot.utils.string.clearTableComment
import top.potmot.utils.string.entityNameToPropertyName
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
@Throws(ConvertException::class, ColumnTypeException::class)
fun convertAssociationProperties(
    basePropertyMap: Map<Long, PropertyInput>,
    typeMapping: TypeMapping,
    associationMeta: TableAssociationMeta,
): Map<Long, ConvertPropertyMeta> {
    val context = getContextOrGlobal()

    val identifiers = context.dataSourceType.getIdentifierProcessor()

    val (
        outAssociationMetas,
        inAssociationMetas,
    ) = associationMeta

    val propertiesMap =
        basePropertyMap.mapValues {
            ConvertPropertyMeta(
                it.value,
                mutableListOf(),
            )
        }.toMutableMap()

    outAssociationMetas.forEach { meta ->
        val (
            association,
            sourceTable,
            sourceEntity,
            sourceColumns,
            sourceProperties,
            targetTable,
            targetEntity,
            targetColumns,
            targetProperties,
        ) = meta

        if (sourceColumns.size != 1 || targetColumns.size != 1)
            throw ConvertException.multipleColumnsNotSupported(
                association = IdName(association.id, association.name),
                sourceTable = IdName(sourceTable.id, sourceTable.name),
                sourceColumns = sourceColumns.map { IdName(it.id, it.name) },
                targetTable = IdName(targetTable.id, targetTable.name),
                targetColumns = targetColumns.map { IdName(it.id, it.name) },
            )

        val sourceColumn = sourceColumns[0]
        val targetColumn = targetColumns[0]

        if (association.type == ONE_TO_MANY) {
            throw ConvertException.associationCannotBeOneToMany(
                "OutAssociation [${association.name}] convert property fail: \n" +
                        "AssociationType can not be OneToMany",
                association = IdName(association.id, association.name),
                sourceTable = IdName(sourceTable.id, sourceTable.name),
                sourceColumn = IdName(sourceColumn.id, sourceColumn.name),
                targetTable = IdName(targetTable.id, targetTable.name),
                targetColumn = IdName(targetColumn.id, targetColumn.name),
            )
        }

        if (!propertiesMap.containsKey(sourceColumn.id)) {
            throw ConvertException.outAssociationCannotFountSourceColumn(
                "OutAssociation [${association.name}] convert property fail: \n" +
                        "SourceColumn [${sourceColumn.name}] not found in table [${sourceTable.name}]",
                association = IdName(association.id, association.name),
                sourceTable = IdName(sourceTable.id, sourceTable.name),
                sourceColumn = IdNullableName(sourceColumn.id, sourceColumn.name),
                targetTable = IdName(targetTable.id, targetTable.name),
                targetColumn = IdName(targetColumn.id, targetColumn.name),
            )
        }

        val current = propertiesMap[sourceColumn.id]
            ?: throw ConvertException.outAssociationCannotFoundSourceBaseProperty(
                "OutAssociation [${association.name}] convert property fail: \n" +
                        "SourceColumn [${sourceColumn.name}] converted baseProperty not found",
                association = IdName(association.id, association.name),
                sourceTable = IdName(sourceTable.id, sourceTable.name),
                sourceColumn = IdName(sourceColumn.id, sourceColumn.name),
                targetTable = IdName(targetTable.id, targetTable.name),
                targetColumn = IdName(targetColumn.id, targetColumn.name),
            )

        val sourceProperty = current.baseProperty

        val singularName =
            if (sourceProperty.idProperty) {
                if (targetEntity != null) {
                    entityNameToPropertyName(targetEntity.name)
                } else {
                    tableNameToPropertyName(targetTable.name)
                }
            } else {
                sourceProperty.name.removeLastId()
            }

        // 基于基础类型和关联信息制作出关联类型
        val associationProperty = sourceProperty.toEntity().copy {
            unload(this, GenProperty::id)
            name = singularName
            if (comment.isBlank() || this.idProperty) {
                comment = targetEntity?.comment ?: targetTable.comment.clearTableComment()
            }
            type = targetEntity?.let { "${it.packagePath}.${it.name}" } ?: tableToEntityFullPath(
                targetTable,
                context.packagePath
            )
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
                        joinColumns = meta.toJoinColumns(identifiers)
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
                        joinTable = meta.toJoinTable(identifiers)
                    )
                )
                toPlural()
            }
        }
            .let { PropertyInput(it) }
            .initAssociationBusinessConfig(tableId = sourceTable.id)

        current.associationPropertyPairs +=
            if (context.idViewProperty) {
                val idView = createIdViewProperty(
                    singularName,
                    sourceProperty,
                    sourceColumn,
                    associationProperty,
                    targetTable,
                    typeMapping
                ).initAssociationBusinessConfig(tableId = sourceTable.id)

                AssociationPropertyPairWaitMergeExist(associationProperty, idView, sourceProperties)
            } else {
                AssociationPropertyPairWaitMergeExist(associationProperty, null, sourceProperties)
            }

        propertiesMap[sourceColumn.id] = current
    }

    inAssociationMetas.forEach { meta ->
        val (
            association,
            sourceTable,
            sourceEntity,
            sourceColumns,
            sourceProperties,
            targetTable,
            targetEntity,
            targetColumns,
            targetProperties,
        ) = meta

        if (sourceColumns.size != 1 || targetColumns.size != 1)
            throw ConvertException.multipleColumnsNotSupported(
                association = IdName(association.id, association.name),
                sourceTable = IdName(sourceTable.id, sourceTable.name),
                sourceColumns = sourceColumns.map { IdName(it.id, it.name) },
                targetTable = IdName(targetTable.id, targetTable.name),
                targetColumns = targetColumns.map { IdName(it.id, it.name) },
            )

        val sourceColumn = sourceColumns[0]
        val targetColumn = targetColumns[0]

        if (association.type == ONE_TO_MANY) {
            throw ConvertException.associationCannotBeOneToMany(
                "InAssociation [${association.name}] convert property fail: \n" +
                        "AssociationType can not be OneToMany",
                association = IdName(association.id, association.name),
                sourceTable = IdName(sourceTable.id, sourceTable.name),
                sourceColumn = IdName(sourceColumn.id, sourceColumn.name),
                targetTable = IdName(targetTable.id, targetTable.name),
                targetColumn = IdName(targetColumn.id, targetColumn.name),
            )
        }

        val current = propertiesMap[targetColumn.id]
            ?: throw ConvertException.inAssociationCannotFoundTargetBaseProperty(
                "InAssociation [${association.name}] convert property fail: \n" +
                        "TargetColumn [${targetColumn.name}] converted property not found",
                association = IdName(association.id, association.name),
                sourceTable = IdName(sourceTable.id, sourceTable.name),
                sourceColumn = IdName(sourceColumn.id, sourceColumn.name),
                targetTable = IdName(targetTable.id, targetTable.name),
                targetColumn = IdName(targetColumn.id, targetColumn.name),
            )

        val targetProperty = current.baseProperty

        val singularName =
            if (targetProperty.idProperty) {
                if (sourceEntity != null) {
                    entityNameToPropertyName(sourceEntity.name)
                } else {
                    tableNameToPropertyName(sourceTable.name)
                }
            } else {
                targetProperty.name.removeLastId()
            }

        // 基于基础类型和关联信息制作出关联类型
        val associationProperty = targetProperty.toEntity().copy {
            unload(this, GenProperty::id)
            name = singularName
            if (comment.isBlank() || this.idProperty) {
                comment = sourceEntity?.comment ?: sourceTable.comment.clearTableComment()
            }
            type = sourceEntity?.let { "${it.packagePath}.${it.name}" } ?: tableToEntityFullPath(
                sourceTable,
                context.packagePath
            )
            typeTableId = sourceTable.id
            idProperty = false
            idGenerationAnnotation = null
            keyProperty = false

            val mappedBy =
                (if (sourceColumn.partOfPk) {
                    if (targetEntity != null) {
                        entityNameToPropertyName(targetEntity.name)
                    } else {
                        tableNameToPropertyName(targetTable.name)
                    }
                } else {
                    tableNameToPropertyName(
                        sourceColumn.name
                    ).removeLastId()
                })
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
        }
            .let { PropertyInput(it) }
            .initAssociationBusinessConfig(tableId = targetTable.id)

        current.associationPropertyPairs +=
            if (context.idViewProperty) {
                val idView = createIdViewProperty(
                    singularName,
                    targetProperty,
                    targetColumn,
                    associationProperty,
                    sourceTable,
                    typeMapping
                ).initAssociationBusinessConfig(tableId = targetTable.id)

                AssociationPropertyPairWaitMergeExist(associationProperty, idView, targetProperties)
            } else {
                AssociationPropertyPairWaitMergeExist(associationProperty, null, targetProperties)
            }

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

private fun tableToEntityFullPath(
    typeTable: GenTableConvertView,
    modelBasePackagePath: String,
): String =
    "${tableToEntityPackagePath(typeTable, modelBasePackagePath)}.${tableNameToEntityName(typeTable.name)}"