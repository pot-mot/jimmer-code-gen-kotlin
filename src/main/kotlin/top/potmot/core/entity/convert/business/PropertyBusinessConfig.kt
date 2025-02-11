package top.potmot.core.entity.convert.business

import top.potmot.core.entity.convert.PropertyInput

/**
 * 初始化属性的业务配置
 */
fun PropertyInput.initBusinessConfig() =
    if (idProperty) {
        copy(
            inListView = false,
            inDetailView = false,
            inOptionView = true,
            inUpdateInput = true,
        ).let {
            if (generatedId) {
                it.copy(
                    inInsertInput = false,
                )
            } else {
                it
            }
        }
    } else if (logicalDelete) {
        copy(
            inListView = false,
            inDetailView = false,
            inInsertInput = false,
            inUpdateInput = false,
            inSpecification = false,
            inOptionView = false,
            inShortAssociationView = false,
            inLongAssociationView = false,
            inLongAssociationInput = false,
        )
    } else if (columnId == null) {
        copy(
            inListView = false,
            inDetailView = false,
            inInsertInput = false,
            inUpdateInput = false,
            inSpecification = false,
            inOptionView = false,
            inShortAssociationView = false,
            inLongAssociationView = false,
            inLongAssociationInput = false,
        )
    } else {
        this
    }

fun PropertyInput.initAssociationBusinessConfig(tableId: Long) =
    if (associationType != null) {
        val isTargetOne = associationType.isTargetOne

        if (!isTargetOne) {
            copy(
                inListView = false,
                inDetailView = true,
                inInsertInput = false,
                inUpdateInput = false,
                inSpecification = false,
                inOptionView = false,
                inShortAssociationView = false,
                inLongAssociationView = false,
                inLongAssociationInput = false,
            )
        } else {
            copy(
                inOptionView = typeTableId == tableId,
            )
        }
    } else {
        this
    }