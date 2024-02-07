package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.model.dto.GenEntityPropertiesView

object KotlinEntityCodeGenerator : EntityCodeGenerator() {
    override fun getFileSuffix(): String = ".kt"

    override fun stringify(entity: GenEntityPropertiesView): String =
        KotlinEntityBuilder.build(entity)

    override fun stringify(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): String =
        KotlinEnumBuilder.build(enum)
}
