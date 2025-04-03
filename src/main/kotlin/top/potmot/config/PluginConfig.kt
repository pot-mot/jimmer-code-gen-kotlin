package top.potmot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jimmer-code-gen-plugin")
class PluginConfig {
    var items: List<String> = emptyList()
}