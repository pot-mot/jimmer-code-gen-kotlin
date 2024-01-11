package top.potmot.utils.bean

import kotlin.reflect.KClass
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

fun <O : Any, T : Any> createDataClassFromObject(obj: O, dataClass: KClass<T>): T? {
    val objProperties = obj::class.declaredMemberProperties
    val dataClassPropertyMap = dataClass.declaredMemberProperties.associateBy { it.name }

    val propertyValues = mutableMapOf<String, Any>()

    for (objProperty in objProperties) {
        val dataClassProperty = dataClassPropertyMap[objProperty.name] ?: continue

        if (objProperty.name == dataClassProperty.name &&
            objProperty.returnType.classifier == dataClassProperty.returnType.classifier
        ) {
            val value = (objProperty as KProperty1<O, *>).get(obj)
            propertyValues[dataClassProperty.name] = value ?: continue
        }
    }

    return if (propertyValues.size == dataClassPropertyMap.size) {
        val constructor = dataClass.constructors.first()
        constructor.callBy(constructor.parameters.associateWith { propertyValues[it.name] })
    } else {
        null
    }
}
