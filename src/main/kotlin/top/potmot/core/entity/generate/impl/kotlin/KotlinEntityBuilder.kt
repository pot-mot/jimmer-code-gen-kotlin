package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.builder.EntityBuilder
import top.potmot.core.entity.generate.builder.EntityView
import top.potmot.core.entity.generate.builder.PropertyView
import top.potmot.core.common.intType
import top.potmot.core.common.numberMax
import top.potmot.core.common.numberMin
import top.potmot.core.common.numericType
import top.potmot.core.common.stringType
import top.potmot.entity.sub.AnnotationWithImports
import top.potmot.utils.string.buildScopeString

object KotlinEntityBuilder : EntityBuilder() {
    override val associationAnnotationBuilder = KotlinAssociationAnnotationBuilder

    override fun packageLine(path: String): String = "package $path"

    override fun importLine(item: String): String = "import $item"

    override fun entityLine(entity: EntityView): String =
        buildScopeString {
            append("interface ${entity.name}")

            if (entity.superEntities.isNotEmpty()) {
                append(" :")
                append(" ${entity.superEntities.joinToString(", ") { it.name }}")
            }
        }

    override fun propertyBlock(property: PropertyView) =
        buildScopeString {
            append("val ${property.name}: ${property.shortType()}${if (property.typeNotNull) "" else "?"}")

            if (property.body != null) {
                scope {
                    line()
                    scopeEndNoLine("get() {", "}") {
                        block(property.body.codeBlock)
                    }
                }
            }
        }

    override fun annotationWithImports(property: PropertyView): AnnotationWithImports {
        val base = super.annotationWithImports(property)

        val imports = mutableListOf<String>()
        val annotations = mutableListOf<String>()

        if (property.typeTable != null) {
            imports += "jakarta.validation.Valid"
            annotations += "@get:Valid"
        }

        if (!property.idProperty && !property.logicalDelete && property.associationType == null) {
            when (property.type) {
                in stringType -> {
                    property.column?.dataSize?.let {
                        imports += "org.hibernate.validator.constraints.Length"
                        annotations += "@get:Length(max = ${it})"
                    }
                }
                in intType -> {
                    property.column?.let {
                        numberMax(it.typeCode, it.dataSize, it.numericPrecision)?.let { max ->
                            imports += "jakarta.validation.constraints.Max"
                            annotations += "@get:Max(value = ${max}, message = \"${property.comment}不可大于${max}\")"
                        }
                        numberMin(it.typeCode, it.dataSize, it.numericPrecision)?.let { min ->
                            imports += "jakarta.validation.constraints.Min"
                            annotations += "@get:Min(value = ${min}, message = \"${property.comment}不可小于${min}\")"
                        }
                    }
                }
                in numericType -> {
                    property.column?.let {
                        numberMax(it.typeCode, it.dataSize, it.numericPrecision)?.let { max ->
                            imports += "jakarta.validation.constraints.DecimalMax"
                            annotations += "@get:DecimalMax(value = \"${max}\", message = \"${property.comment}不可大于${max}\")"
                        }
                        numberMin(it.typeCode, it.dataSize, it.numericPrecision)?.let { min ->
                            imports += "jakarta.validation.constraints.DecimalMin"
                            annotations += "@get:DecimalMax(value = \"${min}\", message = \"${property.comment}不可小于${min}\")"
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
