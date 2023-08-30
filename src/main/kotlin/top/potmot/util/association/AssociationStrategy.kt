package top.potmot.util.association

import top.potmot.constant.GenConstants
import top.potmot.model.dto.GenColumnMatchView

/**
 * 两个库表之间关联判断的匹配函数
 * 其中 source （源实体）对应表为主表，target （目标实体）对应表为子表
 *
 * warning 请一定要保证 column 获取到了 table
 */
typealias AssociationMatch = (source: GenColumnMatchView, target: GenColumnMatchView) -> Boolean

object AssociationStrategy {
    /**
     * 简单关联，只匹配字段名称和类型
     */
    val simple: AssociationMatch = { source, target ->
        target.isPk && "${target.table!!.name}_${target.name}" == source.name
    }

    /**
     * 多对多映射关系
     */
    val mapping: AssociationMatch = { source, target ->
        source.table!!.name.lowercase().endsWith("mapping") &&
                target.name.lowercase().endsWith("id") &&
                "${target.table!!.name}_${target.name}" == source.name
    }

    /**
     * 后缀匹配
     */
    val suffix: AssociationMatch = { source, target ->
        target.table!!.id != source.table!!.id &&
                target.isPk &&
                suffixMatch(target.name, source.name) > 1
    }

    private fun suffixMatch(s1: String, s2: String): Int {
        val s1List = s1.split(GenConstants.SEPARATOR).reversed()
        val s2List = s2.split(GenConstants.SEPARATOR).reversed()
        return s1List.zip(s2List).takeWhile { (a, b) -> a == b }.count()
    }
}
