package top.potmot.core.entity.convert.meta

import top.potmot.core.entity.convert.PropertyInput

/**
 * 转换属性过程中的元数据
 */
data class ConvertPropertyMeta(
    val baseProperty: PropertyInput,
    val associationPropertyPairs: MutableList<AssociationPropertyPairInterface>,
    var enableBase: Boolean = true,
) {
    val properties: List<PropertyInput>
        get() =
            if (enableBase)
                listOf(baseProperty) + associationPropertyPairs.flatMap {
                    listOfNotNull(
                        it.associationProperty,
                        it.idView
                    )
                }
            else
                associationPropertyPairs.flatMap { listOfNotNull(it.associationProperty, it.idView) }
}


/**
 * 关联属性对，由原始关联数据和 idView 组成
 */
interface AssociationPropertyPairInterface {
    val associationProperty: PropertyInput
    val idView: PropertyInput?
}

data class AssociationPropertyPair(
    override val associationProperty: PropertyInput,
    override val idView: PropertyInput? = null,
) : AssociationPropertyPairInterface
