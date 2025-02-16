package top.potmot.core.entity.config

import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.transaction.support.TransactionTemplate
import top.potmot.entity.GenEntity
import top.potmot.entity.GenEnum
import top.potmot.entity.GenTable
import top.potmot.entity.dto.GenEntityConfigInput
import top.potmot.entity.dto.GenEntityModelView
import top.potmot.entity.dto.GenEnumModelBusinessFillView
import top.potmot.entity.dto.GenPropertyEntityConfigInput
import top.potmot.entity.dto.GenPropertyModelView
import top.potmot.entity.dto.GenTableModelBusinessFillView
import top.potmot.entity.dto.IdName
import top.potmot.entity.modelId
import top.potmot.error.ModelBusinessInputException

/**
 * 实体 - 用于模型业务配置的输入
 * 将直接被用于 save
 */
data class EntityModelBusinessInput(
    /**
     * 实体业务配置输入，仅包含特定的业务配置项
     */
    val entity: GenEntityConfigInput,

    /**
     * 属性业务配置输入，具有全标量字段，用于编辑无需考虑关联的非转换属性
     */
    val properties: List<GenPropertyEntityConfigInput>,
)

/**
 * 实体 - 用于模型业务配置的输出
 * 同时也将在 JSON 保存时作为输入对象，此时将被被转换为 EntityModelBusinessInput
 */
data class EntityModelBusinessView(
    /**
     * 表直接转换得到的实体
     */
    val tableConvertedEntity: GenEntityModelView,

    /**
     * 其余非转换属性
     */
    val otherProperties: List<GenPropertyModelView>,
)

@Throws(
    ModelBusinessInputException.PropertyCannotMatchColumn::class,
    ModelBusinessInputException.PropertyCannotRematchOldProperty::class,
    ModelBusinessInputException.PropertyMatchedMoreThanOneOldProperty::class,
)
private fun GenEntityModelView.toConfigInput(
    id: Long,
    columnNameMap: Map<String, GenTableModelBusinessFillView.TargetOf_columns>,
) = GenEntityConfigInput(
    id = id,
    name = name,
    overwriteName = overwriteName,
    comment = comment,
    overwriteComment = overwriteComment,
    remark = remark,
    otherAnnotation = otherAnnotation,
    canAdd = canAdd,
    canEdit = canEdit,
    canQuery = canQuery,
    canDelete = canDelete,
    hasPage = hasPage,
    pageCanAdd = pageCanAdd,
    pageCanEdit = pageCanEdit,
    pageCanViewDetail = pageCanViewDetail,
    pageCanQuery = pageCanQuery,
    pageCanDelete = pageCanDelete,
    queryByPage = queryByPage,
    properties = properties.map { property ->
        val column = columnNameMap[property.columnName]
            ?: throw ModelBusinessInputException.propertyCannotMatchColumn(
                "Property [${property.name}] cannot match column",
                entity = IdName(id, name),
                propertyName = property.name,
            )

        val matchProperties = column.properties.filter {
            (if (it.overwriteName || property.overwriteName) true else it.name == property.name)
                    && it.type == property.type
                    && it.typeNotNull == property.typeNotNull
                    && it.typeTable?.name == property.typeTable?.name
                    && it.associationType == property.associationType
                    && it.joinColumnMetas.toString() == property.joinColumnMetas.toString()
                    && it.joinTableMeta.toString() == property.joinTableMeta.toString()
        }

        if (matchProperties.isEmpty())
            throw ModelBusinessInputException.propertyCannotRematchOldProperty(
                "Property [${property.name}] matched column [${column.name}] has no properties",
                entity = IdName(id, name),
                propertyName = property.name,
                matchedColumn = IdName(column.id, column.name)
            )

        if (matchProperties.size > 1)
            throw ModelBusinessInputException.propertyMatchedMoreThanOneOldProperty(
                "Property [${property.name}] matched column [${column.name}] has more than one matched properties:" +
                        matchProperties.joinToString(", ") { "[${property.name}]" },
                entity = IdName(id, name),
                propertyName = property.name,
                matchedColumn = IdName(column.id, column.name),
                matchedProperties = matchProperties.map { IdName(it.id, it.name) }
            )

        val matchProperty = matchProperties[0]

        GenEntityConfigInput.TargetOf_properties(
            id = matchProperty.id,
            name = property.name,
            overwriteName = property.overwriteName,
            comment = property.comment,
            overwriteComment = property.overwriteComment,
            remark = property.remark,

            generatedId = property.generatedId,
            generatedIdAnnotation = property.generatedIdAnnotation,

            logicalDeletedAnnotation = property.logicalDeletedAnnotation,

            otherAnnotation = property.otherAnnotation,

            orderKey = property.orderKey,

            longAssociation = property.longAssociation,
            inListView = property.inListView,
            inDetailView = property.inDetailView,
            inInsertInput = property.inInsertInput,
            inUpdateInput = property.inUpdateInput,
            inOptionView = property.inOptionView,
            inSpecification = property.inSpecification,
            inShortAssociationView = property.inShortAssociationView,
            inLongAssociationInput = property.inLongAssociationInput,
            inLongAssociationView = property.inLongAssociationView,
            specialFormType = property.specialFormType,
        )
    }
)

