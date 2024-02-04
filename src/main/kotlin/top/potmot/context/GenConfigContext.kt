package top.potmot.context

import org.babyfish.jimmer.kt.merge
import top.potmot.config.GlobalConfig
import top.potmot.model.dto.GenConfig
import top.potmot.model.dto.GenConfigProperties

private val contextMap = mutableMapOf<Long, GenConfig>()

fun getContext(id: Long = Thread.currentThread().id) =
    contextMap[id]

fun getContextOrGlobal(id: Long = Thread.currentThread().id) =
    contextMap[id] ?: GlobalConfig.common

fun cleanContext(id: Long = Thread.currentThread().id) {
    contextMap.remove(id)
}

fun <T> useContext(
    initProperties: GenConfigProperties? = GlobalConfig.common.toProperties(),
    id: Long = Thread.currentThread().id,
    block: () -> T
): T {
    val context = merge(
        GlobalConfig.common.toEntity(),
        initProperties?.toEntity()
    )
    contextMap[id] = GenConfig(context)
    val temp = block()
    cleanContext(id)

    return temp
}
