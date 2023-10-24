package top.potmot.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.config.GenConfig
import top.potmot.config.GenConfigProperties

@RestController
@RequestMapping("/config")
class ConfigService (
    @Autowired val genConfig: GenConfig
) {
    @GetMapping
    fun getConfig(): GenConfig {
        return genConfig
    }

    @PutMapping
    fun setConfig(
        @RequestBody newConfig: GenConfigProperties
    ) {
        genConfig.merge(newConfig)
    }
}
