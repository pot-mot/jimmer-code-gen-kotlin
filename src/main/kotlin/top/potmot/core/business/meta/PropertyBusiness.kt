package top.potmot.core.business.meta

import top.potmot.core.business.view.generate.staticPath
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.enumeration.AssociationType
import top.potmot.utils.string.toSingular

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
    val upperName: String = property.name.replaceFirstChar { it.uppercaseChar() },
    val inListView: Boolean = property.inListView,
    val inDetailView: Boolean = property.inDetailView,
    val inInsertInput: Boolean = property.inInsertInput,
    val inUpdateInput: Boolean = property.inUpdateInput,
    val inSpecification: Boolean = property.inSpecification,
    val inOptionView: Boolean = property.inOptionView,
    val inShortAssociationView: Boolean = property.inShortAssociationView,
    val inLongAssociationView: Boolean = property.inLongAssociationView,
    val inLongAssociationInput: Boolean = property.inLongAssociationInput,
) {
    /**
     * 在编辑时是否可能为空（因为前端编辑需求，部分属性例如对单短关联 id 就需要可空）
     */
    val editNullable by lazy {
        this is ForceIdViewProperty && associationType.isTargetOne
    }

    fun toNullable() = when (this) {
        is CommonProperty -> copy(property = property.copy(typeNotNull = false))
        is EnumProperty -> copy(property = property.copy(typeNotNull = false))
        is AssociationProperty -> copy(
            property = property.copy(typeNotNull = false),
            idView = idView?.copy(typeNotNull = false)
        )
        is ForceIdViewProperty -> copy(
            property = property.copy(typeNotNull = false),
        )
    }
}

data class CommonProperty(
    override val entityBusiness: EntityBusiness,
    override val property: TargetOf_properties,
) : PropertyBusiness(entityBusiness, property) {
    val formType by lazy {
        property.formType
    }

    val queryType by lazy {
        property.queryType
    }

    val numberMin by lazy {
        property.numberMin
    }

    val numberMax by lazy {
        property.numberMax
    }
}

data class EnumProperty(
    override val entityBusiness: EntityBusiness,
    override val property: TargetOf_properties,
    val enum: EnumBusiness,
) : PropertyBusiness(entityBusiness, property)

sealed interface TypeEntityProperty {
    val path: AssociationPath
    val typeEntity: GenEntityBusinessView
    val associationType: AssociationType
    val isTargetOne: Boolean
    val isShortView: Boolean
    val typeEntityBusiness: SubEntityBusiness
    val selectOption: SelectOption
}

data class AssociationProperty(
    override val path: AssociationPath,
    override val entityBusiness: EntityBusiness,
    override val property: TargetOf_properties,
    val idView: TargetOf_properties?,
    override val typeEntity: GenEntityBusinessView,
    override val associationType: AssociationType,
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
    val nameWithId =
        idView?.name ?: "${if (listType) property.name.toSingular() else property.name}${if (listType) "Ids" else "Id"}"

    override val isTargetOne: Boolean = associationType.isTargetOne

    override val isShortView: Boolean = typeEntity.properties.any { it.inShortAssociationView }

    val isLongAssociation: Boolean = property.longAssociation

    override val typeEntityBusiness by lazy {
        val fkProperty = typeEntity.properties.firstOrNull {
            it.mappedBy == property.name || property.mappedBy == it.name
        }

        val filteredEntity = if (fkProperty != null)
            typeEntity.copy(properties = typeEntity.properties.filter { it != fkProperty })
        else
            typeEntity

        SubEntityBusiness(
            path = path.append(
                entity = entityBusiness,
                property = property,
                type = AssociationPathItemType.ENTITY,
                isSelfAssociated = entityBusiness.isSelfAssociated
            ),
            entity = filteredEntity,
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
            path = path,
            entityBusiness = entityBusiness,
            property = forceToIdViewProperty(),
            typeEntity = typeEntity,
            associationProperty = this,
            associationType = associationType,
        )
    }

    override val selectOption by lazy {
        val selectOptionName = path.propertyItems.joinToString("") { it.property.name.replaceFirstChar { c -> c.uppercaseChar() } }.replaceFirstChar { c -> c.lowercaseChar() } + "Options"
        val selectOptionComment = path.propertyItems.joinToString("-") { it.property.comment } + "选项"

        SelectOption(
            selectOptionName,
            selectOptionComment,
            typeEntityBusiness.dto.optionView,
            staticPath,
            typeEntityBusiness.apiServiceName,
        )
    }

    val longComponent by lazy {
        if (listType)
            typeEntityBusiness.components.editTable
        else
            typeEntityBusiness.components.subForm
    }

    val longComponentRefName by lazy {
        longComponent.name.replaceFirstChar { it.uppercaseChar() } + "Ref"
    }
}

data class ForceIdViewProperty(
    override val path: AssociationPath,
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

    override val selectOption by lazy {
        val selectOptionName = path.propertyItems.joinToString("") { it.property.name.replaceFirstChar { c -> c.uppercaseChar() } }.replaceFirstChar { c -> c.lowercaseChar() } + "Options"
        val selectOptionComment = path.propertyItems.joinToString("-") { it.property.comment } + "选项"

        SelectOption(
            selectOptionName,
            selectOptionComment,
            typeEntityBusiness.dto.optionView,
            staticPath,
            typeEntityBusiness.apiServiceName,
        )
    }
}
