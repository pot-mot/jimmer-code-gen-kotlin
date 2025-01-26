package top.potmot.core.business.meta

import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.IdName
import top.potmot.error.ModelException

enum class AssociationPathItemType {
    ENTITY,
    PROPERTY,
}

data class AssociationPathItem(
    val entity: EntityBusiness,
    val property: GenEntityBusinessView.TargetOf_properties,
    val type: AssociationPathItemType,
) {
    val key = "${entity.id} ${property.id} ${type.name}"
}

data class AssociationPath(
    val rootEntity: EntityBusiness,
    val items: List<AssociationPathItem>,
) {
    val itemKeys by lazy {
        items.map { it.key }
    }

    val propertyItems by lazy {
        items.filter { it.type == AssociationPathItemType.PROPERTY }
    }

    fun append(
        entity: EntityBusiness,
        property: GenEntityBusinessView.TargetOf_properties,
        type: AssociationPathItemType,
        isSelfAssociated: Boolean,
    ): AssociationPath {
        val newPath = AssociationPathItem(entity, property, type)

        if (
            (!isSelfAssociated && newPath.key !in itemKeys) ||
            itemKeys.filter { it == newPath.key }.size < 2
        ) {
            return AssociationPath(rootEntity, items + newPath)
        } else {
            throw ModelException.longAssociationCircularDependence(
                message = "entity [${entity.name}] property [${property.name}] become circular dependence path\n" +
                        "full path: [${(items + newPath)}]",
                entity = IdName(rootEntity.id, rootEntity.name),
                properties = propertyItems.map { IdName(it.property.id, it.property.name) }
            )
        }
    }
}
