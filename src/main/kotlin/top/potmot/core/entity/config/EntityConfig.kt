package top.potmot.core.entity.config

import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import top.potmot.entity.dto.toEntities
import top.potmot.service.EntityModelBusinessInput

fun configEntities(
    sqlClient: KSqlClient,
    inputs: List<EntityModelBusinessInput>
) {
    sqlClient.updateInputs(inputs.map { it.entity }, AssociatedSaveMode.REPLACE)

    sqlClient.insertEntities(
        inputs.flatMap {
            it.properties.toEntities {
                entityId = it.entity.id
                columnId = null
                typeTable = null
            }
        }
    )
}

fun configEntity(
    sqlClient: KSqlClient,
    input: EntityModelBusinessInput,
) {
    val (entity, properties) = input

    sqlClient.update(entity, AssociatedSaveMode.REPLACE)
    sqlClient.insertEntities(properties.toEntities {
        entityId = entity.id
        columnId = null
        typeTable = null
    })
}