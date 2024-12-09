package top.potmot.core.entity.convert

import top.potmot.entity.dto.GenPropertyInput
import top.potmot.entity.dto.GenTableConvertView
import top.potmot.enumeration.targetOneAssociationTypes

/**
 * 初始化属性的业务配置
 */
fun initPropertyBusinessConfig(
    table: GenTableConvertView,
    properties: List<GenPropertyInput>
): List<GenPropertyInput> = properties.map { property ->
    if (property.idProperty) {
        property.copy(
            inListView = false,
            inDetailView = false,
            inOptionView = true,
            inUpdateInput = true,
        ).let {
            if (!property.idGenerationAnnotation.isNullOrBlank()) {
                it.copy(
                    inInsertInput = false,
                )
            } else {
                it
            }
        }
    } else if (property.logicalDelete) {
        property.copy(
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
    } else if (property.associationType != null) {
        val isSelfAssociation = property.typeTableId == table.id
        val isTargetOne = property.associationType in targetOneAssociationTypes

        if (!isTargetOne) {
            property.copy(
                inListView = isSelfAssociation,
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
            property.copy(
                inOptionView = isSelfAssociation,
            )
        }
    } else {
        property
    }
}