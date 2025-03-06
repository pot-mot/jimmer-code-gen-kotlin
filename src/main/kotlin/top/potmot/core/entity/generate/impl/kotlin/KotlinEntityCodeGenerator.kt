package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.enumeration.GenLanguage

object KotlinEntityCodeGenerator : EntityCodeGenerator {
    override val suffix = GenLanguage.KOTLIN.suffix

    override fun stringify(entity: GenEntityGenerateView): String =
        KotlinEntityBuilder.build(entity)

    override fun stringify(enum: GenEnumGenerateView): String =
        KotlinEnumBuilder.build(enum)
}
