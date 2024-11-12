package top.potmot.core.business.view.generate.builder.property

import top.potmot.core.business.utils.idProperty
import top.potmot.core.business.utils.targetOneAssociationType
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.utils.string.toSingular

interface ViewProperties {
    val GenEntityBusinessView.selectProperties
        get() = properties.filter {
            it.associationType in targetOneAssociationType && it.typeEntity != null
        }.produceIdView()

    val GenEntityBusinessView.tableProperties
        get() = properties.filter {
            !it.idProperty && (it.associationType == null || it.associationType in targetOneAssociationType)
        }.produceIdView()

    val GenEntityBusinessView.queryProperties
        get() = properties.filter {
            !it.idProperty && (it.associationType == null || it.associationType in targetOneAssociationType)
        }.produceIdView()

    val GenEntityBusinessView.addFormProperties
        get() = properties.filter {
            !it.idProperty && (it.associationType == null || it.associationType in targetOneAssociationType)
        }.produceIdView()

    val GenEntityBusinessView.editFormProperties
        get() = properties.filter {
            it.associationType == null || it.associationType in targetOneAssociationType
        }.produceIdView()

    val GenEntityBusinessView.editTableProperties
        get() = properties.filter {
            !it.idProperty && (it.associationType == null || it.associationType in targetOneAssociationType)
        }.produceIdView()

    private fun List<TargetOf_properties>.produceIdView(): List<TargetOf_properties> {
        val producedProperties = mutableListOf<TargetOf_properties>()

        for (property in this) {
            if (property.associationType != null) {
                if (property.idView) continue
                if (property.typeEntity == null) continue
                if (property.typeEntity.idProperties.size != 1) continue

                if (property.listType) {
                    producedProperties.add(
                        property.copy(
                            name = "${property.name.toSingular()}Ids",
                            type = property.typeEntity.idProperty.type,
                            idView = true,
                        )
                    )
                } else {
                    producedProperties.add(
                        property.copy(
                            name = "${property.name}Id",
                            type = property.typeEntity.idProperty.type,
                            idView = true,
                        )
                    )
                }
            } else {
                producedProperties.add(property)
            }
        }

        return producedProperties
    }
}