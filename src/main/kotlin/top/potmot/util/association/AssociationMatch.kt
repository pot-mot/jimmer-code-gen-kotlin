package top.potmot.util.association

import top.potmot.config.GenConfig
import top.potmot.model.dto.GenColumnMatchView
import top.potmot.util.convert.removePrefixes
import top.potmot.util.convert.removeSuffixes

/**
 * 两个库表之间关联判断的匹配函数
 * 其中 source （源实体）对应表为主表，target （目标实体）对应表为子表
 *
 * warning 请一定要保证 column 获取到了 table
 */
typealias AssociationMatch = (source: GenColumnMatchView, target: GenColumnMatchView) -> Boolean

val allMatch: AssociationMatch = { source, target ->
    simplePkColumnMatch(source, target) || includeTableNamePkColumnMatch(source, target) || suffixPkColumnMatch(source, target)
}

/**
 * 简单主键列关联
 * eq:      source                     target
 *          table2.table1_id        -> table1.id
 *          table2.table1_id        -> prefix_table1.id
 */
val simplePkColumnMatch: AssociationMatch = { source, target ->
    if (target.isPk && target.table!!.id != source.table!!.id) {
        val targetTableName = target.table.name.removePrefixes().removeSuffixes()
        "${targetTableName}${GenConfig.separator}${target.name}" == source.name
    } else {
        false
    }
}

/**
 * 含表名主键列关联
 * eq:      source                     target
 *          table2.table1_id        -> table1.table1_id
 *          table2.table1_id        -> prefix_table1.table1_id
 */
val includeTableNamePkColumnMatch: AssociationMatch = { source, target ->
    if (target.isPk && target.table!!.id != source.table!!.id) {
        val targetTableName = target.table.name.removePrefixes().removeSuffixes()
        target.name.contains(targetTableName) && target.name == source.name
    } else {
        false
    }
}

/**
 * 后缀相似关联
 * 粗糙匹配，仅判断最后 2 段是否一致
 * eq:      source                     target
 *          prefix_item.group_id           -> prefix_item_group.id
 */
val suffixPkColumnMatch: AssociationMatch = { source, target ->
    if (target.isPk && target.table!!.id != source.table!!.id) {
        val targetTableName = target.table.name.removePrefixes().removeSuffixes()
        val sourceTableName = source.table.name.removePrefixes().removeSuffixes()

        val targetMatchString = "${targetTableName}${GenConfig.separator}${target.name}"
        val sourceMatchString = "${sourceTableName}${GenConfig.separator}${source.name}"

        suffixMatch(targetMatchString, sourceMatchString).size >= 2
    } else {
        false
    }
}

/**
 * 计算两个字符串的后缀匹配列表
 * @param s1 第一个字符串
 * @param s2 第二个字符串
 * @return 后缀匹配的列表
 */
fun suffixMatch(s1: String, s2: String): List<String> {
    // 将字符串 s1 按分隔符进行分割，并反转列表
    val s1List = s1.split(GenConfig.separator).reversed()
    // 将字符串 s2 按分隔符进行分割，并反转列表
    val s2List = s2.split(GenConfig.separator).reversed()

    // 使用 zip 函数将两个列表进行对应元素的配对，再使用 takeWhile 函数获取匹配的元素对，并将其转换为列表
    return s1List.zip(s2List)
        .takeWhile { (a, b) -> a == b }
        .map { pair -> pair.first }
}
