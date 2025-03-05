package top.potmot.core.entity.generate.impl.kotlin

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

object KotlinEntityBuilder : EntityBuilder() {
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
                    line("get() {")
                    scope {
                        block(property.body.codeBlock)
                    }
                    append("}")
                }
            }
        }

    override fun validateAnnotations(property: PropertyView): AnnotationWithImports {
        val imports = mutableListOf<String>()
        val annotations = mutableListOf<String>()

        if (property.typeTable != null) {
            imports += "javax.validation.Valid"
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
                            imports += "javax.validation.constraints.Max"
                            annotations += "@Max(value = ${max}, message = \"${property.comment}不可大于${max}\")"
                        }
                        numberMin(it.typeCode, it.dataSize, it.numericPrecision)?.let { min ->
                            imports += "javax.validation.constraints.Min"
                            annotations += "@Min(value = ${min}, message = \"${property.comment}不可小于${min}\")"
                        }
                    }
                }
                in numericType -> {
                    property.column?.let {
                        numberMax(it.typeCode, it.dataSize, it.numericPrecision)?.let { max ->
                            imports += "javax.validation.constraints.DecimalMax"
                            annotations += "@DecimalMax(value = \"${max}\", message = \"${property.comment}不可大于${max}\")"
                        }
                        numberMin(it.typeCode, it.dataSize, it.numericPrecision)?.let { min ->
                            imports += "javax.validation.constraints.DecimalMin"
                            annotations += "@DecimalMax(value = \"${min}\", message = \"${property.comment}不可小于${min}\")"
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
