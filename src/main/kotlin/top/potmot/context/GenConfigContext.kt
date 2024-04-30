package top.potmot.context

import org.babyfish.jimmer.kt.merge
import top.potmot.config.GlobalGenConfig
import top.potmot.entity.dto.GenConfig
import top.potmot.entity.dto.GenConfigProperties

private val contextMap = mutableMapOf<Long, GenConfig>()

fun getContext(id: Long = Thread.currentThread().id) =
    contextMap[id]

fun getContextOrGlobal(id: Long = Thread.currentThread().id) =
    contextMap[id] ?: GenConfig(GlobalGenConfig.toEntity())

fun cleanContext(id: Long = Thread.currentThread().id) {
    contextMap.remove(id)
}

fun <T> useContext(
    initProperties: GenConfigProperties? = GlobalGenConfig.toProperties(),
    id: Long = Thread.currentThread().id,
    block: () -> T
): T {
    val context = merge(
        GlobalGenConfig.toEntity(),
        initProperties?.toEntity()
    )
    contextMap[id] = GenConfig(context)
    val temp = block()
    cleanContext(id)

    return temp
}
