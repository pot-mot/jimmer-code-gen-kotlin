package top.potmot.utils.bean

import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

fun <T : Any> T.copyPropertiesFrom(source: T) {
    val properties = this::class.memberProperties
    for (property in properties) {
        if (property is KMutableProperty<*>) {
            property.isAccessible = true
            val value = (property as KProperty1<T, *>).get(source)
            property.setter.call(this, value)
        }
    }
}
