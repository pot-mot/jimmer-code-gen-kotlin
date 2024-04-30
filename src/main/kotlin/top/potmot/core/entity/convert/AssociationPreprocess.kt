package top.potmot.core.entity.convert

import top.potmot.core.database.meta.InAssociationMeta
import top.potmot.core.database.meta.OutAssociationMeta
import top.potmot.core.database.meta.TableAssociationMeta
import top.potmot.enumeration.AssociationType
import top.potmot.entity.extension.allLeafTables

/**
 * 将所有 OneToMany 恢复为 ManyToOne
 */
fun TableAssociationMeta.processOneToMany(): TableAssociationMeta {
    val newOutAssociations = mutableListOf<OutAssociationMeta>()

    val newInAssociations = mutableListOf<InAssociationMeta>()

    outAssociations.forEach {
        if (it.association.type != AssociationType.ONE_TO_MANY) {
            newOutAssociations += it
        } else {
            newInAssociations += it.reversed()
        }
    }

    inAssociations.forEach {
        if (it.association.type != AssociationType.ONE_TO_MANY) {
            newInAssociations += it
        } else {
            newOutAssociations += it.reversed()
        }
    }

    return TableAssociationMeta(
        newOutAssociations,
        newInAssociations
    )
}

private fun OutAssociationMeta.reversed(): InAssociationMeta {
    return InAssociationMeta(
        association = association.copy(type = association.type.reversed()),
        sourceTable = targetTable,
        sourceColumns = targetColumns.map { it },
        targetTable = sourceTable,
        targetColumns = sourceColumns
    )
}

private fun InAssociationMeta.reversed(): OutAssociationMeta {
    return OutAssociationMeta(
        association = association.copy(type = association.type.reversed()),
        sourceTable = targetTable,
        sourceColumns = targetColumns,
        targetTable = sourceTable,
        targetColumns = sourceColumns.map { it }
    )
}

fun TableAssociationMeta.processLeafTables() : TableAssociationMeta {
    val newOutAssociations = mutableListOf<OutAssociationMeta>()

    val newInAssociations = mutableListOf<InAssociationMeta>()

    outAssociations.forEach {
        val allLeafTables = it.targetTable.allLeafTables()

        if (allLeafTables.isNotEmpty()) {
            newOutAssociations += allLeafTables.map { leafTable ->
                it.copy(targetTable = leafTable)
            }
        } else {
            newOutAssociations += it
        }
    }

    inAssociations.forEach {
        val allLeafTables = it.sourceTable.allLeafTables()

        if (it.sourceTable.allLeafTables().isNotEmpty()) {
            newInAssociations += allLeafTables.map { leafTable ->
                it.copy(sourceTable = leafTable)
            }
        } else {
            newInAssociations += it
        }
    }

    return TableAssociationMeta(
        newOutAssociations,
        newInAssociations
    )
}
