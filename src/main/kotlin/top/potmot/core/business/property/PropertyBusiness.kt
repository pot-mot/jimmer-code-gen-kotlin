package top.potmot.core.business.property

import kotlin.jvm.Throws
import top.potmot.core.business.utils.entity.idProperty
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.enumeration.AssociationType
import top.potmot.error.GenerateException
import top.potmot.utils.string.toSingular

sealed interface PropertyBusiness {
    val property: TargetOf_properties

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

data class AssociationProperty(
    override val property: TargetOf_properties,
    val idView: TargetOf_properties?,
    val typeEntity: GenEntityBusinessView,
    val associationType: AssociationType = property.associationType!!,
    val shortView: Boolean = typeEntity.properties.any { it.inShortAssociationView },
    val longAssociation: Boolean = property.longAssociation,
    override val inListView: Boolean = property.inListView || idView?.inListView ?: false,
    override val inDetailView: Boolean = property.inDetailView || idView?.inDetailView ?: false,
    override val inInsertInput: Boolean = property.inInsertInput || idView?.inInsertInput ?: false,
    override val inUpdateInput: Boolean = property.inUpdateInput || idView?.inUpdateInput ?: false,
    override val inSpecification: Boolean = property.inSpecification || idView?.inSpecification ?: false,
    override val inOptionView: Boolean = property.inOptionView || idView?.inOptionView ?: false,
    override val inShortAssociationView: Boolean = property.inShortAssociationView || idView?.inShortAssociationView ?: false,
    override val inLongAssociationView: Boolean = property.inLongAssociationView || idView?.inLongAssociationView ?: false,
    override val inLongAssociationInput: Boolean = property.inLongAssociationInput || idView?.inLongAssociationInput ?: false,
) : PropertyBusiness {
    // 将关联属性强行转换为 IdView
    fun forceToIdView(): TargetOf_properties {
        if (idView != null) {
            return idView
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
}

fun Iterable<PropertyBusiness>.selfOrForceIdView() = map {
    if (it is AssociationProperty) {
        it.forceToIdView()
    } else {
        it.property
    }
}

@Throws(GenerateException.EntityNotFound::class)
fun GenEntityBusinessView.getPropertyBusiness(
    entityIdMap: Map<Long, GenEntityBusinessView>
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
                entityIdMap[it.entityId] ?: throw GenerateException.EntityNotFound(entityId = it.entityId)
            )
        }
    }

    return result
}
