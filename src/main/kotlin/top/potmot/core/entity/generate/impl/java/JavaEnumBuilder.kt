package top.potmot.core.entity.generate.impl.java

import top.potmot.core.entity.generate.builder.EnumBuilder
import top.potmot.core.entity.generate.builder.EnumItemView
import top.potmot.core.entity.generate.builder.EnumView

object JavaEnumBuilder : EnumBuilder() {
    override fun packageLine(path: String): String = "package ${path};"

    override fun importLine(item: String): String = "import ${item};"

    override fun enumLine(enum: EnumView): String =
        "public enum ${enum.name}"

    override fun itemLine(item: EnumItemView): String =
        "${item.name},"
}
