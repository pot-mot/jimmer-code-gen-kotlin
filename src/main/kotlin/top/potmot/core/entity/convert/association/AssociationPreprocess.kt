package top.potmot.core.entity.convert.association

import top.potmot.core.entity.convert.meta.AssociationMeta
import top.potmot.core.entity.convert.meta.TableAssociationMeta
import top.potmot.core.entity.convert.EntityView
import top.potmot.entity.extension.allLeafTables
import top.potmot.enumeration.AssociationType

/**
 * 将所有 OneToMany 恢复为 ManyToOne
 */
fun TableAssociationMeta.reverseOneToMany(): TableAssociationMeta {
    val newOutAssociations = mutableListOf<AssociationMeta>()
    val newInAssociations = mutableListOf<AssociationMeta>()

    outAssociationMetas.forEach {
        if (it.association.type == AssociationType.ONE_TO_MANY) {
            newInAssociations += it.reversed()
        } else {
            newOutAssociations += it
        }
    }

    inAssociationMetas.forEach {
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
fun TableAssociationMeta.reverseReversedOneToOne(): TableAssociationMeta {
    val newOutAssociations = mutableListOf<AssociationMeta>()
    val newInAssociations = mutableListOf<AssociationMeta>()

    outAssociationMetas.forEach { association ->
        if (
            association.association.type == AssociationType.ONE_TO_ONE &&
            association.sourceColumns.all { it.partOfPk }
        ) {
            newInAssociations += association.reversed()
        } else {
            newOutAssociations += association
        }
    }

    inAssociationMetas.forEach { association ->
        if (
            association.association.type == AssociationType.ONE_TO_ONE &&
            association.sourceColumns.all { it.partOfPk }
        ) {
            newOutAssociations += association.reversed()
        } else {
            newInAssociations += association
        }
    }

    return TableAssociationMeta(
        newOutAssociations,
        newInAssociations
    )
}

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
fun TableAssociationMeta.aggregateOtherSideLeafTableAssociations(
    tableIdEntityMap: Map<Long, EntityView>,
): TableAssociationMeta {
    val newOutAssociations = mutableListOf<AssociationMeta>()

    val newInAssociations = mutableListOf<AssociationMeta>()

    outAssociationMetas.forEach {
        val allTargetLeafTables = it.targetTable.allLeafTables()

        newOutAssociations += allTargetLeafTables.map { targetTable ->
            it.copy(targetTable = targetTable, targetEntity = tableIdEntityMap[targetTable.id])
        }
    }

    inAssociationMetas.forEach {
        val allSourceLeafTables = it.sourceTable.allLeafTables()

        newInAssociations += allSourceLeafTables.map { sourceTable ->
            it.copy(sourceTable = sourceTable, sourceEntity = tableIdEntityMap[sourceTable.id])
        }
    }

    return TableAssociationMeta(
        newOutAssociations,
        newInAssociations
    )
}