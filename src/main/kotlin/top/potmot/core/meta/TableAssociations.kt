package top.potmot.core.meta

import org.babyfish.jimmer.ImmutableObjects
import top.potmot.model.GenAssociation
import top.potmot.model.copy
import top.potmot.model.dto.GenTableAssociationsView

data class TableAssociations(
    val outAssociations: List<GenAssociation>,
    val inAssociations: List<GenAssociation>,
)

fun GenTableAssociationsView.getAssociations(): TableAssociations {
    val columnMap = columns.associateBy { it.id }

    val outAssociations = this.outAssociations.map { outAssociation ->
        outAssociation.toEntity().copy {
            this.sourceTable = ImmutableObjects.toLonely(this@getAssociations.toEntity())
            this.columnReferences = outAssociation.columnReferences.map { columnReference ->
                columnReference.toEntity().copy {
                    if (!columnMap.containsKey(columnReference.sourceColumn.id)) {
                        throw RuntimeException("out association [${outAssociation.name}] restore fail: \nsourceColumn [${columnReference.sourceColumn.id}] not found in table [${this@getAssociations.name}]")
                    }
                    this.sourceColumn = ImmutableObjects.toLonely(columnMap[columnReference.sourceColumn.id]!!.toEntity())
                }
            }
        }
    }

    val inAssociations = this.inAssociations.map { inAssociation ->
        inAssociation.toEntity().copy {
            this.targetTable = ImmutableObjects.toLonely(this@getAssociations.toEntity())
            this.columnReferences = inAssociation.columnReferences.map { columnReference ->
                columnReference.toEntity().copy {
                    if (!columnMap.containsKey(columnReference.targetColumn.id)) {
                        throw RuntimeException("in association [${inAssociation.name}] restore fail: \ntargetColumn [${columnReference.targetColumn.id}] not found in table [${this@getAssociations.name}]")
                    }
                    this.targetColumn = ImmutableObjects.toLonely(columnMap[columnReference.targetColumn.id]!!.toEntity())
                }
            }
        }
    }

    return TableAssociations(
        outAssociations, inAssociations
    )
}
