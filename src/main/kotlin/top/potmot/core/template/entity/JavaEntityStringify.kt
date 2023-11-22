package top.potmot.core.template.entity

import org.jetbrains.annotations.Nullable
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.extension.packagePath
import top.potmot.model.extension.shortType
import kotlin.reflect.KClass

/**
 * java 语言下的实体生成
 */

fun GenEntityPropertiesView.javaClassStringify(): String =
    JavaEntityStringifyContext().apply {
        line("package ${packagePath()};")

        separate()
        lines(importLines()) { "import $it;" }
        separate()

        lines(blockComment())
        lines(annotationLines())
        line("interface $name {")

        increaseIndentation()
        joinParts(properties.map { it.javaPropertyStringify() })
        decreaseIndentation()

        line("}")
    }.build()


private fun GenEntityPropertiesView.TargetOf_properties.javaPropertyStringify(): String =
    JavaEntityStringifyContext().apply {
        lines(blockComment())
        lines(annotationLines())
        line("${shortType()} $name;")
    }.build()

/**
 * java 语言下的枚举生成
 */
fun GenEntityPropertiesView.javaEnumsStringify(): List<Pair<String, String>> =
    properties.filter { it.enum != null }
        .map { it.enum!! }
        .map { Pair(name, it.javaEnumStringify()) }

private fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.javaEnumStringify(): String =
    JavaEntityStringifyContext().apply {
        line("package ${packagePath()};")

        separate()
        lines(importLines()) { "import $it;" }
        separate()

        lines(blockComment())
        lines(annotationLines())
        line("public enum $name {")

        increaseIndentation()
        joinParts(items.map { "${it.enumItemAnnotation(enumType)}\n${it.name}" })
        decreaseIndentation()

        line("}")
    }.build()

class JavaEntityStringifyContext : EntityStringifyContext() {
    override fun getImportClasses(property: GenEntityPropertiesView.TargetOf_properties): List<KClass<*>> {
        return super.getImportClasses(property).let {
            if (!property.typeNotNull) {
                it + Nullable::class
            } else {
                it
            }
        }
    }

    override fun getAnnotationLines(property: GenEntityPropertiesView.TargetOf_properties): List<String> {
        return super.getAnnotationLines(property).let {
            if (!property.typeNotNull) {
                it + "@Nullable"
            } else {
                it
            }
        }
    }
}
