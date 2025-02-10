package top.potmot.core.entity.config

import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.transaction.support.TransactionTemplate
import top.potmot.entity.GenEnum
import top.potmot.entity.GenTable
import top.potmot.entity.dto.GenEntityConfigInput
import top.potmot.entity.dto.GenEntityModelView
import top.potmot.entity.dto.GenEnumModelBusinessFillView
import top.potmot.entity.dto.GenPropertyEntityConfigInput
import top.potmot.entity.dto.GenPropertyModelView
import top.potmot.entity.dto.GenTableModelBusinessFillView
import top.potmot.entity.dto.IdName
import top.potmot.entity.dto.toEntities
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
    canAdd = canAdd,
    canEdit = canEdit,
    canQuery = canQuery,
    canDelete = canDelete,
    hasPage = hasPage,
    otherAnnotation = otherAnnotation,
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
        )
    }
)

private fun GenPropertyModelView.toConfigInput(
    enumNameIdMap: Map<String, Long>,
) = GenPropertyEntityConfigInput(
    name = name,
    overwriteName = overwriteName,
    comment = comment,
    overwriteComment = overwriteComment,
    type = type,
    listType = listType,
    typeNotNull = typeNotNull,
    idProperty = idProperty,
    idGenerationAnnotation = idGenerationAnnotation,
    keyProperty = keyProperty,
    keyGroup = keyGroup,
    logicalDelete = logicalDelete,
    idView = idView,
    idViewTarget = idViewTarget,
    associationType = associationType,
    longAssociation = longAssociation,
    mappedBy = mappedBy,
    inputNotNull = inputNotNull,
    joinColumnMetas = joinColumnMetas,
    joinTableMeta = joinTableMeta,
    dissociateAnnotation = dissociateAnnotation,
    otherAnnotation = otherAnnotation,
    body = body,
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
    remark = remark,
    enumId = enumNameIdMap[enum?.name]
)

@Throws(ModelBusinessInputException::class)
private fun List<EntityModelBusinessView>.toInputs(
    tables: List<GenTableModelBusinessFillView>,
    enums: List<GenEnumModelBusinessFillView>,
): List<EntityModelBusinessInput> {
    val tableNameMap = tables.associateBy { it.name }
    val enumNameIdMap = enums.associate { it.name to it.id }

    return map { (entity, properties) ->
        val table = tableNameMap[entity.tableName]
            ?: throw ModelBusinessInputException.entityCannotMatchTable(
                "entity [${entity.name}] cannot match table",
                entityName = entity.name,
                tableName = entity.tableName
            )
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
                property.toConfigInput(enumNameIdMap)
            }
        )
    }
}

interface EntityBusinessConfig {
    val transactionTemplate: TransactionTemplate

    fun KSqlClient.configEntities(
        inputs: List<EntityModelBusinessInput>,
    ) = transactionTemplate.execute {
        updateInputs(inputs.map { it.entity }, AssociatedSaveMode.REPLACE)

        insertEntities(
            inputs.flatMap {
                it.properties.toEntities {
                    entityId = it.entity.id
                    columnId = null
                    typeTable = null
                }
            }
        )
    }

    fun KSqlClient.configEntity(
        input: EntityModelBusinessInput,
    ) = transactionTemplate.execute {
        val (entity, properties) = input

        update(entity, AssociatedSaveMode.REPLACE)

        insertEntities(properties.toEntities {
            entityId = entity.id
            columnId = null
            typeTable = null
        })
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

        val inputs = views.toInputs(tables, enums)

        configEntities(inputs)
    }
}