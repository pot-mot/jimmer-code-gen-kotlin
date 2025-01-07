package top.potmot.core.entity.meta

import top.potmot.entity.dto.GenTableConvertView

data class TableAssociationMeta(
    val outAssociationMetas: List<AssociationMeta>,
    val inAssociationMetas: List<AssociationMeta>,
)

fun GenTableConvertView.getAssociationMeta(
    associationMetaIdMap: Map<Long, AssociationMeta>,
): TableAssociationMeta {
    val outAssociationMetas = outAssociationIds
        .mapNotNull { associationMetaIdMap[it] }

    val inAssociationMetas = inAssociationIds
        .mapNotNull { associationMetaIdMap[it] }

    return TableAssociationMeta(
        outAssociationMetas = outAssociationMetas,
        inAssociationMetas = inAssociationMetas
    )
}