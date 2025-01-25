package top.potmot.core.business.meta

import top.potmot.entity.dto.IdName
import top.potmot.error.ModelException

enum class AssociationPathItemType {
    ENTITY,
    PROPERTY,
}

data class AssociationPathItem(
    val entity: IdName,
    val property: IdName,
    val type: AssociationPathItemType,
)

data class AssociationPath(
    val rootEntity: EntityBusiness,
    val items: List<AssociationPathItem>,
) {
    fun append(
        entity: IdName,
        property: IdName,
        type: AssociationPathItemType,
        isSelfAssociated: Boolean,
    ): AssociationPath {
        val newPath = AssociationPathItem(entity, property, type)

        if (
            (!isSelfAssociated && newPath !in items) ||
            items.filter { it == newPath }.size < 2
        ) {
            return AssociationPath(rootEntity, items + newPath)
        } else {
            throw ModelException.longAssociationCircularDependence(
                message = "entity [${entity.name}] property [${property.name}] become circular dependence path\n" +
                        "full path: [${(items + newPath)}]",
                entity = IdName(rootEntity.id, rootEntity.name),
                associationPath = items + newPath
            )
        }
    }

    val properties by lazy {
        items.filter { it.type == AssociationPathItemType.PROPERTY }
    }
}
