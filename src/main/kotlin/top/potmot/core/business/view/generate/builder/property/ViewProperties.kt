package top.potmot.core.business.view.generate.builder.property

import top.potmot.core.business.utils.idProperty
import top.potmot.core.business.utils.targetOneAssociationType
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.utils.string.toSingular

// TODO 添加长短关联区分

interface ViewProperties {
    val GenEntityBusinessView.selectProperties
        get() = properties.filter {
            it.associationType in targetOneAssociationType
        }.produceIdView()

    val GenEntityBusinessView.tableProperties
        get() = properties.filter {
            !it.idProperty && (it.associationType == null || it.associationType in targetOneAssociationType)
        }.produceIdView()

    val GenEntityBusinessView.queryProperties
        get() = properties.filter {
            !it.idProperty && (it.associationType == null || it.associationType in targetOneAssociationType)
        }.produceIdView().let { properties ->
            properties.map {
                it.copy(typeNotNull = false)
            }
        }

    val GenEntityBusinessView.insertInputProperties
        get() = properties.filter {
            !it.idProperty && (it.associationType == null || it.associationType in targetOneAssociationType)
        }.produceIdView()

    val GenEntityBusinessView.addFormProperties
        get() = insertInputProperties.produceIdViewNullable()

    val GenEntityBusinessView.addFormInsertInputNullableDiffProperty
        get() = insertInputProperties.filter { it.needChangeToNullable() }

    val GenEntityBusinessView.editFormProperties
        get() = properties.filter {
            it.associationType == null || it.associationType in targetOneAssociationType
        }.produceIdView()

    val GenEntityBusinessView.editTableProperties
        get() = editFormProperties.filter { !it.idProperty }.produceIdViewNullable()

    private fun Iterable<TargetOf_properties>.produceIdView(): List<TargetOf_properties> {
        val producedProperties = mutableListOf<TargetOf_properties>()

        val idViewPropertyMap = filter { it.idView }.associateBy { it.idViewTarget }

        for (property in this) {
            if (property.associationType != null) {
                if (property.idView) continue
                if (property.typeEntity == null) continue

                val idViewProperty = idViewPropertyMap[property.name]

                if (idViewProperty != null) {
                    producedProperties.add(idViewProperty.copy(
                        comment = property.comment,
                        typeEntity = property.typeEntity
                    ))
                    continue
                }

                producedProperties.add(
                    if (property.listType) {
                        property.copy(
                            name = "${property.name.toSingular()}Ids",
                            type = property.typeEntity.idProperty.type,
                            idView = true,
                        )
                    } else {
                        property.copy(
                            name = "${property.name}Id",
                            type = property.typeEntity.idProperty.type,
                            idView = true,
                        )
                    }
                )
            } else {
                producedProperties.add(property)
            }
        }

        return producedProperties
    }

    private fun TargetOf_properties.needChangeToNullable() =
        idView && associationType in targetOneAssociationType && typeNotNull

    private fun Iterable<TargetOf_properties>.produceIdViewNullable(): List<TargetOf_properties> =
        map {
            if (it.needChangeToNullable()) {
                it.copy(typeNotNull = false)
            } else {
                it
            }
        }
}