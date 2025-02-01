package top.potmot.core.business.meta

import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.IdName
import top.potmot.enumeration.AssociationType
import top.potmot.error.GenerateException
import top.potmot.error.ModelException

// 不是长关联就转换成 IdView，适用于查询场景
private fun Iterable<PropertyBusiness>.`force to IdView`() = map {
    if (it is AssociationProperty) {
        it.forceToIdView()
    } else {
        it
    }
}

// 不是长关联就转换成 IdView，适用于编辑场景
private fun Iterable<PropertyBusiness>.`notLong force to IdView`() = map {
    if (it is AssociationProperty && !it.isLongAssociation) {
        it.forceToIdView()
    } else {
        it
    }
}

// 不是长关联且不是短关联视图才转换成 IdView，适用于视图场景
private fun Iterable<PropertyBusiness>.`notLong and notShortView force to IdView`() = map {
    if (it is AssociationProperty && !it.isLongAssociation && !it.isShortView) {
        it.forceToIdView()
    } else {
        it
    }
}

sealed class EntityBusiness(
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

    abstract val dto: DtoNames

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
                    path = when (this) {
                        is RootEntityBusiness -> AssociationPath(
                            rootEntity = this,
                            items = listOf(
                                AssociationPathItem(
                                    entity = this,
                                    property = it,
                                    type = AssociationPathItemType.PROPERTY,
                                )
                            )
                        )

                        is SubEntityBusiness -> path.append(
                            entity = this,
                            property = it,
                            type = AssociationPathItemType.PROPERTY,
                            isSelfAssociated = isSelfAssociated
                        )
                    },
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

    private val editLongAssociationProperties by lazy {
        associationProperties
            .filter {
                it.isLongAssociation &&
                        when (this) {
                            is RootEntityBusiness -> (canAdd && it.inInsertInput) || (canEdit && it.inUpdateInput)
                            is SubEntityBusiness -> it.inLongAssociationInput
                        }
            }
    }

    val editSubEntityMap by lazy {
        editLongAssociationProperties.associateWith {
            it.typeEntityBusiness
        }
    }

    val editSubEntities by lazy {
        editSubEntityMap.values
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
            .filter { it.inOptionView && (if (isTree) it.id != parentProperty.id else true) }
    }

    val optionLabelProperties by lazy {
        if (optionViewProperties.size > 1) {
            optionViewProperties.filter {
                !it.property.idProperty
            }
        } else {
            optionViewProperties
        }
            .`notLong and notShortView force to IdView`()
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
            .`notLong force to IdView`()
    }

    val detailViewProperties by lazy {
        properties
            .filter { it.inDetailView }
    }

    val viewFormProperties by lazy {
        detailViewProperties
            .filter { !it.property.idProperty }
            .`notLong and notShortView force to IdView`()
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
            .`force to IdView`()
    }
    val queryFormProperties by lazy {
        `queryFormProperties nullable not change`.map {
            it.toNullable()
        }
    }


    val insertInputProperties by lazy {
        properties
            .filter { it.inInsertInput }
    }
    val addFormProperties by lazy {
        insertInputProperties
            .`notLong force to IdView`()
    }


    val updateInputProperties by lazy {
        properties
            .filter { it.inUpdateInput }
    }
    val editFormProperties by lazy {
        updateInputProperties
            .`notLong force to IdView`()
    }
    val editFormNoIdProperties by lazy {
        editFormProperties
            .filter { !it.property.idProperty }
    }


    val specificationSelectPairs by lazy {
        `queryFormProperties nullable not change`
            .filterIsInstance<ForceIdViewProperty>()
            .map { it to it.selectOption }
    }
    val specificationSelects by lazy {
        specificationSelectPairs.map { it.second }
    }

    val insertSelectPairs: List<Pair<ForceIdViewProperty, SelectOption>> by lazy {
        addFormProperties
            .filterIsInstance<ForceIdViewProperty>()
            .map { it to it.selectOption } +
                addFormProperties
                    .filterIsInstance<AssociationProperty>()
                    .flatMap { it.typeEntityBusiness.subFormSelectPairs }
    }
    val insertSelects by lazy {
        insertSelectPairs.map { it.second }
    }

    val updateSelectPairs: List<Pair<ForceIdViewProperty, SelectOption>> by lazy {
        editFormProperties
            .filterIsInstance<ForceIdViewProperty>()
            .map { it to it.selectOption } +
                editFormProperties
                    .filterIsInstance<AssociationProperty>()
                    .flatMap { it.typeEntityBusiness.subFormSelectPairs }
    }
    val updateSelects: List<SelectOption> by lazy {
        updateSelectPairs.map { it.second }
    }


    val pageSelectPairs by lazy {
        val result = mutableListOf<Pair<ForceIdViewProperty, SelectOption>>()

        if (entity.canAdd) result += insertSelectPairs
        if (entity.canEdit) result += updateSelectPairs
        if (entity.canQuery) result += specificationSelectPairs

        result
            .distinctBy { it.first }
    }
    val pageSelects by lazy {
        pageSelectPairs.map { it.second }
    }
}

