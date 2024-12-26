package top.potmot.context

import org.babyfish.jimmer.kt.merge
import top.potmot.config.GlobalGenConfig
import top.potmot.entity.dto.GenConfig
import top.potmot.entity.dto.GenConfigProperties

private val contextLocal = ThreadLocal<GenConfig>()

private lateinit var globalGenConfig: GlobalGenConfig

fun initContextGlobal(init: GlobalGenConfig) {
    globalGenConfig = init
}

fun getContextOrGlobal() =
    contextLocal.get() ?: GenConfig(globalGenConfig.toEntity())

fun <T> useContext(
    initProperties: GenConfigProperties? = globalGenConfig.toProperties(),
    block: (context: GenConfig) -> T
): T {
    val merged = merge(
        globalGenConfig.toEntity(),
        initProperties?.toEntity()
    )

    val context = GenConfig(merged)

    contextLocal.set(context)
    val temp = block(context)
    contextLocal.remove()

    return temp
}
