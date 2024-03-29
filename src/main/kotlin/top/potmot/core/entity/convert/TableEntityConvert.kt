package top.potmot.core.entity.convert

import org.babyfish.jimmer.kt.new
import top.potmot.context.getContextOrGlobal
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
import top.potmot.model.GenEntity
import top.potmot.model.by
import top.potmot.model.copy
import top.potmot.model.dto.ColumnTypeMeta
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.GenTypeMappingView

typealias TypeMapping = (column: ColumnTypeMeta) -> String

/**
 * 转换 table 为 entity
 * 由以下流程组成
 *  tableToEntity -> baseEntity :
 *      仅有实体基本信息
 *  convertBaseProperties -> basePropertyMap Map<columnId, Property> :
 *      一列映射成一个属性的基础映射结果
 *  convertAssociationProperties -> associationPropertyMap Map<columnId, List<Property>> :
 *      一列映射成多个属性的关联映射结果
 *  fillProperty -> result :
 *      将 associationProperty 中的数据填充到 baseEntity 中
 */
@Throws(ConvertEntityException::class, ColumnTypeException::class)
fun GenTableAssociationsView.toGenEntity(
    modelId: Long?,
    typeMappings: List<GenTypeMappingView>,
): GenEntity {
    val baseEntity = tableToEntity(this, modelId)

    val typeMapping: TypeMapping = { typeMeta -> getPropertyType(typeMeta, typeMappings) }

    val basePropertyMap = convertBaseProperties(
        this,
        typeMapping,
    )

    val associationPropertyMap = convertAssociationProperties(
        this,
        basePropertyMap,
        typeMapping
    )

    val associationProperties = associationPropertyMap.flatMap { it.value }

    return baseEntity.copy {
        this.properties =
            associationProperties.mapIndexed { index, genProperty ->
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
): GenEntity {
    return new(GenEntity::class).by {
        val context = getContextOrGlobal()

        this.table().id = genTable.id
        this.modelId = modelId
        this.author = context.author
        this.name = tableNameToClassName(genTable.name)
        this.comment = genTable.comment.clearTableComment()
        this.remark = genTable.remark
        this.packagePath = context.packagePath
    }
}
