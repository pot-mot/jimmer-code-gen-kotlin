package top.potmot.core.business.dto.generate

import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.enumeration.AssociationType
import top.potmot.utils.string.toSingular

object DtoGenerator {
    private fun formatFileName(
        entity: GenEntityPropertiesView,
    ): String =
        "${entity.name}.dto"

    private fun GenEntityPropertiesView.noIdView() =
        properties.count { it.idView } == 0 && properties.count { it.associationType != null } > 0

    private fun GenEntityPropertiesView.propertyIds(): List<String> =
        if (noIdView()) {
            properties
                .filter { it.associationType != null }
                .map { "id(${it.name})" }
        } else {
            properties
                .filter { it.idView }
                .map { it.name }
        }

    private fun GenEntityPropertiesView.propertyIdsTargetOne(): List<String> =
        if (noIdView()) {
            properties
                .filter { it.associationType == AssociationType.ONE_TO_ONE || it.associationType == AssociationType.MANY_TO_ONE }
                .map { "id(${it.name})" }
        } else {
            properties
                .filter { it.idView }
                .filter { it.associationType == AssociationType.ONE_TO_ONE || it.associationType == AssociationType.MANY_TO_ONE }
                .map { it.name }
        }


    fun generateListView(entity: GenEntityPropertiesView) = buildString {
        appendLine("${entity.name}ListView {")
        appendLine("    #allScalars")
        entity.propertyIdsTargetOne().forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    fun generateDetailView(entity: GenEntityPropertiesView) = buildString {
        appendLine("${entity.name}DetailView {")
        appendLine("    #allScalars")
        entity.propertyIds().forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    fun generateInsertInput(entity: GenEntityPropertiesView) = buildString {
            val idProperty = entity.properties.first { it.idProperty }
            val idName = idProperty.name

            appendLine("input ${entity.name}InsertInput {")
            appendLine("    #allScalars(this)")
            appendLine("    -${idName}")
            entity.propertyIds().forEach {
                appendLine("    $it")
            }
            appendLine("}")
        }

    fun generateUpdateInput(entity: GenEntityPropertiesView) = buildString {
        val idProperty = entity.properties.first { it.idProperty }
        val idName = idProperty.name

        appendLine("input ${entity.name}UpdateInput {")
        appendLine("    #allScalars(this)")
        appendLine("    ${idName}!")
        entity.propertyIds().forEach {
            appendLine("    $it")
        }
        appendLine("}")
    }

    fun generateSpec(entity: GenEntityPropertiesView) = buildString {
        appendLine("specification ${entity.name}Spec {")

        entity.properties.mapNotNull {
            if (it.associationType == null) {
                if (it.type.startsWith("java.time.")) {
                    appendLine("    le(${it.name})")
                    appendLine("    ge(${it.name})")
                } else if (it.type.endsWith("String")) {
                    appendLine("    like/i(${it.name})")
                } else {
                    it.name
                }
            } else if (!it.idView) {
                if (it.associationType == AssociationType.ONE_TO_ONE || it.associationType == AssociationType.MANY_TO_ONE) {
                    appendLine("    associationIdEq(${it.name})")
                } else {
                    appendLine("    associatedIdIn(${it.name}) as ${it.name.toSingular()}Ids")
                }
            } else {
                null
            }
        }

        appendLine("}")
    }

    fun stringify(entity: GenEntityPropertiesView): String {
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
        entity: GenEntityPropertiesView,
    ): Pair<String, String> =
        Pair(formatFileName(entity), stringify(entity))

    fun generateDto(
        entities: Collection<GenEntityPropertiesView>,
    ): List<Pair<String, String>> =
        entities
            .map { generateDto(it) }
            .distinct().sortedBy { it.first }
}