package top.potmot.core.entity.reconvert

import top.potmot.core.entity.reconvert.type.toColumnType
import top.potmot.entity.dto.GenAssociationModelInput
import top.potmot.entity.dto.GenEntityReconvertView
import top.potmot.entity.dto.GenTableModelInput
import top.potmot.enumeration.TableType
import top.potmot.utils.string.entityNameToTableName
import top.potmot.utils.string.propertyNameToColumnName

data class ReconvertResult(
    val tableEntities: List<GenTableModelInput>,
    val associations: List<GenAssociationModelInput>,
)

data class ReconvertPropertyData(
    val property: GenEntityReconvertView.TargetOf_properties,
    val columnName: String,
)

data class ReconvertEntityData(
    val entity: GenEntityReconvertView,
    val tableName: String,
    val columnProperties: List<ReconvertPropertyData>,
)

private val GenEntityReconvertView.fullName
    get() = "${packagePath}.${name}"

private val GenEntityReconvertView.TargetOf_superEntities.fullName
    get() = "${packagePath}.${name}"

fun reconvertEntitiesToTables(
    entities: Iterable<GenEntityReconvertView>,
): ReconvertResult {
    val tables = mutableListOf<GenTableModelInput>()
    val associations = mutableListOf<GenAssociationModelInput>()

    val entityDataList = entities.map {
        ReconvertEntityData(
            entity = it,
            tableName = (it.table?.name ?: entityNameToTableName(it.name)),
            /**
             * 将被转换为列的属性，不得为 idView、mappedBy、对多关联属性、Formula、Transient、ManyToManyView
             */
            columnProperties = it.properties.filter { property ->
                val otherAnnotationStr = property.otherAnnotation.toString()
                property.body == null
                        && !property.idView
                        && property.mappedBy == null
                        && !(property.associationType != null && property.listType)
                        && !otherAnnotationStr.contains("@Formula")
                        && !otherAnnotationStr.contains("@Transient")
                        && !otherAnnotationStr.contains("@ManyToManyView")
            }.map { property ->
                val baseColumnName = (property.column?.name ?: propertyNameToColumnName(property.name))
                ReconvertPropertyData(
                    property = property,
                    columnName = if (property.associationType != null) {
                        baseColumnName + "_id"
                    } else {
                        baseColumnName
                    },
                )
            }
        )
    }
    val entityFullNameToDataMap = entityDataList.associateBy { it.entity.fullName }

    for ((entity, tableName, columnProperties) in entityDataList) {
        val isSuper = entity.table != null && entity.table.type == TableType.SUPER_TABLE

        val keyPropertyGroupNameToColumnNameMap = mutableMapOf<String?, MutableList<String>>()
        val indexes = mutableListOf<GenTableModelInput.TargetOf_indexes>()

        for ((property, columnName) in columnProperties) {
            if (property.keyProperty) {
                if (property.keyGroup != null) {
                    for (group in property.keyGroups) {
                        val current = keyPropertyGroupNameToColumnNameMap[group]
                        if (current == null) {
                            keyPropertyGroupNameToColumnNameMap[group] = mutableListOf(columnName)
                        } else {
                            current += columnName
                        }
                    }
                } else {
                    val current = keyPropertyGroupNameToColumnNameMap[null]
                    if (current == null) {
                        keyPropertyGroupNameToColumnNameMap[null] = mutableListOf(columnName)
                    } else {
                        current += columnName
                    }
                }
            }
        }

        for ((group, propertyNames) in keyPropertyGroupNameToColumnNameMap) {
            val baseName = "key_of_${if (isSuper) "{}" else tableName}"
            indexes += GenTableModelInput.TargetOf_indexes(
                name = group?.let { "${baseName}_$it" } ?: baseName,
                remark = "",
                uniqueIndex = true,
                columns = propertyNames.map {
                    GenTableModelInput.TargetOf_indexes.TargetOf_columns(
                        name = it
                    )
                },
            )
        }

        tables += GenTableModelInput(
            name = tableName,
            comment = entity.comment,
            remark = entity.remark,
            type = if (isSuper) TableType.SUPER_TABLE else TableType.TABLE,
            superTables = entity.superEntities.map {
                GenTableModelInput.TargetOf_superTables(
                    name = entityFullNameToDataMap[it.fullName]?.tableName ?: entityNameToTableName(it.name),
                )
            },
            columns = columnProperties.map { (it, columnName) ->
                val columnType = toColumnType(it)

                GenTableModelInput.TargetOf_columns(
                    name = columnName,
                    typeCode = columnType.vendorTypeNumber,
                    overwriteByRaw = false,
                    rawType = columnType.name,
                    typeNotNull = it.typeNotNull,
                    dataSize = it.column?.dataSize ?: 0,
                    numericPrecision = it.column?.numericPrecision ?: 0,
                    comment = it.comment,

                    partOfPk = it.idProperty,
                    autoIncrement = it.generatedIdAnnotation?.toString()?.let { str ->
                        str.contains("GeneratedValue") &&
                                (str.contains("AUTO") || str.contains("IDENTITY") || str.contains("SEQUENCE"))
                    } ?: false,

                    businessKey = it.keyProperty,
                    keyGroup = it.keyGroup,

                    logicalDelete = it.logicalDelete,

                    orderKey = it.orderKey,
                    remark = it.remark,

                    enum = it.enum?.let { enum ->
                        GenTableModelInput.TargetOf_columns.TargetOf_enum(
                            name = enum.name
                        )
                    }
                )
            },
            indexes = indexes
        )

        for ((property, columnName) in columnProperties) {
            if (property.associationType != null) {
                TODO()
            }
        }
    }

    return ReconvertResult(tables, associations)
}
