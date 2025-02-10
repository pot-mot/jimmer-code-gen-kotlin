package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.builder.EntityBuilder
import top.potmot.core.entity.generate.builder.EntityView
import top.potmot.core.entity.generate.builder.PropertyView
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
}
