package top.potmot.model.extension

import top.potmot.core.meta.createFkName
import top.potmot.enumeration.AssociationType
import top.potmot.model.dto.GenAssociationMatchView
import top.potmot.model.dto.GenColumnMatchView

fun newGenAssociationMatchView(
    associationType: AssociationType,
    fake: Boolean,
    source: GenColumnMatchView,
    target: GenColumnMatchView
): GenAssociationMatchView =
    GenAssociationMatchView(
        createFkName(source.table.name, listOf(source.name), target.table.name, listOf(target.name)),
        associationType,
        fake,
        source.table.id,
        target.table.id,

        columnReferences = listOf(
            GenAssociationMatchView.TargetOf_columnReferences(
                source.id,
                target.id
            )
        )
    )
