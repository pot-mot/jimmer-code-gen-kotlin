package top.potmot.core.business.dto.generate

import top.potmot.core.business.property.EntityPropertyCategories
import top.potmot.core.business.utils.PropertyQueryType
import top.potmot.core.business.utils.dto
import top.potmot.core.business.utils.existValidItems
import top.potmot.core.business.utils.idProperty
import top.potmot.core.business.utils.queryType
import top.potmot.core.business.utils.selectOptionLabel
import top.potmot.core.business.utils.toFlat
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_properties
import top.potmot.error.ModelException
import top.potmot.utils.string.appendBlock
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

    private fun GenEntityBusinessView.targetOneId(
        onlyThis: Boolean = false,
    ): List<String> =
        targetOneProperties.filter { if (onlyThis) it.entityId == id else true }.map {
            if (it.idView) {
                it.name
            } else {
                "id(${it.name})"
            }
        }

    private fun generateListView(entity: GenEntityBusinessView) = buildString {
        val listViewProperties = entity.listViewProperties

        appendLine("${entity.dto.listView} {")
        appendLine("    #allScalars")
        entity.scalarProperties.exclude(listViewProperties).forEach {
            appendLine("    -${it.name}")
        }
        entity.copy(properties = listViewProperties).targetOneId().forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    private fun generateOptionView(entity: GenEntityBusinessView) = buildString {
        val idProperty = entity.idProperty
        val idName = idProperty.name
        val label = entity.selectOptionLabel

        appendLine("${entity.dto.optionView} {")
        appendLine("    $idName")
        label?.let { appendLine("   $it") }
        appendLine("}")
    }

    private fun generateDetailView(entity: GenEntityBusinessView) = buildString {
        val detailViewProperties = entity.detailViewProperties

        appendLine("${entity.dto.detailView} {")
        appendLine("    #allScalars")
        entity.scalarProperties.exclude(detailViewProperties).forEach {
            appendLine("    -${it.name}")
        }
        entity.copy(properties = detailViewProperties).targetOneId().forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    private fun generateInsertInput(entity: GenEntityBusinessView) = buildString {
        val insertInputProperties = entity.insertInputProperties
        val idProperty = entity.idProperty
        val idName = idProperty.name

        appendLine("input ${entity.dto.insertInput} {")
        appendLine("    #allScalars")
        appendLine("    -${idName}")
        entity.scalarProperties.exclude(insertInputProperties).forEach {
            appendLine("    -${it.name}")
        }
        entity.copy(properties = insertInputProperties).targetOneId().forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    private fun generateUpdateInput(entity: GenEntityBusinessView) = buildString {
        val updateInputProperties = entity.updateInputProperties
        val idProperty = entity.idProperty
        val idName = idProperty.name

        appendLine("input ${entity.dto.updateInput} {")
        appendLine("    #allScalars")
        appendLine("    ${idName}!")
        entity.scalarProperties.exclude(updateInputProperties).forEach {
            appendLine("    -${it.name}")
        }
        entity.copy(properties = updateInputProperties).targetOneId().forEach {
            appendLine("    $it")
        }
        appendLine("}")
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

    private fun generateSpec(entity: GenEntityBusinessView) = buildString {
        appendLine("specification ${entity.dto.spec} {")

        entity.specificationProperties
            .flatMap { it.specExpression }
            .forEach { appendLine("    $it") }

        appendLine("}")
    }

    private fun generateExistValidDto(entity: GenEntityBusinessView): String {
        val idProperty = entity.idProperty
        val idName = idProperty.name

        return entity.existValidItems.joinToString("\n\n") { item ->
            buildString {
                appendLine("specification ${item.dtoName} {")

                appendLine("    ne($idName) as $idName")
                item.scalarProperties.forEach {
                    appendLine("    eq(${it.name})")
                }
                item.associationPropertyPairs.forEach {
                    appendLine("    associatedIdEq(${it.first.name})")
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
        if (entity.canEdit) appendBlock(generateDetailView(entity))
        appendBlock(generateOptionView(entity))
        if (entity.canAdd) appendBlock(generateInsertInput(entity))
        if (entity.canEdit) appendBlock(generateUpdateInput(entity))
        appendBlock(generateSpec(entity))
        appendBlock(generateExistValidDto(entity))
    }.trim()

    @Throws(ModelException.IdPropertyNotFound::class)
    fun generateDto(
        entity: GenEntityBusinessView,
    ): Pair<String, String> {
        val flatEntity = entity.toFlat()

        return Pair(formatFileName(flatEntity), stringify(flatEntity))
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    fun generateDto(
        entities: Iterable<GenEntityBusinessView>,
    ): List<Pair<String, String>> =
        entities
            .map { generateDto(it) }
            .distinct().sortedBy { it.first }
}
