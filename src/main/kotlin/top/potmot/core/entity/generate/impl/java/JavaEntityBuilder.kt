package top.potmot.core.entity.generate.impl.java

import org.jetbrains.annotations.Nullable
import top.potmot.core.entity.generate.builder.EntityBuilder
import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.entity.dto.GenPropertyView
import kotlin.reflect.KClass

object JavaEntityBuilder : EntityBuilder() {
    override fun packageLine(path: String): String = "package ${path};"

    override fun importLine(item: String): String = "import ${item};"

    override fun entityLine(entity: GenEntityPropertiesView): String =
        buildString {
            append("public interface ${entity.name}")

            if (entity.superEntities.isNotEmpty()) {
                append(" extends")
                append(" ${entity.superEntities.joinToString(", ") { it.name }}")
            }
        }

    override fun propertyLine(property: GenPropertyView): String =
        "${property.shortType()} ${property.name}();"

    override fun classesToLines(classes: Set<KClass<*>>): Set<String> {
        return classes.map { it.java.name }.toSet()
    }

    override fun importClasses(property: GenPropertyView): Set<KClass<*>> {
        return super.importClasses(property)
            .let {
                if (!property.typeNotNull) {
                    it + Nullable::class
                } else {
                    it
                }
            }
    }

    override fun annotationLines(property: GenPropertyView): List<String> {
        return super.annotationLines(property).let {
            if (!property.typeNotNull) {
                it + "@Nullable"
            } else {
                it
            }
        }
    }
}
