package top.potmot.core.template.entity

import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2 as TargetOfEnum

/**
 * java 语言下的实体生成
 */

fun GenEntityPropertiesView.kotlinClassStringify(): String {
    return """package ${packagePath()}

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Table
${import()}

${blockComment()}
@Entity
${tableAnnotation()}
interface $name {
${properties.joinToString("\n\n") { it.kotlinPropertyStringify() }}
}"""
}

private fun GenEntityPropertiesView.TargetOf_properties.kotlinPropertyStringify(): String {
    return """${blockComment()}${annotation()}
    val $name: ${shortType()}${if (typeNotNull) "" else "?"}"""
}

private fun GenEntityPropertiesView.import(): String =
    properties
        .flatMap { it.importList() }
        .distinct()
        .let { importListFilter(it) }
        .joinToString("\n") { "import $it" }

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
    """package ${packagePath()}

import org.babyfish.jimmer.sql.EnumType

${blockComment()}
@EnumType(EnumType.Strategy.$enumType)
enum class $name {
${items.joinToString("\n\n") { it.toEntity().stringify(enumType) }}  
}"""
