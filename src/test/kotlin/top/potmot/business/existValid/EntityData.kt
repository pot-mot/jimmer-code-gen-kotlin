package top.potmot.business.existValid

import top.potmot.business.enumNullableProperty
import top.potmot.business.enumProperty
import top.potmot.business.toManyProperty
import top.potmot.business.toOneNullableProperty
import top.potmot.business.toOneProperty
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_indexes
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_indexes.TargetOf_columns
import top.potmot.entity.dto.GenEntityBusinessView.TargetOf_indexes.TargetOf_columns.TargetOf_properties

val toOneUniqueIndex = TargetOf_indexes(
    id = 0,
    name = "index",
    uniqueIndex = true,
    columns = listOf(
        TargetOf_columns(
            id = 0,
            properties = listOf(
                TargetOf_properties(id = toOneProperty.id),
                TargetOf_properties(id = toOneNullableProperty.id)
            )
        )
    )
)

val enumUniqueIndex = TargetOf_indexes(
    id = 0,
    name = "index",
    uniqueIndex = true,
    columns = listOf(
        TargetOf_columns(
            id = 0,
            properties = listOf(
                TargetOf_properties(id = enumProperty.id),
                TargetOf_properties(id = enumNullableProperty.id)
            )
        )
    )
)

// 必然报错
val toManyUniqueIndex = TargetOf_indexes(
    id = 0,
    name = "index",
    uniqueIndex = true,
    columns = listOf(
        TargetOf_columns(
            id = 0,
            properties = listOf(
                TargetOf_properties(id = toManyProperty.id)
            )
        )
    )
)