package top.potmot.core.business.property

import top.potmot.core.business.utils.idProperty
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.enumeration.targetOneAssociationTypes
import top.potmot.utils.string.toSingular

// TODO 添加长短关联区分

interface EntityPropertyCategories {
    val GenEntityBusinessView.scalarProperties
        get() = properties.filter {
            !it.idProperty && it.associationType == null
        }

    val GenEntityBusinessView.targetOneProperties
        get() = properties.filter {
            it.associationType in targetOneAssociationTypes
        }

    val GenEntityBusinessView.selectProperties
        get() = targetOneProperties.filter {
            it.inSpecification || it.inInsertInput || it.inUpdateInput
        }.forceConvertIdView()

    val GenEntityBusinessView.specificationSelectProperties
        get() = targetOneProperties.filter {
            it.inSpecification
        }.forceConvertIdView()

    val GenEntityBusinessView.insertSelectProperties
        get() = targetOneProperties.filter {
            it.inInsertInput
        }.forceConvertIdView()

    val GenEntityBusinessView.updateSelectProperties
        get() = targetOneProperties.filter {
            it.inUpdateInput
        }.forceConvertIdView()

    val GenEntityBusinessView.editTableSelectProperties
        get() = targetOneProperties.filter {
            it.inLongAssociationInput
        }.forceConvertIdView()


    val GenEntityBusinessView.listViewProperties
        get() = properties
            .filter {
                it.inListView &&
                        (it.associationType == null || it.associationType in targetOneAssociationTypes)
            }
            .produceIdView()

    val GenEntityBusinessView.tableProperties
        get() = listViewProperties
            .filter { !it.idProperty }
            .forceConvertIdView()


    val GenEntityBusinessView.detailViewProperties
        get() = properties
            .filter {
                it.inDetailView &&
                        (it.associationType == null || it.associationType in targetOneAssociationTypes)
            }
            .produceIdView()


    val GenEntityBusinessView.specificationProperties
        get() = properties
            .filter {
                it.inSpecification && !it.idView
            }

    val GenEntityBusinessView.queryProperties
        get() = specificationProperties
            .filter {
                !it.idProperty &&
                        (it.associationType == null || it.associationType in targetOneAssociationTypes)
            }
            .forceConvertIdView()
            .map { it.copy(typeNotNull = false) }


    val GenEntityBusinessView.insertInputProperties
        get() = properties
            .filter {
                it.inInsertInput &&
                        !it.idProperty &&
                        (it.associationType == null || it.associationType in targetOneAssociationTypes)
            }
            .produceIdView()

    val GenEntityBusinessView.addFormProperties
        get() = insertInputProperties
            .forceConvertIdView()
            .produceEditNullable()

    val GenEntityBusinessView.addFormEditNullableProperty
        get() = insertInputProperties
            .forceConvertIdView()
            .filter { it.editNullable }

    val GenEntityBusinessView.addFormRulesProperties
        get() = insertInputProperties
            .forceConvertIdView()


    val GenEntityBusinessView.updateInputProperties
        get() = properties
            .filter {
                it.inUpdateInput &&
                        (it.associationType == null || it.associationType in targetOneAssociationTypes)
            }
            .produceIdView()

    val GenEntityBusinessView.editFormProperties
        get() = updateInputProperties
            .filter { !it.idProperty }
            .forceConvertIdView()

    val GenEntityBusinessView.editFormRulesProperties
        get() = updateInputProperties
            .forceConvertIdView()


    val GenEntityBusinessView.longAssociationInputProperties
        get() = properties
            .filter {
                it.inLongAssociationInput &&
                        (it.associationType == null || it.associationType in targetOneAssociationTypes)
            }
            .produceEditNullable()

    val GenEntityBusinessView.longAssociationViewProperties
        get() = properties
            .filter {
                it.inLongAssociationView &&
                        (it.associationType == null || it.associationType in targetOneAssociationTypes)
            }
            .produceIdView()

    val GenEntityBusinessView.editTableProperties
        get() = longAssociationInputProperties
            .filter { !it.idProperty }
            .forceConvertIdView()
            .produceEditNullable()

        val GenEntityBusinessView.editTableRulesProperties
        get() = longAssociationInputProperties
            .filter { !it.idProperty }
            .forceConvertIdView()

    val GenEntityBusinessView.viewSubTableProperties
        get() = longAssociationViewProperties
            .filter { !it.idProperty }
            .forceConvertIdView()

    // 将关联属性映射为 IdView
    private fun Iterable<TargetOf_properties>.produceIdView(): List<TargetOf_properties> {
        val producedProperties = mutableListOf<TargetOf_properties>()

        val idViewPropertyMap = filter { it.idView }.associateBy { it.idViewTarget }

        for (property in this) {
            // 若是基础属性，不进行处理
            if (property.associationType == null) {
                producedProperties.add(property)
                continue
            }

            // 若是 IdView 属性，忽略
            if (property.idView)
                continue

            // 若不具有 typeEntity, 忽略
            if (property.typeEntity == null)
                continue

            // 获取对应的 IdView 属性
            val idViewProperty = idViewPropertyMap[property.name]

            producedProperties.add(
                // 若 IdView 属性存在，则取 IdView，不然使用默认属性
                idViewProperty
                    ?.copy(
                        comment = property.comment,
                        typeEntity = property.typeEntity
                    )
                    ?: property
            )
        }

        return producedProperties
    }

    // 将关联属性*强行*转换为 IdView
    private fun Iterable<TargetOf_properties>.forceConvertIdView() =
        mapNotNull { property ->
            // 若不是关联属性，不进行处理
            if (property.associationType === null)
                return@mapNotNull property

            // 若不具有 typeEntity，忽略
            if (property.typeEntity == null)
                return@mapNotNull null

            // 若是 IdView 属性，不进行处理
            if (property.idView)
                return@mapNotNull property

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
        }

    // 判断属性是否需要在编辑场景中转换为可null
    private val TargetOf_properties.editNullable
        get() = idView && associationType in targetOneAssociationTypes && typeNotNull

    // 将编辑场景中必须转换为可null的属性处理为可null
    private fun Iterable<TargetOf_properties>.produceEditNullable(): List<TargetOf_properties> =
        map {
            if (it.editNullable) {
                it.copy(typeNotNull = false)
            } else {
                it
            }
        }
}