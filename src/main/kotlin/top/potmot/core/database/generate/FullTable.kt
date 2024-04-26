package top.potmot.core.database.generate

import top.potmot.constant.INHERIT_PLACEHOLDER
import top.potmot.constant.SEPARATOR
import top.potmot.model.dto.GenTableAssociationsView
import top.potmot.model.extension.allSuperTables

fun GenTableAssociationsView.toFull(): GenTableAssociationsView {
    val allSuperTables = allSuperTables()

    return copy(
        superTables = emptyList(),
        columns = columns + allSuperTables.flatMap { it.columns },
        inAssociations = inAssociations + allSuperTables.flatMap {
            it.inAssociations.map {inAssociation ->
                inAssociation.copy(name = createInheritIdentifier(inAssociation.name, name))
            }
        },
        outAssociations = outAssociations + allSuperTables.flatMap {
            it.outAssociations.map {outAssociation ->
                outAssociation.copy(name = createInheritIdentifier(outAssociation.name, name))
            }
        },
        indexes = indexes + allSuperTables.flatMap {
            it.indexes.map {index ->
                index.copy(name = createInheritIdentifier(index.name, name))
            }
        }
    )
}

/**
 * 使用当前表继承覆盖高级表的原 identifier
 */
private fun createInheritIdentifier(
    base: String,
    replacer: String
): String {
    val indexOfPlaceholder = base.indexOf(INHERIT_PLACEHOLDER)
    if (indexOfPlaceholder == -1) {
        return base + SEPARATOR + replacer
    } else {
        return base.replace(INHERIT_PLACEHOLDER, replacer)
    }
}
