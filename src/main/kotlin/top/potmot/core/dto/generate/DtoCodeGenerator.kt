package top.potmot.core.dto.generate

import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.enumeration.AssociationType

object DtoCodeGenerator {
    private fun formatFileName(
        entity: GenEntityPropertiesView,
    ): String =
        "${entity.name}.dto"

    fun stringify(entity: GenEntityPropertiesView): String {
        val idProperty = entity.properties.first { it.idProperty }
        val idName = idProperty.name

        val specProperties = entity.properties.map {
            if (it.associationType == AssociationType.MANY_TO_ONE) {
                "associationIdEq(${it.name})"
            } else if (it.associationType != null) {
                null
            } else if (it.type.startsWith("java.time.")) {
                "le(${it.name})\nge(${it.name})"
            } else if (it.type.endsWith("String")) {
                "like/i(${it.name})"
            } else {
                it.name
            }
        }.joinToString("\n").split("\n").joinToString { "    " }

        return """
export ${entity.packagePath}.${entity.name}

${entity.name}View {
    #allScalars
}

input ${entity.name}InsertInput {
    #allScalars(this)
    -${idName}
}

input ${entity.name}UpdateInput {
    #allScalars(this)
    ${idName}!
}

specification ${entity.name}Spec {
    $specProperties
}
        """.trim()
    }

    fun generateDto(
        entity: GenEntityPropertiesView,
    ): Pair<String, String> =
        Pair(formatFileName(entity), stringify(entity))

    fun generateDtos(
        entities: Collection<GenEntityPropertiesView>,
    ): List<Pair<String, String>> =
        entities
            .map { generateDto(it) }
            .distinct().sortedBy { it.first }
}