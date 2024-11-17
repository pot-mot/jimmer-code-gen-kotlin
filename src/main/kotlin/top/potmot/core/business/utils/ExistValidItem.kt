package top.potmot.core.business.utils

import java.util.SortedSet
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.IdName
import top.potmot.error.ModelException

data class ExistValidItem(
    val properties: SortedSet<GenEntityBusinessView.TargetOf_properties>,
) {
    val functionName = "existBy${properties.joinToString(separator = "And") { it.upperName }}"
}

interface ExistValidItems {
 val GenEntityBusinessView.existValidItems: List<ExistValidItem>
    @Throws(ModelException.IndexRefPropertyNotFound::class, ModelException.IndexRefPropertyCannotBeList::class)
    get() {
        val propertyMap = properties.associateBy { it.id }
        val idViewTargets = properties.mapNotNull { it.idViewTarget }.toSet()

        return indexes.asSequence().filter { it.uniqueIndex }.map { index ->
            index.columns.flatMap { column ->
                column.properties.mapNotNull { propertyIdOnly ->
                    val property = propertyMap[propertyIdOnly.id]
                        ?: throw ModelException.indexRefPropertyNotFound(
                            entity = IdName(id, name),
                            entityProperties = properties.map { IdName(it.id, it.name) },
                            index = IdName(index.id, index.name),
                            indexPropertyIds = index.columns.flatMap { column -> column.properties.map { it.id } },
                            notFoundPropertyId = propertyIdOnly.id
                        )
                    if (property.associationType == null)
                        property
                    else {
                        if (property.idView) {
                            property
                        } else if (property.listType) {
                            throw ModelException.indexRefPropertyCannotBeList(
                                entity = IdName(id, name),
                                entityProperties = properties.map { IdName(it.id, it.name) },
                                index = IdName(index.id, index.name),
                                indexPropertyIds = index.columns.flatMap { column -> column.properties.map { it.id } },
                                listProperty = IdName(property.id, property.name)
                            )
                        } else {
                            if (property.name in idViewTargets)
                                null
                            else
                                property
                        }
                    }
                }
            }
        }
            .filter { it.isNotEmpty() }
            .map {
                ExistValidItem(
                    it.toSortedSet { property1, property2 ->
                        property1.id.compareTo(property2.id)
                    }
                )
            }
            .distinctBy { it.properties }.toList()
    }
}