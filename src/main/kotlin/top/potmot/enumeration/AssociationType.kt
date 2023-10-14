package top.potmot.enumeration

import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.OneToOne
import kotlin.reflect.KClass

enum class AssociationType {
    ONE_TO_ONE,
    MANY_TO_ONE,
    ONE_TO_MANY,
    MANY_TO_MANY;

    fun toAnnotation(): KClass<out Annotation> =
        when (this) {
            ONE_TO_ONE -> OneToOne::class
            MANY_TO_ONE -> ManyToOne::class
            ONE_TO_MANY -> OneToMany::class
            MANY_TO_MANY -> ManyToMany::class
        }

    companion object {
        fun fromValue(value: String?): AssociationType? {
            if (value == null) return null
            return try {
                AssociationType.valueOf(value.uppercase())
            } catch (e: Exception) {
                null
            }
        }
    }
}
