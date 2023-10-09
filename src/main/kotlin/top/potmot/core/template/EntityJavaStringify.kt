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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun GenEntityPropertiesView.stringifyJava(): String {
    val entity = this

    return """package ${entity.packagePath()}

${entity.importJava()}

/**
 * ${entity.comment}
 *
 * @author ${GenConfig.author}
 * @since ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))} 
 */
interface ${entity.name} {
${properties.joinToString("") { it.stringifyJava() }}
}"""
}

fun GenEntityPropertiesView.packagePathJava(): String {
    return this.packagePath() + ";"
}

fun GenEntityPropertiesView.TargetOf_properties.stringifyJava(): String {
    val property = this
    return """
    /**
     * ${property.comment}
     */${property.annotation()}
    ${if (property.isNotNull) "@NotNull" else ""}
    ${property.shortType()} ${property.name};
"""
}

fun GenEntityPropertiesView.importJava(): String {
    return this.properties.flatMap { it.importListJava() }.distinct().joinToString("\n") { "import $it;" }
}

fun GenEntityPropertiesView.TargetOf_properties.importListJava(): List<String> {
    val property = this

    val result = mutableListOf<String>()

    result += Entity::class.java.name

    if (property.isId) {
        result += Id::class.java.name
        if (property.idGenerationType != null) {
            result += GeneratedValue::class.java.name
            result += GenerationType::class.java.name
        }
    } else if (property.isKey) {
        result += Key::class.java.name
    }

    if (property.associationType != null) {
        if (property.associationAnnotation != null) {
            result += property.associationType.getAnnotation()::class.java.name
            if (property.dissociateAnnotation != null) {
                result += OnDissociate::class.java.name
                result += DissociateAction::class.java.name
            }
        } else if (property.isIdView) {
            result += IdView::class.java.name
        }
    }

    result += property.type
    if (property.isList) {
        result += List::class.java.name
    }

    return result
}
