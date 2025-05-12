package top.potmot.core.entity.generate.impl.java

import org.jetbrains.annotations.Nullable
import top.potmot.core.common.intType
import top.potmot.core.common.numberMax
import top.potmot.core.common.numberMin
import top.potmot.core.common.numericType
import top.potmot.core.common.stringType
import top.potmot.core.entity.generate.builder.EntityBuilder
import top.potmot.core.entity.generate.builder.EntityView
import top.potmot.core.entity.generate.builder.PropertyView
import top.potmot.entity.sub.AnnotationWithImports
import top.potmot.utils.string.buildScopeString

object JavaEntityBuilder : EntityBuilder() {
    override val associationAnnotationBuilder = JavaAssociationAnnotationBuilder

    override fun packageLine(path: String): String = "package ${path};"

    override fun importLine(item: String): String = "import ${item};"

    override fun entityLine(entity: EntityView): String =
        buildString {
            append("public interface ${entity.name}")

            if (entity.superEntities.isNotEmpty()) {
                append(" extends ${entity.superEntities.joinToString(", ") { it.name }}")
            }
        }

    override fun blockComment(property: PropertyView): String? =
        createBlockComment(
            property.comment,
            property.remark,
            params =
            if (property.mappedBy != null) {
                mapOf(
                    Pair("see", property.type + "#" + property.mappedBy),
                )
            } else emptyMap()
        )

    override fun propertyBlock(property: PropertyView) =
        buildScopeString {
            if (property.body != null) {
                append("default ")
            }
            append("${property.shortType()} ${property.name}()")
            if (property.body == null) {
                append(";")
            } else {
                scopeEndNoLine(" {", "}") {
                    block(property.body.codeBlock)
                }
            }
        }

    override fun annotationWithImports(property: PropertyView): AnnotationWithImports {
        val base = super.annotationWithImports(property)

        val imports = mutableListOf<String>()
        val annotations = mutableListOf<String>()

        if (property.listType) {
            imports += List::class.java.name
        }

        if (!property.typeNotNull) {
            imports += Nullable::class.java.name
            annotations += "@Nullable"
        }

        if (property.typeTable != null) {
            imports += "jakarta.validation.Valid"
            annotations += "@Valid"
        }

        if (!property.idProperty && !property.logicalDelete && property.associationType == null) {
            when (property.type) {
                in stringType -> {
                    property.column?.dataSize?.let {
                        if (it > 0) {
                            imports += "org.hibernate.validator.constraints.Length"
                            annotations += "@Length(max = ${it}, message = \"${property.comment}长度不可大于${it}\")"
                        }
                    }
                }

                in intType -> {
                    property.column?.let {
                        numberMax(it.typeCode, it.dataSize, it.numericPrecision)?.let { max ->
                            imports += "jakarta.validation.constraints.Max"
                            annotations += "@Max(value = ${max}, message = \"${property.comment}不可大于${max}\")"
                        }
                        numberMin(it.typeCode, it.dataSize, it.numericPrecision)?.let { min ->
                            imports += "jakarta.validation.constraints.Min"
                            annotations += "@Min(value = ${min}, message = \"${property.comment}不可小于${min}\")"
                        }
                    }
                }

                in numericType -> {
                    property.column?.let {
                        numberMax(it.typeCode, it.dataSize, it.numericPrecision)?.let { max ->
                            imports += "jakarta.validation.constraints.DecimalMax"
                            annotations += "@DecimalMax(value = \"${max}\", message = \"${property.comment}不可大于${max}\")"
                        }
                        numberMin(it.typeCode, it.dataSize, it.numericPrecision)?.let { min ->
                            imports += "jakarta.validation.constraints.DecimalMin"
                            annotations += "@DecimalMin(value = \"${min}\", message = \"${property.comment}不可小于${min}\")"
                        }
                    }
                }
            }
        }

        return base.copy(
            imports = base.imports + imports,
            annotations = base.annotations + annotations,
        )
    }
}
