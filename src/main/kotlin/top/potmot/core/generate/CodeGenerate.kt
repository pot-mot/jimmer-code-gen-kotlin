package top.potmot.core.generate

import top.potmot.config.GenConfig
import top.potmot.core.template.entity.javaClassStringify
import top.potmot.core.template.entity.javaEnumsStringify
import top.potmot.core.template.entity.kotlinClassStringify
import top.potmot.core.template.entity.kotlinEnumsStringify
import top.potmot.enumeration.GenLanguage
import top.potmot.model.dto.GenEntityPropertiesView

fun generateCode(
    entity: GenEntityPropertiesView,
    language: GenLanguage = GenConfig.language
): Map<String, String> =
    when (language) {
        GenLanguage.KOTLIN -> generateKotlin(entity)
        GenLanguage.JAVA -> generateJava(entity)
    }

private fun generateKotlin(
    entity: GenEntityPropertiesView
): Map<String, String> {
    val map = mutableMapOf<String, String>()

    map["${entity.name}.kt"] = entity.kotlinClassStringify()

    entity.kotlinEnumsStringify().forEach {
        map["${it.first}.kt"] = it.second
    }

    return map
}

private fun generateJava(
    entity: GenEntityPropertiesView
): Map<String, String> {
    val map = mutableMapOf<String, String>()

    map["${entity.name}.java"] = entity.javaClassStringify()

    entity.javaEnumsStringify().forEach {
        map["${it.first}.kt"] = it.second
    }

    return map
}
