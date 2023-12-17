package top.potmot.core.entity.build

import org.jetbrains.annotations.Nullable
import top.potmot.model.dto.GenEntityPropertiesView
import kotlin.reflect.KClass

class JavaEntityCodeBuilder : EntityCodeBuilder() {
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
