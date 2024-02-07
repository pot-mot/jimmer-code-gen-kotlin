package top.potmot.core.entity.generate.impl.kotlin

import top.potmot.core.entity.generate.builder.EntityBuilder
import top.potmot.model.dto.GenEntityPropertiesView
import top.potmot.model.extension.shortType

object KotlinEntityBuilder : EntityBuilder() {
    override fun packageLine(path: String): String = "package $path"

    override fun importLine(item: String): String = "import $item"

    override fun entityLine(entity: GenEntityPropertiesView): String =
        "interface ${entity.name}"

    override fun propertyLine(property: GenEntityPropertiesView.TargetOf_properties): String =
        "val ${property.name}: ${property.shortType()}${if (property.typeNotNull) "" else "?"}"
}
