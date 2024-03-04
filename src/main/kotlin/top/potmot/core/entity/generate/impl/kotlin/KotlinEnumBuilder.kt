package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.builder.EnumBuilder
import top.potmot.model.dto.PropertyEnum
import top.potmot.model.dto.PropertyEnumItem

object KotlinEnumBuilder : EnumBuilder() {
    override fun packageLine(path: String): String = "package $path"

    override fun importLine(item: String): String = "import $item"

    override fun enumLine(enum: PropertyEnum): String =
        "enum class ${enum.name}"

    override fun itemLine(item: PropertyEnumItem): String =
        "${item.name},"
}
