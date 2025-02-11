package top.potmot.config

import javax.annotation.PostConstruct
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import top.potmot.core.config.initContextGlobal
import top.potmot.core.config.MutableGenConfig

/**
 * 代码生成配置
 */
@Component
@ConfigurationProperties(prefix = "jimmer-code-gen")
class GlobalGenConfig : MutableGenConfig() {
    @PostConstruct
    fun init() {
        initContextGlobal(this)
    }
}
