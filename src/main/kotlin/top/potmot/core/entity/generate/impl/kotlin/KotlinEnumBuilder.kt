package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.builder.EnumBuilder
import top.potmot.core.entity.generate.builder.EnumItemView
import top.potmot.core.entity.generate.builder.EnumView

object KotlinEnumBuilder : EnumBuilder() {
    override fun packageLine(path: String): String = "package $path"

    override fun importLine(item: String): String = "import $item"

    override fun enumLine(enum: EnumView): String =
        "enum class ${enum.name}"

    override fun itemLine(item: EnumItemView): String =
        "${item.name},"
}
