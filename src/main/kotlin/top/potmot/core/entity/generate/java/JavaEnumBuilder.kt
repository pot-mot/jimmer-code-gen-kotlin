package top.potmot.core.entity.generate.java

import top.potmot.core.entity.generate.builder.EnumBuilder
import top.potmot.model.dto.GenEntityPropertiesView

class JavaEnumBuilder: EnumBuilder() {
    override fun packageLine(path: String): String = "package ${path};"

    override fun importLine(item: String): String = "import ${item};"

    override fun enumLine(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): String =
        "public enum ${enum.name}"

    override fun itemLine(item: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.TargetOf_items_3): String =
        "${item.name},"
}
