package top.potmot.enum

import org.babyfish.jimmer.sql.EnumItem
import org.babyfish.jimmer.sql.EnumType
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import top.potmot.enum.AssociationType.*
import kotlin.reflect.KClass

@EnumType(EnumType.Strategy.NAME)
enum class AssociationType {

    @EnumItem(name = "OneToOne")
    ONE_TO_ONE,

    @EnumItem(name = "ManyToOne")
    MANY_TO_ONE,

    @EnumItem(name = "OneToMany")
    ONE_TO_MANY,

    @EnumItem(name = "ManyToMany")
    MANY_TO_MANY,
}

fun getAssociationAnnotation(type: AssociationType): KClass<out Annotation> {
    return when (type) {
        ONE_TO_ONE -> OneToOne::class
        MANY_TO_ONE -> ManyToOne::class
        ONE_TO_MANY -> OneToMany::class
        MANY_TO_MANY -> ManyToMany::class
    }
}

fun AssociationType.getAnnotation(): KClass<out Annotation> {
    return getAssociationAnnotation(this)
}
