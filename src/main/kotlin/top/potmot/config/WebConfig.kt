package top.potmot.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import top.potmot.constant.API
import kotlin.reflect.full.findAnnotation

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        // 全局为 RestController 添加 /api 前缀
        configurer.addPathPrefix(API) { controller ->
            controller.kotlin.findAnnotation<RestController>() != null
        }
    }
}
