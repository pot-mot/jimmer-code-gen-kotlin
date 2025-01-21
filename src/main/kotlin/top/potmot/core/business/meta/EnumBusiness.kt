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
        EnumComponentNames(
            view = "${enum.name}View",
            select = "${enum.name}Select",
            nullableSelect = "${enum.name}NullableSelect",
        )
    }

    val items = enum.items

    val defaultItem by lazy {
        items.firstOrNull { it.defaultItem }
            ?: throw ModelException.defaultItemNotFound("enumName: $name", enum = IdName(id, name))
    }
}