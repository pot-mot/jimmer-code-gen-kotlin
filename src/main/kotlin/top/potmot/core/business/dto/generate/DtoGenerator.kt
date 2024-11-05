package top.potmot.core.business.dto.generate

import top.potmot.core.business.utils.PropertyQueryType
import top.potmot.core.business.utils.targetOneProperties
import top.potmot.core.business.utils.dtoNames
import top.potmot.core.business.utils.queryType
import top.potmot.core.business.utils.toFlat
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.utils.string.toSingular

object DtoGenerator {
    private fun formatFileName(
        entity: GenEntityBusinessView,
    ): String =
        "${entity.name}.dto"

    private fun GenEntityBusinessView.targetOneId(onlyThis: Boolean): List<String> =
        targetOneProperties.filter { if (onlyThis) it.entityId == id else true }.map {
            if (it.idView) {
                it.name
            } else {
                "id(${it.name})"
            }
        }

    private fun generateListView(entity: GenEntityBusinessView) = buildString {
        appendLine("${entity.dtoNames.listView} {")
        appendLine("    #allScalars")
        entity.targetOneId(onlyThis = true).forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    private fun generateDetailView(entity: GenEntityBusinessView) = buildString {
        appendLine("${entity.dtoNames.detailView} {")
        appendLine("    #allScalars")
        entity.targetOneId(onlyThis = false).forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    private fun generateInsertInput(entity: GenEntityBusinessView) = buildString {
        val idProperty = entity.properties.first { it.idProperty }
        val idName = idProperty.name

        appendLine("input ${entity.dtoNames.insertInput} {")
        appendLine("    #allScalars(this)")
        appendLine("    -${idName}")
        entity.targetOneId(onlyThis = true).forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    private fun generateUpdateInput(entity: GenEntityBusinessView) = buildString {
        val idProperty = entity.properties.first { it.idProperty }
        val idName = idProperty.name

        appendLine("input ${entity.dtoNames.updateInput} {")
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
                PropertyQueryType.ENUM_SELECT ->
                    listOf("eq(${name})")

                PropertyQueryType.DATE_RANGE,
                PropertyQueryType.TIME_RANGE,
                PropertyQueryType.DATETIME_RANGE,
                PropertyQueryType.INT_RANGE,
                PropertyQueryType.FLOAT_RANGE ->
                    listOf("le(${name})", "ge(${name})")

                PropertyQueryType.LIKE ->
                    listOf("like/i(${name})")

                PropertyQueryType.ASSOCIATION_EQ ->
                    listOf("associatedIdEq(${name})")

                PropertyQueryType.ASSOCIATION_IN ->
                    listOf("associatedIdIn(${name}) as ${name.toSingular()}Ids")
            }

    private fun generateSpec(entity: GenEntityBusinessView) = buildString {
        appendLine("specification ${entity.dtoNames.spec} {")

        entity.properties
            .filterNot { it.idView }
            .flatMap { it.specExpression }
            .forEach { appendLine("    $it") }

        appendLine("}")
    }

    private fun stringify(entity: GenEntityBusinessView): String {
        return """
export ${entity.packagePath}.${entity.name}

${generateListView(entity)}
${generateDetailView(entity)}
${generateInsertInput(entity)}
${generateUpdateInput(entity)}
${generateSpec(entity)}
        """.trim()
    }

    fun generateDto(
        entity: GenEntityBusinessView,
    ): Pair<String, String> {
        val flatEntity = entity.toFlat()

        return Pair(formatFileName(flatEntity), stringify(flatEntity))
    }

    fun generateDto(
        entities: Iterable<GenEntityBusinessView>,
    ): List<Pair<String, String>> =
        entities
            .map { generateDto(it) }
            .distinct().sortedBy { it.first }
}