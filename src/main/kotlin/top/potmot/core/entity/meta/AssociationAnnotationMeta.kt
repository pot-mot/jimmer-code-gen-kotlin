package top.potmot.core.entity.meta

import top.potmot.enumeration.AssociationType

data class AssociationAnnotationMeta(
    val type: AssociationType,
    val mappedBy: String? = null,
    val inputNotNull: Boolean? = null,
    val joinColumns: List<JoinColumnMeta> = emptyList(),
    val joinTable: JoinTableMeta? = null,
)
