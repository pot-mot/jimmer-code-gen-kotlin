package top.potmot.core.entity.generate.impl.java

import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.PropertyEnum

object JavaEntityCodeGenerator : EntityCodeGenerator() {
    override fun getFileSuffix(): String = ".java"

    override fun stringify(entity: GenEntityPropertiesView): String =
        JavaEntityBuilder.build(entity)

    override fun stringify(enum: PropertyEnum): String =
        JavaEnumBuilder.build(enum)
}
