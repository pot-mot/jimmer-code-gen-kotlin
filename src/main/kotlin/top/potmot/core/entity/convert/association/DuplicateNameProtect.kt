package top.potmot.core.entity.convert.association

import top.potmot.core.entity.convert.meta.ConvertPropertyMeta
import top.potmot.entity.dto.GenPropertyInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.entity.dto.IdName
import top.potmot.error.ConvertException
import top.potmot.error.PropertyNameDuplicateData

/**
 * 对属性处理名称重复：
 *  如果存在名称重复，则将基础属性拼接到关联属性名称之后
 */
@Throws(ConvertException.PropertyNameDuplicate::class)
fun handleDuplicateName(
    table: GenTableConvertView,
    propertiesMap: Map<Long, ConvertPropertyMeta>,
): List<GenPropertyInput> {
    val protectDuplicateItems = mutableListOf<ProtectDuplicateItem>()

    propertiesMap.values.forEach { meta ->
        if (meta.enableBase)
            protectDuplicateItems += BaseProtectDuplicateItem(meta.baseProperty, meta)
        meta.associationPropertyPairs.map {
            protectDuplicateItems += AssociationProtectDuplicateItem(
                it.associationProperty,
                it.idView,
                meta,
                meta.baseProperty
            )
        }
    }

    val nameMap = protectDuplicateItems.groupBy { it.property.name }

    val producedMappedByProperties = nameMap.flatMap { produceDuplicateNameMappedBy(it.value) }

    producedMappedByProperties.groupBy { it.name }.values.filter { it.size > 1 }.forEach {
        val propertyNameDuplicateItems = it.map { property ->
            PropertyNameDuplicateData(
                columnId = property.columnId,
                name = property.name,
                comment = property.comment,
                type = property.type,
                listType = property.listType,
                typeNotNull = property.typeNotNull,
                typeTableId = property.typeTableId,
                enumId = property.enumId,
                associationType = property.associationType,
                joinColumnMetas = property.joinColumnMetas,
                joinTableMeta = property.joinTableMeta,
                idView = property.idView,
                idViewTarget = property.idViewTarget,
            )
        }

        throw ConvertException.propertyNameDuplicate(
            "PropertyName [${it.first().name}] is Duplicate",
            table = IdName(table.id, table.name),
            duplicateName = it.first().name,
            properties = propertyNameDuplicateItems,
        )
    }

    return producedMappedByProperties
}

private fun produceDuplicateNameMappedBy(items: List<ProtectDuplicateItem>): List<GenPropertyInput> =
    if (items.size == 1) {
        when (val item = items.first()) {
            is BaseProtectDuplicateItem ->
                listOf(item.property)

            is AssociationProtectDuplicateItem ->
                listOfNotNull(item.property, item.idView)
        }
    } else {
        items.flatMap { item ->
            when (item) {
                is BaseProtectDuplicateItem ->
                    listOf(item.property)

                is AssociationProtectDuplicateItem -> {
                    val (property, idView) = item
                    val mappedBy = property.mappedBy
                    if (mappedBy != null) {
                        val suffix = "For${mappedBy.replaceFirstChar { it.uppercaseChar() }}"
                        val newPropertyName = "${property.name}${suffix}"
                        listOfNotNull(
                            property.copy(
                                name = newPropertyName,
                                comment = "${property.comment} (MappedBy ${property.type}.${mappedBy})"
                            ),
                            idView?.copy(
                                idViewTarget = newPropertyName,
                                name = "${idView.name}${suffix}",
                                comment = "${idView.comment} (MappedBy ${property.type}.${mappedBy})"
                            )
                        )
                    } else {
                        listOfNotNull(property, idView)
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