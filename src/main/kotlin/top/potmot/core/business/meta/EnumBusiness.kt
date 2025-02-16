package top.potmot.core.business.meta

import top.potmot.core.config.getContextOrGlobal
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.IdName
import top.potmot.error.ModelException

data class EnumBusiness(
    val enum: GenEnumGenerateView,
) {
    val id = enum.id

    val packagePath = enum.packagePath

    val name = enum.name

    val comment = enum.comment

    val dir = "enums/${name.replaceFirstChar { it.lowercase() }}"

    val constants = "${name}_CONSTANTS"

    val components by lazy {
        val suffix = getContextOrGlobal().viewType.suffix
        EnumComponentFiles(
            view = NamePath("${enum.name}View", suffix, "components/${dir}"),
            nullableView = NamePath("${enum.name}NullableView", suffix, "components/${dir}"),
            multipleView = NamePath("${enum.name}MultiView", suffix, "components/${dir}"),
            select = NamePath("${enum.name}Select", suffix, "components/${dir}"),
            nullableSelect = NamePath("${enum.name}NullableSelect", suffix, "components/${dir}"),
            multipleSelect = NamePath("${enum.name}MultiSelect", suffix, "components/${dir}"),
        )
    }

    val items = enum.items

    val defaultItem by lazy {
        items.firstOrNull { it.defaultItem }
            ?: throw ModelException.defaultItemNotFound("enumName: $name", enum = IdName(id, name))
    }
}