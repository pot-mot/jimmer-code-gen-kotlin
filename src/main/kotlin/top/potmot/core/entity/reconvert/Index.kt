package top.potmot.core.entity.reconvert

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

fun reconvertEntitiesToTables(
    entities: Iterable<GenEntityReconvertView>,
): ReconvertResult {
    val tables = mutableListOf<GenTableModelInput>()
    val associations = mutableListOf<GenAssociationModelInput>()

    val entityNameTableNameMap = entities.associate {
        it.name to (it.table?.name ?: entityNameToTableName(it.name))
    }

    for (entity in entities) {
        val index = mutableListOf<GenTableModelInput.TargetOf_indexes>()

        tables += GenTableModelInput(
            name = entityNameTableNameMap[entity.name] ?: entityNameToTableName(entity.name),
            comment = entity.comment,
            remark = entity.remark,
            type = if (entity.table != null && entity.table.type == TableType.SUPER_TABLE ) TableType.SUPER_TABLE else TableType.TABLE,
            superTables = entity.superEntities.map {
                GenTableModelInput.TargetOf_superTables(
                    name = entityNameTableNameMap[it.name] ?: entityNameToTableName(it.name),
                )
            },
            columns = entity.properties.map {
                GenTableModelInput.TargetOf_columns(
                    name = it.column?.name ?: propertyNameToColumnName(it.name),
                    typeCode = TODO(),
                    overwriteByRaw = false,
                    rawType = TODO(),
                    typeNotNull = it.typeNotNull,
                    dataSize = it.column?.dataSize ?: 0,
                    numericPrecision = it.column?.numericPrecision ?: 0,
                    comment = it.comment,

                    partOfPk = it.idProperty,
                    autoIncrement = TODO(),

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
            indexes = emptyList(),

            )
    }

    return ReconvertResult(tables, associations)
}
