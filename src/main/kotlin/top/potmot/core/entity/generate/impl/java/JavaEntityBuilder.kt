package top.potmot.core.entity.generate.impl.java

import org.jetbrains.annotations.Nullable
import top.potmot.core.entity.generate.builder.EntityBuilder
import top.potmot.core.entity.generate.builder.EntityView
import top.potmot.core.entity.generate.builder.PropertyView
import top.potmot.core.intType
import top.potmot.core.numberMax
import top.potmot.core.numberMin
import top.potmot.core.numericType
import top.potmot.core.stringType
import top.potmot.entity.sub.AnnotationWithImports
import top.potmot.utils.string.buildScopeString
import kotlin.reflect.KClass

object JavaEntityBuilder : EntityBuilder() {
    override fun packageLine(path: String): String = "package ${path};"

    override fun importLine(item: String): String = "import ${item};"

    override fun entityLine(entity: EntityView): String =
        buildString {
            append("public interface ${entity.name}")

            if (entity.superEntities.isNotEmpty()) {
                append(" extends")
                append(" ${entity.superEntities.joinToString(", ") { it.name }}")
            }
        }

    override fun propertyBlock(property: PropertyView) =
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

    override fun validateAnnotations(property: PropertyView): AnnotationWithImports {
        val imports = mutableListOf<String>()
        val annotations = mutableListOf<String>()

        if (!property.typeNotNull) {
                imports += Nullable::class.java.name
                annotations += "@Nullable"
        }

        if (property.typeTable != null) {
            imports += "javax.validation.Valid"
            annotations += "@Valid"
        }

        if (!property.idProperty && !property.logicalDelete && property.associationType == null) {
            when (property.type) {
                in stringType -> {
                    property.column?.dataSize?.let {
                        imports += "org.hibernate.validator.constraints.Length"
                        annotations += "@Length(max = ${it}, message = \"${property.comment}长度不可大于${it}\")"
                    }
                }
                in intType -> {
                    property.column?.let {
                        numberMax(it.typeCode, it.dataSize, it.numericPrecision)?.let { max ->
                            imports += "javax.validation.constraints.Max"
                            annotations += "@get:Max(value = ${max}, message = \"${property.comment}不可大于${max}\")"
                        }
                        numberMin(it.typeCode, it.dataSize, it.numericPrecision)?.let { min ->
                            imports += "javax.validation.constraints.Min"
                            annotations += "@get:Min(value = ${min}, message = \"${property.comment}不可小于${min}\")"
                        }
                    }
                }
                in numericType -> {
                    property.column?.let {
                        numberMax(it.typeCode, it.dataSize, it.numericPrecision)?.let { max ->
                            imports += "javax.validation.constraints.DecimalMax"
                            annotations += "@get:DecimalMax(value = \"${max}\", message = \"${property.comment}不可大于${max}\")"
                        }
                        numberMin(it.typeCode, it.dataSize, it.numericPrecision)?.let { min ->
                            imports += "javax.validation.constraints.DecimalMin"
                            annotations += "@get:DecimalMax(value = \"${min}\", message = \"${property.comment}不可小于${min}\")"
                        }
                    }
                }
            }
        }

        return AnnotationWithImports(
            imports = imports,
            annotations = annotations,
        )
    }
}
