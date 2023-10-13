package top.potmot.core.template

import top.potmot.config.GenConfig
import top.potmot.core.generate.annotation
import top.potmot.core.generate.importClassList
import top.potmot.core.generate.importEntityList
import top.potmot.core.generate.now
import top.potmot.core.generate.packagePath
import top.potmot.core.generate.type
import top.potmot.model.dto.GenEntityPropertiesView

fun GenEntityPropertiesView.kotlinClassStringify(): String {
    return """package ${packagePath()}

${import()}

/**
 * $comment
 * $remark
 * @author ${GenConfig.author}
 * @since ${now()} 
 */
@Entity
interface $name {
${properties.joinToString("") { it.kotlinPropertyStringify() }}
}"""
}

private fun GenEntityPropertiesView.TargetOf_properties.kotlinPropertyStringify(): String {
    return """
    /**
     * $comment
     * $remark
     */${annotation()}
    val $name: ${type()}${if (isNotNull) "" else "?"}
"""
}

private fun GenEntityPropertiesView.import(): String {
    return this.properties.flatMap { it.importList() }.distinct().joinToString("\n") { "import $it" }
}

private fun GenEntityPropertiesView.TargetOf_properties.importList(): List<String> {
    val importList = importClassList().mapNotNull {
        it.qualifiedName
    }.toMutableList()

    importList += importEntityList()

    return importList.filter { it.isNotEmpty() }
}
