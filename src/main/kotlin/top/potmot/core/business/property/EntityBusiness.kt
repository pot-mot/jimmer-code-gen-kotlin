package top.potmot.core.business.property

import top.potmot.core.business.utils.entity.idProperty
import top.potmot.core.business.utils.mark.apiServiceName
import top.potmot.core.business.utils.mark.components
import top.potmot.core.business.utils.mark.dir
import top.potmot.core.business.utils.mark.dto
import top.potmot.core.business.utils.mark.enums
import top.potmot.core.business.utils.mark.lowerName
import top.potmot.core.business.utils.mark.packages
import top.potmot.core.business.utils.mark.permissionStrList
import top.potmot.core.business.utils.mark.permissions
import top.potmot.core.business.utils.mark.requestPath
import top.potmot.core.business.utils.mark.rules
import top.potmot.core.business.utils.mark.serviceFilePath
import top.potmot.core.business.utils.mark.serviceName
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.IdName
import top.potmot.enumeration.AssociationType
import top.potmot.enumeration.targetOneAssociationTypes
import top.potmot.error.ModelException

class EntityBusiness(
    val entity: GenEntityBusinessView,
    entityIdMap: Map<Long, GenEntityBusinessView>,
) {
    val id = entity.id

    val name = entity.name

    val lowerName = entity.lowerName

    val comment = entity.comment

    val canAdd = entity.canAdd

    val canEdit = entity.canEdit

    val canDelete = entity.canDelete

    val canQuery = entity.canQuery

    val hasPage = entity.hasPage

    val packagePath = entity.packagePath

    val dto by lazy {
        entity.dto
    }

    val dir by lazy {
        entity.dir
    }

    val packages by lazy {
        entity.packages
    }

    val components by lazy {
        entity.components
    }

    val rules by lazy {
        entity.rules
    }

    val permissions by lazy {
        entity.permissions
    }

    val permissionStrList by lazy {
        entity.permissionStrList
    }

    val enums by lazy {
        entity.enums
    }

    val serviceFilePath by lazy {
        entity.serviceFilePath
    }

    val requestPath by lazy {
        entity.requestPath
    }

    val serviceName by lazy {
        entity.serviceName
    }

    val apiServiceName by lazy {
        entity.apiServiceName
    }

    val properties = entity.properties

    val idProperty by lazy {
        entity.idProperty
    }


    val propertyBusiness by lazy {
        entity.getPropertyBusiness(entityIdMap)
    }

    val propertyBusinessIdMap by lazy {
        val map = mutableMapOf<Long, PropertyBusiness>()

        for (propertyBusiness in propertyBusiness) {
            map[propertyBusiness.id] = propertyBusiness
            if (propertyBusiness is AssociationProperty && propertyBusiness.idView != null) {
                map[propertyBusiness.idView.id] = propertyBusiness
            }
        }

        map
    }

    // 关联属性
    val associationPropertyBusiness by lazy {
        propertyBusiness.filterIsInstance<AssociationProperty>()
    }


    val scalarPropertyBusiness by lazy {
        propertyBusiness
            .filter {
                !it.property.idProperty && !it.property.logicalDelete && it.property.associationType == null
            }
    }


    val noColumnPropertyBusiness by lazy {
        propertyBusiness
            .filter {
                it.property.column == null
            }
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
        parentPropertyBusiness.forceToIdViewProperty()
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

    val childIdsProperty by lazy {
        childrenPropertyBusiness.forceToIdViewProperty()
    }


    val specificationSelectPropertyBusiness by lazy {
        associationPropertyBusiness
            .filter { it.inSpecification && it.associationType in targetOneAssociationTypes }
            .forceIdView()
    }

    val insertSelectPropertyBusiness by lazy {
        associationPropertyBusiness
            .filter { it.inInsertInput }
            .forceIdView()
    }

    val updateSelectPropertyBusiness by lazy {
        associationPropertyBusiness
            .filter { it.inUpdateInput }
            .forceIdView()
    }

    val pageSelectPropertyBusiness by lazy {
        associationPropertyBusiness
            .filter {
                (it.inSpecification && it.associationType in targetOneAssociationTypes) || it.inInsertInput || it.inUpdateInput
            }
            .forceIdView()
    }

    val editTableSelectPropertyBusiness by lazy {
        associationPropertyBusiness
            .filter { it.inLongAssociationInput }
            .forceIdView()
    }


    val optionViewPropertyBusiness by lazy {
        propertyBusiness
            .filter { it.inOptionView }
    }

    val optionLabelProperties by lazy {
        val optionViewProperties = optionViewPropertyBusiness.selfOrForceIdViewProperties()

        if (optionViewProperties.size > 1) {
            optionViewProperties.filter {
                !it.idProperty && it.typeEntityId != entity.id
            }
        } else {
            optionViewProperties
        }
    }


    val listViewPropertyBusiness by lazy {
        propertyBusiness
            .filter { it.inListView }
    }

    val tablePropertyBusiness by lazy {
        listViewPropertyBusiness
            .filter {
                val notParentProperty =
                    if (isTree) {
                        it.property.id != parentProperty.id && it.property.id != parentIdProperty.id
                    } else {
                        true
                    }

                !it.property.idProperty && notParentProperty
            }
            .selfOrForceIdView()
    }

    val detailViewPropertyBusiness by lazy {
        propertyBusiness
            .filter { it.inDetailView }
    }

    val viewFormPropertyBusiness by lazy {
        detailViewPropertyBusiness
            .filter { !it.property.idProperty }
            .selfOrForceIdView()
    }


    val specificationPropertyBusiness by lazy {
        propertyBusiness
            .filter { it.inSpecification }
    }

    val queryPropertyBusiness by lazy {
        specificationPropertyBusiness
            .filter {
                !it.property.idProperty &&
                        (it.property.associationType == null || it.property.associationType in targetOneAssociationTypes)
            }
            .map {
                when (it) {
                    is CommonProperty -> it.copy(property = it.property.copy(typeNotNull = false))
                    is AssociationProperty -> it.copy(
                        property = it.property.copy(typeNotNull = false),
                        idView = it.idView?.copy(typeNotNull = false)
                    )
                    is ForceIdViewProperty -> it.copy(property = it.property.copy(typeNotNull = false))
                }
            }
            .selfOrForceIdView()
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


    val insertInputPropertyBusiness by lazy {
        propertyBusiness
            .filter { it.inInsertInput }
    }

    val addFormPropertyBusiness by lazy {
        insertInputPropertyBusiness
            .produceEditNullable()
            .selfOrForceIdView()
    }

    val addFormEditNullablePropertyBusiness by lazy {
        insertInputPropertyBusiness
            .filter { it.isEditNullable }
            .selfOrForceIdView()
    }

    val addFormRulesPropertyBusiness by lazy {
        insertInputPropertyBusiness
            .selfOrForceIdView()
    }


    val updateInputPropertyBusiness by lazy {
        propertyBusiness
            .filter { it.inUpdateInput }
    }

    val editFormPropertyBusiness by lazy {
        updateInputPropertyBusiness
            .filter { !it.property.idProperty }
            .selfOrForceIdView()
    }

    val editFormRulesPropertyBusiness by lazy {
        updateInputPropertyBusiness
            .filter { !it.property.idProperty }
            .selfOrForceIdView()
    }


    val shortViewPropertyBusiness by lazy {
        propertyBusiness
            .filter { it.inShortAssociationView }
    }


    val longAssociationInputPropertyBusiness by lazy {
        propertyBusiness
            .filter { it.inLongAssociationInput }
    }

    val editTablePropertyBusiness by lazy {
        longAssociationInputPropertyBusiness
            .filter { !it.property.idProperty }
            .produceEditNullable()
            .selfOrForceIdView()
    }

    val editTableRulesPropertyBusiness by lazy {
        longAssociationInputPropertyBusiness
            .filter { !it.property.idProperty }
            .selfOrForceIdView()
    }


    val longAssociationViewPropertyBusiness by lazy {
        propertyBusiness
            .filter { it.inLongAssociationView }
    }

    val viewSubTablePropertyBusiness by lazy {
        longAssociationViewPropertyBusiness
            .filter { !it.property.idProperty }
            .selfOrForceIdView()
    }
}