package top.potmot.core.entity.convert.meta

import top.potmot.entity.sub.JoinColumnMeta
import top.potmot.entity.sub.JoinTableMeta
import top.potmot.enumeration.AssociationType

data class AssociationAnnotationMeta(
    val type: AssociationType,
    val mappedBy: String? = null,
    val inputNotNull: Boolean? = null,
    val joinColumns: List<JoinColumnMeta>? = null,
    val joinTable: JoinTableMeta? = null,
)
