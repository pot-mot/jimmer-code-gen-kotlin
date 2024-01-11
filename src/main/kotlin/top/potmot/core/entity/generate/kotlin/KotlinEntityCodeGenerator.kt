package top.potmot.core.entity.generate.kotlin

import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.model.dto.GenEntityPropertiesView

private val KOTLIN_ENTITY_BUILDER = KotlinEntityBuilder()

private val KOTLIN_ENUM_BUILDER = KotlinEnumBuilder()

class KotlinEntityCodeGenerator : EntityCodeGenerator() {
    override fun getFileSuffix(): String = ".kt"

    override fun stringify(entity: GenEntityPropertiesView): String =
        KOTLIN_ENTITY_BUILDER.build(entity)

    override fun stringify(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): String =
        KOTLIN_ENUM_BUILDER.build(enum)
}
