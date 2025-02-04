package top.potmot.core.business.view.generate.meta.rules

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.entity.dto.IdName
import top.potmot.error.ModelException

data class ExistValidItem(
    val dtoName: String,
    val functionName: String,
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

                val validProperties = mutableListOf<PropertyBusiness>()

                indexPropertyIds.forEach { propertyId ->
                    val propertyBusiness = propertyIdMap[propertyId]
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

                    validProperties += propertyBusiness
                }

                if (scalarProperties.isEmpty() && associationProperties.isEmpty()) {
                    return@mapNotNull null
                }

                val producedValidProperties = validProperties.distinctBy { it.id }.sortedBy { it.property.orderKey }
                val upperNameJoin = producedValidProperties.joinToString("And") { it.upperName }

                ExistValidItem(
                    "${entity.name}ExistBy${upperNameJoin}Spec",
                    "existBy$upperNameJoin",
                    producedValidProperties
                )
            }
            .distinctBy {
                it.properties.toString()
            }
    }

/**
 * 将 ExistValidItem 转换为 ExistValidRule
 *
 */
@Throws(
    ModelException.IdPropertyNotFound::class,
    ModelException.IndexRefPropertyNotFound::class,
    ModelException.IndexRefPropertyCannotBeList::class
)
fun EntityBusiness.existValidRules(
    withId: Boolean,
    filterProperties: List<PropertyBusiness> = properties,
): Map<PropertyBusiness, List<ExistValidRule>> {
    val filterPropertyIds = filterProperties.map { it.property.id }.toSet()

    return existValidItems
        .flatMap { item ->
            item.properties.mapNotNull { property ->
                if (property.id !in filterPropertyIds)
                    null
                else
                    ExistValidRule(
                        item,
                        property,
                        this,
                        withId
                    )
            }
        }
        .groupBy { it.property }
}