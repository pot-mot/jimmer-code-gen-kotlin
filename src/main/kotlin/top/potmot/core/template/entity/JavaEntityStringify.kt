package top.potmot.core.template.entity

import top.potmot.model.dto.GenEntityPropertiesView

fun GenEntityPropertiesView.javaClassStringify(): String {
    return """package ${packagePath()};

${import()}

${blockComment()}
@Entity
interface $name {
${properties.joinToString("") { it.javaPropertyStringify() }}
}"""
}

private fun GenEntityPropertiesView.TargetOf_properties.javaPropertyStringify(): String {
    return """
${blockComment()}${annotation()}${if (isNotNull) "\n    @NotNull" else ""}
    ${shortTypeName()} $name;
"""
}

private fun GenEntityPropertiesView.import(): String =
    properties.flatMap { it.importList() }.distinct().joinToString("\n") { "import $it;" }

private fun GenEntityPropertiesView.TargetOf_properties.importList(): List<String> {
    val importList = importClassList().mapNotNull {
        it.qualifiedName
    }.toMutableList()

    importList += importEntityList()

    if (isNotNull) {
        importList += org.jetbrains.annotations.Nullable::class.java.name
    }

    return importList.filter { it.isNotEmpty() }
}
