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

fun GenEntityPropertiesView.kotlinClassStringify(): String {
    return """package ${packagePath()}

${import()}

/**
 * $comment
 * $remark
 * @author ${GenConfig.author}
 * @since ${now()} 
 */
@Entity
interface $name {
${properties.joinToString("") { it.kotlinPropertyStringify() }}
}"""
}

private fun GenEntityPropertiesView.TargetOf_properties.kotlinPropertyStringify(): String {
    return """
    /**
     * $comment
     * $remark
     */${annotation()}
    val $name: ${type()}${if (isNotNull) "" else "?"}
"""
}

private fun GenEntityPropertiesView.import(): String {
    return this.properties.flatMap { it.importList() }.distinct().joinToString("\n") { "import $it" }
}

private fun GenEntityPropertiesView.TargetOf_properties.importList(): List<String> {
    val result = mutableListOf<String>()

    Entity::class.qualifiedName?.let {result += it}

    if (isId) {
        Id::class.qualifiedName?.let { result += it }
        if (idGenerationType != null) {
            GeneratedValue::class.qualifiedName?.let { result += it }
            GenerationType::class.qualifiedName?.let { result += it }
        }
    } else if (isKey) {
        Key::class.qualifiedName?.let { result += it }
    }

    if (associationType != null) {
        if (associationAnnotation != null) {
            associationType.getAnnotation().qualifiedName?.let { result += it }
            if (dissociateAnnotation != null) {
                OnDissociate::class.qualifiedName?.let { result += it }
                DissociateAction::class.qualifiedName?.let { result += it }
            }
        } else if (isIdView) {
            IdView::class.qualifiedName?.let { result += it }
        }
    }

    result += type
    if (isList) {
        List::class.qualifiedName?.let { result += it }
    }

    return result
}
