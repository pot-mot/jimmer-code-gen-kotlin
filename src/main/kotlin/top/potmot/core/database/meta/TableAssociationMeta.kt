package top.potmot.core.database.meta

import top.potmot.error.ConvertEntityException
import top.potmot.entity.dto.GenAssociationSimpleView
import top.potmot.entity.dto.GenTableAssociationsView

data class TableAssociationMeta(
    val outAssociations: List<OutAssociationMeta>,
    val inAssociations: List<InAssociationMeta>,
)

val GenTableAssociationsView.columnMap
    get() = columns.associateBy { it.id }

@Throws(ConvertEntityException::class)
fun GenTableAssociationsView.outAssociations() = outAssociations.map { outAssociation ->
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
fun GenTableAssociationsView.inAssociations() = inAssociations.map { inAssociation ->
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
fun GenTableAssociationsView.getAssociations(): TableAssociationMeta {
    return TableAssociationMeta(
        outAssociations(), inAssociations()
    )
}
