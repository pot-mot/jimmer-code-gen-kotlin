package top.potmot.context

import top.potmot.model.dto.GenConfig

private val genConfigContextMap = mutableMapOf<Long, GenConfig>()

fun getContextGenConfig(id: Long = Thread.currentThread().id): GenConfig {
    val value = genConfigContextMap[id]

    return if (value == null) {
        val newGenConfig = createGenConfig()
        genConfigContextMap[id] = newGenConfig
        newGenConfig
    } else {
        value
    }
}

fun cleanContextGenConfig(id: Long = Thread.currentThread().id) {
    genConfigContextMap.remove(id)
}
