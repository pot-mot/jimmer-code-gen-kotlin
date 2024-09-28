package top.potmot.core.database.generate

import top.potmot.constant.INHERIT_PLACEHOLDER
import top.potmot.constant.SOURCE_INHERIT_PLACEHOLDER
import top.potmot.constant.TARGET_INHERIT_PLACEHOLDER
import top.potmot.entity.dto.GenTableAssociationsView
import top.potmot.entity.extension.allSuperTables
import top.potmot.utils.string.replaceFirstOrAppend

fun GenTableAssociationsView.toFull(): GenTableAssociationsView {
    val allSuperTables = allSuperTables()

    val tableName = this.name

    return copy(
        superTables = emptyList(),
        columns = columns + allSuperTables.flatMap { it.columns },
        inAssociations = inAssociations + allSuperTables.flatMap {
            it.inAssociations.flatMap { inAssociation ->
                inAssociation.getLeafAssociations().map { leafAssociation ->
                    leafAssociation.copy(
                        name = leafAssociation.name.replaceFirstOrAppend(
                            TARGET_INHERIT_PLACEHOLDER,
                            tableName
                        )
                    )
                }
            }
        },
        outAssociations = outAssociations + allSuperTables.flatMap {
            it.outAssociations.flatMap { outAssociation ->
                outAssociation.getLeafAssociations().map { leafAssociation ->
                    leafAssociation.copy(
                        name = leafAssociation.name.replaceFirstOrAppend(
                            SOURCE_INHERIT_PLACEHOLDER,
                            tableName
                        )
                    )
                }
            }
        },
        indexes = indexes + allSuperTables.flatMap {
            it.indexes.map { index ->
                index.copy(
                    name = index.name.replaceFirstOrAppend(
                        INHERIT_PLACEHOLDER,
                        tableName
                    )
                )
            }
        }
    )
}

private fun GenTableAssociationsView.TargetOf_inAssociations.TargetOf_sourceTable.getLeafTables():
        List<GenTableAssociationsView.TargetOf_inAssociations.TargetOf_sourceTable> =
    if (!this.inheritTables.isNullOrEmpty()) {
        this.inheritTables!!.flatMap { it.getLeafTables() }
    } else {
        listOf(this)
    }

private fun GenTableAssociationsView.TargetOf_outAssociations.TargetOf_targetTable.getLeafTables():
        List<GenTableAssociationsView.TargetOf_outAssociations.TargetOf_targetTable> =
    if (!this.inheritTables.isNullOrEmpty()) {
        this.inheritTables!!.flatMap { it.getLeafTables() }
    } else {
        listOf(this)
    }

private fun GenTableAssociationsView.TargetOf_inAssociations.getLeafAssociations():
        List<GenTableAssociationsView.TargetOf_inAssociations> =
    sourceTable.getLeafTables().map {
        if (it == sourceTable)
            this
        else
            copy(sourceTable = it, name = name.replaceFirstOrAppend(SOURCE_INHERIT_PLACEHOLDER, it.name))
    }

private fun GenTableAssociationsView.TargetOf_outAssociations.getLeafAssociations():
        List<GenTableAssociationsView.TargetOf_outAssociations> =
    targetTable.getLeafTables().map {
        if (it == targetTable)
            this
        else
            copy(targetTable = it, name = name.replaceFirstOrAppend(TARGET_INHERIT_PLACEHOLDER, it.name))
    }