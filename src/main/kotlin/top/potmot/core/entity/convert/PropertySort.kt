package top.potmot.core.entity.convert

import top.potmot.entity.dto.GenPropertyInput

/**
 * 整理关联属性
 *
 * 处理名称重复的关联属性
 * 根据最终顺序设置 index
 */
fun sortProperties(
    properties: List<GenPropertyInput>
): List<GenPropertyInput> {
    return properties.mapIndexed { index, property ->
        property.copy(
            orderKey = index.toLong()
        )
    }
}