package top.potmot.entity.extension

import top.potmot.entity.dto.GenTableConvertView
import top.potmot.enumeration.TableType
import top.potmot.entity.dto.GenTableGenerateView

fun GenTableGenerateView.pkColumns(): List<GenTableGenerateView.TargetOf_columns> =
    columns.filter { it.partOfPk }

/**
 * 获取全部上级表
 */
fun GenTableGenerateView.allSuperTables(): List<GenTableGenerateView> {
    val result = superTables ?: listOf()
    return result + result.flatMap { it.allSuperTables() }
}

/**
 * 获取全部下级表，并传播逻辑删除
 */
fun GenTableConvertView.allInheritTables(): List<GenTableConvertView> {
    val result = inheritTables ?: listOf()
    return (result + result.flatMap { it.allInheritTables() })
        .map { if (logicalDelete) copy(logicalDelete = true) else it }
}

/**
 * 获取全部叶子表，即从自身开始到下级全部非 SUPER_TABLE 的表
 */
fun GenTableConvertView.allLeafTables(): List<GenTableConvertView> {
    return (allInheritTables() + this).filter { it.type != TableType.SUPER_TABLE }
}
