package top.potmot.core.template

import top.potmot.model.dto.GenEntityPropertiesView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
