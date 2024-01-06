package top.potmot.core.entity.generate.java

import org.jetbrains.annotations.Nullable
import top.potmot.core.database.generate.getIdentifierFilter
import top.potmot.core.entity.generate.EntityCodeBuilder
import top.potmot.core.entity.generate.EntityCodeGenerator
import top.potmot.enumeration.DataSourceType
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.extension.shortType
import top.potmot.utils.identifier.IdentifierFilter
import kotlin.reflect.KClass

class JavaEntityCodeGenerator : EntityCodeGenerator() {
    override fun getFileSuffix(): String = ".java"

    override fun stringify(entity: GenEntityPropertiesView, dataSourceType: DataSourceType): String =
        entity.javaClassStringify(dataSourceType)

    override fun stringify(enum: GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2, dataSourceType: DataSourceType): String =
        enum.javaEnumStringify(dataSourceType)

    private fun GenEntityPropertiesView.javaClassStringify(dataSourceType: DataSourceType): String =
        JavaEntityCodeBuilder(dataSourceType.getIdentifierFilter()).apply {
            line("package $packagePath;")

            separate()
            lines(importLines()) { "import $it;" }
            separate()

            lines(blockComment())
            lines(annotationLines())
            line("public interface $name {")

            increaseIndentation()
            properties.joinPartsProduce {
                lines(it.blockComment())
                lines(it.annotationLines())
                line("${it.shortType()} ${it.name}();")
            }
            decreaseIndentation()

            line("}")
        }.build()

    private fun GenEntityPropertiesView.TargetOf_properties.TargetOf_enum_2.javaEnumStringify(dataSourceType: DataSourceType): String =
        JavaEntityCodeBuilder(dataSourceType.getIdentifierFilter()).apply {
            line("package $packagePath;")

            separate()
            lines(importLines()) { "import $it;" }
            separate()

            lines(blockComment())
            lines(annotationLines())
            line("public enum $name {")

            increaseIndentation()
            items.joinPartsProduce(",\n\n") {
                lines(it.blockComment())
                lines(it.annotation(enumType))
                lineNoWrap(it.name)
            }
            decreaseIndentation()

            line("\n}")
        }.build()

    class JavaEntityCodeBuilder(
        identifierFilter: IdentifierFilter
    ) : EntityCodeBuilder(identifierFilter) {
        override fun classesToLines(classes: Set<KClass<*>>): Set<String> {
            return classes.map { it.java.name }.toSet()
        }

        override fun getImportClasses(property: GenEntityPropertiesView.TargetOf_properties): Set<KClass<*>> {
            return super.getImportClasses(property)
                .let {
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
}
