package top.potmot.core.business.meta

import top.potmot.core.business.utils.entity.idProperty
import top.potmot.core.business.utils.mark.upperName
import top.potmot.core.business.utils.property.nameOrWithId
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.enumeration.AssociationType
import top.potmot.error.GenerateException
import top.potmot.utils.string.toSingular

sealed interface PropertyBusiness {
    val property: TargetOf_properties

    val formType: PropertyFormType

    val queryType: PropertyQueryType

    val id: Long

    val name: String

    val comment: String

    val typeNotNull: Boolean

    val numberPrecision: Int?

    val upperName: String

    val numberMin: String?

    val numberMax: String?

    /**
     * 是否在列表视图DTO中
     */
    val inListView: Boolean

    /**
     * 是否在详情视图DTO中
     */
    val inDetailView: Boolean

    /**
     * 是否在新增入参DTO中
     */
    val inInsertInput: Boolean

    /**
     * 是否在修改入参DTO中
     */
    val inUpdateInput: Boolean

    /**
     * 是否在查询规格DTO中
     */
    val inSpecification: Boolean

    /**
     * 是否在选项视图DTO中
     */
    val inOptionView: Boolean

    /**
     * 是否在短关联视图DTO中
     */
    val inShortAssociationView: Boolean

    /**
     * 是否在长关联视图DTO中
     */
    val inLongAssociationView: Boolean

    /**
     * 是否在长关联入参DTO中
     */
    val inLongAssociationInput: Boolean
}

data class CommonProperty(
    override val property: TargetOf_properties,
    override val id: Long = property.id,
    override val name: String = property.name,
    override val comment: String = property.comment,
    override val typeNotNull: Boolean = property.typeNotNull,
    override val numberPrecision: Int? = property.numberPrecision,
    override val formType: PropertyFormType = property.formType,
    override val queryType: PropertyQueryType = property.queryType,
    override val upperName: String = property.upperName,
    override val numberMin: String? = property.numberMin,
    override val numberMax: String? = property.numberMax,
    override val inListView: Boolean = property.inListView,
    override val inDetailView: Boolean = property.inDetailView,
    override val inInsertInput: Boolean = property.inInsertInput,
    override val inUpdateInput: Boolean = property.inUpdateInput,
    override val inSpecification: Boolean = property.inSpecification,
    override val inOptionView: Boolean = property.inOptionView,
    override val inShortAssociationView: Boolean = property.inShortAssociationView,
    override val inLongAssociationView: Boolean = property.inLongAssociationView,
    override val inLongAssociationInput: Boolean = property.inLongAssociationInput,
) : PropertyBusiness

sealed interface TypeEntityProperty {
    val typeEntity: GenEntityBusinessView
    val associationType: AssociationType
    val isShortView: Boolean
}

