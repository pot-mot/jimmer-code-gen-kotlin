package top.potmot.core.business.dto.generate

import top.potmot.core.business.utils.dto
import top.potmot.core.business.utils.noIdView
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.enumeration.AssociationType
import top.potmot.utils.string.toSingular

object DtoGenerator {
    private fun formatFileName(
        entity: GenEntityBusinessView,
    ): String =
        "${entity.name}.dto"

    private fun GenEntityBusinessView.propertyIds(): List<String> =
        if (noIdView) {
            properties
                .filter { it.associationType != null }
                .map { "id(${it.name})" }
        } else {
            properties
                .filter { it.idView }
                .map { it.name }
        }

    private fun GenEntityBusinessView.propertyIdsTargetOne(): List<String> =
        if (noIdView) {
            properties
                .filter { it.associationType == AssociationType.ONE_TO_ONE || it.associationType == AssociationType.MANY_TO_ONE }
                .map { "id(${it.name})" }
        } else {
            properties
                .filter { it.idView }
                .filter { it.associationType == AssociationType.ONE_TO_ONE || it.associationType == AssociationType.MANY_TO_ONE }
                .map { it.name }
        }


    private fun generateListView(entity: GenEntityBusinessView) = buildString {
        appendLine("${entity.dto.listView} {")
        appendLine("    #allScalars")
        entity.propertyIdsTargetOne().forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    private fun generateDetailView(entity: GenEntityBusinessView) = buildString {
        appendLine("${entity.dto.detailView} {")
        appendLine("    #allScalars")
        entity.propertyIds().forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    private fun generateInsertInput(entity: GenEntityBusinessView) = buildString {
            val idProperty = entity.properties.first { it.idProperty }
            val idName = idProperty.name

            appendLine("input ${entity.dto.insertInput} {")
            appendLine("    #allScalars(this)")
            appendLine("    -${idName}")
            entity.propertyIds().forEach {
                appendLine("    $it")
            }
            appendLine("}")
        }

    private fun generateUpdateInput(entity: GenEntityBusinessView) = buildString {
        val idProperty = entity.properties.first { it.idProperty }
        val idName = idProperty.name

        appendLine("input ${entity.dto.updateInput} {")
        appendLine("    #allScalars(this)")
        appendLine("    ${idName}!")
        entity.propertyIds().forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    private fun generateSpec(entity: GenEntityBusinessView) = buildString {
        appendLine("specification ${entity.dto.spec} {")

        entity.properties.forEach {
            if (it.associationType == null) {
                if (it.enum != null) {
                    appendLine("    eq(${it.name})")
                } else if (it.type.startsWith("java.time.")) {
                    appendLine("    le(${it.name})")
                    appendLine("    ge(${it.name})")
                } else if (it.type.endsWith("String")) {
                    appendLine("    like/i(${it.name})")
                } else {
                    appendLine("    eq(${it.name})")
                }
            } else if (!it.idView) {
                if (it.associationType == AssociationType.ONE_TO_ONE || it.associationType == AssociationType.MANY_TO_ONE) {
                    appendLine("    associationIdEq(${it.name})")
                } else {
                    appendLine("    associatedIdIn(${it.name}) as ${it.name.toSingular()}Ids")
                }
            }
        }

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
    ): Pair<String, String> =
        Pair(formatFileName(entity), stringify(entity))

    fun generateDto(
        entities: Collection<GenEntityBusinessView>,
    ): List<Pair<String, String>> =
        entities
            .map { generateDto(it) }
            .distinct().sortedBy { it.first }
}