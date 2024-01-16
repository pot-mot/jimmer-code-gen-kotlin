package top.potmot.utils.bean

import top.potmot.error.ConfigException
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

fun <O : Any, T : Any> createDataClassFromObject(obj: O, dataClass: KClass<T>): T {

    val dataClassPropertyMap = dataClass.declaredMemberProperties.associateBy { it.name }
    val objProperties = obj::class.declaredMemberProperties.associateBy { it.name }

    val constructorParameterValues = mutableMapOf<String, Any>()
    val notMatchConstructorParameters = mutableMapOf<String, Any?>()

    val constructors = dataClass.constructors.filter { it.parameters.size == dataClassPropertyMap.size }

    if (constructors.size != 1) {
        throw ConfigException("Create data class instance from a object fail\n can't find full parameter constructor")
    }

    val constructor = constructors[0]

    val constructorParameterNames = constructor.parameters.map { it.name }

    for (parameterName in constructorParameterNames) {
        if (parameterName == null) continue

        val constructorParameter = dataClassPropertyMap[parameterName] ?: continue
        val objProperty = objProperties[parameterName]
            ?: throw ConfigException("Create data class instance from a object fail\n can't find property $parameterName in obj")

        if (constructorParameter.returnType.classifier == constructorParameter.returnType.classifier
        ) {
            constructorParameterValues[parameterName] = (objProperty as KProperty1<O, *>).get(obj) ?: continue
        } else {
            notMatchConstructorParameters[parameterName] = (objProperty as KProperty1<O, *>).get(obj)
        }
    }

    if (constructorParameterValues.size < constructorParameterNames.size)
        throw ConfigException("Create data class instance from a object fail\n object properties less than data class properties")

    try {
        return constructor.callBy(
            constructor.parameters.associateWith {
                constructorParameterValues[it.name]
            }
        )
    } catch (e: Exception) {
        throw ConfigException(
            "Create data class instance from a object fail\n object properties not match with data class properties\n" +
                    " notMatchConstructorParameters: $notMatchConstructorParameters\n" +
                    " constructorParameterValues: $constructorParameterValues"
        )
    }
}
