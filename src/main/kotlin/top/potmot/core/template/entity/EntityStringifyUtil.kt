package top.potmot.core.template.entity

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
import top.potmot.extension.toPath
import top.potmot.model.dto.GenEntityPropertiesView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

fun now(formatPattern: String = "yyyy-MM-dd HH:mm:ss"): String =
    LocalDateTime.now().format(
        DateTimeFormatter.ofPattern(formatPattern)
    )

fun GenEntityPropertiesView.packagePath(): String =
    genPackage?.toEntity()?.toPath() ?: ""

fun GenEntityPropertiesView.TargetOf_properties.shortTypeName(): String {
    val baseType = type.split(".").last()

    return if (isList) {
        "List<$baseType>"
    } else {
        baseType
    }
}

private fun String.toTextBlock(wrapLength: Int = 40, separator: String = " "): List<String> {
    val list = mutableListOf<String>()

    val words = this.split(separator)

    var currentLine = ""

    // 遍历每个单词，并将它们添加到当前行
    for (word in words) {
        // 如果当前行加上当前单词的长度超过了wrapLength，则将当前行添加到列表中，并开始一个新行
        if (currentLine.length + word.length > wrapLength) {
            list += currentLine.trim()
            currentLine = ""
        }

        // 将当前单词添加到当前行，并添加一个空格
        currentLine += word
    }

    // 添加最后一行到列表中
    if (currentLine.isNotBlank()) {
        list += currentLine.trim()
    }


    return list
}

fun GenEntityPropertiesView.blockComment(): String {
    val list = mutableListOf<String>()

    comment.toTextBlock().let {
        if (it.isNotEmpty()) list += it
    }

    remark.toTextBlock().let {
        if (it.isNotEmpty()) list += it
    }

    if (list.isNotEmpty()) {
        list += ""
    }

    list += "@author ${author.ifEmpty { GenConfig.author }}"
    list += "@since ${now()}"

    return "/**\n" + list.joinToString("\n") { " * $it" } + "\n */"
}

fun GenEntityPropertiesView.TargetOf_properties.blockComment(): String {
    val list = mutableListOf<String>()

    comment.toTextBlock().let {
        if (it.isNotEmpty()) list += it
    }

    remark.toTextBlock().let {
        if (it.isNotEmpty()) list += it
    }

    return "    /**\n" + list.joinToString("\n") { "     * $it" } + "\n     */"
}

fun GenEntityPropertiesView.TargetOf_properties.annotation(): String {
    val list = mutableListOf<String>()

    if (isId) {
        list += "@Id"
        if (idGenerationType != null) {
            list += "@GeneratedValue(strategy = GenerationType.${idGenerationType})"
        }
    } else if (isKey) {
        list += "@Key"
    }

    if (isLogicalDelete) {
        list += GenConfig.logicalDeletedAnnotation
    }

    if (associationType != null) {
        if (associationAnnotation != null) {
            list += associationAnnotation
            if (dissociateAnnotation != null) {
                list += dissociateAnnotation
            }
        } else if (isIdView && idViewAnnotation != null) {
            list += idViewAnnotation
        }
    }

    val resultStr = list.joinToString("\n") { "    $it" }

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

fun GenEntityPropertiesView.TargetOf_properties.importEntityList(): List<String> {
    val result = mutableListOf<String>()

    result +=
        if (typeTable?.entity != null) {
            if (typeTable.entity.genPackage != null) {
                typeTable.entity.genPackage.toEntity().toPath() + "." + typeTable.entity.name
            } else {
                typeTable.entity.name
            }
        } else {
            type
        }

    if (enum != null) {
        result +=
            if (enum.genPackage != null) {
                enum.genPackage.toEntity().toPath() + "." + enum.name
            } else {
                enum.name
            }
    }

    return result
}
