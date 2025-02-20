package top.potmot.core.model.config

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import top.potmot.core.entity.config.EntityConfig
import top.potmot.core.entity.config.EntityConfigInput
import top.potmot.core.model.export.EntityExportView
import top.potmot.entity.GenEnum
import top.potmot.entity.GenTable
import top.potmot.entity.dto.GenEntityConfigInput
import top.potmot.entity.dto.GenEntityExportView
import top.potmot.entity.dto.GenEnumModelBusinessFillView
import top.potmot.entity.dto.GenPropertyConfigInput
import top.potmot.entity.dto.GenPropertyExportView
import top.potmot.entity.dto.GenTableModelBusinessFillView
import top.potmot.entity.dto.IdName
import top.potmot.entity.modelId
import top.potmot.error.ModelBusinessInputException

private data class EntityExportView_WithTable(
    val entity: GenEntityExportView,
    val properties: List<GenPropertyExportView>,
    val table: GenTableModelBusinessFillView,
)

@Throws(
    ModelBusinessInputException.PropertyCannotMatchColumn::class,
    ModelBusinessInputException.PropertyCannotRematchOldProperty::class,
    ModelBusinessInputException.PropertyMatchedMoreThanOneOldProperty::class,
)
private fun GenEntityExportView.toConfigInput(
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

private fun GenPropertyExportView.toConfigInput(
    entityNameTableIdMap: Map<String, Long>,
    enumNameIdMap: Map<String, Long>,
) = GenPropertyConfigInput(
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

@Throws(ModelBusinessInputException::class)
private fun List<EntityExportView>.toConfigInputs(
    tables: List<GenTableModelBusinessFillView>,
    enums: List<GenEnumModelBusinessFillView>,
): List<EntityConfigInput> {
    val tableNameMap = tables.associateBy { it.name }
    val enumNameIdMap = enums.associate { it.name to it.id }

    val entityWithTables = map { (entity, properties) ->
        val table = tableNameMap[entity.tableName]
            ?: throw ModelBusinessInputException.entityCannotMatchTable(
                "entity [${entity.name}] cannot match table",
                entityName = entity.name,
                tableName = entity.tableName
            )

        EntityExportView_WithTable(entity, properties, table)
    }
    val entityNameTableIdMap = entityWithTables.associate { (entity, _, table) ->
        entity.name to table.id
    }

    return entityWithTables.map { (entity, properties, table) ->
        val entityId = table.entityId
            ?: throw ModelBusinessInputException.entityMatchedTableConvertedEntityNotFound(
                entityName = entity.name,
                table = IdName(table.id, table.name)
            )

        val columnNameMap = table.columns.associateBy { it.name }

        EntityConfigInput(
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

interface ModelEntitiesConfig : EntityConfig {
    @Throws(ModelBusinessInputException::class)
    private fun KSqlClient.convertModelEntityInputsToViews(
        modelId: Long,
        views: List<EntityExportView>,
    ): List<EntityConfigInput> {
        val tables = executeQuery(GenTable::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenTableModelBusinessFillView::class))
        }

        val enums = executeQuery(GenEnum::class) {
            where(table.modelId eq modelId)
            select(table.fetch(GenEnumModelBusinessFillView::class))
        }

        return views.toConfigInputs(tables, enums)
    }

    fun KSqlClient.configModelEntities(
        modelId: Long,
        views: List<EntityExportView>,
    ) {
        transactionTemplate.execute {
            val entityInputs = convertModelEntityInputsToViews(
                modelId = modelId,
                views = views
            )
            configEntities(entityInputs)
        }
    }
}
