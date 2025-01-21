package top.potmot.core.business.dto.generate

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.EnumProperty
import top.potmot.core.business.meta.ForceIdViewProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.PropertyQueryType
import top.potmot.core.business.view.generate.builder.rules.existValidItems
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException
import top.potmot.utils.string.StringIndentScopeBuilder
import top.potmot.utils.string.buildScopeString

object DtoGenerator {
    private fun formatFileName(
        entity: EntityBusiness,
    ): String =
        "${entity.name}.dto"

    private fun StringIndentScopeBuilder.dtoBlock(name: String, body: StringIndentScopeBuilder.() -> Unit) {
        line("$name {")
        scope {
            this.body()
        }
        line("}")
    }

    private fun Iterable<PropertyBusiness>.insect(
        includeProperties: Collection<PropertyBusiness>,
    ) =
        filter { it in includeProperties }

    private fun Iterable<PropertyBusiness>.exclude(
        includeProperties: Collection<PropertyBusiness>,
    ) =
        filter { it !in includeProperties }

    private val AssociationProperty.associationIdExpress
        get() =
            idView?.name
                ?: if (property.listType) {
                    "id(${name}) as $nameWithId"
                } else {
                    "id(${name})"
                }

    private fun StringIndentScopeBuilder.extractShortView(property: AssociationProperty) {
        val shortViewProperties = property.typeEntityBusiness.shortViewProperties

        if (shortViewProperties.isEmpty()) {
            append(property.associationIdExpress)
        } else {
            dtoBlock(property.name) {
                shortViewProperties.forEach {
                    line(it.name)
                }
            }
        }
    }

    private fun StringIndentScopeBuilder.extractLong(
        property: AssociationProperty,
        block: StringIndentScopeBuilder.(typeEntity: EntityBusiness) -> Unit
    ) {
        dtoBlock(property.name) {
            block(property.typeEntityBusiness)
        }
    }


    private fun StringIndentScopeBuilder.generateListViewBody(entity: EntityBusiness) {
        val listViewProperties = entity.listViewProperties

        line("#allScalars")
        entity.scalarProperties.exclude(listViewProperties).forEach {
            line("-${it.name}")
        }
        listViewProperties.filterIsInstance<AssociationProperty>().forEach {
            if (it.isShortView) {
                extractShortView(it)
            } else {
                line(it.associationIdExpress)
            }
        }
        entity.noColumnProperties.insect(listViewProperties).forEach {
            line(it.name)
        }
    }

    private fun StringIndentScopeBuilder.generateTreeViewBody(entity: EntityBusiness) {
        val listViewProperties = entity.listViewProperties

        val parentProperty = entity.parentProperty
        val childrenProperty = entity.childrenProperty

        line("#allScalars")
        entity.scalarProperties.exclude(listViewProperties).forEach {
            line("-${it.name}")
        }
        line("id(${parentProperty.name})")
        line("${childrenProperty.name}*")
        listViewProperties.filterIsInstance<AssociationProperty>()
            .filter {
                it.id != parentProperty.id && it.id != childrenProperty.id
            }
            .forEach {
                if (it.isShortView) {
                    extractShortView(it)
                } else {
                    line(it.associationIdExpress)
                }
            }
        entity.noColumnProperties.insect(listViewProperties).forEach {
            line(it.name)
        }
    }

    private fun StringIndentScopeBuilder.generateOptionViewBody(entity: EntityBusiness) {
        val optionViewProperties = entity.optionViewProperties

        if (entity.isTree) {
            line("id(${entity.parentProperty.name})")
        }
        optionViewProperties.forEach {
            if (it is AssociationProperty) {
                if (it.typeEntity.id != entity.id) {
                    line(it.associationIdExpress)
                }
            } else {
                line(it.name)
            }
        }
    }

