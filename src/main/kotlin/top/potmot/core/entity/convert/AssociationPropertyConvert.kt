package top.potmot.core.entity.convert

import org.babyfish.jimmer.ImmutableObjects.deepClone
import org.babyfish.jimmer.ImmutableObjects.isLoaded
import org.babyfish.jimmer.sql.ForeignKeyType
import top.potmot.config.GenConfig
import top.potmot.core.database.meta.getPropertyType
import top.potmot.core.database.meta.getTypeMeta
import top.potmot.core.database.meta.createMappingTableName
import top.potmot.core.database.meta.getAssociations
import top.potmot.enumeration.AssociationType
import top.potmot.enumeration.AssociationType.MANY_TO_MANY
import top.potmot.enumeration.AssociationType.MANY_TO_ONE
import top.potmot.enumeration.AssociationType.ONE_TO_MANY
import top.potmot.enumeration.AssociationType.ONE_TO_ONE
import top.potmot.error.GenerateEntityException
import top.potmot.model.GenProperty
import top.potmot.model.GenPropertyDraft
import top.potmot.model.copy
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.utils.string.toPlural
import top.potmot.utils.string.toSingular

/**
 * 根据基础属性、表关联处理出关联属性
 *
 * TODO 映射复合属性与多列外键
 */
fun producePropertyWithAssociationAndUniqueIndexes(
    table: GenTableAssociationsView,
    columnPropertyPairs: List<Pair<GenTableAssociationsView.TargetOf_columns, GenProperty>>,
    typeMapping: TypeMapping = { it.getPropertyType() },
): List<GenProperty> {
    val (
        outAssociations,
        inAssociations,
    ) = table.getAssociations()

    val propertiesMap =
        columnPropertyPairs.associate { (column, property) ->
            column.id to mutableListOf(property)
        }.toMutableMap()

    outAssociations.forEach { outAssociation ->
        if (outAssociation.columnReferences.isEmpty()) {
            throw GenerateEntityException.association("out association [${outAssociation.name}] generate property fail: \ncolumnReferences is empty")
        }

        val sourceTable = table.toEntity()
        val targetTable = outAssociation.targetTable

        val sourceColumns = outAssociation.columnReferences.map { it.sourceColumn }
        val sourceColumn = sourceColumns[0]
        val targetColumns = outAssociation.columnReferences.map { it.targetColumn }
        val targetColumn = targetColumns[0]

        sourceColumns.forEach {
            if (!propertiesMap.containsKey(it.id)) {
                throw GenerateEntityException.association("out association [${outAssociation.name}] generate property fail: \nsourceColumn [${it.name}] not found in table [${table.name}]")
            }
        }

        val currentColumnPropertiesList = propertiesMap[sourceColumn.id]!!

        if (currentColumnPropertiesList.isEmpty()) {
            throw GenerateEntityException.association("out association [${outAssociation.name}] generate property fail: \nsourceColumn [${sourceColumn.name}]'s property not found")
        }
        val baseProperty = currentColumnPropertiesList[0]

        if (outAssociation.associationType == MANY_TO_ONE || outAssociation.associationType == ONE_TO_ONE) {
            currentColumnPropertiesList.clear()
        }

        // 生成结果为复数形式
        val targetPlural =
            outAssociation.associationType == ONE_TO_MANY || outAssociation.associationType == MANY_TO_MANY

        val associationProperty = deepClone(baseProperty).copy {
            name = tableNameToPropertyName(targetTable.name)
            comment = targetTable.comment.clearTableComment()
            type = tableNameToClassName(targetTable.name)
            typeTableId = targetTable.id
            idProperty = false
            idGenerationType = null

            when (outAssociation.associationType) {
                ONE_TO_ONE, MANY_TO_ONE -> {
                    setAssociation(
                        outAssociation.associationType,
                        joinColumns = outAssociation.columnReferences.map {
                            JoinColumnProps(
                                joinColumnName = it.sourceColumn.name,
                                referencedColumnName = it.targetColumn.name,
                                foreignKeyType =
                                if (GenConfig.realFk) {
                                    if (outAssociation.fake) {
                                        ForeignKeyType.FAKE
                                    } else null
                                } else {
                                    if (!outAssociation.fake) {
                                        ForeignKeyType.REAL
                                    } else null
                                }
                            )
                        }
                    )
                    outAssociation.dissociateAction?.let {
                        this.dissociateAnnotation = "@OnDissociate(DissociateAction.${it})"
                    }
                }

                MANY_TO_MANY -> {
                    val sourceTableName = sourceTable.name.clearTableName()
                    val targetTableName = targetTable.name.clearTableName()

                    val sourceColumnName = sourceColumn.name.clearColumnName()
                    val targetColumnName = targetColumn.name.clearColumnName()

                    keyProperty = false
                    setAssociation(
                        outAssociation.associationType,
                        joinTable = JoinTableProps(
                            joinTableName = createMappingTableName(sourceTableName, targetTableName),
                            joinColumnName = "${sourceTableName}_${sourceColumnName}",
                            inverseJoinColumnName = "${targetTableName}_${targetColumnName}",
                        )
                    )
                }

                ONE_TO_MANY -> {
                    keyProperty = false
                    setAssociation(
                        outAssociation.associationType,
                        mappedBy = tableNameToPropertyName(targetColumn.name)
                    )
                }
            }

            // 当目标为复数时，该属性也为复数
            if (targetPlural) toPlural()
        }

        currentColumnPropertiesList += associationProperty

        if (GenConfig.idViewProperty) {
            val idViewTypeMapping = { typeNotNull: Boolean ->
                sourceColumn.getTypeMeta().apply { this.typeNotNull = typeNotNull }.let {
                    typeMapping(it)
                }
            }
            currentColumnPropertiesList += createIdViewProperty(baseProperty, associationProperty, idViewTypeMapping)
        }

        propertiesMap[sourceColumn.id] = currentColumnPropertiesList
    }

    inAssociations.forEach { inAssociation ->
        if (inAssociation.columnReferences.isEmpty()) {
            throw GenerateEntityException.association("out association [${inAssociation.name}] generate property fail: \ncolumnReferences is empty")
        }

        val sourceTable = inAssociation.sourceTable
        val targetTable = table.toEntity()

        val sourceColumns = inAssociation.columnReferences.map { it.sourceColumn }
        val sourceColumn = sourceColumns[0]
        val targetColumns = inAssociation.columnReferences.map { it.targetColumn }
        val targetColumn = targetColumns[0]

        if (!propertiesMap.containsKey(targetColumn.id)) {
            throw GenerateEntityException.association("in association [${inAssociation.name}] generate property fail: \ntargetColumn [${targetColumn.name}] not found in table [${table.name}]")
        }
        val currentColumnPropertiesList = propertiesMap[targetColumn.id]!!

        if (currentColumnPropertiesList.isEmpty()) {
            throw GenerateEntityException.association("in association [${inAssociation.name}] generate property fail: \ntargetColumn [${targetColumn.name}]'s property not found")
        }
        val baseProperty = currentColumnPropertiesList[0]

        if (inAssociation.associationType == ONE_TO_MANY) {
            currentColumnPropertiesList.clear()
        }

        val sourcePlural = inAssociation.associationType == MANY_TO_ONE || inAssociation.associationType == MANY_TO_MANY

        val targetPlural = inAssociation.associationType == ONE_TO_MANY || inAssociation.associationType == MANY_TO_MANY

        /**
         * 这个属性为向内关联的 source 在 target 类中的映射
         */
        val associationProperty = deepClone(baseProperty).copy {
            name = tableNameToPropertyName(sourceTable.name)
            comment = sourceTable.comment.clearTableComment()
            type = tableNameToClassName(sourceTable.name)
            typeTableId = sourceTable.id
            idProperty = false
            idGenerationType = null

            val mappedBy = tableNameToPropertyName(targetTable.name).let {
                if (targetPlural) it.toPlural() else it
            }

            when (inAssociation.associationType) {
                ONE_TO_ONE, MANY_TO_ONE, MANY_TO_MANY -> {
                    keyProperty = false

                    setAssociation(
                        inAssociation.associationType.reverse(),
                        mappedBy,
                    )
                }

                ONE_TO_MANY -> {
                    setAssociation(
                        MANY_TO_ONE,
                        joinColumns = inAssociation.columnReferences.map {
                            JoinColumnProps(
                                joinColumnName = it.sourceColumn.name,
                                referencedColumnName = it.targetColumn.name,
                                foreignKeyType =
                                if (GenConfig.realFk) {
                                    if (inAssociation.fake) {
                                        ForeignKeyType.FAKE
                                    } else null
                                } else {
                                    if (!inAssociation.fake) {
                                        ForeignKeyType.REAL
                                    } else null
                                }
                            )
                        }
                    )
                    inAssociation.dissociateAction?.let {
                        this.dissociateAnnotation = "@OnDissociate(DissociateAction.${it})"
                    }
                }
            }

            if (sourcePlural) toPlural()
        }

        currentColumnPropertiesList += associationProperty

        if (GenConfig.idViewProperty) {
            val idViewTypeMapping = { typeNotNull: Boolean ->
                targetColumn.getTypeMeta().apply { this.typeNotNull = typeNotNull }.let {
                    typeMapping(it)
                }
            }
            currentColumnPropertiesList += createIdViewProperty(baseProperty, associationProperty, idViewTypeMapping)
        }

        propertiesMap[targetColumn.id] = currentColumnPropertiesList
    }

    return propertiesMap.flatMap { it.value }
}

