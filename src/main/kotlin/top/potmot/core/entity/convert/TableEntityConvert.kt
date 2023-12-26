package top.potmot.core.entity.convert

import org.babyfish.jimmer.kt.new
import top.potmot.config.GenConfig
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenEntity
import top.potmot.model.GenTable
import top.potmot.model.by
import top.potmot.model.copy
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTypeMappingView

fun GenTable.toGenEntity(
    packagePath: String,
    typeMappings: List<GenTypeMappingView>,
    language: GenLanguage,
): GenEntity =
    convertTableToEntity(GenTableAssociationsView(this), packagePath, language, typeMappings)

fun GenTableAssociationsView.toGenEntity(
    packagePath: String,
    typeMappings: List<GenTypeMappingView>,
    language: GenLanguage,
): GenEntity =
    convertTableToEntity(this, packagePath, language, typeMappings)

private fun convertTableToEntity(
    table: GenTableAssociationsView,
    packagePath: String,
    language: GenLanguage,
    typeMappings: List<GenTypeMappingView> = emptyList(),
): GenEntity {
    val baseEntity = tableToEntity(table, packagePath)

    val properties = createProperties(
        table,
        language,
        table.schema?.dataSource?.type ?: GenConfig.dataSourceType,
        typeMappings
    )

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
    packagePath: String
): GenEntity {
    return new(GenEntity::class).by {
        table().id = genTable.id
        author = GenConfig.author
        name = tableNameToClassName(genTable.name)
        comment = genTable.comment.clearTableComment()
        remark = genTable.remark
        this.packagePath = packagePath
    }
}
