package top.potmot.core.business.meta

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
        EnumComponentFiles(
            view = NamePath("${enum.name}View", "vue", "components/${dir}"),
            select = NamePath("${enum.name}Select", "vue", "components/${dir}"),
            nullableSelect = NamePath("${enum.name}NullableSelect", "vue", "components/${dir}"),
        )
    }

    val items = enum.items

    val defaultItem by lazy {
        items.firstOrNull { it.defaultItem }
            ?: throw ModelException.defaultItemNotFound("enumName: $name", enum = IdName(id, name))
    }
}