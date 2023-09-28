package top.potmot.core.match

import top.potmot.config.GenConfig
import top.potmot.model.dto.GenColumnMatchView
import top.potmot.core.convert.removePrefixes
import top.potmot.core.convert.removeSuffixes

/**
 * 两个库表之间关联判断的匹配函数
 * 其中 source （源实体）对应表为主表，target （目标实体）对应表为子表
 *
 * warning 请一定要保证 column 获取到了 table
 */
typealias AssociationMatch = (source: GenColumnMatchView, target: GenColumnMatchView) -> Boolean

/**
 * 简单主键列关联匹配
 *
 * eq:      source                     target
 *          table2.table1_id        -> table1.id
 *          table2.table1_id        -> prefix_table1.id
 */
val simplePkColumnMatch: AssociationMatch = { source, target ->
    if (target.isPk && target.table != null && source.table != null && target.table.id != source.table.id) {
        val targetTableName = target.table.name.removePrefixes().removeSuffixes()
        "${targetTableName}${GenConfig.separator}${target.name}" == source.name
    } else {
        false
    }
}

/**
 * 含表名主键列关联匹配
 *
 * eq:      source                     target
 *          table2.table1_id        -> table1.table1_id
 *          table2.table1_id        -> prefix_table1.table1_id
 */
val includeTableNamePkColumnMatch: AssociationMatch = { source, target ->
    if (target.isPk && target.table != null && source.table != null && target.table.id != source.table.id) {
        val targetTableName = target.table.name.removePrefixes().removeSuffixes()
        target.name.contains(targetTableName) && target.name == source.name
    } else {
        false
    }
}

/**
 * 后缀相似关联匹配
 *
 * 判断表的最后两段与列的最后两段的累加列表是否一致
 * eq:      source                     target
 *          item.group_id           -> item_group.id
 *          （取 item, group, id）      （取 item, group, id）
 */
val pkSuffixColumnMatch: AssociationMatch = { source, target ->
    if (target.isPk && target.table != null && source.table != null && target.table.id != source.table.id) {

        val separator = GenConfig.separator

        val targetMatchList =
            target.table.name.removePrefixes().removeSuffixes().split(separator).takeLast(2) +
                target.name.split(separator).takeLast(2)

        val sourceMatchList =
            source.table.name.removePrefixes().removeSuffixes().split(separator).takeLast(2) +
                source.name.split(separator).takeLast(2)

        sourceMatchList == targetMatchList
    } else {
        false
    }
}

/**
 * 计算两个字符串列表的后缀匹配列表
 * @param s1 第一个字符串列表
 * @param s2 第二个字符串列表
 * @return 后缀匹配的列表
 */
fun suffixMatch(s1: List<String>, s2: List<String>): List<String> {
    // 将字符串 s1 按分隔符进行分割，并反转列表
    val s1Reversed = s1.reversed()
    // 将字符串 s2 按分隔符进行分割，并反转列表
    val s2Reversed = s2.reversed()

    // 使用 zip 函数将两个列表进行对应元素的配对，再使用 takeWhile 函数获取匹配的元素对，并将其转换为列表
    return s1Reversed.zip(s2Reversed)
        .takeWhile { (a, b) -> a == b }
        .map { pair -> pair.first }
}
