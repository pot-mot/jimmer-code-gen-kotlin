package top.potmot.core.entity.generate

import top.potmot.core.entity.build.EntityCodeBuilder
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.extension.shortType
import top.potmot.model.dto.GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2 as TargetOfEnum

class KotlinEntityCodeGenerator: EntityCodeGenerator() {
    override fun formatFileName(name: String): String = "$name.kt"

    override fun stringify(entity: GenEntityPropertiesView): String =
        entity.kotlinClassStringify()

    override fun stringify(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): String =
        enum.kotlinEnumStringify()

    private fun GenEntityPropertiesView.kotlinClassStringify(): String =
        EntityCodeBuilder().apply {
            line("package $packagePath")

            separate()
            lines(importLines()) { "import $it" }
            separate()

            lines(blockComment())
            lines(annotationLines())
            line("interface $name {")

            increaseIndentation()
            properties.joinPartsProduce {
                lines(it.blockComment())
                lines(it.annotationLines())
                line("val ${it.name}: ${it.shortType()}${if (it.typeNotNull) "" else "?"}")
            }
            decreaseIndentation()

            line("}")

        }.build()

    private fun TargetOfEnum.kotlinEnumStringify(): String =
        EntityCodeBuilder().apply {
            line("package $packagePath")

            separate()
            lines(importLines()) { "import $it" }
            separate()

            lines(blockComment())
            lines(annotationLines())
            line("enum class $name {")

            increaseIndentation()
            items.joinPartsProduce(",\n\n") {
                lines(it.blockComment())
                lines(it.annotation(enumType))
                lineNoWrap(it.name)
            }
            decreaseIndentation()

            line("\n}")
        }.build()
}
