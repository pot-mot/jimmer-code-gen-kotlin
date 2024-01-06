package top.potmot.core.entity.generate

import top.potmot.config.GenConfig
import top.potmot.core.entity.generate.java.JavaEntityCodeGenerator
import top.potmot.core.entity.generate.kotlin.KotlinEntityCodeGenerator
import top.potmot.enumeration.DataSourceType
import top.potmot.enumeration.GenLanguage
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2 as PropertyEnum

private val KOTLIN_GENERATOR = KotlinEntityCodeGenerator()

private val JAVA_GENERATOR = JavaEntityCodeGenerator()

fun GenLanguage?.getEntityGenerator(): EntityCodeGenerator =
    when (this ?: GenConfig.language) {
        GenLanguage.KOTLIN -> KOTLIN_GENERATOR
        GenLanguage.JAVA -> JAVA_GENERATOR
    }

fun generateEntitiesCode(
    entities: Collection<GenEntityPropertiesView>,
    dataSourceType: DataSourceType?,
    language: GenLanguage?,
    withPath: Boolean?
): List<Pair<String, String>> =
    language.getEntityGenerator().let {
        entities.flatMap { entity ->
            it.generateWithEnums(entity, dataSourceType ?: GenConfig.dataSourceType, withPath ?: false)
        }
            .distinct()
    }

fun generateEnumsCode(
    enums: Collection<PropertyEnum>,
    dataSourceType: DataSourceType?,
    language: GenLanguage?,
    withPath: Boolean?
): List<Pair<String, String>> =
    language.getEntityGenerator().let {
        enums.map {enum ->
            it.generate(enum, dataSourceType ?: GenConfig.dataSourceType, withPath ?: false)
        }
    }
