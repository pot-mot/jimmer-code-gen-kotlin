package top.potmot.core.template

import top.potmot.config.GenConfig
import top.potmot.model.dto.GenEntityPropertiesView

fun GenEntityPropertiesView.javaClassStringify(): String {
    return """package ${packagePath()};

${import()}

/**
 * $comment
 * $remark
 * @author ${GenConfig.author}
 * @since ${now()} 
 */
interface $name {
${properties.joinToString("") { it.javaPropertyStringify() }}
}"""
}

private fun GenEntityPropertiesView.TargetOf_properties.javaPropertyStringify(): String {
    return """
    /**
     * $comment
     * $remark
     */${annotation()}
    ${if (isNotNull) "@NotNull" else ""}
    ${type()} $name;
"""
}

private fun GenEntityPropertiesView.import(): String {
    return this.properties.flatMap { it.importList() }.distinct().joinToString("\n") { "import $it;" }
}

private fun GenEntityPropertiesView.TargetOf_properties.importList(): List<String> {
    val importList = importClassList().mapNotNull {
        it.qualifiedName
    }.toMutableList()

    importList += type

    if (isNotNull) {
        importList += org.jetbrains.annotations.Nullable::class.java.name
    }

    return importList
}
