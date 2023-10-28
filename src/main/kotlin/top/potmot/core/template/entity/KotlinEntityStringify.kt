package top.potmot.core.template.entity

import top.potmot.model.dto.GenEntityPropertiesView


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
@Table(name = "${table.schema.name}.${table.name}")
interface $name {
${properties.joinToString("\n\n") { it.kotlinPropertyStringify() }}
}"""
}

private fun GenEntityPropertiesView.TargetOf_properties.kotlinPropertyStringify(): String {
    return """${blockComment()}${annotation()}
    val $name: ${shortType()}${if (notNull) "" else "?"}"""
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

private fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum.kotlinEnumStringify(): String =
    """package ${packagePath()}

import org.babyfish.jimmer.sql.EnumType

${blockComment()}
@EnumType(EnumType.Strategy.$enumType)
enum class $name {
${items.joinToString("\n\n") { it.toEntity().stringify(enumType) }}  
}"""
