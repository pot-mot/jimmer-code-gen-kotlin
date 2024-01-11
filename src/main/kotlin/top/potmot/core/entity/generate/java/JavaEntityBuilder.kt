package top.potmot.core.entity.generate.java

import org.jetbrains.annotations.Nullable
import top.potmot.core.entity.generate.builder.EntityBuilder
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.extension.shortType
import kotlin.reflect.KClass

class JavaEntityBuilder: EntityBuilder() {
    override fun packageLine(path: String): String = "package ${path};"

    override fun importLine(item: String): String = "import ${item};"

    override fun entityLine(entity: GenEntityPropertiesView): String =
        "public interface ${entity.name}"

    override fun propertyLine(property: GenEntityPropertiesView.TargetOf_properties): String =
        "${property.shortType()} ${property.name}();"

    override fun classesToLines(classes: Set<KClass<*>>): Set<String> {
        return classes.map { it.java.name }.toSet()
    }

    override fun importClasses(property: GenEntityPropertiesView.TargetOf_properties): Set<KClass<*>> {
        return super.importClasses(property)
            .let {
                if (!property.typeNotNull) {
                    it + Nullable::class
                } else {
                    it
                }
            }
    }

    override fun annotationLines(property: GenEntityPropertiesView.TargetOf_properties): List<String> {
        return super.annotationLines(property).let {
            if (!property.typeNotNull) {
                it + "@Nullable"
            } else {
                it
            }
        }
    }
}
