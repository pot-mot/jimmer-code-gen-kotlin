package top.potmot.core.entity.convert.meta

import top.potmot.core.entity.meta.JoinColumnMeta
import top.potmot.core.entity.meta.JoinTableMeta
import top.potmot.enumeration.AssociationType

data class AssociationAnnotationMeta(
    val type: AssociationType,
    val mappedBy: String? = null,
    val inputNotNull: Boolean? = null,
    val joinColumns: List<JoinColumnMeta>? = null,
    val joinTable: JoinTableMeta? = null,
)
