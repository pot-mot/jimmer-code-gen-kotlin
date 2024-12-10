package top.potmot.core.model.business

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
import top.potmot.entity.modelId
import top.potmot.error.ModelBusinessInputException
import top.potmot.utils.transaction.executeNotNull

data class EntityModelBusinessInput(
    val entity: GenEntityConfigInput,
    val properties: List<GenPropertyEntityConfigInput>,
)

@Throws(
    ModelBusinessInputException.PropertyCannotMatchColumn::class,
    ModelBusinessInputException.PropertyCannotRematchOldProperty::class,
    ModelBusinessInputException.PropertyMatchedMoreThanOneOldProperty::class,
)
private fun GenEntityModelView.toConfigInput(
    id: Long,
    columnNameMap: Map<String, GenTableModelBusinessFillView.TargetOf_columns>,
) =
    GenEntityConfigInput(
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
                (if (it.overwriteName) true else it.name == property.name)
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

interface EntityModelBusinessViewToModelBusinessInputs {
    /**
     * 处理 ModelBusinessInput 为 EntityModelBusinessView
     */
    @Throws(ModelBusinessInputException::class)
    fun List<EntityModelBusinessView>.toModelBusinessInputs(
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
}

interface ModelBusinessInput : EntityModelBusinessViewToModelBusinessInputs {
    val transactionTemplate: TransactionTemplate

    /**
     * 创建实体配置输入对象
     */
    @Throws(ModelBusinessInputException::class)
    fun KSqlClient.createEntityModelBusinessInputs(
        modelId: Long,
        entities: List<EntityModelBusinessView>,
    ) = transactionTemplate.executeNotNull {
        val tables = executeQuery(GenTable::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenTableModelBusinessFillView::class))
        }

        val enums = executeQuery(GenEnum::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenEnumModelBusinessFillView::class))
        }

        entities.toModelBusinessInputs(tables, enums)
    }
}
