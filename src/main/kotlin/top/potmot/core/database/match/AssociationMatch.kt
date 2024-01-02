package top.potmot.core.database.match

import top.potmot.core.entity.convert.clearTableName
import top.potmot.enumeration.AssociationType
import top.potmot.model.dto.GenAssociationMatchView
import top.potmot.model.dto.GenColumnMatchView
import top.potmot.model.extension.newGenAssociationMatchView

/**
 * 两个库表之间关联判断的匹配函数
 * 其中 source （源实体）对应表为主表，target （目标实体）对应表为子表
 *
 * warning 请一定要保证 column 获取到了 table
 */
typealias AssociationMatch = (
    source: GenColumnMatchView,
    target: GenColumnMatchView
) -> GenAssociationMatchView?

/**
 * 简单主键列关联匹配
 * eq:      source                     target
 *          table2.table1_id        -> table1.id
 *          table2.table1_id        -> prefix_table1.id
 */
val simplePkColumnMatch: AssociationMatch = { source, target ->
    if (target.partOfPk && target.table.id != source.table.id) {
        val targetTableName = target.table.name.clearTableName()
        if ("${targetTableName}_${target.name}" == source.name) {
            newGenAssociationMatchView(AssociationType.MANY_TO_ONE, false, source, target)
        } else {
            null
        }
    } else {
        null
    }
}

/**
 * 含表名主键列关联匹配
 * eq:      source                     target
 *          table2.table1_id        -> table1.table1_id
 *          table2.table1_id        -> prefix_table1.table1_id
 */
val includeTableNamePkColumnMatch: AssociationMatch = { source, target ->
    if (target.partOfPk && target.table.id != source.table.id) {
        val targetTableName = target.table.name.clearTableName()
        if (target.name.contains(targetTableName) && target.name == source.name) {
            newGenAssociationMatchView(AssociationType.MANY_TO_ONE, false, source, target)
        } else {
            null
        }
    } else {
        null
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
    if (target.partOfPk && target.table.id != source.table.id) {

        val separator = "_"

        val targetMatchList =
            target.table.name.clearTableName().split(separator).takeLast(2) +
                    target.name.split(separator).takeLast(2)

        val sourceMatchList =
            source.table.name.clearTableName().split(separator).takeLast(2) +
                    source.name.split(separator).takeLast(2)

        if (sourceMatchList == targetMatchList) {
            newGenAssociationMatchView(AssociationType.MANY_TO_ONE, false, source, target)
        } else {
            null
        }
    } else {
        null
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
