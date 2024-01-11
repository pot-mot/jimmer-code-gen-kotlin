package top.potmot.core.entity.meta

import top.potmot.config.GlobalGenConfig
import top.potmot.enumeration.AssociationType
import top.potmot.model.GenPropertyDraft

data class AssociationAnnotationMeta(
    val associationType: AssociationType,
    val mappedBy: String? = null,
    val joinColumns: List<JoinColumnMeta> = emptyList(),
    val joinTable: JoinTableMeta? = null,
) {
    fun toAnnotation() =
        buildString {
            append("@" + associationType.toAnnotation().simpleName)
            if (mappedBy.isNullOrBlank()) {
                if (GlobalGenConfig.joinColumnAnnotation) {
                    joinColumns.forEach {
                        append("\n${it.toAnnotation()}")
                    }
                }

                if (GlobalGenConfig.joinTableAnnotation) {
                    joinTable?.let {
                        append("\n${it.toAnnotation()}")
                    }
                }
            } else {
                append("(mappedBy = \"${mappedBy}\")")
            }
        }
}

fun GenPropertyDraft.setAssociation(
    meta: AssociationAnnotationMeta
) {
    this.associationType = meta.associationType
    this.associationAnnotation = meta.toAnnotation()
    meta.mappedBy?.takeIf { it.isNotBlank() }?.let {
        if (associationType == AssociationType.ONE_TO_ONE) {
            this.typeNotNull = false
        }
    }
}
