package top.potmot.core.entity.convert

import top.potmot.entity.GenEntity
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.entity.dto.GenEntityInput

/**
 * 对已存在的实体和转换出的实体进行合并
 * 保留原实体的id，名称，注释，备注，业务配置
 * 根据属性名称匹配旧属性，并保留旧属性的id，名称，注释，备注，排序键，业务配置
 *      最后拼接 无法映射至当前属性 且 无对应列 的旧属性
 */
fun mergeExistAndConvertEntity(
    existEntity: GenEntityDetailView,
    convertEntity: GenEntityInput,
): GenEntity =
    convertEntity.toEntity {
        id = existEntity.id
        name = existEntity.name
        comment = existEntity.comment
        remark = existEntity.remark

        canAdd = existEntity.canAdd
        canEdit = existEntity.canEdit
        canQuery = existEntity.canQuery
        canDelete = existEntity.canDelete
        hasPage = existEntity.hasPage

        val existPropertyNameMap =
            existEntity.properties.associateBy { it.name }

        val mergedConvertProperties = convertEntity.properties.map {
            val existProperty = existPropertyNameMap[it.name]

            if (existProperty == null)
                it.toEntity()
            else
                it.toEntity {
                    id = existProperty.id
                    name = existProperty.name
                    comment = existProperty.comment
                    remark = existEntity.remark
                    orderKey = existProperty.orderKey

                    inListView = existProperty.inListView
                    inDetailView = existProperty.inDetailView
                    inSpecification = existProperty.inSpecification
                    inInsertInput = existProperty.inInsertInput
                    inUpdateInput = existProperty.inUpdateInput
                    longAssociation = existProperty.longAssociation
                    inLongAssociationInput = existProperty.inLongAssociationInput
                    inLongAssociationView = existProperty.inLongAssociationView
                }
        }

        val mergedConvertPropertyNameSet = mergedConvertProperties.map { it.name }.toSet()

        val unMergeProperties = existEntity.properties
            .filter {
                it.name !in mergedConvertPropertyNameSet && it.columnId == null
            }
            .map { it.toEntity() }

        properties = listOf(
            mergedConvertProperties,
            unMergeProperties
        )
            .flatten()
            .sortedBy { it.orderKey }
    }