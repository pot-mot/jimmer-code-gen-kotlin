package top.potmot.enumeration

import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import kotlin.reflect.KClass

enum class AssociationType(
    val isTargetOne: Boolean,
    val isTargetMany: Boolean = !isTargetOne
) {
    ONE_TO_ONE(true),
    MANY_TO_ONE(true),
    ONE_TO_MANY(false),
    MANY_TO_MANY(false);

    fun toAnnotation(): KClass<out Annotation> =
        when (this) {
            ONE_TO_ONE -> OneToOne::class
            MANY_TO_ONE -> ManyToOne::class
            ONE_TO_MANY -> OneToMany::class
            MANY_TO_MANY -> ManyToMany::class
        }

    fun reversed(): AssociationType =
        when (this) {
            ONE_TO_ONE -> ONE_TO_ONE
            MANY_TO_ONE -> ONE_TO_MANY
            ONE_TO_MANY -> MANY_TO_ONE
            MANY_TO_MANY -> MANY_TO_MANY
        }
}
