package top.potmot.core.template.entity

import org.babyfish.jimmer.sql.DissociateAction
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.LogicalDeleted
import org.babyfish.jimmer.sql.OnDissociate
import org.babyfish.jimmer.sql.Table
import top.potmot.config.GenConfig
import top.potmot.core.template.TemplateBuilder
import top.potmot.enumeration.EnumType
import top.potmot.model.extension.toPath
import top.potmot.model.GenEnumItem
import top.potmot.model.dto.GenEntityPropertiesView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

fun GenEntityPropertiesView.annotation(): String =
    "@Table(name = \"${table.schema?.name?.let { "$it." } ?: ""}${table.name}\")"

fun now(formatPattern: String = "yyyy-MM-dd HH:mm:ss"): String =
    LocalDateTime.now().format(
        DateTimeFormatter.ofPattern(formatPattern)
    )

fun GenEntityPropertiesView.packagePath(): String =
    genPackage?.toEntity()?.toPath() ?: GenConfig.defaultPackagePath

fun GenEntityPropertiesView.TargetOf_properties.TargetOf_typeTable_2.packagePath(): String =
    entity?.genPackage?.toEntity()?.toPath() ?: GenConfig.defaultPackagePath

fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.packagePath(): String =
    genPackage?.toEntity()?.toPath() ?: GenConfig.defaultPackagePath

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

private fun createBlockComment(
    comment: String,
    remark: String = "",
    indentation: Int = 0,
    params: Map<String, String> = emptyMap(),
): String? =
    TemplateBuilder(
        indentation,
    ).apply {
        block(comment) { " * $it"}
        block(remark) { " * $it"}

        if (params.isNotEmpty() && !isEmptyOrBlank()) {
            line(" * ")
        }

        params.forEach { (key, value) ->
            line(" * @$key $value")
        }
    }.build().let {
        if (it.isBlank()) null else "/**\n$it\n */"
    }

fun GenEntityPropertiesView.blockComment(): String? =
    createBlockComment(
        comment, remark, params = mapOf(
            Pair("author", author.ifEmpty { GenConfig.author }),
            Pair("since", now())
        )
    )

fun GenEntityPropertiesView.TargetOf_properties.blockComment(): String? =
    createBlockComment(comment, remark)

fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.blockComment(): String? =
    createBlockComment(comment, remark)

fun GenEntityPropertiesView.TargetOf_properties.annotation(): String =
    TemplateBuilder().apply {
        if (idProperty) {
            line("@Id")
            if (idGenerationType != null) {
                line("@GeneratedValue(strategy = GenerationType.${idGenerationType})")
            }
        } else if (keyProperty) {
            line("@Key")
        }

        if (logicalDelete) {
            line(GenConfig.logicalDeletedAnnotation)
        }

        if (associationType != null) {
            if (associationAnnotation != null) {
                lines(associationAnnotation.split("\n"))
                if (dissociateAnnotation != null) {
                    line(dissociateAnnotation)
                }
            } else if (idView && idViewAnnotation != null) {
                line(idViewAnnotation)
            }
        }
    }.build()

fun GenEntityPropertiesView.importClassList(): List<KClass<*>> {
    val result = mutableListOf<KClass<*>>()

    result += Entity::class
    result += Table::class

    return result
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

            if (associationAnnotation.contains("@JoinTable")) {
                result += JoinTable::class
            }

            if (associationAnnotation.contains("@JoinColumn")) {
                result += JoinColumn::class
            }

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

/**
 * 过滤不必要和不合理的 import
 */
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
