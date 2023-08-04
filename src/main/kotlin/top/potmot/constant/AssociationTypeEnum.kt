package top.potmot.constant

import org.babyfish.jimmer.sql.EnumItem
import org.babyfish.jimmer.sql.EnumType

@EnumType(EnumType.Strategy.NAME)
enum class AssociationTypeEnum {

    @EnumItem(name = "OneToOne")
    ONE_TO_ONE,

    @EnumItem(name = "ManyToOne")
    MANY_TO_ONE,

    @EnumItem(name = "OneToMany")
    ONE_TO_MANY,

    @EnumItem(name = "ManyToMany")
    MANY_TO_MANY,
}
