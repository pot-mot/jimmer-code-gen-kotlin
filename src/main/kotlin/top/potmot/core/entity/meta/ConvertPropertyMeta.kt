package top.potmot.core.entity.meta

import top.potmot.entity.dto.GenPropertyInput

/**
 * 转换属性过程中的元数据
 */
data class ConvertPropertyMeta(
    val baseProperty: GenPropertyInput,
    val associationPropertyPairs: MutableList<AssociationPropertyPairInterface>,
    var enableBase: Boolean = true,
) {
    val properties: List<GenPropertyInput>
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
    val associationProperty: GenPropertyInput
    val idView: GenPropertyInput?
}

data class AssociationPropertyPair(
    override val associationProperty: GenPropertyInput,
    override val idView: GenPropertyInput? = null,
) : AssociationPropertyPairInterface
