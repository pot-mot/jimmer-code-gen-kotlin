package top.potmot.core.entity.generate.builder

import org.babyfish.jimmer.sql.EnumItem
import top.potmot.enumeration.EnumType
import top.potmot.model.dto.PropertyEnum
import top.potmot.model.dto.PropertyEnumItem
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines
import kotlin.reflect.KClass

abstract class EnumBuilder : CodeBuilder() {
    abstract fun enumLine(enum: PropertyEnum): String

    abstract fun itemLine(item: PropertyEnumItem): String

    fun build(enum: PropertyEnum): String =
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

    open fun importClasses(enum: PropertyEnum): Set<KClass<*>> {
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

    open fun importLines(enum: PropertyEnum): Set<String> =
        classesToLines(importClasses(enum))

    open fun blockComment(enum: PropertyEnum): String? =
        createBlockComment(
            enum.comment,
            enum.remark
        )

    open fun blockComment(enumItem: PropertyEnumItem): String? =
        createBlockComment(
            enumItem.comment,
            enumItem.remark
        )

    open fun annotationLines(enum: PropertyEnum): List<String> {
        val list = mutableListOf<String>()

        enum.enumType?.let {
            list += "@EnumType(EnumType.Strategy.$it)"
        }

        return list
    }

    open fun annotationBlock(
        enumItem: PropertyEnumItem,
        enumType: EnumType?
    ): String? =
        when (enumType) {
            EnumType.NAME -> "@EnumItem(name = \"${enumItem.mappedValue}\")"
            EnumType.ORDINAL -> "@EnumItem(ordinal = ${enumItem.mappedValue})"
            null -> null
        }
}
