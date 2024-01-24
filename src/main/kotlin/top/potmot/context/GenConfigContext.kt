package top.potmot.context

import top.potmot.config.GlobalConfig
import top.potmot.model.dto.GenConfig

private val genConfigContextMap = mutableMapOf<Long, GenConfig>()

fun hasContextGenConfig(id: Long = Thread.currentThread().id): Boolean =
    genConfigContextMap.containsKey(id)

fun getContextGenConfig(id: Long = Thread.currentThread().id): GenConfig {
    val value = genConfigContextMap[id]

    return if (value == null) {
        val newContext = GlobalConfig.common.copy()
        genConfigContextMap[id] = newContext
        newContext
    } else {
        value
    }
}

fun cleanContextGenConfig(id: Long = Thread.currentThread().id) {
    genConfigContextMap.remove(id)
}
