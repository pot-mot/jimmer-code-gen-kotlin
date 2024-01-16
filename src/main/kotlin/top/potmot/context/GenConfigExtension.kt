package top.potmot.context

import top.potmot.config.GlobalGenConfig
import top.potmot.model.dto.GenConfig
import top.potmot.model.dto.GenConfigProperties
import top.potmot.utils.bean.copyProperties
import top.potmot.utils.bean.createDataClassFromObject
import kotlin.reflect.full.declaredMemberProperties

fun GlobalGenConfig.merge(properties: GenConfigProperties): GlobalGenConfig {
    copyProperties(properties, this)
    return this
}

fun GenConfig.merge(properties: GenConfigProperties): GenConfig {
    copyProperties(properties, this)
    return this
}

fun createGenConfig() =
    createDataClassFromObject(GlobalGenConfig, GenConfig::class)

private fun configEquals(global: GlobalGenConfig, context: GenConfig): Boolean {
    val globalProperties = GlobalGenConfig::class.declaredMemberProperties
    val contextPropertyMap = GenConfig::class.declaredMemberProperties.associateBy { it.name }

    for (globalProperty in globalProperties) {
        assert(contextPropertyMap.containsKey(globalProperty.name))

        val contextProperty = contextPropertyMap[globalProperty.name]!!

        val globalValue = globalProperty.get(global)
        val contextValue = contextProperty.get(context)

        if (globalValue != contextValue) {
            return false
        }
    }
    return true
}

infix fun GenConfig.equals(global: GlobalGenConfig) =
    configEquals(global, this)

infix fun GlobalGenConfig.equals(context: GenConfig) =
    configEquals(this, context)

fun GlobalGenConfig.toProperties(): GenConfigProperties =
    copyProperties(this, GenConfigProperties())

fun GenConfig.toProperties(): GenConfigProperties =
    copyProperties(this, GenConfigProperties())
