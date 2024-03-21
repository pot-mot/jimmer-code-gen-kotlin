package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.builder.EntityBuilder
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.dto.GenPropertyView

object KotlinEntityBuilder : EntityBuilder() {
    override fun packageLine(path: String): String = "package $path"

    override fun importLine(item: String): String = "import $item"

    override fun entityLine(entity: GenEntityPropertiesView): String =
        buildString {
            append("interface ${entity.name}")

            if (entity.superEntities.isNotEmpty()) {
                append(" :")
                entity.superEntities.forEach {
                    append(" ${it.name},")
                }
            }
        }

    override fun propertyLine(property: GenPropertyView): String =
        "val ${property.name}: ${property.shortType()}${if (property.typeNotNull) "" else "?"}"
}
