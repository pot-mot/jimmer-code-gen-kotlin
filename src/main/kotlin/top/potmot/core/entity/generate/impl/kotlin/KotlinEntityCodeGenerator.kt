package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.entity.dto.GenPropertyEnum

object KotlinEntityCodeGenerator : EntityCodeGenerator() {
    override fun getFileSuffix(): String = ".kt"

    override fun stringify(entity: GenEntityPropertiesView): String =
        KotlinEntityBuilder.build(entity)

    override fun stringify(enum: GenPropertyEnum): String =
        KotlinEnumBuilder.build(enum)
}
