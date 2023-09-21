package top.potmot.enum

import org.babyfish.jimmer.sql.EnumType

@EnumType(EnumType.Strategy.ORDINAL)
enum class SortDirection {
    ASC,
    DESC
}
