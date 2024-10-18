package top.potmot.context

import org.babyfish.jimmer.kt.merge
import top.potmot.config.GlobalGenConfig
import top.potmot.entity.dto.GenConfig
import top.potmot.entity.dto.GenConfigProperties

private val contextLocal = ThreadLocal<GenConfig>()

fun getContextOrGlobal() =
    contextLocal.get() ?: GenConfig(GlobalGenConfig.toEntity())

fun <T> useContext(
    initProperties: GenConfigProperties? = GlobalGenConfig.toProperties(),
    block: (context: GenConfig) -> T
): T {
    val merged = merge(
        GlobalGenConfig.toEntity(),
        initProperties?.toEntity()
    )

    val context = GenConfig(merged)

    contextLocal.set(context)
    val temp = block(context)
    contextLocal.remove()

    return temp
}
