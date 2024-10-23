package top.potmot.core.entity.convert

import top.potmot.core.database.meta.InAssociationMeta
import top.potmot.core.database.meta.OutAssociationMeta
import top.potmot.core.database.meta.TableAssociationMeta
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.extension.allLeafTables
import top.potmot.enumeration.AssociationType

/**
 * 将所有 OneToMany 恢复为 ManyToOne
 */
fun TableAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns>.reverseOneToMany(): TableAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns> {
    val newOutAssociations = mutableListOf<OutAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns>>()
    val newInAssociations = mutableListOf<InAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns>>()

    outAssociations.forEach {
        if (it.association.type == AssociationType.ONE_TO_MANY) {
            newInAssociations += it.reversed()
        } else {
            newOutAssociations += it
        }
    }

    inAssociations.forEach {
        if (it.association.type == AssociationType.ONE_TO_MANY) {
            newOutAssociations += it.reversed()
        } else {
            newInAssociations += it
        }
    }

    return TableAssociationMeta(
        newOutAssociations,
        newInAssociations
    )
}

/**
 * 将所有反向 OneToOne 恢复为正向 OneToOne
 */
fun TableAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns>.reverseReversedOneToOne(): TableAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns> {
    val newOutAssociations = mutableListOf<OutAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns>>()
    val newInAssociations = mutableListOf<InAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns>>()

    outAssociations.forEach {
        if (it.association.type == AssociationType.ONE_TO_ONE && it.sourceColumns.all { it.partOfPk }) {
            newInAssociations += it.reversed()
        } else {
            newOutAssociations += it
        }
    }

    inAssociations.forEach {
        if (it.association.type == AssociationType.ONE_TO_ONE && it.sourceColumns.all { it.partOfPk }) {
            newOutAssociations += it.reversed()
        } else {
            newInAssociations += it
        }
    }

    return TableAssociationMeta(
        newOutAssociations,
        newInAssociations
    )
}

private fun OutAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns>.reversed() =
    InAssociationMeta(
        association = association.copy(type = association.type.reversed()),
        sourceTable = targetTable,
        sourceColumns = targetColumns.map { it },
        targetTable = sourceTable,
        targetColumns = sourceColumns
    )

private fun InAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns>.reversed() =
    OutAssociationMeta(
        association = association.copy(type = association.type.reversed()),
        sourceTable = targetTable,
        sourceColumns = targetColumns,
        targetTable = sourceTable,
        targetColumns = sourceColumns.map { it }
    )

/**
 *  将所有叶子表中的关联汇集到祖关联元数据中，使对应上级表的关联转换为对全部叶子表的关联
 *  eq:
 *      存在关联：
 *          BaseEntity.createBy -> User.id
 *      假定 Entity1、Entity2、Entity3 继承于 BaseEntity，则该关联将转变为：
 *          Entity1.createBy -> User.id
 *          Entity2.createBy -> User.id
 *          Entity3.createBy -> User.id
 */
fun TableAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns>.aggregateOtherSideLeafTableAssociations():
        TableAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns> {
    val newOutAssociations = mutableListOf<OutAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns>>()

    val newInAssociations = mutableListOf<InAssociationMeta<GenTableConvertView, GenTableConvertView.TargetOf_columns>>()

    outAssociations.forEach {
        val allTargetLeafTables = it.targetTable.allLeafTables()

        if (allTargetLeafTables.size == 1) {
            newOutAssociations += it
        } else {
            newOutAssociations +=
                allTargetLeafTables.map { targetTable ->
                    it.copy(targetTable = targetTable)
                }
        }
    }

    inAssociations.forEach {
        val allSourceLeafTables = it.sourceTable.allLeafTables()

        if (allSourceLeafTables.size == 1) {
            newInAssociations += it
        } else {
            newInAssociations += allSourceLeafTables.map { sourceTable ->
                it.copy(sourceTable = sourceTable)
            }
        }
    }

    return TableAssociationMeta(
        newOutAssociations,
        newInAssociations
    )
}