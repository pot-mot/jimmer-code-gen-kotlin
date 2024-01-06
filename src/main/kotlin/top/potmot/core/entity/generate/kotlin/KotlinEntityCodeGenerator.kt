package top.potmot.core.entity.generate.kotlin

import top.potmot.core.database.generate.getIdentifierFilter
import top.potmot.core.entity.generate.EntityCodeBuilder
import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.extension.shortType
import top.potmot.model.dto.GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2 as TargetOfEnum

class KotlinEntityCodeGenerator: EntityCodeGenerator() {
    override fun getFileSuffix(): String = ".kt"

    override fun stringify(entity: GenEntityPropertiesView, dataSourceType: DataSourceType): String =
        entity.kotlinClassStringify(dataSourceType)

    override fun stringify(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2, dataSourceType: DataSourceType): String =
        enum.kotlinEnumStringify(dataSourceType)

    private fun GenEntityPropertiesView.kotlinClassStringify(dataSourceType: DataSourceType): String =
        EntityCodeBuilder(dataSourceType.getIdentifierFilter()).apply {
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

    private fun TargetOfEnum.kotlinEnumStringify(dataSourceType: DataSourceType): String =
        EntityCodeBuilder(dataSourceType.getIdentifierFilter()).apply {
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
