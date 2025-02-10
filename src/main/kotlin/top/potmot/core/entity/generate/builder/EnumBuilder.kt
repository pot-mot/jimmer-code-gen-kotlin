package top.potmot.core.entity.generate.builder

import kotlin.reflect.KClass
import org.babyfish.jimmer.sql.EnumItem
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.GenEnumItemGenerateView
import top.potmot.enumeration.EnumType
import top.potmot.utils.string.buildScopeString

abstract class EnumBuilder : CodeBuilder() {
    abstract fun enumLine(enum: GenEnumGenerateView): String

    abstract fun itemLine(item: GenEnumItemGenerateView): String

    fun build(enum: GenEnumGenerateView): String =
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

    open fun importClasses(enum: GenEnumGenerateView): Set<KClass<*>> {
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

    open fun importLines(enum: GenEnumGenerateView): Set<String> =
        classesToLines(importClasses(enum))

    open fun blockComment(enum: GenEnumGenerateView): String? =
        createBlockComment(
            enum.comment,
            enum.remark
        )

    open fun blockComment(enumItem: GenEnumItemGenerateView): String? =
        createBlockComment(
            enumItem.comment,
            enumItem.remark
        )

    open fun annotationLines(enum: GenEnumGenerateView): List<String> {
        val list = mutableListOf<String>()

        enum.enumType?.let {
            list += "@EnumType(EnumType.Strategy.$it)"
        }

        return list
    }

    open fun annotationBlock(
        enumItem: GenEnumItemGenerateView,
        enumType: EnumType?,
    ): String? =
        when (enumType) {
            EnumType.NAME -> "@EnumItem(name = \"${enumItem.mappedValue}\")"
            EnumType.ORDINAL -> "@EnumItem(ordinal = ${enumItem.mappedValue})"
            null -> null
        }
}
