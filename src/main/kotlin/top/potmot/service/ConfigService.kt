package top.potmot.service

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.config.GlobalGenConfig
import top.potmot.context.createGenConfig
import top.potmot.context.getContextGenConfig
import top.potmot.context.hasContextGenConfig
import top.potmot.context.merge
import top.potmot.model.dto.GenConfig
import top.potmot.model.dto.GenConfigProperties

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
        if (hasContextGenConfig()) {
            getContextGenConfig().merge(newConfig)
        }
    }
}
