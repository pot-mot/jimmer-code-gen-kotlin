package top.potmot.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.config.GlobalGenConfig
import top.potmot.context.GenConfig
import top.potmot.context.GenConfigProperties
import top.potmot.context.createGenConfig
import top.potmot.context.merge

@RestController
@RequestMapping("/config")
class ConfigService {
    @GetMapping
    fun getConfig(): GenConfig {
        return createGenConfig()
    }

    @PutMapping
    fun setConfig(
        @RequestBody newConfig: GenConfigProperties
    ) {
        GlobalGenConfig.merge(newConfig)
    }
}
