package top.potmot.core.generate

import top.potmot.config.GenConfig
import top.potmot.core.template.entity.EntityBuilder
import top.potmot.core.template.entity.JavaEntityBuilder
import top.potmot.core.template.entity.KotlinEntityBuilder
import top.potmot.enumeration.GenLanguage
import top.potmot.model.dto.GenEntityPropertiesView

fun GenLanguage?.getEntityBuilder(): EntityBuilder =
    when (this ?: GenConfig.language) {
        GenLanguage.KOTLIN -> KotlinEntityBuilder()
        GenLanguage.JAVA -> JavaEntityBuilder()
    }

fun generateEntityCode(
    entity: GenEntityPropertiesView,
    language: GenLanguage?
): Map<String, String> =
    language.getEntityBuilder().generate(entity)
