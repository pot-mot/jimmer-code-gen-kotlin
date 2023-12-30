package top.potmot.core.entity.generate

import top.potmot.config.GenConfig
import top.potmot.enumeration.GenLanguage
import top.potmot.model.dto.GenEntityPropertiesView

private val KOTLIN_GENERATOR = KotlinEntityCodeGenerator()

private val JAVA_GENERATOR = JavaEntityCodeGenerator()

fun GenLanguage?.getEntityGenerator(): EntityCodeGenerator =
    when (this ?: GenConfig.language) {
        GenLanguage.KOTLIN -> KOTLIN_GENERATOR
        GenLanguage.JAVA -> JAVA_GENERATOR
    }

fun generateEntityCode(
    entity: GenEntityPropertiesView,
    language: GenLanguage?,
    withPath: Boolean?
): List<Pair<String, String>> =
    language.getEntityGenerator().generateWithEnums(entity, withPath ?: false)
