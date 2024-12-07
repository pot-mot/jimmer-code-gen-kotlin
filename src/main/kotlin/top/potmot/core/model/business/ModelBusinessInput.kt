package top.potmot.core.model.business

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import top.potmot.entity.GenEnum
import top.potmot.entity.GenTable
import top.potmot.entity.dto.GenEntityConfigInput
import top.potmot.entity.dto.GenEntityModelView
import top.potmot.entity.dto.GenEnumModelBusinessFillView
import top.potmot.entity.dto.GenPropertyEntityConfigInput
import top.potmot.entity.dto.GenPropertyModelView
import top.potmot.entity.dto.GenTableModelBusinessFillView
import top.potmot.entity.modelId

data class EntityModelBusinessInput(
    val entity: GenEntityConfigInput,
    val properties: List<GenPropertyEntityConfigInput>,
)

// TODO 添加单元测试和报错异常信息

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
                ?: throw RuntimeException()

            val matchProperties = column.properties.filter {
                if (it.overwriteName) true else it.name == property.name
                        && it.type == property.type
                        && it.typeNotNull == property.typeNotNull
                        && it.typeTable?.name == property.typeTable?.name
                        && it.associationType == property.associationType
                        && it.joinColumnMetas.toString() == property.joinColumnMetas.toString()
                        && it.joinTableMeta.toString() == property.joinTableMeta.toString()
            }

            if (matchProperties.size != 1)
                throw RuntimeException()

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
                inOptionView = property.inInsertInput,
                inInsertInput = property.inInsertInput,
                inUpdateInput = property.inUpdateInput,
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


/**
 * 处理 ModelBusinessInput 为 EntityModelBusinessView
 */
private fun List<EntityModelBusinessView>.toModelBusinessInputs(
    tables: List<GenTableModelBusinessFillView>,
    enums: List<GenEnumModelBusinessFillView>,
): List<EntityModelBusinessInput> {
    val tableNameMap = tables.associateBy { it.name }
    val enumNameIdMap = enums.associate { it.name to it.id }

    return map { (entity, properties) ->
        val table = tableNameMap[entity.tableName] ?: throw RuntimeException()
        val entityId = table.entityId ?: throw RuntimeException()

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

/**
 * 创建实体配置输入对象
 */
fun createEntityModelBusinessInputs(
    sqlClient: KSqlClient,
    modelId: Long,
    entities: List<EntityModelBusinessView>,
): List<EntityModelBusinessInput> {
    val tables = sqlClient.executeQuery(GenTable::class) {
        where(table.modelId eq modelId)
        select(table.fetch(GenTableModelBusinessFillView::class))
    }

    val enums = sqlClient.executeQuery(GenEnum::class) {
        where(table.modelId eq modelId)
        select(table.fetch(GenEnumModelBusinessFillView::class))
    }

    return entities.toModelBusinessInputs(tables, enums)
}