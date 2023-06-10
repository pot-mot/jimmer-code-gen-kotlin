package top.potmot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * 读取代码生成相关配置
 *
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "gen.association")
class AssociationConfig {
    fun setIgnoreColumns(ignoreColumns: List<String>) {
        Companion.ignoreColumns = ignoreColumns
    }

    companion object {
        /** 忽略列  */
        var ignoreColumns: List<String> = listOf("id", "remark")
    }
}