data class AssociationProperty(
    override val property: TargetOf_properties,
    val idView: TargetOf_properties?,
    override val typeEntity: GenEntityBusinessView,
    override val associationType: AssociationType = property.associationType!!,
    override val isShortView: Boolean = typeEntity.properties.any { it.inShortAssociationView },
    val isLongAssociation: Boolean = property.longAssociation,

    override val id: Long = property.id,
    override val name: String = property.name,
    override val comment: String = property.comment,
    override val typeNotNull: Boolean = property.typeNotNull,
    override val numberPrecision: Int? = property.numberPrecision,
    override val formType: PropertyFormType = property.formType,
    override val queryType: PropertyQueryType = property.queryType,
    override val upperName: String = property.upperName,
    override val numberMin: String? = property.numberMin,
    override val numberMax: String? = property.numberMax,
    override val inListView: Boolean = property.inListView || idView?.inListView ?: false,
    override val inDetailView: Boolean = property.inDetailView || idView?.inDetailView ?: false,
    override val inInsertInput: Boolean = property.inInsertInput || idView?.inInsertInput ?: false,
    override val inUpdateInput: Boolean = property.inUpdateInput || idView?.inUpdateInput ?: false,
    override val inSpecification: Boolean = property.inSpecification || idView?.inSpecification ?: false,
    override val inOptionView: Boolean = property.inOptionView || idView?.inOptionView ?: false,
    override val inShortAssociationView: Boolean = property.inShortAssociationView || idView?.inShortAssociationView ?: false,
    override val inLongAssociationView: Boolean = property.inLongAssociationView || idView?.inLongAssociationView ?: false,
    override val inLongAssociationInput: Boolean = property.inLongAssociationInput || idView?.inLongAssociationInput ?: false,
) : PropertyBusiness, TypeEntityProperty {
    val nameOrWithId =
        idView?.name ?: property.nameOrWithId

    // 将关联属性强制转换为 IdView，保留关联属性 comment 和 typeEntityId
    fun forceToIdViewProperty(): TargetOf_properties {
        if (idView != null) {
            return idView.copy(comment = property.comment, typeEntityId = property.typeEntityId)
        }

        return if (property.listType) {
            property.copy(
                name = "${property.name.toSingular()}Ids",
                type = typeEntity.idProperty.type,
                idView = true,
            )
        } else {
            property.copy(
                name = "${property.name}Id",
                type = typeEntity.idProperty.type,
                idView = true,
            )
        }
    }

    fun forceToIdView(): ForceIdViewProperty {
        return ForceIdViewProperty(
            property = forceToIdViewProperty(),
            typeEntity = typeEntity,
            associationProperty = this,
        )
    }
}

data class ForceIdViewProperty(
    override val property: TargetOf_properties,
    override val typeEntity: GenEntityBusinessView,
    val associationProperty: AssociationProperty,
    override val associationType: AssociationType = property.associationType!!,
    override val isShortView: Boolean = typeEntity.properties.any { it.inShortAssociationView },
    override val id: Long = property.id,
    override val name: String = property.name,
    override val comment: String = property.comment,
    override val typeNotNull: Boolean = property.typeNotNull,
    override val numberPrecision: Int? = property.numberPrecision,
    override val formType: PropertyFormType = property.formType,
    override val queryType: PropertyQueryType = property.queryType,
    override val upperName: String = property.upperName,
    override val numberMin: String? = property.numberMin,
    override val numberMax: String? = property.numberMax,
    override val inListView: Boolean = property.inListView,
    override val inDetailView: Boolean = property.inDetailView,
    override val inInsertInput: Boolean = property.inInsertInput,
    override val inUpdateInput: Boolean = property.inUpdateInput,
    override val inSpecification: Boolean = property.inSpecification,
    override val inOptionView: Boolean = property.inOptionView,
    override val inShortAssociationView: Boolean = property.inShortAssociationView,
    override val inLongAssociationView: Boolean = property.inLongAssociationView,
    override val inLongAssociationInput: Boolean = property.inLongAssociationInput,
): PropertyBusiness, TypeEntityProperty


fun Iterable<PropertyBusiness>.selfOrForceIdViewProperties() = map {
    if (it is AssociationProperty) {
        it.forceToIdViewProperty()
    } else {
        it.property
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

@Throws(GenerateException.EntityNotFound::class)
fun GenEntityBusinessView.getPropertyBusiness(
    entityIdMap: Map<Long, GenEntityBusinessView>,
): List<PropertyBusiness> {
    val result = mutableListOf<PropertyBusiness>()
    val idViewTargetMap = properties.filter { it.idView }.associateBy { it.idViewTarget }

    properties.forEach {
        if (it.associationType == null) {
            result += CommonProperty(it)
        } else if (!it.idView) {
            result += AssociationProperty(
                it,
                idViewTargetMap[it.name],
                entityIdMap[it.typeEntityId] ?: throw GenerateException.EntityNotFound(
                    message = "Entity [${it.typeEntityId}] Not Found",
                    entityId = it.typeEntityId
                )
            )
        }
    }

    return result
}
