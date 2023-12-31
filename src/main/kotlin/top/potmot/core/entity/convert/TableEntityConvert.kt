package top.potmot.core.entity.convert

import org.babyfish.jimmer.kt.new
import top.potmot.config.GenConfig
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.GenEntity
import top.potmot.model.by
import top.potmot.model.copy
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTypeMappingView

fun GenTableAssociationsView.toGenEntity(
    modelId: Long?,
    dataSourceType: DataSourceType,
    language: GenLanguage,
    packagePath: String,
    typeMappings: List<GenTypeMappingView>,
): GenEntity =
    convertTableToEntity(this, modelId, dataSourceType, language, packagePath, typeMappings)

private fun convertTableToEntity(
    table: GenTableAssociationsView,
    modelId: Long?,
    dataSourceType: DataSourceType,
    language: GenLanguage,
    packagePath: String,
    typeMappings: List<GenTypeMappingView> = emptyList(),
): GenEntity {
    val baseEntity = tableToEntity(table, modelId, packagePath)

    val properties = createProperties(
        table,
        language,
        dataSourceType,
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
    modelId: Long?,
    packagePath: String,
): GenEntity {
    return new(GenEntity::class).by {
        this.table().id = genTable.id
        this.modelId = modelId
        this.author = GenConfig.author
        this.name = tableNameToClassName(genTable.name)
        this.comment = genTable.comment.clearTableComment()
        this.remark = genTable.remark
        this.packagePath = packagePath
    }
}
