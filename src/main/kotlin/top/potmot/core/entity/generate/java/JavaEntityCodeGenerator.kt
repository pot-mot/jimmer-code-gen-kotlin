package top.potmot.core.entity.generate.java

import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.model.dto.GenEntityPropertiesView

private val JAVA_ENTITY_BUILDER = JavaEntityBuilder()

private val JAVA_ENUM_BUILDER = JavaEnumBuilder()

class JavaEntityCodeGenerator : EntityCodeGenerator() {
    override fun getFileSuffix(): String = ".java"

    override fun stringify(entity: GenEntityPropertiesView): String =
        JAVA_ENTITY_BUILDER.build(entity)

    override fun stringify(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): String =
        JAVA_ENUM_BUILDER.build(enum)
}
