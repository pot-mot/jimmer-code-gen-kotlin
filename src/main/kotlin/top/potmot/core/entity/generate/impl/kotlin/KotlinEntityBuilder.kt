package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.builder.EntityBuilder
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenPropertyView
import top.potmot.utils.string.buildScopeString

object KotlinEntityBuilder : EntityBuilder() {
    override fun packageLine(path: String): String = "package $path"

    override fun importLine(item: String): String = "import $item"

    override fun entityLine(entity: GenEntityGenerateView): String =
        buildScopeString {
            append("interface ${entity.name}")

            if (entity.superEntities.isNotEmpty()) {
                append(" :")
                append(" ${entity.superEntities.joinToString(", ") { it.name }}")
            }
        }

    override fun propertyBlock(property: GenPropertyView) =
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
