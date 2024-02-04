package top.potmot.service

import org.babyfish.jimmer.kt.merge
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.config.GlobalConfig
import top.potmot.model.dto.GenConfig
import top.potmot.model.dto.GenConfigProperties

@RestController
@RequestMapping("/config")
class ConfigService {
    @GetMapping
    fun getConfig(): GenConfig {
        return GlobalConfig.common
    }

    @PutMapping
    fun setConfig(
        @RequestBody newConfig: GenConfigProperties
    ) {
        val newConfigEntity = merge(
            GlobalConfig.common.toEntity(),
            newConfig.toEntity()
        )

        GlobalConfig.common = GenConfig(newConfigEntity)
    }
}
