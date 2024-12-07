package top.potmot.core.entity.business

import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import top.potmot.core.model.business.EntityModelBusinessInput
import top.potmot.entity.dto.toEntities

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