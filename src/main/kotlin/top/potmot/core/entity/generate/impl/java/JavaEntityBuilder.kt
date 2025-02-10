package top.potmot.core.entity.generate.impl.java

import kotlin.reflect.KClass
import org.jetbrains.annotations.Nullable
import top.potmot.core.entity.generate.builder.EntityBuilder
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenPropertyView
import top.potmot.utils.string.buildScopeString

object JavaEntityBuilder : EntityBuilder() {
    override fun packageLine(path: String): String = "package ${path};"

    override fun importLine(item: String): String = "import ${item};"

    override fun entityLine(entity: GenEntityGenerateView): String =
        buildString {
            append("public interface ${entity.name}")

            if (entity.superEntities.isNotEmpty()) {
                append(" extends")
                append(" ${entity.superEntities.joinToString(", ") { it.name }}")
            }
        }

    override fun propertyBlock(property: GenPropertyView) =
        buildScopeString {
            if (property.body != null) {
                append("default ")
            }
            append("${property.shortType()} ${property.name}()")
            if (property.body == null) {
                append(";")
            } else {
                line(" {")
                scope {
                    block(property.body.codeBlock)
                }
                append("}")
            }
        }


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
