package top.potmot.core.entity.convert

import top.potmot.context.getContextOrGlobal
import top.potmot.error.ColumnTypeException
import top.potmot.error.ConvertEntityException
import top.potmot.entity.dto.ColumnTypeMeta
import top.potmot.entity.dto.GenEntityInput
import top.potmot.entity.dto.GenTableAssociationsView
import top.potmot.entity.dto.GenTypeMappingView

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
): GenEntityInput {
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

    val associationProperties = associationPropertyMap
        .flatMap { it.value }
        .mapIndexed { index, genProperty ->
            genProperty.copy(
                orderKey = index.toLong()
            )
        }

    return baseEntity.copy(
        properties = associationProperties
    )
}

/**
 * 表到实体转换
 */
private fun tableToEntity(
    genTable: GenTableAssociationsView,
    modelId: Long?,
): GenEntityInput {
    val context = getContextOrGlobal()

    return GenEntityInput(
        tableId = genTable.id,
        modelId = modelId,
        author = context.author,
        name = snakeToUpperCamel(genTable.name),
        comment = genTable.comment.clearTableComment(),
        remark = genTable.remark,
        packagePath = context.packagePath,
        superEntityIds = emptyList(),
        properties = emptyList()
    )
}
