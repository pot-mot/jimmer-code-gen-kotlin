package top.potmot.core.entity.generate.builder

import org.babyfish.jimmer.sql.EnumItem
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.sub.AnnotationWithImports
import top.potmot.enumeration.EnumType
import top.potmot.utils.collection.forEachJoinDo
import top.potmot.utils.string.buildScopeString

typealias EnumView = GenEnumGenerateView
typealias EnumItemView = GenEnumGenerateView.TargetOf_items

abstract class EnumBuilder : CodeBuilder() {
    abstract fun enumLine(enum: EnumView): String

    abstract fun itemLine(item: EnumItemView): String

    fun build(enum: EnumView): String =
        buildScopeString {
            line(packageLine(enum.packagePath))

            val (imports, annotations) = annotationWithImports(enum)

            line()
            lines(filterImports(enum.packagePath, imports)) { importLine(it) }
            line()

            block(blockComment(enum))
            annotations.forEach { block(it) }
            scope(enumLine(enum) + " {", "}") {
                enum.items.forEachJoinDo({
                    line()
                }) { item ->
                    block(blockComment(item))
                    block(annotationBlock(item, enum.enumType))
                    line(itemLine(item))
                }
            }
        }

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

    open fun annotationWithImports(enum: EnumView): AnnotationWithImports {
        val imports = mutableListOf<String>()
        val annotations = mutableListOf<String>()

        enum.enumType?.let {
            imports += org.babyfish.jimmer.sql.EnumType::class.java.name
            annotations += "@EnumType(EnumType.Strategy.$it)"

            if (enum.items.isNotEmpty()) {
                imports += EnumItem::class.java.name
            }
        }

        return AnnotationWithImports(
            imports = imports,
            annotations = annotations
        )
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
