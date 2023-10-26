package top.potmot.core.template.entity

import top.potmot.model.dto.GenEntityPropertiesView

fun GenEntityPropertiesView.kotlinClassStringify(): String {
    return """package ${packagePath()}

import org.babyfish.jimmer.sql.Entity
${import()}

${blockComment()}
@Entity
interface $name {
${properties.joinToString("\n\n") { it.kotlinPropertyStringify() }}
}"""
}

private fun GenEntityPropertiesView.TargetOf_properties.kotlinPropertyStringify(): String {
    return """${blockComment()}${annotation()}
    val $name: ${shortTypeName()}${if (notNull) "" else "?"}"""
}

private fun GenEntityPropertiesView.import(): String =
    properties
        .flatMap { it.importList() }
        .distinct()
        .let { importListFilter(it) }
        .joinToString("\n") { "import $it" }

private fun GenEntityPropertiesView.TargetOf_properties.importList(): List<String> {
    val importList = importClassList().mapNotNull {
        it.qualifiedName
    }.toMutableList()

    importList += importEntityList()

    return importList.filter { it.isNotEmpty() }
}
