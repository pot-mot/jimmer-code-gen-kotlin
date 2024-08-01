package top.potmot.entity.extension

import top.potmot.enumeration.TableType
import top.potmot.entity.dto.GenTableAssociationsView
import top.potmot.entity.dto.share.ReferenceTable

fun GenTableAssociationsView.pkColumns(): List<GenTableAssociationsView.TargetOf_columns> =
    columns.filter { it.partOfPk }

/**
 * 获取全部上级表
 */
fun GenTableAssociationsView.allSuperTables(): List<GenTableAssociationsView> {
    val result = superTables ?: listOf()
    return result + result.flatMap { it.allSuperTables() }
}

/**
 * 获取全部下级表
 */
fun ReferenceTable.allInheritTables(): List<ReferenceTable> {
    val result = inheritTables ?: listOf()
    return result + result.flatMap { it.allInheritTables() }
}

/**
 * 获取全部叶子表，即下级非 SUPER_TABLE 的实表
 */
fun ReferenceTable.allLeafTables(): List<ReferenceTable> {
    return allInheritTables().filter { it.type != TableType.SUPER_TABLE }
}
