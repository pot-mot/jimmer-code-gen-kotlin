package top.potmot.model.extension

import org.babyfish.jimmer.sql.kt.ast.expression.all
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import top.potmot.core.entity.convert.SEPARATOR
import top.potmot.enumeration.TableType
import top.potmot.model.GenTable
import top.potmot.model.by
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

fun GenTableAssociationsView.toFull(): GenTableAssociationsView {
    val allSuperTables = allSuperTables()

    return copy(
        superTables = emptyList(),
        columns = columns + allSuperTables.flatMap { it.columns },
        inAssociations = inAssociations + allSuperTables.flatMap {
            it.inAssociations.map {inAssociation ->
                inAssociation.copy(name = inAssociation.name + SEPARATOR + name)
            }
        },
        outAssociations = outAssociations + allSuperTables.flatMap {
            it.outAssociations.map {outAssociation ->
                outAssociation.copy(name = outAssociation.name + SEPARATOR + name)
            }
        },
        indexes = indexes + allSuperTables.flatMap {
            it.indexes.map {index ->
                index.copy(name = index.name + SEPARATOR + name)
            }
        }
    )
}
