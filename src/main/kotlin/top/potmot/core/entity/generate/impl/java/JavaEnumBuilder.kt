package top.potmot.core.entity.generate.impl.java

import top.potmot.core.entity.generate.builder.EnumBuilder
import top.potmot.model.dto.PropertyEnum
import top.potmot.model.dto.PropertyEnumItem

object JavaEnumBuilder: EnumBuilder() {
    override fun packageLine(path: String): String = "package ${path};"

    override fun importLine(item: String): String = "import ${item};"

    override fun enumLine(enum: PropertyEnum): String =
        "public enum ${enum.name}"

    override fun itemLine(item: PropertyEnumItem): String =
        "${item.name},"
}
