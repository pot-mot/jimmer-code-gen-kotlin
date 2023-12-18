package top.potmot.model.extension

import top.potmot.enumeration.AssociationType
import top.potmot.model.dto.GenAssociationMatchView
import top.potmot.model.dto.GenColumnMatchView

fun newGenAssociationMatchView(
    associationType: AssociationType,
    fake: Boolean,
    source: GenColumnMatchView,
    target: GenColumnMatchView
): GenAssociationMatchView {
    return GenAssociationMatchView(
        associationType,
        fake,
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
