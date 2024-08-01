package top.potmot.core.entity.convert

import top.potmot.core.entity.meta.ConvertPropertyMeta
import top.potmot.entity.dto.GenPropertyInput
import top.potmot.error.ConvertEntityException

/**
 * 对属性处理名称重复：
 *  如果存在名称重复，则将基础属性拼接到关联属性名称之后
 */
fun handleDuplicateName(
    propertiesMap: Map<Long, ConvertPropertyMeta>
): List<GenPropertyInput> {
    val protectDuplicateItems = mutableListOf<ProtectDuplicateItem>()

    propertiesMap.values.forEach { meta ->
        if (meta.enableBase)
            protectDuplicateItems += BaseProtectDuplicateItem(meta.baseProperty, meta)
        meta.associationPropertyPairs.map {
            protectDuplicateItems += AssociationProtectDuplicateItem(it.first, it.idView, meta, meta.baseProperty)
        }
    }

    val nameMap = protectDuplicateItems.groupBy { it.property.name }

    return nameMap.values.flatMap { produceDuplicateNameItems(it) }
}

fun produceDuplicateNameItems(items: List<ProtectDuplicateItem>): List<GenPropertyInput> =
    if (items.size == 1) {
        when(val item = items.first()) {
            is BaseProtectDuplicateItem ->
                listOf(item.property)
            is AssociationProtectDuplicateItem ->
                listOfNotNull(item.property, item.idView)
        }
    } else {
        items.flatMap { item ->
            when(item) {
                is BaseProtectDuplicateItem ->
                    throw ConvertEntityException.property("BaseProperty has same name [${item.property.name}]")
                is AssociationProtectDuplicateItem -> {
                    val (property, idView) = item
                    val mappedBy = property.mappedBy
                    if (mappedBy != null) {
                        val suffix = "For${mappedBy.replaceFirstChar { it.uppercaseChar() }}"
                        val newPropertyName = "${property.name}${suffix}"
                        listOfNotNull(
                            property.copy(
                                name = newPropertyName,
                                comment = "${property.comment} (映射自 ${property.type}.${mappedBy})"
                            ),
                            idView?.copy(
                                idViewTarget = newPropertyName,
                                name = "${idView.name}${suffix}",
                                comment = "${idView.comment} (映射自 ${property.type}.${mappedBy})"
                            )
                        )
                    } else {
                        throw ConvertEntityException.property("AssociationProperty has same name [${item.property.name}] and has no mappedBy")
                    }
                }
            }
        }
    }


/**
 * 防重名元素
 */
sealed class ProtectDuplicateItem(
    open val property: GenPropertyInput,
    open val meta: ConvertPropertyMeta,
)

/**
 * 基础属性防重名元素
 */
data class BaseProtectDuplicateItem(
    override val property: GenPropertyInput,
    override val meta: ConvertPropertyMeta,
) : ProtectDuplicateItem(
    property, meta
)

/**
 * 关联属性防重名元素
 */
data class AssociationProtectDuplicateItem(
    override val property: GenPropertyInput,
    val idView: GenPropertyInput?,
    override val meta: ConvertPropertyMeta,
    val baseProperty: GenPropertyInput,
) : ProtectDuplicateItem(
    property, meta
)