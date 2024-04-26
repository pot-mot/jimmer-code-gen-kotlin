package top.potmot.core.database.meta

import top.potmot.enumeration.AssociationType
import top.potmot.error.ConvertEntityException
import top.potmot.model.dto.GenAssociationSimpleView
import top.potmot.model.dto.GenTableAssociationsView

data class TableAssociationMeta(
    val outAssociations: List<OutAssociationMeta>,
    val inAssociations: List<InAssociationMeta>,
)

@Throws(ConvertEntityException::class)
fun GenTableAssociationsView.getAssociations(): TableAssociationMeta {
    val table = this

    val columnMap = columns.associateBy { it.id }

    val outAssociations = this.outAssociations.map { outAssociation ->
        val sourceColumns =
            outAssociation.columnReferences.map { columnReference ->
                columnMap[columnReference.sourceColumn.id] ?: throw ConvertEntityException.association(
                    "OutAssociation [${outAssociation.name}] create fail: " +
                            " sourceColumn [${columnReference.sourceColumn.id}] not found in table [${table.name}]"
                )
            }

        val targetColumns =
            outAssociation.columnReferences.map { it.targetColumn }

        OutAssociationMeta(
            association = GenAssociationSimpleView(outAssociation.toEntity()),
            sourceTable = table,
            sourceColumns = sourceColumns,
            targetTable = outAssociation.targetTable,
            targetColumns = targetColumns,
        )
    }

    val inAssociations = this.inAssociations.map { inAssociation ->
        val targetColumns =
            inAssociation.columnReferences.map { columnReference ->
                columnMap[columnReference.targetColumn.id] ?: throw ConvertEntityException.association(
                    "InAssociation [${inAssociation.name}] create fail: " +
                            " targetColumn [${columnReference.targetColumn.id}] not found in table [${table.name}]"
                )
            }

        val sourceColumns =
            inAssociation.columnReferences.map { it.sourceColumn }

        InAssociationMeta(
            association = GenAssociationSimpleView(inAssociation.toEntity()),
            targetTable = table,
            targetColumns = targetColumns,
            sourceTable = inAssociation.sourceTable,
            sourceColumns = sourceColumns,
        )
    }

    return TableAssociationMeta(
        outAssociations, inAssociations
    )
}
