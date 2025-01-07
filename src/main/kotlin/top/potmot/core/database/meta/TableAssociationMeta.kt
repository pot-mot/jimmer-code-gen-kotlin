package top.potmot.core.database.meta

import top.potmot.entity.dto.GenAssociationSimpleView
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.entity.dto.IdName
import top.potmot.entity.dto.IdNullableName
import top.potmot.error.GenerateException

val GenTableGenerateView.columnMap
    get() = columns.associateBy { it.id }

@Throws(GenerateException::class)
fun GenTableGenerateView.outAssociationMetas() = outAssociations.map { outAssociation ->
    val sourceColumns =
        outAssociation.columnReferences.map { columnReference ->
            columnMap[columnReference.sourceColumn.id] ?: throw GenerateException.outAssociationCannotFountSourceColumn(
                "OutAssociation [${outAssociation.name}] create fail: " +
                        " sourceColumn [${columnReference.sourceColumn.id}] not found in table [$name]",
                association = IdName(outAssociation.id, outAssociation.name),
                sourceTable = IdName(id, name),
                sourceColumn = IdNullableName(columnReference.sourceColumn.id, null),
                targetTable = IdName(outAssociation.targetTable.id, outAssociation.targetTable.name),
                targetColumn = IdName(columnReference.targetColumn.id, columnReference.targetColumn.name)
            )
        }

    val targetColumns =
        outAssociation.columnReferences.map { it.targetColumn }

    OutAssociationMeta(
        association = GenAssociationSimpleView(outAssociation.toEntity()),
        sourceTable = this,
        sourceColumns = sourceColumns,
        targetTable = outAssociation.targetTable,
        targetColumns = targetColumns,
    )
}
