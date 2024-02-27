package top.potmot.core.entity.convert

import org.babyfish.jimmer.ImmutableObjects.deepClone
import org.babyfish.jimmer.ImmutableObjects.isLoaded
import top.potmot.context.getContextOrGlobal
import top.potmot.core.database.generate.identifier.getIdentifierFilter
import top.potmot.core.database.meta.getAssociations
import top.potmot.core.database.meta.getTypeMeta
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
import top.potmot.model.GenColumn
import top.potmot.model.GenProperty
import top.potmot.model.GenPropertyDraft
import top.potmot.model.copy
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
    basePropertyMap: Map<Long, GenProperty>,
    typeMapping: TypeMapping,
): Map<Long, List<GenProperty>> {
    val context = getContextOrGlobal()

    val identifierFilter = context.dataSourceType.getIdentifierFilter()

    val (
        outAssociations,
        inAssociations,
    ) = table.getAssociations()

    val propertiesMap =
        basePropertyMap.mapValues {
            mutableListOf(it.value)
        }.toMutableMap()

    outAssociations.forEach { outAssociation ->
        if (outAssociation.columnReferences.isEmpty()) {
            throw ConvertEntityException.association(
                "out association [${outAssociation.name}] convert property fail: \n" +
                        "columnReferences is empty"
            )
        }

        val targetTable = outAssociation.targetTable

        val baseColumn = outAssociation.columnReferences[0].sourceColumn

        if (!propertiesMap.containsKey(baseColumn.id)) {
            throw ConvertEntityException.association(
                "out association [${outAssociation.name}] convert property fail: \n" +
                        "sourceColumn [${baseColumn.name}] not found in table [${table.name}]"
            )
        }

        val currentColumnProperties = propertiesMap[baseColumn.id]

        if (currentColumnProperties.isNullOrEmpty()) {
            throw ConvertEntityException.association(
                "out association [${outAssociation.name}] convert property fail: \n" +
                        "baseColumn [${baseColumn.name}] converted property not found"
            )
        }

        val baseProperty = currentColumnProperties[0]

        if (outAssociation.type == MANY_TO_ONE || outAssociation.type == ONE_TO_ONE) {
            currentColumnProperties.clear()
        }

        // 当关联类型为 ONE_TO_MANY 或 MANY_TO_MANY 时，目标属性需要为复数形式
        val targetPlural =
            outAssociation.type == ONE_TO_MANY || outAssociation.type == MANY_TO_MANY

        // 基于基础类型和关联信息制作出关联类型
        val associationProperty = deepClone(baseProperty).copy {
            name = tableNameToPropertyName(targetTable.name)
            comment = targetTable.comment.clearTableComment()
            type = tableNameToClassName(targetTable.name)
            typeTableId = targetTable.id
            idProperty = false
            idGenerationAnnotation = null

            when (outAssociation.type) {
                ONE_TO_ONE, MANY_TO_ONE -> {
                    setAssociation(
                        AssociationAnnotationMeta(
                            outAssociation.type,
                            joinColumns = outAssociation.toJoinColumns(identifierFilter)
                        )
                    )
                    outAssociation.dissociateAction?.let {
                        this.dissociateAnnotation = "@OnDissociate(DissociateAction.${it.name})"
                    }

                    // 当外键为伪时，需要将类型设置为可空
                    if (outAssociation.fake) {
                        typeNotNull = false
                    }
                }

                MANY_TO_MANY -> {
                    keyProperty = false
                    setAssociation(
                        AssociationAnnotationMeta(
                            outAssociation.type,
                            joinTable = outAssociation.toJoinTable(identifierFilter)
                        )
                    )
                }

                ONE_TO_MANY -> {
                    val mappedBy = tableNameToPropertyName(table.name)

                    keyProperty = false
                    setAssociation(
                        AssociationAnnotationMeta(
                            outAssociation.type,
                            mappedBy = mappedBy
                        )
                    )
                }
            }

            // 当目标为复数时，该属性也为复数
            if (targetPlural) toPlural()
        }

        currentColumnProperties += associationProperty

        if (context.idViewProperty) {
            currentColumnProperties += createIdViewProperty(baseProperty, baseColumn, associationProperty, typeMapping)
        }

        propertiesMap[baseColumn.id] = currentColumnProperties
    }

    inAssociations.forEach { inAssociation ->
        if (inAssociation.columnReferences.isEmpty()) {
            throw ConvertEntityException.association(
                "in association [${inAssociation.name}] convert property fail: \n" +
                        "columnReferences is empty"
            )
        }

        val sourceTable = inAssociation.sourceTable

        val baseColumn = inAssociation.columnReferences[0].targetColumn

        val currentColumnProperties = propertiesMap[baseColumn.id]

        if (currentColumnProperties.isNullOrEmpty()) {
            throw ConvertEntityException.association(
                "in association [${inAssociation.name}] convert property fail: \n" +
                        "baseColumn [${baseColumn.name}] converted property not found"
            )
        }

        val baseProperty = currentColumnProperties[0]

        if (inAssociation.type == ONE_TO_MANY) {
            currentColumnProperties.clear()
        }

        // 当关联类型为 MANY_TO_ONE 或 MANY_TO_MANY 时，来源属性需要为复数形式
        val sourcePlural = inAssociation.type == MANY_TO_ONE || inAssociation.type == MANY_TO_MANY

        // 当关联类型为 ONE_TO_MANY 或 MANY_TO_MANY 时，目标属性需要为复数形式
        val targetPlural = inAssociation.type == ONE_TO_MANY || inAssociation.type == MANY_TO_MANY

        // 基于基础类型和关联信息制作出关联类型
        val associationProperty = deepClone(baseProperty).copy {
            name = tableNameToPropertyName(sourceTable.name)
            comment = sourceTable.comment.clearTableComment()
            type = tableNameToClassName(sourceTable.name)
            typeTableId = sourceTable.id
            idProperty = false
            idGenerationAnnotation = null

            val mappedBy = tableNameToPropertyName(table.name).let {
                if (targetPlural) it.toPlural() else it
            }

            when (inAssociation.type) {
                ONE_TO_ONE, MANY_TO_ONE, MANY_TO_MANY -> {
                    keyProperty = false

                    setAssociation(
                        AssociationAnnotationMeta(
                            inAssociation.type.reverse(),
                            mappedBy,
                        )
                    )

                    // 当关联为一对一时，被动方需要将类型设置为可空
                    if (inAssociation.type == ONE_TO_ONE) {
                        typeNotNull = false
                    }
                    // 当外键为伪时，需要将类型设置为可空
                    if (inAssociation.fake && inAssociation.type == MANY_TO_ONE) {
                        typeNotNull = false
                    }
                }

                ONE_TO_MANY -> {
                    setAssociation(
                        AssociationAnnotationMeta(
                            MANY_TO_ONE,
                            joinColumns = inAssociation.toJoinColumns(identifierFilter).map { it.reverse() }
                        )
                    )
                    inAssociation.dissociateAction?.let {
                        this.dissociateAnnotation = "@OnDissociate(DissociateAction.${it})"
                    }
                    // 当外键为伪时，需要将类型设置为可空
                    if (inAssociation.fake) {
                        typeNotNull = false
                    }
                }
            }

            if (sourcePlural) toPlural()
        }

        currentColumnProperties += associationProperty

        if (context.idViewProperty) {
            currentColumnProperties += createIdViewProperty(baseProperty, baseColumn, associationProperty, typeMapping)
        }

        propertiesMap[baseColumn.id] = currentColumnProperties
    }

    return propertiesMap
}