private data class JoinColumnProps(
    val joinColumnName: String,
    val referencedColumnName: String? = null,
    val foreignKeyType: ForeignKeyType? = null
) {
    fun toAnnotation() =
        buildString {
            val onlyName = referencedColumnName == null && foreignKeyType == null

            if (onlyName) {
                append("@JoinColumn(name = \"${joinColumnName.changeCase()}\")")
            } else {
                appendLine("@JoinColumn(")
                appendLine("    name = \"${joinColumnName.changeCase()}\",")
                if (referencedColumnName != null) {
                    appendLine("    referencedColumnName = \"${referencedColumnName.changeCase()}\"")
                }
                if (foreignKeyType != null) {
                    appendLine("    foreignKeyType = ForeignKeyType.${foreignKeyType.name}")
                }
                appendLine(")")
            }
        }
}

private data class JoinTableProps(
    val joinTableName: String,
    val joinColumnName: String,
    val inverseJoinColumnName: String,
) {
    fun toAnnotation() =
        buildString {
            appendLine("@JoinTable(")
            appendLine("    name = \"${joinTableName.changeCase()}\",")
            appendLine("    joinColumnName = \"${joinColumnName.changeCase()}\",")
            appendLine("    inverseJoinColumnName = \"${inverseJoinColumnName.changeCase()}\"")
            appendLine(")")
        }
}

