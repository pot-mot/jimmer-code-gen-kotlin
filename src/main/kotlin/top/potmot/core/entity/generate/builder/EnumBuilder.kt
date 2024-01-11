package top.potmot.core.entity.generate.builder

import org.babyfish.jimmer.sql.EnumItem
import top.potmot.enumeration.EnumType
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines
import kotlin.reflect.KClass

abstract class EnumBuilder : CodeBuilder() {
    abstract fun enumLine(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): String

    abstract fun itemLine(item: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.TargetOf_items_3): String

    fun build(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): String =
        buildString {
            appendLine(packageLine(enum.packagePath))

            appendLine()
            appendLines(importLines(enum)) { importLine(it) }
            appendLine()

            appendBlock(blockComment(enum))
            appendLines(annotationLines(enum))
            appendLine(enumLine(enum) + " {")

            enum.items.forEachIndexed { index, item ->
                appendBlock(blockComment(item)) { "    $it" }
                appendBlock(annotationBlock(item, enum.enumType)) { "    $it" }
                appendLine("    ${itemLine(item)}")
                if (index < enum.items.size - 1) appendLine("    ")
            }

            appendLine("}")
        }

    open fun importClasses(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): Set<KClass<*>> {
        val result = mutableSetOf<KClass<*>>()

        enum.apply {
            if (enumType != null) {
                result += org.babyfish.jimmer.sql.EnumType::class
            }
            if (items.isNotEmpty() && enumType != null) {
                result += EnumItem::class
            }
        }

        return result
    }

    open fun importLines(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): Set<String> =
        classesToLines(importClasses(enum))

    open fun blockComment(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): String? =
        createBlockComment(
            enum.comment,
            enum.remark
        )

    open fun blockComment(enumItem: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.TargetOf_items_3): String? =
        createBlockComment(
            enumItem.comment,
            enumItem.remark
        )

    open fun annotationLines(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): List<String> {
        val list = mutableListOf<String>()

        enum.enumType?.let {
            list += "@EnumType(EnumType.Strategy.$it)"
        }

        return list
    }

    open fun annotationBlock(
        enumItem: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.TargetOf_items_3,
        enumType: EnumType?
    ): String? =
        when (enumType) {
            EnumType.NAME -> "@EnumItem(name = \"${enumItem.mappedValue}\")"
            EnumType.ORDINAL -> "@EnumItem(ordinal = ${enumItem.mappedValue})"
            null -> null
        }
}
