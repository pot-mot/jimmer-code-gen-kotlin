package top.potmot.extension

import top.potmot.enumeration.AssociationType
import top.potmot.model.dto.GenAssociationMatchView
import top.potmot.model.dto.GenColumnMatchView

fun newGenAssociationMatchView(
    associationType: AssociationType,
    source: GenColumnMatchView,
    target: GenColumnMatchView
): GenAssociationMatchView {
    return GenAssociationMatchView(
        associationType,
        GenAssociationMatchView.TargetOf_sourceColumn(
            source.id,
            GenAssociationMatchView.TargetOf_sourceColumn.TargetOf_table_2(source.table.id)
        ),
        GenAssociationMatchView.TargetOf_targetColumn(
            target.id,
            GenAssociationMatchView.TargetOf_targetColumn.TargetOf_table_2(target.table.id)
        ),
    )
}
