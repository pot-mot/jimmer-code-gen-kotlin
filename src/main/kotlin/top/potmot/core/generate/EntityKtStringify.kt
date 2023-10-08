package top.potmot.core.generate

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

fun GenEntityPropertiesView.stringify(): String {
    val entity = this

    return """package ${entity.packagePath()}

${entity.import()}

/**
 * ${entity.comment}
 *
 * @author ${GenConfig.author}
 * @since ${LocalDateTime.now()} 
 */
@Entity
interface ${entity.name} {
${properties.joinToString("") { it.stringify() }}
}"""
}

fun GenEntityPropertiesView.packagePath(): String {
    val tempPath = mutableListOf<String>()

    if (genPackage != null) {
        tempPath += genPackage.name
        var tempParentPackage = genPackage.parent
        while (tempParentPackage != null) {
            tempPath += tempParentPackage.name
            tempParentPackage = tempParentPackage.parent
        }
    }

    return tempPath.joinToString(".")
}

fun GenEntityPropertiesView.TargetOf_properties.shortType(): String {
    val baseType = type.split(".").last()

    return if (isList) {
        "List<$baseType>"
    } else {
        baseType
    }
}

fun GenEntityPropertiesView.TargetOf_properties.stringify(): String {
    val property = this
    return """
    /**
     * ${property.comment}
     */${property.annotation()}
    val ${property.name}: ${property.shortType()}${if (property.isNotNull) "" else "?"}
"""
}

fun GenEntityPropertiesView.import(): String {
    return this.properties.flatMap { it.importList() }.distinct().joinToString("\n") { "import $it" }
}

fun GenEntityPropertiesView.TargetOf_properties.annotation(): String {
    val property = this

    val builder = StringBuilder()

    if (property.isId) {
        builder.append("    @Id\n")
        if (property.idGenerationType != null) {
            builder.append("    @GeneratedValue(strategy = GenerationType.${property.idGenerationType})\n")
        }
    } else if (property.isKey) {
        builder.append("    @Key\n")
    }

    if (property.associationType != null) {
        if (property.associationAnnotation != null) {
            builder.append("    ${property.associationAnnotation}\n")
            if (property.dissociateAnnotation != null) {
                builder.append("    ${property.dissociateAnnotation}\n")
            }
        } else if (property.isIdView) {
            builder.append("    ${property.idViewAnnotation}\n")
        }
    }

    val resultStr = builder.toString()

    return if (resultStr.isNotEmpty()) {
        "\n" + resultStr.removeSuffix("\n")
    } else {
        ""
    }
}

fun GenEntityPropertiesView.TargetOf_properties.importList(): List<String> {
    val property = this

    val result = mutableListOf<String>()

    Entity::class.qualifiedName?.let {result += it}

    if (property.isId) {
        Id::class.qualifiedName?.let { result += it }
        if (property.idGenerationType != null) {
            GeneratedValue::class.qualifiedName?.let { result += it }
            GenerationType::class.qualifiedName?.let { result += it }
        }
    } else if (property.isKey) {
        Key::class.qualifiedName?.let { result += it }
    }

    if (property.associationType != null) {
        if (property.associationAnnotation != null) {
            property.associationType.getAnnotation().qualifiedName?.let { result += it }
            if (property.dissociateAnnotation != null) {
                OnDissociate::class.qualifiedName?.let { result += it }
                DissociateAction::class.qualifiedName?.let { result += it }
            }
        } else if (property.isIdView) {
            IdView::class.qualifiedName?.let { result += it }
        }
    }

    result += property.type
    if (property.isList) {
        List::class.qualifiedName?.let { result += it }
    }

    return result
}
