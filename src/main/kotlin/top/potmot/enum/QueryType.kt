package top.potmot.enum

import org.babyfish.jimmer.sql.EnumType

@EnumType(EnumType.Strategy.NAME)
enum class QueryType {
    EQ,
    NE,
    GT,
    GTE,
    LT,
    LTE,
    BETWEEN,
    IN,
    LIKE,
    ILIKE
}
