package top.potmot.core.entity.generate.impl.java

import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.model.dto.GenEntityPropertiesView

object JavaEntityCodeGenerator : EntityCodeGenerator() {
    override fun getFileSuffix(): String = ".java"

    override fun stringify(entity: GenEntityPropertiesView): String =
        JavaEntityBuilder.build(entity)

    override fun stringify(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): String =
        JavaEnumBuilder.build(enum)
}
