package top.potmot.core.template.entity

import top.potmot.model.dto.GenEntityPropertiesView

fun GenEntityPropertiesView.javaClassStringify(): String {
    return """package ${packagePath()};

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Table;
${import()}

${blockComment()}
@Entity
@Table(name = "${table.schema.name}.${table.name}")
interface $name {
${properties.joinToString("\n\n") { it.javaPropertyStringify() }}
}"""
}

private fun GenEntityPropertiesView.TargetOf_properties.javaPropertyStringify(): String {
    return """${blockComment()}${annotation()}${if (notNull) "\n    @NotNull" else ""}
    ${shortTypeName()} $name;"""
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

    importList += importEntityList()

    if (notNull) {
        importList += org.jetbrains.annotations.NotNull::class.java.name
    }

    return importList.filter { it.isNotEmpty() }
}
