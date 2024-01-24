package top.potmot.utils.bean

import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

fun <T : Any, R : Any> copyProperties(source: T, target: R): R {
    val sourceProperties = source::class.declaredMemberProperties
    val targetPropertyMap = target::class.declaredMemberProperties.associateBy { it.name }

    for (sourceProperty in sourceProperties) {
        val sourceValue = (sourceProperty as KProperty1<T, *>).get(source)

        val targetProperty = targetPropertyMap[sourceProperty.name] ?: continue

        if (
            sourceValue != null &&
            targetProperty is KMutableProperty<*> &&
            targetProperty.name == sourceProperty.name &&
            targetProperty.returnType.classifier == sourceProperty.returnType.classifier
        ) {
            (targetProperty as KMutableProperty<*>).setter.call(target, sourceValue)
        }
    }

    return target
}
