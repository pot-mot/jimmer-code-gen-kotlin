package top.potmot.core.entity.generate.builder

import org.babyfish.jimmer.sql.EnumItem
import top.potmot.enumeration.EnumType
import top.potmot.entity.dto.GenPropertyEnum
import top.potmot.entity.dto.GenPropertyEnumItem
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines
import kotlin.reflect.KClass

abstract class EnumBuilder : CodeBuilder() {
    abstract fun enumLine(enum: GenPropertyEnum): String

    abstract fun itemLine(item: GenPropertyEnumItem): String

    fun build(enum: GenPropertyEnum): String =
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
                if (index < enum.items.size - 1) appendLine()
            }

            appendLine("}")
        }

    open fun importClasses(enum: GenPropertyEnum): Set<KClass<*>> {
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

    open fun importLines(enum: GenPropertyEnum): Set<String> =
        classesToLines(importClasses(enum))

    open fun blockComment(enum: GenPropertyEnum): String? =
        createBlockComment(
            enum.comment,
            enum.remark
        )

    open fun blockComment(enumItem: GenPropertyEnumItem): String? =
        createBlockComment(
            enumItem.comment,
            enumItem.remark
        )

    open fun annotationLines(enum: GenPropertyEnum): List<String> {
        val list = mutableListOf<String>()

        enum.enumType?.let {
            list += "@EnumType(EnumType.Strategy.$it)"
        }

        return list
    }

    open fun annotationBlock(
        enumItem: GenPropertyEnumItem,
        enumType: EnumType?
    ): String? =
        when (enumType) {
            EnumType.NAME -> "@EnumItem(name = \"${enumItem.mappedValue}\")"
            EnumType.ORDINAL -> "@EnumItem(ordinal = ${enumItem.mappedValue})"
            null -> null
        }
}
