package top.potmot.core.template.entity

import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.extension.packagePath
import top.potmot.model.extension.shortType
import top.potmot.model.dto.GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2 as TargetOfEnum

/**
 * java 语言下的实体生成
 */

fun GenEntityPropertiesView.kotlinClassStringify(): String =
    EntityStringifyContext().apply {
        line("package ${packagePath()}")

        separate()
        lines(importLines()) { "import $it" }
        separate()

        lines(blockComment())
        lines(annotationLines())
        line("interface $name {")

        increaseIndentation()
        joinParts(properties.map { it.kotlinPropertyStringify() })
        decreaseIndentation()

        line("}")

    }.build()

private fun GenEntityPropertiesView.TargetOf_properties.kotlinPropertyStringify(): String =
    EntityStringifyContext().apply {
        lines(blockComment())
        lines(annotationLines())
        line("val $name: ${shortType()}${if (typeNotNull) "" else "?"}")
    }.build()

fun GenEntityPropertiesView.kotlinEnumsStringify(): List<Pair<String, String>> =
    properties.filter { it.enum != null }
        .map { it.enum!! }
        .map { Pair(name, it.kotlinEnumStringify()) }

private fun TargetOfEnum.kotlinEnumStringify(): String =
    EntityStringifyContext().apply {
        line("package ${packagePath()}")

        separate()
        line("import org.babyfish.jimmer.sql.EnumType")
        separate()

        lines(blockComment())
        lines(annotationLines())
        line("enum class $name {")

        increaseIndentation()
        joinParts(items.map { "${it.enumItemAnnotation(enumType)}\n${it.name};" })
        decreaseIndentation()

        line("}")
    }.build()
