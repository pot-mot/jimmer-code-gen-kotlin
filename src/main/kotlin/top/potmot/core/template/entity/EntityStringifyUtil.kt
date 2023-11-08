package top.potmot.core.template.entity

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.OnDissociate
import top.potmot.config.GenConfig
import top.potmot.enumeration.EnumType
import top.potmot.extension.toPath
import top.potmot.model.GenEnumItem
import top.potmot.model.dto.GenEntityPropertiesView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

fun GenEntityPropertiesView.tableAnnotation(): String =
    "@Table(name = \"${table.schema?.name?.let { "$it." } ?: ""}${table.name}\")"

fun now(formatPattern: String = "yyyy-MM-dd HH:mm:ss"): String =
    LocalDateTime.now().format(
        DateTimeFormatter.ofPattern(formatPattern)
    )

fun GenEntityPropertiesView.packagePath(): String =
    genPackage?.toEntity()?.toPath() ?: ""

fun GenEntityPropertiesView.TargetOf_properties.TargetOf_typeTable_2.packagePath(): String =
    entity?.genPackage?.toEntity()?.toPath() ?: ""

fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.packagePath(): String =
    genPackage?.toEntity()?.toPath() ?: ""

/**
 * 获取属性的简单类型名
 * 判断过程如下：
 *  1. 优先采用枚举类型
 *  2. 使用 typeTable 对应的 Entity
 *  3. 使用映射后的 type
 */
fun GenEntityPropertiesView.TargetOf_properties.shortType(): String {
    if (enum != null) {
        return enum.name
    }

    val baseType =
        if (typeTable?.entity != null) {
            typeTable.entity.name
        } else {
            type.split(".").last()
        }

    return if (listType) {
        "List<$baseType>"
    } else {
        baseType
    }
}

fun GenEntityPropertiesView.TargetOf_properties.fullType(): String {
    if (enum != null) {
        val packagePath = enum.packagePath()

        return if (packagePath.isNotBlank()) {
            packagePath + "." + enum.name
        } else {
            enum.name
        }
    }


    if (typeTable?.entity != null) {
        val packagePath = typeTable.packagePath()

        return if (packagePath.isNotBlank()) {
            packagePath + "." + typeTable.entity.name
        } else {
            typeTable.entity.name
        }
    }

    return type
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

private fun getBlockComment(
    comment: String,
    remark: String = "",
    retract: String = "",
    params: Map<String, String> = emptyMap(),
): String {
    val list = mutableListOf<String>()

    comment.toTextBlock().let {
        if (it.isNotEmpty()) list += it
    }

    remark.toTextBlock().let {
        if (it.isNotEmpty()) list += it
    }

    if (params.isNotEmpty()) {
        list += ""
    }

    params.forEach { (key, value) ->
        list += "@$key $value"
    }

    return "$retract/**\n" + list.joinToString("\n") { "$retract * $it" } + "\n$retract */"
}

fun GenEntityPropertiesView.blockComment(): String =
    getBlockComment(
        comment, remark, params = mapOf(
            Pair("author", author.ifEmpty { GenConfig.author }),
            Pair("since", now())
        )
    )

fun GenEntityPropertiesView.TargetOf_properties.blockComment(): String = getBlockComment(comment, remark, "    ")

fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.blockComment(): String =
    getBlockComment(comment, remark, "    ")

fun GenEntityPropertiesView.TargetOf_properties.annotation(): String {
    val list = mutableListOf<String>()

    if (idProperty) {
        list += "@Id"
        if (idGenerationType != null) {
            list += "@GeneratedValue(strategy = GenerationType.${idGenerationType})"
        }
    } else if (keyProperty) {
        list += "@Key"
    }

    if (logicalDelete) {
        list += GenConfig.logicalDeletedAnnotation
    }

    if (associationType != null) {
        if (associationAnnotation != null) {
            list += associationAnnotation.split("\n")
            if (dissociateAnnotation != null) {
                list += dissociateAnnotation
            }
        } else if (idView && idViewAnnotation != null) {
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

    if (idProperty) {
        result += Id::class
        if (idGenerationType != null) {
            result += GeneratedValue::class
            result += GenerationType::class
        }
    } else if (keyProperty) {
        result += Key::class
    }

    if (logicalDelete) {
        result += LogicalDeleted::class
    }

    if (associationType != null) {
        if (associationAnnotation != null) {
            result += associationType.toAnnotation()
            if (dissociateAnnotation != null) {
                result += OnDissociate::class
                result += DissociateAction::class
            }
        } else if (idView) {
            result += IdView::class
        }
    }

    if (listType) {
        result += List::class
    }

    return result
}


fun importListFilter(importList: List<String>): List<String> =
    importList.filter { importItem ->
        !(importItem.startsWith("kotlin.") && importItem.split(".").size == 2)
                &&
                !(importItem.startsWith("java.lang.") && importItem.split(".").size == 3)
                &&
                (importItem.split(".").filter { it.isNotBlank() }.size >= 2)
    }

fun GenEnumItem.stringify(enumType: EnumType?): String =
    when (enumType) {
        EnumType.NAME -> "@EnumItem(name = \"$value\")\n"
        EnumType.ORDINAL -> "@EnumItem(ordinal = $value)\n"
        null -> ""
    } + name
