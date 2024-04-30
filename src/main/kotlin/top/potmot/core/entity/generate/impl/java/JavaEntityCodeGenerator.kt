package top.potmot.core.entity.generate.impl.java

import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.entity.dto.GenPropertyEnum

object JavaEntityCodeGenerator : EntityCodeGenerator() {
    override fun getFileSuffix(): String = ".java"

    override fun stringify(entity: GenEntityPropertiesView): String =
        JavaEntityBuilder.build(entity)

    override fun stringify(enum: GenPropertyEnum): String =
        JavaEnumBuilder.build(enum)
}
