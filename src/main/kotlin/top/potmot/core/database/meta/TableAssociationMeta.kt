package top.potmot.core.database.meta

import top.potmot.error.ConvertEntityException
import top.potmot.entity.dto.GenAssociationSimpleView
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.GenTableGenerateView

data class TableAssociationMeta<T, C>(
    val outAssociations: List<OutAssociationMeta<T, C>>,
    val inAssociations: List<InAssociationMeta<T, C>>,
)

val GenTableConvertView.columnMap
    get() = columns.associateBy { it.id }

val GenTableGenerateView.columnMap
    get() = columns.associateBy { it.id }

@Throws(ConvertEntityException::class)
fun GenTableGenerateView.outAssociations() = outAssociations.map { outAssociation ->
    val sourceColumns =
        outAssociation.columnReferences.map { columnReference ->
            columnMap[columnReference.sourceColumn.id] ?: throw ConvertEntityException.association(
                "OutAssociation [${outAssociation.name}] create fail: " +
                        " sourceColumn [${columnReference.sourceColumn.id}] not found in table [$name]"
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

@Throws(ConvertEntityException::class)
fun GenTableGenerateView.inAssociations() = inAssociations.map { inAssociation ->
        val targetColumns =
            inAssociation.columnReferences.map { columnReference ->
                columnMap[columnReference.targetColumn.id] ?: throw ConvertEntityException.association(
                    "InAssociation [${inAssociation.name}] create fail: " +
                            " targetColumn [${columnReference.targetColumn.id}] not found in table [$name]"
                )
            }

        val sourceColumns =
            inAssociation.columnReferences.map { it.sourceColumn }

        InAssociationMeta(
            association = GenAssociationSimpleView(inAssociation.toEntity()),
            targetTable = this,
            targetColumns = targetColumns,
            sourceTable = inAssociation.sourceTable,
            sourceColumns = sourceColumns,
        )
    }

@Throws(ConvertEntityException::class)
fun GenTableConvertView.outAssociations() = outAssociations.map { outAssociation ->
    val sourceColumns =
        outAssociation.columnReferences.map { columnReference ->
            columnMap[columnReference.sourceColumn.id] ?: throw ConvertEntityException.association(
                "OutAssociation [${outAssociation.name}] create fail: " +
                        " sourceColumn [${columnReference.sourceColumn.id}] not found in table [$name]"
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

@Throws(ConvertEntityException::class)
fun GenTableConvertView.inAssociations() = inAssociations.map { inAssociation ->
    val targetColumns =
        inAssociation.columnReferences.map { columnReference ->
            columnMap[columnReference.targetColumn.id] ?: throw ConvertEntityException.association(
                "InAssociation [${inAssociation.name}] create fail: " +
                        " targetColumn [${columnReference.targetColumn.id}] not found in table [$name]"
            )
        }

    val sourceColumns =
        inAssociation.columnReferences.map { it.sourceColumn }

    InAssociationMeta(
        association = GenAssociationSimpleView(inAssociation.toEntity()),
        targetTable = this,
        targetColumns = targetColumns,
        sourceTable = inAssociation.sourceTable,
        sourceColumns = sourceColumns,
    )
}

@Throws(ConvertEntityException::class)
fun GenTableConvertView.getAssociations() =
    TableAssociationMeta(
        outAssociations(), inAssociations()
    )
