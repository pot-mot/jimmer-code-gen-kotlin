package top.potmot.constant

import org.babyfish.jimmer.sql.EnumType

@EnumType(EnumType.Strategy.ORDINAL)
enum class SortDirection {
    ASC,
    DESC
}
