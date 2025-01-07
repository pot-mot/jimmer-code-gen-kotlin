package top.potmot.entity.extension

import top.potmot.entity.dto.GenEntityBusinessView

/**
 * 获取全部上级实体
 */
fun GenEntityBusinessView.allSuperEntities(): List<GenEntityBusinessView> {
    val result = superEntities ?: listOf()
    return result + result.flatMap { it.allSuperEntities() }
}