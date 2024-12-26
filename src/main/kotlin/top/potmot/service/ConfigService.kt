package top.potmot.service

import org.babyfish.jimmer.kt.merge
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.config.GlobalGenConfig
import top.potmot.entity.dto.GenConfig
import top.potmot.entity.dto.GenConfigProperties
import top.potmot.entity.dto.MutableGenConfig
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

@RestController
@RequestMapping("/config")
class ConfigService(
    val globalGenConfig: GlobalGenConfig
) {
    @GetMapping
    fun getConfig(): GenConfig {
        return GenConfig(globalGenConfig.toEntity())
    }

    @PutMapping
    fun setConfig(
        @RequestBody properties: GenConfigProperties
    ) {
        val newConfig = MutableGenConfig(
            merge(
                globalGenConfig.toEntity(),
                properties.toEntity()
            )
        )

        globalGenConfig.copyPropertiesFrom(newConfig)
    }

    private fun MutableGenConfig.copyPropertiesFrom(source: MutableGenConfig) {
        val properties = MutableGenConfig::class.memberProperties
        for (property in properties) {
            if (property is KMutableProperty<*>) {
                property.isAccessible = true
                val value = (property).get(source)
                property.setter.call(this, value)
            }
        }
    }
}
