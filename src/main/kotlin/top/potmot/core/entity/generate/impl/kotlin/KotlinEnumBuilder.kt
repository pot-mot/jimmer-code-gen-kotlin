package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.builder.EnumBuilder
import top.potmot.entity.dto.GenPropertyEnum
import top.potmot.entity.dto.GenPropertyEnumItem

object KotlinEnumBuilder : EnumBuilder() {
    override fun packageLine(path: String): String = "package $path"

    override fun importLine(item: String): String = "import $item"

    override fun enumLine(enum: GenPropertyEnum): String =
        "enum class ${enum.name}"

    override fun itemLine(item: GenPropertyEnumItem): String =
        "${item.name},"
}
