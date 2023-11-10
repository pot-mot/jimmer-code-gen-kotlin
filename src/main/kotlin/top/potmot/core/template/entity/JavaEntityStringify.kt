package top.potmot.core.template.entity

import org.jetbrains.annotations.Nullable
import top.potmot.core.template.TemplateBuilder
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2 as TargetOfEnum

/**
 * java 语言下的实体生成
 */

fun GenEntityPropertiesView.javaClassStringify(): String =
    TemplateBuilder().apply {
        line("package ${packagePath()};")

        separate()
        line("import org.babyfish.jimmer.sql.Entity;")
        line("import org.babyfish.jimmer.sql.Table;")
        lines(importList()) { "import $it;" }
        separate()

        lines(blockComment())
        line("@Entity")
        lines(annotation())
        line("interface $name {")

        increaseIndentation()
        joinParts(properties.map { it.javaPropertyStringify() })
        decreaseIndentation()

        line("}")
    }.build()


private fun GenEntityPropertiesView.TargetOf_properties.javaPropertyStringify(): String =
    TemplateBuilder().apply {
        lines(blockComment())
        lines(annotation())
        if (!typeNotNull) { line("@Nullable") }
        line("${shortType()} $name;")
    }.build()

private fun GenEntityPropertiesView.importList(): List<String> =
    properties
        .flatMap { it.importList() }
        .distinct()
        .let { importListFilter(it) }

private fun GenEntityPropertiesView.TargetOf_properties.importList(): List<String> {
    val importList = importClassList().mapNotNull {
        it.qualifiedName
    }.toMutableList()

    importList += fullType()

    if (!typeNotNull) {
        importList += Nullable::class.java.name
    }

    return importList.filter { it.isNotEmpty() }
}

/**
 * java 语言下的枚举生成
 */

fun GenEntityPropertiesView.javaEnumsStringify(): List<Pair<String, String>> =
    properties.filter { it.enum != null }
        .map { it.enum!! }
        .map { Pair(name, it.javaEnumStringify()) }

private fun TargetOfEnum.javaEnumStringify(): String =
    TemplateBuilder().apply {
        line("package ${packagePath()};")

        separate()
        line("import org.babyfish.jimmer.sql.EnumType;")
        separate()

        lines(blockComment())
        line("@EnumType(EnumType.Strategy.$enumType)")
        line("public enum $name {")

        increaseIndentation()
        joinParts(items.map { it.toEntity().stringify(enumType) })
        decreaseIndentation()

        line("}")
    }.build()
