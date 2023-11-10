package top.potmot.core.template.entity

import top.potmot.core.template.TemplateBuilder
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2 as TargetOfEnum

/**
 * java 语言下的实体生成
 */

fun GenEntityPropertiesView.kotlinClassStringify(): String =
    TemplateBuilder().apply {
        line("package ${packagePath()}")

        separate()
        line("import org.babyfish.jimmer.sql.Entity")
        line("import org.babyfish.jimmer.sql.Table")
        lines(importList())
        separate()

        lines(blockComment())
        line("@Entity")
        lines(annotation())
        line("interface $name {")

        increaseIndentation()
        joinParts(properties.map { it.kotlinPropertyStringify() })
        decreaseIndentation()

        line("}")

    }.build()

private fun GenEntityPropertiesView.TargetOf_properties.kotlinPropertyStringify(): String =
    TemplateBuilder().apply {
        lines(blockComment())
        lines(annotation())
        line("val $name: ${shortType()}${if (typeNotNull) "" else "?"}")
    }.build()

private fun GenEntityPropertiesView.importList(): List<String> =
    properties
        .flatMap { it.importList() }
        .distinct()
        .let { importListFilter(it) }
        .map { "import $it" }

private fun GenEntityPropertiesView.TargetOf_properties.importList(): List<String> {
    val importList = importClassList().mapNotNull {
        it.qualifiedName
    }.toMutableList()

    importList += fullType()

    return importList.filter { it.isNotEmpty() }
}

/**
 * kotlin 语言下的枚举生成
 */

fun GenEntityPropertiesView.kotlinEnumsStringify(): List<Pair<String, String>> =
    properties.filter { it.enum != null }
        .map { it.enum!! }
        .map { Pair(name, it.kotlinEnumStringify()) }

private fun TargetOfEnum.kotlinEnumStringify(): String =
    TemplateBuilder().apply {
        line("package ${packagePath()}")

        separate()
        line("import org.babyfish.jimmer.sql.EnumType")
        separate()

        lines(blockComment())
        line("@EnumType(EnumType.Strategy.$enumType)")
        line("enum class $name {")

        increaseIndentation()
        joinParts(items.map { it.toEntity().stringify(enumType) })
        decreaseIndentation()

        line("}")
    }.build()
