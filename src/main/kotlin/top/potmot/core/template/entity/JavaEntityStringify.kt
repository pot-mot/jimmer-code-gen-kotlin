package top.potmot.core.template.entity

import top.potmot.model.dto.GenEntityPropertiesView


/**
 * java 语言下的实体生成
 */

fun GenEntityPropertiesView.javaClassStringify(): String {
    return """package ${packagePath()};

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Table;
${import()}

${blockComment()}
@Entity
${tableAnnotation()}
interface $name {
${properties.joinToString("\n\n") { it.javaPropertyStringify() }}
}"""
}


private fun GenEntityPropertiesView.TargetOf_properties.javaPropertyStringify(): String {
    return """${blockComment()}${annotation()}${if (notNull) "\n    @NotNull" else ""}
    ${shortType()} $name;"""
}

private fun GenEntityPropertiesView.import(): String =
    properties
        .flatMap { it.importList() }
        .distinct()
        .let { importListFilter(it) }
        .joinToString("\n") { "import $it;" }

private fun GenEntityPropertiesView.TargetOf_properties.importList(): List<String> {
    val importList = importClassList().mapNotNull {
        it.qualifiedName
    }.toMutableList()

    importList += fullType()

    if (notNull) {
        importList += org.jetbrains.annotations.NotNull::class.java.name
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

private fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum.javaEnumStringify(): String =
    """package ${packagePath()};

import org.babyfish.jimmer.sql.EnumType;

${blockComment()}
@EnumType(EnumType.Strategy.$enumType)
public enum $name {
${items.joinToString("\n\n") { it.toEntity().stringify(enumType) }}
}"""
