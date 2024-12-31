package top.potmot.core.business.dto.generate

import top.potmot.core.business.property.EntityPropertyCategories
import top.potmot.core.business.property.isShortAssociation
import top.potmot.core.business.property.PropertyQueryType
import top.potmot.core.business.utils.mark.dto
import top.potmot.core.business.utils.entity.existValidItems
import top.potmot.core.business.utils.entity.idProperty
import top.potmot.core.business.property.queryType
import top.potmot.core.business.utils.entity.toFlat
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.entity.dto.GenerateFile
import top.potmot.enumeration.GenerateTag
import top.potmot.error.ModelException
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.buildScopeString
import top.potmot.utils.string.toSingular

object DtoGenerator : EntityPropertyCategories {
    private fun formatFileName(
        entity: GenEntityBusinessView,
    ): String =
        "${entity.name}.dto"

    private fun Iterable<TargetOf_properties>.exclude(
        includeProperties: Collection<TargetOf_properties>,
    ) =
        filter { it !in includeProperties }

    private val TargetOf_properties.associationIdExpress
        get() =
            if (idView)
                name
            else {
                if (listType) {
                    "id(${name}) as ${name.toSingular()}Ids"
                } else {
                    "id(${name})"
                }
            }

    private fun TargetOf_properties.extractShortAssociation(): String = buildScopeString {
        if (typeEntity == null || typeEntity.shortViewProperties.isEmpty()) {
            append(associationIdExpress)
        } else {
            line("$name {")
            scope {
                typeEntity.shortViewProperties.forEach {
                    line(it.name)
                }
            }
            append("}")
        }
    }

    private val GenEntityBusinessView.associationIdExpress: List<String>
        get() = associationProperties.map {
            it.associationIdExpress
        }

    private fun generateListView(entity: GenEntityBusinessView) = buildScopeString {
        val listViewProperties = entity.listViewProperties

        line("${entity.dto.listView} {")
        scope {
            line("#allScalars")
            entity.scalarProperties.exclude(listViewProperties).forEach {
                line("-${it.name}")
            }
            listViewProperties.filter { it.associationType != null }.forEach {
                if (it.isShortAssociation) {
                    block(it.extractShortAssociation())
                } else {
                    line(it.associationIdExpress)
                }
            }
        }
        line("}")
    }

    private fun generateTreeView(entity: GenEntityBusinessView) = buildScopeString {
        val listViewProperties = entity.listViewProperties

        val parentProperty = entity.parentProperty
        val childrenProperty = entity.childrenProperty

        line("${entity.dto.treeView} {")
        scope {
            line("#allScalars")
            entity.scalarProperties.exclude(listViewProperties).forEach {
                line("-${it.name}")
            }
            line("id(${parentProperty.name})")
            line("${childrenProperty.name}*")
            listViewProperties.filter {
                it.associationType != null && it.typeEntity?.id != entity.id
            }.forEach {
                if (it.isShortAssociation) {
                    block(it.extractShortAssociation())
                } else {
                    line(it.associationIdExpress)
                }
            }
        }
        line("}")
    }

    private fun generateOptionView(entity: GenEntityBusinessView) = buildScopeString {
        val optionViewProperties = entity.optionViewProperties

        line("${entity.dto.optionView} {")
        scope {
            if (entity.isTreeEntity()) {
                line("id(${entity.parentProperty.name})")
            }
            optionViewProperties.filter {
                it.typeEntity?.id != entity.id
            }.forEach {
                if (it.associationType != null) {
                    line(it.associationIdExpress)
                } else {
                    line(it.name)
                }
            }
        }
        line("}")
    }

    private fun generateDetailView(entity: GenEntityBusinessView) = buildScopeString {
        val detailViewProperties = entity.detailViewProperties

        line("${entity.dto.detailView} {")
        scope {
            line("#allScalars")
            entity.scalarProperties.exclude(detailViewProperties).forEach {
                line("-${it.name}")
            }
            detailViewProperties.filter { it.associationType != null }.forEach {
                if (it.isShortAssociation) {
                    block(it.extractShortAssociation())
                } else {
                    line(it.associationIdExpress)
                }
            }
        }
        line("}")
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    private fun generateInsertInput(entity: GenEntityBusinessView) = buildScopeString {
        val insertInputProperties = entity.insertInputProperties
        val idProperty = entity.idProperty

        line("input ${entity.dto.insertInput} {")
        scope {
            line("#allScalars")
            if (!idProperty.idGenerationAnnotation.isNullOrBlank()) {
                line("-${idProperty.name}")
            }
            entity.scalarProperties.exclude(insertInputProperties).forEach {
                line("-${it.name}")
            }
            lines(entity.copy(properties = insertInputProperties).associationIdExpress)
        }
        line("}")
    }

    private fun generateUpdateFillView(entity: GenEntityBusinessView) = buildScopeString {
        val updateInputProperties = entity.updateInputProperties

        line("${entity.dto.updateFillView} {")
        scope {
            line("#allScalars")
            entity.scalarProperties.exclude(updateInputProperties).forEach {
                line("-${it.name}")
            }
            lines(entity.copy(properties = updateInputProperties).associationIdExpress)
        }
        line("}")
    }

    private fun generateUpdateInput(entity: GenEntityBusinessView) = buildScopeString {
        val updateInputProperties = entity.updateInputProperties

        line("input ${entity.dto.updateInput} {")
        scope {
            line("#allScalars")
            updateInputProperties.firstOrNull { it.idProperty }?.let {
                line("${it.name}!")
            }
            entity.scalarProperties.exclude(updateInputProperties).forEach {
                line("-${it.name}")
            }
            lines(entity.copy(properties = updateInputProperties).associationIdExpress)
        }
        line("}")
    }

    private val TargetOf_properties.specExpression
        get() =
            if (idProperty)
                listOf("eq(${name})")
            else when (queryType) {
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

    private fun generateSpec(entity: GenEntityBusinessView) = buildScopeString {
        line("specification ${entity.dto.spec} {")
        scope {
            lines(entity.specificationProperties.flatMap { it.specExpression })
        }
        line("}")
    }

    private fun generateExistValidDto(entity: GenEntityBusinessView): String {
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
                    item.associationPropertyPairs.forEach {
                        line("associatedIdEq(${it.first.name})")
                    }
                }
                append("}")
            }
        }
    }


    @Throws(ModelException.IdPropertyNotFound::class)
    private fun stringify(entity: GenEntityBusinessView) = buildString {
        appendLine("export ${entity.packagePath}.${entity.name}")
        appendLine()

        appendBlock(generateListView(entity))
        if (entity.isTreeEntity()) appendBlock(generateTreeView(entity))
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
        entity: GenEntityBusinessView,
    ): GenerateFile {
        val flatEntity = entity.toFlat()

        return GenerateFile(
            entity,
            "dto/${formatFileName(entity)}",
            stringify(flatEntity),
            listOf(GenerateTag.BackEnd, GenerateTag.DTO)
        )
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    fun generateDto(
        entities: Iterable<GenEntityBusinessView>,
    ): List<GenerateFile> =
        entities
            .map { generateDto(it) }
            .sortedBy { it.path }
}
