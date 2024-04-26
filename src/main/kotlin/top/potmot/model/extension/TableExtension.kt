package top.potmot.model.extension

import top.potmot.enumeration.TableType
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.dto.share.ReferenceTable

fun GenTableAssociationsView.pkColumns(): List<GenTableAssociationsView.TargetOf_columns> =
    columns.filter { it.partOfPk }

fun GenTableAssociationsView.allSuperTables(): List<GenTableAssociationsView> {
    val result = superTables ?: listOf()
    return result + result.flatMap { it.allSuperTables() }
}

fun ReferenceTable.allInheritTables(): List<ReferenceTable> {
    val result = inheritTables ?: listOf()
    return result + result.flatMap { it.allInheritTables() }
}

fun ReferenceTable.allLeafTables(): List<ReferenceTable> {
    return allInheritTables().filter { it.type != TableType.SUPER_TABLE }
}
