package top.potmot.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.config.GlobalGenConfig
import top.potmot.entity.dto.GenConfigProperties

@RestController
@RequestMapping("/config")
class ConfigService(
    val globalGenConfig: GlobalGenConfig
) {
    @GetMapping
    fun getConfig() = globalGenConfig.toConfig()

    @PutMapping
    fun setConfig(
        @RequestBody properties: GenConfigProperties
    ) {
        globalGenConfig.merge(properties)
    }
}
