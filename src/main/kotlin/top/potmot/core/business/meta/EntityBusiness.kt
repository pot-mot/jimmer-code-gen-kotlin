package top.potmot.core.business.meta

import top.potmot.core.config.getContextOrGlobal
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.IdName
import top.potmot.enumeration.AssociationType
import top.potmot.error.GenerateException
import top.potmot.error.ModelException

// 不是关联就转换成 IdView，适用于查询场景
private fun Iterable<PropertyBusiness>.`force to IdView`() = map {
    if (it is AssociationProperty) {
        it.forceIdView
    } else {
        it
    }
}

// 不是长关联就转换成 IdView，适用于编辑场景
private fun Iterable<PropertyBusiness>.`notLong force to IdView`() = map {
    if (it is AssociationProperty && !it.isLongAssociation) {
        it.forceIdView
    } else {
        it
    }
}

// 不是长关联且不是短关联视图才转换成 IdView，适用于视图场景
private fun Iterable<PropertyBusiness>.`notLong and notShortView force to IdView`() = map {
    if (it is AssociationProperty && !it.isLongAssociation && !it.isShortView) {
        it.forceIdView
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

    val pageCanAdd = entity.hasPage && entity.canAdd && entity.pageCanAdd

    val pageCanEdit = entity.hasPage && entity.canEdit && entity.pageCanEdit

    val pageCanViewDetail = entity.hasPage && entity.pageCanViewDetail

    val pageCanDelete = entity.hasPage && entity.canDelete && entity.pageCanDelete

    val pageCanQuery = entity.hasPage && entity.canQuery && entity.pageCanQuery

    val queryByPage = entity.queryByPage

    val requestPath = lowerName

    val packagePath = entity.packagePath

    val subGroup = entity.subGroup

    val subPackagePath = entity.subGroup?.subPackagePath

    val dir = buildString {
        if (!subPackagePath.isNullOrBlank()) {
            append("${subPackagePath.replace(".", "/")}/")
        }
        append(lowerName)
    }

    val packages by lazy {
        val basePackagePath = getContextOrGlobal().packagePath
        val entityIndex = packagePath.lastIndexOf(".entity")

        if (entityIndex == -1) {
            Packages(
                base = basePackagePath,
                entity = packagePath,
                service = "$basePackagePath.service",
                exception = "$basePackagePath.exception",
                dto = "${packagePath}.dto",
            )
        } else {
            Packages(
                base = basePackagePath,
                entity = packagePath,
                service = packagePath.replace(".entity", ".service"),
                exception = "$basePackagePath.exception",
                dto = "${packagePath}.dto",
            )
        }

    }

    abstract val dto: DtoNames

    val permissionBase = buildString {
        if (!subPackagePath.isNullOrBlank()) {
            append("${subPackagePath.replace(".", ":")}:")
        }
        append(lowerName)
    }

    val permissions by lazy {
        EntityPermissions(
            role = "${lowerName}_manager",
            get = "$permissionBase:get",
            list = "$permissionBase:list",
            select = "$permissionBase:select",
            insert = "$permissionBase:insert",
            update = "$permissionBase:update",
            delete = "$permissionBase:delete",
            menu = "$permissionBase:menu",
        )
    }

    val allPermissionStrList by lazy {
        listOf(
            permissions.get,
            permissions.list,
            permissions.select,
            permissions.menu,
            permissions.insert,
            permissions.update,
            permissions.delete,
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


    val serviceName = "${name}Service"

    val serviceFilePath by lazy {
        packages.service.replace(".", "/")
    }

    val testName = "${name}Test"

    val testFilePath by lazy {
        packagePath.replace(".", "/")
    }

    val serviceLowerName = "${lowerName}Service"

    val apiServiceName = serviceLowerName


    val isSelfAssociated by lazy {
        entity.properties.any {
            it.typeEntityId == entity.id
        }
    }

    val properties by lazy {
        val properties = entity.properties
        val result = mutableListOf<PropertyBusiness>()
        val idViewTargetMap = properties
            .filter { it.idView && it.idViewTarget != null }
            .associateBy { it.idViewTarget }

        properties.forEach {
            if (it.typeEntityId != null) {
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
                        ?: if (it.listType) AssociationType.MANY_TO_MANY else AssociationType.MANY_TO_ONE
                )
            } else if (it.associationType == null || !it.idView) {
                if (it.enumId != null) {
                    val enum = enumIdMap[it.enumId] ?: throw GenerateException.EnumNotFound(
                        message = "Enum [${it.enumId}] Not Found",
                        enumId = it.enumId
                    )
                    result += EnumProperty(this, it, enum)
                } else {
                    result += CommonProperty(this, it)
                }
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


    val existValidItems: List<ExistValidItem> by lazy {
        if (entity.indexes == null) emptyList()
        else entity.indexes
            .filter { it.uniqueIndex }
            .mapNotNull { index ->
                val indexPropertyIds = index.columns.flatMap { column ->
                    column.properties.map { it.id }
                }

                val validProperties = mutableListOf<PropertyBusiness>()

                indexPropertyIds.forEach { propertyId ->
                    val propertyBusiness = propertyIdMap[propertyId]
                        ?: throw ModelException.indexRefPropertyNotFound(
                            entity = IdName(entity.id, entity.name),
                            entityProperties = properties.map { IdName(it.id, it.name) },
                            index = IdName(index.id, index.name),
                            indexPropertyIds = index.columns.flatMap { column -> column.properties.map { it.id } },
                            notFoundPropertyId = propertyId
                        )

                    val property = propertyBusiness.property
                    if (property.listType) {
                        throw ModelException.indexRefPropertyCannotBeList(
                            entity = IdName(entity.id, entity.name),
                            entityProperties = properties.map { IdName(it.id, it.name) },
                            index = IdName(index.id, index.name),
                            indexPropertyIds = index.columns.flatMap { column -> column.properties.map { it.id } },
                            listProperty = IdName(property.id, property.name)
                        )
                    }

                    validProperties += propertyBusiness
                }

                if (scalarProperties.isEmpty() && associationProperties.isEmpty()) {
                    return@mapNotNull null
                }

                val producedValidProperties = validProperties.distinctBy { it.id }.sortedBy { it.property.orderKey }
                val upperNameJoin = producedValidProperties.joinToString("And") { it.upperName }

                ExistValidItem(
                    "${entity.name}ExistBy${upperNameJoin}Spec",
                    "existBy$upperNameJoin",
                    producedValidProperties
                )
            }
            .distinctBy {
                it.properties.toString()
            }
    }

    private val idProperties by lazy {
        properties.filter { it.property.idProperty }
    }

    val idProperty by lazy {
        if (idProperties.isEmpty()) {
            throw ModelException.idPropertyNotFound(
                "entity [$name]($id) id not found",
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
        parentProperty.forceIdView
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

    open val viewTableProperties by lazy {
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
            .`notLong and notShortView force to IdView`()
    }

    val detailViewProperties by lazy {
        properties
            .filter { it.inDetailView }
    }

    open val viewFormProperties by lazy {
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
                !it.property.idProperty
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
        val suffix = getContextOrGlobal().viewType.suffix
        RootEntityComponentFiles(
            table = NamePath("${name}Table", suffix, "components/$dir"),
            viewForm = NamePath("${name}View", suffix, "components/$dir"),
            addForm = NamePath("${name}AddForm", suffix, "components/$dir"),
            addFormType = NamePath("${name}AddData", "ts", "components/$dir"),
            addFormDefault = NamePath("createDefault${name}", "ts", "components/$dir"),
            editForm = NamePath("${name}EditForm", suffix, "components/$dir"),
            editFormType = NamePath("${name}EditData", "ts", "components/$dir"),
            queryForm = NamePath("${name}QueryForm", suffix, "components/$dir"),
            page = NamePath("${name}Page", suffix, buildString {
                append("pages")
                if (!subPackagePath.isNullOrBlank()) {
                    append("/")
                    append(subPackagePath.replace(".", "/"))
                }
            }),
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
    val asRoot: RootEntityBusiness by lazy {
        RootEntityBusiness(
            entity,
            entityIdMap,
            enumIdMap
        )
    }

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
    private val subViewProperties by lazy {
        longAssociationViewProperties
            .filter { !it.property.idProperty }
            .`notLong and notShortView force to IdView`()
    }
    override val viewFormProperties by lazy {
        subViewProperties
    }
    override val viewTableProperties by lazy {
        subViewProperties
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
        val suffix = getContextOrGlobal().viewType.suffix

        val rootEntity = path.rootEntity

        val currentName = (path.propertyItems.lastOrNull()?.property)?.let {
            it.name.replaceFirstChar { c -> c.uppercaseChar() }
        } ?: throw ModelException.subEntityNoCurrentPath(
            "Entity [${entity.name}] No current Path: [\n" +
                    "\t${path.propertyItems.joinToString("\n\t") { it.property.name }}\n" +
                    "]",
            entity = IdName(entity.id, entity.name),
            pathProperties = path.propertyItems.map { IdName(it.property.id, it.property.name) }
        )

        val rootEntityDir = rootEntity.dir
        val propertyDirs =
            path.propertyItems.joinToString("/") { it.property.name }
        val currentPath = "$rootEntityDir/$propertyDirs"

        SubEntityComponentFiles(
            viewForm = NamePath("${currentName}View", suffix, "components/$currentPath"),
            editForm = NamePath("${currentName}Form", suffix, "components/$currentPath"),
            editFormType = NamePath("${currentName}FormData", "ts", "components/$currentPath"),
            editFormDefault = NamePath("createDefault${currentName}", "ts", "components/$currentPath"),
            viewTable = NamePath("${currentName}Table", suffix, "components/$currentPath"),
            editTable = NamePath("${currentName}EditTable", suffix, "components/$currentPath"),
            editTableType = NamePath("${currentName}EditTableData", "ts", "components/$currentPath"),
            idSelect = NamePath("${name}IdSelect", suffix, "components/$dir"),
            idMultiSelect = NamePath("${name}IdMultiSelect", suffix, "components/$dir"),
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
