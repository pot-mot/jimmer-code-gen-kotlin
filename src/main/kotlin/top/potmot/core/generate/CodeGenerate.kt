package top.potmot.core.generate

import top.potmot.config.GenConfig
import top.potmot.core.template.javaClassStringify
import top.potmot.core.template.kotlinClassStringify
import top.potmot.enum.GenLanguage
import top.potmot.model.dto.GenEntityPropertiesView

fun generateCode(
    entity: GenEntityPropertiesView,
    language: GenLanguage = GenConfig.language
): Map<String, String> {
    return when (language) {
        GenLanguage.KOTLIN -> generateKotlin(entity)
        GenLanguage.JAVA -> generateJava(entity)
    }
}

private fun generateKotlin(
    entity: GenEntityPropertiesView
): Map<String, String> {
    val map = mutableMapOf<String, String>()

    map["${entity.name}.kt"] = entity.kotlinClassStringify()

    return map
}

private fun generateJava(
    entity: GenEntityPropertiesView
): Map<String, String> {
    val map = mutableMapOf<String, String>()

    map["${entity.name}.java"] = entity.javaClassStringify()

    return map
}
