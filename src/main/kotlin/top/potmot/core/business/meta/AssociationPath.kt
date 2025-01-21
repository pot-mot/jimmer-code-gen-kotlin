package top.potmot.core.business.meta

import top.potmot.entity.dto.IdName
import top.potmot.error.ModelException

enum class AssociationPathItemType {
    ENTITY,
    PROPERTY,
}

data class AssociationPathItem(
    val entityId: Long,
    val propertyId: Long,
    val type: AssociationPathItemType,
)

data class AssociationPath(
    val items: List<AssociationPathItem>,
) {
    fun append(entity: IdName, property: IdName, type: AssociationPathItemType, isSelfAssociated: Boolean): AssociationPath {
        val newPath = AssociationPathItem(entityId = entity.id, propertyId = property.id, type)

        if (
            (!isSelfAssociated && newPath !in items) ||
            items.filter { it == newPath }.size <= 2
        ) {
            return AssociationPath(items + newPath)
        } else {
            throw ModelException.longAssociationCircularDependence(
                message = "entity [${entity.name}] property [${property.name}] become circular dependences\n" +
                        "full path: [${(items + newPath)}]",
                entity = entity,
                property = property
            )
        }
    }
}


val emptyAssociationPath = AssociationPath(emptyList())