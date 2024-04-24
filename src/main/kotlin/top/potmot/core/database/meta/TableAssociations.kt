package top.potmot.core.database.meta

import top.potmot.error.ConvertEntityException
import top.potmot.model.dto.GenTableAssociationsView

data class TableAssociations(
    val outAssociations: List<OutAssociationMeta>,
    val inAssociations: List<InAssociationMeta>,
)

@Throws(ConvertEntityException::class)
fun GenTableAssociationsView.getAssociations(): TableAssociations {
    val columnMap = columns.associateBy { it.id }

    val outAssociations = this.outAssociations.map { outAssociation ->
        val sourceColumns =
            outAssociation.columnReferences.map { columnReference ->
                columnMap[columnReference.sourceColumn.id] ?: throw ConvertEntityException.association(
                    "OutAssociation [${outAssociation.name}] create fail: " +
                            " sourceColumn [${columnReference.sourceColumn.id}] not found in table [${this@getAssociations.name}]"
                )
            }

        val targetColumns =
            outAssociation.columnReferences.map { it.targetColumn }

        OutAssociationMeta(
            association = outAssociation,
            sourceTable = this@getAssociations,
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
                            " targetColumn [${columnReference.targetColumn.id}] not found in table [${this@getAssociations.name}]"
                )
            }

        val sourceColumns =
            inAssociation.columnReferences.map { it.sourceColumn }

        InAssociationMeta(
            association = inAssociation,
            targetTable = this@getAssociations,
            targetColumns = targetColumns,
            sourceTable = inAssociation.sourceTable,
            sourceColumns = sourceColumns,
        )
    }

    return TableAssociations(
        outAssociations, inAssociations
    )
}
