package top.potmot.core.meta

import top.potmot.model.GenAssociation
import top.potmot.model.copy
import top.potmot.model.dto.GenTableAssociationsView

data class TableAssociationMeta(
    val outAssociations: List<GenAssociation>,
    val inAssociations: List<GenAssociation>,
)

fun GenTableAssociationsView.getAssociationMeta(): TableAssociationMeta {
    val columnMap = columns.associate { it.id to it }

    val outAssociations = this.outAssociations.map { outAssociation ->
        outAssociation.toEntity().copy {
            this.columnReferences = outAssociation.columnReferences.map { columnReference ->
                columnReference.toEntity().copy {
                    if (!columnMap.containsKey(columnReference.sourceColumnId)) {
                        throw RuntimeException("out association [${outAssociation.name}] restore fail: \nsourceColumn [${columnReference.sourceColumnId}] not found in table [${this@getAssociationMeta.name}]")
                    }
                    this.sourceColumn = columnMap[columnReference.sourceColumnId]!!.toEntity()
                }
            }
        }
    }

    val inAssociations = this.inAssociations.map { inAssociation ->
        inAssociation.toEntity().copy {
            this.columnReferences = inAssociation.columnReferences.map { columnReference ->
                columnReference.toEntity().copy {
                    if (!columnMap.containsKey(columnReference.targetColumnId)) {
                        throw RuntimeException("in association [${inAssociation.name}] restore fail: \ntargetColumn [${columnReference.targetColumnId}] not found in table [${this@getAssociationMeta.name}]")
                    }
                    this.targetColumn = columnMap[columnReference.targetColumnId]!!.toEntity()
                }
            }
        }
    }

    return TableAssociationMeta(
        outAssociations, inAssociations
    )
}
