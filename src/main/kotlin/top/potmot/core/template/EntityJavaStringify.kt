package top.potmot.core.template

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.OnDissociate
import top.potmot.config.GenConfig
import top.potmot.enum.getAnnotation
import top.potmot.model.dto.GenEntityPropertiesView

fun GenEntityPropertiesView.javaClassStringify(): String {
    return """package ${packagePath()};

${import()}

/**
 * $comment
 * $remark
 * @author ${GenConfig.author}
 * @since ${now()} 
 */
interface $name {
${properties.joinToString("") { it.javaPropertyStringify() }}
}"""
}

private fun GenEntityPropertiesView.TargetOf_properties.javaPropertyStringify(): String {
    return """
    /**
     * $comment
     * $remark
     */${annotation()}
    ${if (isNotNull) "@NotNull" else ""}
    ${type()} $name;
"""
}

private fun GenEntityPropertiesView.import(): String {
    return this.properties.flatMap { it.importList() }.distinct().joinToString("\n") { "import $it;" }
}

private fun GenEntityPropertiesView.TargetOf_properties.importList(): List<String> {
    val result = mutableListOf<String>()

    result += Entity::class.java.name

    if (isNotNull) {
        result += org.jetbrains.annotations.Nullable::class.java.name
    }

    if (isId) {
        result += Id::class.java.name
        if (idGenerationType != null) {
            result += GeneratedValue::class.java.name
            result += GenerationType::class.java.name
        }
    } else if (isKey) {
        result += Key::class.java.name
    }

    if (associationType != null) {
        if (associationAnnotation != null) {
            result += associationType.getAnnotation()::class.java.name
            if (dissociateAnnotation != null) {
                result += OnDissociate::class.java.name
                result += DissociateAction::class.java.name
            }
        } else if (isIdView) {
            result += IdView::class.java.name
        }
    }

    result += type
    if (isList) {
        result += List::class.java.name
    }

    return result
}