private fun GenPropertyDraft.setAssociation(
    associationType: AssociationType,
    mappedBy: String? = null,
    joinColumns: List<JoinColumnProps> = emptyList(),
    joinTable: JoinTableProps? = null,
) {
    this.associationType = associationType

    this.associationAnnotation = "@" + associationType.toAnnotation().simpleName
    mappedBy?.takeIf { it.isNotBlank() }?.let {
        this.associationAnnotation += "(mappedBy = \"${it}\")"
        if (associationType == ONE_TO_ONE) {
            this.typeNotNull = false
        }
    }

    if (GenConfig.joinColumnAnnotation) {
        joinColumns.forEach {
            this.associationAnnotation += "\n${it.toAnnotation()}"
        }
    }

    if (GenConfig.joinTableAnnotation) {
        joinTable?.let {
            this.associationAnnotation += "\n${it.toAnnotation()}"
        }
    }
}

private fun GenPropertyDraft.toPlural() {
    if (isLoaded(this, "name")) {
        this.name = this.name.toPlural()
    }
    this.listType = true
    this.typeNotNull = true
    this.keyProperty = false
}

typealias IdViewTypeMapping = (typeNotNull: Boolean) -> String

/**
 * 从基础属性和关联属性生成 IdView
 *
 * @param baseProperty 基础属性
 * @param associationProperty 关联属性
 * @param typeMapping 处理因为 typeNotNull 不同而生成的不同基本类型
 */
fun createIdViewProperty(
    baseProperty: GenProperty,
    associationProperty: GenProperty,
    typeMapping: IdViewTypeMapping
): GenProperty {
    return deepClone(baseProperty).copy {
        idProperty = false
        idGenerationType = null

        if (associationProperty.typeNotNull != baseProperty.typeNotNull) {
            this.typeNotNull = associationProperty.typeNotNull
        }

        if (associationProperty.listType) {
            this.name = associationProperty.name.toSingular() + "Ids"
            this.listType = true
            this.typeNotNull = true
        } else {
            this.name = associationProperty.name + "Id"
        }

        this.type = typeMapping(this.typeNotNull)

        associationProperty.comment.takeIf { it.isNotBlank() }?.let {
            this.comment = "$it ID 视图"
        }
        this.associationType = associationProperty.associationType
        this.idView = true
        this.idViewAnnotation = "@IdView(\"${associationProperty.name}\")"
        this.keyProperty = false
    }
}

private fun String.changeCase(): String =
    this.let { if (GenConfig.lowerCaseName) lowercase() else uppercase() }
