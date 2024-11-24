package top.potmot.core.business.property

import top.potmot.core.business.utils.idProperty
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.enumeration.targetOneAssociationTypes
import top.potmot.utils.string.toSingular

val TargetOf_properties.isShortAssociation
    get() = associationType != null && !idView && typeEntity != null && typeEntity.shortViewProperties.isNotEmpty()

fun Iterable<TargetOf_properties>.createIdViewTargetMap() =
    filter { it.idView }.associateBy { it.idViewTarget }


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


    val GenEntityBusinessView.listViewProperties: List<TargetOf_properties>
        get() {
            // TODO 需要考虑对多短关联

            val filteredProperties = properties.filter {
                it.inListView && (it.associationType == null || it.associationType in targetOneAssociationTypes)
            }

            val idViewTargetMap = filteredProperties.createIdViewTargetMap()

            return filteredProperties.mapNotNull {
                if (it.isShortAssociation) {
                    it
                } else {
                    it.produceIdView(idViewTargetMap)
                }
            }
        }

    val GenEntityBusinessView.tableProperties
        get() = listViewProperties
            .filter { !it.idProperty }
            .mapNotNull {
                if (it.isShortAssociation) {
                    it
                } else {
                    it.forceConvertIdView()
                }
            }


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
            .produceIdView()
            .produceEditNullable()

    val GenEntityBusinessView.editTableProperties
        get() = longAssociationInputProperties
            .filter { !it.idProperty }
            .forceConvertIdView()
            .produceEditNullable()

    val GenEntityBusinessView.editTableRulesProperties
        get() = longAssociationInputProperties
            .filter { !it.idProperty }
            .forceConvertIdView()


    val GenEntityBusinessView.longAssociationViewProperties
        get() = properties
            .filter {
                it.inLongAssociationView &&
                        (it.associationType == null || it.associationType in targetOneAssociationTypes)
            }
            .produceIdView()

    val GenEntityBusinessView.viewSubTableProperties
        get() = longAssociationViewProperties
            .filter { !it.idProperty }
            .forceConvertIdView()

    // 将关联属性映射为 IdView，若找不到 IdView 则保持原状
    // 适用于 dto 本身的处理
    private fun TargetOf_properties.produceIdView(
        idViewTargetMap: Map<String?, TargetOf_properties>,
    ): TargetOf_properties? {
        // 若是基础属性，不进行处理
        if (associationType == null) {
            return this
        }

        // 若是 IdView 属性，忽略
        if (idView)
            return null

        // 若不具有 typeEntity, 忽略
        if (typeEntity == null)
            return null

        // 获取对应的 IdView 属性
        val idViewProperty = idViewTargetMap[name]

        // 若 IdView 属性存在，则取 IdView，不然使用默认属性
        return idViewProperty?.copy(
            comment = comment,
            typeEntity = typeEntity
        ) ?: this
    }

    private fun Iterable<TargetOf_properties>.produceIdView(): List<TargetOf_properties> {
        val idViewTargetMap = createIdViewTargetMap()
        return mapNotNull { it.produceIdView(idViewTargetMap) }
    }

    // 将关联属性*强行*转换为 IdView
    // 适用于前端必然应用 dto id 函数的情况
    private fun TargetOf_properties.forceConvertIdView(): TargetOf_properties? {
        // 若不是关联属性，不进行处理
        if (associationType === null)
            return this

        // 若不具有 typeEntity，忽略
        if (typeEntity == null)
            return null

        // 若是 IdView 属性，不进行处理
        if (idView)
            return this

        return if (listType) {
            copy(
                name = "${name.toSingular()}Ids",
                type = typeEntity.idProperty.type,
                idView = true,
            )
        } else {
            copy(
                name = "${name}Id",
                type = typeEntity.idProperty.type,
                idView = true,
            )
        }
    }

    private fun Iterable<TargetOf_properties>.forceConvertIdView() =
        mapNotNull { it.forceConvertIdView() }

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