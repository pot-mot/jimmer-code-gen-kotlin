package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.builder.EnumBuilder
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.GenEnumItemGenerateView

object KotlinEnumBuilder : EnumBuilder() {
    override fun packageLine(path: String): String = "package $path"

    override fun importLine(item: String): String = "import $item"

    override fun enumLine(enum: GenEnumGenerateView): String =
        "enum class ${enum.name}"

    override fun itemLine(item: GenEnumItemGenerateView): String =
        "${item.name},"
}
