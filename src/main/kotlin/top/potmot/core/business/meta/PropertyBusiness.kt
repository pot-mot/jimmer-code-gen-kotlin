package top.potmot.core.business.meta

import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.enumeration.AssociationType

sealed class PropertyBusiness(
    open val entityBusiness: EntityBusiness,
    open val property: TargetOf_properties,
    val id: Long = property.id,
    val name: String = property.name,
    val comment: String = property.comment,
    val type: String = property.type,
    val typeNotNull: Boolean = property.typeNotNull,
    val listType: Boolean = property.listType,
    val dataSize: Int? = property.column?.dataSize,
    val numericPrecision: Int? = property.column?.numericPrecision,
    val formType: PropertyFormType = property.formType,
    val queryType: PropertyQueryType = property.queryType,
    val upperName: String = property.name.replaceFirstChar { it.uppercaseChar() },
    val numberMin: String? = property.numberMin,
    val numberMax: String? = property.numberMax,
    val inListView: Boolean = property.inListView,
    val inDetailView: Boolean = property.inDetailView,
    val inInsertInput: Boolean = property.inInsertInput,
    val inUpdateInput: Boolean = property.inUpdateInput,
    val inSpecification: Boolean = property.inSpecification,
    val inOptionView: Boolean = property.inOptionView,
    val inShortAssociationView: Boolean = property.inShortAssociationView,
    val inLongAssociationView: Boolean = property.inLongAssociationView,
    val inLongAssociationInput: Boolean = property.inLongAssociationInput,
)

data class CommonProperty(
    override val entityBusiness: EntityBusiness,
    override val property: TargetOf_properties,
) : PropertyBusiness(entityBusiness, property)

data class EnumProperty(
    override val entityBusiness: EntityBusiness,
    override val property: TargetOf_properties,
    val enum: EnumBusiness
) : PropertyBusiness(entityBusiness, property)

sealed interface TypeEntityProperty {
    val typeEntity: GenEntityBusinessView
    val associationType: AssociationType
    val isTargetOne: Boolean
    val isShortView: Boolean
    val typeEntityBusiness: EntityBusiness
}

data class AssociationProperty(
    override val entityBusiness: EntityBusiness,
    override val property: TargetOf_properties,
    val idView: TargetOf_properties?,
    override val typeEntity: GenEntityBusinessView,
    override val associationType: AssociationType
) : PropertyBusiness(
    entityBusiness,
    property,
    inListView = property.inListView || idView?.inListView ?: false,
    inDetailView = property.inDetailView || idView?.inDetailView ?: false,
    inInsertInput = property.inInsertInput || idView?.inInsertInput ?: false,
    inUpdateInput = property.inUpdateInput || idView?.inUpdateInput ?: false,
    inSpecification = property.inSpecification || idView?.inSpecification ?: false,
    inOptionView = property.inOptionView || idView?.inOptionView ?: false,
    inShortAssociationView = property.inShortAssociationView || idView?.inShortAssociationView ?: false,
    inLongAssociationView = property.inLongAssociationView || idView?.inLongAssociationView ?: false,
    inLongAssociationInput = property.inLongAssociationInput || idView?.inLongAssociationInput ?: false,
), TypeEntityProperty {
    val nameOrWithId =
        idView?.name ?: "${property.name}${if (listType) "Ids" else "Id"}"

    override val isTargetOne: Boolean = associationType.isTargetOne

    override val isShortView: Boolean = typeEntity.properties.any { it.inShortAssociationView }

    val isLongAssociation: Boolean = property.longAssociation

    override val typeEntityBusiness by lazy {
        EntityBusiness(
            typeEntity,
            entityIdMap = entityBusiness.entityIdMap,
            enumIdMap = entityBusiness.enumIdMap,
        )
    }

    // 将关联属性强制转换为 IdView，保留关联属性 comment 和 typeEntityId
    private fun forceToIdViewProperty(): TargetOf_properties =
        if (idView != null) {
            idView.copy(
                comment = property.comment,
                typeEntityId = property.typeEntityId
            )
        } else {
            property.copy(
                name = "${property.name}${if (listType) "Ids" else "Id"}",
                type = typeEntityBusiness.idProperty.type,
                idView = true,
            )
        }

    fun forceToIdView(): ForceIdViewProperty {
        return ForceIdViewProperty(
            entityBusiness = entityBusiness,
            property = forceToIdViewProperty(),
            typeEntity = typeEntity,
            associationProperty = this,
            associationType = associationType,
        )
    }
}

data class ForceIdViewProperty(
    override val entityBusiness: EntityBusiness,
    override val property: TargetOf_properties,
    override val typeEntity: GenEntityBusinessView,
    val associationProperty: AssociationProperty,
    override val associationType: AssociationType,
) : PropertyBusiness(entityBusiness, property), TypeEntityProperty {
    override val isTargetOne: Boolean = associationType.isTargetOne

    override val isShortView: Boolean = typeEntity.properties.any { it.inShortAssociationView }

    override val typeEntityBusiness by lazy {
        associationProperty.typeEntityBusiness
    }
}


fun Iterable<PropertyBusiness>.selfOrForceIdView() = map {
    if (it is AssociationProperty) {
        it.forceToIdView()
    } else {
        it
    }
}

fun Iterable<AssociationProperty>.forceIdView() = map {
    it.forceToIdView()
}
