package top.potmot.core.config

import org.babyfish.jimmer.kt.merge
import top.potmot.entity.dto.GenConfig
import top.potmot.entity.dto.GenConfigProperties

private var globalGenConfig: MutableGenConfig = MutableGenConfig()

fun initContextGlobal(init: MutableGenConfig) {
    globalGenConfig = init
}

private val contextLocal = ThreadLocal<GenConfig>()

fun getContextOrGlobal() =
    contextLocal.get() ?: globalGenConfig.toConfig()

fun <T> useContext(
    initProperties: GenConfigProperties? = globalGenConfig.toProperties(),
    block: (context: GenConfig) -> T,
): T {
    val context = GenConfig(
        merge(
            globalGenConfig.toEntity(),
            initProperties?.toEntity()
        )
    )

    contextLocal.set(context)
    val temp = block(context)
    contextLocal.remove()

    return temp
}
