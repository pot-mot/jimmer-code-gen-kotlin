package top.potmot.core.business.meta

import top.potmot.entity.dto.GenerateFile

sealed interface LazyGenerated {
    val component: NamePath
    val key: String
}

data class LazyIdSelect(
    val entity: SubEntityBusiness,
    val multiple: Boolean,
) : LazyGenerated {
    override val key = "LazyIdSelect(${entity.packagePath} ${entity.name} $multiple)"

    override val component =
        if (multiple) entity.components.idMultiSelect
        else entity.components.idSelect
}

data class LazyShortViewTable(
    val entity: SubEntityBusiness,
    val properties: Collection<PropertyBusiness>,
) : LazyGenerated {
    override val key = "LazyShortViewTable(${entity.packagePath} ${entity.name} ${properties.joinToString { it.name }})"

    override val component =
        entity.components.viewTable

    val propName = "rows"
}

data class LazySubEdit(
    val entity: SubEntityBusiness,
    val multiple: Boolean,
    val nullable: Boolean,
) : LazyGenerated {
    override val key = "LazySubEdit(${entity.path.rootEntity.name} ${entity.path.propertyItems.joinToString { it.property.name }} ${entity.packagePath} ${entity.name} $multiple $nullable)"

    override val component =
        if (multiple) entity.components.editTable
        else entity.components.editForm

    val componentRef =
        component.name.replaceFirstChar { it.uppercaseChar() } + (if (multiple) "Refs" else "Ref")
}

data class LazySubView(
    val entity: SubEntityBusiness,
    val multiple: Boolean,
    val nullable: Boolean,
) : LazyGenerated {
    override val key = "LazySubView(${entity.path.rootEntity.name} ${entity.path.propertyItems.joinToString { it.property.name }} ${entity.packagePath} ${entity.name} $multiple $nullable)"

    override val component =
        if (multiple) entity.components.viewTable
        else entity.components.viewForm

    val propName = if (multiple) "rows" else "value"
}

data class LazyEnumSelect(
    val enum: EnumBusiness,
    val multiple: Boolean,
    val nullable: Boolean,
) : LazyGenerated {
    override val key = "LazyEnumSelect(${enum.packagePath} ${enum.name} $multiple $nullable)"

    override val component =
        if (multiple) enum.components.multipleSelect
        else if (nullable) enum.components.nullableSelect
        else enum.components.select

    val type = if (multiple) "Array<${enum.name}>" else if (nullable) "${enum.name} | undefined" else enum.name

    val lazySingleView = LazyEnumView(
        enum, multiple = false, nullable = false
    )
}

data class LazyEnumView(
    val enum: EnumBusiness,
    val multiple: Boolean,
    val nullable: Boolean,
) : LazyGenerated {
    override val key = "LazyEnumView(${enum.packagePath} ${enum.name} $multiple $nullable)"

    override val component =
        if (multiple) enum.components.multipleView
        else if (nullable) enum.components.nullableView
        else enum.components.view

    val type = if (multiple) "Array<${enum.name}>" else if (nullable) "${enum.name} | undefined" else enum.name
}

data class LazyGenerateResult(
    val files: List<GenerateFile>,
    val lazyItems: List<LazyGenerated>,
)
