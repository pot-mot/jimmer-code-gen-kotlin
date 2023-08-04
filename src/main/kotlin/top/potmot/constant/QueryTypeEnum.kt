package top.potmot.constant

import org.babyfish.jimmer.sql.EnumType

@EnumType(EnumType.Strategy.NAME)
enum class QueryTypeEnum {
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
