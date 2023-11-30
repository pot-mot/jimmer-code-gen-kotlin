package top.potmot.core.template.entity

import org.jetbrains.annotations.Nullable
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.extension.packagePath
import top.potmot.model.extension.shortType
import kotlin.reflect.KClass

class JavaEntityBuilder : EntityBuilder() {
    override fun formatFileName(name: String): String = "$name.java"

    override fun stringify(entity: GenEntityPropertiesView): String =
        entity.javaClassStringify()

    override fun stringify(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2): String =
        enum.javaEnumStringify()
}

/**
 * java 语言下的实体生成
 */
private fun GenEntityPropertiesView.javaClassStringify(): String =
    JavaEntityContext().apply {
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
    JavaEntityContext().apply {
        lines(blockComment())
        lines(annotationLines())
        line("${shortType()} $name;")
    }.build()

private fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.javaEnumStringify(): String =
    JavaEntityContext().apply {
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

private class JavaEntityContext : EntityContext() {
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
