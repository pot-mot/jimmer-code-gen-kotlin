package top.potmot.core.business.meta

import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.IdName
import top.potmot.enumeration.AssociationType
import top.potmot.error.GenerateException
import top.potmot.error.ModelException

data class EntityBusiness(
    val path: AssociationPath,
    val entity: GenEntityBusinessView,
    val entityIdMap: Map<Long, GenEntityBusinessView>,
    val enumIdMap: Map<Long, EnumBusiness>,
) {
    val id = entity.id

    val name = entity.name

    val lowerName = entity.name.replaceFirstChar { it.lowercaseChar() }

    val comment = entity.comment

    val canAdd = entity.canAdd

    val canEdit = entity.canEdit

    val canDelete = entity.canDelete

    val canQuery = entity.canQuery

    val hasPage = entity.hasPage

    val packagePath = entity.packagePath

    val dir = lowerName

    val requestPath = lowerName

    val packages by lazy {
        Packages(
            entity = packagePath,
            service = packagePath.replaceAfterLast(".", "service"),
            utils = packagePath.replaceAfterLast(".", "utils"),
            exception = packagePath.replaceAfterLast(".", "exception"),
            dto = "${packagePath}.dto",
        )
    }

    val serviceFilePath by lazy {
        packages.service.replace(".", "/")
    }

    val dto by lazy {
        DtoNames(
            listView = "${name}ListView",
            treeView = "${name}TreeView",
            detailView = "${name}DetailView",
            updateFillView = "${name}UpdateFillView",
            updateInput = "${name}UpdateInput",
            insertInput = "${name}InsertInput",
            spec = "${name}Spec",
            optionView = "${name}OptionView",
        )
    }

    val components by lazy {
        EntityComponentNames(
            table = "${name}Table",
            addForm = "${name}AddForm",
            editForm = "${name}EditForm",
            queryForm = "${name}QueryForm",
            page = "${name}Page",
            idSelect = "${name}IdSelect",
            idMultiSelect = "${name}IdMultiSelect",
            editTable = "${name}EditTable",
        )
    }

    val rules by lazy {
        EntityRulesNames(
            addFormRules = "${name}AddFormRules",
            editFormRules = "${name}EditFormRules",
            editTableRules = "${name}EditTableRules",
        )
    }

    val permissions by lazy {
        EntityPermissions(
            get = "$lowerName:get",
            list = "$lowerName:list",
            select = "$lowerName:select",
            insert = "$lowerName:insert",
            update = "$lowerName:update",
            delete = "$lowerName:delete",
            menu = "$lowerName:menu",
        )
    }

    val permissionStrList by lazy {
        listOfNotNull(
            permissions.get,
            permissions.list,
            permissions.select,
            if (hasPage) permissions.menu else null,
            if (canAdd) permissions.insert else null,
            if (canEdit) permissions.update else null,
            if (canDelete) permissions.delete else null,
        )
    }

    val enumProperties by lazy {
        properties.filterIsInstance<EnumProperty>()
    }

    val enums by lazy {
        enumProperties.map { it.enum }
    }

    val serviceName = "${name}Service"

    val apiServiceName = "${lowerName}Service"

    val addFormType = "${name}AddFormType"

    val addFormDefault = "createDefault${name}"


    val isSelfAssociated by lazy {
        entity.properties.any {
            it.typeEntityId == entity.id
        }
    }

    val properties by lazy {
        val properties = entity.properties
        val result = mutableListOf<PropertyBusiness>()
        val idViewTargetMap = properties.filter { it.idView }.associateBy { it.idViewTarget }

        properties.forEach {
            if (it.associationType == null) {
                if (it.enumId == null) {
                    result += CommonProperty(this, it)
                } else {
                    val enum = enumIdMap[it.enumId] ?: throw GenerateException.EnumNotFound(
                        message = "Enum [${it.enumId}] Not Found",
                        enumId = it.enumId
                    )
                    result += EnumProperty(this, it, enum)
                }
            } else if (!it.idView) {
                result += AssociationProperty(
                    path = path.append(
                        entity = IdName(id, name),
                        property = IdName(it.id, it.name),
                        type = AssociationPathItemType.PROPERTY,
                        isSelfAssociated = isSelfAssociated
                    ),
                    entityBusiness = this,
                    property = it,
                    idView = idViewTargetMap[it.name],
                    typeEntity = entityIdMap[it.typeEntityId] ?: throw GenerateException.EntityNotFound(
                        message = "Entity [${it.typeEntityId}] Not Found",
                        entityId = it.typeEntityId
                    ),
                    associationType = it.associationType
                )
            }
        }

        result
    }

    val propertyIdMap by lazy {
        val map = mutableMapOf<Long, PropertyBusiness>()

        for (propertyBusiness in properties) {
            map[propertyBusiness.id] = propertyBusiness
            if (propertyBusiness is AssociationProperty && propertyBusiness.idView != null) {
                map[propertyBusiness.idView.id] = propertyBusiness
            }
        }

        map
    }


    val idProperties by lazy {
        properties.filter { it.property.idProperty }
    }

    val idProperty by lazy {
        if (idProperties.isEmpty()) {
            throw ModelException.idPropertyNotFound(
                "entityName: $name",
                entity = IdName(id, name)
            )
        } else if (idProperties.size > 1) {
            throw ModelException.idPropertyMoreThanOne(
                "entityName: $name",
                entity = IdName(id, name),
                idProperties = idProperties.map { IdName(it.id, it.name) }
            )
        } else {
            idProperties[0]
        }
    }

    // 关联属性
    val associationProperties by lazy {
        properties.filterIsInstance<AssociationProperty>()
    }

    val longAssociationProperties by lazy {
        associationProperties.filter { it.isLongAssociation }
    }

    val longAssociationPropertyEntityMap by lazy {
        longAssociationProperties.associateWith {
            it.typeEntityBusiness
        }
    }
    
    val longAssociationEntities by lazy {
        longAssociationPropertyEntityMap.values
    }


    val scalarProperties by lazy {
        properties
            .filter {
                !it.property.idProperty && !it.property.logicalDelete && it.property.column != null && it.property.associationType == null
            }
    }


    val noColumnProperties by lazy {
        properties
            .filter {
                it.property.column == null
            }
    }


    val isTree by lazy {
        associationProperties.any {
            it.property.typeEntityId == entity.id && (it.associationType == AssociationType.MANY_TO_ONE || it.associationType == AssociationType.ONE_TO_MANY)
        }
    }

    val parentProperty: AssociationProperty by lazy {
        associationProperties.firstOrNull {
            it.typeEntity.id == entity.id && !it.property.listType
        }
            ?: throw ModelException.treeEntityCannotFoundParentProperty(
                "Tree Entity [${entity.name}] cannot found Parent Property",
                entity = IdName(entity.id, entity.name),
                selfAssociationProperties = entity.properties.filter { it.typeEntityId == entity.id }
                    .map { IdName(it.id, it.name) }
            )
    }

    val parentIdProperty by lazy {
        parentProperty.forceToIdView()
    }


    val childrenProperty: AssociationProperty by lazy {
        associationProperties.firstOrNull {
            it.typeEntity.id == entity.id && it.property.listType
        }
            ?: throw ModelException.treeEntityCannotFoundParentProperty(
                "Tree Entity [${entity.name}] cannot found Children Property",
                entity = IdName(entity.id, entity.name),
                selfAssociationProperties = entity.properties.filter { it.typeEntityId == entity.id }
                    .map { IdName(it.id, it.name) }
            )
    }

    val childIdsProperty by lazy {
        childrenProperty.forceToIdView()
    }


    val optionViewProperties by lazy {
        properties
            .filter { it.inOptionView }
    }

    val optionLabelProperties by lazy {
        if (optionViewProperties.size > 1) {
            optionViewProperties.filter {
                !it.property.idProperty && it.property.typeEntityId != entity.id
            }
        } else {
            optionViewProperties
        }
    }


    val listViewProperties by lazy {
        properties
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
            .selfOrForceIdView()
    }

    val detailViewProperties by lazy {
        properties
            .filter { it.inDetailView }
    }

    val viewFormProperties by lazy {
        detailViewProperties
            .filter { !it.property.idProperty }
            .selfOrForceIdView()
    }


    val specificationProperties by lazy {
        properties
            .filter { it.inSpecification }
    }

    private val `queryFormProperties nullable not change` by lazy {
        specificationProperties
            .filter {
                !it.property.idProperty &&
                        (it.property.associationType == null || it.property.associationType!!.isTargetOne)
            }
            .selfOrForceIdView()
    }

    val queryFormProperties by lazy {
        `queryFormProperties nullable not change`.map {
            when (it) {
                is CommonProperty -> it.copy(property = it.property.copy(typeNotNull = false))
                is EnumProperty -> it.copy(property = it.property.copy(typeNotNull = false))
                is AssociationProperty -> it.copy(
                    property = it.property.copy(typeNotNull = false),
                    idView = it.idView?.copy(typeNotNull = false)
                )

                is ForceIdViewProperty -> it.copy(property = it.property.copy(typeNotNull = false))
            }
        }
    }


    private val PropertyBusiness.isEditNullable
        get() = this is AssociationProperty && associationType.isTargetOne && property.typeNotNull

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
        properties
            .filter { it.inInsertInput }
    }

    private val `addFormProperties nullable not change` by lazy {
        insertInputProperties
            .selfOrForceIdView()
    }

    val addFormProperties by lazy {
        insertInputProperties
            .produceEditNullable()
            .selfOrForceIdView()
    }

    val addFormEditNullableProperties by lazy {
        insertInputProperties
            .filter { it.isEditNullable }
            .selfOrForceIdView()
    }

    val addFormRulesProperties by lazy {
        `addFormProperties nullable not change`
    }


    val updateInputProperties by lazy {
        properties
            .filter { it.inUpdateInput }
    }

    val editFormProperties by lazy {
        updateInputProperties
            .filter { !it.property.idProperty }
            .selfOrForceIdView()
    }

    val editFormRulesProperties by lazy {
        updateInputProperties
            .filter { !it.property.idProperty }
            .selfOrForceIdView()
    }


    val shortViewProperties by lazy {
        properties
            .filter { it.inShortAssociationView }
    }


    val longAssociationInputProperties by lazy {
        properties
            .filter { it.inLongAssociationInput }
    }

    private val `subFormProperties nullable not change` by lazy {
        longAssociationInputProperties
            .filter { !it.property.idProperty }
            .selfOrForceIdView()
    }

    val subFormProperties by lazy {
        longAssociationInputProperties
            .filter { !it.property.idProperty }
            .produceEditNullable()
            .selfOrForceIdView()
    }

    val subFormRulesProperties by lazy {
        `subFormProperties nullable not change`
    }


    val longAssociationViewProperties by lazy {
        properties
            .filter { it.inLongAssociationView }
    }

    val viewSubTableProperties by lazy {
        longAssociationViewProperties
            .filter { !it.property.idProperty }
            .selfOrForceIdView()
    }



    val subFormSelectProperties: List<ForceIdViewProperty> by lazy {
        `subFormProperties nullable not change`
            .filterIsInstance<ForceIdViewProperty>() +
                longAssociationEntities
                    .flatMap { it.subFormSelectProperties }
    }

    val specificationSelectProperties: List<ForceIdViewProperty> by lazy {
        `queryFormProperties nullable not change`
            .filterIsInstance<ForceIdViewProperty>() +
                longAssociationEntities
                    .flatMap { it.subFormSelectProperties }
    }

    val insertSelectProperties: List<ForceIdViewProperty> by lazy {
        `addFormProperties nullable not change`
            .filterIsInstance<ForceIdViewProperty>() +
                longAssociationEntities
                    .flatMap { it.subFormSelectProperties }
    }

    val updateSelectProperties: List<ForceIdViewProperty> by lazy {
        editFormProperties
            .filterIsInstance<ForceIdViewProperty>() +
                longAssociationEntities
                    .flatMap { it.subFormSelectProperties }
    }

    val pageSelectProperties: List<ForceIdViewProperty> by lazy {
        (insertSelectProperties + updateSelectProperties + specificationSelectProperties)
            .distinctBy { it.property.id } +
                longAssociationEntities
                    .flatMap { it.subFormSelectProperties }
    }
}