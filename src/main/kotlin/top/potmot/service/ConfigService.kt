package top.potmot.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.config.GenConfig
import top.potmot.config.GenConfigProperties

@RestController
@RequestMapping("/config")
class ConfigService {
    @GetMapping
    fun getConfig(): GenConfig {
        return GenConfig
    }

    @PutMapping
    fun setConfig(
        @RequestBody newConfig: GenConfigProperties
    ) {
        GenConfig.merge(newConfig)
    }
}
