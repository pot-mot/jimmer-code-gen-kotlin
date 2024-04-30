package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.builder.EntityBuilder
import top.potmot.entity.dto.GenEntityPropertiesView
import top.potmot.entity.dto.GenPropertyView

object KotlinEntityBuilder : EntityBuilder() {
    override fun packageLine(path: String): String = "package $path"

    override fun importLine(item: String): String = "import $item"

    override fun entityLine(entity: GenEntityPropertiesView): String =
        buildString {
            append("interface ${entity.name}")

            if (entity.superEntities.isNotEmpty()) {
                append(" :")
                append(" ${entity.superEntities.joinToString(", ") { it.name }}")
            }
        }

    override fun propertyLine(property: GenPropertyView): String =
        "val ${property.name}: ${property.shortType()}${if (property.typeNotNull) "" else "?"}"
}
