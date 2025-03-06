package top.potmot.core.business.meta

import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.common.numberMax
import top.potmot.core.common.numberMin
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.entity.dto.IdName
import top.potmot.enumeration.AssociationType
import top.potmot.error.ModelException
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
        if (property.column == null) null
        else numberMin(property.column.typeCode, property.column.dataSize, property.column.numericPrecision)
    }

    val numberMax by lazy {
        if (property.column == null) null
        else numberMax(property.column.typeCode, property.column.dataSize, property.column.numericPrecision)
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

private fun withIdName(name: String, listType: Boolean) =
    "${name.let { if (listType) it.toSingular() else it }}${if (listType) "Ids" else "Id"}"

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

        SubEntityBusiness(
            path = path.append(
                entity = entityBusiness,
                property = property,
                type = AssociationPathItemType.ENTITY,
                isSelfAssociated = entityBusiness.isSelfAssociated
            ),
            currentFkProperty = fkProperty,
            entity = typeEntity,
            entityIdMap = entityBusiness.entityIdMap,
            enumIdMap = entityBusiness.enumIdMap,
        )
    }

    val forceIdView: ForceIdViewProperty by lazy {
        val forceConvertedIdViewProperty = idView?.copy(
            id = property.id,
            comment = property.comment,
            typeEntityId = property.typeEntityId,
        )
            ?: property.copy(
                name = withIdName(name = property.name, listType = property.listType),
                type = typeEntity.properties.firstOrNull { it.idProperty }?.type
                    ?: throw ModelException.idPropertyNotFound(
                        "entity [${typeEntity.name}](${typeEntity.id}) id not found",
                        entity = IdName(typeEntity.id, typeEntity.name)
                    ),
                idView = true,
            )

        val replaceLastPath = path.copy(
            items = if (path.items.isNotEmpty())
                path.items.dropLast(1) + path.items.last().copy(property = forceConvertedIdViewProperty)
            else emptyList()
        )

        ForceIdViewProperty(
            path = replaceLastPath,
            entityBusiness = entityBusiness,
            // 将关联属性强制转换为 IdView，保留关联属性 comment 和 typeEntityId
            property = forceConvertedIdViewProperty,
            typeEntity = typeEntity,
            associationProperty = this,
            associationType = associationType,
        )
    }

    override val selectOption by lazy {
        val selectOptionName =
            path.propertyItems.joinToString("") { it.property.name.replaceFirstChar { c -> c.uppercaseChar() } }
                .replaceFirstChar { c -> c.lowercaseChar() }
                .let { "${withIdName(name = it, listType = property.listType)}Options" }
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
        val selectOptionName =
            path.propertyItems.joinToString("") { it.property.name.replaceFirstChar { c -> c.uppercaseChar() } }
                .replaceFirstChar { c -> c.lowercaseChar() } + "Options"
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