private fun GenPropertyModelView.toConfigInput(
    entityNameTableIdMap: Map<String, Long>,
    enumNameIdMap: Map<String, Long>,
) = GenPropertyEntityConfigInput(
    name = name,
    overwriteName = overwriteName,
    comment = comment,
    overwriteComment = overwriteComment,
    remark = remark,
    body = body,

    type = type,
    listType = listType,
    typeNotNull = typeNotNull,

    otherAnnotation = otherAnnotation,

    orderKey = orderKey,

    inListView = inListView,
    inDetailView = inDetailView,
    inInsertInput = inInsertInput,
    inUpdateInput = inUpdateInput,
    inSpecification = inSpecification,
    inOptionView = inOptionView,
    inShortAssociationView = inShortAssociationView,
    inLongAssociationView = inLongAssociationView,
    inLongAssociationInput = inLongAssociationInput,
    specialFormType = specialFormType,

    typeTableId = entityNameTableIdMap[typeEntity?.name],
    enumId = enumNameIdMap[enum?.name]
)

private data class EntityModelBusinessViewWithTable(
    val entity: GenEntityModelView,
    val table: GenTableModelBusinessFillView,
    val properties: List<GenPropertyModelView>,
)

@Throws(ModelBusinessInputException::class)
private fun List<EntityModelBusinessView>.toConfigInputs(
    tables: List<GenTableModelBusinessFillView>,
    enums: List<GenEnumModelBusinessFillView>,
): List<EntityModelBusinessInput> {
    val tableNameMap = tables.associateBy { it.name }
    val enumNameIdMap = enums.associate { it.name to it.id }

    val entityWithTables = map { (entity, properties) ->
        val table = entity.tableName.let { tableNameMap[it] }
            ?: throw ModelBusinessInputException.entityCannotMatchTable(
                "entity [${entity.name}] cannot match table",
                entityName = entity.name,
                tableName = entity.tableName
            )

        EntityModelBusinessViewWithTable(entity, table, properties)
    }
    val entityNameTableIdMap = entityWithTables.associate { (entity, table) ->
        entity.name to table.id
    }

    return entityWithTables.map { (entity, table, properties) ->
        val entityId = table.entityId
            ?: throw ModelBusinessInputException.entityMatchedTableConvertedEntityNotFound(
                entityName = entity.name,
                table = IdName(table.id, table.name)
            )

        val columnNameMap = table.columns.associateBy { it.name }

        EntityModelBusinessInput(
            entity.toConfigInput(
                entityId,
                columnNameMap
            ),
            properties.map { property ->
                property.toConfigInput(entityNameTableIdMap, enumNameIdMap)
            }
        )
    }
}

private fun GenPropertyEntityConfigInput.toPureInput(entityId: Long) = toEntity {
    this.entityId = entityId

    idProperty = false
    generatedId = false
    generatedIdAnnotation = null

    logicalDelete = false
    logicalDeletedAnnotation = null

    keyProperty = false
    keyGroup = null

    associationType = null
    idView = false
    idViewTarget = null
    mappedBy = null
    inputNotNull = null
    joinTableMeta = null
    joinColumnMetas = null
    dissociateAnnotation = null
}

private fun EntityModelBusinessInput.toPureInput(): GenEntity {
    val configEntity = entity
    val configProperties = properties

    return configEntity.toEntity {
        properties += configProperties.map { it.toPureInput(entity.id) }
    }
}

interface EntityBusinessConfig {
    val transactionTemplate: TransactionTemplate

    fun KSqlClient.configEntities(
        entities: List<EntityModelBusinessInput>,
    ) = transactionTemplate.execute {
        updateEntities(entities.map { it.toPureInput() }, AssociatedSaveMode.REPLACE)
    }

    fun KSqlClient.configEntity(
        entity: EntityModelBusinessInput,
    ) = transactionTemplate.execute {
        update(entity.toPureInput(), AssociatedSaveMode.REPLACE)
    }

    @Throws(ModelBusinessInputException::class)
    fun KSqlClient.configEntities(
        modelId: Long,
        views: List<EntityModelBusinessView>,
    ) {
        val tables = executeQuery(GenTable::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenTableModelBusinessFillView::class))
        }

        val enums = executeQuery(GenEnum::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenEnumModelBusinessFillView::class))
        }

        val inputs = views.toConfigInputs(tables, enums)

        configEntities(inputs)
    }
}