    private fun StringIndentScopeBuilder.generateDetailViewBody(entity: EntityBusiness) {
        val detailViewProperties = entity.detailViewProperties

        line("#allScalars")
        entity.scalarProperties.exclude(detailViewProperties).forEach {
            line("-${it.name}")
        }
        detailViewProperties.filterIsInstance<AssociationProperty>().forEach {
            if (it.isLongAssociation) {
                extractLong(it) { typeEntity ->
                    generateDetailViewBody(typeEntity)
                }
            } else if (it.isShortView) {
                extractShortView(it)
            } else {
                line(it.associationIdExpress)
            }
        }
        entity.noColumnProperties.insect(detailViewProperties).forEach {
            line(it.name)
        }
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    private fun StringIndentScopeBuilder.generateInsertInputBody(entity: EntityBusiness) {
        val insertInputProperties = entity.insertInputProperties
        val idProperty = entity.idProperty

        line("#allScalars")
        if (!idProperty.property.idGenerationAnnotation.isNullOrBlank()) {
            line("-${idProperty.name}")
        }
        entity.scalarProperties.exclude(insertInputProperties).forEach {
            line("-${it.name}")
        }
        insertInputProperties.filterIsInstance<AssociationProperty>().forEach {
            if (it.isLongAssociation) {
                extractLong(it) { typeEntity ->
                    generateInsertInputBody(typeEntity)
                }
            } else {
                line(it.associationIdExpress)
            }
        }
        entity.noColumnProperties.insect(insertInputProperties).forEach {
            line(it.name)
        }
    }

    private fun StringIndentScopeBuilder.generateUpdateFillViewBody(entity: EntityBusiness) {
        val updateInputProperties = entity.updateInputProperties

        line("#allScalars")
        entity.scalarProperties.exclude(updateInputProperties).forEach {
            line("-${it.name}")
        }
        updateInputProperties.filterIsInstance<AssociationProperty>().forEach {
            if (it.isLongAssociation) {
                extractLong(it) { typeEntity ->
                    generateUpdateFillViewBody(typeEntity)
                }
            } else {
                line(it.associationIdExpress)
            }
        }
        entity.noColumnProperties.insect(updateInputProperties).forEach {
            line(it.name)
        }
    }

    enum class UpdateIdStrategy {
        FORCE_NOT_NULL,
        DEFAULT,
    }

    private fun StringIndentScopeBuilder.generateUpdateInputBody(
        entity: EntityBusiness,
        idStrategy: UpdateIdStrategy = UpdateIdStrategy.FORCE_NOT_NULL
    ) {
        val updateInputProperties = entity.updateInputProperties
        val idProperty = entity.idProperty

        line("#allScalars")
        if (idStrategy == UpdateIdStrategy.FORCE_NOT_NULL) {
            line(idProperty.name + "!")
        }
        entity.scalarProperties.exclude(updateInputProperties).forEach {
            line("-${it.name}")
        }
        updateInputProperties.filterIsInstance<AssociationProperty>().forEach {
            if (it.isLongAssociation) {
                extractLong(it) { typeEntity ->
                    generateUpdateInputBody(typeEntity, UpdateIdStrategy.DEFAULT)
                }
            } else {
                line(it.associationIdExpress)
            }
        }
        entity.noColumnProperties.insect(updateInputProperties).forEach {
            line(it.name)
        }
    }

    private val PropertyBusiness.specExpression
        get() =
            when (this) {
                is AssociationProperty ->
                    if (listType)
                        listOf("associatedIdIn(${name}) as $nameWithId")
                    else
                        listOf("associatedIdEq(${name})")

                is ForceIdViewProperty ->
                    if (listType)
                        listOf("associatedIdIn(${associationProperty.name}) as $name")
                    else
                        listOf("associatedIdEq(${associationProperty.name})")

                is EnumProperty ->
                    listOf("eq(${name})")

                else -> when (queryType) {
                    PropertyQueryType.EQ ->
                        listOf("eq(${name})")

                    PropertyQueryType.DATE_RANGE,
                    PropertyQueryType.TIME_RANGE,
                    PropertyQueryType.DATETIME_RANGE,
                    PropertyQueryType.INT_RANGE,
                    PropertyQueryType.FLOAT_RANGE,
                    ->
                        listOf("le(${name})", "ge(${name})")

                    PropertyQueryType.LIKE ->
                        listOf("like/i(${name})")
                }
            }

    private fun StringIndentScopeBuilder.generateSpecBody(entity: EntityBusiness) {
        lines(entity.specificationProperties.flatMap { it.specExpression })
    }

    private fun StringIndentScopeBuilder.generateExistValidDto(entity: EntityBusiness) {
        val idProperty = entity.idProperty
        val idName = idProperty.name

        entity.existValidItems.forEach { item ->
            dtoBlock("specification ${item.dtoName}") {
                line("ne($idName) as $idName")
                item.scalarProperties.forEach {
                    line("eq(${it.name})")
                }
                item.associationProperties.forEach {
                    line("associatedIdEq(${it.property.name})")
                }
            }
            line()
        }
    }


    @Throws(ModelException.IdPropertyNotFound::class)
    private fun stringify(entity: EntityBusiness) = buildScopeString {
        val dto = entity.dto

        line("export ${entity.packagePath}.${entity.name}")
        line()

        dtoBlock(dto.listView) {
            generateListViewBody(entity)
        }
        line()

        if (entity.isTree) {
            dtoBlock(dto.treeView) {
                generateTreeViewBody(entity)
            }
            line()
        }

        dtoBlock(dto.detailView) {
            generateDetailViewBody(entity)
        }
        line()

        dtoBlock(dto.optionView) {
            generateOptionViewBody(entity)
        }
        line()

        if (entity.canAdd) {
            dtoBlock("input ${dto.insertInput}") {
                generateInsertInputBody(entity)
            }
            line()
        }
        if (entity.canEdit) {
            dtoBlock(dto.updateFillView) {
                generateUpdateFillViewBody(entity)
            }
            line()

            dtoBlock("input ${dto.updateInput}") {
                generateUpdateInputBody(entity)
            }
            line()
        }

        dtoBlock("specification ${dto.spec}") {
            generateSpecBody(entity)
        }
        line()

        generateExistValidDto(entity)
    }.trim()

    @Throws(ModelException.IdPropertyNotFound::class)
    fun generateDto(
        entity: EntityBusiness,
    ) = GenerateFile(
        entity,
        "dto/${formatFileName(entity)}",
        stringify(entity),
        listOf(GenerateTag.BackEnd, GenerateTag.DTO)
    )

    @Throws(ModelException.IdPropertyNotFound::class)
    fun generateDto(
        entities: Iterable<EntityBusiness>,
    ): List<GenerateFile> =
        entities
            .map { generateDto(it) }
            .distinctBy { it.path }
            .sortedBy { it.path }
}
