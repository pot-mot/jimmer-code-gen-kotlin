package top.potmot.core.entity.business

import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.transaction.support.TransactionTemplate
import top.potmot.core.model.business.EntityModelBusinessInput
import top.potmot.entity.dto.toEntities

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
}