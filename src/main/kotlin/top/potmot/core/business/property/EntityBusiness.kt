package top.potmot.core.business.property

import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.IdName
import top.potmot.enumeration.AssociationType
import top.potmot.enumeration.targetOneAssociationTypes
import top.potmot.error.ModelException

class EntityBusiness(
    val entity: GenEntityBusinessView,
    entityIdMap: Map<Long, GenEntityBusinessView>,
) {
    val properties = entity.properties

    // 具有列的属性
    val columnProperty by lazy {
        properties
            .filter { it.column != null }
    }

    // 不具有列的属性
    val noColumnProperty by lazy {
        properties
            .filter { it.column == null }
    }

    // 标量属性
    val scalarProperties by lazy {
        columnProperty
            .filter {
                !it.idProperty && !it.logicalDelete && it.associationType == null
            }
    }

    // 关联属性
    val associationProperties by lazy {
        columnProperty
            .filter { it.associationType != null }
    }

    // 目标唯一属性
    val targetOneProperties by lazy {
        associationProperties
            .filter { it.associationType in targetOneAssociationTypes }
    }

    val propertyBusiness by lazy {
        entity.getPropertyBusiness(entityIdMap)
    }

    // 关联属性
    val associationPropertyBusiness by lazy {
        propertyBusiness.filterIsInstance<AssociationProperty>()
    }

    val isTree by lazy {
        entity.properties.any {
            it.typeEntityId == entity.id && (it.associationType == AssociationType.MANY_TO_ONE || it.associationType == AssociationType.ONE_TO_MANY)
        }
    }

    val parentPropertyBusiness: AssociationProperty by lazy {
        associationPropertyBusiness.firstOrNull {
            it.typeEntity.id == entity.id && !it.property.listType
        }
            ?: throw ModelException.treeEntityCannotFoundParentProperty(
                "Tree Entity [${entity.name}] cannot found Parent Property",
                entity = IdName(entity.id, entity.name),
                selfAssociationProperties = properties.filter { it.typeEntityId == entity.id }
                    .map { IdName(it.id, it.name) }
            )
    }

    val parentProperty by lazy {
        parentPropertyBusiness.property
    }

    val parentIdProperty by lazy {
        parentPropertyBusiness.forceToIdView()
    }


    val childrenPropertyBusiness: AssociationProperty by lazy {
        associationPropertyBusiness.firstOrNull {
            it.typeEntity.id == entity.id && it.property.listType
        }
            ?: throw ModelException.treeEntityCannotFoundParentProperty(
                "Tree Entity [${entity.name}] cannot found Children Property",
                entity = IdName(entity.id, entity.name),
                selfAssociationProperties = properties.filter { it.typeEntityId == entity.id }
                    .map { IdName(it.id, it.name) }
            )
    }

    val childrenProperty by lazy {
        childrenPropertyBusiness.property
    }

    val childIdProperty by lazy {
        childrenPropertyBusiness.forceToIdView()
    }


    val specificationSelectProperties by lazy {
        associationPropertyBusiness
            .filter { it.inSpecification && it.associationType in targetOneAssociationTypes }
            .map { it.forceToIdView() }
    }

    val insertSelectProperties by lazy {
        associationPropertyBusiness
            .filter { it.inInsertInput }
            .map { it.forceToIdView() }
    }

    val updateSelectProperties by lazy {
        associationPropertyBusiness
            .filter { it.inUpdateInput }
            .map { it.forceToIdView() }
    }

    val pageSelectProperties by lazy {
        associationPropertyBusiness
            .filter {
                (it.inSpecification && it.associationType in targetOneAssociationTypes) || it.inInsertInput || it.inUpdateInput
            }
            .map { it.forceToIdView() }
    }

    val editTableSelectProperties by lazy {
        associationPropertyBusiness
            .filter { it.inLongAssociationInput }
            .map { it.forceToIdView() }
    }


    val optionViewProperties by lazy {
        propertyBusiness
            .filter { it.inOptionView }
    }

    val optionLabelProperties by lazy {
        val optionViewProperties = optionViewProperties.selfOrForceIdView()

        if (optionViewProperties.size > 1) {
            optionViewProperties.filter { !it.idProperty }
        } else {
            optionViewProperties
        }
    }


    val listViewProperties by lazy {
        propertyBusiness
            .filter { it.inListView }
    }

    val tableProperties by lazy {
        listViewProperties
            .filter {
                val notParentProperty =
                    if (isTree) {
                        it.property.id != parentProperty.id && it.property.id != parentIdProperty.id
                    } else {
                        true
                    }

                !it.property.idProperty && notParentProperty
            }
    }

    val detailViewProperties by lazy {
        propertyBusiness
            .filter { it.inDetailView }
    }

    val viewFormProperties by lazy {
        detailViewProperties
            .filter { !it.property.idProperty }
    }


    val specificationProperties by lazy {
        properties
            .filter { it.inSpecification && !it.idView }
    }

    val queryProperties by lazy {
        propertyBusiness
            .filter {
                it.inSpecification
            }
            .selfOrForceIdView()
            .filter {
                !it.idProperty &&
                        (it.associationType == null || it.associationType in targetOneAssociationTypes)
            }
            .map { it.copy(typeNotNull = false) }
    }


    private val PropertyBusiness.isEditNullable
        get() = this is AssociationProperty && associationType in targetOneAssociationTypes && property.typeNotNull

    // 将编辑场景中必须转换为可null的属性处理为可null
    private fun Iterable<PropertyBusiness>.produceEditNullable(): List<PropertyBusiness> =
        map {
            if (it is AssociationProperty && it.isEditNullable) {
                it.copy(property = it.property.copy(typeNotNull = false), idView = it.idView?.copy(typeNotNull = false))
            } else {
                it
            }
        }


    val insertInputProperties by lazy {
        propertyBusiness
            .filter { it.inInsertInput }
    }

    val addFormProperties by lazy {
        insertInputProperties
            .produceEditNullable()
    }

    val addFormEditNullableProperty by lazy {
        insertInputProperties
            .filter { it.isEditNullable }
    }

    val addFormRulesProperties by lazy {
        insertInputProperties
            .selfOrForceIdView()
    }


    val updateInputProperties by lazy {
        propertyBusiness
            .filter { it.inUpdateInput }
    }

    val editFormProperties by lazy {
        updateInputProperties
            .filter { !it.property.idProperty }
    }

    val editFormRulesProperties by lazy {
        updateInputProperties
            .filter { !it.property.idProperty }
    }


    val shortViewProperties by lazy {
        propertyBusiness
            .filter { it.inShortAssociationView }
    }


    val longAssociationInputProperties by lazy {
        propertyBusiness
            .filter { it.inLongAssociationInput }
    }

    val editTableProperties by lazy {
        longAssociationInputProperties
            .filter { !it.property.idProperty }
            .produceEditNullable()
    }

    val editTableRulesProperties by lazy {
        longAssociationInputProperties
            .filter { !it.property.idProperty }
    }


    val longAssociationViewProperties by lazy {
        propertyBusiness
            .filter { it.inLongAssociationView }
    }

    val viewSubTableProperties by lazy {
        longAssociationViewProperties
            .filter { !it.property.idProperty }
    }
}