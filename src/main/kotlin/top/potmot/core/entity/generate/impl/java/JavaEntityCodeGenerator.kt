package top.potmot.core.entity.generate.impl.java

import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenEnumGenerateView

object JavaEntityCodeGenerator : EntityCodeGenerator() {
    override fun getFileSuffix(): String = ".java"

    override fun stringify(entity: GenEntityGenerateView): String =
        JavaEntityBuilder.build(entity)

    override fun stringify(enum: GenEnumGenerateView): String =
        JavaEnumBuilder.build(enum)
}