private fun GenPropertyDraft.toPlural() {
    if (isLoaded(this, "name")) {
        this.name = this.name.toPlural()
    }
    this.listType = true
    this.typeNotNull = true
    this.keyProperty = false
    this.logicalDelete = false
}

/**
 * 从基础属性和关联属性生成 IdView
 *
 * @param baseProperty 基础属性
 * @param baseColumn 基础列
 * @param associationProperty 关联属性
 */
private fun createIdViewProperty(
    baseProperty: GenProperty,
    baseColumn: GenColumn,
    associationProperty: GenProperty,
    typeMapping: TypeMapping,
) =
    deepClone(baseProperty).copy {
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

        this.type = baseColumn.getTypeMeta()
            .let {
                // 同步关联属性的可空性
                it.typeNotNull = associationProperty.typeNotNull
                // 当为列表属性时，java 为允许集合泛型使用，此时映射时必须调整为可空模式
                if (this.listType) {
                    it.typeNotNull = false
                }
                typeMapping(it)
            }

        associationProperty.comment.takeIf { it.isNotBlank() }?.let {
            this.comment = "$it ID 视图"
        }
        this.associationType = associationProperty.associationType
        this.idView = true
        this.idViewAnnotation = "@IdView(\"${associationProperty.name}\")"
        this.keyProperty = false
    }


private fun GenPropertyDraft.setAssociation(
    meta: AssociationAnnotationMeta
) {
    val context = getContextOrGlobal()
    val associationAnnotationBuilder = context.language.getAssociationAnnotationBuilder()

    this.associationType = meta.type
    this.associationAnnotation = associationAnnotationBuilder.build(meta)
}
