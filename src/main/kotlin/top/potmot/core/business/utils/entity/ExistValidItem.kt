package top.potmot.core.business.utils.entity

import top.potmot.core.business.property.AssociationProperty
import top.potmot.core.business.property.CommonProperty
import top.potmot.core.business.property.EntityBusiness
import top.potmot.core.business.property.ForceIdViewProperty
import top.potmot.core.business.property.PropertyBusiness
import top.potmot.entity.dto.IdName
import top.potmot.error.ModelException

data class ExistValidItem(
    val dtoName: String,
    val functionName: String,
    val scalarProperties: List<CommonProperty>,
    val associationProperties: List<AssociationProperty>,
    val properties: List<PropertyBusiness>,
)

/**
 * 根据实体*全量属性*映射 index 为 existValidItems
 * 要求实体一定具有全部 index 对应的属性（也及 superTable 中的全部属性）
 */
val EntityBusiness.existValidItems: List<ExistValidItem>
    @Throws(ModelException.IndexRefPropertyNotFound::class, ModelException.IndexRefPropertyCannotBeList::class)
    get() {
        return entity.indexes
            .filter { it.uniqueIndex }
            .mapNotNull { index ->
                val indexPropertyIds = index.columns.flatMap { column ->
                    column.properties.map { it.id }
                }

                val scalarProperties = mutableSetOf<CommonProperty>()
                val associationProperties = mutableSetOf<AssociationProperty>()

                indexPropertyIds.forEach { propertyId ->
                    val propertyBusiness = propertyBusinessIdMap[propertyId]
                        ?: throw ModelException.indexRefPropertyNotFound(
                            entity = IdName(entity.id, entity.name),
                            entityProperties = properties.map { IdName(it.id, it.name) },
                            index = IdName(index.id, index.name),
                            indexPropertyIds = index.columns.flatMap { column -> column.properties.map { it.id } },
                            notFoundPropertyId = propertyId
                        )

                    val property = propertyBusiness.property
                    if (property.listType) {
                        throw ModelException.indexRefPropertyCannotBeList(
                            entity = IdName(entity.id, entity.name),
                            entityProperties = properties.map { IdName(it.id, it.name) },
                            index = IdName(index.id, index.name),
                            indexPropertyIds = index.columns.flatMap { column -> column.properties.map { it.id } },
                            listProperty = IdName(property.id, property.name)
                        )
                    }

                    when(propertyBusiness) {
                        is CommonProperty -> scalarProperties += propertyBusiness
                        is AssociationProperty -> associationProperties += propertyBusiness
                        is ForceIdViewProperty -> associationProperties += propertyBusiness.associationProperty
                    }
                }

                if (scalarProperties.isEmpty() && associationProperties.isEmpty()) {
                    return@mapNotNull null
                }

                val validProperties = (scalarProperties + associationProperties)
                val upperNameJoin = validProperties.joinToString("And") { it.upperName }

                ExistValidItem(
                    "${entity.name}ExistBy${upperNameJoin}Spec",
                    "existBy$upperNameJoin",
                    scalarProperties.sortedBy { it.id },
                    associationProperties.sortedBy { it.id },
                    validProperties.sortedBy { it.id }
                )
            }
            .distinctBy {
                it.properties.toString()
            }
    }