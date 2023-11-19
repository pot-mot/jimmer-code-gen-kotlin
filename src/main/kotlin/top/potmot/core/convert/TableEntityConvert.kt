package top.potmot.core.convert

import org.babyfish.jimmer.kt.new
import top.potmot.config.GenConfig
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenEntity
import top.potmot.model.by
import top.potmot.model.copy
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTypeMappingView

fun GenTableAssociationsView.toGenEntity(
    typeMappings: List<GenTypeMappingView> = emptyList(),
    language: GenLanguage? = GenConfig.language,
): GenEntity =
    convertTableToEntity(this, language, typeMappings)

private fun convertTableToEntity(
    table: GenTableAssociationsView,
    language: GenLanguage?,
    typeMappings: List<GenTypeMappingView> = emptyList(),
): GenEntity {
    val baseEntity = tableToEntity(table)

    val properties = table.columns.flatMap { column ->
        columnToProperties(
            column
        ) {
            it.getPropertyType(
                table.schema?.dataSource?.type ?: GenConfig.dataSourceType,
                language ?: GenConfig.language,
                typeMappings)
        }
    }

    return baseEntity.copy {
        this.properties = properties.mapIndexed { index, genProperty ->
            genProperty.copy {
                orderKey = index.toLong()
            }
        }
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
