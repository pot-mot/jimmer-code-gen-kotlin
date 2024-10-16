package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenEnumGenerateView

object KotlinEntityCodeGenerator : EntityCodeGenerator() {
    override fun getFileSuffix(): String = ".kt"

    override fun stringify(entity: GenEntityGenerateView): String =
        KotlinEntityBuilder.build(entity)

    override fun stringify(enum: GenEnumGenerateView): String =
        KotlinEnumBuilder.build(enum)
}
