package top.potmot.core.business.utils.entity

import top.potmot.core.business.utils.mark.upperName
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.entity.dto.IdName
import top.potmot.error.ModelException

data class ExistValidItem(
    val dtoName: String,
    val functionName: String,
    val scalarProperties: List<TargetOf_properties>,
    val associationPropertyPairs: List<Pair<TargetOf_properties, TargetOf_properties?>>,
    val properties: List<TargetOf_properties>,
)

/**
 * 根据实体*全量属性*映射 index 为 existValidItems
 * 要求实体一定具有全部 index 对应的属性（也及 superTable 中的全部属性）
 */
val GenEntityBusinessView.existValidItems: List<ExistValidItem>
    @Throws(ModelException.IndexRefPropertyNotFound::class, ModelException.IndexRefPropertyCannotBeList::class)
    get() {
        val propertyMap = properties.associateBy { it.id }
        val idViewTargetMap = properties.associateBy { it.idViewTarget }

        return indexes
            .filter { it.uniqueIndex }
            .mapNotNull { index ->
                val indexPropertyIds = index.columns.flatMap { column ->
                    column.properties.map { it.id }
                }

                val scalarProperties = mutableListOf<TargetOf_properties>()
                val associationPropertyPairs = mutableListOf<Pair<TargetOf_properties, TargetOf_properties?>>()

                indexPropertyIds.forEach { propertyId ->
                    val property = propertyMap[propertyId]
                        ?: throw ModelException.indexRefPropertyNotFound(
                            entity = IdName(id, name),
                            entityProperties = properties.map { IdName(it.id, it.name) },
                            index = IdName(index.id, index.name),
                            indexPropertyIds = index.columns.flatMap { column -> column.properties.map { it.id } },
                            notFoundPropertyId = propertyId
                        )
                    if (property.associationType == null) {
                        // 普通属性
                        scalarProperties += property
                    } else if (property.listType) {
                        throw ModelException.indexRefPropertyCannotBeList(
                            entity = IdName(id, name),
                            entityProperties = properties.map { IdName(it.id, it.name) },
                            index = IdName(index.id, index.name),
                            indexPropertyIds = index.columns.flatMap { column -> column.properties.map { it.id } },
                            listProperty = IdName(property.id, property.name)
                        )
                    } else if (!property.idView) {
                        associationPropertyPairs += property to idViewTargetMap[property.name]
                    }
                }

                if (scalarProperties.isEmpty() && associationPropertyPairs.isEmpty()) {
                    return@mapNotNull null
                }

                val validProperties = (scalarProperties + associationPropertyPairs.map { it.first })
                val upperNameJoin = validProperties.joinToString("And") { it.upperName }

                ExistValidItem(
                    "${name}ExistBy${upperNameJoin}Spec",
                    "existBy$upperNameJoin",
                    scalarProperties.sortedBy { it.id },
                    associationPropertyPairs.sortedBy { it.first.id },
                    validProperties
                )
            }
            .distinctBy {
                it.properties.toString()
            }
    }