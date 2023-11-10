package top.potmot.core.convert

import org.babyfish.jimmer.ImmutableObjects
import org.babyfish.jimmer.kt.new
import top.potmot.config.GenConfig
import top.potmot.enumeration.AssociationType.*
import top.potmot.model.GenEntity
import top.potmot.model.GenTypeMapping
import top.potmot.model.by
import top.potmot.model.copy
import top.potmot.model.dto.GenTableAssociationsView

fun GenTableAssociationsView.toGenEntity(
    typeMappings: List<GenTypeMapping> = emptyList(),
): GenEntity =
    convertTableToEntity(this, typeMappings)

private fun convertTableToEntity(
    table: GenTableAssociationsView,
    typeMappings: List<GenTypeMapping> = emptyList(),
): GenEntity {
    val baseEntity = tableToEntity(table)

    val properties = table.columns.flatMap { column ->
        columnToProperties(
            column,
            typeMappings
        )
    }

    val associationIsLoadedMap = properties.groupBy {
        ImmutableObjects.isLoaded(it, "associationType")
    }

    val unloadAssociationProperties = associationIsLoadedMap[false] ?: emptyList()

    val associationPropertiesMap = associationIsLoadedMap[true]?.groupBy {
        it.associationType
    } ?: emptyMap()

    val resortProperties =
        unloadAssociationProperties +
                (associationPropertiesMap[null] ?: emptyList()) +
                (associationPropertiesMap[ONE_TO_ONE] ?: emptyList()) +
                (associationPropertiesMap[MANY_TO_ONE] ?: emptyList()) +
                (associationPropertiesMap[ONE_TO_MANY] ?: emptyList()) +
                (associationPropertiesMap[MANY_TO_MANY] ?: emptyList())

    return baseEntity.copy {
        this.properties = resortProperties
    }
}

/**
 * 表到实体转换
 */
private fun tableToEntity(
    genTable: GenTableAssociationsView,
): GenEntity {
    return new(GenEntity::class).by {
        table().id = genTable.id
        author = GenConfig.author
        name = tableNameToClassName(genTable.name)
        comment = genTable.comment.clearTableComment()
        remark = genTable.remark
    }
}
