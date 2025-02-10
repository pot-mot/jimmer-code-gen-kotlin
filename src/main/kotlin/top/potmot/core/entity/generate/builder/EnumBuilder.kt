package top.potmot.core.entity.generate.builder

import kotlin.reflect.KClass
import org.babyfish.jimmer.sql.EnumItem
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.enumeration.EnumType
import top.potmot.utils.string.buildScopeString

typealias EnumView = GenEnumGenerateView
typealias EnumItemView = GenEnumGenerateView.TargetOf_items

abstract class EnumBuilder : CodeBuilder() {
    abstract fun enumLine(enum: EnumView): String

    abstract fun itemLine(item: EnumItemView): String

    fun build(enum: EnumView): String =
        buildScopeString {
            line(packageLine(enum.packagePath))

            line()
            lines(importLines(enum)) { importLine(it) }
            line()

            block(blockComment(enum))
            lines(annotationLines(enum))
            line(enumLine(enum) + " {")

            scope {
                enum.items.forEachIndexed { index, item ->
                    block(blockComment(item))
                    block(annotationBlock(item, enum.enumType))
                    line(itemLine(item))
                    if (index < enum.items.size - 1) line()
                }
            }

            line("}")
        }

    open fun importClasses(enum: EnumView): Set<KClass<*>> {
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

    open fun importLines(enum: EnumView): Set<String> =
        classesToLines(importClasses(enum))

    open fun blockComment(enum: EnumView): String? =
        createBlockComment(
            enum.comment,
            enum.remark
        )

    open fun blockComment(enumItem: EnumItemView): String? =
        createBlockComment(
            enumItem.comment,
            enumItem.remark
        )

    open fun annotationLines(enum: EnumView): List<String> {
        val list = mutableListOf<String>()

        enum.enumType?.let {
            list += "@EnumType(EnumType.Strategy.$it)"
        }

        return list
    }

    open fun annotationBlock(
        enumItem: EnumItemView,
        enumType: EnumType?,
    ): String? =
        when (enumType) {
            EnumType.NAME -> "@EnumItem(name = \"${enumItem.mappedValue}\")"
            EnumType.ORDINAL -> "@EnumItem(ordinal = ${enumItem.mappedValue})"
            null -> null
        }
}
