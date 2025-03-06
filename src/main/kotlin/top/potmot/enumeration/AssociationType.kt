package top.potmot.enumeration

import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import kotlin.reflect.KClass

enum class AssociationType(
    val annotation: KClass<out Annotation>,
    val isTargetOne: Boolean,
) {
    ONE_TO_ONE(OneToOne::class, true),
    MANY_TO_ONE(ManyToOne::class, true),
    ONE_TO_MANY(OneToMany::class, false),
    MANY_TO_MANY(ManyToMany::class, false);

    fun reversed(): AssociationType =
        when (this) {
            ONE_TO_ONE -> ONE_TO_ONE
            MANY_TO_ONE -> ONE_TO_MANY
            ONE_TO_MANY -> MANY_TO_ONE
            MANY_TO_MANY -> MANY_TO_MANY
        }
}
