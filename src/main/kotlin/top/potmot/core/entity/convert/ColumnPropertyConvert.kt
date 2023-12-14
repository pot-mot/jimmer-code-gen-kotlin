package top.potmot.core.entity.convert

import org.babyfish.jimmer.ImmutableObjects
import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.sql.GenerationType
import top.potmot.config.GenConfig
import top.potmot.utils.immutable.copyProperties
import top.potmot.enumeration.AssociationType
import top.potmot.model.GenProperty
import top.potmot.model.GenPropertyDraft
import top.potmot.model.by
import top.potmot.enumeration.AssociationType.*
import top.potmot.model.dto.GenTableAssociationsView

/**
 * 列到属性转换
 * 具备关联时将生成关联字段 和 IdView
 *
 * warning: 为保证保存时关联成立，请保证对应 column 具有 id
 */
fun columnToProperties(
    column: GenTableAssociationsView.TargetOf_columns,
    typeMapping: (column: ColumnTypeData) -> String = {it.getPropertyType()},
): List<GenProperty> {
    val properties = mutableListOf<GenProperty>()

    val baseProperty = column.toBaseProperty(typeMapping)

    val defaultProperty =
        new(GenProperty::class).by {
            copyProperties(baseProperty, this)

            if (column.partOfPk) {
                idProperty = true
                typeNotNull = true
                keyProperty = false
                logicalDelete = false
                idView = false
                if (column.autoIncrement) {
                    idGenerationType = GenerationType.IDENTITY
                }
            } else if (column.partOfUniqueIdx) {
                keyProperty = true
            }
        }

    var includeDefaultProperty = true

    val idViewTypeMapping = { typeNotNull: Boolean ->
        column.getTypeData().apply { this.typeNotNull = typeNotNull }.let {
            typeMapping(it)
        }
    }

    column.outAssociations.forEach { outAssociation ->
        if (outAssociation.associationType == MANY_TO_ONE || outAssociation.associationType == ONE_TO_ONE) {
            includeDefaultProperty = false
        }

        val sourceColumn = column

        val targetColumn = outAssociation.targetColumn

        val targetPlural = outAssociation.associationType == ONE_TO_MANY || outAssociation.associationType == MANY_TO_MANY

        /**
         * 这个属性为对外关联的 target 在 source 类中的映射
         */
        val associationProperty = new(GenProperty::class).by {
            copyProperties(baseProperty, this)

            name = tableNameToPropertyName(targetColumn.table.name)
            comment = targetColumn.table.comment.clearTableComment()
            type = tableNameToClassName(targetColumn.table.name)
            typeTableId = targetColumn.table.id

            when (outAssociation.associationType) {
                ONE_TO_ONE, MANY_TO_ONE -> {
                    setAssociation(
                        outAssociation.associationType,
                        joinColumn = JoinColumnProps(joinColumnName = sourceColumn.name)
                    )
                    outAssociation.dissociateAction?.let {
                        this.dissociateAnnotation = "@OnDissociate(DissociateAction.${it})"
                    }
                }

                MANY_TO_MANY -> {
                    val sourceTableName = sourceColumn.table.name.clearTableName()
                    val targetTableName =  targetColumn.table.name.clearTableName()
                    val sourceColumnName = sourceColumn.name.clearColumnName()
                    val targetColumnName = targetColumn.name.clearColumnName()

                    keyProperty = false
                    setAssociation(
                        outAssociation.associationType,
                        joinTable = JoinTableProps(
                            joinTableName = mappingTableName(sourceTableName, targetTableName),
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

        properties += associationProperty

        if (GenConfig.idViewProperty) {
            properties += createIdViewProperty(baseProperty, associationProperty, idViewTypeMapping)
        }
    }

    column.inAssociations.forEach { inAssociation ->
        if (inAssociation.associationType == ONE_TO_MANY) {
            includeDefaultProperty = false
        }

        val targetColumn = column

        val sourceColumn = inAssociation.sourceColumn

        val sourcePlural = inAssociation.associationType == MANY_TO_ONE || inAssociation.associationType == MANY_TO_MANY

        val targetPlural = inAssociation.associationType == ONE_TO_MANY || inAssociation.associationType == MANY_TO_MANY

        /**
         * 这个属性为向内关联的 source 在 target 类中的映射
         */
        val associationProperty = new(GenProperty::class).by {
            copyProperties(baseProperty, this)

            name = tableNameToPropertyName(sourceColumn.table.name)
            comment = sourceColumn.table.comment.clearTableComment()
            type = tableNameToClassName(sourceColumn.table.name)
            typeTableId = sourceColumn.table.id

            val mappedBy = tableNameToPropertyName(targetColumn.table.name).let {
                if (targetPlural) it.toPlural() else it
            }

            when(inAssociation.associationType) {
                ONE_TO_ONE, MANY_TO_ONE, MANY_TO_MANY -> {
                    keyProperty = false

                    setAssociation(
                        inAssociation.associationType.reverse(),
                        mappedBy,
                    )
                }

                ONE_TO_MANY  -> {
                    setAssociation(
                        MANY_TO_ONE,
                        joinColumn = JoinColumnProps(joinColumnName = sourceColumn.name)
                    )
                    inAssociation.dissociateAction?.let {
                        this.dissociateAnnotation = "@OnDissociate(DissociateAction.${it})"
                    }
                }
            }

            if (sourcePlural) toPlural()
        }

        properties += associationProperty

        if(GenConfig.idViewProperty) {
            properties += createIdViewProperty(baseProperty, associationProperty, idViewTypeMapping)
        }
    }

    if (includeDefaultProperty) {
        properties.add(0, defaultProperty)
    }

    return properties
}

fun GenTableAssociationsView.TargetOf_columns.getTypeData() =
    ColumnTypeData(type, typeCode, displaySize, numericPrecision, typeNotNull)

/**
 * 转换为基础属性
 */
fun GenTableAssociationsView.TargetOf_columns.toBaseProperty(
    typeMapping: (column: ColumnTypeData) -> String,
): GenProperty {
    val column = this

    return new(GenProperty::class).by {
        this.columnId = column.id
        this.name = columnNameToPropertyName(column.name)
        this.comment = column.comment.clearColumnComment()
        this.type = typeMapping(column.getTypeData())
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

private fun AssociationType.reverse() =
    when (this) {
        MANY_TO_ONE -> ONE_TO_MANY
        ONE_TO_MANY -> MANY_TO_ONE
        else -> this
    }

private data class JoinColumnProps(
    val joinColumnName: String,
) {
    fun toAnnotation() = "@JoinColumn(name = \"$joinColumnName\")"
}

private data class JoinTableProps(
    val joinTableName: String,
    val joinColumnName: String,
    val inverseJoinColumnName: String,
) {
    fun toAnnotation() = """@JoinTable(
    name = "$joinTableName",
    joinColumnName = "$joinColumnName",
    inverseJoinColumnName = "$inverseJoinColumnName"
)"""
}

private fun GenPropertyDraft.setAssociation(
    associationType: AssociationType,
    mappedBy: String? = null,
    joinColumn: JoinColumnProps? = null,
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
    joinColumn?.takeIf { GenConfig.joinColumnAnnotation }?.let {
        this.associationAnnotation += "\n${it.toAnnotation()}"
    }
    joinTable?.takeIf { GenConfig.joinTableAnnotation }?.let {
        this.associationAnnotation += "\n${it.toAnnotation()}"
    }
}

private fun mappingTableName(sourceTableName: String, targetTableName: String): String =
    "${sourceTableName.clearTableName()}_${targetTableName.clearTableName()}_mapping"

private fun GenPropertyDraft.toPlural() {
    if (ImmutableObjects.isLoaded(this, "name")) {
        this.name = this.name.toPlural()
    }
    this.listType = true
    this.typeNotNull = true
    this.keyProperty = false
}

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
    typeMapping: (typeNotNull: Boolean) -> String
): GenProperty {
    return new(GenProperty::class).by {
        copyProperties(baseProperty, this)

        if (associationProperty.listType) {
            this.name = associationProperty.name.toSingular() + "Ids"
            this.listType = true
        } else {
            this.name = associationProperty.name + "Id"
        }

        if (associationProperty.typeNotNull != baseProperty.typeNotNull) {
            this.typeNotNull = associationProperty.typeNotNull
            this.type = typeMapping(this.typeNotNull)
        }

        associationProperty.comment.takeIf { it.isNotBlank() }?.let {
            this.comment = "$it ID 视图"
        }
        this.associationType = associationProperty.associationType
        this.idView = true
        this.idViewAnnotation = "@IdView(\"${associationProperty.name}\")"
        this.keyProperty = false
    }
}
