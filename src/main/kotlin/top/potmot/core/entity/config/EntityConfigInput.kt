package top.potmot.core.entity.config

import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.transaction.support.TransactionTemplate
import top.potmot.entity.GenEntity
import top.potmot.entity.dto.GenEntityConfigInput
import top.potmot.entity.dto.GenPropertyConfigInput

/**
 * 实体 - 用于模型业务配置的输入
 * 将直接被用于 save
 */
data class EntityConfigInput(
    /**
     * 实体业务配置输入，仅包含特定的业务配置项
     */
    val tableConvertedEntity: GenEntityConfigInput,

    /**
     * 属性业务配置输入，具有全标量字段，用于编辑无需考虑关联的非转换属性
     */
    val notConvertedProperties: List<GenPropertyConfigInput>,
)

private fun GenPropertyConfigInput.toPureInput(entityId: Long) = toEntity {
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

private fun EntityConfigInput.toPureInput(): GenEntity {
    val configEntity = tableConvertedEntity
    val configProperties = notConvertedProperties

    return configEntity.toEntity {
        properties += configProperties.map { it.toPureInput(tableConvertedEntity.id) }
    }
}

interface EntityConfig {
    val transactionTemplate: TransactionTemplate

    fun KSqlClient.configEntities(
        entities: List<EntityConfigInput>,
    ) = transactionTemplate.execute {
        updateEntities(entities.map { it.toPureInput() }, AssociatedSaveMode.REPLACE)
    }

    fun KSqlClient.configEntity(
        entity: EntityConfigInput,
    ) = transactionTemplate.execute {
        update(entity.toPureInput(), AssociatedSaveMode.REPLACE)
    }
}