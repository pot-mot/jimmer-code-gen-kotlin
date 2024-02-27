package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.PropertyEnum

object KotlinEntityCodeGenerator : EntityCodeGenerator() {
    override fun getFileSuffix(): String = ".kt"

    override fun stringify(entity: GenEntityPropertiesView): String =
        KotlinEntityBuilder.build(entity)

    override fun stringify(enum: PropertyEnum): String =
        KotlinEnumBuilder.build(enum)
}
