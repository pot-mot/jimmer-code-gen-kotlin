package top.potmot.core.entity.generate

import top.potmot.config.GenConfig
import top.potmot.enumeration.GenLanguage
import top.potmot.model.dto.GenEntityPropertiesView

fun GenLanguage?.getEntityGenerator(): EntityCodeGenerator =
    when (this ?: GenConfig.language) {
        GenLanguage.KOTLIN -> KotlinEntityCodeGenerator()
        GenLanguage.JAVA -> JavaEntityCodeGenerator()
    }

fun generateEntityCode(
    entity: GenEntityPropertiesView,
    language: GenLanguage?
): List<Pair<String, String>> =
    language.getEntityGenerator().generateWithEnums(entity)
