package top.potmot.model.enums

import top.potmot.entity.dto.GenModelInput.TargetOf_enums
import top.potmot.entity.dto.GenModelInput.TargetOf_enums.TargetOf_items
import top.potmot.enumeration.EnumType


val COMMON_ENUM = TargetOf_enums(
    packagePath = "top.potmot.enum",
    name = "EnumCommon",
    comment = "",
    enumType = null,
    remark = "",
    modelId = 2L,
    items = listOf(
        TargetOf_items(
            name = "item1",
            mappedValue = "",
            comment = "",
            defaultItem = true,
            orderKey = 0,
            remark = ""
        ),
        TargetOf_items(
            name = "item2",
            mappedValue = "",
            comment = "",
            defaultItem = false,
            orderKey = 1,
            remark = ""
        )
    )
)

val ORDINAL_ENUM = TargetOf_enums(
    packagePath = "top.potmot.enum",
    name = "EnumOrdinal",
    comment = "",
    enumType = EnumType.ORDINAL,
    remark = "",
    modelId = 2L,
    items = listOf(
        TargetOf_items(
            name = "item1",
            mappedValue = "0",
            comment = "",
            defaultItem = true,
            orderKey = 0,
            remark = ""
        ),
        TargetOf_items(
            name = "item2",
            mappedValue = "1",
            comment = "",
            defaultItem = false,
            orderKey = 1,
            remark = ""
        )
    )
)

val NAME_ENUM = TargetOf_enums(
    packagePath = "top.potmot.enum",
    name = "EnumName",
    comment = "",
    enumType = EnumType.NAME,
    remark = "",
    modelId = 2L,
    items = listOf(
        TargetOf_items(
            name = "item1",
            mappedValue = "item1",
            comment = "",
            defaultItem = true,
            orderKey = 0,
            remark = ""
        ),
        TargetOf_items(
            name = "item2",
            mappedValue = "item2",
            comment = "",
            defaultItem = false,
            orderKey = 1,
            remark = ""
        )
    )
)

val ENUMS = listOf(
    COMMON_ENUM,
    ORDINAL_ENUM,
    NAME_ENUM
)
