package top.potmot.core.business.dto.generate

import top.potmot.core.business.utils.ExistByValid
import top.potmot.core.business.utils.ExistByValidItem
import top.potmot.core.business.utils.PropertyQueryType
import top.potmot.core.business.utils.dto
import top.potmot.core.business.utils.idProperty
import top.potmot.core.business.utils.queryType
import top.potmot.core.business.utils.selectOptionLabel
import top.potmot.core.business.utils.toFlat
import top.potmot.core.business.utils.upperName
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.enumeration.targetOneAssociationTypes
import top.potmot.error.ModelException
import top.potmot.utils.string.toSingular

object DtoGenerator : ExistByValid {
    private fun formatFileName(
        entity: GenEntityBusinessView,
    ): String =
        "${entity.name}.dto"

    private val GenEntityBusinessView.noIdView
        get() = properties.count { it.idView } == 0 && properties.count { it.associationType != null } > 0

    private val GenEntityBusinessView.targetOneProperties
        get() =
            if (noIdView)
                properties.filter { it.associationType in targetOneAssociationTypes }
            else
                properties.filter { it.associationType in targetOneAssociationTypes && it.idView }

    private fun GenEntityBusinessView.targetOneId(onlyThis: Boolean): List<String> =
        targetOneProperties.filter { if (onlyThis) it.entityId == id else true }.map {
            if (it.idView) {
                it.name
            } else {
                "id(${it.name})"
            }
        }

    private fun generateListView(entity: GenEntityBusinessView) = buildString {
        appendLine("${entity.dto.listView} {")
        appendLine("    #allScalars")
        entity.targetOneId(onlyThis = true).forEach {
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
        appendLine("${entity.dto.detailView} {")
        appendLine("    #allScalars")
        entity.targetOneId(onlyThis = false).forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    private fun generateInsertInput(entity: GenEntityBusinessView) = buildString {
        val idProperty = entity.idProperty
        val idName = idProperty.name

        appendLine("input ${entity.dto.insertInput} {")
        appendLine("    #allScalars(this)")
        appendLine("    -${idName}")
        entity.targetOneId(onlyThis = true).forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    @Throws(ModelException.IdPropertyNotFound::class)
    private fun generateUpdateInput(entity: GenEntityBusinessView) = buildString {
        val idProperty = entity.idProperty
        val idName = idProperty.name

        appendLine("input ${entity.dto.updateInput} {")
        appendLine("    #allScalars(this)")
        appendLine("    ${idName}!")
        entity.targetOneId(onlyThis = true).forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    private val GenEntityBusinessView.TargetOf_properties.specExpression
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

        entity.properties
            .filterNot { it.idView }
            .flatMap { it.specExpression }
            .forEach { appendLine("    $it") }

        appendLine("}")
    }

    private fun ExistByValidItem.dtoName(entityName: String) =
        entityName + "ExistBy" + properties.joinToString("And") { it.upperName } + "Spec"

    private val GenEntityBusinessView.TargetOf_properties.existByValidSpecExpression
        get() =
            if (associationType != null && !idView) {
                "associatedIdEq(${name})"
            } else {
                name
            }

    private fun generateExistByValidDto(entity: GenEntityBusinessView) =
        entity.existByValidItems.joinToString("\n") { item ->
            buildString {
                appendLine("specification ${item.dtoName(entity.name)} {")

                item.properties.forEach {
                    appendLine("    ${it.existByValidSpecExpression}")
                }

                appendLine("}")
            }
        }

    @Throws(ModelException.IdPropertyNotFound::class)
    private fun stringify(entity: GenEntityBusinessView): String {
        return """
export ${entity.packagePath}.${entity.name}

${generateListView(entity)}
${generateDetailView(entity)}
${generateOptionView(entity)}
${generateInsertInput(entity)}
${generateUpdateInput(entity)}
${generateSpec(entity)}
${generateExistByValidDto(entity)}
        """.trim()
    }

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