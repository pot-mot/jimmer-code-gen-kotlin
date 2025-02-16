package top.potmot.core.business.meta

import top.potmot.entity.dto.GenerateFile

sealed interface LazyGenerated {
    val component: NamePath
}

data class LazyIdSelect(
    val entity: SubEntityBusiness,
    val multiple: Boolean,
) : LazyGenerated {
    override val component =
        if (multiple) entity.components.idMultiSelect
        else entity.components.idSelect
}

data class LazyShortViewTable(
    val entity: SubEntityBusiness,
    val properties: Collection<PropertyBusiness>,
) : LazyGenerated {
    override val component =
        entity.components.viewTable

    val propName = "value"
}

data class LazySubEdit(
    val entity: SubEntityBusiness,
    val multiple: Boolean,
    val nullable: Boolean,
) : LazyGenerated {
    override val component =
        if (multiple) entity.components.editTable
        else entity.components.editForm

    val componentRef =
        component.name.replaceFirstChar { it.uppercaseChar() } + "Ref"
}

data class LazySubView(
    val entity: SubEntityBusiness,
    val multiple: Boolean,
    val nullable: Boolean,
) : LazyGenerated {
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
    override val component =
        if (multiple) enum.components.multipleSelect
        else if (nullable) enum.components.nullableSelect
        else enum.components.select

    val type = if (multiple) "Array<${enum.name}>" else if (nullable) "${enum.name} | undefined" else enum.name

    val lazyView = LazyEnumView(
        enum, multiple, nullable
    )
}

data class LazyEnumView(
    val enum: EnumBusiness,
    val multiple: Boolean,
    val nullable: Boolean,
) : LazyGenerated {
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
