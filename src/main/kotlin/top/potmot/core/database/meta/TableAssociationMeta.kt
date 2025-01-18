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

@Throws(GenerateException::class)
fun GenTableGenerateView.inAssociationMetas() = inAssociations.map { inAssociation ->
    val targetColumns =
        inAssociation.columnReferences.map { columnReference ->
            columnMap[columnReference.targetColumn.id] ?: throw GenerateException.inAssociationCannotFountTargetColumn(
                "InAssociation [${inAssociation.name}] create fail: " +
                        " targetColumn [${columnReference.targetColumn.id}] not found in table [$name]",
                association = IdName(inAssociation.id, inAssociation.name),
                sourceTable = IdName(inAssociation.sourceTable.id, inAssociation.sourceTable.name),
                sourceColumn = IdName(columnReference.sourceColumn.id, columnReference.sourceColumn.name),
                targetTable = IdName(id, name),
                targetColumn = IdNullableName(columnReference.sourceColumn.id, null)
            )
        }

    val sourceColumns =
        inAssociation.columnReferences.map { it.sourceColumn }

    InAssociationMeta(
        association = GenAssociationSimpleView(inAssociation.toEntity()),
        sourceTable = inAssociation.sourceTable,
        sourceColumns = sourceColumns,
        targetTable = this,
        targetColumns = targetColumns,
    )
}