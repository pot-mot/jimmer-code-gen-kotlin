package top.potmot.util.extension

import top.potmot.enum.AssociationType
import top.potmot.model.dto.GenAssociationMatchView
import top.potmot.model.dto.GenColumnMatchView

fun newGenAssociationMatchView(
    associationType: AssociationType,
    source: GenColumnMatchView,
    target: GenColumnMatchView
): GenAssociationMatchView {
    return GenAssociationMatchView(
        associationType,
        GenAssociationMatchView.TargetOf_sourceColumn.create(
            source.id,
            GenAssociationMatchView.TargetOf_sourceColumn.TargetOf_table.create(source.table!!.id)
        ),
        GenAssociationMatchView.TargetOf_targetColumn.create(
            target.id,
            GenAssociationMatchView.TargetOf_targetColumn.TargetOf_table.create(target.table!!.id)
        ),
    )
}
