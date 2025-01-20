package top.potmot.core.business.dto.generate

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.PropertyQueryType
import top.potmot.core.business.view.generate.builder.rules.existValidItems
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.buildScopeString
import top.potmot.utils.string.toSingular

object DtoGenerator {
    private fun formatFileName(
        entity: EntityBusiness,
    ): String =
        "${entity.name}.dto"

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
                    "id(${name}) as ${name.toSingular()}Ids"
                } else {
                    "id(${name})"
                }

    private fun AssociationProperty.extractShortView(): String = buildScopeString {
        val shortViewProperties = typeEntity.properties.filter { it.inShortAssociationView }

        if (shortViewProperties.isEmpty()) {
            append(associationIdExpress)
        } else {
            line("$name {")
            scope {
                shortViewProperties.forEach {
                    line(it.name)
                }
            }
            append("}")
        }
    }


    private fun generateListView(entity: EntityBusiness) = buildScopeString {
        val listViewProperties = entity.listViewPropertyBusiness

        line("${entity.dto.listView} {")
        scope {
            line("#allScalars")
            entity.scalarPropertyBusiness.exclude(listViewProperties).forEach {
                line("-${it.name}")
            }
            listViewProperties.filterIsInstance<AssociationProperty>().forEach {
                if (it.isShortView) {
                    block(it.extractShortView())
                } else {
                    line(it.associationIdExpress)
                }
            }
            entity.noColumnPropertyBusiness.insect(listViewProperties).forEach {
                line(it.name)
            }
        }
        line("}")
    }

    private fun generateTreeView(entity: EntityBusiness) = buildScopeString {
        val listViewProperties = entity.listViewPropertyBusiness

        val parentProperty = entity.parentProperty
        val childrenProperty = entity.childrenProperty

        line("${entity.dto.treeView} {")
        scope {
            line("#allScalars")
            entity.scalarPropertyBusiness.exclude(listViewProperties).forEach {
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
                        block(it.extractShortView())
                    } else {
                        line(it.associationIdExpress)
                    }
                }
            entity.noColumnPropertyBusiness.insect(listViewProperties).forEach {
                line(it.name)
            }
        }
        line("}")
    }

    private fun generateOptionView(entity: EntityBusiness) = buildScopeString {
        val optionViewProperties = entity.optionViewPropertyBusiness

        line("${entity.dto.optionView} {")
        scope {
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
        line("}")
    }

    private fun generateDetailView(entity: EntityBusiness) = buildScopeString {
        val detailViewProperties = entity.detailViewPropertyBusiness

        line("${entity.dto.detailView} {")
        scope {
            line("#allScalars")
            entity.scalarPropertyBusiness.exclude(detailViewProperties).forEach {
                line("-${it.name}")
            }
            detailViewProperties.filterIsInstance<AssociationProperty>().forEach {
                if (it.isShortView) {
                    block(it.extractShortView())
                } else {
                    line(it.associationIdExpress)
                }
            }
            entity.noColumnPropertyBusiness.insect(detailViewProperties).forEach {
                line(it.name)
            }
        }
        line("}")
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    private fun generateInsertInput(entity: EntityBusiness) = buildScopeString {
        val insertInputProperties = entity.insertInputPropertyBusiness
        val idProperty = entity.idProperty

        line("input ${entity.dto.insertInput} {")
        scope {
            line("#allScalars")
            if (!idProperty.idGenerationAnnotation.isNullOrBlank()) {
                line("-${idProperty.name}")
            }
            entity.scalarPropertyBusiness.exclude(insertInputProperties).forEach {
                line("-${it.name}")
            }
            insertInputProperties.filterIsInstance<AssociationProperty>().forEach {
                line(it.associationIdExpress)
            }
            entity.noColumnPropertyBusiness.insect(insertInputProperties).forEach {
                line(it.name)
            }
        }
        line("}")
    }

    private fun generateUpdateFillView(entity: EntityBusiness) = buildScopeString {
        val updateInputProperties = entity.updateInputPropertyBusiness

        line("${entity.dto.updateFillView} {")
        scope {
            line("#allScalars")
            entity.scalarPropertyBusiness.exclude(updateInputProperties).forEach {
                line("-${it.name}")
            }
            updateInputProperties.filterIsInstance<AssociationProperty>().forEach {
                line(it.associationIdExpress)
            }
            entity.noColumnPropertyBusiness.insect(updateInputProperties).forEach {
                line(it.name)
            }
        }
        line("}")
    }

    private fun generateUpdateInput(entity: EntityBusiness) = buildScopeString {
        val updateInputProperties = entity.updateInputPropertyBusiness

        line("input ${entity.dto.updateInput} {")
        scope {
            line("#allScalars")
            updateInputProperties.firstOrNull { it.property.idProperty }?.let {
                line("${it.name}!")
            }
            entity.scalarPropertyBusiness.exclude(updateInputProperties).forEach {
                line("-${it.name}")
            }
            updateInputProperties.filterIsInstance<AssociationProperty>().forEach {
                line(it.associationIdExpress)
            }
            entity.noColumnPropertyBusiness.insect(updateInputProperties).forEach {
                line(it.name)
            }
        }
        line("}")
    }

    private val PropertyBusiness.specExpression
        get() =
            when (queryType) {
                PropertyQueryType.EQ,
                PropertyQueryType.ENUM_SELECT,
                ->
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

                PropertyQueryType.ASSOCIATION_ID_EQ ->
                    listOf("associatedIdEq(${name})")

                PropertyQueryType.ASSOCIATION_ID_IN ->
                    listOf("associatedIdIn(${name}) as ${name.toSingular()}Ids")
            }

    private fun generateSpec(entity: EntityBusiness) = buildScopeString {
        line("specification ${entity.dto.spec} {")
        scope {
            lines(entity.specificationPropertyBusiness.flatMap { it.specExpression })
        }
        line("}")
    }

    private fun generateExistValidDto(entity: EntityBusiness): String {
        val idProperty = entity.idProperty
        val idName = idProperty.name

        return entity.existValidItems.joinToString("\n\n") { item ->
            buildScopeString {
                line("specification ${item.dtoName} {")
                scope {
                    line("ne($idName) as $idName")
                    item.scalarProperties.forEach {
                        line("eq(${it.name})")
                    }
                    item.associationProperties.forEach {
                        line("associatedIdEq(${it.property.name})")
                    }
                }
                append("}")
            }
        }
    }


    @Throws(ModelException.IdPropertyNotFound::class)
    private fun stringify(entity: EntityBusiness) = buildString {
        appendLine("export ${entity.packagePath}.${entity.name}")
        appendLine()

        appendBlock(generateListView(entity))
        if (entity.isTree) appendBlock(generateTreeView(entity))
        if (entity.canEdit) appendBlock(generateDetailView(entity))
        appendBlock(generateOptionView(entity))
        if (entity.canAdd) appendBlock(generateInsertInput(entity))
        if (entity.canEdit) {
            appendBlock(generateUpdateFillView(entity))
            appendBlock(generateUpdateInput(entity))
        }
        appendBlock(generateSpec(entity))
        appendBlock(generateExistValidDto(entity))
    }.trim()

    @Throws(ModelException.IdPropertyNotFound::class)
    fun generateDto(
        entityBusiness: EntityBusiness,
    ) = GenerateFile(
        entityBusiness,
        "dto/${formatFileName(entityBusiness)}",
        stringify(entityBusiness),
        listOf(GenerateTag.BackEnd, GenerateTag.DTO)
    )

    @Throws(ModelException.IdPropertyNotFound::class)
    fun generateDto(
        entityBusinessList: Iterable<EntityBusiness>,
    ): List<GenerateFile> =
        entityBusinessList
            .map { generateDto(it) }
            .distinctBy { it.path }
            .sortedBy { it.path }
}
