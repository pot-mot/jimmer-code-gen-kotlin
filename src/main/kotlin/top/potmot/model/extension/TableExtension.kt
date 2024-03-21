package top.potmot.model.extension

import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import top.potmot.model.GenTable
import top.potmot.model.by
import top.potmot.model.dto.GenTableAssociationsView

fun GenTableAssociationsView.pkColumns(): List<GenTableAssociationsView.TargetOf_columns> =
    columns.filter { it.partOfPk }

fun GenTableAssociationsView.allSuperTables(): List<GenTableAssociationsView> {
    val result = superTables ?: listOf()
    return result + result.flatMap { it.allSuperTables() }
}

fun GenTableAssociationsView.toFull(): GenTableAssociationsView = copy(
    superTables = emptyList(),
    columns = allSuperTables().flatMap { it.columns } + columns
)

val GenTableAssociationsOneDeepSuperTableFetcher = newFetcher(GenTable::class).by(GenTableAssociationsView.METADATA.fetcher) {
    `superTables*` {
        depth(1)
    }
}
