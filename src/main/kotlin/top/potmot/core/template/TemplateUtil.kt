package top.potmot.core.template

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.OnDissociate
import top.potmot.config.GenConfig
import top.potmot.enum.toAnnotation
import top.potmot.model.dto.GenEntityPropertiesView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

fun now(): String = LocalDateTime.now().format(
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
)

fun GenEntityPropertiesView.TargetOf_properties.type(): String {
    val baseType = type.split(".").last()

    return if (isList) {
        "List<$baseType>"
    } else {
        baseType
    }
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

fun GenEntityPropertiesView.TargetOf_properties.annotation(): String {
    val builder = StringBuilder()

    if (isId) {
        builder.append("    @Id\n")
        if (idGenerationType != null) {
            builder.append("    @GeneratedValue(strategy = GenerationType.${idGenerationType})\n")
        }
    } else if (isKey) {
        builder.append("    @Key\n")
    }

    if (isLogicalDelete) {
        builder.append("    ${GenConfig.logicalDeletedAnnotation}")
    }

    if (associationType != null) {
        if (associationAnnotation != null) {
            builder.append("    ${associationAnnotation}\n")
            if (dissociateAnnotation != null) {
                builder.append("    ${dissociateAnnotation}\n")
            }
        } else if (isIdView) {
            builder.append("    ${idViewAnnotation}\n")
        }
    }

    val resultStr = builder.toString()

    return if (resultStr.isNotEmpty()) {
        "\n" + resultStr.removeSuffix("\n")
    } else {
        ""
    }
}

fun GenEntityPropertiesView.TargetOf_properties.importClassList(): List<KClass<*>> {
    val result = mutableListOf<KClass<*>>()

    result += Entity::class

    if (isId) {
        result += Id::class
        if (idGenerationType != null) {
            result += GeneratedValue::class
            result += GenerationType::class
        }
    } else if (isKey) {
        result += Key::class
    }

    if (isLogicalDelete) {
        result += LogicalDeleted::class
    }

    if (associationType != null) {
        if (associationAnnotation != null) {
            result += associationType.toAnnotation()::class
            if (dissociateAnnotation != null) {
                result += OnDissociate::class
                result += DissociateAction::class
            }
        } else if (isIdView) {
            result += IdView::class
        }
    }

    if (isList) {
        result += List::class
    }

    return result
}
