package top.potmot.service

import org.babyfish.jimmer.kt.merge
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.config.GlobalGenConfig
import top.potmot.model.dto.GenConfig
import top.potmot.model.dto.GenConfigProperties
import top.potmot.model.dto.MutableGenConfig
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

@RestController
@RequestMapping("/config")
class ConfigService {
    @GetMapping
    fun getConfig(): GenConfig {
        return GenConfig(GlobalGenConfig.toEntity())
    }

    @PutMapping
    fun setConfig(
        @RequestBody properties: GenConfigProperties
    ) {
        val newConfig = MutableGenConfig(
            merge(
                GlobalGenConfig.toEntity(),
                properties.toEntity()
            )
        )

        GlobalGenConfig.copyPropertiesFrom(newConfig)
    }

    private fun MutableGenConfig.copyPropertiesFrom(source: MutableGenConfig) {
        val properties = this::class.memberProperties
        for (property in properties) {
            if (property is KMutableProperty<*>) {
                property.isAccessible = true
                val value = (property as KProperty1<MutableGenConfig, *>).get(source)
                property.setter.call(this, value)
            }
        }
    }
}