class RootEntityBusiness(
    entity: GenEntityBusinessView,
    entityIdMap: Map<Long, GenEntityBusinessView>,
    enumIdMap: Map<Long, EnumBusiness>,
) : EntityBusiness(
    entity,
    entityIdMap,
    enumIdMap
) {
    override val dto: DtoNames by lazy {
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
        RootEntityComponentFiles(
            table = NamePath("${name}Table", "vue", "components/$dir"),
            addForm = NamePath("${name}AddForm", "vue", "components/$dir"),
            addFormType = NamePath("${name}AddData", "ts", "components/$dir"),
            addFormDefault = NamePath("createDefault${name}", "ts", "components/$dir"),
            editForm = NamePath("${name}EditForm", "vue", "components/$dir"),
            editFormType = NamePath("${name}EditData", "ts", "components/$dir"),
            queryForm = NamePath("${name}QueryForm", "vue", "components/$dir"),
            page = NamePath("${name}Page", "vue", "pages/$dir"),
        )
    }

    val rules by lazy {
        RootEntityRuleFiles(
            addFormRules = NamePath("${name}AddFormRules", "ts", "rules/$dir"),
            editFormRules = NamePath("${name}EditFormRules", "ts", "rules/$dir"),
        )
    }
}

class SubEntityBusiness(
    val path: AssociationPath,
    val currentFkProperty: GenEntityBusinessView.TargetOf_properties?,
    entity: GenEntityBusinessView,
    entityIdMap: Map<Long, GenEntityBusinessView>,
    enumIdMap: Map<Long, EnumBusiness>,
) : EntityBusiness(
    entity,
    entityIdMap,
    enumIdMap
) {
    val shortViewProperties by lazy {
        properties
            .filter { it.inShortAssociationView }
    }


    val longAssociationInputProperties by lazy {
        properties
            .filter { it.inLongAssociationInput && it.property.id != currentFkProperty?.id }
    }
    val subEditProperties by lazy {
        longAssociationInputProperties
            .map {
                if (it.property.idProperty) {
                    it.toNullable()
                } else it
            }
            .`notLong force to IdView`()
    }
    val subEditNoIdProperties by lazy {
        subEditProperties
            .filter { !it.property.idProperty }
    }


    val longAssociationViewProperties by lazy {
        properties
            .filter { it.inLongAssociationView && it.property.id != currentFkProperty?.id }
    }
    val subViewProperties by lazy {
        longAssociationViewProperties
            .filter { !it.property.idProperty }
            .`notLong and notShortView force to IdView`()
    }

    val subFormSelectPairs: List<Pair<ForceIdViewProperty, SelectOption>> by lazy {
        subEditProperties
            .filterIsInstance<ForceIdViewProperty>()
            .map { it to it.selectOption } +
                subEditProperties
                    .filterIsInstance<AssociationProperty>()
                    .filter { it.inLongAssociationInput }
                    .flatMap { it.typeEntityBusiness.subFormSelectPairs }
    }
    val subFormSelects by lazy {
        subFormSelectPairs.map { it.second }
    }

    override val dto by lazy {
        val rootEntityName = path.rootEntity.name
        val propertyNames = path.propertyItems.map { it.property.name }.joinToString("") {
            "_TargetOf_$it"
        }

        DtoNames(
            listView = "${rootEntityName}ListView$propertyNames",
            treeView = "${rootEntityName}TreeView$propertyNames",
            detailView = "${rootEntityName}DetailView$propertyNames",
            updateFillView = "${rootEntityName}UpdateFillView$propertyNames",
            updateInput = "${rootEntityName}UpdateInput$propertyNames",
            insertInput = "${rootEntityName}InsertInput$propertyNames",
            spec = "${rootEntityName}Spec$propertyNames",
            optionView = "${name}OptionView",
        )
    }

    val components by lazy {
        val rootEntity = path.rootEntity

        val rootEntityName = rootEntity.name
        val propertyNames =
            path.propertyItems.map { it.property.name.replaceFirstChar { it.uppercaseChar() } }.joinToString("")
        val currentName = rootEntityName + propertyNames

        val rootEntityDir = rootEntity.dir
        val propertyDirs =
            path.propertyItems.map { it.property.name }.joinToString("/")
        val currentPath = "$rootEntityDir/$propertyDirs"

        SubEntityComponentFiles(
            subForm = NamePath("${currentName}SubForm", "vue", "components/$currentPath"),
            subFormType = NamePath("${currentName}SubFormData", "ts", "components/$currentPath"),
            subFormDefault = NamePath("createDefault${currentName}", "ts", "components/$currentPath"),
            editTable = NamePath("${currentName}EditTable", "vue", "components/$currentPath"),
            editTableType = NamePath("${currentName}EditTableData", "ts", "components/$currentPath"),
            idSelect = NamePath("${name}IdSelect", "vue", "components/$dir"),
            idMultiSelect = NamePath("${name}IdMultiSelect", "vue", "components/$dir"),
        )
    }

    val rules by lazy {
        val rootEntity = path.rootEntity
        val rootEntityName = rootEntity.name
        val propertyNames =
            path.propertyItems.joinToString("") { item -> item.property.name.replaceFirstChar { it.uppercaseChar() } }
        val currentName = rootEntityName + propertyNames

        SubEntityRuleFiles(
            subFormRules = NamePath("${currentName}SubFormRules", "ts", "rules/$dir"),
            editTableRules = NamePath("${currentName}EditTableRules", "ts", "rules/$dir"),
        )
    }
}
