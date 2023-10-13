package top.potmot.core.template

import top.potmot.config.GenConfig
import top.potmot.core.generate.annotation
import top.potmot.core.generate.importClassList
import top.potmot.core.generate.importEntityList
import top.potmot.core.generate.now
import top.potmot.core.generate.packagePath
import top.potmot.core.generate.type
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
     */${annotation()}${if (isNotNull) "\n    @NotNull" else ""}
    ${type()} $name;
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
